import { useEffect, useState } from "react";
import "./mainform.css";
import { ProductGrid } from "./ProductGrid";

type Product = {
    id: number;
    name: string;
    price: number;
    region: string;
    weight: number;
};

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
    const [userData, setUserData] = useState({
        name: "",
        surname: "",
        mail: ""
    });

    // 🔄 FETCH PRODUKTÓW
    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await fetch(
                    "https://unexchangeable-julio-acaroid.ngrok-free.dev/products",
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

    // 🏷 MARKI
    const brands = Array.from(
        new Set(products.map(p => extractBrand(p.name)))
    );

    // 📦 PRODUKTY DANEJ MARKI
    const filteredProducts = products.filter(
        p => extractBrand(p.name) === chosenBrand
    );

    // 🚀 SUBMIT – JEDEN POST NA KAŻDY PRODUKT
    const handleSubmit = async () => {
        try {
            if (chosenProducts.length === 0) {
                alert("Wybierz przynajmniej jeden produkt");
                return;
            }

            for (const productId of chosenProducts) {
                const product = products.find(p => p.id === productId);
                if (!product) continue;

                const response = await fetch(
                    "https://unexchangeable-julio-acaroid.ngrok-free.dev/single_orders",
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
            setUserData({ name: "", surname: "", mail: "" });

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

                {/* FORMULARZ */}
                {chosenProducts.length > 0 && (
                    <div className="user-details-form">
                        <hr />
                        <form
                            onSubmit={(e) => {
                                e.preventDefault();
                                handleSubmit();
                            }}
                        >
                            <label>Imię</label>
                            <input
                                value={userData.name}
                                onChange={(e) =>
                                    setUserData({ ...userData, name: e.target.value })
                                }
                            />

                            <label>Nazwisko</label>
                            <input
                                value={userData.surname}
                                onChange={(e) =>
                                    setUserData({ ...userData, surname: e.target.value })
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

                            <button type="submit" className="submit-btn">
                                Potwierdź zamówienie
                            </button>
                        </form>
                    </div>
                )}
            </div>
        </div>
    );
}
