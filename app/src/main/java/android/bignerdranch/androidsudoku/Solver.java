package android.bignerdranch.androidsudoku;

//interface between ui and backend
public class Solver {
    SudokuSolver sudokuSolver = new SudokuSolver();
    SudokuGrid mySudoku = new SudokuGrid();
    int selected_row;
    int selected_column;

    //constructor
    Solver(){
        selected_column = -1;
        selected_row = -1;
    }

    //getters and setters
    public int getSelected_row(){
        return selected_row;
    }

    public int getSelected_column(){
        return selected_column;
    }

    public void setSelected_row(int r){
        selected_row = r;
    }

    public void setSelected_column(int c){
        selected_column = c;
    }


    //sets the selected position on the board to the number clicked
    public void setNumberPos(int num){
        if(this.selected_row != -1 && this.selected_column != -1){
            //Todo: set inputted number here
            mySudoku.getSudokCell(this.selected_row, this.selected_column).solve(num);
        }
    }
}
