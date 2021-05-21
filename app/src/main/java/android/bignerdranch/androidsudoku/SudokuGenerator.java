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

                //Todo: comment these lines out
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        if (sudokuGrid.getSudokCell(r, c).isSolved()) {
                            if (sudokuGrid.getSudokCell(r, c).getVal() != copy.getSudokCell(r, c).getVal()) {
                                throw new IllegalArgumentException("blah");
                            }
                        }
                    }
                }
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
            }
            else{
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
                for(int sol: solutionNumber){
                    for(int coordNum : coordList){
                        int r = coordNum / 9;
                        int c = coordNum % 9;
                        //if first solution doesn't equal current solution at r, c
                        if(allSolutions.size() != 0) {
                            if (!firstSolution.getSudokCell(r, c).equals(allSolutions.get(sol).getSudokCell(r, c))) {
                                mySudoku.getSudokuGrid()[r][c].solve(firstSolution.getSudokCell(r, c).getVal());
                                copy.getSudokuGrid()[r][c].solve(firstSolution.getSudokCell(r, c).getVal());
                                break;
                            }
                        }
                        else{
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
}
