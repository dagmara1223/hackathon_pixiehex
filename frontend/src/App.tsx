import Navbar from "./shared/components/Navbar/Navbar";
import "./App.css";
import { Outlet } from "react-router-dom";

export default function App() {
  return (
    <>
      <Navbar />
      <main>
        <Outlet /> 
      </main>
    </>
  );
}
