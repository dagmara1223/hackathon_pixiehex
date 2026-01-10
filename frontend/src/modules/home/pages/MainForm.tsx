import { useEffect, useState } from "react";
import "./mainform.css";

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
    const [chosenProduct, setChosenProduct] = useState<number | "">("");
    const [userData, setUserData] = useState({
        name: "",
        surname: "",
        mail: ""
    });

    // fetch 
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

    // marki produkty
    const brands = Array.from(
        new Set(products.map(p => extractBrand(p.name)))
    );


    // produkty marki danej
    const filteredProducts = products.filter(
        p => extractBrand(p.name) === chosenBrand
    );


    return (
        <div className="mainform-container">
            <div className="order-card">
                <h2>Zbierz grupę i zamów już teraz!</h2>

                {/* marka */}
                <div className="selection-section">
                    <label>Marka:</label>
                    <select
                        value={chosenBrand}
                        onChange={(e) => {
                            setChosenBrand(e.target.value);
                            setChosenProduct("");
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

                {/*produkt*/}
                {chosenBrand && (
                    <div className="selection-section">
                        <label>Produkt:</label>
                        <select
                            value={chosenProduct}
                            onChange={(e) =>
                                setChosenProduct(Number(e.target.value))
                            }
                        >
                            <option value="">--Wybierz produkt--</option>
                            {filteredProducts.map((product) => (
                                <option key={product.id} value={product.id}>
                                    {product.name}
                                </option>
                            ))}
                        </select>
                    </div>
                )}

                {/*form*/}
                {chosenProduct && (
                    <div className="user-details-form">
                        <hr />
                        <form
                            onSubmit={(e) => {
                                e.preventDefault();
                                console.log({
                                    productId: chosenProduct,
                                    userData
                                });
                            }}
                        >
                            <label>Imię</label>
                            <input
                                name="name"
                                value={userData.name}
                                onChange={(e) =>
                                    setUserData({ ...userData, name: e.target.value })
                                }
                            />

                            <label>Nazwisko</label>
                            <input
                                name="surname"
                                value={userData.surname}
                                onChange={(e) =>
                                    setUserData({ ...userData, surname: e.target.value })
                                }
                            />

                            <label>Email</label>
                            <input
                                type="email"
                                name="mail"
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
