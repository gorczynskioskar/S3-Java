package main;
import matrixController.MatrixController;
import matrixModel.MatrixModel;
import matrixView.MatrixView;

public class Main {
	public static void main(String[] args) {
		MatrixView view = new MatrixView();
		MatrixModel model = new MatrixModel();
		MatrixController controller = new MatrixController(model, view);
		controller.start();
	}
}