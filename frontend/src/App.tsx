import Navbar from "./shared/components/Navbar/Navbar";
import "./App.css";

function App() {
  return (
    <>
      <Navbar />

      <main style={{ padding: "2rem" }}>
        <h1>Strona główna</h1>
        <p>
          Platforma do wspólnych zamówień importowych z Korei Południowej.
        </p>
      </main>
    </>
  );
}

export default App;
