package android.bignerdranch.androidsudoku;

import java.util.ArrayList;
//Todo: CoordinateList is here, needed?
/*
public class CoordinateList
{
    private ArrayList<Coordinate> myList;
}*/

public class SudokuHinter
{
    public Hint GetHint(SudokuGrid mySudoku)
    {
        SudokuSolver sudokuSolver = new SudokuSolver();
        Hint myHint = new Hint();

        SudokuGrid Copy = sudokuSolver.Copy(mySudoku);
        //if invalid
        if (!Copy.IsValid())
        {
            myHint.Text = "This Sudoku is invalid";
            return myHint;
        }
        //generate cands
        myHint = RookHinter(mySudoku);
        if (!myHint.VoidCoord())
        {
            return myHint;
        }

        myHint = BoxHinter(mySudoku);
        if (!myHint.VoidCoord())
        {
            return myHint;
        }

        myHint.Text = "no hint found due to solver limitations, remainder solved with brute force";
        return myHint;
    }






    //eliminates by rook method
    public Hint RookHinter(SudokuGrid mySudoku)
    {
        Hint returnHint = new Hint();

        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if that element is solved
                if (mySudoku[row, column].getSolved())
                {
                    //for each other element in the row
                    for (int row2 = 0; row2 < 9; row2++)
                    {
                        //if the other element is not solved
                        if (!mySudoku[row2, column].getSolved())
                        {
                            //index is index of the solved value in not solved element
                            int index = mySudoku[row2, column].indexOf(mySudoku[row, column].getVal());

                            //if not solved element has solved value
                            if (index != -1)
                            {
                                mySudoku[row2, column].RemoveAt(index);
                                if (mySudoku[row2, column].getSolved())
                                {
                                    returnHint = new Hint(new Coordinate(row2, column));
                                    returnHint.Text = "Naked single at " + new Coordinate(row2, column);
                                    return returnHint;
                                }
                                //checkerMethodOneWorks = true;
                                //checkerMethodOneWorks =
                                //RookHinter(mySudoku);
                                //checkerMethodOneWorks =
                                //BoxHinter(mySudoku);
                            }
                        }
                    }
                    //for each other element in the column
                    for (int column2 = 0; column2 < 9; column2++)
                    {
                        //if the other element is not solved
                        if (!mySudoku[row, column2].getSolved())
                        {
                            //index is index of the solved value in not solved element
                            int index = mySudoku[row, column2].indexOf(mySudoku[row, column].getVal());

                            //if not solved element has solved value
                            if (index != -1)
                            {
                                mySudoku[row, column2].RemoveAt(index);
                                if (mySudoku[row, column2].getSolved())
                                {
                                    returnHint = new Hint(new Coordinate(row, column2));
                                    returnHint.Text = "Naked single at " + new Coordinate(row, column2);
                                    return returnHint;
                                }

                                //checkerMethodOneWorks = true;
                                //RookHinterWorks =
                                //returnHint = RookHinter(mySudoku);
                                //RookHinterWorks =
                                //returnHint = BoxHinter(mySudoku);
                            }
                        }
                    }
                }
            }
        }
        return new Hint();
    }

    //eliminates by checking 3 by 3 boxes
    public Hint BoxHinter(SudokuGrid mySudoku)
    {
        bool boxCheckerWorks = false;
        Hint returnHint = new Hint();

        //for each solved cell in main array
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if that element is solved
                if (mySudoku[row, column].getSolved())
                {
                    int boxColumn = column / 3;
                    int boxRow = row / 3;

                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                    {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                        {
                            //if the other element is not solved
                            if (!mySudoku[row2, column2].getSolved())
                            {
                                //index is index of the solved value in not solved element
                                int index = mySudoku[row2, column2].indexOf(mySudoku[row, column].getVal());

                                if (index != -1)
                                {
                                    mySudoku[row2, column2].RemoveAt(index);
                                    if (mySudoku[row, column2].getSolved())
                                    {
                                        returnHint = new Hint(new Coordinate(row, column2));
                                        returnHint.Text = "Naked single at " + new Coordinate(row, column2);
                                        return returnHint;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnHint;
    }

    //check if candidate is only candidate in one spot in a row or column
    public bool OnlyCandidateLeftRookHinter(SudokuGrid mySudoku)
    {
        bool onlyCandidateLeftRookHinterWorks = false;
        //check each column
        for (int column = 0; column < 9; column++)
        {
            //for each possible integer
            for (int i = 1; i <= 9; i++)
            {
                int num = 0;
                int index = -1;
                //for each row
                for (int row = 0; row < 9; row++)
                {
                    //if not solved
                    if (!mySudoku[row, column].getSolved())
                    {

                        if (mySudoku[row, column].contains(i))
                        {
                            num++;
                            index = row;
                        }
                    }
                }
                if (num == 1)
                {
                    mySudoku[index, column].solve(i);
                    RookHinter(mySudoku);
                    BoxHinter(mySudoku);
                    onlyCandidateLeftRookHinterWorks = true;
                    //onlyCandidateLeftRookHinterWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                }
            }
        }

        //check each row
        for (int row = 0; row < 9; row++)
        {
            //for each possible integer
            for (int i = 1; i <= 9; i++)
            {
                int num = 0;
                int index = -1;
                //for each column
                for (int column = 0; column < 9; column++)
                {
                    //if not solved
                    if (!mySudoku[row, column].getSolved())
                    {
                        if (mySudoku[row, column].contains(i))
                        {
                            num++;
                            index = column;
                        }
                    }
                }
                if (num == 1)
                {
                    mySudoku[row, index].solve(i);
                    onlyCandidateLeftRookHinterWorks = true;
                    RookHinter(mySudoku);
                    BoxHinter(mySudoku);
                    //onlyCandidateLeftRookHinterWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                }
            }
        }
        return onlyCandidateLeftRookHinterWorks;
    }



    //check if candidate is only candidate in one spot in a box
    public bool OnlyCandidateLeftBoxChecker(SudokuGrid mySudoku)
    {
        bool OnlyCandidateLeftBoxCheckerWorks = false;
        //for each box row
        for (int boxRow = 0; boxRow < 3; boxRow++)
        {
            //for each box column
            for (int boxColumn = 0; boxColumn < 3; boxColumn++)
            {
                //for each integer possible
                for (int i = 1; i <= 9; i++)
                {
                    int num = 0;
                    int rowIndex = -1;
                    int columnIndex = -1;
                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                    {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                        {
                            //if the element is not solved
                            if (!mySudoku[row2, column2].getSolved())
                            {
                                //if it contains i
                                if (mySudoku[row2, column2].contains(i))
                                {
                                    num++;
                                    rowIndex = row2;
                                    columnIndex = column2;
                                }
                            }
                        }
                    }
                    //if only one cell in the box has that candidate, solve it
                    if (num == 1)
                    {
                        mySudoku[rowIndex, columnIndex].solve(i);
                        RookHinter(mySudoku);
                        BoxHinter(mySudoku);
                        OnlyCandidateLeftBoxCheckerWorks = true;
                        OnlyCandidateLeftBoxChecker(mySudoku);
                    }
                }
            }
        }
        return OnlyCandidateLeftBoxCheckerWorks;
    }

    //checks for 2 boxes that have only 2 candidates in a column or row, eliminates those candidates from that column OR row
    public bool NakedCandidateRookHinter(SudokuGrid mySudoku)
    {
        bool candidatePairRookHinterWorks = false;
        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if that element is unsolved
                if (!mySudoku[row, column].getSolved())
                {
                    //for each other element in the column
                    int numSame = 0;
                    List<int> rowVals = new List<int>();

                    for (int row2 = 0; row2 < 9; row2++)
                    {
                        //if the other element has same candidates
                        if (mySudoku[row2, column].samePossible(mySudoku[row, column]))
                        {
                            numSame++;
                            rowVals.Add(row2);
                        }
                    }
                    //if the number of cells with same possibles equals number of possibles per cell
                    if (numSame == mySudoku[row, column].size())
                    {
                        //for each other element in the column
                        for (int row2 = 0; row2 < 9; row2++)
                        {
                            if (!rowVals.Contains(row2))
                            {
                                for (int possibleIndex = 0; possibleIndex < mySudoku[row, column].size(); possibleIndex++)
                                {
                                    if (mySudoku[row2, column].indexOf(mySudoku[row, column].getVal(possibleIndex)) != -1)
                                    {
                                        mySudoku[row2, column].RemoveAt(mySudoku[row2, column].indexOf(mySudoku[row, column].getVal(possibleIndex)));
                                    }
                                }
                            }
                        }
                        //candidatePairRookHinterWorks = true;
                        //candidatePairRookHinterWorks =
                        RookHinter(mySudoku);
                        //candidatePairRookHinterWorks =
                        BoxHinter(mySudoku);
                    }


                    //for each other element in the row
                    List<int> columnVals = new List<int>();
                    numSame = 0;
                    for (int column2 = 0; column2 < 9; column2++)
                    {
                        //if the other element is not solved
                        if (mySudoku[row, column2].samePossible(mySudoku[row, column2]))
                        {
                            numSame++;
                            columnVals.Add(column2);
                        }
                    }
                    //if the number of cells with same possibles equals number of possibles per cell
                    if (numSame == mySudoku[row, column].size())
                    {
                        //for each other element in that row
                        for (int column2 = 0; column2 < 9; column2++)
                        {
                            if (!columnVals.Contains(column2))
                            {
                                for (int possibleIndex = 0; possibleIndex < mySudoku[row, column].size(); possibleIndex++)
                                {
                                    if (mySudoku[row, column2].indexOf(mySudoku[row, column].getVal(possibleIndex)) != -1)
                                    {
                                        mySudoku[row, column2].RemoveAt(mySudoku[row, column2].indexOf(mySudoku[row, column].getVal(possibleIndex)));
                                    }
                                }
                            }
                        }
                        candidatePairRookHinterWorks = true;
                        //candidatePairRookHinterWorks =
                        RookHinter(mySudoku);
                        //candidatePairRookHinterWorks =
                        BoxHinter(mySudoku);
                    }
                }
            }
        }
        return candidatePairRookHinterWorks;
    }

    //checks for 2 boxes that have only 2 candidates in a box, eliminates those candidates from that box
    public bool NakedCandidateBoxChecker(SudokuGrid mySudoku)
    {
        bool candidatePairBoxCheckerWorks = false;
        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if that element is unsolved
                if (!mySudoku[row, column].getSolved())
                {
                    int numSame = 0;
                    List<int> rowVals = new List<int>();
                    List<int> columnVals = new List<int>();

                    int boxRow = row / 3;
                    int boxColumn = column / 3;


                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                    {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                        {
                            //if the element is not solved
                            if (!mySudoku[row2, column2].getSolved())
                            {
                                //if it has same possibles
                                if (mySudoku[row2, column2].samePossible(mySudoku[row, column]))
                                {
                                    numSame++;
                                    rowVals.Add(row2);
                                    columnVals.Add(column2);
                                }
                            }
                        }
                    }

                    //if the number of cells with same possibles equals number of possibles per cell
                    if (numSame == mySudoku[row, column].size())
                    {

                        //for each other element in that box
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                        {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                            {
                                //if the box was not one of the ones that had same pair
                                if (!columnVals.Contains(column2) || !rowVals.Contains(row2))
                                {

                                    for (int possibleIndex = 0; possibleIndex < mySudoku[row, column].size(); possibleIndex++)
                                    {
                                        //if the other cell Contains that possibility
                                        if (mySudoku[row2, column2].contains(mySudoku[row, column].getVal(possibleIndex)))
                                        {
                                            //remove that possibility from the other cell
                                            //printBoard(mySudoku, true);
                                            mySudoku[row2, column2].RemoveAt(mySudoku[row2, column2].indexOf(mySudoku[row, column].getVal(possibleIndex)));
                                        }
                                    }
                                }
                            }
                        }
                        candidatePairBoxCheckerWorks = true;
                        //candidatePairBoxCheckerWorks =
                        RookHinter(mySudoku);
                        //candidatePairBoxCheckerWorks =
                        BoxHinter(mySudoku);
                    }
                }
            }
        }
        return candidatePairBoxCheckerWorks;
    }

    //checks for hidden candidate sets and removes candidates from those
    public bool HiddenCandidatePairChecker(SudokuGrid mySudoku)
    {
        bool hiddenCandidatePairCheckerWorks = false;
        //find in a row
        for (int row = 0; row < 9; row++)
        {
            //for each candidate i
            for (int i = 1; i <= 9; i++)
            {
                //num is number of appearances of that candidate
                int num = 0;
                int iColumnCoord1 = -1;
                int iColumnCoord2 = -1;

                for (int column = 0; column < 9; column++)
                {
                    //if that cell contains the candidate
                    if (mySudoku[row, column].contains(i))
                    {
                        iColumnCoord2 = iColumnCoord1;
                        iColumnCoord1 = column;
                        num++;
                    }
                }
                //if 2 possibles for the first candidate
                if (num == 2)
                {
                    //find second candidate
                    for (int k = i + 1; k <= 9; k++)
                    {
                        //num for second pair
                        int numK = 0;
                        int kColumnCoord1 = -1;
                        int kColumnCoord2 = -1;
                        for (int column = 0; column < 9; column++)
                        {
                            //if that cell contains the candidate
                            if (mySudoku[row, column].contains(k))
                            {
                                kColumnCoord2 = kColumnCoord1;
                                kColumnCoord1 = column;
                                numK++;
                            }
                        }
                        //if pair for second candidate
                        if (numK == 2)
                        {
                            //if coord of both pairs are same
                            if (kColumnCoord1 == iColumnCoord1 && kColumnCoord2 == iColumnCoord2)
                            {
                                //remove all other candidates from both cells
                                for (int j = 1; j <= 9; j++)
                                {
                                    //i and k should be the two candidates
                                    if (j != i && j != k)
                                    {
                                        //removal
                                        if (mySudoku[row, kColumnCoord1].contains(j))
                                        {
                                            mySudoku[row, kColumnCoord1].RemoveAt(mySudoku[row, kColumnCoord1].indexOf(j));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                        if (mySudoku[row, kColumnCoord2].contains(j))
                                        {
                                            mySudoku[row, kColumnCoord2].RemoveAt(mySudoku[row, kColumnCoord2].indexOf(j));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //find in a column
        for (int column = 0; column < 9; column++)
        {
            //for each candidate i
            for (int i = 1; i <= 9; i++)
            {
                //num is number of appearances of that candidate
                int num = 0;
                int iRowCoord1 = -1;
                int iRowCoord2 = -1;

                for (int row = 0; row < 9; row++)
                {
                    //if that cell contains the candidate
                    if (mySudoku[row, column].contains(i))
                    {
                        iRowCoord2 = iRowCoord1;
                        iRowCoord1 = row;
                        num++;
                    }
                }
                //if 2 possibles for the first candidate
                if (num == 2)
                {
                    //find second candidate
                    for (int k = i + 1; k <= 9; k++)
                    {
                        //num for second pair
                        int numK = 0;
                        int kRowCoord1 = -1;
                        int kRowCoord2 = -1;
                        for (int row = 0; row < 9; row++)
                        {
                            //if that cell contains the candidate
                            if (mySudoku[row, column].contains(k))
                            {
                                kRowCoord2 = kRowCoord1;
                                kRowCoord1 = row;
                                numK++;
                            }
                        }
                        //if pair for second candidate
                        if (numK == 2)
                        {
                            //if coord of both pairs are same
                            if (kRowCoord1 == iRowCoord1 && kRowCoord2 == iRowCoord2)
                            {
                                //remove all other candidates from both cells
                                for (int j = 1; j <= 9; j++)
                                {
                                    //i and k should be the two candidates remaining
                                    if (j != i && j != k)
                                    {
                                        //removal
                                        if (mySudoku[kRowCoord1, column].contains(j))
                                        {
                                            mySudoku[kRowCoord1, column].RemoveAt(mySudoku[kRowCoord1, column].indexOf(j));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                        if (mySudoku[kRowCoord2, column].contains(j))
                                        {
                                            mySudoku[kRowCoord2, column].RemoveAt(mySudoku[kRowCoord2, column].indexOf(j));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //find in box
        //for each big box
        for (int boxRow = 0; boxRow < 3; boxRow++)
        {
            for (int boxColumn = 0; boxColumn < 3; boxColumn++)
            {
                //for each candidate i
                for (int i = 1; i <= 9; i++)
                {
                    //num is number of appearances of that candidate
                    int num = 0;
                    int iRowCoord1 = -1;
                    int iRowCoord2 = -1;
                    int iColumnCoord1 = -1;
                    int iColumnCoord2 = -1;

                    //for each row in the small box
                    for (int row = boxRow * 3; row < boxRow * 3 + 3; row++)
                    {
                        //for each column in the small box
                        for (int column = boxColumn * 3; column < boxColumn * 3 + 3; column++)
                        {
                            //if that cell contains the candidate
                            if (mySudoku[row, column].contains(i))
                            {
                                iRowCoord2 = iRowCoord1;
                                iRowCoord1 = row;
                                iColumnCoord2 = iColumnCoord1;
                                iColumnCoord1 = column;
                                num++;
                            }
                        }
                    }
                    //if 2 possibles for the first candidate
                    if (num == 2)
                    {
                        //find second candidate
                        for (int k = i + 1; k <= 9; k++)
                        {
                            //num for second pair
                            int numK = 0;
                            int kRowCoord1 = -1;
                            int kRowCoord2 = -1;
                            int kColumnCoord1 = -1;
                            int kColumnCoord2 = -1;

                            //for each row in the small box
                            for (int row = boxRow * 3; row < boxRow * 3 + 3; row++)
                            {
                                //for each column in the small box
                                for (int column = boxColumn * 3; column < boxColumn * 3 + 3; column++)
                                {
                                    //if that cell contains the candidate
                                    if (mySudoku[row, column].contains(k))
                                    {
                                        kRowCoord2 = iRowCoord1;
                                        kRowCoord1 = row;
                                        kColumnCoord2 = iColumnCoord1;
                                        kColumnCoord1 = column;
                                        numK++;
                                    }
                                }
                            }
                            //if pair for second candidate
                            if (numK == 2)
                            {
                                //if coord of both pairs are same
                                if (kRowCoord1 == iRowCoord1 && kRowCoord2 == iRowCoord2 && kColumnCoord1 == iColumnCoord1 && kColumnCoord2 == iColumnCoord2)
                                {
                                    //remove all other candidates from both cells
                                    for (int j = 1; j <= 9; j++)
                                    {
                                        //i and k should be the two candidates
                                        if (j != i && j != k)
                                        {
                                            //removal
                                            if (mySudoku[kRowCoord1, kColumnCoord1].contains(j))
                                            {
                                                mySudoku[kRowCoord1, kColumnCoord1].RemoveAt(mySudoku[kRowCoord1, kColumnCoord1].indexOf(j));
                                                RookHinter(mySudoku);
                                                BoxHinter(mySudoku);
                                            }
                                            if (mySudoku[kRowCoord2, kColumnCoord2].contains(j))
                                            {
                                                mySudoku[kRowCoord2, kColumnCoord2].RemoveAt(mySudoku[kRowCoord2, kColumnCoord2].indexOf(j));
                                                RookHinter(mySudoku);
                                                BoxHinter(mySudoku);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return hiddenCandidatePairCheckerWorks;
    }


    //method for hiddenCandidateChecker, takes in array list, finds the hidden candidates, returns them in an arraylist
    public List<int> FindHiddenCandidatesPair(List<SudokCell> candidateSet)
    {
        return new List<int>();
    }

    //method for candidate lines (only place in a box where candidate must go is in a line, eliminate candidate from that line outside the box)
    public bool CandidateLinesChecker(SudokuGrid mySudoku)
    {
        bool candidateLinesCheckerWorks = false;
        //for each big box
        for (int boxRow = 0; boxRow < 3; boxRow++)
        {
            for (int boxColumn = 0; boxColumn < 3; boxColumn++)
            {
                //for each candidate
                for (int i = 1; i <= 9; i++)
                {
                    List<int> rowVals = new List<int>();
                    List<int> columnVals = new List<int>();
                    int numHasCandidate = 0;
                    bool removedACandidate = false;

                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                    {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                        {
                            //if the element is not solved
                            if (!mySudoku[row2, column2].getSolved())
                            {
                                //if it contains the candidate integer
                                if (mySudoku[row2, column2].contains(i))
                                {
                                    numHasCandidate++;
                                    rowVals.Add(row2);
                                    columnVals.Add(column2);
                                }
                            }
                        }
                    }
                    //if number of cells that has that candidate in a box is 3 or lower
                    if (numHasCandidate <= 3)
                    {
                        //if all in same row
                        bool allInSameRow = false;
                        //if 2 in same row
                        if (rowVals.Count == 2)
                        {
                            if (rowVals[0] == rowVals[1])
                            {
                                allInSameRow = true;
                            }
                        }
                        //if 3 in same row
                        if (rowVals.Count == 3)
                        {
                            if (rowVals[0] == rowVals[1] && rowVals[1] == rowVals[2])
                            {
                                allInSameRow = true;
                            }
                        }
                        if (allInSameRow)
                        {
                            //eliminate values along that row
                            for (int column2 = 0; column2 < 9; column2++)
                            {
                                //if outside of the box (not one of the previously selected)
                                if (!columnVals.Contains(column2))
                                {
                                    //if it contains the candidate
                                    if (mySudoku[rowVals[0], column2].contains(i))
                                    {
                                        //remove that candidate
                                        mySudoku[rowVals[0], column2].RemoveAt(mySudoku[rowVals[0], column2].indexOf(i));
                                        removedACandidate = true;
                                    }
                                }
                            }
                        }



                        //if all in same column
                        bool allInSameColumn = false;
                        //if 2 in same column
                        if (columnVals.Count == 2)
                        {
                            if (columnVals[0] == columnVals[1])
                            {
                                allInSameColumn = true;
                            }
                        }
                        //if 3 in same column
                        if (columnVals.Count == 3)
                        {
                            if (columnVals[0] == columnVals[1] && columnVals[1] == columnVals[2])
                            {
                                allInSameColumn = true;
                            }
                        }
                        if (allInSameColumn)
                        {
                            //eliminate values along that column
                            for (int row2 = 0; row2 < 9; row2++)
                            {
                                //if outside of the box (not one of the previously selected)
                                if (!rowVals.Contains(row2))
                                {
                                    //if it contains the candidate
                                    if (mySudoku[row2, columnVals[0]].contains(i))
                                    {
                                        //remove that candidate
                                        mySudoku[row2, columnVals[0]].RemoveAt(mySudoku[row2, columnVals[0]].indexOf(i));
                                        removedACandidate = true;
                                    }
                                }
                            }
                        }
                        //if a candidate was removed, run through box and rook checker
                        if (removedACandidate)
                        {
                            candidateLinesCheckerWorks = true;
                            //candidateLinesCheckerWorks =
                            BoxHinter(mySudoku);
                            RookHinter(mySudoku);
                        }
                    }
                }
            }
        }
        return candidateLinesCheckerWorks;
    }

    public bool pointingPairRookToBoxChecker(SudokuGrid mySudoku)
    {
        bool pointingPairRookToBoxWorks = false;
        //check rows
        for (int row = 0; row < 9; row++)
        {
            //for candidate i
            for (int i = 1; i <= 9; i++)
            {
                int num = 0;
                int columnCoord1 = -1;
                int columnCoord2 = -1;
                int columnCoord3 = -1;

                for (int column = 0; column < 9; column++)
                {
                    if (mySudoku[row, column].contains(i))
                    {
                        columnCoord1 = columnCoord2;
                        columnCoord2 = columnCoord3;
                        columnCoord3 = column;
                        num++;
                    }
                }
                //if the number of cells in that line with that candidate is 3
                if (num == 3)
                {
                    //if cells in the same box
                    if (columnCoord1 / 3 == columnCoord2 / 3 && columnCoord2 / 3 == columnCoord3 / 3)
                    {
                        //remove i from rest of box
                        int boxRow = row / 3;
                        int boxColumn = columnCoord1 / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                        {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                            {
                                //if none of the three
                                if (!(columnCoord1 == column2 && row == row2) && !(columnCoord2 == column2 && row == row2) && !(columnCoord3 == column2 && row == row2))
                                {
                                    if (!mySudoku[row2, column2].getSolved())
                                    {
                                        //remove i
                                        if (mySudoku[row2, column2].contains(i))
                                        {
                                            mySudoku[row2, column2].RemoveAt(mySudoku[row2, column2].indexOf(i));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //if the number of cells in that line with that candidate is 2
                if (num == 2)
                {
                    //if cells in the same box
                    if (columnCoord2 / 3 == columnCoord3 / 3)
                    {
                        //remove i from rest of box
                        int boxRow = row / 3;
                        int boxColumn = columnCoord2 / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                        {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                            {
                                //if none of the two
                                if (!(columnCoord2 == column2 && row == row2) && !(columnCoord3 == column2 && row == row2))
                                {
                                    //if other cell is not solved
                                    if (!mySudoku[row2, column2].getSolved())
                                    {
                                        //remove i
                                        if (mySudoku[row2, column2].contains(i))
                                        {
                                            mySudoku[row2, column2].RemoveAt(mySudoku[row2, column2].indexOf(i));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //check columns
        for (int column = 0; column < 9; column++)
        {
            //for candidate i
            for (int i = 1; i <= 9; i++)
            {
                int num = 0;
                int rowCoord1 = -1;
                int rowCoord2 = -1;
                int rowCoord3 = -1;

                for (int row = 0; row < 9; row++)
                {
                    if (mySudoku[row, column].contains(i))
                    {
                        rowCoord1 = rowCoord2;
                        rowCoord2 = rowCoord3;
                        rowCoord3 = row;
                        num++;
                    }
                }
                //if the number of cells in that line with that candidate is 3
                if (num == 3)
                {
                    //if cells in the same box
                    if (rowCoord1 / 3 == rowCoord2 / 3 && rowCoord2 / 3 == rowCoord3 / 3)
                    {
                        //remove i from rest of box
                        int boxRow = rowCoord1 / 3;
                        int boxColumn = column / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                        {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                            {
                                //if none of the three
                                if (!(rowCoord1 == row2 && column == column2) && !(rowCoord2 == row2 && column == column2) && !(rowCoord3 == row2 && column == column2))
                                {
                                    if (!mySudoku[row2, column2].getSolved())
                                    {
                                        //remove i
                                        if (mySudoku[row2, column2].contains(i))
                                        {
                                            mySudoku[row2, column2].RemoveAt(mySudoku[row2, column2].indexOf(i));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //if the number of cells in that line with that candidate is 2
                if (num == 2)
                {
                    //if cells in the same box
                    if (rowCoord2 / 3 == rowCoord3 / 3)
                    {
                        //remove i from rest of box
                        int boxRow = rowCoord2 / 3;
                        int boxColumn = column / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
                        {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
                            {
                                //if none of the two
                                if (!(rowCoord2 == row2 && column == column2) && !(rowCoord3 == row2 && column == column2))
                                {
                                    //if other cell is not solved
                                    if (!mySudoku[row2, column2].getSolved())
                                    {
                                        //remove i
                                        if (mySudoku[row2, column2].contains(i))
                                        {
                                            mySudoku[row2, column2].RemoveAt(mySudoku[row2, column2].indexOf(i));
                                            RookHinter(mySudoku);
                                            BoxHinter(mySudoku);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        return pointingPairRookToBoxWorks;
    }

    //forcing chains checker
    public bool forcingChainsChecker(SudokuGrid mySudoku)
    {
        //System.out.println("I was called");
        bool forcingChainsCheckerWorks = false;
        //for each unsolved cell
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if unsolved
                if (!mySudoku[row, column].getSolved())
                {
                    //setup
                    int numCands = mySudoku[row, column].getPossibles().Count;

                    //bool array, set initially to true if the corresponding cell is unsolved
                    bool[,] sameSolved = new bool[9, 9];
                    for (int solvedRow = 0; solvedRow < 9; solvedRow++)
                    {
                        for (int solvedColumn = 0; solvedColumn < 9; solvedColumn++)
                        {
                            sameSolved[solvedRow, solvedColumn] = true;
                            if (mySudoku[solvedRow, solvedColumn].getSolved())
                            {
                                sameSolved[solvedRow, solvedColumn] = false;
                            }
                        }
                    }

                    //first guess
                    SudokuGrid copy1 = Copy(mySudoku);

                    copy1[row, column].solve(mySudoku[row, column].getPossibles()[0]);

                    if (solveForForcingChains(copy1))
                    {
                        mySudoku[row, column].RemoveAt(mySudoku[row, column].indexOf(mySudoku[row, column].getPossibles()[0]));
                        return true;
                    }
                    if (numUnsolved(copy1) == 0 && !IsSolved(copy1, false))
                    {
                        mySudoku[row, column].RemoveAt(mySudoku[row, column].indexOf(mySudoku[row, column].getPossibles()[0]));
                        return true;
                    }
                    for (int row2 = 0; row2 < 9; row2++)
                    {
                        for (int column2 = 0; column2 < 9; column2++)
                        {
                            if (!copy1[row2, column2].getSolved())
                            {
                                sameSolved[row2, column2] = false;
                            }
                        }
                    }


                    //all other guesses
                    for (int candidateIndex = 1; candidateIndex < numCands; candidateIndex++)
                    {
                        SudokuGrid copy = Copy(mySudoku);
                        copy[row, column].solve(mySudoku[row, column].getPossibles()[candidateIndex]);
                        if (solveForForcingChains(copy))
                        {
                            mySudoku[row, column].RemoveAt(mySudoku[row, column].indexOf(mySudoku[row, column].getPossibles()[candidateIndex]));
                            return true;
                        }
                        if (numUnsolved(copy) == 0 && !IsSolved(copy, false))
                        {
                            mySudoku[row, column].RemoveAt(mySudoku[row, column].indexOf(mySudoku[row, column].getPossibles()[candidateIndex]));
                            return true;
                        }

                        for (int row2 = 0; row2 < 9; row2++)
                        {
                            for (int column2 = 0; column2 < 9; column2++)
                            {
                                if (!mySudoku[row2, column2].getSolved())
                                {
                                    if (copy[row2, column2].getSolved())
                                    {
                                        if (!copy[row2, column2].samePossible(copy1[row2, column2]))
                                        {
                                            sameSolved[row2, column2] = false;
                                        }
                                    }
                                }
                                if (!copy[row2, column2].getSolved())
                                {
                                    sameSolved[row2, column2] = false;
                                }
                            }
                        }
                    }
                    for (int row2 = 0; row2 < 9; row2++)
                    {
                        for (int column2 = 0; column2 < 9; column2++)
                        {
                            if (!mySudoku[row2, column2].getSolved())
                            {
                                if (sameSolved[row2, column2])
                                {
                                    if (copy1[row2, column2].getSolved())
                                    {
                                        mySudoku[row2, column2].solve(copy1[row2, column2].getVal());
                                        forcingChainsCheckerWorks = true;
                                        //System.out.println("I did it");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return forcingChainsCheckerWorks;
    }

    //solve method for hidden candidate copies
    public bool solveForForcingChains(SudokuGrid mySudoku)
    {
        try
        {
            for (int i = 0; i < 10; i++)
            {
                RookHinter(mySudoku);
                BoxHinter(mySudoku);
                OnlyCandidateLeftRookHinter(mySudoku);
                OnlyCandidateLeftBoxChecker(mySudoku);
                NakedCandidateRookHinter(mySudoku);
                NakedCandidateBoxChecker(mySudoku);
                CandidateLinesChecker(mySudoku);
                HiddenCandidatePairChecker(mySudoku);
                pointingPairRookToBoxChecker(mySudoku);

                //System.out.println("HeHe");
            }
        }
        //return if made a remove all error
        catch (System.Exception)
        {
            return true;
        }
        //return false;

        bool forcingChainsCheckerWorks = false;

        if (InvalidMove(mySudoku))
        {
            return true;
        }
        if (!IsSolved(mySudoku, false))
        {
            forcingChainsCheckerWorks = forcingChainsChecker(mySudoku);
        }



        if (forcingChainsCheckerWorks)
        {
            solveForForcingChains(mySudoku);
        }
        return false;
    }

    //make a copy of values
    public SudokuGrid Copy(SudokuGrid mySudoku)
    {

        SudokuGrid mySudoku2 = new SudokuGrid();
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                SudokCell sudokCell = new SudokCell();
                SudokCell x = mySudoku[row, column];
                mySudoku2[row, column] = sudokCell.Clone(x);

            }
        }
        return mySudoku2;
    }

    //check if the sudoku is solved
    public bool IsSolved(SudokuGrid mySudoku)
    {
        return IsSolved(mySudoku, false);
    }
    //check if the sudoku is solved
    public bool IsSolved(SudokuGrid mySudoku, bool printChecks)
    {
        //checks each box is solved
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if unsolved, return false
                if (!mySudoku[row, column].getSolved())
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
                myList.Add(mySudoku[row, column].getVal());
            }
            if (ContainsDuplicate(myList))
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
                myList.Add(mySudoku[row, column].getVal());
            }
            if (ContainsDuplicate(myList))
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
                        myList.Add(mySudoku[row2, column2].getVal());
                    }
                }
                if (ContainsDuplicate(myList))
                {
                    return false;
                }
            }
        }
        //if(printChecks) System.out.println("Boxes add up");
        return true;
    }

    //check how many boxes remain unsolved
    public int numUnsolved(SudokuGrid mySudoku)
    {
        int numUnsolvedB = 81;
        //for each solved cell in main array
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if that element is solved
                if (mySudoku[row, column].getSolved())
                {
                    numUnsolvedB--;
                }
            }
        }
        return numUnsolvedB;
    }

    //solve method
    public void Solve(SudokuGrid mySudoku, bool forcingChains)
    {
        for (int i = 0; i < 10; i++)
        {
            RookHinter(mySudoku);
            BoxHinter(mySudoku);
            OnlyCandidateLeftRookHinter(mySudoku);
            OnlyCandidateLeftBoxChecker(mySudoku);
            NakedCandidateRookHinter(mySudoku);
            NakedCandidateBoxChecker(mySudoku);
            CandidateLinesChecker(mySudoku);
            HiddenCandidatePairChecker(mySudoku);
            pointingPairRookToBoxChecker(mySudoku);

            //System.out.println("HeHe");
        }
        bool forcingChainsCheckerWorks = false;
        if (forcingChains)
        {
            forcingChainsCheckerWorks = forcingChainsChecker(mySudoku);
        }
        if (forcingChainsCheckerWorks)
        {
            Solve(mySudoku, true);
        }
            /*if(bruteForce && !solved(mySudoku, false)){
                System.out.println("multiple guesses");
                bruteForceSolver(mySudoku);
            }*/
    }


    //checks if an invalid move has been made
    public bool InvalidMove(SudokuGrid mySudoku)
    {
        //check columns for duplicates
        for (int column = 0; column < 9; column++)
        {
            List<int> myList = new List<int>();
            for (int row = 0; row < 9; row++)
            {
                myList.Add(mySudoku[row, column].getVal());
            }
            if (ContainsDuplicate(myList))
            {
                return true;
            }
        }

        //check rows for duplicates
        for (int row = 0; row < 9; row++)
        {
            List<int> myList = new List<int>();
            for (int column = 0; column < 9; column++)
            {
                myList.Add(mySudoku[row, column].getVal());
            }
            if (ContainsDuplicate(myList))
            {
                return true;
            }
        }

        //check boxes for duplicates

        //for box coords
        for (int boxRow = 0; boxRow < 3; boxRow++)
        {
            for (int boxColumn = 0; boxColumn < 3; boxColumn++)
            {
                List<int> myList = new List<int>();
                //for each row in the small box
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++)
                {
                    //for each column in the small box
                    for (int column = boxColumn * 3; column < boxColumn * 3 + 3; column++)
                    {
                        myList.Add(mySudoku[row, column].getVal());
                    }
                }
                if (ContainsDuplicate(myList))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public bool InvalidCell(SudokuGrid mySudoku, int row, int column)
    {
        //check columns for duplicates


        for (int row2 = 0; row2 < 9; row2++)
        {
            if (mySudoku[row2, column].getSolved())
            {
                if (row2 != row && mySudoku[row2, column].getVal() == mySudoku[row, column].getVal())
                {
                    return true;
                }
            }
        }


        //check rows for duplicates

        for (int column2 = 0; column2 < 9; column2++)
        {
            if (mySudoku[row, column2].getSolved())
            {
                if (column2 != column && mySudoku[row, column2].getVal() == mySudoku[row, column].getVal())
                {
                    return true;
                }
            }
        }


        //in box

        int boxRow = row / 3;
        int boxColumn = column / 3;
        //for each row in the small box
        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++)
        {
            //for each column in the small box
            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++)
            {
                if (mySudoku[row2, column2].getSolved())
                {
                    if (column2 != column && row2 != row && mySudoku[row2, column2].getVal() == mySudoku[row, column].getVal())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //checks if a List contains a duplicate ovre the domain [1, 9]
    public bool ContainsDuplicate(List<int> myList)
    {
        List<int> results = new List<int>();
        for (int i = 1; i <= 9; i++)
        {
            results = myList.FindAll(
                    delegate (int bk)
            {
                return bk == i;
            }
                );

            if (results.Count > 1)
            {
                return true;
            }
        }
        return false;
    }

    //converts int array to sudokcell sudokugrid
    public SudokuGrid FromIntArray(int[,] myArray)
    {
        SudokuGrid mySudoku = new SudokuGrid();
        //my sudoku to be worked with
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                mySudoku[row, column] = new SudokCell(myArray[row, column]);
            }
        }

        return mySudoku;
    }

    //is a sudoku digit helper method
    public bool isDigit(String input)
    {
        for (int i = 1; i <= 9; i++)
        {
            if (input.Equals("" + i))
            {
                return true;
            }
        }
        return false;
    }

    public String bruteForceSolver(ref SudokuGrid mySudoku)
    {
        if (IsSolved(mySudoku))
        {
            return "already solved";
        }
        solveForBruteForce(mySudoku);
        //else, guess all possibles and brute force solve. if multiple solutions, return false
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                //if unsolved
                if (!mySudoku[row, column].getSolved())
                {

                    for (int i = 0; i < mySudoku[row, column].getPossibles().Count; i++)
                    {

                        SudokuGrid copy = Copy(mySudoku);
                        //solve to the index of the guess
                        copy[row, column].solve(mySudoku[row, column].getPossibles()[i]);

                        if (solveForBruteForce(copy))
                        {
                            mySudoku[row, column].RemoveAt(i);
                            i--;
                            solveForBruteForce(mySudoku);
                        }
                        else if (IsSolved(copy))
                        {
                            mySudoku = Copy(copy);
                            return "Solved";
                        }
                        else if (InvalidMove(copy))
                        {
                            try
                            {
                                mySudoku[row, column].RemoveAt(i);
                                i--;
                                solveForBruteForce(mySudoku);
                            }
                            catch
                            {
                                return "Mistake Made";
                            }
                            //bruteForceSolver(mySudoku);
                            //return "";
                        }
                        else if (numUnsolved(copy) == 0)
                        {
                            mySudoku[row, column].RemoveAt(i);
                            i--;
                            solveForBruteForce(mySudoku);
                            //bruteForceSolver(mySudoku);
                            //return "";
                        }
                        else //guess again
                        {
                            //brute force it
                            if (bruteForceSolver(ref copy).Equals("Mistake Made"))
                            {
                                mySudoku[row, column].RemoveAt(i);
                                i--;
                                solveForBruteForce(mySudoku);

                            }
                            else if (IsSolved(copy))
                            {
                                mySudoku = Copy(copy);
                                return "solved";
                            }
                            //return "Valid moves, still not solved";
                        }
                    }
                }
            }
        }
        return "blah";
    }

    public bool solveForBruteForce(SudokuGrid mySudoku)
    {
        try
        {
            for (int i = 0; i < 10; i++)
            {
                RookHinter(mySudoku);
                BoxHinter(mySudoku);
                OnlyCandidateLeftRookHinter(mySudoku);
                OnlyCandidateLeftBoxChecker(mySudoku);
                NakedCandidateRookHinter(mySudoku);
                NakedCandidateBoxChecker(mySudoku);
                CandidateLinesChecker(mySudoku);

                //System.out.println("HeHe");
            }
        }
        //return if made a remove all error
        catch (System.Exception)
        {
            return true;
        }
        return false;
    }

    public void bruteForceSolverOld(SudokuGrid mySudoku)
    {
        //for each level guess
        SudokuGrid mySudoku2 = Copy(mySudoku);
        for (int k = 0; k < numUnsolved(mySudoku) && !IsSolved(mySudoku, false); k++)
        {
            //while sudoku2 is unsolved
            int infiniteLoop = 0;
            int i = 1;
            while (!IsSolved(mySudoku2, false) && infiniteLoop < 100)
            {
                //infiniteLoop++;
                SudokuGrid testCase = Copy(mySudoku2);
                //find i unsolved cell and solve to be random of candidates
                bool foundUnsolved = false;
                int row = -1;
                int column = 0;
                int num = 0;
                while (!foundUnsolved)
                {
                    row++;
                    if (row == 9)
                    {
                        row = 0;
                        column++;
                    }
                    if (column == 9)
                    {
                        row = 0;
                        column = 0;
                    }
                    if (!testCase[row, column].getSolved())
                    {
                        num++;
                        if (num == i)
                        {
                            foundUnsolved = true;
                        }
                    }
                }
                i++;
                List<int> possibles = testCase[row, column].getPossibles();
                var rand = new Random();
                int randomIndex = (int)(rand.NextDouble() * possibles.Count);
                testCase[row, column].solve(possibles[randomIndex]);
                if (i > numUnsolved(testCase))
                {
                    //System.out.println("i greater than num unsolved");
                    //System.out.println("numUnsolved " + numUnsolved(testCase));
                    i = 1;
                        /*if(numUnsolved(testCase) != 0){
                            solve(testCase, true);
                        }*/
                }
                //if all cells solved but sudoku not solved
                if (numUnsolved(testCase) == 0 && !IsSolved(testCase, false))
                {
                    //System.out.println("restarted brute force");
                    bruteForceSolver(ref mySudoku);
                }

                for (int j = 0; j < 10; j++)
                {
                    RookHinter(testCase);
                    BoxHinter(testCase);
                    OnlyCandidateLeftRookHinter(mySudoku);
                    OnlyCandidateLeftBoxChecker(mySudoku);
                    NakedCandidateRookHinter(mySudoku);
                    NakedCandidateBoxChecker(mySudoku);
                    CandidateLinesChecker(mySudoku);
                }
                if (!IsSolved(testCase, false))
                {
                    //System.out.println("multiple guesses\n numunsolved " + numUnsolved(testCase));
                    mySudoku2 = Copy(testCase);
                    infiniteLoop = 100;
                }
                //if the test case is properly solved, make sudoku equal testcase
                if (IsSolved(testCase, false))
                {
                    //printBoard(testCase, false);
                    for (int roww = 0; roww < 9; roww++)
                    {
                        for (int columnn = 0; columnn < 9; columnn++)
                        {
                            mySudoku[roww, columnn] = testCase[roww, columnn];
                        }
                    }
                }
                //if all cells solved but sudoku not solved
                if (numUnsolved(testCase) == 0 && !IsSolved(testCase, false))
                {
                    //System.out.println("restarted brute force");
                    bruteForceSolver(ref mySudoku);
                }
            }
        }
    }

    public bool Equals(SudokuGrid obj, SudokuGrid obj2)
    {
        for (int row = 0; row < 9; row++)
        {
            for (int column = 0; column < 9; column++)
            {
                if (!obj2[row, column].Equals(obj[row, column]))
                {
                    return false;
                }
            }
        }
        return true;
    }
