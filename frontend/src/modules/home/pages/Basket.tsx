import { useEffect, useState } from "react";
import { getCart, removeFromCart, clearCart } from "../../../utils/cart";
import type { CartProduct } from "../../../utils/cart";

export default function Basket() {
    const [products, setProducts] = useState<CartProduct[]>([]);
    const [showDepositModal, setShowDepositModal] = useState(false);
    const [isPaying, setIsPaying] = useState(false);
    const [depositPaid, setDepositPaid] = useState(false);


    useEffect(() => {
        setProducts(getCart());
    }, []);

    const handleRemove = (index: number) => {
        removeFromCart(index);
        setProducts(getCart()); 
    };

    const total = products.reduce((sum, p) => sum + p.price, 0);

    if (products.length === 0) {
        return (
            <div>
                <h1>Twój koszyk</h1>
            </div>
        );
    }
    const sendBulkOrder = async () => {
        const payload = {
            userEmail: "test@mail.com", 
            products: products.map(p => ({
                productId: p.id,
                name: p.name,
                price: p.price
            }))
        };

        const response = await fetch(
            "http://localhost:8080/bulk",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "ngrok-skip-browser-warning": "true"
                },
                body: JSON.stringify(payload)
            }
        );

        if (!response.ok) {
            const text = await response.text();
            console.error("BACKEND RESPONSE:", text);
            throw new Error(text);
        }

    };


    return (
        <div>
            <h1>Twój koszyk</h1>

            <ul>
                {products.map((product, index) => (
                    <li key={index}>
                        {product.name} — {product.price} zł
                        <button
                            style={{ marginLeft: "10px" }}
                            onClick={() => handleRemove(index)}
                        >
                            ❌ Usuń
                        </button>
                    </li>
                ))}
            </ul>

            <h3>Suma: {total.toFixed(2)} zł</h3>
            <button
                className="submit-btn"
                onClick={() => setShowDepositModal(true)}
            >
                Zapłać zaliczkę 40 zł
            </button>
            {showDepositModal && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h3>Rezerwacja miejsca w grupie</h3>

                        <p>
                            Aby potwierdzić udział w zamówieniu grupowym,
                            wymagana jest <strong>zaliczka 40 zł</strong>.
                        </p>

                        <p>
                            Finalna cena (z uwzględnieniem liczby osób w grupie,
                            VAT 23% oraz kosztów wysyłki) zostanie wysłana
                            na Twój email po skompletowaniu grupy.
                        </p>

                        <p className="modal-small">
                            Zaliczka zostanie odliczona od ceny końcowej.
                        </p>

                        <div className="modal-actions">
                            <button
                                className="modal-cancel"
                                onClick={() => setShowDepositModal(false)}
                            >
                                Anuluj
                            </button>

                            <button
                                className="modal-confirm"
                                disabled={isPaying}
                                onClick={async () => {
                                    setIsPaying(true);

                                    try {
                                        await sendBulkOrder();

                                        setDepositPaid(true);
                                        clearCart();
                                        setProducts([]);

                                        alert(" Zaliczka opłacona. Zamówienie zapisane!");
                                        setShowDepositModal(false);
                                    } catch (e) {
                                        alert(" Błąd zapisu zamówienia. Spróbuj ponownie.");
                                        console.error(e);
                                    } finally {
                                        setIsPaying(false);
                                    }
                                }}

                            >
                                {isPaying ? "Przetwarzanie..." : "Zapłać 40 zł"}
                            </button>

                        </div>
                    </div>
                </div>
            )}

        </div>

    );
}
