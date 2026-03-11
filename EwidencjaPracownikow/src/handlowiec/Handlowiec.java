package handlowiec;

import java.math.BigDecimal;
import pracownik.Pracownik;

public class Handlowiec extends Pracownik {
    private static final long serialVersionUID = 1L;

    private BigDecimal stawkaProwizji;

    public BigDecimal getStawkaProwizji() { return stawkaProwizji; }
    public void setStawkaProwizji(BigDecimal stawkaProcentowa) { this.stawkaProwizji = stawkaProcentowa; }
}
