package android.bignerdranch.androidsudoku;

public class Hint {

    private  String text;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    private List<SudokCell> RelevantCells;
    //hint types: naked single, naked pair...

    private Coordinate hintCoord;

    public Hint()
    {
        hintCoord = new Coordinate(-1, -1);
        //hintCoord.Row = -1;
        //hintCoord.Column = -1;
        text = "";
    }

    public Hint(Coordinate newHintCoord)
    {
        hintCoord = newHintCoord;
    }

    public Hint(int row, int column)
    {
        hintCoord = new Coordinate(row, column);

    }

    public String toString()
    {
        return text;
    }

    public boolean VoidCoord() {
        if (hintCoord.getColumn() != -1 && hintCoord.getRow() != -1) {
            return false;
        }
        return true;
    }
}
