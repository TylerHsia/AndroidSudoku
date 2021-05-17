package android.bignerdranch.androidsudoku;

public class SudokuGenerator {
    private SudokuSolver sudokuSolver;
    private SudokuGrid mySudoku;

    public SudokuGenerator(){
        sudokuSolver = new SudokuSolver();
        mySudoku = new SudokuGrid();
    }

    public void rotateClockwise(SudokuGrid sudokuGrid){

    }

    public void flipHorizontal(SudokuGrid sudokuGrid){
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){

            }
        }
    }
}
