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

    //by senior project
    //Todo: All UI capability - remove hello, change name, change black text on dark blue
    //Todo: solve cell
    //Todo: isvalid
    //Todo: basic hinting - next solved cell


    //After senior project
    //undo and redo
    //hinting
    //improve and comment all old C# code
    //store data on app close
    //fix generation code where a stack overflow is possible
    //custom animation for onsolved
    //options menu
    //sound effects
    //reason for why a sudoku is invalid (no solutions, multiples solutions)
    //activity for settings
    //Log.i("infinite loop
    //change difficulty ratings to enum
    //Notes are removed when number is inputted
    //note checkbox sometimes hidden by bottom screen menu



    @Override
    public void onResume() {
        super.onResume();
        sudokuBoard.setSudokuBoard(sudokuBoard);
        //sudokuBoard = findViewById(R.id.SudokuBoard);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //store current state of board to shared preferences
    @Override
    public void onStop() {

        Log.i("Shared Preferences", "on destroy called");

        //sets to null so that when the app is reloaded, it makes a new sudokuBoard
        sudokuBoard.setSudokuBoard(null);

        SharedPreferences sharedPreferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        json = gson.toJson(sudokuBoard.getUserNotesForJson());
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
        super.onStop();
        //putting super.onDestroy here fixes problem of back button
        //super.onDestroy();

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
        if (!json.equals("")) {
            String[] stringNotes = (gson.fromJson(json, String[].class));
            ArrayList<Integer>[][] userNotes = new ArrayList[9][9];
            userNotes = new ArrayList[9][9];
            for (int i = 0; i < 81; i++) {
                int row = i / 9;
                int column = i % 9;
                userNotes[row][column] = new ArrayList<Integer>();
                if(stringNotes[i] != null) {
                    for (int j = 0; j < stringNotes[i].length(); j++) {
                        userNotes[row][column].add(Integer.parseInt(stringNotes[i].substring(j, j + 1)));
                    }
                }
            }
            sudokuBoard.setUserNotes(userNotes);

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


        if (savedInstanceState != null) {
            try {
                int difficulty = savedInstanceState.getInt(difficultyExtra);


            } catch (Exception e) {

            }
        }


        generateButton = (Button) findViewById(R.id.generatebutton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewGenerate.newIntent(getApplicationContext());
                startActivityForResult(intent, REQUEST_CODE_GENERATE);

                //sudokuBoard.getInput((int) (Math.random() * 24) + 1);
            }
        });


        solveButton = (Button) findViewById(R.id.solvebutton);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudokuBoard.solveSudoku();
                sudokuBoard.invalidate();

            }
        });

        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        noteBox.setChecked(sudokuBoard.getNotesOn());
        noteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sudokuBoard.setNotesON(isChecked);


            }
        });
    }

    public static Intent newIntent(Context packageContext, int difficulty) {
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
            SharedPreferences sharedPreferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
            //set program to not use shared preferences for data, but just use current board.
            Log.i("Shared Preferences", "shared preferences erased");
            sharedPreferences.edit().putString(userNotesKey, "").apply();
        }
    }

}