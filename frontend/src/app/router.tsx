import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import HomePage from "../modules/home/pages/HomePage";
import LoginForm from "../modules/auth/components/LoginForm";
import RegisterForm from "../modules/auth/components/RegisterForm";
import Basket from "../modules/home/pages/Basket";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,      // layout z Navbar + Outlet
    children: [
      { index: true, element: <HomePage /> },
      { path: "login", element: <LoginForm /> },
      { path: "register", element: <RegisterForm /> },
      {path: "basket", element: <Basket />},
    ],
  },
]);

export default router; // ⬅️ UWAGA: eksportujesz OBIEKT, nie funkcję
