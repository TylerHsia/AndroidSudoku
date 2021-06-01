package android.bignerdranch.androidsudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.FileVisitOption;
import java.util.ArrayList;
import java.util.List;

//visual board
public class SudokuBoard extends View {

    SudokuSolver sudokuSolver = new SudokuSolver();
    private SudokuGrid mySudoku = new SudokuGrid();
    private ArrayList<Integer>[][] userNotes;
    static SudokuBoard sudokuBoard;
    int selected_row;
    int selected_column;
    static AttributeSet myAttrs;
    private boolean notesOn = false;

    //ints of various colors used across the board
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    private final int numberColorInputted;
    private final int numberColorSolved;
    private final int numberColorGiven;
    private final int sameNumberHighlightColor;
    private final int solvedHighlightColor;

    //paints for drawing in the board grid and highlighting cells
    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();

    //paint for drawing in letters
    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;

    private Canvas canvas;

    private boolean highlightCells = true;

    //boolean arrays to keep track of extra states of certain cells on the board
    boolean[][] isGiven = new boolean[9][9];
    boolean[][] computerSolved = new boolean[9][9];
    boolean[][] invalidUserMove = new boolean[9][9];
    int hintCoord = -1;


    public static SudokuBoard get(Context context) {
        if (sudokuBoard == null) {
            sudokuBoard = new SudokuBoard(context, myAttrs);
        }
        return sudokuBoard;
    }

    public void setSudokuBoard(SudokuBoard s){
        sudokuBoard = s;
    }


    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myAttrs = attrs;
        if (sudokuBoard == null) {
            sudokuBoard = this;
        }
        userNotes = new ArrayList[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                userNotes[row][column] = new ArrayList<Integer>();
            }
        }

        selected_column = -1;
        selected_row = -1;

        //retrieve custom attrs
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.SudokuBoard, -1, -1);

        try {
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            numberColorInputted = a.getInteger(R.styleable.SudokuBoard_numberColorInputted, 0);
            numberColorSolved = a.getInteger(R.styleable.SudokuBoard_numberColorSolved, 0);
            numberColorGiven = a.getInteger(R.styleable.SudokuBoard_numberColorGiven, 0);
            sameNumberHighlightColor = a.getInteger(R.styleable.SudokuBoard_sameNumberHighlightColor, 0);
            solvedHighlightColor = a.getInteger(R.styleable.SudokuBoard_solvedHighlightColor, 0);


        } finally {
            a.recycle();
        }


    }

    public void setHighlightCells(boolean checked) {
        highlightCells = checked;
    }

    //sets dimensions of sudoku grid to be square that fills up canvas
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //determining the sizing of the sudoku board as a square
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int dimension = Math.min(width, height);
        cellSize = dimension / 9;

        setMeasuredDimension(dimension, dimension);
    }

    //draws the sudoku grid
    @Override
    protected void onDraw(Canvas canv) {
        canvas = canv;
        //set up BOARDCOLORPAINT, which can act as both a paintbrush and paint bucket
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setAntiAlias(true);
        cellFillColorPaint.setColor(cellFillColor);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColorPaint.setAntiAlias(true);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(numberColorInputted);

        if (!mySudoku.IsSolved()) {
            colorCells(canvas, selected_row, selected_column);

        }else{
            Paint solvedPaint = new Paint();
            solvedPaint.setStyle(Paint.Style.FILL);
            solvedPaint.setAntiAlias(true);
            solvedPaint.setColor(solvedHighlightColor);
            colorAllCells(canvas, solvedPaint);
        }
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
        drawNumbers();
    }

    //click event handler
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        //can differentiate between types of taps on screen
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            selected_row = ((int) Math.ceil(y / cellSize)) - 1;
            selected_column = ((int) Math.ceil(x / cellSize)) - 1;

            colorCells(canvas, selected_row, selected_column);
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    //draws the numbers in the grid
    private void drawNumbers() {
        //for each cell, if it is solved, draw the number
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (mySudoku.getSudokCell(row, column).isSolved()) {
                    letterPaint.setTextSize(cellSize);
                    String text = "" + mySudoku.getSudokCell(row, column).getVal();
                    float width, height;

                    //get dimensions of the number to be drawn
                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();
                    letterPaint.setColor(numberColorInputted);


                    if (isGiven[row][column]) {
                        letterPaint.setColor(numberColorGiven);
                    }
                    if (computerSolved[row][column]) {
                        letterPaint.setColor(numberColorSolved);
                    }

                    if (selected_column != -1 && selected_row != -1) {
                        if (mySudoku.getSudokCell(row, column).getVal() == mySudoku.getSudokCell(selected_row, selected_column).getVal()
                                && mySudoku.getSudokCell(row, column).isSolved()) {
                            letterPaint.setColor(sameNumberHighlightColor);
                        }
                    }

                    if (invalidUserMove[row][column]) {
                        letterPaint.setColor(Color.RED);
                    }
                    //draws the number centered
                    canvas.drawText(text, (column * cellSize) + ((cellSize - width) / 2),
                            (row * cellSize + cellSize) - (cellSize - height) / 2, letterPaint);

                    //givens black, user inputted blue, computer solved green. simple error inputted red
                }


                //draw notes
                else {

                    for (Integer i : userNotes[row][column]) {
                        letterPaint.setColor(Color.BLACK);
                        letterPaint.setTextSize(cellSize / 3);

                        String text = "" + i;
                        float width, height;

                        //get dimensions of the number to be drawn
                        letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                        width = letterPaint.measureText(text);
                        height = letterPaintBounds.height();
                        int tinyRow = (i - 1) / 3;
                        int tinyColumn = (i - 1) % 3;
                        //draws the number centered
                        canvas.drawText(text, (column * cellSize) + ((cellSize - width) / 2) + (tinyColumn - 1) * cellSize / 3,
                                (row * cellSize + cellSize) - (cellSize - height) / 2 + (tinyRow - 1) * cellSize / 3, letterPaint);
                    }

                }
            }
        }
    }

    //Highlights the row, column, and cell that user selected
    private void colorCells(Canvas canv, int r, int c) {
        if (highlightCells) {
            canvas = canv;
            if (selected_column != -1 && selected_row != -1) {

                //fill all cells in the row, column, and box, highlight
                for (int row = 0; row < 9; row++) {
                    for (int column = 0; column < 9; column++) {
                        //fill row
                        if (row == r && column != c) {
                            fillCell(row, column, cellsHighlightColorPaint);
                        }
                        //fill column
                        if (column == c && row != r) {
                            fillCell(row, column, cellsHighlightColorPaint);
                        }
                        //fill box
                        if (row / 3 == r / 3 && column / 3 == c / 3) {
                            //if not already row and column
                            if ((row != r || column == c) && (column != c || row == r)) {
                                fillCell(row, column, cellsHighlightColorPaint);
                            }
                        }
                    }
                }


            }
        }
        if (notesOn) {
            cellFillColorPaint.setColor(Color.GREEN);
        }

        //fill cell selected with a different color
        fillCell(r, c, cellFillColorPaint);

        //fill hint cell
        if(hintCoord != -1){
            cellFillColorPaint.setColor(Color.YELLOW);
            fillCell(hintCoord / 9, hintCoord % 9, cellFillColorPaint);
        }
        invalidate();
    }

    public void colorAllCells(Canvas canvas, Paint paint){
        for(int r = 0; r < 9; r++){
            for(int c = 0; c< 9 ; c++){
                fillCell(r, c, paint);
            }
        }
    }


    //set BOARDCOLORPAINT to thick value
    private void thickLineStyle() {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    //set BOARDCOLORPAINT to thin value
    private void thinLineStyle() {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);
    }

    //draws the board grid
    private void drawBoard(Canvas canv) {
        canvas = canv;
        //draw column lines
        for (int c = 0; c < 10; c++) {
            if (c % 3 == 0) {
                thickLineStyle();
            } else {
                thinLineStyle();
            }
            canvas.drawLine(cellSize * c, 0, cellSize * c, getWidth(), boardColorPaint);
        }
        //draw row lines
        for (int r = 0; r < 10; r++) {
            if (r % 3 == 0) {
                thickLineStyle();
            } else {
                thinLineStyle();
            }
            canvas.drawLine(0, cellSize * r, getHeight(), cellSize * r, boardColorPaint);

        }
    }

    //sets the selected position on the board to the number clicked
    public void setNumberPos(int num) {
        //if a cell is selected
        if (this.selected_row != -1 && this.selected_column != -1) {

            //if note is on
            if (notesOn) {
                //make a note
                //if cell blank
                if (!mySudoku.getSudokCell(selected_row, selected_column).isSolved()) {
                    //if note is already there, remove
                    if (userNotes[selected_row][selected_column].indexOf(num) != -1) {
                        userNotes[selected_row][selected_column].remove((Integer) num);
                    } else {
                        userNotes[selected_row][selected_column].add(num);
                    }
                }
            }


            //if not a given
            else if (!isGiven[selected_row][selected_column]) {
                //if same val is already there, delete
                if (mySudoku.getSudokCell(this.selected_row, this.selected_column).getVal() == num) {
                    mySudoku.getSudokuGrid()[this.selected_row][this.selected_column] = new SudokCell();
                } else {
                    //set to new val
                    hintCoord = -1;
                    mySudoku.getSudokCell(this.selected_row, this.selected_column).solve(num);
                    if (sudokuSolver.InvalidMove(mySudoku, selected_row, selected_column)) {
                        invalidUserMove[selected_row][selected_column] = true;
                    } else {
                        invalidUserMove[selected_row][selected_column] = false;
                    }
                    //if the sudoku is solved
                    if (mySudoku.IsSolved()) {
                        Toast.makeText(sudokuBoard.getContext(), "You solved it!", Toast.LENGTH_SHORT).show();
                    }

                    //set notes blank
                    userNotes[selected_row][selected_column] = new ArrayList<Integer>();
                }
            }
            invalidate();
        }
    }

    //solves sudoku
    public void solveSudoku() {
        if (mySudoku.IsSolved()) {
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    if (computerSolved[row][column]) {
                        mySudoku.getSudokuGrid()[row][column] = new SudokCell();
                        computerSolved[row][column] = false;
                    }
                }
            }
        } else if (mySudoku.IsValid()) {
            //when solving, if the cell is unsolved, set that that cell will be solved by computer
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    if (!mySudoku.getSudokCell(row, column).isSolved()) {
                        computerSolved[row][column] = true;
                    }
                }
            }
            sudokuSolver.Solve(mySudoku, false);
            if (!mySudoku.IsSolved()) {
                sudokuSolver.Solve(mySudoku, true);
                if (!mySudoku.IsSolved()) {
                    sudokuSolver.bruteForceSolver(mySudoku);
                }
            }
            drawNumbers();
        } else Toast.makeText(getContext(), "Sudoku is invalid", Toast.LENGTH_SHORT).show();
    }

    //gets sudoku from input list
    public void getInput(int i) {
        mySudoku = sudokuSolver.getInput(i);

        //each currently solved cell is a given. set given[][] to reflect that.
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (mySudoku.getSudokCell(row, column).isSolved()) {
                    isGiven[row][column] = true;
                }
                //reset memory of previous game
                else {
                    isGiven[row][column] = false;
                }
                computerSolved[row][column] = false;
                invalidUserMove[row][column] = false;
            }
        }
        drawNumbers();
    }

    //paints in the cell at the given row, column index
    public void fillCell(int row, int column, Paint paint) {
        //highlight cell
        canvas.drawRect((column) * cellSize, (row) * cellSize, (column + 1) * cellSize, (row + 1) * cellSize,
                paint);
    }

    //removes number in a given cell
    public void removeCell() {
        if (selected_column != -1 && selected_row != -1) {
            if (!isGiven[selected_row][selected_column]) {
                invalidUserMove[selected_row][selected_column] = false;
                mySudoku.deleteCell(selected_row, selected_column);
                userNotes[selected_row][selected_column] = new ArrayList<Integer>();

            }
        }
        invalidate();
    }

    public void generateSudoku(int difficulty) {

        if (difficulty == 6) {
            mySudoku = new SudokuGrid();
        }

        //retrieve from file
        if (difficulty != 6 && difficulty != 7) {
            SudokuGenerator sudokuGenerator = new SudokuGenerator();
            String txt;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("difficulty" + difficulty)));
                String sudokuString = "";
                int index = (int) (Math.random() * 100);
                for(int i = 0; i <= index; i++){
                    if(reader.readLine().equals("Index " + index)){
                        sudokuString = reader.readLine();
                    }
                    reader.readLine();
                }
                SudokuGrid sudokuGrid = new SudokuGrid();
                mySudoku = sudokuGrid.readString(sudokuString);

            }
            catch(Exception e){
                Log.i("test", "test");
            }
        }

        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        sudokuGenerator.perturb(mySudoku);




        //each currently solved cell is a given. set given[][] to reflect that.
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (mySudoku.getSudokCell(row, column).isSolved()) {
                    isGiven[row][column] = true;
                }
                //reset memory of previous game
                else {
                    isGiven[row][column] = false;
                }
                computerSolved[row][column] = false;
                invalidUserMove[row][column] = false;
                userNotes[row][column] = new ArrayList<Integer>();
            }
        }


        drawNumbers();
    }

    //getters and setters
    public boolean getNotesOn(){
        return notesOn;
    }

    public void setNotesON(boolean isChecked) {
        notesOn = isChecked;
    }

    //1d array, 81 values, each value has a string with all userNotes in that cell
    public String[] getUserNotesForJson() {
        String[] stringNotes = new String[81];
        for(int i = 0; i < 81; i++){
            int row = i/9;
            int column = i%9;
            String cellNote = "";
            for(int j = 0; j < userNotes[row][column].size(); j++){
                cellNote = cellNote + userNotes[row][column].get(j);
            }
            stringNotes[i] = cellNote;
        }
        return stringNotes;
    }

    public void setUserNotes(ArrayList<Integer>[][] userNotes) {
        this.userNotes = userNotes;

        /*
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                for(int i = 0; i < userNotes[r][c].size(); i++){
                    double numD =  (userNotes[r][c].get(i).doubleValue());
                    int num = (int) numD;

                    userNotes[r][c].set(i, num);
                }
            }
        }

         */



    }

    public SudokuGrid getMySudoku() {
        return mySudoku;
    }

    public void setMySudoku(SudokuGrid mySudoku) {
        this.mySudoku = mySudoku;
    }

    public boolean[][] getIsGiven() {
        return isGiven;
    }

    public void setIsGiven(boolean[][] isGiven) {
        this.isGiven = isGiven;
    }

    public boolean[][] getComputerSolved() {
        return computerSolved;
    }

    public void setComputerSolved(boolean[][] computerSolved) {
        this.computerSolved = computerSolved;
    }

    public boolean[][] getInvalidUserMove() {
        return invalidUserMove;
    }

    public void setInvalidUserMove(boolean[][] invalidUserMove) {
        this.invalidUserMove = invalidUserMove;
    }

    public void isValid() {
        if(mySudoku.IsValid()){
            Toast.makeText(getContext(), "Sudoku is valid", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Sudoku is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    public void solveCell() {
        //if selected cell is unsolved
        if(mySudoku.IsValid()) {
            if (selected_column != -1 && selected_row != -1) {
                if (!mySudoku.getSudokCell(selected_row, selected_column).isSolved()) {
                    //solve it
                    SudokuGrid copy = sudokuSolver.Copy(mySudoku);
                    sudokuSolver.bruteForceSolver(copy);
                    mySudoku.getSudokCell(selected_row, selected_column).solve(copy.getSudokCell(selected_row, selected_column).getVal());
                    computerSolved[selected_row][selected_column] = true;
                }


            }
        }

    }

    public void hint() {
        SudokuHinter sudokuHinter = new SudokuHinter();
        hintCoord = sudokuHinter.getNextSolvedCoord(mySudoku);
    }


    //end of getters and setters
}

