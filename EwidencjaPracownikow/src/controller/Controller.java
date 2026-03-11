
package controller;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipException;

import dyrektor.Dyrektor;
import handlowiec.Handlowiec;
import model.BackupRequest;
import model.Model;
import pracownik.Pracownik;
import view.View;

public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        while (true) {
            try {
                switch (view.menu()) {
                    case 1: 
                    	listEmployees();
                    	break;
                    case 2:
                    	addEmployee();
                    	break;
                    case 3: 
                    	deleteEmployee();
                    	break;
                    case 4:
                    	backup();
                    	break;
                    default:
                    	view.showError("Nieznana opcja menu.");
                    	break;
                }
            } catch (IllegalStateException ex) {                 // porzucenia (Q) oraz „miękkie” stany
                view.showMessage(ex.getMessage());
            } catch (IllegalArgumentException ex) {              // walidacje/argumenty
                view.showError(ex.getMessage());
            } catch (NoSuchElementException ex) {                // brak pracownika itp.
                view.showError(ex.getMessage());
            } catch (FileNotFoundException ex) {                 // kopia zapasowa – plik/brak uprawnień
                view.showError("Plik nie istnieje lub brak uprawnień: " + ex.getMessage());
            } catch (ZipException ex) {
                view.showError("Błędny plik ZIP: " + ex.getMessage());
            } catch (StreamCorruptedException ex) {
                view.showError("Uszkodzony strumień (nieprawidłowy format).");
            } catch (EOFException ex) {
                view.showError("Plik archiwum jest pusty lub uszkodzony.");
            } catch (IOException ex) {
                view.showError("Błąd I/O: " + ex.getMessage());
            } catch (Exception ex) {
                view.showError("Nieoczekiwany błąd: " + ex.getMessage());
            }
        }
    }

    private void listEmployees() {
        List<Pracownik> list = model.allEmployees();
        view.showEmployees(list);
    }

    private void addEmployee() {
        View.Data d = view.readInputData();
        if (model.containsPesel(d.pesel)) {
            throw new IllegalArgumentException("PESEL " + d.pesel + " już istnieje.");
        }
        if (d.wynagrodzenie == null || d.wynagrodzenie.signum() < 0) {
            throw new IllegalArgumentException("Wynagrodzenie musi być nieujemne.");
        }

        Pracownik p;
        if (d instanceof View.DataDyrektor dd) {
            Dyrektor x = new Dyrektor();
            x.setStanowisko("Dyrektor");
            x.setPesel(dd.pesel);
            x.setImie(dd.imie);
            x.setNazwisko(dd.nazwisko);
            x.setWynagrodzenie(dd.wynagrodzenie);
            x.setTelefon(dd.telefon);
            x.setDodatekSluzbowy(dd.dodatekSluzbowy);
            x.setKarta(dd.karta);
            x.setLimit(dd.limit); // limit kosztów/miesiąc
            p = x;
        } else if (d instanceof View.DataHandlowiec dh) {
            Handlowiec x = new Handlowiec();
            x.setStanowisko("Handlowiec");
            x.setPesel(dh.pesel);
            x.setImie(dh.imie);
            x.setNazwisko(dh.nazwisko);
            x.setWynagrodzenie(dh.wynagrodzenie);
            x.setStawkaProwizji(dh.stawkaProwizji);
            x.setLimit(dh.limit); // limit prowizji/miesiąc
            p = x;
        } else {
            throw new IllegalStateException("Nieznany typ pracownika.");
        }

        model.addEmployee(p);
        view.showMessage("Dodano pracownika: " + p.getImie() + " " + p.getNazwisko());
    }

    private void deleteEmployee() {
        String pesel = view.askPeselToDelete();
        Pracownik p = model.getByPesel(pesel);
        if (p == null) throw new NoSuchElementException("Nie znaleziono pracownika o PESEL " + pesel + ".");
        if (!view.confirmDeletion(p)) throw new IllegalStateException("Porzucono usuwanie.");
        model.removeByPesel(pesel);
        view.showMessage("Usunięto pracownika.");
    }

    private void backup() throws IOException, ClassNotFoundException {
        BackupRequest req = view.backupScreen();
        switch (req.mode) {
            case SAVE -> {
                model.save(req.fileName, req.compression);
                view.showMessage("Zapisano kopię: " + req.fileName);
            }
            case RESTORE -> {
                model.restore(req.fileName);
                view.showMessage("Odtworzono z kopii: " + req.fileName);
            }
            default -> throw new IllegalArgumentException("Nieznany tryb kopii.");
        }
    }
}
