package android.bignerdranch.androidsudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuBoard extends View {

    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();

    private int cellSize;

    private final Solver solver = new Solver();
    private Canvas canvas;

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Resources.Theme theme = context.getTheme();
        TypedArray a= theme.obtainStyledAttributes(attrs, R.styleable.SudokuBoard, -1, -1);
        TypedArray b = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard,
                0, 0);

        //Todo: delete this
        for(int i = 0; i < 100; i++){
            //Log.d("Blah", "SudokuBoard: i:" + i + " a:" + a.getInteger(i, 2));
        }
        try{
            //Todo: this line doesn't work, uses default
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, Color.BLUE);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHightlightColor, 0);



        }finally{
            a.recycle();
        }

    }

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

        colorCell(canvas, solver.getSelected_row(), solver.getSelected_column());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
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

            colorCell(canvas, solver.getSelected_row(), solver.getSelected_column());
            isValid = true;
        }else{
            isValid = false;
        }

        return isValid;
    }
    private void colorCell(Canvas canv, int r, int c){
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
}
