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

import androidx.annotation.Nullable;

//visual board
public class SudokuBoard extends View {

    //ints of various colors used across the board
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    private final int numberColor;
    private final int numberColorSolved;

    //paints for drawing in the board grid and highlighting cells
    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();

    //paint for drawing in letters
    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;

    private final Solver solver = new Solver();
    private Canvas canvas;

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //retrieve custom attrs
        Resources.Theme theme = context.getTheme();
        TypedArray a= theme.obtainStyledAttributes(attrs, R.styleable.SudokuBoard, -1, -1);

        try{
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            numberColor = a.getInteger(R.styleable.SudokuBoard_numberColor, 0);
            //Todo: set up xml for numbercolor and numbercolorsolved
            numberColorSolved = a.getInteger(R.styleable.SudokuBoard_numberColorSolved, 0);



        }finally{
            a.recycle();
        }

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
        letterPaint.setColor(numberColor);

        colorCells(canvas, solver.getSelected_row(), solver.getSelected_column());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
        drawnumbers();
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
            solver.setSelected_row((int) Math.ceil(y/cellSize));
            solver.setSelected_column((int) Math.ceil(x/cellSize));

            colorCells(canvas, solver.getSelected_row(), solver.getSelected_column());
            isValid = true;
        }else{
            isValid = false;
        }

        return isValid;
    }

    //draws the numbers in the grid
    private void drawnumbers(){
        letterPaint.setTextSize(cellSize);
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(true /*Todo: if the cell has a single value*/){
                    String text = "0"; //Todo: text = int at the indeces
                    float width, height;

                    //get dimensions of the number to be drawn
                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    //draws the number centered
                    canvas.drawText(text, (column*cellSize) + ((cellSize - width) / 2),
                            (row*cellSize + cellSize) - (cellSize - height)/2, letterPaint);

                    //Todo: different color for phone solved nums. 26:00 part 3
                }
            }
        }
    }

    //Highlights the row, column, and cell that user selected
    private void colorCells(Canvas canv, int r, int c){
        canvas = canv;
        if(solver.getSelected_column() != -1 && solver.getSelected_row() != -1){
            //highlight column
            canvas.drawRect((c-1)*cellSize, 0, c*cellSize, cellSize*9,
                    cellsHighlightColorPaint);
            //highlight row
            canvas.drawRect(0, (r-1)*cellSize, cellSize*9, r*cellSize,
                    cellsHighlightColorPaint);

            //hightlight cell
            canvas.drawRect((c-1)*cellSize, (r-1)*cellSize, c*cellSize, r*cellSize,
                    cellsHighlightColorPaint);

        }

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

    public Solver getSolver(){
        return this.solver;
    }
}
