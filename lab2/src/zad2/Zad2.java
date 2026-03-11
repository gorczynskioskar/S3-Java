package zad2;
import java.util.Scanner;
import static java.lang.System.out;
import java.lang.Math;
public class Zad2 {

	public static void main(String[] args) {
		float a, b, c;
		Scanner scan = new Scanner(System.in);
		out.println("Podaj a: ");
		a=scan.nextFloat();
		out.println("Podaj b: ");
		b=scan.nextFloat();
		out.println("Podaj c: ");
		c=scan.nextFloat();
		float delta = (b*b)-(4*a*c);
		if(delta>0) {
			out.println(String.format("Dwa rozwiązania: %f, %f", (((-1*b)+Math.sqrt(delta))/2*a),(((-1*b)-Math.sqrt(delta))/2*a)));
		}
		else if(delta==0) {
			out.println(String.format("Jedno rozwiązanie: %f",  ((-1)*b)/2*a));
		}
		else {
			out.println("Brak rozwiązań w zbiorze liczb rzeczywistych.");
		}
	}

}
