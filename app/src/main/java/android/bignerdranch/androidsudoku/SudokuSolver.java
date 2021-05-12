package android.bignerdranch.androidsudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SudokuSolver {


    //eliminates by rook method
    public boolean RookChecker(SudokuGrid mySudoku) {
        boolean RookCheckerWorks = false;

        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is solved
                if (mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    //for each other element in the row
                    for (int row2 = 0; row2 < 9; row2++) {
                        //if the other element is not solved
                        if (!mySudoku.getSudokuGrid()[row2][column].getSolved()) {
                            //index is index of the solved value in not solved element
                            int index = mySudoku.getSudokuGrid()[row2][column].indexOf(mySudoku.getSudokuGrid()[row][column].
                                    getVal());

                            //if not solved element has solved value
                            if (index != -1) {
                                mySudoku.getSudokuGrid()[row2][column].remove(index);
                                //checkerMethodOneWorks = true;
                                //checkerMethodOneWorks =
                                RookChecker(mySudoku);
                                //checkerMethodOneWorks =
                                BoxChecker(mySudoku);
                            }
                        }
                    }
                    //for each other element in the column
                    for (int column2 = 0; column2 < 9; column2++) {
                        //if the other element is not solved
                        if (!mySudoku.getSudokuGrid()[row][column2].getSolved()) {
                            //index is index of the solved value in not solved element
                            int index = mySudoku.getSudokuGrid()[row][column2].indexOf(mySudoku.getSudokuGrid()[row][column].
                                    getVal());

                            //if not solved element has solved value
                            if (index != -1) {
                                mySudoku.getSudokuGrid()[row][column2].remove(index);

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
    public boolean BoxChecker(SudokuGrid mySudoku) {
        boolean boxCheckerWorks = false;

        //for each solved cell in main array
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is solved
                if (mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    int boxColumn = column / 3;
                    int boxRow = row / 3;

                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                            //if the other element is not solved
                            if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                //index is index of the solved value in not solved element
                                int index = mySudoku.getSudokuGrid()[row2][column2].indexOf(mySudoku.getSudokuGrid()[row][column].
                                        getVal());

                                if (index != -1) {
                                    mySudoku.getSudokuGrid()[row2][column2].remove(index);
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
    public boolean OnlyCandidateLeftRookChecker(SudokuGrid mySudoku) {
        boolean onlyCandidateLeftRookCheckerWorks = false;
        //check each column
        for (int column = 0; column < 9; column++) {
            //for each possible integer
            for (int i = 1; i <= 9; i++) {
                int num = 0;
                int index = -1;
                //for each row
                for (int row = 0; row < 9; row++) {
                    //if not solved
                    if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {

                        if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                            num++;
                            index = row;
                        }
                    }
                }
                if (num == 1) {
                    mySudoku.getSudokuGrid()[index][column].solve(i);
                    RookChecker(mySudoku);
                    BoxChecker(mySudoku);
                    onlyCandidateLeftRookCheckerWorks = true;
                    //onlyCandidateLeftRookCheckerWorks = OnlyCandidateLeftBoxChecker(mySudoku);
                }
            }
        }

        //check each row
        for (int row = 0; row < 9; row++) {
            //for each possible integer
            for (int i = 1; i <= 9; i++) {
                int num = 0;
                int index = -1;
                //for each column
                for (int column = 0; column < 9; column++) {
                    //if not solved
                    if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {
                        if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                            num++;
                            index = column;
                        }
                    }
                }
                if (num == 1) {
                    mySudoku.getSudokuGrid()[row][index].solve(i);
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
    public boolean OnlyCandidateLeftBoxChecker(SudokuGrid mySudoku) {
        boolean OnlyCandidateLeftBoxCheckerWorks = false;
        //for each box row
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            //for each box column
            for (int boxColumn = 0; boxColumn < 3; boxColumn++) {
                //for each integer possible
                for (int i = 1; i <= 9; i++) {
                    int num = 0;
                    int rowIndex = -1;
                    int columnIndex = -1;
                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                            //if the element is not solved
                            if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                //if it contains i
                                if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                    num++;
                                    rowIndex = row2;
                                    columnIndex = column2;
                                }
                            }
                        }
                    }
                    //if only one cell in the box has that candidate, solve it
                    if (num == 1) {
                        mySudoku.getSudokuGrid()[rowIndex][columnIndex].solve(i);
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
    public boolean NakedCandidateRookChecker(SudokuGrid mySudoku) {
        boolean candidatePairRookCheckerWorks = false;
        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is unsolved
                if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    //for each other element in the column
                    int numSame = 0;
                    ArrayList<Integer> rowVals = new ArrayList<Integer>();

                    for (int row2 = 0; row2 < 9; row2++) {
                        //if the other element has same candidates
                        if (mySudoku.getSudokuGrid()[row2][column].samePossible(mySudoku.getSudokuGrid()[row][column])) {
                            numSame++;
                            rowVals.add(row2);
                        }
                    }
                    //if the number of cells with same possibles equals number of possibles per cell
                    if (numSame == mySudoku.getSudokuGrid()[row][column].size()) {
                        //for each other element in the column
                        for (int row2 = 0; row2 < 9; row2++) {
                            if (!rowVals.contains(row2)) {
                                for (int possibleIndex = 0; possibleIndex < mySudoku.getSudokuGrid()[row][column].
                                        size();
                                     possibleIndex++) {
                                    if (mySudoku.getSudokuGrid()[row2][column].indexOf(mySudoku.getSudokuGrid()[row][column].
                                            getVal(possibleIndex)) != -1) {
                                        mySudoku.getSudokuGrid()[row2][column].remove(mySudoku.getSudokuGrid()[row2][column].
                                                indexOf(mySudoku.getSudokuGrid()[row][column].getVal(possibleIndex)));
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
                    ArrayList<Integer> columnVals = new ArrayList<Integer>();
                    numSame = 0;
                    for (int column2 = 0; column2 < 9; column2++) {
                        //if the other element is not solved
                        if (mySudoku.getSudokuGrid()[row][column2].
                                samePossible(mySudoku.getSudokuGrid()[row][column2])) {
                            numSame++;
                            columnVals.add(column2);
                        }
                    }
                    //if the number of cells with same possibles equals number of possibles per cell
                    if (numSame == mySudoku.getSudokuGrid()[row][column].size()) {
                        //for each other element in that row
                        for (int column2 = 0; column2 < 9; column2++) {
                            if (!columnVals.contains(column2)) {
                                for (int possibleIndex = 0; possibleIndex < mySudoku.getSudokuGrid()[row][column].
                                        size();
                                     possibleIndex++) {
                                    if (mySudoku.getSudokuGrid()[row][column2].
                                            indexOf(mySudoku.getSudokuGrid()[row][column].
                                                    getVal(possibleIndex)) != -1) {
                                        mySudoku.getSudokuGrid()[row][column2].remove(mySudoku.getSudokuGrid()[row][column2].
                                                indexOf(mySudoku.getSudokuGrid()[row][column].getVal(possibleIndex)));
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
    public boolean NakedCandidateBoxChecker(SudokuGrid mySudoku) {
        boolean candidatePairBoxCheckerWorks = false;
        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is unsolved
                if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    int numSame = 0;
                    ArrayList<Integer> rowVals = new ArrayList<Integer>();
                    ArrayList<Integer> columnVals = new ArrayList<Integer>();

                    int boxRow = row / 3;
                    int boxColumn = column / 3;


                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                            //if the element is not solved
                            if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                //if it has same possibles
                                if (mySudoku.getSudokuGrid()[row2][column2].samePossible(mySudoku.getSudokuGrid()[row][column])) {
                                    numSame++;
                                    rowVals.add(row2);
                                    columnVals.add(column2);
                                }
                            }
                        }
                    }

                    //if the number of cells with same possibles equals number of possibles per cell
                    if (numSame == mySudoku.getSudokuGrid()[row][column].size()) {

                        //for each other element in that box
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                                //if the box was not one of the ones that had same pair
                                if (!columnVals.contains(column2) || !rowVals.contains(row2)) {

                                    for (int possibleIndex = 0; possibleIndex < mySudoku.getSudokuGrid()[row][column].
                                            size();
                                         possibleIndex++) {
                                        //if the other cell Contains that possibility
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(mySudoku.getSudokuGrid()[row][column].
                                                getVal(possibleIndex))) {
                                            //remove that possibility from the other cell
                                            //printBoard(mySudoku, true);
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(mySudoku.getSudokuGrid()[row][column].getVal(possibleIndex)));
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
    public boolean HiddenCandidatePairChecker(SudokuGrid mySudoku) {
        boolean hiddenCandidatePairCheckerWorks = false;
        //find in a row
        for (int row = 0; row < 9; row++) {
            //for each candidate i
            for (int i = 1; i <= 9; i++) {
                //num is number of appearances of that candidate
                int num = 0;
                int iColumnCoord1 = -1;
                int iColumnCoord2 = -1;

                for (int column = 0; column < 9; column++) {
                    //if that cell contains the candidate
                    if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                        iColumnCoord2 = iColumnCoord1;
                        iColumnCoord1 = column;
                        num++;
                    }
                }
                //if 2 possibles for the first candidate
                if (num == 2) {
                    //find second candidate
                    for (int k = i + 1; k <= 9; k++) {
                        //num for second pair
                        int numK = 0;
                        int kColumnCoord1 = -1;
                        int kColumnCoord2 = -1;
                        for (int column = 0; column < 9; column++) {
                            //if that cell contains the candidate
                            if (mySudoku.getSudokuGrid()[row][column].contains(k)) {
                                kColumnCoord2 = kColumnCoord1;
                                kColumnCoord1 = column;
                                numK++;
                            }
                        }
                        //if pair for second candidate
                        if (numK == 2) {
                            //if coord of both pairs are same
                            if (kColumnCoord1 == iColumnCoord1 && kColumnCoord2 == iColumnCoord2) {
                                //remove all other candidates from both cells
                                for (int j = 1; j <= 9; j++) {
                                    //i and k should be the two candidates
                                    if (j != i && j != k) {
                                        //removal
                                        if (mySudoku.getSudokuGrid()[row][kColumnCoord1].contains(j)) {
                                            mySudoku.getSudokuGrid()[row][kColumnCoord1]
                                                    .remove(mySudoku.getSudokuGrid()[row][kColumnCoord1].indexOf(j));
                                            RookChecker(mySudoku);
                                            BoxChecker(mySudoku);
                                        }
                                        if (mySudoku.getSudokuGrid()[row][kColumnCoord2].contains(j)) {
                                            mySudoku.getSudokuGrid()[row][kColumnCoord2].
                                                    remove(mySudoku.getSudokuGrid()[row][kColumnCoord2].indexOf(j));
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
        for (int column = 0; column < 9; column++) {
            //for each candidate i
            for (int i = 1; i <= 9; i++) {
                //num is number of appearances of that candidate
                int num = 0;
                int iRowCoord1 = -1;
                int iRowCoord2 = -1;

                for (int row = 0; row < 9; row++) {
                    //if that cell contains the candidate
                    if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                        iRowCoord2 = iRowCoord1;
                        iRowCoord1 = row;
                        num++;
                    }
                }
                //if 2 possibles for the first candidate
                if (num == 2) {
                    //find second candidate
                    for (int k = i + 1; k <= 9; k++) {
                        //num for second pair
                        int numK = 0;
                        int kRowCoord1 = -1;
                        int kRowCoord2 = -1;
                        for (int row = 0; row < 9; row++) {
                            //if that cell contains the candidate
                            if (mySudoku.getSudokuGrid()[row][column].contains(k)) {
                                kRowCoord2 = kRowCoord1;
                                kRowCoord1 = row;
                                numK++;
                            }
                        }
                        //if pair for second candidate
                        if (numK == 2) {
                            //if coord of both pairs are same
                            if (kRowCoord1 == iRowCoord1 && kRowCoord2 == iRowCoord2) {
                                //remove all other candidates from both cells
                                for (int j = 1; j <= 9; j++) {
                                    //i and k should be the two candidates remaining
                                    if (j != i && j != k) {
                                        //removal
                                        if (mySudoku.getSudokuGrid()[kRowCoord1][column].contains(j)) {
                                            mySudoku.getSudokuGrid()[kRowCoord1][column].
                                                    remove(mySudoku.getSudokuGrid()[kRowCoord1][column].indexOf(j));
                                            RookChecker(mySudoku);
                                            BoxChecker(mySudoku);
                                        }
                                        if (mySudoku.getSudokuGrid()[kRowCoord2][column].contains(j)) {
                                            mySudoku.getSudokuGrid()[kRowCoord2][column].
                                                    remove(mySudoku.getSudokuGrid()[kRowCoord2][column].indexOf(j));
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
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxColumn = 0; boxColumn < 3; boxColumn++) {
                //for each candidate i
                for (int i = 1; i <= 9; i++) {
                    //num is number of appearances of that candidate
                    int num = 0;
                    int iRowCoord1 = -1;
                    int iRowCoord2 = -1;
                    int iColumnCoord1 = -1;
                    int iColumnCoord2 = -1;

                    //for each row in the small box
                    for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                        //for each column in the small box
                        for (int column = boxColumn * 3; column < boxColumn * 3 + 3; column++) {
                            //if that cell contains the candidate
                            if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                                iRowCoord2 = iRowCoord1;
                                iRowCoord1 = row;
                                iColumnCoord2 = iColumnCoord1;
                                iColumnCoord1 = column;
                                num++;
                            }
                        }
                    }
                    //if 2 possibles for the first candidate
                    if (num == 2) {
                        //find second candidate
                        for (int k = i + 1; k <= 9; k++) {
                            //num for second pair
                            int numK = 0;
                            int kRowCoord1 = -1;
                            int kRowCoord2 = -1;
                            int kColumnCoord1 = -1;
                            int kColumnCoord2 = -1;

                            //for each row in the small box
                            for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                                //for each column in the small box
                                for (int column = boxColumn * 3; column < boxColumn * 3 + 3; column++) {
                                    //if that cell contains the candidate
                                    if (mySudoku.getSudokuGrid()[row][column].contains(k)) {
                                        kRowCoord2 = iRowCoord1;
                                        kRowCoord1 = row;
                                        kColumnCoord2 = iColumnCoord1;
                                        kColumnCoord1 = column;
                                        numK++;
                                    }
                                }
                            }
                            //if pair for second candidate
                            if (numK == 2) {
                                //if coord of both pairs are same
                                if (kRowCoord1 == iRowCoord1 && kRowCoord2 == iRowCoord2 && kColumnCoord1 == iColumnCoord1 && kColumnCoord2 == iColumnCoord2) {
                                    //remove all other candidates from both cells
                                    for (int j = 1; j <= 9; j++) {
                                        //i and k should be the two candidates
                                        if (j != i && j != k) {
                                            //removal
                                            if (mySudoku.getSudokuGrid()[kRowCoord1][kColumnCoord1].contains(j)) {
                                                mySudoku.getSudokuGrid()[kRowCoord1][kColumnCoord1].
                                                        remove(mySudoku.getSudokuGrid()[kRowCoord1][kColumnCoord1].
                                                                indexOf(j));
                                                RookChecker(mySudoku);
                                                BoxChecker(mySudoku);
                                            }
                                            if (mySudoku.getSudokuGrid()[kRowCoord2][kColumnCoord2].contains(j)) {
                                                mySudoku.getSudokuGrid()[kRowCoord2][kColumnCoord2].
                                                        remove(mySudoku.getSudokuGrid()[kRowCoord2][kColumnCoord2].
                                                                indexOf(j));
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
    public ArrayList<Integer> FindHiddenCandidatesPair(ArrayList<SudokCell> candidateSet) {
        return new ArrayList<Integer>();
    }

    //method for candidate lines (only place in a box where candidate must go is in a line, eliminate candidate from that line outside the box)
    public boolean CandidateLinesChecker(SudokuGrid mySudoku) {
        boolean candidateLinesCheckerWorks = false;
        //for each big box
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxColumn = 0; boxColumn < 3; boxColumn++) {
                //for each candidate
                for (int i = 1; i <= 9; i++) {
                    ArrayList<Integer> rowVals = new ArrayList<Integer>();
                    ArrayList<Integer> columnVals = new ArrayList<Integer>();
                    int numHasCandidate = 0;
                    boolean removedACandidate = false;

                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                            //if the element is not solved
                            if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                //if it contains the candidate integer
                                if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                    numHasCandidate++;
                                    rowVals.add(row2);
                                    columnVals.add(column2);
                                }
                            }
                        }
                    }
                    //if number of cells that has that candidate in a box is 3 or lower
                    if (numHasCandidate <= 3) {
                        //if all in same row
                        boolean allInSameRow = false;
                        //if 2 in same row
                        if (rowVals.size() == 2) {
                            if (rowVals.get(0) == rowVals.get(1)) {
                                allInSameRow = true;
                            }
                        }
                        //if 3 in same row
                        if (rowVals.size() == 3) {
                            if (rowVals.get(0) == rowVals.get(1) && rowVals.get(1) == rowVals.get(2)) {
                                allInSameRow = true;
                            }
                        }
                        if (allInSameRow) {
                            //eliminate values along that row
                            for (int column2 = 0; column2 < 9; column2++) {
                                //if outside of the box (not one of the previously selected)
                                if (!columnVals.contains(column2)) {
                                    //if it contains the candidate
                                    if (mySudoku.getSudokuGrid()[rowVals.get(0)][column2].contains(i)) {
                                        //remove that candidate
                                        mySudoku.getSudokuGrid()[rowVals.get(0)][column2].
                                                remove(mySudoku.getSudokuGrid()[rowVals.get(0)][column2].indexOf(i));
                                        removedACandidate = true;
                                    }
                                }
                            }
                        }


                        //if all in same column
                        boolean allInSameColumn = false;
                        //if 2 in same column
                        if (columnVals.size() == 2) {
                            if (columnVals.get(0) == columnVals.get(1)) {
                                allInSameColumn = true;
                            }
                        }
                        //if 3 in same column
                        if (columnVals.size() == 3) {
                            if (columnVals.get(0) == columnVals.get(1) && columnVals.get(1) == columnVals.get(2)) {
                                allInSameColumn = true;
                            }
                        }
                        if (allInSameColumn) {
                            //eliminate values along that column
                            for (int row2 = 0; row2 < 9; row2++) {
                                //if outside of the box (not one of the previously selected)
                                if (!rowVals.contains(row2)) {
                                    //if it contains the candidate
                                    if (mySudoku.getSudokuGrid()[row2][columnVals.get(0)].contains(i)) {
                                        //remove that candidate
                                        mySudoku.getSudokuGrid()[row2][columnVals.get(0)].
                                                remove(mySudoku.getSudokuGrid()[row2][columnVals.get(0)].indexOf(i));
                                        removedACandidate = true;
                                    }
                                }
                            }
                        }
                        //if a candidate was removed, run through box and rook checker
                        if (removedACandidate) {
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

    public boolean pointingPairRookToBoxChecker(SudokuGrid mySudoku) {
        boolean pointingPairRookToBoxWorks = false;
        //check rows
        for (int row = 0; row < 9; row++) {
            //for candidate i
            for (int i = 1; i <= 9; i++) {
                int num = 0;
                int columnCoord1 = -1;
                int columnCoord2 = -1;
                int columnCoord3 = -1;

                for (int column = 0; column < 9; column++) {
                    if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                        columnCoord1 = columnCoord2;
                        columnCoord2 = columnCoord3;
                        columnCoord3 = column;
                        num++;
                    }
                }
                //if the number of cells in that line with that candidate is 3
                if (num == 3) {
                    //if cells in the same box
                    if (columnCoord1 / 3 == columnCoord2 / 3 && columnCoord2 / 3 == columnCoord3 / 3) {
                        //remove i from rest of box
                        int boxRow = row / 3;
                        int boxColumn = columnCoord1 / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                                //if none of the three
                                if (!(columnCoord1 == column2 && row == row2) && !(columnCoord2 == column2 && row == row2) && !(columnCoord3 == column2 && row == row2)) {
                                    if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));
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
                if (num == 2) {
                    //if cells in the same box
                    if (columnCoord2 / 3 == columnCoord3 / 3) {
                        //remove i from rest of box
                        int boxRow = row / 3;
                        int boxColumn = columnCoord2 / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                                //if none of the two
                                if (!(columnCoord2 == column2 && row == row2) && !(columnCoord3 == column2 && row == row2)) {
                                    //if other cell is not solved
                                    if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));
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
        for (int column = 0; column < 9; column++) {
            //for candidate i
            for (int i = 1; i <= 9; i++) {
                int num = 0;
                int rowCoord1 = -1;
                int rowCoord2 = -1;
                int rowCoord3 = -1;

                for (int row = 0; row < 9; row++) {
                    if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                        rowCoord1 = rowCoord2;
                        rowCoord2 = rowCoord3;
                        rowCoord3 = row;
                        num++;
                    }
                }
                //if the number of cells in that line with that candidate is 3
                if (num == 3) {
                    //if cells in the same box
                    if (rowCoord1 / 3 == rowCoord2 / 3 && rowCoord2 / 3 == rowCoord3 / 3) {
                        //remove i from rest of box
                        int boxRow = rowCoord1 / 3;
                        int boxColumn = column / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                                //if none of the three
                                if (!(rowCoord1 == row2 && column == column2) && !(rowCoord2 == row2 && column == column2) && !(rowCoord3 == row2 && column == column2)) {
                                    if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));
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
                if (num == 2) {
                    //if cells in the same box
                    if (rowCoord2 / 3 == rowCoord3 / 3) {
                        //remove i from rest of box
                        int boxRow = rowCoord2 / 3;
                        int boxColumn = column / 3;
                        //for each row in the small box
                        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                            //for each column in the small box
                            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                                //if none of the two
                                if (!(rowCoord2 == row2 && column == column2) && !(rowCoord3 == row2 && column == column2)) {
                                    //if other cell is not solved
                                    if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));
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
    public boolean forcingChainsChecker(SudokuGrid mySudoku) {
        //System.out.println("I was called");
        boolean forcingChainsCheckerWorks = false;
        //for each unsolved cell
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if unsolved
                if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    //setup
                    int numCands = mySudoku.getSudokuGrid()[row][column].getPossibles().size();

                    //boolean array, set initially to true if the corresponding cell is unsolved
                    boolean[][] sameSolved = new boolean[9][9];
                    for (int solvedRow = 0; solvedRow < 9; solvedRow++) {
                        for (int solvedColumn = 0; solvedColumn < 9; solvedColumn++) {
                            sameSolved[solvedRow][solvedColumn] = true;
                            if (mySudoku.getSudokuGrid()[solvedRow][solvedColumn].getSolved()) {
                                sameSolved[solvedRow][solvedColumn] = false;
                            }
                        }
                    }

                    //first guess
                    SudokuGrid copy1 = Copy(mySudoku);

                    copy1.getSudokuGrid()[row][column].
                            solve(mySudoku.getSudokuGrid()[row][column].getPossibles().get(0));

                    if (solveForForcingChains(copy1)) {
                        mySudoku.getSudokuGrid()[row][column].remove(mySudoku.getSudokuGrid()[row][column].
                                indexOf(mySudoku.getSudokuGrid()[row][column].getPossibles().get(0)));
                        return true;
                    }
                    if (numUnsolved(copy1) == 0 && !IsSolved(copy1, false)) {
                        mySudoku.getSudokuGrid()[row][column].remove(mySudoku.getSudokuGrid()[row][column].
                                indexOf(mySudoku.getSudokuGrid()[row][column].getPossibles().get(0)));
                        return true;
                    }
                    for (int row2 = 0; row2 < 9; row2++) {
                        for (int column2 = 0; column2 < 9; column2++) {
                            if (!copy1.getSudokuGrid()[row2][column2].getSolved()) {
                                sameSolved[row2][column2] = false;
                            }
                        }
                    }




                    //all other guesses
                    for (int candidateIndex = 1; candidateIndex < numCands; candidateIndex++) {
                        SudokuGrid copy = Copy(mySudoku);
                        copy.getSudokuGrid()[row][column].
                                solve(mySudoku.getSudokuGrid()[row][column].getPossibles().get(candidateIndex))
                        ;
                        if (solveForForcingChains(copy)) {
                            mySudoku.getSudokuGrid()[row][column].remove(mySudoku.getSudokuGrid()[row][column].
                                    indexOf(mySudoku.getSudokuGrid()[row][column].getPossibles().get(candidateIndex)));
                            return true;
                        }
                        if (numUnsolved(copy) == 0 && !IsSolved(copy, false)) {
                            mySudoku.getSudokuGrid()[row][column].remove(mySudoku.getSudokuGrid()[row][column].
                                    indexOf(mySudoku.getSudokuGrid()[row][column].getPossibles().get(candidateIndex)));
                            return true;
                        }

                        for (int row2 = 0; row2 < 9; row2++) {
                            for (int column2 = 0; column2 < 9; column2++) {
                                if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                    if (copy.getSudokuGrid()[row2][column2].getSolved()) {
                                        if (!copy.getSudokuGrid()[row2][column2].samePossible(copy1.getSudokuGrid()[row2][column2])) {
                                            sameSolved[row2][column2] = false;
                                        }
                                    }
                                }
                                if (!copy.getSudokuGrid()[row2][column2].getSolved()) {
                                    sameSolved[row2][column2] = false;
                                }
                            }
                        }
                    }
                    for (int row2 = 0; row2 < 9; row2++) {
                        for (int column2 = 0; column2 < 9; column2++) {
                            if (!mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                                if (sameSolved[row2][column2]) {
                                    if (copy1.getSudokuGrid()[row2][column2].getSolved()) {
                                        mySudoku.getSudokuGrid()[row2][column2].solve(copy1.getSudokuGrid()[row2][column2].
                                                getVal())
                                        ;
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
    public boolean solveForForcingChains(SudokuGrid mySudoku) {
        try {
            for (int i = 0; i < 10; i++) {
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
        catch (Exception e) {
            return true;
        }
        //return false;

        boolean forcingChainsCheckerWorks = false;

        if (InvalidMove(mySudoku)) {
            return true;
        }
        if (!IsSolved(mySudoku, false)) {
            forcingChainsCheckerWorks = forcingChainsChecker(mySudoku);
        }


        if (forcingChainsCheckerWorks) {
            solveForForcingChains(mySudoku);
        }
        return false;
    }

    //make a copy of values
    public SudokuGrid Copy(SudokuGrid mySudoku) {

        SudokuGrid mySudoku2 = new SudokuGrid();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                SudokCell sudokCell = new SudokCell();
                SudokCell x = mySudoku.getSudokuGrid()[row][column];
                mySudoku2.getSudokuGrid()[row][column] = sudokCell.Clone(x);

            }
        }
        return mySudoku2;
    }

    //check if the sudoku is solved
    public boolean IsSolved(SudokuGrid mySudoku) {
        return IsSolved(mySudoku, false);
    }

    //check if the sudoku is solved
    public boolean IsSolved(SudokuGrid mySudoku, boolean printChecks) {
        //checks each box is solved
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if unsolved, return false
                if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    return false;
                }
            }
        }

        //two for loops to go through each row, check no duplicates
        for (int row = 0; row < 9; row++) {
            ArrayList myList = new ArrayList<Integer>();
            int numTotal = 0;
            for (int column = 0; column < 9; column++) {
                myList.add(mySudoku.getSudokuGrid()[row][column].getVal());
            }
            if (ContainsDuplicate(myList)) {
                return false;
            }
        }

        //if(printChecks) System.out.println("Rows add up");
        //two for loops to go through each column, check no duplicates
        for (int column = 0; column < 9; column++) {
            ArrayList myList = new ArrayList<Integer>();
            int numTotal = 0;
            for (int row = 0; row < 9; row++) {
                myList.add(mySudoku.getSudokuGrid()[row][column].getVal());
            }
            if (ContainsDuplicate(myList)) {
                return false;
            }
        }
        //if(printChecks) System.out.println("Columns add up");

        //check each box, check no duplicates
        //for each box row
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            //for each box column
            for (int boxColumn = 0; boxColumn < 3; boxColumn++) {
                ArrayList myList = new ArrayList<Integer>();
                int numTotal = 0;
                //for each row in the small box
                for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                    //for each column in the small box
                    for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                        myList.add(mySudoku.getSudokuGrid()[row2][column2].getVal());
                    }
                }
                if (ContainsDuplicate(myList)) {
                    return false;
                }
            }
        }
        //if(printChecks) System.out.println("Boxes add up");
        return true;
    }

    //check how many boxes remain unsolved
    public int numUnsolved(SudokuGrid mySudoku) {
        int numUnsolvedB = 81;
        //for each solved cell in main array
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is solved
                if (mySudoku.getSudokuGrid()[row][column].getSolved()) {
                    numUnsolvedB--;
                }
            }
        }
        return numUnsolvedB;
    }

    //solve method
    public void Solve(SudokuGrid mySudoku, boolean forcingChains) {
        for (int i = 0; i < 10; i++) {
            RookChecker(mySudoku);
            BoxChecker(mySudoku);
            OnlyCandidateLeftRookChecker(mySudoku);
            OnlyCandidateLeftBoxChecker(mySudoku);
            NakedCandidateRookChecker(mySudoku);
            NakedCandidateBoxChecker(mySudoku);
            CandidateLinesChecker(mySudoku);
            HiddenCandidatePairChecker(mySudoku);
            pointingPairRookToBoxChecker(mySudoku);
            //forcingChainsChecker(mySudoku);
        }
        boolean forcingChainsCheckerWorks = true;
        if (forcingChains) {
            while(forcingChainsCheckerWorks){
                forcingChainsCheckerWorks = forcingChainsChecker(mySudoku);
            }
        }
    }


    //checks if an invalid move has been made
    public boolean InvalidMove(SudokuGrid mySudoku) {
        //check columns for duplicates
        for (int column = 0; column < 9; column++) {
            ArrayList<Integer> myList = new ArrayList<Integer>();
            for (int row = 0; row < 9; row++) {
                if(mySudoku.getSudokuGrid()[row][column].getVal() != -1) {
                    myList.add(mySudoku.getSudokuGrid()[row][column].getVal());
                }
            }
            if (ContainsDuplicate(myList)) {
                return true;
            }
        }

        //check rows for duplicates
        for (int row = 0; row < 9; row++) {
            ArrayList<Integer> myList = new ArrayList<Integer>();
            for (int column = 0; column < 9; column++) {
                if(mySudoku.getSudokuGrid()[row][column].getVal() != -1) {
                    myList.add(mySudoku.getSudokuGrid()[row][column].getVal());
                }
            }
            if (ContainsDuplicate(myList)) {
                return true;
            }
        }

        //check boxes for duplicates

        //for box coords
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxColumn = 0; boxColumn < 3; boxColumn++) {
                ArrayList<Integer> myList = new ArrayList<Integer>();
                //for each row in the small box
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    //for each column in the small box
                    for (int column = boxColumn * 3; column < boxColumn * 3 + 3; column++) {
                        if(mySudoku.getSudokuGrid()[row][column].getVal() != -1) {
                            myList.add(mySudoku.getSudokuGrid()[row][column].getVal());
                        }
                    }
                }
                if (ContainsDuplicate(myList)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean InvalidCell(SudokuGrid mySudoku, int row, int column) {
        //check columns for duplicates


        for (int row2 = 0; row2 < 9; row2++) {
            if (mySudoku.getSudokuGrid()[row2][column].getSolved()) {
                if (row2 != row && mySudoku.getSudokuGrid()[row2][column].getVal() == mySudoku.getSudokuGrid()[row][column].getVal()) {
                    return true;
                }
            }
        }


        //check rows for duplicates

        for (int column2 = 0; column2 < 9; column2++) {
            if (mySudoku.getSudokuGrid()[row][column2].getSolved()) {
                if (column2 != column && mySudoku.getSudokuGrid()[row][column2].
                        getVal() == mySudoku.getSudokuGrid()[row][column].
                        getVal()) {
                    return true;
                }
            }
        }


        //in box

        int boxRow = row / 3;
        int boxColumn = column / 3;
        //for each row in the small box
        for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
            //for each column in the small box
            for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                if (mySudoku.getSudokuGrid()[row2][column2].getSolved()) {
                    if (column2 != column && row2 != row && mySudoku.getSudokuGrid()[row2][column2].
                            getVal() == mySudoku.getSudokuGrid()[row][column].getVal()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //checks if a List contains a duplicate over the domain [1, 9]
    public boolean ContainsDuplicate(ArrayList<Integer> myList) {
        ArrayList<Integer> results = new ArrayList<Integer>();
        Set<Integer> set = new HashSet<Integer>(myList);

        if (set.size() < myList.size()) {
            return true;
        }
        return false;
    }

    //converts int array to sudokcell sudokugrid
    public SudokuGrid FromIntArray(int[][] myArray) {
        SudokuGrid mySudoku = new SudokuGrid();
        //my sudoku to be worked with
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                mySudoku.getSudokuGrid()[row][column] = new SudokCell(myArray[row][column]);
            }
        }

        return mySudoku;
    }

    //is a sudoku digit helper method
    public boolean isDigit(String input) {
        for (int i = 1; i <= 9; i++) {
            if (input.equals("" + i)) {
                return true;
            }
        }
        return false;
    }

    //Todo: make passed by preference
    public String bruteForceSolver(SudokuGrid mySudoku) {
        if (IsSolved(mySudoku)) {
            return "already solved";
        }
        solveForBruteForce(mySudoku);
        //else, guess all possibles and brute force solve. if multiple solutions, return false
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if unsolved
                if (!mySudoku.getSudokuGrid()[row][column].getSolved()) {

                    for (int i = 0; i < mySudoku.getSudokuGrid()[row][column].getPossibles().size();
                         i++) {

                        SudokuGrid copy = Copy(mySudoku);
                        //solve to the index of the guess
                        copy.getSudokuGrid()[row][column].solve(mySudoku.getSudokuGrid()[row][column].getPossibles().get(i));

                        if (solveForBruteForce(copy)) {
                            mySudoku.getSudokuGrid()[row][column].remove(i);
                            i--;
                            solveForBruteForce(mySudoku);
                        } else if (IsSolved(copy)) {
                            mySudoku = Copy(copy);
                            return "Solved";
                        } else if (InvalidMove(copy)) {
                            try {
                                mySudoku.getSudokuGrid()[row][column].remove(i);
                                i--;
                                solveForBruteForce(mySudoku);
                            } catch (Exception e) {

                            }
                            {
                                return "Mistake Made";
                            }
                            //bruteForceSolver(mySudoku);
                            //return "";
                        } else if (numUnsolved(copy) == 0) {
                            mySudoku.getSudokuGrid()[row][column].remove(i);
                            i--;
                            solveForBruteForce(mySudoku);
                            //bruteForceSolver(mySudoku);
                            //return "";
                        } else //guess again
                        {
                            //brute force it
                            if (bruteForceSolver(copy).equals("Mistake Made")) {
                                mySudoku.getSudokuGrid()[row][column].remove(i);
                                i--;
                                solveForBruteForce(mySudoku);

                            } else if (IsSolved(copy)) {
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

    public boolean solveForBruteForce(SudokuGrid mySudoku) {
        try {
            for (int i = 0; i < 10; i++) {
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
        catch (Exception e) {
            return true;
        }
        return false;
    }

    public void bruteForceSolverOld(SudokuGrid mySudoku) {
        //for each level guess
        SudokuGrid mySudoku2 = Copy(mySudoku);
        for (int k = 0; k < numUnsolved(mySudoku) && !IsSolved(mySudoku, false); k++) {
            //while sudoku2 is unsolved
            int infiniteLoop = 0;
            int i = 1;
            while (!IsSolved(mySudoku2, false) && infiniteLoop < 100) {
                //infiniteLoop++;
                SudokuGrid testCase = Copy(mySudoku2);
                //find i unsolved cell and solve to be random of candidates
                boolean foundUnsolved = false;
                int row = -1;
                int column = 0;
                int num = 0;
                while (!foundUnsolved) {
                    row++;
                    if (row == 9) {
                        row = 0;
                        column++;
                    }
                    if (column == 9) {
                        row = 0;
                        column = 0;
                    }
                    if (!testCase.getSudokuGrid()[row][column].getSolved()) {
                        num++;
                        if (num == i) {
                            foundUnsolved = true;
                        }
                    }
                }
                i++;
                ArrayList<Integer> possibles = testCase.getSudokuGrid()[row][column].getPossibles();
                double rand = Math.random();
                int randomIndex = (int) (Math.random() * possibles.size());
                testCase.getSudokuGrid()[row][column].solve(possibles.get(randomIndex));
                if (i > numUnsolved(testCase)) {
                    //System.out.println("i greater than num unsolved");
                    //System.out.println("numUnsolved " + numUnsolved(testCase));
                    i = 1;
                        /*if(numUnsolved(testCase) != 0){
                            solve(testCase, true);
                        }*/
                }
                //if all cells solved but sudoku not solved
                if (numUnsolved(testCase) == 0 && !IsSolved(testCase, false)) {
                    //System.out.println("restarted brute force");
                    bruteForceSolver(mySudoku);
                }

                for (int j = 0; j < 10; j++) {
                    RookChecker(testCase);
                    BoxChecker(testCase);
                    OnlyCandidateLeftRookChecker(mySudoku);
                    OnlyCandidateLeftBoxChecker(mySudoku);
                    NakedCandidateRookChecker(mySudoku);
                    NakedCandidateBoxChecker(mySudoku);
                    CandidateLinesChecker(mySudoku);
                }
                if (!IsSolved(testCase, false)) {
                    //System.out.println("multiple guesses\n numunsolved " + numUnsolved(testCase));
                    mySudoku2 = Copy(testCase);
                    infiniteLoop = 100;
                }
                //if the test case is properly solved, make sudoku equal testcase
                if (IsSolved(testCase, false)) {
                    //printBoard(testCase, false);
                    for (int roww = 0; roww < 9; roww++) {
                        for (int columnn = 0; columnn < 9; columnn++) {
                            mySudoku.getSudokuGrid()[roww][columnn] = testCase.getSudokuGrid()[roww][columnn];
                        }
                    }
                }
                //if all cells solved but sudoku not solved
                if (numUnsolved(testCase) == 0 && !IsSolved(testCase, false)) {
                    //System.out.println("restarted brute force");
                    bruteForceSolver(mySudoku);
                }
            }
        }
    }

    public boolean equals(SudokuGrid obj, SudokuGrid obj2) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (!obj2.getSudokuGrid()[row][column].equals(obj.getSudokuGrid()[row][column])) {
                    return false;
                }
            }
        }
        return true;
    }

    public int RateDifficulty(SudokuGrid mySudoku) {
        if (!mySudoku.IsValid()) {
            return 0;
        }
        //level 1
        boolean currentWorks = true;
        RookChecker(mySudoku);
        BoxChecker(mySudoku);
        if (mySudoku.IsSolved()) {
            return 1;
        }


        //level 2
        while (currentWorks) {
            currentWorks = OnlyCandidateLeftRookChecker(mySudoku);
            if (!currentWorks) {
                currentWorks = OnlyCandidateLeftBoxChecker(mySudoku);
            }
        }
        if (mySudoku.IsSolved()) {
            return 2;
        }


        //level 3
        currentWorks = true;
        while (currentWorks) {
            currentWorks = NakedCandidateRookChecker(mySudoku);
            if (!currentWorks) {
                currentWorks = NakedCandidateBoxChecker(mySudoku);
            }
            if (!currentWorks) {
                currentWorks = OnlyCandidateLeftRookChecker(mySudoku);
            }
            if (!currentWorks) {
                currentWorks = OnlyCandidateLeftBoxChecker(mySudoku);
            }
        }
        if (mySudoku.IsSolved()) {
            return 3;
        }


        //level 4
        currentWorks = true;
        while (currentWorks) {
            currentWorks = CandidateLinesChecker(mySudoku);
            if (!currentWorks) {
                HiddenCandidatePairChecker(mySudoku);
            }
            if (!currentWorks) {
                pointingPairRookToBoxChecker(mySudoku);
            }
            if (!currentWorks) {
                currentWorks = NakedCandidateRookChecker(mySudoku);
            }
            if (!currentWorks) {
                currentWorks = NakedCandidateBoxChecker(mySudoku);
            }
            if (!currentWorks) {
                currentWorks = OnlyCandidateLeftRookChecker(mySudoku);
            }
            if (!currentWorks) {
                currentWorks = OnlyCandidateLeftBoxChecker(mySudoku);
            }
        }
        if (mySudoku.IsSolved()) {
            return 4;
        }


        //level 5
        Solve(mySudoku, true);
        if (mySudoku.IsSolved()) {
            return 5;
        }


        //level 6
        bruteForceSolver(mySudoku);
        if (mySudoku.IsSolved()) {
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
    public int[][] input(int x) {

        if (x == 1) {
            int[][]beginner = {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
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
        if (x == 2) {
            int[][]normal = {{0, 0, 0, 0, 9, 0, 0, 0, 0},
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
        if (x == 3) {
            int[][]hard = {{0, 8, 0, 0, 0, 0, 0, 4, 0},
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
        if (x == 4) {
            int[][]expert = {{0, 0, 7, 0, 0, 0, 6, 3, 0},
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
        if (x == 5) {
            int[][]expert2 = {{0, 9, 1, 0, 0, 0, 0, 0, 0},
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
        if (x == 6) {
            int[][]fiveStar = {{0, 5, 0, 0, 1, 3, 0, 0, 0},
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
        if (x == 7) {
            int[][]fiveStar2 = {{0, 0, 6, 0, 0, 0, 0, 4, 0},
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
        if (x == 8) {
            int[][]fiveStar3 = {{8, 0, 0, 0, 5, 6, 0, 0, 0},
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


        if (x == 9) {
            ArrayList<Integer> onlineSudokuHard = new ArrayList<Integer>();
            onlineSudokuHard.add(204010);
            onlineSudokuHard.add(174030000);
            onlineSudokuHard.add(180049);
            onlineSudokuHard.add(40826);
            onlineSudokuHard.add(60000050);
            onlineSudokuHard.add(43060071);
            onlineSudokuHard.add(400050000);
            onlineSudokuHard.add(80410090);
            onlineSudokuHard.add(90608030);

            return twoDConverter(onlineSudokuHard);
        }
        if (x == 10) {
            ArrayList<Integer> fiveStar4 = new ArrayList<Integer>();
            fiveStar4.add(90008);
            fiveStar4.add(41005000);
            fiveStar4.add(60070300);
            fiveStar4.add(602510000);
            fiveStar4.add(403020501);
            fiveStar4.add(37206);
            fiveStar4.add(4050020);
            fiveStar4.add(900130);
            fiveStar4.add(500080000);

            return twoDConverter(fiveStar4);
        }
        if (x == 11) {
            ArrayList<Integer> fiveStar5 = new ArrayList<Integer>();
            fiveStar5.add(310006900);
            fiveStar5.add(200090000);
            fiveStar5.add(98030100);
            fiveStar5.add(41000);
            fiveStar5.add(60879040);
            fiveStar5.add(520000);
            fiveStar5.add(1080490);
            fiveStar5.add(60003);
            fiveStar5.add(9400028);

            return twoDConverter(fiveStar5);
        }
        if (x == 12) {
            ArrayList<Integer> outrageouslyEvilSudoku100 = new ArrayList<Integer>();
            outrageouslyEvilSudoku100.add(904208001);
            outrageouslyEvilSudoku100.add(20000000);
            outrageouslyEvilSudoku100.add(60103500);
            outrageouslyEvilSudoku100.add(80090006);
            outrageouslyEvilSudoku100.add(703000040);
            outrageouslyEvilSudoku100.add(600070000);
            outrageouslyEvilSudoku100.add(0);
            outrageouslyEvilSudoku100.add(410080600);
            outrageouslyEvilSudoku100.add(6057);

            return twoDConverter(outrageouslyEvilSudoku100);
        }
        if (x == 13) {
            ArrayList<Integer> expertSudokuPartiallySolved = new ArrayList<Integer>();
            expertSudokuPartiallySolved.add(3468159);
            expertSudokuPartiallySolved.add(869715423);
            expertSudokuPartiallySolved.add(154923608);
            expertSudokuPartiallySolved.add(200096);
            expertSudokuPartiallySolved.add(600800012);
            expertSudokuPartiallySolved.add(600784);
            expertSudokuPartiallySolved.add(376241);
            expertSudokuPartiallySolved.add(194865);
            expertSudokuPartiallySolved.add(416582937);

            return twoDConverter(expertSudokuPartiallySolved);
        }
        if (x == 14) {
            ArrayList<Integer> outrageouslyEvilSudoku99 = new ArrayList<Integer>();
            outrageouslyEvilSudoku99.add(600000700);
            outrageouslyEvilSudoku99.add(9003000);
            outrageouslyEvilSudoku99.add(340080090);
            outrageouslyEvilSudoku99.add(704000508);
            outrageouslyEvilSudoku99.add(60950000);
            outrageouslyEvilSudoku99.add(2100600);
            outrageouslyEvilSudoku99.add(7);
            outrageouslyEvilSudoku99.add(400500300);
            outrageouslyEvilSudoku99.add(17000085);

            return twoDConverter(outrageouslyEvilSudoku99);
        }
        if (x == 15) {
            ArrayList<Integer> outrageouslyEvilSudoku98 = new ArrayList<Integer>();
            outrageouslyEvilSudoku98.add(490860);
            outrageouslyEvilSudoku98.add(10000500);
            outrageouslyEvilSudoku98.add(500000000);
            outrageouslyEvilSudoku98.add(109807004);
            outrageouslyEvilSudoku98.add(40600);
            outrageouslyEvilSudoku98.add(5012000);
            outrageouslyEvilSudoku98.add(20000090);
            outrageouslyEvilSudoku98.add(906000200);
            outrageouslyEvilSudoku98.add(83);

            return twoDConverter(outrageouslyEvilSudoku98);
        }
        if (x == 16) {
            ArrayList<Integer> outrageouslyEvilSudoku97 = new ArrayList<Integer>();
            outrageouslyEvilSudoku97.add(200005060);
            outrageouslyEvilSudoku97.add(4600800);
            outrageouslyEvilSudoku97.add(30700000);
            outrageouslyEvilSudoku97.add(98020);
            outrageouslyEvilSudoku97.add(900001000);
            outrageouslyEvilSudoku97.add(78000004);
            outrageouslyEvilSudoku97.add(80040000);
            outrageouslyEvilSudoku97.add(650);
            outrageouslyEvilSudoku97.add(100000003);

            return twoDConverter(outrageouslyEvilSudoku97);
        }
        if (x == 17) {
            ArrayList<Integer> outrageouslyEvilSudoku96 = new ArrayList<Integer>();
            outrageouslyEvilSudoku96.add(907100);
            outrageouslyEvilSudoku96.add(70430050);
            outrageouslyEvilSudoku96.add(301000000);
            outrageouslyEvilSudoku96.add(14700000);
            outrageouslyEvilSudoku96.add(70);
            outrageouslyEvilSudoku96.add(96000000);
            outrageouslyEvilSudoku96.add(80007);
            outrageouslyEvilSudoku96.add(200003004);
            outrageouslyEvilSudoku96.add(50000039);
            //test
            return twoDConverter(outrageouslyEvilSudoku96);
        }
        if (x == 18) {
            ArrayList<Integer> outrageouslyEvilSudoku95 = new ArrayList<Integer>();
            outrageouslyEvilSudoku95.add(49200000);
            outrageouslyEvilSudoku95.add(800010);
            outrageouslyEvilSudoku95.add(3);
            outrageouslyEvilSudoku95.add(203056000);
            outrageouslyEvilSudoku95.add(400000000);
            outrageouslyEvilSudoku95.add(900040051);
            outrageouslyEvilSudoku95.add(80002000);
            outrageouslyEvilSudoku95.add(500800);
            outrageouslyEvilSudoku95.add(7300900);

            return twoDConverter(outrageouslyEvilSudoku95);
        }
        if (x == 19) {
            ArrayList<Integer> outrageouslyEvilSudoku94 = new ArrayList<Integer>();
            outrageouslyEvilSudoku94.add(710860000);
            outrageouslyEvilSudoku94.add(68074002);
            outrageouslyEvilSudoku94.add(0);
            outrageouslyEvilSudoku94.add(1000030);
            outrageouslyEvilSudoku94.add(650000000);
            outrageouslyEvilSudoku94.add(92000080);
            outrageouslyEvilSudoku94.add(500700009);
            outrageouslyEvilSudoku94.add(600010);
            outrageouslyEvilSudoku94.add(300028);

            return twoDConverter(outrageouslyEvilSudoku94);
        }

        if (x == 20) {
            ArrayList<Integer> blackBeltSudoku60 = new ArrayList<Integer>();
            blackBeltSudoku60.add(400800);
            blackBeltSudoku60.add(180700004);
            blackBeltSudoku60.add(290003070);
            blackBeltSudoku60.add(400000500);
            blackBeltSudoku60.add(39070410);
            blackBeltSudoku60.add(5000009);
            blackBeltSudoku60.add(40500031);
            blackBeltSudoku60.add(900001058);
            blackBeltSudoku60.add(1008000);

            return twoDConverter(blackBeltSudoku60);
        }

        if (x == 21) {
            ArrayList<Integer> fiveStar6 = new ArrayList<Integer>();
            fiveStar6.add(30008);
            fiveStar6.add(3268004);
            fiveStar6.add(6040010);
            fiveStar6.add(100080032);
            fiveStar6.add(20000040);
            fiveStar6.add(340050007);
            fiveStar6.add(60090200);
            fiveStar6.add(400876300);
            fiveStar6.add(900020000);

            return twoDConverter(fiveStar6);
        }

        if (x == 22) {
            ArrayList<Integer> blackBeltSudoku59 = new ArrayList<Integer>();
            blackBeltSudoku59.add(800010054);
            blackBeltSudoku59.add(700040600);
            blackBeltSudoku59.add(200500000);
            blackBeltSudoku59.add(1095);
            blackBeltSudoku59.add(307000408);
            blackBeltSudoku59.add(50400000);
            blackBeltSudoku59.add(3002);
            blackBeltSudoku59.add(1070009);
            blackBeltSudoku59.add(920060007);

            return twoDConverter(blackBeltSudoku59);
        }

        if (x == 23) {
            ArrayList<Integer> telegraphHardestSudoku = new ArrayList<Integer>();
            telegraphHardestSudoku.add(800000000);
            telegraphHardestSudoku.add(3600000);
            telegraphHardestSudoku.add(70090200);
            telegraphHardestSudoku.add(50007000);
            telegraphHardestSudoku.add(45700);
            telegraphHardestSudoku.add(100030);
            telegraphHardestSudoku.add(1000068);
            telegraphHardestSudoku.add(8500010);
            telegraphHardestSudoku.add(90000400);

            return twoDConverter(telegraphHardestSudoku);
        }

        if (x == 24) {
            ArrayList<Integer> crackingAToughClassic = new ArrayList<Integer>();
            crackingAToughClassic.add(340001000);
            crackingAToughClassic.add(20009000);
            crackingAToughClassic.add(500070);
            crackingAToughClassic.add(3107);
            crackingAToughClassic.add(680000302);
            crackingAToughClassic.add(60);
            crackingAToughClassic.add(8074010);
            crackingAToughClassic.add(0);
            crackingAToughClassic.add(9000685);

            return twoDConverter(crackingAToughClassic);
        }
        int[][] other = new int[9][9];
        return other;
    }

    //converts int[] of integers length 9 into a 2d array
    public static int[][] twoDConverter(ArrayList<Integer> oneD) {
        int[][]twoD = new int[9][9];
        for (int row = 0; row < 9; row++) {
            int oneDRow = oneD.get(row);
            for (int column = 8; column >= 0/*(int)(11 - Math.log(oneD.get(row)))*/; column--) {
                twoD[row][column] =oneDRow % 10;
                oneDRow = oneDRow / 10;
            }
        }
        return twoD;

    }
}
