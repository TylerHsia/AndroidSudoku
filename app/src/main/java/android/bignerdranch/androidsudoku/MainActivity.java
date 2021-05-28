package android.bignerdranch.androidsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard sudokuBoard;
    private Button generateButton;
    private Button solveButton;
    private Button deleteButton;
    private CheckBox checkBoxHighlightCells;
    private static final int REQUEST_CODE_GENERATE = 0;
    private static final String difficultyExtra = "Difficulty";
    private CheckBox noteBox;
    //keys for storing state data
    private final String userNotesKey = "usernotes";
    private final String mySudokuKey = "mysudok";
    private final String notesOnKey = "note";
    private final String isGivenKey = "givens";
    private final String computerSolvedKey = "computerSolved";
    private final String invalidUserMoveKey = "invalidUserMove";

    //Long term by senior project
    //Todo: All UI capability
    //write and read to and from file
    //Todo: options menu
    //Todo: store sudokus in file, read from file
    //Todo: solve cell
    //Todo: isvalid
    //Todo: savestate save current sudoku

    //Short term
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
    public void onPause(){
        super.onPause();
    }

    //store current state of board to shared preferences
    @Override
    public void onDestroy(){


        SharedPreferences sharedPreferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        json = gson.toJson(sudokuBoard.getUserNotes());
        sharedPreferences.edit().putString(userNotesKey, json).apply();

        json = gson.toJson(sudokuBoard.getMySudoku());
        sharedPreferences.edit().putString(mySudokuKey, json).apply();

        json = gson.toJson(sudokuBoard.getNotesOn());
        sharedPreferences.edit().putString(notesOnKey, json).apply();

        json = gson.toJson(sudokuBoard.getIsGiven());
        sharedPreferences.edit().putString(isGivenKey, json).apply();

        json = gson.toJson(sudokuBoard.getComputerSolved());
        sharedPreferences.edit().putString(computerSolvedKey, json).apply();

        json = gson.toJson(sudokuBoard.getInvalidUserMove());
        sharedPreferences.edit().putString(invalidUserMoveKey, json).apply();
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuBoard = findViewById(R.id.SudokuBoard);

        //retrieve values from sharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

        json = sharedPreferences.getString(userNotesKey, "");
        //if shared preferences not blank
        if(!json.equals("")){
            ArrayList[][] userNotesD = (gson.fromJson(json, ArrayList[][].class));
            ArrayList<Integer>[][] userNotes = new ArrayList[9][9];
            userNotes = new ArrayList[9][9];
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    userNotes[row][column] = new ArrayList<Integer>();
                    for(int i = 0; i < userNotesD[row][column].size(); i++){
                        //userNotes[row][column].add(userNotesD[row][column].get(i));
                    }
                }
            }



            //sudokuBoard.setUserNotes(userNotes);

            json = sharedPreferences.getString(mySudokuKey, "");
            sudokuBoard.setMySudoku(gson.fromJson(json, SudokuGrid.class));

            json = sharedPreferences.getString(notesOnKey, "");
            sudokuBoard.setNotesON(gson.fromJson(json, boolean.class));

            json = sharedPreferences.getString(isGivenKey, "");
            sudokuBoard.setIsGiven(gson.fromJson(json, boolean[][].class));

            json = sharedPreferences.getString(computerSolvedKey, "");
            sudokuBoard.setComputerSolved(gson.fromJson(json, boolean[][].class));

            json = sharedPreferences.getString(invalidUserMoveKey, "");
            sudokuBoard.setInvalidUserMove(gson.fromJson(json, boolean[][].class));
        }








        if(savedInstanceState != null){
            try{
                int difficulty = savedInstanceState.getInt(difficultyExtra);
                //sudokuBoard.generateSudoku(difficulty);
                // sudokuBoard.getInput((int) (Math.random() * 24) + 1);
                //Todo: sudokuBoard.set(sudoku, boolean arrays, notes array

                //sudokuBoard = gson.fromJson(json, SudokuBoard.class);

            }
            catch(Exception e){

            }
        }




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
                sudokuBoard.setNotesON(isChecked);


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