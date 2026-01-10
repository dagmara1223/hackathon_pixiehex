// modules/home/pages/HomePage.tsx
import Slider from "../../auth/components/slider/Slider";
import BrandStrip from "../../auth/components/brandstrip/BrandStrip";
import AboutSection from "./sections/AboutSection";
export default function HomePage() {
  return (
    <>
      <Slider />
      <BrandStrip></BrandStrip>
      <AboutSection />
    </>
  );
}