package android.bignerdranch.androidsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

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
        /*
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            AttributeSet attributes = Xml.asAttributeSet(xpp);

            sudokuBoard = SudokuBoard.get(getApplicationContext(), attributes);


        }catch (Exception e){

        }

        XmlPullParser parser = Resources.getXml(R.xml.class);
        AttributeSet attributes = Xml.asAttributeSet(parser);

        sudokuBoard = SudokuBoard.get(getApplicationContext());

         */
        //sudokuBoard = SudokuBoard.get(getApplicationContext());
        sudokuBoard = findViewById(R.id.SudokuBoard);

        //android.content.res.Resources.Theme#obtainStyledAttributes(AttributeSet, int[], int, int)
   //* Resources.Theme.obtainStyledAttributes()

        myButton = (CustomButton) findViewById(R.id.custombuttontest);
        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
                sudokuBoard.getInput((int) (Math.random() * 24));

            }
        });
    }

    public void buttonOnePress(View view){
        //sudokuBoard.setNumberPos(1);
        sudokuBoard.solveSudoku();
        sudokuBoard.invalidate();
    }//Todo: button press capability
}