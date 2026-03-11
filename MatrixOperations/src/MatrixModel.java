package matrixModel;
import matrix.Matrix;
import matrixView.MatrixView.Data;

public class MatrixModel {
	Matrix A = null;
	Matrix B = null;
	Matrix C = null;

	public void pushData(Data data) {
		A = new Matrix(data.rA, data.cA, data.A);
		B = new Matrix(data.rB, data.cB, data.B);
	}
	public void transposeInputMatrices() {
		if(A == null || B == null) {
			throw new IllegalStateException("Macierze wejściowe nie zostały zainicjalizowane.\n");
		}
		A.transpose();
		B.transpose();
	}
	public void transposeOutputMatrix() {
		if(C == null) {
			throw new IllegalStateException("Macierz wyjściowa nie została zainicjalizowana.\n");
		}
		C.transpose();
	}
	public void multiplyInputMatrices() {
		if(A == null || B == null) {
			throw new IllegalStateException("Macierze wejściowe nie zostały zainicjalizowane.\n");
		}
		int[][] dataA = A.getData();
		int[][] dataB = B.getData();
		if(A.getColumns()!=B.getRows()) {
			throw new IllegalStateException("Mnożenie jest niemożliwe. Liczba kolumn macierzy A różni się od liczby wierszy macierzy B.\n");
		}
		int[][] dataC = new int[A.getRows()][B.getColumns()];
		for(int i=0;i<dataC.length;i++) {
			for(int j=0;j<dataC[0].length;j++) {
				dataC[i][j]=0;
			}
		}
		for(int i=0;i<A.getRows();i++) {
			for(int j=0;j<B.getColumns();j++) {
				for(int k=0;k<A.getColumns();k++) {
					dataC[i][j]+=(dataA[i][k]*dataB[k][j]);
				}
			}
		}
		C = new Matrix(dataC.length, dataC[0].length, dataC);
	}
	public String displayInputMatrices() {
		if(A == null || B == null) {
			throw new IllegalStateException("Macierze wejściowe nie zostały zainicjalizowane.\n");
		}
		String s = "Matrix A:\n";
		int[][] dataA = A.getData();
		for(int i=0;i<A.getRows();i++) {
			for(int j=0; j<A.getColumns();j++) {
				s = s+String.format("%d ", dataA[i][j]);
			}
			s = s+"\n";
		}
		s = s + "Matrix B:\n";
		int[][] dataB = B.getData();
		for(int i=0;i<B.getRows();i++) {
			for(int j=0; j<B.getColumns();j++) {
				s = s+String.format("%d ", dataB[i][j]);
			}
			s = s+"\n";
		}
		return s;
	}
	public String displayOutputMatrix() {
		if(C == null) {
			throw new IllegalStateException("Macierz wyjściowa nie została zainicjalizowana.\n");
		}
		String s = "Matrix C:\n";
		int[][] dataC = C.getData();
		for(int i=0;i<C.getRows();i++) {
			for(int j=0; j<C.getColumns();j++) {
				s = s+String.format("%d ", dataC[i][j]);
			}
			s = s+"\n";
		}
		return s;
	}
}
