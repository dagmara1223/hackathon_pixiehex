import './Auth.css';
export default function LoginForm() {
    return (
        <div className="auth-container">
            <form className="auth-form">
                <h2>Zaloguj się</h2>
                <div className="input-group">
                    <label htmlFor="name">Nazwa użytkownika</label>
                    <input type="text" id="name" name="name" placeholder="Wpisz nazwę..." />
                </div>
                <div className="input-group">
                    <label htmlFor="password">Hasło</label>
                    <input type="password" id="password" placeholder="Wpisz hasło..." />
                </div>
                <button type="submit" className="auth-button">Zaloguj</button>
            </form>
        </div>
    );
}