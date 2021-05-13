package android.bignerdranch.androidsudoku;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.bignerdranch.androidsudoku.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class CustomButton extends androidx.appcompat.widget.AppCompatButton {
    private int mHeight;
    private int mWidth;
    public CustomButton(Context context) {
        super(context);
        initView();
    }
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mHeight == 0 && mWidth == 0 && w != 0 && h != 0) {
            mHeight = getHeight();
            mWidth = getWidth();
        }
    }


    //Todo: implement properly
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        //can differentiate between types of taps on screen
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            SudokuBoard sudokuBoard = SudokuBoard.get(getContext());
            sudokuBoard.setNumberPos(Integer.parseInt(getText().toString()));
            isValid = true;
        }else{
            isValid = false;
        }


        return isValid;
    }

    private void initView() {
        Resources resources = getResources();
        setTextSize(1, 24);
        setTextColor(Color.BLACK);
    }

    /*
    android:id="@+id/button2"
    style="@style/Widget.AppCompat.Button.Borderless"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:text="@string/button2"
    android:textColor="@color/black"
    android:textSize="24sp" />

     */

}