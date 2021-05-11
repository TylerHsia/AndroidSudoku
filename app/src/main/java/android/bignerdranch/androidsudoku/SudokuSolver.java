package android.bignerdranch.androidsudoku;

public class SudokuSolver {
    public class SudokuSolver
    {


        //eliminates by rook method
        public bool RookChecker(SudokuGrid mySudoku)
        {
            bool RookCheckerWorks = false;

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
                                    //checkerMethodOneWorks = true;
                                    //checkerMethodOneWorks =
                                    RookChecker(mySudoku);
                                    //checkerMethodOneWorks =
                                    BoxChecker(mySudoku);
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

                                    //checkerMethodOneWorks = true;
                                    //RookCheckerWorks =
                                    RookChecker(mySudoku);
                                    //RookCheckerWorks =
                                    BoxChecker(mySudoku);
                                }
                            }
                        }
                    }
                }
            }
            return RookCheckerWorks;
        }

        //eliminates by checking 3 by 3 boxes
        public bool BoxChecker(SudokuGrid mySudoku)
        {
            bool boxCheckerWorks = false;

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
                                        //boxCheckerWorks = true;
                                        //boxCheckerWorks =
                                        BoxChecker(mySudoku);
                                        //boxCheckerWorks =
                                        RookChecker(mySudoku);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return boxCheckerWorks;
        }

        //check if candidate is only candidate in one spot in a row or column
        public bool OnlyCandidateLeftRookChecker(SudokuGrid mySudoku)
        {
            bool onlyCandidateLeftRookCheckerWorks = false;
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
                        RookChecker(mySudoku);
                        BoxChecker(mySudoku);
                        onlyCandidateLeftRookCheckerWorks = true;
                        //onlyCandidateLeftRookCheckerWorks = OnlyCandidateLeftBoxChecker(mySudoku);
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
                        onlyCandidateLeftRookCheckerWorks = true;
                        RookChecker(mySudoku);
                        BoxChecker(mySudoku);
                        //onlyCandidateLeftRookCheckerWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                    }
                }
            }
            return onlyCandidateLeftRookCheckerWorks;
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
                            RookChecker(mySudoku);
                            BoxChecker(mySudoku);
                            OnlyCandidateLeftBoxCheckerWorks = true;
                            OnlyCandidateLeftBoxChecker(mySudoku);
                        }
                    }
                }
            }
            return OnlyCandidateLeftBoxCheckerWorks;
        }

        //checks for 2 boxes that have only 2 candidates in a column or row, eliminates those candidates from that column OR row
        public bool NakedCandidateRookChecker(SudokuGrid mySudoku)
        {
            bool candidatePairRookCheckerWorks = false;
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
                            //candidatePairRookCheckerWorks = true;
                            //candidatePairRookCheckerWorks =
                            RookChecker(mySudoku);
                            //candidatePairRookCheckerWorks =
                            BoxChecker(mySudoku);
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
                            candidatePairRookCheckerWorks = true;
                            //candidatePairRookCheckerWorks =
                            RookChecker(mySudoku);
                            //candidatePairRookCheckerWorks =
                            BoxChecker(mySudoku);
                        }
                    }
                }
            }
            return candidatePairRookCheckerWorks;
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
                                                candidatePairBoxCheckerWorks = true;
                                            }
                                        }
                                    }
                                }
                            }

                            //candidatePairBoxCheckerWorks =
                            RookChecker(mySudoku);
                            //candidatePairBoxCheckerWorks =
                            BoxChecker(mySudoku);
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
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
                                            }
                                            if (mySudoku[row, kColumnCoord2].contains(j))
                                            {
                                                mySudoku[row, kColumnCoord2].RemoveAt(mySudoku[row, kColumnCoord2].indexOf(j));
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
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
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
                                            }
                                            if (mySudoku[kRowCoord2, column].contains(j))
                                            {
                                                mySudoku[kRowCoord2, column].RemoveAt(mySudoku[kRowCoord2, column].indexOf(j));
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
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
                                                    RookChecker(mySudoku);
                                                    BoxChecker(mySudoku);
                                                }
                                                if (mySudoku[kRowCoord2, kColumnCoord2].contains(j))
                                                {
                                                    mySudoku[kRowCoord2, kColumnCoord2].RemoveAt(mySudoku[kRowCoord2, kColumnCoord2].indexOf(j));
                                                    RookChecker(mySudoku);
                                                    BoxChecker(mySudoku);
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
                                candidateLinesCheckerWorks = BoxChecker(mySudoku);
                                candidateLinesCheckerWorks = RookChecker(mySudoku);
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
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
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
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
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
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
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
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
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
                    RookChecker(mySudoku);
                    BoxChecker(mySudoku);
                    OnlyCandidateLeftRookChecker(mySudoku);
                    OnlyCandidateLeftBoxChecker(mySudoku);
                    NakedCandidateRookChecker(mySudoku);
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
            for(int row = 0; row < 9; row++)
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
                RookChecker(mySudoku);
                BoxChecker(mySudoku);
                OnlyCandidateLeftRookChecker(mySudoku);
                OnlyCandidateLeftBoxChecker(mySudoku);
                NakedCandidateRookChecker(mySudoku);
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
                    if(row2 != row && mySudoku[row2, column].getVal() == mySudoku[row, column].getVal())
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
                                if(bruteForceSolver(ref copy).Equals("Mistake Made"))
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
                    RookChecker(mySudoku);
                    BoxChecker(mySudoku);
                    OnlyCandidateLeftRookChecker(mySudoku);
                    OnlyCandidateLeftBoxChecker(mySudoku);
                    NakedCandidateRookChecker(mySudoku);
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
                        RookChecker(testCase);
                        BoxChecker(testCase);
                        OnlyCandidateLeftRookChecker(mySudoku);
                        OnlyCandidateLeftBoxChecker(mySudoku);
                        NakedCandidateRookChecker(mySudoku);
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

        public int RateDifficulty(SudokuGrid mySudoku)
        {
            if (!mySudoku.IsValid())
            {
                return 0;
            }
            //level 1
            bool currentWorks = true;
            RookChecker(mySudoku);
            BoxChecker(mySudoku);
            if (mySudoku.IsSolved())
            {
                return 1;
            }


            //level 2
            while (currentWorks)
            {
                currentWorks = OnlyCandidateLeftRookChecker(mySudoku);
                if (!currentWorks)
                {
                    currentWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                }
            }
            if (mySudoku.IsSolved())
            {
                return 2;
            }


            //level 3
            currentWorks = true;
            while (currentWorks)
            {
                currentWorks = NakedCandidateRookChecker(mySudoku);
                if (!currentWorks)
                {
                    currentWorks = NakedCandidateBoxChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    currentWorks = OnlyCandidateLeftRookChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    currentWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                }
            }
            if (mySudoku.IsSolved())
            {
                return 3;
            }


            //level 4
            currentWorks = true;
            while (currentWorks)
            {
                currentWorks = CandidateLinesChecker(mySudoku);
                if (!currentWorks)
                {
                    HiddenCandidatePairChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    pointingPairRookToBoxChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    currentWorks = NakedCandidateRookChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    currentWorks = NakedCandidateBoxChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    currentWorks = OnlyCandidateLeftRookChecker(mySudoku);
                }
                if (!currentWorks)
                {
                    currentWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                }
            }
            if (mySudoku.IsSolved())
            {
                return 4;
            }


            //level 5
            Solve(mySudoku, true);
            if (mySudoku.IsSolved())
            {
                return 5;
            }


            //level 6
            bruteForceSolver(ref mySudoku);
            if (mySudoku.IsSolved())
            {
                return 6;
            }

            return 7;
        }
        /*
         * RookChecker(mySudoku);
                BoxChecker(mySudoku);
                OnlyCandidateLeftRookChecker(mySudoku);
                OnlyCandidateLeftBoxChecker(mySudoku);
                NakedCandidateRookChecker(mySudoku);
                NakedCandidateBoxChecker(mySudoku);
                CandidateLinesChecker(mySudoku);
                HiddenCandidatePairChecker(mySudoku);
                pointingPairRookToBoxChecker(mySudoku);
        */

        //gives sudoku from list of possibles
        public int[,] input(int x)
    {
        if (x == 1)
        {
            int[,] beginner =     {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
                {3, 1, -1, 9, -1, 4, 5, -1, -1},
                {-1, 4, 2, -1, 3, -1, 9, 6, 8},

                {6, 3, -1, -1, -1, -1, 7, 4, 5},
                {1, 2, -1, 4, 5, -1, -1, 8, 9},
                {8, 5, -1, -1, -1, -1, -1, 3, 2},

                {-1, -1, 6, 3, 7, 2, -1, 5, -1},
                {-1, 8, 1, 6, -1, 9, 2, 7, -1},
                {2, 7, 3, 1, -1, -1, -1, 9, 6}};
            return beginner;
        }
        if (x == 2)
        {
            int[,] normal = {{0, 0, 0, 0, 9, 0, 0, 0, 0},
                {0, 9, 0, 0, 0, 0, 0, 4, 0},
                {0, 5, 1, 8, 0, 7, 0, 0, 0},

                {0, 8, 7, 9, 0, 0, 0, 2, 0},
                {4, 0, 0, 3, 0, 0, 0, 0, 7},
                {9, 1, 0, 4, 0, 0, 3, 6, 8},

                {1, 0, 2, 0, 0, 4, 0, 7, 5},
                {0, 4, 0, 0, 0, 0, 2, 0, 0},
                {7, 0, 0, 0, 5, 0, 0, 8, 0}};
            return normal;
        }
        if (x == 3)
        {
            int[,] hard = {{0, 8, 0, 0, 0, 0, 0, 4, 0},
                {2, 0, 0, 8, 0, 0, 5, 0, 7},
                {0, 0, 4, 7, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 7, 1},
                {0, 0, 8, 0, 0, 6, 0, 0, 3},
                {7, 0, 0, 0, 0, 1, 0, 0, 4},
                {8, 0, 0, 0, 9, 2, 0, 0, 6},
                {6, 0, 2, 0, 0, 0, 7, 0, 0},
                {1, 0, 0, 0, 0, 0, 3, 9, 0}};
            return hard;
        }
        if (x == 4)
        {
            int[,] expert =   {{0, 0, 7, 0, 0, 0, 6, 3, 0},
                {6, 0, 0, 5, 0, 3, 0, 0, 9},
                {8, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 0, 9, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 8, 5, 4},
                {0, 0, 0, 8, 0, 0, 0, 0, 0},
                {7, 6, 0, 0, 0, 1, 0, 0, 0},
                {5, 0, 0, 0, 0, 7, 0, 0, 6},
                {0, 4, 1, 0, 9, 0, 0, 0, 5}};
            return expert;
        }
        if (x == 5)
        {
            int[,] expert2 = {{0, 9, 1, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 9, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 7, 0, 0, 0},
                {9, 0, 0, 0, 0, 0, 0, 1, 0},
                {6, 0, 4, 0, 1, 0, 0, 9, 0},
                {0, 0, 0, 7, 8, 0, 0, 0, 4},
                {0, 0, 6, 0, 0, 0, 0, 8, 0},
                {0, 0, 0, 0, 2, 1, 0, 0, 7},
                {7, 0, 9, 4, 0, 5, 0, 0, 1}};
            return expert2;
        }
        if (x == 6)
        {
            int[,] fiveStar = {{0, 5, 0, 0, 1, 3, 0, 0, 0},
                {0, 0, 1, 0, 8, 0, 3, 0, 0},
                {8, 0, 0, 5, 0, 0, 0, 6, 4},
                {5, 0, 7, 0, 3, 0, 0, 0, 0},
                {0, 4, 0, 0, 5, 0, 0, 2, 0},
                {0, 0, 0, 0, 2, 0, 8, 0, 5},
                {1, 6, 0, 0, 0, 9, 0, 0, 8},
                {0, 0, 9, 0, 7, 0, 2, 0, 0},
                {0, 0, 0, 8, 6, 0, 0, 4, 0}};
            return fiveStar;
        }
        if (x == 7)
        {
            int[,] fiveStar2 = {{0, 0, 6, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 0, 3, 0, 7, 1, 6},
                {3, 0, 0, 0, 7, 9, 8, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 7},
                {0, 0, 5, 3, 4, 2, 1, 0, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 3, 9, 5, 0, 0, 0, 4},
                {6, 9, 7, 0, 1, 0, 0, 0, 0},
                {0, 8, 0, 0, 0, 0, 3, 0, 0}};
            return fiveStar2;
        }
        if (x == 8)
        {
            int[,] fiveStar3 =    {{8, 0, 0, 0, 5, 6, 0, 0, 0},
                {0, 0, 0, 8, 0, 0, 0, 6, 0},
                {9, 0, 0, 3, 4, 0, 1, 0, 0},
                {6, 0, 0, 0, 3, 0, 0, 5, 0},
                {1, 5, 0, 0, 8, 0, 0, 3, 9},
                {0, 2, 0, 0, 9, 0, 0, 0, 7},
                {0, 0, 8, 0, 6, 3, 0, 0, 5},
                {0, 1, 0, 0, 0, 8, 0, 0, 0},
                {0, 0, 0, 5, 2, 0, 0, 0, 4}};
            return fiveStar3;
        }
        if (x == 9)
        {
            List<int> onlineSudokuHard = new List<int>();
            onlineSudokuHard.Add(204010);
            onlineSudokuHard.Add(174030000);
            onlineSudokuHard.Add(180049);
            onlineSudokuHard.Add(40826);
            onlineSudokuHard.Add(60000050);
            onlineSudokuHard.Add(43060071);
            onlineSudokuHard.Add(400050000);
            onlineSudokuHard.Add(80410090);
            onlineSudokuHard.Add(90608030);

            return twoDConverter(onlineSudokuHard);
        }
        if (x == 10)
        {
            List<int> fiveStar4 = new List<int>();
            fiveStar4.Add(90008);
            fiveStar4.Add(41005000);
            fiveStar4.Add(60070300);
            fiveStar4.Add(602510000);
            fiveStar4.Add(403020501);
            fiveStar4.Add(37206);
            fiveStar4.Add(4050020);
            fiveStar4.Add(900130);
            fiveStar4.Add(500080000);

            return twoDConverter(fiveStar4);
        }
        if (x == 11)
        {
            List<int> fiveStar5 = new List<int>();
            fiveStar5.Add(310006900);
            fiveStar5.Add(200090000);
            fiveStar5.Add(98030100);
            fiveStar5.Add(41000);
            fiveStar5.Add(60879040);
            fiveStar5.Add(520000);
            fiveStar5.Add(1080490);
            fiveStar5.Add(60003);
            fiveStar5.Add(9400028);

            return twoDConverter(fiveStar5);
        }
        if (x == 12)
        {
            List<int> outrageouslyEvilSudoku100 = new List<int>();
            outrageouslyEvilSudoku100.Add(904208001);
            outrageouslyEvilSudoku100.Add(20000000);
            outrageouslyEvilSudoku100.Add(60103500);
            outrageouslyEvilSudoku100.Add(80090006);
            outrageouslyEvilSudoku100.Add(703000040);
            outrageouslyEvilSudoku100.Add(600070000);
            outrageouslyEvilSudoku100.Add(0);
            outrageouslyEvilSudoku100.Add(410080600);
            outrageouslyEvilSudoku100.Add(6057);

            return twoDConverter(outrageouslyEvilSudoku100);
        }
        if (x == 13)
        {
            List<int> expertSudokuPartiallySolved = new List<int>();
            expertSudokuPartiallySolved.Add(3468159);
            expertSudokuPartiallySolved.Add(869715423);
            expertSudokuPartiallySolved.Add(154923608);
            expertSudokuPartiallySolved.Add(200096);
            expertSudokuPartiallySolved.Add(600800012);
            expertSudokuPartiallySolved.Add(600784);
            expertSudokuPartiallySolved.Add(376241);
            expertSudokuPartiallySolved.Add(194865);
            expertSudokuPartiallySolved.Add(416582937);

            return twoDConverter(expertSudokuPartiallySolved);
        }
        if (x == 14)
        {
            List<int> outrageouslyEvilSudoku99 = new List<int>();
            outrageouslyEvilSudoku99.Add(600000700);
            outrageouslyEvilSudoku99.Add(9003000);
            outrageouslyEvilSudoku99.Add(340080090);
            outrageouslyEvilSudoku99.Add(704000508);
            outrageouslyEvilSudoku99.Add(60950000);
            outrageouslyEvilSudoku99.Add(2100600);
            outrageouslyEvilSudoku99.Add(7);
            outrageouslyEvilSudoku99.Add(400500300);
            outrageouslyEvilSudoku99.Add(17000085);

            return twoDConverter(outrageouslyEvilSudoku99);
        }
        if (x == 15)
        {
            List<int> outrageouslyEvilSudoku98 = new List<int>();
            outrageouslyEvilSudoku98.Add(490860);
            outrageouslyEvilSudoku98.Add(10000500);
            outrageouslyEvilSudoku98.Add(500000000);
            outrageouslyEvilSudoku98.Add(109807004);
            outrageouslyEvilSudoku98.Add(40600);
            outrageouslyEvilSudoku98.Add(5012000);
            outrageouslyEvilSudoku98.Add(20000090);
            outrageouslyEvilSudoku98.Add(906000200);
            outrageouslyEvilSudoku98.Add(83);

            return twoDConverter(outrageouslyEvilSudoku98);
        }
        if (x == 16)
        {
            List<int> outrageouslyEvilSudoku97 = new List<int>();
            outrageouslyEvilSudoku97.Add(200005060);
            outrageouslyEvilSudoku97.Add(4600800);
            outrageouslyEvilSudoku97.Add(30700000);
            outrageouslyEvilSudoku97.Add(98020);
            outrageouslyEvilSudoku97.Add(900001000);
            outrageouslyEvilSudoku97.Add(78000004);
            outrageouslyEvilSudoku97.Add(80040000);
            outrageouslyEvilSudoku97.Add(650);
            outrageouslyEvilSudoku97.Add(100000003);

            return twoDConverter(outrageouslyEvilSudoku97);
        }
        if (x == 17)
        {
            List<int> outrageouslyEvilSudoku96 = new List<int>();
            outrageouslyEvilSudoku96.Add(907100);
            outrageouslyEvilSudoku96.Add(70430050);
            outrageouslyEvilSudoku96.Add(301000000);
            outrageouslyEvilSudoku96.Add(14700000);
            outrageouslyEvilSudoku96.Add(70);
            outrageouslyEvilSudoku96.Add(96000000);
            outrageouslyEvilSudoku96.Add(80007);
            outrageouslyEvilSudoku96.Add(200003004);
            outrageouslyEvilSudoku96.Add(50000039);
            //test
            return twoDConverter(outrageouslyEvilSudoku96);
        }
        if (x == 18)
        {
            List<int> outrageouslyEvilSudoku95 = new List<int>();
            outrageouslyEvilSudoku95.Add(49200000);
            outrageouslyEvilSudoku95.Add(800010);
            outrageouslyEvilSudoku95.Add(3);
            outrageouslyEvilSudoku95.Add(203056000);
            outrageouslyEvilSudoku95.Add(400000000);
            outrageouslyEvilSudoku95.Add(900040051);
            outrageouslyEvilSudoku95.Add(80002000);
            outrageouslyEvilSudoku95.Add(500800);
            outrageouslyEvilSudoku95.Add(7300900);

            return twoDConverter(outrageouslyEvilSudoku95);
        }
        if (x == 19)
        {
            List<int> outrageouslyEvilSudoku94 = new List<int>();
            outrageouslyEvilSudoku94.Add(710860000);
            outrageouslyEvilSudoku94.Add(68074002);
            outrageouslyEvilSudoku94.Add(0);
            outrageouslyEvilSudoku94.Add(1000030);
            outrageouslyEvilSudoku94.Add(650000000);
            outrageouslyEvilSudoku94.Add(92000080);
            outrageouslyEvilSudoku94.Add(500700009);
            outrageouslyEvilSudoku94.Add(600010);
            outrageouslyEvilSudoku94.Add(300028);

            return twoDConverter(outrageouslyEvilSudoku94);
        }

        if (x == 20)
        {
            List<int> blackBeltSudoku60 = new List<int>();
            blackBeltSudoku60.Add(400800);
            blackBeltSudoku60.Add(180700004);
            blackBeltSudoku60.Add(290003070);
            blackBeltSudoku60.Add(400000500);
            blackBeltSudoku60.Add(39070410);
            blackBeltSudoku60.Add(5000009);
            blackBeltSudoku60.Add(40500031);
            blackBeltSudoku60.Add(900001058);
            blackBeltSudoku60.Add(1008000);

            return twoDConverter(blackBeltSudoku60);
        }

        if (x == 21)
        {
            List<int> fiveStar6 = new List<int>();
            fiveStar6.Add(30008);
            fiveStar6.Add(3268004);
            fiveStar6.Add(6040010);
            fiveStar6.Add(100080032);
            fiveStar6.Add(20000040);
            fiveStar6.Add(340050007);
            fiveStar6.Add(60090200);
            fiveStar6.Add(400876300);
            fiveStar6.Add(900020000);

            return twoDConverter(fiveStar6);
        }

        if (x == 22)
        {
            List<int> blackBeltSudoku59 = new List<int>();
            blackBeltSudoku59.Add(800010054);
            blackBeltSudoku59.Add(700040600);
            blackBeltSudoku59.Add(200500000);
            blackBeltSudoku59.Add(1095);
            blackBeltSudoku59.Add(307000408);
            blackBeltSudoku59.Add(50400000);
            blackBeltSudoku59.Add(3002);
            blackBeltSudoku59.Add(1070009);
            blackBeltSudoku59.Add(920060007);

            return twoDConverter(blackBeltSudoku59);
        }

        if (x == 23)
        {
            List<int> telegraphHardestSudoku = new List<int>();
            telegraphHardestSudoku.Add(800000000);
            telegraphHardestSudoku.Add(3600000);
            telegraphHardestSudoku.Add(70090200);
            telegraphHardestSudoku.Add(50007000);
            telegraphHardestSudoku.Add(45700);
            telegraphHardestSudoku.Add(100030);
            telegraphHardestSudoku.Add(1000068);
            telegraphHardestSudoku.Add(8500010);
            telegraphHardestSudoku.Add(90000400);

            return twoDConverter(telegraphHardestSudoku);
        }

        if (x == 24)
        {
            List<int> crackingAToughClassic = new List<int>();
            crackingAToughClassic.Add(340001000);
            crackingAToughClassic.Add(20009000);
            crackingAToughClassic.Add(500070);
            crackingAToughClassic.Add(3107);
            crackingAToughClassic.Add(680000302);
            crackingAToughClassic.Add(60);
            crackingAToughClassic.Add(8074010);
            crackingAToughClassic.Add(0);
            crackingAToughClassic.Add(9000685);

            return twoDConverter(crackingAToughClassic);
        }
        int[,] other = new int[9, 9];
        return other;
    }

        //converts int[] of integers length 9 into a 2d array
        public static int[,] twoDConverter(List<int> oneD)
    {
        int[,] twoD = new int[9, 9];
        for (int row = 0; row < 9; row++)
        {
            int oneDRow = oneD[row];
            for (int column = 8; column >= 0/*(int)(11 - Math.log(oneD.get(row)))*/; column--)
            {
                twoD[row, column] = oneDRow % 10;
                oneDRow = oneDRow / 10;
            }
        }
        return twoD;

    }

    }
}
