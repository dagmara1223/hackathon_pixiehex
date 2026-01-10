import "./ProductGrid.css";
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

export function ProductGrid({ products, selectedIds, onSelect }: ProductGridProps) {
    return (
        <div className="product-grid">
            {products.map((product) => (
                <div 
                    key={product.id} 
                    className={`product-card ${selectedIds.includes(product.id) ? 'active' : ''}`}
                    onClick={() => onSelect(product.id)}
                >
                    <h4>{product.name}</h4>
                    {product.price && <span>{product.price} zł</span>}
                </div>
            ))}
        </div>
    );
}