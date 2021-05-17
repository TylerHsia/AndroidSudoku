package android.bignerdranch.androidsudoku;

public class SudokuGenerator {
    private SudokuSolver sudokuSolver;
    private SudokuGrid mySudoku;

    public SudokuGenerator(){
        sudokuSolver = new SudokuSolver();
        mySudoku = new SudokuGrid();
    }

    public void rotateClockwise(SudokuGrid sudokuGrid){
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                sudokuGrid.setSudokCell(i, j, copy.getSudokCell(9 - j - 1, i));
            }
        }
    }

    public void flipHorizontal(SudokuGrid sudokuGrid){
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 9; column++){
                SudokCell temp = sudokuGrid.getSudokCell(row, column);
                sudokuGrid.setSudokCell(row, column, sudokuGrid.getSudokCell(8 - row, column));
                sudokuGrid.setSudokCell(8 - row, column, temp);
            }
        }
    }

    public void flipVertical(SudokuGrid sudokuGrid){
        for(int column = 0; column < 3; column++){
            for(int row = 0; row < 9; row++){
                SudokCell temp = sudokuGrid.getSudokCell(row, column);
                sudokuGrid.setSudokCell(row, column, sudokuGrid.getSudokCell(8 - row, column));
                sudokuGrid.setSudokCell(8 - row, column, temp);
            }
        }
    }
}
