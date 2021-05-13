package android.bignerdranch.androidsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard sudokuBoard;
    private Button generate;
    private Button solve;
    private Button delete;
    //Todo: All UI capability
    //Todo: hinting
    //Todo: generation
    //Todo: rotation and mirroring
    //Todo: note capability


    //Todo: delete button
    //Todo: lowercase method names
    //Todo: change all sudoku.getSudokuGrid[row][column] to getsudokucell(row, column)
    //Todo: sound effects
    //Todo: reason for why a sudoku is invalid (no solutions, multiples solutions)


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sudokuBoard = findViewById(R.id.SudokuBoard);


        generate = (Button) findViewById(R.id.generatebutton);
        generate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sudokuBoard.getInput((int) (Math.random() * 24));

            }
        });

        solve = (Button) findViewById(R.id.solvebutton);
        solve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sudokuBoard.solveSudoku();
                sudokuBoard.invalidate();

            }
        });

        delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sudokuBoard.removeCell();

            }
        });
    }


    //Todo: button press capability
}