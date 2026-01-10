import "./ProductGrid.css";
import { addToCart } from "../../../utils/cart";

type Product = {
    id: number;
    name: string;
    price: number;
    region: string;
    weight: number;
};

interface ProductGridProps {
    products: Product[];
    selectedIds: number[];
    onSelect: (id: number) => void;
}

import { useState } from "react";
import "./ProductGrid.css";

export function ProductGrid({ products, selectedIds, onSelect }: ProductGridProps) {
    const [addedId, setAddedId] = useState<number | null>(null);

    return (
        <div className="product-grid">
            {products.map((product) => (
                <div
                    key={product.id}
                    className={`product-card ${selectedIds.includes(product.id) ? "active" : ""}`}
                >
                    <h4>{product.name}</h4>
                    <span>{product.price} zł</span>

                    <div className="product-actions">
                        <button onClick={() => onSelect(product.id)}>
                            {selectedIds.includes(product.id)
                                ? "✓ Wybrane"
                                : "Wybierz"}
                        </button>

                        <button
                            onClick={() => {
                                addToCart({
                                    id: product.id,
                                    name: product.name,
                                    price: product.price
                                });
                                setAddedId(product.id);
                                setTimeout(() => setAddedId(null), 1500);
                            }}
                        >
                            🛒 Dodaj do koszyka
                        </button>
                    </div>

                    {addedId === product.id && (
                        <div className="cart-confirmation">
                            ✅ Dodano do koszyka
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
}
