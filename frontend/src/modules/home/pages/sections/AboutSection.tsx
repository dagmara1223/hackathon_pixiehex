import "./AboutSection.css";
import boxImg from "./box.png";
import peopleImg from "./people.png"

export default function AboutSection() {
    return (
        <section className="about">
            <div className="about__content">
                <div className="about__box">
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
                <p>
                    W PIXIEHEX cena <strong>nie jest stała</strong>. Koszt końcowy
                    <strong> dynamicznie się zmienia</strong> w zależności od liczby osób
                    zapisanych do wspólnego zamówienia.
                </p>

                <p>
                    Im większa grupa, tym niższa opłata za import z Korei Południowej.
                    Zbieramy zgłoszenia poprzez formularze, łączymy użytkowników w grupy
                    zakupowe i realizujemy jedno zbiorcze zamówienie.
                </p>

                <p>
                    Cena końcowa zawsze uwzględnia: <strong>VAT 23%</strong>,{" "}
                    <strong>opłatę platformy (10%)</strong> oraz{" "}
                    <strong>przesyłkę na terenie Polski</strong>.
                </p>
                </div>
                <div className="about__pricing">
                    <h2 className="about__title">Jak zmienia się cena?</h2>

                    <p className="about__pricing-desc">
                        Koszt wysyłki z Korei Południowej maleje wraz ze wzrostem liczby osób
                        w grupie. To realna oszczędność dla każdego uczestnika.
                    </p>

                    <table className="pricing-table">
                        <thead>
                            <tr>
                                <th>Liczba osób w grupie</th>
                                <th>Koszt wysyłki z Korei (na osobę)</th>
                                <th>Twoja oszczędność</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>5 osób</td>
                                <td>72 PLN</td>
                                <td>75%</td>
                            </tr>
                            <tr>
                                <td>10 osób</td>
                                <td>44 PLN</td>
                                <td>85 %</td>
                            </tr>
                            <tr className="highlight-row">
                                <td>20 osób</td>
                                <td>33 PLN</td>
                                <td>90 %</td>
                            </tr>
                        </tbody>
                    </table>
                </div>


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
