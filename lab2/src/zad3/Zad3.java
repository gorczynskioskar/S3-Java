package zad3;
import java.util.Scanner;
import static java.lang.System.out;

public class Zad3 {

	public static void main(String[] args) {
		int[] tablica = new int[] {3,2,5,2,3,7,1};
		int max = tablica[0];
		for(int i=1; i<tablica.length;i++) {
			if(tablica[i]>max) max=tablica[i];
		}
		out.println(String.format("Max: %d", max));
	}
}
