package android.bignerdranch.androidsudoku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class ViewGenerate extends AppCompatActivity {

    private int selectedDifficulty = 1;
    private Button generateButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_view);

        Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);

        /*
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

         */

        String[] items = new String[] { "Very Easy", "Easy", "Medium", "Hard", "Impossible", "Custom", "Blank" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(adapter);





        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                //index 1
                position++;
                if(position <= 5){
                    selectedDifficulty = position;
                }
                //Custom
                if(position == 6){
                    //Todo: implement
                    Toast.makeText(getApplicationContext(), "not implemented", Toast.LENGTH_SHORT).show();
                    selectedDifficulty = position;
                }
                //blank
                if(position == 7){
                    selectedDifficulty = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        generateButton = (Button) findViewById(R.id.generate);
        generateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = MainActivity.newIntent(getApplicationContext(), selectedDifficulty);
                Intent data = new Intent();
                data.putExtra("Difficulty", selectedDifficulty);
                setResult(RESULT_OK, data);
                finish();
                //startActivity(intent);

                //sudokuBoard.getInput((int) (Math.random() * 24) + 1);
            }
        });
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ViewGenerate.class);
        //intent.putExtra(, questionID);
        return intent;
    }
}
