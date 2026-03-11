package zad5;
import java.util.Scanner;
import static java.lang.System.out;

public class Zad5 {

	public static void main(String[] args) {
		float a, b;
		String dzialanie;
		Scanner scan = new Scanner(System.in);
		out.println("Podaj liczbę 1: ");
		a=scan.nextFloat();
		out.println("Podaj liczbę 2: ");
		b=scan.nextFloat();
		out.println("Podaj działanie: ");
		dzialanie=scan.nextLine();
		if(dzialanie=="+") out.println(String.format("Wynik działania: %f", a+b));
	}

}
