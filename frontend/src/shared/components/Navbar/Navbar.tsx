import "./Navbar.css";

export default function Navbar() {
    return (
        <nav className="navbar">
            <div className="navbar__logo">
                PIXIEHEX
            </div>

            <div className="navbar__actions">
                <button className="navbar__button">Logowanie</button>
                <button className="navbar__button navbar__button--primary">
                    Rejestracja
                </button>
            </div>
        </nav>
    );
}
