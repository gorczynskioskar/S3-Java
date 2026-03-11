package matrix;

public class Matrix {
	int columns;
	int rows;
	int[][] data;
	public Matrix(int rows, int columns, int[][] data) {
		this.rows = rows;
		this.columns = columns;
		this.data = data;
	}
	public int getColumns() {
		return columns;
	}
	public int getRows() {
		return rows;
	}
	public int[][] getData(){
		return data;
	}
	public void transpose() {
		int[][] transposed = new int[columns][rows];
		for(int i=0;i<columns;i++) {
			for(int j=0;j<rows;j++) {
				transposed[i][j]=data[j][i];
			}
		}
		int temp = columns;
		columns = rows;
		rows = temp;
		data = transposed;
	}
}