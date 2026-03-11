package pracownik;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pracownik implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pesel;
    private String imie;
    private String nazwisko;
    private BigDecimal wynagrodzenie;
    private BigDecimal limit;        // Dyrektor: limit kosztów/mc; Handlowiec: limit prowizji/mc
    private String stanowisko;       // "Dyrektor" / "Handlowiec"

    public String getPesel() {
    	return pesel;
    	}
    public void setPesel(String pesel) {
    	this.pesel = pesel;
    }
    public String getImie() {
    	return imie;
    	}
    public void setImie(String imie) {
    	this.imie = imie;
    	}
    public String getNazwisko() {
    	return nazwisko;
    	}
    public void setNazwisko(String nazwisko) {
    	this.nazwisko = nazwisko;
    }
    public BigDecimal getWynagrodzenie() {
    	return wynagrodzenie;
    	}
    public void setWynagrodzenie(BigDecimal wynagrodzenie) {
    	this.wynagrodzenie = wynagrodzenie;
    	}

    public BigDecimal getLimit() {
    	return limit;
    	}
    public void setLimit(BigDecimal limit) {
    	this.limit = limit;
    	}

    public String getStanowisko() {
    	return stanowisko;
    	}
    public void setStanowisko(String stanowisko) {
    	this.stanowisko = stanowisko;
    	}
}
