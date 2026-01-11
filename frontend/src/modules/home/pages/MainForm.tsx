import { useEffect, useState } from "react";
import "./mainform.css";
import { ProductGrid } from "./ProductGrid";
import * as z from 'zod';
import { useNavigate } from "react-router-dom";

type Product = {
    id: number;
    name: string;
    price: number;
    region: string;
    weight: number;
};

export const contactSchema = z.object({
    name: z
        .string()
        .min(2, "Imię i nazwisko są za krótkie")
        .regex(/^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]+$/, "Użyj tylko liter"),

    mail: z
        .string()
        .email("To nie jest poprawny adres e-mail"),

    address: z
        .string()
        .min(5, "Adres musi mieć co najmniej 5 znaków"),

    postal_code: z
        .string()
        .regex(/^\d{2}-\d{3}$/, "Kod pocztowy musi mieć format 00-000"),

    city: z
        .string()
        .min(2, "Nazwa miasta jest za krótka")
});

function extractBrand(productName: string): string {
    const knownBrands = [
        "Beauty of Joseon",
        "COSRX",
        "Dr. Althea",
        "I’m From",
        "Round Lab"
    ];

    const found = knownBrands.find((brand) =>
        productName.startsWith(brand)
    );

    return found ?? "Inne";
}

export default function MainForm() {
    const [products, setProducts] = useState<Product[]>([]);
    const [chosenBrand, setChosenBrand] = useState("");
    const [chosenProducts, setChosenProducts] = useState<number[]>([]);
    const [showConfirmModal, setShowConfirmModal] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [errors, setErrors] = useState("");
    const navigate = useNavigate();
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

    const [userData, setUserData] = useState({
        name: "",
        mail: "",
        address: "",
        postal_code: "",
        city: ""
    });

    // fetch
    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await fetch(
                    "https://concerned-sprayless-brandie.ngrok-free.dev/products",
                    {
                        headers: {
                            "ngrok-skip-browser-warning": "true"
                        }
                    }
                );

                if (!response.ok) {
                    throw new Error("Błąd pobierania produktów");
                }

                const data = await response.json();
                setProducts(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchProducts();
    }, []);

    // marki
    const brands = Array.from(
        new Set(products.map(p => extractBrand(p.name)))
    );

    // produkty danej marki
    const filteredProducts = products.filter(
        p => extractBrand(p.name) === chosenBrand
    );

    const handleCheck = () => {
        const validation = contactSchema.safeParse(userData);

        if (!validation.success) {
            // Mapujemy błędy Zod na stan Twoich komunikatów w UI
            //const errorMessages = validation.error.map(err => err.message);
            const pretty = z.prettifyError(validation.error);
            setErrors(pretty);
            return false;
        }

        setErrors(""); // Czyścimy błędy, jeśli wszystko OK
        return true;
    }
    // submit
    const handleSubmit = async (e: React.FormEvent) => {

        e.preventDefault()
        try {
            if (chosenProducts.length === 0) {
                alert("Wybierz przynajmniej jeden produkt");
                return;
            }

            for (const productId of chosenProducts) {
                const product = products.find(p => p.id === productId);
                if (!product) continue;

                const response = await fetch(
                    "https://concerned-sprayless-brandie.ngrok-free.dev/single_orders",
                    {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "ngrok-skip-browser-warning": "true"
                        },
                        body: JSON.stringify({
                            productName: product.name,
                            price: product.price,
                            userEmail: userData.mail
                        })
                    }
                );

                if (!response.ok) {
                    throw new Error("Błąd zapisu zamówienia");
                }
            }

            alert("✅ Zamówienie zapisane!");
            setChosenProducts([]);
            setUserData({ name: "", mail: "", address: "", postal_code: "", city: "" });

        } catch (err) {
            console.error(err);
            alert("❌ Błąd przy zapisie zamówienia");
        }
    };

    return (
        <div className="mainform-container">
            <div className="order-card">
                <h2>Zbierz grupę i zamów już teraz!</h2>

                {/* MARKA */}
                <div className="selection-section">
                    <label>Marka:</label>
                    <select
                        value={chosenBrand}
                        onChange={(e) => {
                            setChosenBrand(e.target.value);
                            setChosenProducts([]);
                        }}
                    >
                        <option value="">--Wybierz markę--</option>
                        {brands.map((brand) => (
                            <option key={brand} value={brand}>
                                {brand}
                            </option>
                        ))}
                    </select>
                </div>

                {/* PRODUKTY */}
                {chosenBrand && (
                    <div className="selection-section">
                        <label>Wybierz produkty:</label>
                        <ProductGrid
                            products={filteredProducts}
                            selectedIds={chosenProducts}
                            onSelect={(id) => {
                                if (chosenProducts.includes(id)) {
                                    setChosenProducts(chosenProducts.filter(i => i !== id));
                                } else {
                                    setChosenProducts([...chosenProducts, id]);
                                }
                            }}
                        />
                    </div>
                )}
                {errors && <p style={{ color: 'red' }}>{errors}</p>}
                {/* FORMULARZ */}
                {chosenProducts.length > 0 && (
                    <div className="user-details-form">
                        <hr />
                        <form
                            onSubmit={(e) => {
                                e.preventDefault();
                                if (!handleCheck()) {
                                    return;
                                }
                                if (!isLoggedIn) {
                                    navigate("/login");
                                    return;
                                }
                                setShowConfirmModal(true);
                            }}
                        >

                            <label>Imię i Nazwisko</label>
                            <input
                                value={userData.name}
                                onChange={(e) =>
                                    setUserData({ ...userData, name: e.target.value })
                                }
                            />

                            <label>Email</label>
                            <input
                                type="email"
                                value={userData.mail}
                                onChange={(e) =>
                                    setUserData({ ...userData, mail: e.target.value })
                                }
                            />
                            <label>Adres</label>
                            <input
                                value={userData.address}
                                onChange={(e) =>
                                    setUserData({ ...userData, address: e.target.value })
                                }
                            />
                            <label>Kod pocztowy</label>
                            <input
                                value={userData.postal_code}
                                onChange={(e) =>
                                    setUserData({ ...userData, postal_code: e.target.value })
                                }
                            />
                            <label>Miasto</label>
                            <input
                                value={userData.city}
                                onChange={(e) =>
                                    setUserData({ ...userData, city: e.target.value })
                                }
                            />

                            <button type="submit" className="submit-btn">
                                Potwierdź zamówienie
                            </button>
                        </form>
                    </div>
                )}
            </div>
            {showConfirmModal && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h3>Potwierdzenie zamówienia</h3>

                        <p>
                            Finalna cena Twojego zamówienia zostanie wysłana na adres
                            <strong> {userData.mail}</strong>, gdy grupa zakupowa
                            zostanie skompletowana.
                        </p>

                        <p>
                            Aby zarezerwować udział w zamówieniu, wymagana jest
                            <strong> zaliczka 40 zł</strong>.
                        </p>

                        <p className="modal-small">
                            Zaliczka jest bezzwrotna i zostanie odliczona od ceny końcowej.
                        </p>

                        <div className="modal-actions">
                            <button
                                className="modal-cancel"
                                onClick={() => setShowConfirmModal(false)}
                            >
                                Anuluj
                            </button>

                            <button
                                className="modal-confirm"
                                disabled={isSubmitting}
                                onClick={async () => {
                                    setIsSubmitting(true);
                                    await handleSubmit(new Event("submit") as any);
                                    setIsSubmitting(false);
                                    setShowConfirmModal(false);
                                }}
                            >
                                {isSubmitting ? "Przetwarzanie..." : "Zapłać 40 zł"}
                            </button>
                        </div>
                    </div>
                </div>
            )}

        </div>
    );
}
