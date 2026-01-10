import Navbar from "./shared/components/Navbar/Navbar";
import Slider from "./modules/auth/components/slider/Slider";
import "./App.css";
import BrandStrip from "./modules/auth/components/brandstrip/BrandStrip";

function App() {
  return (
    <>
      <Navbar />
      <Slider />
      <BrandStrip />
    </>
  );
}

export default App;
