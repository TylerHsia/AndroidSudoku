package android.bignerdranch.androidsudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

//visual board
public class SudokuBoard extends View {

    SudokuSolver sudokuSolver = new SudokuSolver();
    SudokuGrid mySudoku = new SudokuGrid();
    static SudokuBoard sudokuBoard;
    int selected_row;
    int selected_column;
    static AttributeSet myAttrs;

    //ints of various colors used across the board
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    private final int numberColorInputted;
    private final int numberColorSolved;
    private final int numberColorGiven;

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



    public static SudokuBoard get(Context context){
        if(sudokuBoard == null){
            sudokuBoard = new SudokuBoard(context, myAttrs);
        }
        return sudokuBoard;
    }



    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myAttrs = attrs;

        if(sudokuBoard == null){
            sudokuBoard = this;
        }

        selected_column = -1;
        selected_row = -1;

        //retrieve custom attrs
        Resources.Theme theme = context.getTheme();
        TypedArray a= theme.obtainStyledAttributes(attrs, R.styleable.SudokuBoard, -1, -1);

        try{
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            numberColorInputted = a.getInteger(R.styleable.SudokuBoard_numberColorInputted, 0);
            numberColorSolved = a.getInteger(R.styleable.SudokuBoard_numberColorSolved, 0);
            numberColorGiven = a.getInteger(R.styleable.SudokuBoard_numberColorGiven, 0);




        }finally{
            a.recycle();
        }

    }

    public void setHighlightCells(boolean checked) {
        highlightCells = checked;
    }

    //sets dimensions of sudoku grid to be square that fills up canvas
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
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
    protected  void onDraw(Canvas canv){
        canvas = canv;
        //set up BOARDCOLORPAINT, which can act as both a paintbrush and paint bucket
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        boardColorPaint.setAntiAlias(true);
        cellFillColorPaint.setColor(cellFillColor);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        boardColorPaint.setAntiAlias(true);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(numberColorInputted);

        colorCells(canvas, selected_row, selected_column);
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
        drawNumbers();
    }

    //click event handler
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        //can differentiate between types of taps on screen
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            sudokuBoard.selected_row = ((int) Math.ceil(y/cellSize)) - 1;
            sudokuBoard.selected_column = ((int) Math.ceil(x/cellSize)) - 1;

            colorCells(canvas, selected_row, selected_column);
            isValid = true;
        }else{
            isValid = false;
        }

        return isValid;
    }

    //draws the numbers in the grid
    private void drawNumbers(){
        letterPaint.setTextSize(cellSize);
        //for each cell, if it is solved, draw the number
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(mySudoku.getSudokCell(row, column).isSolved()){
                    String text = "" + mySudoku.getSudokCell(row, column).getVal();
                    float width, height;

                    //get dimensions of the number to be drawn
                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();
                    //Todo: get paint vals from xml
                    letterPaint.setColor(numberColorInputted);

                    if(isGiven[row][column]){
                        letterPaint.setColor(numberColorGiven);
                    }
                    if(computerSolved[row][column]){
                        letterPaint.setColor(numberColorSolved);
                    }
                    //draws the number centered
                    canvas.drawText(text, (column*cellSize) + ((cellSize - width) / 2),
                            (row*cellSize + cellSize) - (cellSize - height)/2, letterPaint);

                    //Todo: different color for phone solved nums. 26:00 part 3

                    //givens black, user inputted blue, computer solved green. simple error inputted red
                }
            }
        }
    }

    //Highlights the row, column, and cell that user selected
    private void colorCells(Canvas canv, int r, int c){
        if(highlightCells) {
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

        //fill cell selected with a different color
        fillCell(r, c, cellFillColorPaint);
        invalidate();
    }

    //set BOARDCOLORPAINT to thick value
    private void thickLineStyle(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    //set BOARDCOLORPAINT to thin value
    private void thinLineStyle(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);
    }
    //draws the board grid
    private void drawBoard(Canvas canv){
        canvas = canv;
        //draw column lines
        for(int c = 0; c < 10; c++){
            if(c % 3 == 0){
                thickLineStyle();
            }else{
                thinLineStyle();
            }
            canvas.drawLine(cellSize * c, 0, cellSize * c, getWidth(), boardColorPaint);
        }
        //draw row lines
        for(int r = 0; r < 10; r++){
            if(r % 3 == 0){
                thickLineStyle();
            }else{
                thinLineStyle();
            }
            canvas.drawLine(0,cellSize * r,  getHeight(), cellSize * r, boardColorPaint);

        }
    }

    //sets the selected position on the board to the number clicked
    public void setNumberPos(int num){
        if(this.selected_row != -1 && this.selected_column != -1){
            mySudoku.getSudokCell(this.selected_row, this.selected_column).solve(num);
            drawNumbers();
        }
    }

    //solves sudoku
    public void solveSudoku(){
        if(mySudoku.IsValid()){
            //when solving, if the cell is unsolved, set that that cell will be solved by computer
            for(int row = 0; row < 9; row++){
                for(int column = 0; column < 9; column++){
                    if(!mySudoku.getSudokCell(row, column).isSolved()){
                        computerSolved[row][column] = true;
                    }
                }
            }
            sudokuSolver.Solve(mySudoku, false);
            if(!mySudoku.IsSolved()){
                sudokuSolver.Solve(mySudoku, true);
                if(!mySudoku.IsSolved()){
                    sudokuSolver.bruteForceSolver(mySudoku);
                }
            }
            drawNumbers();
        }
        else Toast.makeText(getContext(), "Sudoku is invalid", Toast.LENGTH_SHORT).show();
    }

    //gets sudoku from input list
    public void getInput(int i){
        mySudoku = sudokuSolver.getInput(i);

        //each currently solved cell is a given. set given[][] to reflect that.
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(mySudoku.getSudokCell(row, column).isSolved()){
                    isGiven[row][column] = true;
                }
                //reset memory of previous game
                else{
                    isGiven[row][column] = false;
                }
                computerSolved[row][column] = false;
            }
        }
        drawNumbers();
    }

    //fills in the cell at the given row, column index
    public void fillCell(int row, int column, Paint paint){
        //hightlight cell
        canvas.drawRect((column)*cellSize, (row)*cellSize, (column+1)*cellSize, (row+1)*cellSize,
                paint);
    }

    //removes number in a given cell
    public void removeCell() {
        if(selected_column != -1 && selected_row != -1){
            if(!isGiven[selected_row][selected_column]) {
                mySudoku.deleteCell(selected_row, selected_column);
            }
        }
    }
}

