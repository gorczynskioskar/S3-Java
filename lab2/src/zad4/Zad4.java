package zad4;
import java.util.Scanner;
import static java.lang.System.out;

public class Zad4 {

	public static void main(String[] args) {
		int n;
		Scanner scan = new Scanner(System.in);
		out.println("Podaj liczbę liczb w tablicy: ");
		n=scan.nextInt();
		int[] tablica = new int[n];
		for(int i=0; i<tablica.length;i++) {
			out.println(String.format("Podaj liczbę %d:", i+1));
			tablica[i]=scan.nextInt();
		}
		int max = tablica[0];
		for(int i=1; i<tablica.length;i++) {
			if(tablica[i]>max) max=tablica[i];
		}
		out.println(String.format("Max: %d", max));
	}
}
