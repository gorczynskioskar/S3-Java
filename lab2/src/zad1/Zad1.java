package zad1;
import java.util.Scanner;
import static java.lang.System.out;

public class Zad1 {

	public static void main(String[] args) {
		int x, y;
		Scanner scan = new Scanner(System.in);
		out.println("Podaj x: ");
		x=scan.nextInt();
		out.println("Podaj y: ");
		y=scan.nextInt();
		out.println(String.format("%d + %d = %d", x, y, x+y));
	}

}
