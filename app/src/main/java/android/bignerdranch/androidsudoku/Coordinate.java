package android.bignerdranch.androidsudoku;

public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int rowInput, int columnInput) {
        row = rowInput;
        column = columnInput;
    }

    private int Row;
    public int getRow(){
        return row;
    }
    public void setRow(int r){
        row = r;
    }

    private int Column;
    public int getColumn(){
        return column;
    }
    public void setColumn(int c){
        column = c;
    }



    public String toString() {
        return "(" + intToLetter(row) + ", " + (column + 1) + ")";
    }

    public String intToLetter(int x) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return alphabet.substring(x, 1);
    }
}
