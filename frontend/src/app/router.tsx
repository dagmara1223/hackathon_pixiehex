import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import HomePage from "../modules/home/pages/HomePage";
import LoginForm from "../modules/auth/components/LoginForm";
import RegisterForm from "../modules/auth/components/RegisterForm";
import Basket from "../modules/home/pages/Basket";
import OrderHistory from "../modules/home/pages/OrderHistory";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />, 
    children: [
      { index: true, element: <HomePage /> },
      { path: "login", element: <LoginForm /> },
      { path: "register", element: <RegisterForm /> },
      { path: "basket", element: <Basket /> },
      { path: "orderhistory", element: <OrderHistory /> }
    ],
  },
]);

export default router; 
