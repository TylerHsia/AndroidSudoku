package android.bignerdranch.androidsudoku;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SudokuGrid{
    //sudokCell[,] grid;
    //public SudokuGrid()
    //{
    //    grid = new sudokCell[9, 9];
    //    for (int row = 0; row < 9; row++)
    //    {
    //        for (int column = 0; column < 9; column++)
    //        {
    //            grid[row, column] = new sudokCell();
    //        }
    //    }


    //}



    private SudokCell[][] cells;
    public SudokCell[][] getSudokuGrid(){
        return cells;
    }


    private SudokCell sudokCell;
    public SudokCell getSudokCell(int row, int column){
        return cells[row][column];
    }
    public void setSudokCell(int row, int column, SudokCell s){
        cells[row][column] = s;
    }


    public SudokuGrid()
    {
        cells = new SudokCell[9][9];
        for(int row = 0; row < 9; row++)
        {
            for(int column = 0; column < 9; column++)
            {
                cells[row][column] = new SudokCell();
            }
        }
    }






    public String ToString()
    {
        StringBuilder sb = new StringBuilder();
        //Todo: check \n escape character
        sb.append("\n");
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                if (false)
                {
                    sb.append(cells[row][column].ToString());
                }
                else
                {
                    sb.append(cells[row][column].toStringWithoutCands());
                    //sb.Append("0");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /// <summary>
    /// Checks whether a given SudokuGrid is valid
    /// </summary>
    /// <returns></returns>
    public boolean IsValid()
    {
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid mySudoku = sudokuSolver.Copy(this);

        if (sudokuSolver.IsSolved(this))
        {
            return true;
        }


        if(this.NumSolved() < 16)
        {
            return false;
        }

        mySudoku.SolveForIsValid();
        //if simple solve, return is valid
        if (sudokuSolver.IsSolved(mySudoku))
        {
            return true;
        }

        boolean fsolvedOne = false;

        SudokuGrid copy1 = sudokuSolver.Copy(this);
        //Todo: pass by referenece sudokuSolver.bruteForceSolver(ref copy1);
        sudokuSolver.bruteForceSolver(copy1);

        if (!sudokuSolver.IsSolved(copy1))
        {
            return false;
        }

        if (sudokuSolver.InvalidMove(this))
        {
            return false;
        }

        SudokuGrid firstSolve = new SudokuGrid();
        firstSolve = sudokuSolver.Copy(copy1);

        //else, guess all possibles and brute force solve. if multiple solutions, return false
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if unsolved
                if (!cells[row][column].getSolved())
                {

                    for(int i = 0; i < cells[row][column].getPossibles().size(); i++)
                    {
                        SudokuGrid copy = sudokuSolver.Copy(this);
                        //solve to the index of the guess
                        copy.getSudokuGrid()[row][column].solve(copy.getSudokCell(row, column).getPossibles().get(i));



                        boolean solvedThisOne;



                        try
                        {
                            //brute force it
                            sudokuSolver.bruteForceSolver(copy);
                            solvedThisOne = sudokuSolver.IsSolved(copy);
                        }
                        catch(Exception e)
                        {
                            solvedThisOne = false;
                        }

                        //if this one and another different one were solved, invalid for too many solutions
                        if (solvedThisOne)
                        {
                            try
                            {
                                if (!firstSolve.ToString().equals(copy.ToString()))
                                {
                                    //return false;
                                }
                                if (!sudokuSolver.equals(firstSolve, copy))
                                {
                                    return false;
                                }
                            }
                            catch(Exception e)
                            {

                            }
                        }
                        if (solvedThisOne)
                        {
                            firstSolve = sudokuSolver.Copy(copy);
                        }
                    }
                }
            }
        }
        return true;

    }

    public boolean IsSolved()
    {
        SudokuSolver sudokuSolver = new SudokuSolver();
        //checks each box is solved
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if unsolved, return false
                if (!cells[row][column].getSolved())
                {
                    return false;
                }
            }
        }

        //two for loops to go through each row, check no duplicates
        for (int row = 0; row < 9; row++)
        {
            ArrayList myList = new ArrayList<Integer>();
            int numTotal = 0;
            for (int column = 0; column < 9; column++)
            {
                myList.add(cells[row][column].getVal());
            }
            if (sudokuSolver.ContainsDuplicate(myList))
            {
                return false;
            }
        }

        //if(printChecks) System.out.println("Rows add up");
        //two for loops to go through each column, check no duplicates
        for (int column = 0; column < 9; column++)
        {
            ArrayList myList = new ArrayList<Integer>();
            int numTotal = 0;
            for (int row = 0; row < 9; row++)
            {
                myList.add(cells[row][column].getVal());
            }
            if (sudokuSolver.ContainsDuplicate(myList))
            {
                return false;
            }
        }
        //if(printChecks) System.out.println("Columns add up");

        //check each box, check no duplicates
        //for each box row
        for (int boxRow = 0; boxRow < 3; boxRow++)
        {
            //for each box column
            for (int boxColumn = 0; boxColumn < 3; boxColumn++)
            {
                ArrayList mylist = new ArrayList<Integer>();
                int numTotal = 0;
                //for each row in the small box
                for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                {
                    //for each column in the small box
                    for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                    {
                        myList.add(cells[row2][column2].getVal());
                    }
                }
                if (sudokuSolver.ContainsDuplicate(myList))
                {
                    return false;
                }
            }
        }
        //if(printChecks) System.out.println("Boxes add up");
        return true;
    }

    public boolean Equals(SudokuGrid obj)
    {
        for(int row = 0; row < 9; row++)
        {
            for(int column = 0; column < 9; column++)
            {
                if(!cells[row][column].equals(obj.getSudokuGrid()[row][column]))
                {
                    return false;
                }
            }
        }
        return true;
    }
    private int NumSolved()
    {
        int numSolved = 0;

        for(int row = 0; row < 9; row++)
        {
            for(int column = 0; column < 9; column++)
            {
                if(cells[row][column].getSolved())
                {
                    numSolved++;
                }
            }
        }
        return numSolved;
    }

    public void SolveForIsValid()
    {
        SudokuSolver sudokuSolver = new SudokuSolver();
        for (int i = 0; i < 10; i++)
        {
            sudokuSolver.RookChecker(this);
            sudokuSolver.BoxChecker(this);
            sudokuSolver.OnlyCandidateLeftRookChecker(this);
            sudokuSolver.OnlyCandidateLeftBoxChecker(this);
            sudokuSolver.NakedCandidateRookChecker(this);
            sudokuSolver.NakedCandidateBoxChecker(this);
            sudokuSolver.CandidateLinesChecker(this);
        }


    }
}
