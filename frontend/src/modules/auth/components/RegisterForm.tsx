import './Auth.css';
import { useState } from 'react';

function RegisterForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError("");

        if (password !== password2) {
            setError("Niepoprawne powtórzenie hasła!");
            return;
        }
        if (password.length < 8) {
            setError("Za krótkie hasło!");
            return;
        }

        const registerData = {
            mail: email, 
            password: password
        };

        try {
            const response = await fetch('https://concerned-sprayless-brandie.ngrok-free.dev/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'ngrok-skip-browser-warning': 'true'
                },
                body: JSON.stringify(registerData),
            });

            if (response.ok) {
                alert("Konto założone pomyślnie! Teraz możesz się zalogować.");

            } else {
                const data = await response.json();
                setError(data.message || "Błąd podczas rejestracji.");
            }
        } catch (err) {
            setError("Błąd połączenia z serwerem.");
        }
    };

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Załóż konto</h2>
                {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}
                <div className="input-group">
                    <label htmlFor="email">Adres email</label>
                    <input type="email" id="email" placeholder="Wpisz email..." value={email} onChange={(e) => {
                        setEmail(e.target.value);
                    }}>
                    </input>
                </div>
                <div className="input-group">
                    <label htmlFor="password1">Hasło</label>
                    <input type="password" id="password1" placeholder="Minimum 8 znaków" value={password} onChange={(e) => {
                        setPassword(e.target.value);
                    }} />
                </div>
                <div className="input-group">
                    <label htmlFor="password2">Powtórz hasło</label>
                    <input type="password" id="password2" placeholder="Powtórz hasło..." value={password2} onChange={(e) => {
                        setPassword2(e.target.value);
                    }} />
                </div>
                <button type="submit" className="auth-button">Zarejestruj się</button>
            </form>
        </div>
    );
}

export default RegisterForm;