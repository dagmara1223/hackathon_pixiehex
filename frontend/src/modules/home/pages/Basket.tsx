import { useEffect, useState } from "react";
import { getCart, removeFromCart } from "../../../utils/cart";
import type { CartProduct } from "../../../utils/cart";

export default function Basket() {
    const [products, setProducts] = useState<CartProduct[]>([]);

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
        </div>
    );
}
