import { useState } from "react";


export default function Basket(){
    const [products, setProducts] = useState([]);

    let productsDiv = <h3>Twój koszyk jest pusty!</h3>;
    if(products.length){
        productsDiv = <h3>Twój koszyk nie jest pusty :DDDD</h3>
    }
    return <div>
        <h1>Twój koszyk: </h1>
        {productsDiv}
    </div>;
}