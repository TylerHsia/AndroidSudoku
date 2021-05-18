package android.bignerdranch.androidsudoku;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

/*"A unit test, in contrast with an instrumentation test, focuses on the small building blocks of your code.
It’s generally concerned with one class at a time, testing one function at a time.
Unit tests typically run the fastest out of the different kinds of tests, because they are small and independent
of the Android framework and so do not need to run on a device or emulator. JUnit is usually used to run these tests."
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {
        Log.i("Test", "addition_isCorrect: ");
        assertEquals("Test message", 4, 2 + 2);
    }


    private int NumStoredSudokus = 23;


    @Test
    public void testBoxSwitcher() {
        SudokuGrid mySudoku = new SudokuGrid();
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        mySudoku = sudokuSolver.getInput(1);
        SudokuGrid swapBoxColumns = sudokuSolver.getInput(1);
        sudokuGenerator.swapBoxColumns(swapBoxColumns, 1, 1);
        assertTrue(swapBoxColumns.equals(mySudoku));

        sudokuGenerator.swapBoxColumns(swapBoxColumns, 1, 2);
        sudokuGenerator.swapBoxColumns(mySudoku, 2, 1);
        assertTrue(swapBoxColumns.equals(mySudoku));

        sudokuGenerator.swapBoxRows(swapBoxColumns, 1, 2);
        sudokuGenerator.swapBoxRows(mySudoku, 2, 1);
        assertTrue(swapBoxColumns.equals(mySudoku));

        for(int i = 1; i <= 23; i++){
            swapBoxColumns = sudokuSolver.getInput(i);
            assertTrue("" + i, swapBoxColumns.IsValid());
            sudokuGenerator.swapBoxRows(swapBoxColumns, 0, 1);
            assertTrue("" + i, swapBoxColumns.IsValid());
            sudokuGenerator.swapBoxRows(swapBoxColumns, 1, 2);
            assertTrue("" + i, swapBoxColumns.IsValid());
            sudokuGenerator.swapBoxRows(swapBoxColumns, 0, 2);
            assertTrue("" + i, swapBoxColumns.IsValid());

        }

    }
    @Test
    public void testRotationAndReflection(){
        SudokuGrid mySudoku = new SudokuGrid();
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        mySudoku = sudokuSolver.getInput(1);
        SudokuGrid rotateSudoku = sudokuSolver.getInput(1);
        sudokuGenerator.rotateClockwise(rotateSudoku);
        sudokuGenerator.rotateClockwise(rotateSudoku);
        sudokuGenerator.rotateClockwise(rotateSudoku);
        sudokuGenerator.rotateClockwise(rotateSudoku);
        assertTrue(mySudoku.equals(rotateSudoku));

        SudokuGrid flipHorizontal = sudokuSolver.getInput(1);
        sudokuGenerator.flipHorizontal(flipHorizontal);
        sudokuGenerator.flipHorizontal(flipHorizontal);
        assertTrue(mySudoku.equals(flipHorizontal));

        SudokuGrid flipVertical = sudokuSolver.getInput(1);
        sudokuGenerator.flipVertical(flipVertical);
        sudokuGenerator.flipVertical(flipVertical);
        assertTrue(mySudoku.equals(flipVertical));

    }

    @Test
    public void testFillEmptyGrid(){
        for(int i = 0; i < 100; i++) {

            SudokuSolver sudokuSolver = new SudokuSolver();
            SudokuGenerator sudokuGenerator = new SudokuGenerator();
            SudokuGrid mySudoku = sudokuGenerator.fillEmptyGrid();
            assertTrue("" + i + mySudoku,mySudoku.IsValid());
            Log.i("Test", "" + i + mySudoku );

        }
    }

    @Test
    public void testMakeInitialSudoku(){
        for(int i = 0; i < 100; i++) {

            SudokuSolver sudokuSolver = new SudokuSolver();
            SudokuGenerator sudokuGenerator = new SudokuGenerator();
            SudokuGrid mySudoku = sudokuGenerator.makeInitialSudoku();
            Log.i("Test", "" + i + mySudoku);
            assertTrue("" + i + mySudoku,mySudoku.IsValid());
            //sudokuSolver.bruteForceSolver(mySudoku);
            Log.i("Test", "" + i + "\nDifficulty: " + sudokuSolver.RateDifficulty(mySudoku));

        }
    }

    @Test
    public void testGeneratDifficulty(){
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        SudokuSolver sudokuSolver = new SudokuSolver();
        for(int i = 5; i <=5; i++){
            SudokuGrid mySudoku = sudokuGenerator.generateDifficulty(i);
            Log.i("Test", "TestGenerateDifficulty at difficulty " + i + mySudoku);
            //Todo: store mySudoku to file
            //It was able to generate difficulty one and two but not three in 20 minutes
            assertTrue("" + i + mySudoku, mySudoku.IsValid());
            //assertTrue("" + i + mySudoku, sudokuSolver.RateDifficulty(mySudoku) == i);

        }
    }


    @Test
    public void testInvalidMoveRowColumn(){
        SudokuGrid mySudoku = new SudokuGrid();
        SudokuSolver sudokuSolver = new SudokuSolver();
        //duplicate in column
        mySudoku = sudokuSolver.getInput(1);
        mySudoku.getSudokCell(1, 7).solve(7);
        assertTrue(sudokuSolver.InvalidMove(mySudoku, 1, 7));

        //duplicate in row
        mySudoku = sudokuSolver.getInput(1);
        mySudoku.getSudokCell(4, 6).solve(1);
        assertTrue(sudokuSolver.InvalidMove(mySudoku, 4, 6));


        //duplicate in box
        mySudoku = sudokuSolver.getInput(1);
        mySudoku.getSudokCell(1, 4).solve(4);
        assertTrue(sudokuSolver.InvalidMove(mySudoku, 1, 4));



        //no duplicate
        mySudoku = sudokuSolver.getInput(1);
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                assertFalse(sudokuSolver.InvalidMove(mySudoku, row, column));
            }
        }
    }

    @Test
    public void testSudokuGridRemoveMethod(){
        SudokuGrid mySudoku = new SudokuGrid();
        SudokuSolver sudokuSolver = new SudokuSolver();
        mySudoku = sudokuSolver.getInput(3);
        sudokuSolver.BoxChecker(mySudoku);
        mySudoku.deleteCell(0, 1);
        SudokuGrid newSudoku = new SudokuGrid();
        newSudoku = sudokuSolver.getInput(3);
        newSudoku.deleteCell(0, 1);
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                assertTrue("r: " + r + " c: " + c, mySudoku.getSudokCell(r, c).equals(newSudoku.getSudokCell(r, c)));
            }
        }
        assertTrue(mySudoku.equals(newSudoku));
        //Note: assertEquals does not call class's equals method
    }
    @Test
    public void TestRateDifficulty() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        for (int i = 1; i <= NumStoredSudokus; i++) {
            SudokCell sudokCell = new SudokCell();
            //inputted sudoku
            int[][] sudokuInputted = sudokuSolver.input(i);

            SudokuGrid mySudoku = new SudokuGrid();

            //my sudoku to be worked with
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    mySudoku.getSudokuGrid()[row][column] = new SudokCell(sudokuInputted[row][column]);
                }
            }


            Log.i("Difficulty", "the level of " + i + " is " + sudokuSolver.RateDifficulty(mySudoku));
        }
    }

    @Test
    public void TestMethod1() {


        ArrayList<Integer> possibles = new ArrayList<Integer>();
        possibles.add(10);
        possibles.remove(0);
        //var x = new SudokuLogic.Class1();
        //var y = x.HelloTest();
        //Log.i("Test", $"the value is {y}");
        ////Assert.Fail(y);
        SudokCell sudokCell = new SudokCell();
        SudokCell newCell = new SudokCell(0);
        newCell.getPossibles().remove(0);


    }

    @Test
    public void CheckContainsDuplicateMethod() {


        //no duplicate
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        Collections.addAll(list1, 1, 2, 3, 4);

        //duplicate
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        Collections.addAll(list2, 1, 1, 2, 3);

        //no duplicate
        ArrayList<Integer> list3 = new ArrayList<Integer>();
        Collections.addAll(list3, 1, 5, 2, 3, 9, 8, 4, 7, 6);

        //duplicate
        ArrayList<Integer> list4 = new ArrayList<Integer>();
        Collections.addAll(list4, 1, 5, 6, 3, 9, 8, 4, 7, 6);


        SudokuSolver sudokuSolver = new SudokuSolver();

        assertFalse("1 wrong", sudokuSolver.ContainsDuplicate(list1));
        assertTrue("2 wrong", sudokuSolver.ContainsDuplicate(list2));
        assertFalse("3 wrong", sudokuSolver.ContainsDuplicate(list3));
        assertTrue("4 wrong ", sudokuSolver.ContainsDuplicate(list4));


    }

    @Test
    public void TestSudokuGridCopier() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid myGrid = new SudokuGrid();
        myGrid = sudokuSolver.FromIntArray(sudokuSolver.input(1));
        SudokuGrid myGrid2 = sudokuSolver.Copy(myGrid);
        assertTrue(myGrid.toString().equals(myGrid2.toString()));
        assertTrue(myGrid.equals(myGrid2));


        myGrid2.getSudokuGrid()[2][3].solve(2);
        myGrid.getSudokuGrid()[2][3].solve(3);
        assertFalse(myGrid.toString().equals(myGrid2.toString()));
        assertFalse(myGrid.equals(myGrid2));

    }

    @Test
    public void CheckAllStoredLogic() {

        boolean solvedAll = true;
        //1 to 23, inclusive
        for (int i = 1; i <= NumStoredSudokus; i++) {
            SudokuSolver sudokuSolver = new SudokuSolver();

            SudokuGrid mySudoku = getInput(i);

            sudokuSolver.Solve(mySudoku, true);

            //if unsolved
            if (!sudokuSolver.IsSolved(mySudoku, false)) {
                solvedAll = false;
                Log.i("Test", "More work on " + i);
                Log.i("Test", "Num unsolved is " + sudokuSolver.numUnsolved(mySudoku) + mySudoku);
            }
            //if there was a duplicate in row, column, or box
            if (sudokuSolver.InvalidMove(mySudoku)) {
                Log.i("Test", "Invalid move on " + i);
            }
        }
        if (solvedAll) {
            Log.i("Test", "All solved");
        }

        assertTrue("Not all solved", solvedAll);
    }

    @Test
    public void CheckIsValidMethod() {
        //1 to 23, inclusive
        //23 broke
        for (int i = 1; i <= 23; i++) {
            SudokuSolver sudokuSolver = new SudokuSolver();
            SudokCell sudokCell = new SudokCell();
            SudokuGrid mySudoku = getInput(i);
            assertTrue(i + " was said to be not valid" + mySudoku, mySudoku.IsValid());

        }

        SudokuGrid notValid1 = new SudokuGrid();
        //my sudoku to be worked with
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                notValid1.getSudokuGrid()[row][column] = new SudokCell();
            }
        }
        notValid1.getSudokuGrid()[1][1].solve(2);
        assertFalse("IsValid said a nonValid Sudoku is valid", notValid1.IsValid());
    }

    @Test
    public void TestBruteForceChecker() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        boolean solvedAll = true;
        String errorMessage = "";
        int numUnsolved = 0;
        boolean invalidMoveMade = false;
        //1 to 23, inclusive
        for (int i = 1; i <= NumStoredSudokus; i++) {

            SudokuGrid mySudoku = getInput(i);

            sudokuSolver.bruteForceSolver(mySudoku);


            //if unsolved
            if (!sudokuSolver.IsSolved(mySudoku, false)) {
                solvedAll = false;
                Log.i("Test", "More work on " + i);
                Log.i("Test", "Num unsolved is " + sudokuSolver.numUnsolved(mySudoku) + mySudoku);
                numUnsolved++;
            }
            //if there was a duplicate in row, column, or box
            if (sudokuSolver.InvalidMove(mySudoku)) {
                Log.i("Test", "Invalid move on " + i);
                invalidMoveMade = true;
            }
        }
        if (solvedAll) {
            Log.i("Test", "All solved");
        }
        if(invalidMoveMade) errorMessage = errorMessage + "Invalid move made ";
        errorMessage = errorMessage + numUnsolved + " unsolved";
        assertTrue(errorMessage, solvedAll);


        //need to check 5, 9, 12, 13
        SudokuGrid mySudoku = new SudokuGrid();
        int[][] sudokuInputted = sudokuSolver.input(5);
        //my sudoku to be worked with
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                mySudoku.getSudokuGrid()[row][column] = new SudokCell(sudokuInputted[row][column]);
            }
        }


        sudokuSolver.bruteForceSolver(mySudoku);
        assertTrue("Brute force did not solve it", sudokuSolver.IsSolved(mySudoku));


    }


    @Test
    public void TestSudokuGridEquals() { //was testSudokuGridCopy
        SudokuGrid myGrid1 = new SudokuGrid();
        myGrid1.getSudokuGrid()[0][0].solve(1);

        SudokuGrid myGrid2 = new SudokuGrid();
        myGrid2.getSudokuGrid()[0][0].solve(1);

        assertTrue(myGrid1.equals(myGrid2));
    }

    @Test
    public void TestInvalidMove(){
        boolean testPassed = true;
        SudokuSolver sudokuSolver = new SudokuSolver();
        for(int i = 1; i <= NumStoredSudokus; i++){

            SudokuGrid mySudoku = getInput(i);
            if(sudokuSolver.InvalidMove(mySudoku)) {
                Log.i("Test", "Invalid move failed on " + i + mySudoku);
                testPassed = false;
            }
        }
        assertTrue(testPassed);
    }

    @Test
    public void TestSolvingMethods(){
        boolean failed = false;
        //1 to 23, inclusive
        for (int i = 1; i <= NumStoredSudokus; i++) {
            SudokuSolver sudokuSolver = new SudokuSolver();
            SudokuGrid mySudoku = getInput(i);

            //Solving methods to be checked for invalid results
            for(int j = 0; j<10; j++) {
                sudokuSolver.RookChecker(mySudoku);
                sudokuSolver.BoxChecker(mySudoku);
                sudokuSolver.OnlyCandidateLeftBoxChecker(mySudoku);
                sudokuSolver.OnlyCandidateLeftRookChecker(mySudoku);
                sudokuSolver.NakedCandidateRookChecker(mySudoku);
                sudokuSolver.NakedCandidateBoxChecker(mySudoku);
                sudokuSolver.CandidateLinesChecker(mySudoku);
                sudokuSolver.HiddenCandidatePairChecker(mySudoku);
                sudokuSolver.pointingPairRookToBoxChecker(mySudoku);
                sudokuSolver.forcingChainsChecker(mySudoku);
            }

            failed = false;
            //if there was a duplicate in row, column, or box
            if (sudokuSolver.InvalidMove(mySudoku)) {
                Log.i("Test", "Solving methods failed on " + i + mySudoku);
                failed = true;
            }
            if(!sudokuSolver.IsSolved(mySudoku)){
                Log.i("Test", "Solving methods couldn't solve " + i);
            }
        }
        assertFalse(failed);
    }

    public SudokuGrid getInput(int i){
        SudokuSolver sudokuSolver = new SudokuSolver();
        //inputted sudoku
        int[][] sudokuInputted = sudokuSolver.input(i);

        SudokuGrid mySudoku = new SudokuGrid();

        //my sudoku to be worked with
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                mySudoku.getSudokuGrid()[row][column] = new SudokCell(sudokuInputted[row][column]);
            }
        }
        return mySudoku;
    }

            /*
    RookChecker(mySudoku);
    BoxChecker(mySudoku);
    OnlyCandidateLeftRookChecker(mySudoku);
    OnlyCandidateLeftBoxChecker(mySudoku);
    NakedCandidateRookChecker(mySudoku);
    NakedCandidateBoxChecker(mySudoku);
    CandidateLinesChecker(mySudoku);
    HiddenCandidatePairChecker(mySudoku);
    pointingPairRookToBoxChecker(mySudoku);

             */
}