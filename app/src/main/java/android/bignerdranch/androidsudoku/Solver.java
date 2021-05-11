package android.bignerdranch.androidsudoku;

public class Solver {
    private static int selected_row;
    private static int selected_column;

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
}
