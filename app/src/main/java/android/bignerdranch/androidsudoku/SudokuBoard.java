package android.bignerdranch.androidsudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuBoard extends View {

    private final int BOARDCOLOR;
    private final Paint BOARDCOLORPAINT = new Paint();
    private int cellSize;

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard,
                0, 0);

        try{
            //Todo: this line doesn't work, uses default
            BOARDCOLOR = a.getInteger(R.styleable.SudokuBoard_boardColor, Color.BLACK);

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
    protected  void onDraw(Canvas canvas){
        //set up BOARDCOLORPAINT, which can act as both a paintbrush and paint bucket
        BOARDCOLORPAINT.setStyle(Paint.Style.STROKE);
        BOARDCOLORPAINT.setStrokeWidth(16);
        //todo: fix
        BOARDCOLORPAINT.setColor(BOARDCOLOR);
        BOARDCOLORPAINT.setAntiAlias(true);

        canvas.drawRect(0, 0, getWidth(), getHeight(), BOARDCOLORPAINT);
        drawBoard(canvas);
    }

    //set BOARDCOLORPAINT to thick value
    private void thickLineStyle(){
        BOARDCOLORPAINT.setStyle(Paint.Style.STROKE);
        BOARDCOLORPAINT.setStrokeWidth(10);
        BOARDCOLORPAINT.setColor(BOARDCOLOR);
    }

    //set BOARDCOLORPAINT to thin value
    private void thinLineStyle(){
        BOARDCOLORPAINT.setStyle(Paint.Style.STROKE);
        BOARDCOLORPAINT.setStrokeWidth(4);
        BOARDCOLORPAINT.setColor(BOARDCOLOR);
    }
    //draws the board grid
    private void drawBoard(Canvas canvas){
        //draw column lines
        for(int c = 0; c < 10; c++){
            if(c % 3 == 0){
                thickLineStyle();
            }else{
                thinLineStyle();
            }
            canvas.drawLine(cellSize * c, 0, cellSize * c, getWidth(), BOARDCOLORPAINT);
        }
        //draw row lines
        for(int r = 0; r < 10; r++){
            if(r % 3 == 0){
                thickLineStyle();
            }else{
                thinLineStyle();
            }
            canvas.drawLine(0,cellSize * r,  getHeight(), cellSize * r, BOARDCOLORPAINT);

        }
    }
}
