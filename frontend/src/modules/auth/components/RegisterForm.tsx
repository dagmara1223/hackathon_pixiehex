import './Auth.css';
function RegisterForm() {
    return (
        <div className="auth-container">
            <form className="auth-form">
                <h2>Załóż konto</h2>
                <div className="input-group">
                    <label htmlFor="name">Nazwa użytkownika</label>
                    <input type="text" id="name" name="name" placeholder="Wpisz nazwę..." />
                </div>
                <div className="input-group">
                    <label htmlFor="password1">Hasło</label>
                    <input type="password" id="password1" placeholder="Minimum 8 znaków" />
                </div>
                <div className="input-group">
                    <label htmlFor="password2">Powtórz hasło</label>
                    <input type="password" id="password2" placeholder="Powtórz hasło..." />
                </div>
                <button type="submit" className="auth-button">Zarejestruj się</button>
            </form>
        </div>
    );
}

export default RegisterForm;