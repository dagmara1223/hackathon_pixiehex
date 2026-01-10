import './Auth.css';
import { useState } from 'react';

export default function LoginForm() {
    const [mail, setMail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError("");

        try {
            const response = await fetch('https://unexchangeable-julio-acaroid.ngrok-free.dev/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'ngrok-skip-browser-warning': 'true'
                },
                credentials: "include",
                body: JSON.stringify({
                    mail: mail,
                    password: password,
                })
            });

            if (response.ok) {
                const text = await response.text();
                console.log("LOGIN RESPONSE:", text);
                alert("Zalogowano!");
                return;
            }
            else {
                const errorData = await response.json();
                setError(errorData.message || "Błędny login lub hasło");
            }
        } catch (err) {
            setError("Problem z połączeniem z serwerem.");
        }
    };

    return (
        <div className="auth-container">
            {/* Dodajemy onSubmit do form */}
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Zaloguj się</h2>

                {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}

                <div className="input-group">
                    <label htmlFor="name">Mail użytkownika: </label>
                    <input
                        type="email"
                        id="name"
                        value={mail} // Dodajemy sterowanie wartością
                        onChange={(e) => setMail(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="password">Hasło</label>
                    <input
                        type="password"
                        id="password"
                        value={password} // Dodajemy sterowanie wartością
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="auth-button">Zaloguj</button>
            </form>
        </div>
    );
}