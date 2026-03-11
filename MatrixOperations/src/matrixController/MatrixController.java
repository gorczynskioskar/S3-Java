package matrixController;
import java.util.InputMismatchException;

import matrixModel.MatrixModel;
import matrixView.MatrixView;

public class MatrixController {
	MatrixModel model;
	MatrixView view;
	public MatrixController(MatrixModel model, MatrixView view) {
		this.model = model;
		this.view = view;
	}
	public void start() {
		while(true) {
			try {
				switch(view.menu()) {
				case 1: 
					MatrixView.Data data = view.readInputData();
					model.pushData(data);
					break;
				case 21:
					try {
						model.transposeInputMatrices();
					} catch(IllegalStateException e) {
						view.displayError(e.getMessage());
					}
					break;
				case 22:
					try {
						model.multiplyInputMatrices();
					} catch(IllegalStateException e) {
						view.displayError(e.getMessage());
					}
					break;
				case 23:
					try {
						model.transposeOutputMatrix();
					} catch(IllegalStateException e) {
						view.displayError(e.getMessage());
					}
					break;
				case 31:
					try {
						String matrices = model.displayInputMatrices();
						view.display(matrices);
					} catch(IllegalStateException e) {
						view.displayError(e.getMessage());
					}
					break;
				case 32:
					try {
						String matrix = model.displayOutputMatrix();
						view.display(matrix);
					} catch(IllegalStateException e) {
						view.displayError(e.getMessage());
					}
					break;
				default:
					view.displayError("Nieprawidłowa opcja.\n");
					break;
				}
			} catch(InputMismatchException e) {
				view.displayError("Nieprawidłowa opcja.\n");
			}
		}
	}
}
