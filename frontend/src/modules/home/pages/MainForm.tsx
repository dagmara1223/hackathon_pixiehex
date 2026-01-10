import { useState } from "react";
import "./mainform.css";
const brands = ['b1', 'b2', 'b3', 'b4', 'b5'];
const products = ['p1', 'p2', 'p3', 'p4']
export default function MainForm() {
    const [chosenBrand, setChosenBrand] = useState("");
    const [chosenProduct, setChosenProduct] = useState("");
    const [userData, setUserData] = useState({ name: "", surname: "", mail: "" });
    const [errors, setErrors] = useState({});
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUserData({ ...userData, [e.target.name]: e.target.value });
    };

    let selectBrands = <select id="brands" name="brands" onChange={(e) => {
        setChosenBrand(e.target.value);
    }}>
        <option value="">--Wybierz markę--</option>
        {brands.map(b => (
            <option key={b} value={b}>{b}</option>
        ))}</select>
    let selectProduct;
    if (chosenBrand) {
        selectProduct = <div className="selection-section">
            <label>Produkt:</label><select id="products" name="products" onChange={(e) => {
                setChosenProduct(e.target.value);
            }}>
                <option value="">--Wybierz product--</option>
                {products.map(b => (
                    <option key={b} value={b}>{b}</option>
                ))}</select></div>
    }
    else {
        selectProduct = null;
    }
    let form;
    if (chosenProduct && chosenBrand) {
        form =
            <div className="user-details-form">
                <hr />
                <form>
                    <label htmlFor="name">Imię</label>
                    <input type="text" id="name" name="name" placeholder="Wpisz swoje imie..." />
                    <label htmlFor="surname">Nazwisko</label>
                    <input type="text" id="surname" name="surname" placeholder="Wpisz swoje nazwisko..." />
                    <label htmlFor="mail">E-Mail</label>
                    <input type="email" id="mail" name="mail" placeholder="Wpisz swój mail..." />
                </form>
                <button type="submit" className="submit-btn">Potwierdź zamówienie</button>
            </div>
    }
    else {
        form = null;
    }

    return <div className="mainform-container">
        <div className="order-card">
            <h2>Zbierz grupę i zamów już teraz!</h2>

            <div className="selection-section">
                <label>Marka:</label>
                {selectBrands}
            </div>

            {selectProduct}

            {form}
        </div>
    </div>
}