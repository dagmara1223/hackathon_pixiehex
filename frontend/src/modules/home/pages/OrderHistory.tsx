import { useEffect, useState } from "react";

type Order = {
    productName: string,
    userEmail: string,
    shippingAddress: string,
    originalPrice: number,
    depositAmount: number,
    status: string,
    finalPrice: number,
    groupOrder: {
      name: string,
      createdDate: string,
      id: number,
      status: string,
      totalValue: number,
      totalWeight: number
    },
    orderDate: string,
    orderId: number,
    productWeight: number,
    remainingToPay: number
}

export default function OrderHistory(){
    const [orders, setOrders] = useState<Order[]>([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const userMail = localStorage.getItem("userMail");

    
    
    // `https://unexchangeable-julio-acaroid.ngrok-free.dev/single_orders/by-email?mail=${userMail}`
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`https://unexchangeable-julio-acaroid.ngrok-free.dev/single_orders/by-email?mail=${userMail}`, {
                    method: 'GET', // GET jest domyślny, ale warto go wpisać
                    headers: {
                        'Accept': 'application/json',
                        'ngrok-skip-browser-warning': 'true' // OBOWIĄZKOWE dla darmowego ngrok
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
            
    if(loading) return <p>Ładowanie</p>;
    if(error) return <p style={{ color: 'red' }}>{error}</p>;   
    let ordersDiv;
    if(orders.length){
            ordersDiv = orders.map(o => {
            return <div className="order">
                Nazwa produktu: {o.productName}
                Oryginalna cena: {o.originalPrice}
                Status: {o.status}
                Cena Finalna: {o.finalPrice}
                Waga: {o.productWeight}
                Pozostało do zapłaty: {o.remainingToPay}
            </div>;
        });
    }
    else{
        ordersDiv = <h3>Nie masz u nas żadnych zamówień</h3>;
    }    
    return <div>
        <h2>Historia Zamówień</h2>
        {ordersDiv}
    </div>;
}