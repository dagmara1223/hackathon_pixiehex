import "./AboutSection.css";
import boxImg from "./box.png";
import peopleImg from "./people.png"

export default function AboutSection() {
    return (
        <section className="about">
            <div className="about__content">
                <h2 className="about__title">W paczce siła!</h2>

                <p>
                    PIXIEHEX to platforma do wspólnych zamówień kosmetyków i produktów
                    prosto z Korei Południowej. Łączymy osoby zainteresowane tymi samymi
                    produktami, aby obniżyć koszty importu i uprościć cały proces.
                </p>

                <p>
                    Zamiast kilku osobnych paczek — jedna zbiorcza wysyłka, mniej kosztów i
                    zero stresu z odprawą.
                </p>

                {/*co nas wyroznia section */}
                <div className="about__features">
                    <h2 className="about__title">Co nas wyróżnia?</h2>

                    <div className="about__cards">
                        <div className="about__card">
                            <img src={peopleImg} alt="Pojedyncze zamówienie" />
                            <h3>Przyjazna społeczność, szybko i drama - free! </h3>
                            <p>
                                Tworzymy miejsca, w których ludzie szybko się dogadują, wspólnie
                                wybierają produkty i dzielą doświadczenia. Bez chaosu, bez
                                przypadkowych ofert – tylko sprawdzone zamówienia.
                            </p>
                        </div>

                        <div className="about__card about__card--highlight">
                            <img src={boxImg} alt="Wspólne zamówienie PIXIEHEX" />
                            <h3>Wspólne zamówienia PIXIEHEX</h3>
                            <p>
                                Zamiast wielu osobnych paczek – jedna zbiorcza przesyłka.
                                Mniej kosztów, mniej formalności i pełna kontrola nad procesem
                                importu z Korei Południowej.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}
