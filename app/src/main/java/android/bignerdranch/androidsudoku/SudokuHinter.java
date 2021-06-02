package android.bignerdranch.androidsudoku;

import android.content.Context;
import android.mtp.MtpConstants;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/*
public class CoordinateList
{
    private ArrayArrayList<Coordinate> myList;
}*/

public class SudokuHinter {
    //returns coordNum of next solved cell
    public int getNextSolvedCoord(SudokuGrid mySudoku, Context context) {
        SudokuSolver sudokuSolver = new SudokuSolver();

        int coord;
        SudokuGrid copy = sudokuSolver.Copy(mySudoku);
        boolean methodWorks = true;

        while (methodWorks) {
            methodWorks = RookChecker(copy);
            if(!methodWorks){
                methodWorks = BoxChecker(copy);
            }
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "1");
                Toast.makeText(context, "Naked single", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }

        methodWorks = true;
        while (methodWorks) {
            methodWorks = OnlyCandidateLeftRookChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "3");
                Toast.makeText(context, "Hidden single", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }
        methodWorks = true;
        while (methodWorks) {
            methodWorks = OnlyCandidateLeftBoxChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "4");
                Toast.makeText(context, "Hidden single", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }
        methodWorks = true;
        while (methodWorks) {
            methodWorks = NakedCandidateRookChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "5");
                Toast.makeText(context, "Naked pair", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }
        methodWorks = true;
        while (methodWorks) {
            methodWorks = NakedCandidateBoxChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "6");
                Toast.makeText(context, "Naked pair", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }
        methodWorks = true;
        while (methodWorks) {
            methodWorks = CandidateLinesChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "7");
                Toast.makeText(context, "Candidate lines", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }
        methodWorks = true;
        while (methodWorks) {
            methodWorks = HiddenCandidatePairChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "8");
                Toast.makeText(context, "Hidden Pair", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }
        methodWorks = true;
        while (methodWorks) {
            methodWorks = pointingPairRookToBoxChecker(copy);
            coord = getRandCoordNumDifferent(mySudoku, copy);
            if (coord != -1) {
                Log.i("Hint", "9");
                Toast.makeText(context, "Pointing Pair", Toast.LENGTH_SHORT).show();
                return coord;
            }
        }


        return -1;
    }

    //returns a random coord num of a coord where the two inputted sudokus have different cell vals
    public int getRandCoordNumDifferent(SudokuGrid sudok1, SudokuGrid sudok2) {
        ArrayList<Integer> coordList = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            coordList.add(i);
        }
        Collections.shuffle(coordList);
        for (int i : coordList) {
            int row = i / 9;
            int column = i % 9;
            if (sudok1.getSudokCell(row, column).getVal() != sudok2.getSudokCell(row, column).getVal()) {
                return i;
            }
        }
        return -1;
    }

    //eliminates by rook method
    public boolean RookChecker(SudokuGrid mySudoku) {
        boolean RookCheckerWorks = false;

        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is solved
                if (mySudoku.getSudokuGrid()[row][column].isSolved()) {
                    //for each other element in the row
                    for (int row2 = 0; row2 < 9; row2++) {
                        //if the other element is not solved
                        if (!mySudoku.getSudokuGrid()[row2][column].isSolved()) {
                            //index is index of the solved value in not solved element
                            int index = mySudoku.getSudokuGrid()[row2][column].indexOf(mySudoku.getSudokuGrid()[row][column].
                                    getVal());

                            //if not solved element has solved value
                            if (index != -1) {
                                mySudoku.getSudokuGrid()[row2][column].remove(index);
                                return true;
                            }
                        }
                    }
                    //for each other element in the column
                    for (int column2 = 0; column2 < 9; column2++) {
                        //if the other element is not solved
                        if (!mySudoku.getSudokuGrid()[row][column2].isSolved()) {
                            //index is index of the solved value in not solved element
                            int index = mySudoku.getSudokuGrid()[row][column2].indexOf(mySudoku.getSudokuGrid()[row][column].
                                    getVal());

                            //if not solved element has solved value
                            if (index != -1) {
                                mySudoku.getSudokuGrid()[row][column2].remove(index);
                                return true;

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
                if (mySudoku.getSudokuGrid()[row][column].isSolved()) {
                    int boxColumn = column / 3;
                    int boxRow = row / 3;

                    //for each row in the small box
                    for (int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++) {
                        //for each column in the small box
                        for (int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++) {
                            //if the other element is not solved
                            if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
                                //index is index of the solved value in not solved element
                                int index = mySudoku.getSudokuGrid()[row2][column2].indexOf(mySudoku.getSudokuGrid()[row][column].
                                        getVal());

                                if (index != -1) {
                                    mySudoku.getSudokuGrid()[row2][column2].remove(index);
                                    return true;
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
                    if (!mySudoku.getSudokuGrid()[row][column].isSolved()) {

                        if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                            num++;
                            index = row;
                        }
                    }
                }
                if (num == 1) {
                    mySudoku.getSudokuGrid()[index][column].solve(i);

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
                    if (!mySudoku.getSudokuGrid()[row][column].isSolved()) {
                        if (mySudoku.getSudokuGrid()[row][column].contains(i)) {
                            num++;
                            index = column;
                        }
                    }
                }
                if (num == 1) {
                    mySudoku.getSudokuGrid()[row][index].solve(i);
                    onlyCandidateLeftRookCheckerWorks = true;


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
                            if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
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
                if (!mySudoku.getSudokuGrid()[row][column].isSolved()) {
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
                            //if it is not a member of the naked set
                            if (!rowVals.contains(row2)) {
                                for (int possibleIndex = 0; possibleIndex < mySudoku.getSudokuGrid()[row][column].
                                        size();
                                     possibleIndex++) {
                                    //if another cell has the candidates that are in the naked set, remove that candidate from it
                                    if (mySudoku.getSudokuGrid()[row2][column].indexOf(mySudoku.getSudokuGrid()[row][column].
                                            getVal(possibleIndex)) != -1) {
                                        mySudoku.getSudokuGrid()[row2][column].remove(mySudoku.getSudokuGrid()[row2][column].
                                                indexOf(mySudoku.getSudokuGrid()[row][column].getVal(possibleIndex)));
                                        candidatePairRookCheckerWorks = true;
                                    }
                                }
                            }
                        }

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
                            //if it is not a member of the naked set
                            if (!columnVals.contains(column2)) {
                                for (int possibleIndex = 0; possibleIndex < mySudoku.getSudokuGrid()[row][column].
                                        size();
                                     possibleIndex++) {
                                    //if another cell has the candidates that are in the naked set, remove that candidate from it

                                    if (mySudoku.getSudokuGrid()[row][column2].
                                            indexOf(mySudoku.getSudokuGrid()[row][column].
                                                    getVal(possibleIndex)) != -1) {
                                        mySudoku.getSudokuGrid()[row][column2].remove(mySudoku.getSudokuGrid()[row][column2].
                                                indexOf(mySudoku.getSudokuGrid()[row][column].getVal(possibleIndex)));
                                        candidatePairRookCheckerWorks = true;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return candidatePairRookCheckerWorks;
    }

    //checks for 2 cells that have the same 2 candidates in a box, eliminates those candidates from that box
    public boolean NakedCandidateBoxChecker(SudokuGrid mySudoku) {
        boolean candidatePairBoxCheckerWorks = false;
        //two for loops to go through each element in mySudoku
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //if that element is unsolved
                if (!mySudoku.getSudokuGrid()[row][column].isSolved()) {
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
                            if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
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


                    }
                }
            }
        }
        return candidatePairBoxCheckerWorks;
    }

    //checks for hidden candidate sets and removes candidates from those. ex: a 2 and 9 can only go in 2 cells in a row,
    //eliminate all other cands from those cells
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

                                        }
                                        if (mySudoku.getSudokuGrid()[row][kColumnCoord2].contains(j)) {
                                            mySudoku.getSudokuGrid()[row][kColumnCoord2].
                                                    remove(mySudoku.getSudokuGrid()[row][kColumnCoord2].indexOf(j));

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

                                        }
                                        if (mySudoku.getSudokuGrid()[kRowCoord2][column].contains(j)) {
                                            mySudoku.getSudokuGrid()[kRowCoord2][column].
                                                    remove(mySudoku.getSudokuGrid()[kRowCoord2][column].indexOf(j));

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

                                            }
                                            if (mySudoku.getSudokuGrid()[kRowCoord2][kColumnCoord2].contains(j)) {
                                                mySudoku.getSudokuGrid()[kRowCoord2][kColumnCoord2].
                                                        remove(mySudoku.getSudokuGrid()[kRowCoord2][kColumnCoord2].
                                                                indexOf(j));

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
                            if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
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
                                    if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));

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
                                    if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));

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
                                    if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));

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
                                    if (!mySudoku.getSudokuGrid()[row2][column2].isSolved()) {
                                        //remove i
                                        if (mySudoku.getSudokuGrid()[row2][column2].contains(i)) {
                                            mySudoku.getSudokuGrid()[row2][column2].remove(mySudoku.getSudokuGrid()[row2][column2].
                                                    indexOf(i));

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


}
