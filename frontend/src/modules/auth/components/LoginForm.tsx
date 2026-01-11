import './Auth.css';
import { useState } from 'react';
import { useNavigate } from "react-router-dom";

export default function LoginForm() {
    const [mail, setMail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError("");

        try {
            const response = await fetch('https://concerned-sprayless-brandie.ngrok-free.dev/auth/login', {
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
                localStorage.setItem("isLoggedIn", "true");
                localStorage.setItem("userMail", mail);



                // fetch('https://.../client', { headers: { 'ngrok-skip-browser-warning': 'true' } })
                //             .then(res => res.json())
                //             .then(data => {
                //                 const user = data.find((c: any) => c.mail === mail);
                //                 localStorage.setItem("role", user.role);
                //             });

                navigate("/");
                return;
            }

            else {
                const errorData = await response.json();
                setError(errorData.message || "Błędny login lub hasło");
            }
        } catch (err) {
            setError("Błedny login lub hasło.");
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