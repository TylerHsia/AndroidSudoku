package android.bignerdranch.androidsudoku;

public class SudokuGenerator {
    private SudokuSolver sudokuSolver;
    private SudokuGrid mySudoku;

    public SudokuGenerator(){
        sudokuSolver = new SudokuSolver();
        mySudoku = new SudokuGrid();
    }

    //rotates a given sudoku 90 degrees clockwise
    public void rotateClockwise(SudokuGrid sudokuGrid){
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                sudokuGrid.setSudokCell(i, j, copy.getSudokCell(9 - j - 1, i));
            }
        }
    }

    //flips the sudoku around a horizontal midline
    public void flipHorizontal(SudokuGrid sudokuGrid){
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 9; column++){
                SudokCell temp = sudokuGrid.getSudokCell(row, column);
                sudokuGrid.setSudokCell(row, column, sudokuGrid.getSudokCell(8 - row, column));
                sudokuGrid.setSudokCell(8 - row, column, temp);
            }
        }
    }

    //flips the sudoku around a vertical midline
    public void flipVertical(SudokuGrid sudokuGrid){
        for(int column = 0; column < 3; column++){
            for(int row = 0; row < 9; row++){
                SudokCell temp = sudokuGrid.getSudokCell(row, column);
                sudokuGrid.setSudokCell(row, column, sudokuGrid.getSudokCell(8 - row, column));
                sudokuGrid.setSudokCell(8 - row, column, temp);
            }
        }
    }

    //swaps two boxColumns, index zero
    public void swapBoxColumns(SudokuGrid sudokuGrid, int boxColumnOne, int boxColumnTwo){
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(column / 3 == boxColumnOne){
                    sudokuGrid.setSudokCell(row, column, copy.getSudokCell(row, boxColumnTwo * 3 + column % 3));
                }
                if(column / 3 == boxColumnTwo){
                    sudokuGrid.setSudokCell(row, column, copy.getSudokCell(row, boxColumnOne * 3 + column % 3));
                }

            }
        }

    }
}
