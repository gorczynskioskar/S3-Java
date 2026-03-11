package view;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import dyrektor.Dyrektor;
import handlowiec.Handlowiec;
import model.BackupCompression;
import model.BackupRequest;
import pracownik.Pracownik;

public class View {
    private final Scanner scan = new Scanner(System.in);

    public int menu() {
        while (true) {
            try {
                System.out.println("MENU");
                System.out.println("1. Lista pracowników");
                System.out.println("2. Dodaj pracownika");
                System.out.println("3. Usuń pracownika");
                System.out.println("4. Kopia zapasowa");
                System.out.print("Wybór> ");
                String line = scan.nextLine().trim();
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= 4) return choice;
            } catch (Exception ignored) {}
            System.out.println("Niepoprawny wybór.\n");
        }
    }

    static public class Data {
        public String pesel;
        public String imie;
        public String nazwisko;
        public BigDecimal wynagrodzenie;
        public BigDecimal limit;
        public String stanowisko;
    }
    static public class DataDyrektor extends Data {
        public BigDecimal dodatekSluzbowy;
        public String telefon;
        public String karta;
    }
    static public class DataHandlowiec extends Data {
        public BigDecimal stawkaProwizji;
    }

    public Data readInputData() {
        System.out.println("2. Dodaj pracownika\n");
        System.out.print("[D]yrektor/[H]andlowiec:\t");
        String ch = scan.nextLine().trim().toUpperCase(Locale.ROOT);
        if (ch.isEmpty()) throw new IllegalArgumentException("Nie wybrano stanowiska.");

        Data data;
        char stanowisko = ch.charAt(0);
        if (stanowisko == 'D') {
            data = new DataDyrektor();
            data.stanowisko = "Dyrektor";
        } else if (stanowisko == 'H') {
            data = new DataHandlowiec();
            data.stanowisko = "Handlowiec";
        } else {
            throw new IllegalArgumentException("Niepoprawny typ stanowiska");
        }

        System.out.println("----------------------------------------");

        while (true) {
            System.out.print("Identyfikator PESEL\t:\t");
            String p = scan.nextLine().trim();
            if (isPeselValid(p)) { data.pesel = p; break; }
            System.out.println("Niepoprawny numer PESEL (suma kontrolna). Spróbuj ponownie.");
        }

        System.out.print("Imię\t:\t");
        data.imie = scan.nextLine().trim();

        System.out.print("Nazwisko\t:\t");
        data.nazwisko = scan.nextLine().trim();

        data.wynagrodzenie = readNonNegative("Wynagrodzenie (zł)\t:\t");

        if (data instanceof DataDyrektor d) {
            System.out.print("Telefon służbowy numer\t:\t");
            d.telefon = scan.nextLine().trim();

            d.dodatekSluzbowy = readNonNegative("Dodatek służbowy (zł)\t:\t");

            System.out.print("Karta służbowa numer\t:\t");
            d.karta = scan.nextLine().trim();

            d.limit = readNonNegative("Limit kosztów/miesiąc (zł)\t:\t");
        } else if (data instanceof DataHandlowiec h) {
            h.stawkaProwizji = readNonNegative("Stawka prowizji (%)\t:\t");
            h.limit = readNonNegative("Limit prowizji/miesiąc (zł)\t:\t");
        }

        System.out.println("----------------------------------------");
        System.out.println("[Enter] – zapisz   [Q] – porzuć");
        String ans = scan.nextLine();
        if (ans.equalsIgnoreCase("Q")) {
            throw new IllegalStateException("Porzucono wprowadzanie.");
        }
        return data;
    }

    // Lista pracowników
    public void showEmployees(List<Pracownik> list) {
        System.out.println("1. Lista pracowników\n");
        if (list.isEmpty()) {
            System.out.println("(Brak danych)\n");
            return;
        }
        int i = 0, n = list.size();
        while (true) {
            Pracownik p = list.get(i);
            printEmployee(p, i + 1, n);
            System.out.print("[Enter] – następny   [Q] – powrót: ");
            String ans = scan.nextLine();
            if (ans.equalsIgnoreCase("Q")) break;
            i = (i + 1) % n;
            System.out.println();
        }
    }

    private void printEmployee(Pracownik p, int pos, int total) {
        System.out.println("Identyfikator PESEL : " + p.getPesel());
        System.out.println("Imię : " + p.getImie());
        System.out.println("Nazwisko : " + p.getNazwisko());
        System.out.println("Stanowisko : " + p.getStanowisko());
        System.out.println("Wynagrodzenie (zł) : " + p.getWynagrodzenie());

        if (p instanceof Dyrektor d) {
            System.out.println("Telefon służbowy numer : " + d.getTelefon());
            System.out.println("Dodatek służbowy (zł) : " + d.getDodatekSluzbowy());
            System.out.println("Karta służbowa numer : " + d.getKarta());
            System.out.println("Limit kosztów/miesiąc (zł) : " + p.getLimit());
        } else if (p instanceof Handlowiec h) {
            System.out.println("Telefon służbowy numer : - brak -");
            System.out.println("Prowizja (%) : " + h.getStawkaProwizji());
            System.out.println("Limit prowizji/miesiąc (zł) : " + p.getLimit());
        }
        System.out.printf("[Pozycja: %d/%d]%n", pos, total);
    }

    // Usuwanie
    public String askPeselToDelete() {
        System.out.println("3. Usuń pracownika\n");
        System.out.print("Podaj Identyfikator PESEL : ");
        String pesel = scan.nextLine().trim();
        if (pesel.equalsIgnoreCase("Q")) {
            throw new IllegalStateException("Porzucono usuwanie.");
        }
        return pesel;
    }

    public boolean confirmDeletion(Pracownik p) {
        System.out.println("----------------------------------------");
        printEmployee(p, 1, 1);
        System.out.println("----------------------------------------");
        System.out.print("[Enter] – potwierdź   [Q] – porzuć: ");
        String ans = scan.nextLine();
        return !ans.equalsIgnoreCase("Q");
    }

    // Kopia zapasowa
    public BackupRequest backupScreen() {
        System.out.println("4. Kopia zapasowa\n");
        System.out.print("[Z]achowaj/[O]dtwórz : ");
        String mode = scan.nextLine().trim().toUpperCase(Locale.ROOT);
        if ("Z".equals(mode)) {
            System.out.print("Kompresja [G]zip/[Z]ip : ");
            String c = scan.nextLine().trim().toUpperCase(Locale.ROOT);
            BackupCompression comp = "G".equals(c) ? BackupCompression.GZIP : BackupCompression.ZIP;
            System.out.print("Nazwa pliku : ");
            String name = scan.nextLine().trim();
            return new BackupRequest(BackupRequest.Mode.SAVE, comp, name);
        } else if ("O".equals(mode)) {
            System.out.print("Nazwa pliku : ");
            String name = scan.nextLine().trim();
            return new BackupRequest(BackupRequest.Mode.RESTORE, BackupCompression.AUTO, name);
        } else {
            throw new IllegalArgumentException("Niepoprawny wybór.");
        }
    }

    public void showMessage(String msg) {
        System.out.println(msg + "\n");
    }

    public void showError(String msg) {
        System.err.println("[BŁĄD] " + msg + "\n");
    }

    private BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = scan.nextLine().trim().replace(",", ".");
                return new BigDecimal(s);
            } catch (Exception e) {
                System.out.println("Niepoprawna liczba. Spróbuj ponownie.");
            }
        }
    }

    private BigDecimal readNonNegative(String prompt) {
        while (true) {
            BigDecimal v = readBigDecimal(prompt);
            if (v.signum() >= 0) return v;
            System.out.println("Wartość nie może być ujemna. Spróbuj ponownie.");
        }
    }

    private static boolean isPeselValid(String p) {
        if (p == null || p.length() != 11) return false;
        int[] w = {1,3,7,9,1,3,7,9,1,3,1};
        int sum = 0;
        for (int i = 0; i <= 10; i++) {
            char c = p.charAt(i);
            if (c < '0' || c > '9') return false;
            sum += (c - '0') * w[i];
        }
        return sum % 10 == 0;
    }
}
