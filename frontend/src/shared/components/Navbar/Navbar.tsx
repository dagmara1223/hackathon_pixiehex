import { Link } from "react-router-dom";
import "./Navbar.css";

export default function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar__logo">
        <Link to="/">PIXIEHEX</Link>
      </div>

      <div className="navbar__actions">
        <Link to="/login" className="navbar__button">
          Logowanie
        </Link>
        <Link
          to="/register"
          className="navbar__button navbar__button--primary"
        >
          Rejestracja
        </Link>
      </div>
    </nav>
  );
}
