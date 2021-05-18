package android.bignerdranch.androidsudoku;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SudokuGenerator {
    private SudokuSolver sudokuSolver;

    public SudokuGenerator() {
        sudokuSolver = new SudokuSolver();
    }

    //rotates a given sudoku 90 degrees clockwise
    public void rotateClockwise(SudokuGrid sudokuGrid) {
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                sudokuGrid.setSudokCell(i, j, copy.getSudokCell(9 - j - 1, i));
            }
        }
    }

    //flips the sudoku around a horizontal midline
    public void flipHorizontal(SudokuGrid sudokuGrid) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                SudokCell temp = sudokuGrid.getSudokCell(row, column);
                sudokuGrid.setSudokCell(row, column, sudokuGrid.getSudokCell(8 - row, column));
                sudokuGrid.setSudokCell(8 - row, column, temp);
            }
        }
    }

    //flips the sudoku around a vertical midline
    public void flipVertical(SudokuGrid sudokuGrid) {
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 9; row++) {
                SudokCell temp = sudokuGrid.getSudokCell(row, column);
                sudokuGrid.setSudokCell(row, column, sudokuGrid.getSudokCell(8 - row, column));
                sudokuGrid.setSudokCell(8 - row, column, temp);
            }
        }
    }

    //swaps two boxColumns, index zero
    public void swapBoxColumns(SudokuGrid sudokuGrid, int boxColumnOne, int boxColumnTwo) {
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (column / 3 == boxColumnOne) {
                    sudokuGrid.setSudokCell(row, column, copy.getSudokCell(row, boxColumnTwo * 3 + column % 3));
                }
                if (column / 3 == boxColumnTwo) {
                    sudokuGrid.setSudokCell(row, column, copy.getSudokCell(row, boxColumnOne * 3 + column % 3));
                }

            }
        }
    }

    //swaps two boxRows, index zero
    public void swapBoxRows(SudokuGrid sudokuGrid, int boxRowOne, int boxRowTwo) {
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);
        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 9; row++) {
                if (row / 3 == boxRowOne) {
                    sudokuGrid.setSudokCell(row, column, copy.getSudokCell(boxRowTwo * 3 + row % 3, column));
                }
                if (row / 3 == boxRowTwo) {
                    sudokuGrid.setSudokCell(row, column, copy.getSudokCell(boxRowOne * 3 + row % 3, column));
                }

            }
        }
    }

    //makes a random solved sudokuGrid
    public SudokuGrid fillEmptyGrid() {
        SudokuGrid sudokuGrid = new SudokuGrid();

        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for(int i = 0; i < 81; i++){
            coordList.add(i);
        }
        Collections.shuffle(coordList);

        for (int i: coordList) {
            try {
                int row = i / 9;
                int column = i % 9;
                int size = sudokuGrid.getSudokCell(row, column).getPossibles().size();
                int index = (int) (Math.random() * (size));
                //set sudok cell to random of possible candidates
                sudokuGrid.getSudokCell(row, column).solve(sudokuGrid.getSudokCell(row, column).getPossibles().get(index));
                sudokuSolver.eliminateCands(sudokuGrid);
            }
            catch (Exception e){
                return fillEmptyGrid();
            }
        }
        if(sudokuGrid.IsValid()){
            return sudokuGrid;
        }
        return fillEmptyGrid();
    }

    //makes a random solved sudokuGrid
    public SudokuGrid makeInitialSudoku() {
        SudokuGrid sudokuGrid = new SudokuGrid();

        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for(int i = 0; i < 81; i++){
            coordList.add(i);
        }
        Collections.shuffle(coordList);

        for (int i: coordList) {
            try {
                int row = i / 9;
                int column = i % 9;
                int size = sudokuGrid.getSudokCell(row, column).getPossibles().size();
                int index = (int) (Math.random() * (size));
                //set sudok cell to random of possible candidates
                sudokuGrid.getSudokCell(row, column).solve(sudokuGrid.getSudokCell(row, column).getPossibles().get(index));
                sudokuSolver.eliminateCands(sudokuGrid);
                if(sudokuGrid.IsValid()){
                    return sudokuGrid;
                }
            }
            catch (Exception e){
                return makeInitialSudoku();
            }
        }
        if(sudokuGrid.IsValid()){
            return sudokuGrid;
        }
        return makeInitialSudoku();
    }

    public SudokuGrid generateDifficulty(int difficulty) {
        Log.i("GenerateDifficulty", "is called");
        SudokuGrid mySudoku = new SudokuGrid();

        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for(int i = 0; i < 81; i++){
            coordList.add(i);
        }
        Collections.shuffle(coordList);

        for (int i: coordList) {
            try {
                SudokuGrid copy = sudokuSolver.Copy(mySudoku);
                int row = i / 9;
                int column = i % 9;
                int size = mySudoku.getSudokCell(row, column).getPossibles().size();
                int index = (int) (Math.random() * (size));
                //set sudok cell to random of possible candidates
                mySudoku.getSudokCell(row, column).solve(mySudoku.getSudokCell(row, column).getPossibles().get(index));

                if(mySudoku.IsValid() && sudokuSolver.RateDifficulty(mySudoku) == difficulty){
                    return mySudoku;
                }
                //Todo: need to only use solving methods that won't solve the sudoku too much
                //Note: when using difficulty based on num unsolved, cuts runtime to 10% when using .Solve instead of .eliminate cands
                //idea: even using eliminate cands, don't actually solve any cells. leave all cell solving to this algorithm.
                sudokuSolver.eliminateCands(mySudoku);
            }
            catch (Exception e){
                Log.i("GenerateDifficulty", "Caught exception");

                return generateDifficulty(difficulty);
            }
        }

        if(mySudoku.IsValid()){
            Log.i("GenerateDifficulty", "Filled grid valid");
//Todo: put back into one if statement
            if(sudokuSolver.RateDifficulty(mySudoku) == difficulty){

                return mySudoku;
            }else                 Log.i("GenerateDifficulty", "Filled grid without getting right difficulty " + mySudoku);

        }else             Log.i("GenerateDifficulty", "Filled grid invalid " + mySudoku);

        //Log.i("GenerateDifficulty", "Filled grid without getting right difficulty " + sudokuGrid);

        return generateDifficulty(difficulty);

    }

    public int difficultyBasedOnNumSolved(SudokuGrid mySudoku){
        SudokuSolver sudokuSolver = new SudokuSolver();
        return (sudokuSolver.numUnsolved(mySudoku)) / 12;
    }
}
