import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import LoginPage from "../modules/auth/pages/LoginPage";
import RegisterPage from "../modules/auth/pages/RegisterPage";
import HomePage from "../modules/home/pages/HomePage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />, 
    children: [
      { index: true, element: <HomePage /> }, // Slider będzie TYLKO tutaj
      { path: "login", element: <LoginPage /> },
      { path: "register", element: <RegisterPage /> },
    ],
  },
]);