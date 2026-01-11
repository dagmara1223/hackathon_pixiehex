import type { Order } from "./OrderHistory";
import { useState, useEffect } from "react";
import "./orderhistory.css"
export default function AdminPage(){
    const [orders, setOrders] = useState<Order[]>([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    if(localStorage.getItem("role") !== "ADMIN"){
        return <></>;
    }
        useEffect(() => {
            const fetchData = async () => {
                try {
                    const response = await fetch(`https://concerned-sprayless-brandie.ngrok-free.dev/single_orders`, {
                        method: 'GET', 
                        headers: {
                            'Accept': 'application/json',
                            'ngrok-skip-browser-warning': 'true'
                        }
                    });
    
                    if (!response.ok) {
                        throw new Error(`Błąd: ${response.status}`);
                    }
    
                    const result = await response.json();
                    setOrders(result);
                } catch (err) {
                    setError("Nie udało się pobrać danych.");
                    console.error(err);
                } finally {
                    setLoading(false);
                }
            };
    
            fetchData();
        }, []);
    if (loading) return <p>Ładowanie</p>;
    if (error) return <p style={{ color: 'red' }}>{error}</p>;

    let ordersDiv;
    if (orders.length) {
        ordersDiv = orders.map(o => (
            <div className="order-card">
                <div className="order-header">
                    <span className="product-name">{o.productName}</span>
                    <span className={`status-badge ${o.status.toLowerCase()}`}>{o.status}</span>
                </div>
                <div className="order-details">
                    <div className="detail-item">
                        <span className="label">Oryginalna cena:</span>
                        <span className="value">{o.originalPrice} zł</span>
                    </div>
                    <div className="detail-item">
                        <span className="label">Waga:</span>
                        <span className="value">{o.productWeight} kg</span>
                    </div>
                    <div className="detail-item final-price">
                        <span className="label">Cena Finalna:</span>
                        <span className="value">{o.finalPrice} zł</span>
                    </div>
                    <div className="detail-item remaining">
                        <span className="label">Do zapłaty:</span>
                        <span className="value">{o.remainingToPay} zł</span>
                    </div>
                </div>
            </div>
        ));}
    else{
        ordersDiv = <h3>Nie ma żadnych zamówień</h3>;
    }
    return <div>
        <div className="order-container">
        <h2>Historia Zamówień</h2>
        {ordersDiv}
    </div>
    </div>
}