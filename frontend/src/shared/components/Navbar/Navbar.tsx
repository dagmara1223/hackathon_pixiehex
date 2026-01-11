import { Link, useNavigate } from "react-router-dom";
import "./Navbar.css";

export default function Navbar() {
  const navigate = useNavigate();
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
  const userMail = localStorage.getItem("userMail");

  const handleLogout = () => {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userMail");
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="navbar__logo">
        <Link to="/">PIXIEHEX</Link>
      </div>

      <div className="navbar__actions">
        {isLoggedIn ? (
          <>
            {userMail && (
              <span className="navbar__user">
                {userMail}
              </span>
            )}
            <Link to="/orderhistory" className="navbar__button">
            Historia zamówień
            </Link>
            <Link to="/basket" className="navbar__button">
            Koszyk
            </Link>
            <button
              onClick={handleLogout}
              className="navbar__button navbar__button--logout"
            >
              Wyloguj
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="navbar__button">
              Logowanie
            </Link>
            <Link
              to="/register"
              className="navbar__button navbar__button--primary"
            >
              Rejestracja
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}
