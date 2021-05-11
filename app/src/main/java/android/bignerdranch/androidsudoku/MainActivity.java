package android.bignerdranch.androidsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;
    //Todo: UI functional
    //Todo: copy C# code
    //Todo: All UI capability
    //Todo: hinting
    //Todo: generation
    //Todo: rotation and mirroring


    //Todo: make buttons custom
    //Todo: delete button


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
    }

    public void buttonOnePress(View view){
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }//Todo: button press capability
}