package dyrektor;

import java.math.BigDecimal;
import pracownik.Pracownik;

public class Dyrektor extends Pracownik {
    private static final long serialVersionUID = 1L;

    private BigDecimal dodatekSluzbowy;
    private String telefon;
    private String karta;

    public BigDecimal getDodatekSluzbowy() { return dodatekSluzbowy; }
    public void setDodatekSluzbowy(BigDecimal kwota) { this.dodatekSluzbowy = kwota; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getKarta() { return karta; }
    public void setKarta(String karta) { this.karta = karta; }
}
