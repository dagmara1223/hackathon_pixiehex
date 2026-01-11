import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import HomePage from "../modules/home/pages/HomePage";
import LoginForm from "../modules/auth/components/LoginForm";
import RegisterForm from "../modules/auth/components/RegisterForm";
import Basket from "../modules/home/pages/Basket";
import OrderHistory from "../modules/home/pages/OrderHistory";
import AdminPage from "../modules/home/pages/AdminPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />, 
    children: [
      { index: true, element: <HomePage /> },
      { path: "login", element: <LoginForm /> },
      { path: "register", element: <RegisterForm /> },
      { path: "basket", element: <Basket /> },
      { path: "orderhistory", element: <OrderHistory /> },
      {path: "admin", element: <AdminPage />},
    ],
  },
]);

export default router; 
