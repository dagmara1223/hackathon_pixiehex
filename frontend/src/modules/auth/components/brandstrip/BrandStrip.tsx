import "./BrandStrip.css"
import boj from "./boj2.png"
import cosrx from "./cosrx.png"
import dralthea from "./dralthea.png"
import imfrom from "./imfrom.png"
import round_lab from "./round_lab.png"

const brands = [
    { name: "Beauty of Joseon", logo: boj },
    { name: "Cosrx", logo: cosrx },
    { name: "Dr Althea", logo: dralthea },
    { name: "I'm from", logo: imfrom },
    { name: "Round Lab", logo: round_lab }
];

export default function BrandStrip() {
    return (
        <section className="brand-strip">
            <div className="brand-strip__logos">
                {brands.map((brand) => (
                    <img
                        key={brand.name}
                        src={brand.logo}
                        alt={brand.name}
                        className="brand-strip__logo"
                    />
                ))}
            </div>
        </section>
    )
}