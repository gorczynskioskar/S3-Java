package matrixView;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MatrixView {
	Scanner scan = new Scanner(System.in);
	public int menu() {
		int choice = 0;
		System.out.println("1) Wprowadź macierze wejściowe.");
		System.out.println("21) Transponuj macierze wejściowe.");
		System.out.println("22) Pomnóż macierze wejściowe.");
		System.out.println("23) Transponuj macierz wynikową.");
		System.out.println("31) Wyświetl macierze wejściowe.");
		System.out.println("32) Wyświetl macierz wynikową.");
		System.out.print("Podaj opcję: ");
		choice = scan.nextInt();
		return choice;
	}
	static public class Data{
		public int rA;
		public int rB;
		public int cA;
		public int cB;
		public int[][] A;
		public int[][] B;
	}
	public Data readInputData() {
		Data data = new Data();
		System.out.print("Podaj liczbę wierszy macierzy A: ");
		while(true) {
			try {
				data.rA = scan.nextInt();
				break;
			} catch(InputMismatchException e) {
				displayError("Podano coś innego niż liczba. Spróbuj ponownie.\n");
				scan.next();
			}
		}
		System.out.print("Podaj liczbę kolumn macierzy A: ");
		while(true) {
			try {
				data.cA = scan.nextInt();
				break;
			} catch(InputMismatchException e) {
				displayError("Podano coś innego niż liczba. Spróbuj ponownie.\n");
				scan.next();
			}
		}
		System.out.print("Podaj liczbę wierszy macierzy B: ");
		while(true) {
			try {
				data.rB = scan.nextInt();
				break;
			} catch(InputMismatchException e) {
				displayError("Podano coś innego niż liczba. Spróbuj ponownie.\n");
				scan.next();
			}
		}
		System.out.print("Podaj liczbę kolumn macierzy B: ");
		while(true) {
			try {
				data.cB = scan.nextInt();
				break;
			} catch(InputMismatchException e) {
				displayError("Podano coś innego niż liczba. Spróbuj ponownie.\n");
				scan.next();			}
		}
		data.A = new int[data.rA][data.cA];
		data.B = new int[data.rB][data.cB];
		System.out.println("Podaj wartości macierzy A (każdą wartość zatwierdź klawiszem Enter): ");
		for(int i=0;i<data.rA;i++) {
			for(int j=0;j<data.cA;j++) {
				while(true) {
					try {
						data.A[i][j] = scan.nextInt();
						break;
					} catch(InputMismatchException e) {
						displayError("Podano coś innego niż liczba. Spróbuj ponownie.\n");
						scan.next();					}
				}
			}
		}
		System.out.println("Podaj wartości macierzy B (każdą wartość zatwierdź klawiszem Enter): ");
		for(int i=0;i<data.rB;i++) {
			for(int j=0;j<data.cB;j++) {
				while(true) {
					try {
						data.B[i][j] = scan.nextInt();
						break;
					} catch(InputMismatchException e) {
						displayError("Podano coś innego niż liczba. Spróbuj ponownie.\n");
						scan.next();					}
				}
			}
		}
		return data;
	}
	public void display(String s) {
		System.out.print(s);
	}
	public void displayError(String errorMessage) {
		System.err.print(errorMessage);
	}
}
