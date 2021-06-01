package android.bignerdranch.androidsudoku;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

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

    //reflect a given sudoku through the origin
    public void reflectOrigin(SudokuGrid mySudoku){
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                mySudoku.getSudokuGrid()[r][c] = copy.getSudokuGrid()[8-r][8-c];
            }
        }
    }

    //reflect a given sudoku through diagonal bottom left to top right
    public void reflectBottomTopDiagonal(SudokuGrid mySudoku){
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                mySudoku.getSudokuGrid()[r][c] = copy.getSudokuGrid()[8 - c][8 - r];
            }
        }
    }

    //reflect a given sudoku through diagonal top left to bottom right
    public void reflectTopBottomDiagonal(SudokuGrid mySudoku){
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                mySudoku.getSudokuGrid()[r][c] = copy.getSudokuGrid()[c][r];
            }
        }
    }

    //changes all of each number to be entirely a new number. Ex, changes all 9s to be 2s
    public void changeNumbers(SudokuGrid mySudoku){
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
        ArrayList<Integer> numList = new ArrayList<>();
        Collections.addAll(numList, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(numList);
        //for each int i in the original sudoku, replace with the element at index i in numList
        for(int i = 1; i <= 9; i++){
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    if(copy.getSudokuGrid()[r][c].getVal() == i) {
                        mySudoku.getSudokuGrid()[r][c] = new SudokCell(numList.get(i - 1));
                    }
                }
            }
        }
    }


    //makes a random solved sudokuGrid
    public SudokuGrid fillEmptyGrid() {
        SudokuGrid sudokuGrid = new SudokuGrid();

        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++) {
            coordList.add(i);
        }
        Collections.shuffle(coordList);

        for (int i : coordList) {
            try {
                int row = i / 9;
                int column = i % 9;
                int size = sudokuGrid.getSudokCell(row, column).getPossibles().size();
                int index = (int) (Math.random() * (size));
                //set sudok cell to random of possible candidates
                sudokuGrid.getSudokCell(row, column).solve(sudokuGrid.getSudokCell(row, column).getPossibles().get(index));

                sudokuSolver.eliminateCands(sudokuGrid);
            } catch (Exception e) {
                return fillEmptyGrid();
            }
        }
        if (sudokuGrid.IsValid()) {
            return sudokuGrid;
        }
        return fillEmptyGrid();
    }

    //makes a random solved sudokuGrid
    public SudokuGrid makeInitialSudoku() {
        SudokuGrid sudokuGrid = new SudokuGrid();
        SudokuGrid copy = sudokuSolver.Copy(sudokuGrid);

        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++) {
            coordList.add(i);
        }
        Collections.shuffle(coordList);

        for (int i : coordList) {
            try {
                int row = i / 9;
                int column = i % 9;
                int size = copy.getSudokCell(row, column).getPossibles().size();
                int index = (int) (Math.random() * (size));
                //set sudok cell to random of possible candidates
                sudokuGrid.getSudokCell(row, column).solve(copy.getSudokCell(row, column).getPossibles().get(index));
                copy.getSudokCell(row, column).solve(copy.getSudokCell(row, column).getPossibles().get(index));

                /*
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        if (sudokuGrid.getSudokCell(r, c).isSolved()) {
                            if (sudokuGrid.getSudokCell(r, c).getVal() != copy.getSudokCell(r, c).getVal()) {
                                throw new IllegalArgumentException("blah");
                            }
                        }
                    }
                }

                 */
                sudokuSolver.Solve(copy, false);
                if (sudokuGrid.IsValid()) {
                    return sudokuGrid;
                }
                //was invalid move sudoku grid
                if (sudokuSolver.InvalidMove(copy)) {
                    return makeInitialSudoku();
                }
            }
            //Todo: do i need this catch, revert back to normal
            catch (IllegalStateException e) {
                return makeInitialSudoku();
            }
        }
        if (sudokuGrid.IsValid()) {
            return sudokuGrid;
        }
        return makeInitialSudoku();
    }

    //fills in cells one at a time until the desired difficulty is achieved
    public SudokuGrid generateDifficulty(int difficulty) {
        Log.i("GenerateDifficulty", "is called");
        SudokuGrid mySudoku = new SudokuGrid();

        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++) {
            coordList.add(i);
        }
        Collections.shuffle(coordList);

        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
        for (int j = 0; j < coordList.size(); j++) {

            int i = coordList.get(j);
            int row = i / 9;
            int column = i % 9;


            //if many solutions, make random guess of possibles
            if (j < 17 || copy.IsSolved()) {
                try {
                    int size = copy.getSudokCell(row, column).getPossibles().size();
                    int index = (int) (Math.random() * (size));
                    //set sudok cell to random of possible candidates
                    mySudoku.getSudokCell(row, column).solve(copy.getSudokCell(row, column).getPossibles().get(index));
                    copy.setSudokCell(row, column, new SudokCell(copy.getSudokCell(row, column).getPossibles().get(index)));
                } catch (Exception e) {
                    Log.i("GenerateDifficulty", "Caught exception");

                    return generateDifficulty(difficulty);
                }
            } else {
                //only guess variances in allSolutions
                ArrayList<SudokuGrid> allSolutions = allSolutions(copy);

                //randonmly find variance in all solutions
                ArrayList<Integer> solutionNumber = new ArrayList<Integer>();
                for (int k = 0; k < allSolutions.size(); k++) {
                    solutionNumber.add(k);
                }
                Collections.shuffle(coordList);
                ArrayList<Integer> coordListTwo = new ArrayList<Integer>();
                for (int x = 0; x < 81; x++) {
                    coordListTwo.add(x);
                }
                Collections.shuffle(coordListTwo);
                //get first solution
                SudokuGrid firstSolution = allSolutions.get(solutionNumber.get(0));
                //compare to all others
                allSolutions.remove(0);
                for (int sol : solutionNumber) {
                    for (int coordNum : coordList) {
                        int r = coordNum / 9;
                        int c = coordNum % 9;
                        //if first solution doesn't equal current solution at r, c
                        if (allSolutions.size() != 0) {
                            if (!firstSolution.getSudokCell(r, c).equals(allSolutions.get(sol).getSudokCell(r, c))) {
                                mySudoku.getSudokuGrid()[r][c].solve(firstSolution.getSudokCell(r, c).getVal());
                                copy.getSudokuGrid()[r][c].solve(firstSolution.getSudokCell(r, c).getVal());
                                break;
                            }
                        } else {
                            return generateDifficulty(difficulty);
                        }
                    }
                    break;
                }


            }

            if (mySudoku.IsValid() && sudokuSolver.RateDifficulty(mySudoku) == difficulty) {
                return mySudoku;
            }
            sudokuSolver.Solve(copy, false);
        }


        if (mySudoku.IsValid()) {
            Log.i("GenerateDifficulty", "Filled grid valid");
            //Todo: put back into one if statement
            if (sudokuSolver.RateDifficulty(mySudoku) == difficulty) {

                return mySudoku;
            } else
                Log.i("GenerateDifficulty", "Filled grid without getting right difficulty " + mySudoku);

        } else Log.i("GenerateDifficulty", "Filled grid invalid " + mySudoku);

        //Log.i("GenerateDifficulty", "Filled grid without getting right difficulty " + sudokuGrid);

        return generateDifficulty(difficulty);

    }

    //returns true if was succesful
    //takes a given inputted sudoku and tries to modify it to become a certain difficulty
    public boolean modifyDifficuly(SudokuGrid mySudoku, int difficulty) {
        int initialDifficulty = sudokuSolver.RateDifficulty(mySudoku);
        boolean modificationsSuccessful = true;
        while (modificationsSuccessful) {
            int currentDifficulty = sudokuSolver.RateDifficulty(mySudoku);
            Log.i("Modify difficutly", "Current difficulty: " + currentDifficulty + mySudoku + "\n num unsolved: " +sudokuSolver.numUnsolved(mySudoku));
            //if too easy
            if (currentDifficulty < difficulty) {
                modificationsSuccessful = false;
                //Todo: change addOneRemoveTwo to do all possible combinations, rather than loop many times
                for (int i = 0; i < 200; i++) {
                    if (addOneRemoveTwo(mySudoku)) {
                        modificationsSuccessful = true;
                        i = 1000;
                    }
                }
                if (!modificationsSuccessful) {
                    modificationsSuccessful = removeOneGiven(mySudoku);

                }
            }
            //if too hard
            else if (currentDifficulty > difficulty) {
                modificationsSuccessful = false;
                for (int i = 0; i < 1; i++) {
                    if (addOneEasier(mySudoku)) {
                        modificationsSuccessful = true;
                    }
                }
                if (!modificationsSuccessful) {
                    addOneGiven(mySudoku);
                    modificationsSuccessful = true;
                }

            }
            //right difficulty
            else {
                modificationsSuccessful = false;
            }
        }

        if (sudokuSolver.RateDifficulty(mySudoku) == difficulty) {
            return true;
        }
        return false;
    }


    //given a partially solved (valid) sudoku grid, randomly tries to remove two givens and add one and result in a valid sudoku
    //returns true if it modified mySudoku, false otherwise
    public boolean addOneRemoveTwo(SudokuGrid mySudoku) {
        SudokuGrid copy = sudokuSolver.Copy(mySudoku);

        SudokuGrid solved = sudokuSolver.Copy(copy);
        sudokuSolver.bruteForceSolver(solved);

        //make a random list of the coordinates of all solved cells and one of unsolved
        ArrayList<Integer> solvedCoordList = new ArrayList<Integer>();
        ArrayList<Integer> unsolvedCoordList = new ArrayList<Integer>();

        for (int i = 0; i < 81; i++) {
            if (copy.getSudokCell(i / 9, i % 9).isSolved()) {
                solvedCoordList.add(i);
            } else {
                unsolvedCoordList.add(i);
            }
        }
        Collections.shuffle(solvedCoordList);
        Collections.shuffle(unsolvedCoordList);


        //remove two of those solved cells
        for (int i = 0; i <= 1; i++) {
            copy.getSudokuGrid()[solvedCoordList.get(i) / 9][solvedCoordList.get(i) % 9] = new SudokCell();
        }

        //add one
        //if original sudoku wasn't solved
        if (unsolvedCoordList.size() != 0) {
            copy.getSudokuGrid()[unsolvedCoordList.get(0) / 9][unsolvedCoordList.get(0) % 9].solve(solved.getSudokCell(unsolvedCoordList.get(0) / 9, unsolvedCoordList.get(0) % 9).getVal());
        } /*else {
            copy.getSudokuGrid()[solvedCoordList.get(2) / 9][solvedCoordList.get(2) % 9].solve(solved.getSudokCell(solvedCoordList.get(0) / 9, solvedCoordList.get(0) % 9).getVal());

        }
        */

        if (copy.IsValid()) {
            sudokuSolver.set(mySudoku, copy);
            return true;
        }
        return false;
    }

    //adds one given
    public void addOneGiven(SudokuGrid mySudoku) {
        SudokuGrid solved = sudokuSolver.Copy(mySudoku);
        sudokuSolver.bruteForceSolver(solved);
        //for each unsolved cell in my sudoku
        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for (int x = 0; x < 81; x++) {
            if (!mySudoku.getSudokCell(x / 9, x % 9).isSolved()) {
                coordList.add(x);
            }
        }
        Collections.shuffle(coordList);
        int coordNum = coordList.get(0);
        int r = coordNum / 9;
        int c = coordNum % 9;
        mySudoku.getSudokCell(r, c).solve(solved.getSudokCell(r, c).getVal());
    }

    //removes one given, returns true if was able to do so and make valid sudoku
    public boolean removeOneGiven(SudokuGrid mySudoku) {
        //made coordList of all solved cells
        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for (int x = 0; x < 81; x++) {
            if (mySudoku.getSudokCell(x / 9, x % 9).isSolved()) {
                coordList.add(x);
            }
        }
        Collections.shuffle(coordList);

        //remove one at a time in random order until the sudoku was valid
        for (int coordNum : coordList) {
            int r = coordNum / 9;
            int c = coordNum % 9;
            SudokuGrid copy = sudokuSolver.Copy(mySudoku);
            copy.getSudokuGrid()[r][c] = new SudokCell();
            if (copy.IsValid()) {
                sudokuSolver.set(mySudoku, copy);
                return true;
            }
        }
        return false;
    }

    //adds one given which makes a valid sudoku easier
    //returns true if successful
    public boolean addOneEasier(SudokuGrid mySudoku) {
        int initialDifficulty = sudokuSolver.RateDifficulty(mySudoku);
        SudokuGrid solved = sudokuSolver.Copy(mySudoku);
        sudokuSolver.bruteForceSolver(solved);

        //for each unsolved cell in my sudoku
        ArrayList<Integer> coordList = new ArrayList<Integer>();
        for (int x = 0; x < 81; x++) {
            if (!mySudoku.getSudokCell(x / 9, x % 9).isSolved()) {
                coordList.add(x);
            }
        }
        Collections.shuffle(coordList);
        for (int coordNum : coordList) {
            int r = coordNum / 9;
            int c = coordNum % 9;
            SudokuGrid copy = sudokuSolver.Copy(mySudoku);
            copy.getSudokCell(r, c).solve(solved.getSudokCell(r, c).getVal());
            if (sudokuSolver.RateDifficulty(copy) < initialDifficulty) {
                sudokuSolver.set(mySudoku, copy);
                return true;
            }
        }
        return false;
    }


    public int difficultyBasedOnNumSolved(SudokuGrid mySudoku) {
        SudokuSolver sudokuSolver = new SudokuSolver();
        return (sudokuSolver.numUnsolved(mySudoku)) / 12;
    }

    //returns all solutions to an inputted sudoku
    public ArrayList<SudokuGrid> allSolutions(SudokuGrid mySudoku) {

        //Todo: use a set instead of array list
        Log.i("GenerateDifficulty", "Called allSolutions" + mySudoku + "\n" + sudokuSolver.numUnsolved(mySudoku));

        ArrayList<SudokuGrid> solvedSudokus = new ArrayList<>();
        sudokuSolver.Solve(mySudoku, false);
        if (mySudoku.IsSolved()) {
            solvedSudokus.add(mySudoku);
            return solvedSudokus;
        }
        /* new way
        //meant to find only unique solutions by recuring only on different guesses
        //find first unsolved cell, make guesses, recur
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if unsolved
                if (!mySudoku.getSudokuGrid()[row][column].isSolved()) {

                    for (int i = 0; i < mySudoku.getSudokuGrid()[row][column].getPossibles().size(); i++) {
                        //make a guess
                        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
                        copy.setSudokCell(row, column, mySudoku.getSudokuGrid()[row][column].getPossibles().get(i));
                        sudokuSolver.Solve(copy, false);
                        if (copy.IsSolved()) {
                            solvedSudokus.add(copy);
                        } else {
                            solvedSudokus.addAll(allSolutions(copy));
                        }
                    }
                    Log.i("GenerateDifficulty", "Return solved sudokus" + solvedSudokus.size());

                    return solvedSudokus;

                }
            }
        }


         */

        //old inefficient way
        //if still not solved yet, see if there are multiple solutions
        SudokuGrid firstSolve = new SudokuGrid();
        firstSolve = sudokuSolver.Copy(mySudoku);
        sudokuSolver.bruteForceSolver(firstSolve);
        solvedSudokus.add(firstSolve);


        //else, guess all possibles and brute force solve. if multiple solutions, return false
        for (int row = 0; row < 9; row++) {
            //Log.i("GenerateDifficulty", "row " + row);


            for (int column = 0; column < 9; column++) {
                //if unsolved
                if (!mySudoku.getSudokuGrid()[row][column].isSolved()) {

                    for (int i = 0; i < mySudoku.getSudokuGrid()[row][column].getPossibles().size(); i++) {
                        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
                        //solve to the index of the guess
                        copy.getSudokuGrid()[row][column].solve(copy.getSudokCell(row, column).getPossibles().get(i));


                        boolean solvedThisOne;


                        try {
                            //brute force it
                            sudokuSolver.bruteForceSolver(copy);
                            solvedThisOne = sudokuSolver.IsSolved(copy);
                        } catch (Exception e) {
                            solvedThisOne = false;
                        }

                        //if this one and another different one were solved, invalid for too many solutions
                        if (solvedThisOne) {
                            try {

                                if (!sudokuSolver.equals(firstSolve, copy)) {
                                    solvedSudokus.add(copy);
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (solvedThisOne) {
                            firstSolve = sudokuSolver.Copy(copy);
                        }
                    }
                }
            }
        }
        return solvedSudokus;
    }



    public void perturb(SudokuGrid mySudoku) {
        changeNumbers(mySudoku);
        Random random = new Random();

        //50% chance
        if(random.nextBoolean()){
            reflectTopBottomDiagonal(mySudoku);
        }
        //50% chance
        if(random.nextBoolean()){
            reflectBottomTopDiagonal(mySudoku);
        }
        //50% chance
        if(random.nextBoolean()){
            reflectOrigin(mySudoku);
        }
        //50% chance
        if(random.nextBoolean()){
            flipVertical(mySudoku);
        }
        //50% chance
        if(random.nextBoolean()){
            flipHorizontal(mySudoku);
        }
        //random rotation
        int numRotations = (int) (Math.random() * 4);
        for(int i = 0; i < numRotations; i++){
            rotateClockwise(mySudoku);
        }

        //random boxrow swaps a random number of times
        int numBoxRowSwaps = (int) (Math.random() * 4);
        for(int i = 0; i < numBoxRowSwaps; i++){
            swapBoxRows(mySudoku, (int) (Math.random() * 3), (int) (Math.random() * 3));
        }

        //random boxColumn swaps a random number of times
        int numBoxColumnSwaps = (int) (Math.random() * 4);
        for(int i = 0; i < numBoxColumnSwaps; i++){
            swapBoxColumns(mySudoku, (int) (Math.random() * 3), (int) (Math.random() * 3));
        }
    }
}
