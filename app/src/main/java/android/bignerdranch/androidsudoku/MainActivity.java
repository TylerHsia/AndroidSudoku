package android.bignerdranch.androidsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard sudokuBoard;
    private CustomButton myButton;
    //Todo: UI functional
    //Todo: copy C# code
    //Todo: All UI capability
    //Todo: hinting
    //Todo: generation
    //Todo: rotation and mirroring


    //Todo: make buttons custom
    //Todo: delete button
    //Todo: lowercase method names
    //Todo: change all sudoku.getSudokuGrid[row][column] to getsudokucell(row, column)


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuBoard = findViewById(R.id.SudokuBoard);

        myButton = (CustomButton) findViewById(R.id.custombuttontest);
        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
                sudokuBoard.getInput((int) Math.random() * 24);
            }
        });
    }

    public void buttonOnePress(View view){
        //sudokuBoard.setNumberPos(1);
        sudokuBoard.solveSudoku();
        sudokuBoard.invalidate();
    }//Todo: button press capability
}