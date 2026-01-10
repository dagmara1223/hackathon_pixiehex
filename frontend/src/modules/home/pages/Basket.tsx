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
        setProducts(getCart()); //odswiezanie ui
    };

    const total = products.reduce((sum, p) => sum + p.price, 0);

    if (products.length === 0) {
        return (
            <div>
                <h1>Twój koszyk</h1>
                <h3>🛒 Koszyk jest pusty</h3>
            </div>
        );
    }

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

                                    setTimeout(() => {
                                        setDepositPaid(true);

                                        clearCart();
                                        setProducts([]);

                                        setShowDepositModal(false);
                                        setIsPaying(false);

                                        alert("✅ Zaliczka opłacona. Rezerwacja zapisana!");
                                    }, 1200);
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
