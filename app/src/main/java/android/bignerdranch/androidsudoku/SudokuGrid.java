package android.bignerdranch.androidsudoku;

public class SudokuGrid {
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



    public SudokCell[,] cells;


    public SudokCell this[int row, int col]
    {
        get
        {
            return cells[row, col];
        }
        set
        {
            cells[row, col] = value;
        }
    }

    public SudokuGrid()
    {
        cells = new SudokCell[9, 9];
        for(int row = 0; row < 9; row++)
        {
            for(int column = 0; column < 9; column++)
            {
                this[row, column] = new SudokCell();
            }
        }
    }






    public override string ToString()
    {
        var sb = new StringBuilder();
        sb.AppendLine();
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                if (false)
                {
                    sb.Append(this[row, column].ToString());
                }
                else
                {
                    sb.Append(this[row, column].toStringWithoutCands());
                    //sb.Append("0");
                }
            }
            sb.AppendLine();
        }
        return sb.ToString();
    }

    /// <summary>
    /// Checks whether a given SudokuGrid is valid
    /// </summary>
    /// <returns></returns>
    public bool IsValid()
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

        bool solvedOne = false;

        SudokuGrid copy1 = sudokuSolver.Copy(this);
        sudokuSolver.bruteForceSolver(ref copy1);

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
                if (!this[row, column].getSolved())
                {

                    for(int i = 0; i < this[row, column].getPossibles().Count; i++)
                    {
                        SudokuGrid copy = sudokuSolver.Copy(this);
                        //solve to the index of the guess
                        copy[row, column].solve(copy[row, column].getPossibles()[i]);



                        bool solvedThisOne;



                        try
                        {
                            //brute force it
                            sudokuSolver.bruteForceSolver(ref copy);
                            solvedThisOne = sudokuSolver.IsSolved(copy);
                        }
                        catch
                        {
                            solvedThisOne = false;
                        }

                        //if this one and another different one were solved, invalid for too many solutions
                        if (solvedThisOne)
                        {
                            try
                            {
                                if (!firstSolve.ToString().Equals(copy.ToString()))
                                {
                                    //return false;
                                }
                                if (!sudokuSolver.Equals(firstSolve, copy))
                                {
                                    return false;
                                }
                            }
                            catch
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

    public bool IsSolved()
    {
        SudokuSolver sudokuSolver = new SudokuSolver();
        //checks each box is solved
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if unsolved, return false
                if (!this[row, column].getSolved())
                {
                    return false;
                }
            }
        }

        //two for loops to go through each row, check no duplicates
        for (int row = 0; row < 9; row++)
        {
            var myList = new List<int>();
            int numTotal = 0;
            for (int column = 0; column < 9; column++)
            {
                myList.Add(this[row, column].getVal());
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
            var myList = new List<int>();
            int numTotal = 0;
            for (int row = 0; row < 9; row++)
            {
                myList.Add(this[row, column].getVal());
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
                var myList = new List<int>();
                int numTotal = 0;
                //for each row in the small box
                for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                {
                    //for each column in the small box
                    for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                    {
                        myList.Add(this[row2, column2].getVal());
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

    public bool Equals(SudokuGrid obj)
    {
        for(int row = 0; row < 9; row++)
        {
            for(int column = 0; column < 9; column++)
            {
                if(!this[row, column].Equals(obj[row, column]))
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
                if(this[row, column].getSolved())
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
