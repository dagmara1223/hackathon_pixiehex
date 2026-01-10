import { useEffect, useState } from "react";
import "./Slider.css";

import cosmetics1 from "./cosmetics1.jpg";
import cosmetics2 from "./cosmetics2.jpg";
import cosmetics3 from "./cosmetics3.jpg";
import cosmetics4 from "./cosmetics4.jpg";

const images = [cosmetics1, cosmetics2, cosmetics3, cosmetics4];

export default function Slider() {
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            goNext();
        }, 4000);

        return () => clearInterval(interval);
    }, [currentIndex]);

    const goNext = () => {
        setCurrentIndex((prev) => (prev + 1) % images.length);
    };

    const goPrev = () => {
        setCurrentIndex((prev) =>
            prev === 0 ? images.length - 1 : prev - 1
        );
    };

    return (
        <div className="slider">
            <button className="slider__arrow slider__arrow--left" onClick={goPrev}>
                ‹
            </button>

            <div
                className="slider__track"
                style={{ transform: `translateX(-${currentIndex * 100}%)` }}
            >
                {images.map((img, index) => (
                    <div className="slider__slide" key={index}>
                        <img src={img} alt={`slide-${index}`} />
                    </div>
                ))}
            </div>

            <button className="slider__arrow slider__arrow--right" onClick={goNext}>
                ›
            </button>
        </div>
    );
}
