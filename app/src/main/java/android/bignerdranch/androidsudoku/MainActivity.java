package android.bignerdranch.androidsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard sudokuBoard;
    private Button generateButton;
    private Button solveButton;
    private Button deleteButton;
    private CheckBox checkBoxHighlightCells;
    private static final int REQUEST_CODE_GENERATE = 0;
    private static final String difficultyExtra = "Difficulty";
    private CheckBox noteBox;

    //Long term by senior project
    //Todo: All UI capability
    //Todo: generation
    //reflect through origin, diagonals, change numbers,
    //Todo: options menu
    //Todo: store sudokus in file, read from file
    //Todo: solve cell
    //Todo: isvalid

    //Short term
    //Todo: lowercase method names
    //Todo: change all sudoku.getSudokuGrid[row][column] to getsudokucell(row, column)
    //Todo: sound effects
    //Todo: reason for why a sudoku is invalid (no solutions, multiples solutions)
    //Todo: activity for settings
    //Todo: stored logic for 23

    //After senior project
    //undo and redo
    //hinting
    //improve and comment all old C# code
    //store data on app close
    //fix generation code where a stack overflow is possible
    //custom animation for onsolved


    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            try{
                int difficulty = savedInstanceState.getInt(difficultyExtra);
                sudokuBoard.generateSudoku(difficulty);
                // sudokuBoard.getInput((int) (Math.random() * 24) + 1);

            }
            catch(Exception e){

            }
        }

        sudokuBoard = findViewById(R.id.SudokuBoard);



        generateButton = (Button) findViewById(R.id.generatebutton);
        generateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = ViewGenerate.newIntent(getApplicationContext());
                startActivityForResult(intent, REQUEST_CODE_GENERATE);

                //sudokuBoard.getInput((int) (Math.random() * 24) + 1);
            }
        });



        solveButton = (Button) findViewById(R.id.solvebutton);
        solveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sudokuBoard.solveSudoku();
                sudokuBoard.invalidate();

            }
        });

        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sudokuBoard.removeCell();

            }
        });

        checkBoxHighlightCells = (CheckBox) findViewById(R.id.checkBoxHighlightCells);
        checkBoxHighlightCells.setChecked(true);
        checkBoxHighlightCells.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sudokuBoard.setHighlightCells(checkBoxHighlightCells.isChecked());
            }
        });

        noteBox = (CheckBox) findViewById(R.id.noteBox);
        noteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sudokuBoard.setNote(isChecked);


            }
        });
    }

    public static Intent newIntent(Context packageContext, int difficulty){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(difficultyExtra, difficulty);
        return intent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_GENERATE) {
            if (data == null) {
                return;
            }
            int difficulty = data.getIntExtra(difficultyExtra, 0);
            Toast.makeText(getApplicationContext(), "" + difficulty, Toast.LENGTH_SHORT).show();
            sudokuBoard.generateSudoku(difficulty);
        }
    }

}