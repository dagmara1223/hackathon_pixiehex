import Slider from "../../auth/components/slider/Slider";
import BrandStrip from "../../auth/components/brandstrip/BrandStrip";
import AboutSection from "./sections/AboutSection";
import MainForm from "./MainForm";
export default function HomePage() {
  return (
    <>
      <Slider />
      <BrandStrip></BrandStrip>
      <AboutSection />
      <MainForm/>
    </>
  );
}