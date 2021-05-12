package android.bignerdranch.androidsudoku;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        //Todo: can i make messages like this
        assertEquals("Test message", 4, 2 + 2);
    }


    private int NumStoredSudokus = 23;


    @Test
    public void TestRateDifficulty() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        for (int i = 1; i <= NumStoredSudokus; i++) {
            SudokCell sudokCell = new SudokCell();
            //inputted sudoku
            int[][] sudokuInputted = input(i);

            SudokuGrid mySudoku = new SudokuGrid();

            //my sudoku to be worked with
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    mySudoku.getSudokuGrid()[row][column] =new SudokCell(sudokuInputted[row][column]);
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
        int[] x1 = {1, 2, 3, 4};
        ArrayList<Integer> list1 = new ArrayList<Integer>(x1);

        int[] x2 = {1, 1, 2, 3};
        ArrayList<Integer> list2 = new ArrayList<Integer>(x2);

        int[] x3 = {1, 5, 2, 3, 9, 8, 4, 7, 6};
        ArrayList<Integer> list3 = new ArrayList<Integer>(x3);

        int[] x4 = {1, 5, 6, 3, 9, 8, 4, 7, 6};
        ArrayList<Integer> list4 = new ArrayList<Integer>(x4);

        SudokuSolver sudokuSolver = new SudokuSolver();

        assertFalse(sudokuSolver.ContainsDuplicate(list1), "1 wrong");
        assertTrue(sudokuSolver.ContainsDuplicate(list2), "2 wrong");
        assertFalse(sudokuSolver.ContainsDuplicate(list3), "3 wrong");
        assertTrue(sudokuSolver.ContainsDuplicate(list4), "4 wrong ");
    }

    @Test
    public void TestSudokuGridCopier() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        SudokuGrid myGrid = new SudokuGrid();
        myGrid = sudokuSolver.FromIntArray(input(1));
        SudokuGrid myGrid2 = sudokuSolver.Copy(myGrid);
        assertTrue(myGrid.ToString().equals(myGrid2.ToString()));

        myGrid2.getSudokuGrid()[2][3].solve(2);
        myGrid.getSudokuGrid()[2][3].solve(3);
        assertFalse(myGrid.ToString().equals(myGrid2.ToString()));
    }

    @Test
    public void CheckAllStoredLogic() {

        boolean solvedAll = true;
        //1 to 23, inclusive
        for (int i = 1; i <= NumStoredSudokus; i++) {
            SudokuSolver sudokuSolver = new SudokuSolver();
            SudokCell sudokCell = new SudokCell();
            //inputted sudoku
            int[][] sudokuInputted = input(i);

            SudokuGrid mySudoku = new SudokuGrid();

            //my sudoku to be worked with
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    mySudoku.getSudokuGrid()[row][column] =new SudokCell(sudokuInputted[row][column]);
                }
            }

            sudokuSolver.Solve(mySudoku, true);

            //bruteForceSolver(mySudoku);

            //if unsolved
            if (!sudokuSolver.IsSolved(mySudoku, false)) {
                solvedAll = false;
                Log.i("Test", "More work on " + i);
                Log.i("Test", "Num unsolved is " + sudokuSolver.numUnsolved(mySudoku));
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
            //inputted sudoku
            int[][]sudokuInputted = input(i);

            SudokuGrid mySudoku = new SudokuGrid();

            //my sudoku to be worked with
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    mySudoku.getSudokuGrid()[row][column] =new SudokCell(sudokuInputted[row][column]);
                }
            }

            assertTrue(i + " was said to be not valid", mySudoku.IsValid());

        }

        SudokuGrid notValid1 = new SudokuGrid();
        //my sudoku to be worked with
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                notValid1.getSudokuGrid()[row][column] =new SudokCell();
            }
        }
        notValid1.getSudokuGrid()[1][1].solve(2);
        assertFalse("IsValid said a nonValid Sudoku is valid", notValid1.IsValid());
    }

    @Test
    public void TestBruteForceChecker() {
        SudokuSolver sudokuSolver = new SudokuSolver();

        //need to check 5, 9, 12, 13
        SudokuGrid mySudoku = new SudokuGrid();
        int[][] sudokuInputted = input(5);
        //my sudoku to be worked with
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                mySudoku.getSudokuGrid()[row][column] =new SudokCell(sudokuInputted[row][column]);
            }
        }


        sudokuSolver.bruteForceSolver(mySudoku);
        assertTrue( "Brute force did not solve it", sudokuSolver.IsSolved(mySudoku));


    }


    @Test
    public void TestSudokuGridCopy() {
        SudokuGrid myGrid1 = new SudokuGrid();
        myGrid1.getSudokuGrid()[0][0].solve(1);

        SudokuGrid myGrid2 = new SudokuGrid();
        myGrid2.getSudokuGrid()[0][0].solve(1);

        assertTrue(myGrid1.equals(myGrid2));
    }


    //gives sudoku from list of possibles
    public static int[][] input(int x) {
        //Todo: make these sample inputs work
        /*
        if (x == 1) {
            int[,]beginner = {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
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
            int[,]normal = {{0, 0, 0, 0, 9, 0, 0, 0, 0},
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
            int[,]hard = {{0, 8, 0, 0, 0, 0, 0, 4, 0},
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
            int[,]expert = {{0, 0, 7, 0, 0, 0, 6, 3, 0},
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
            int[,]expert2 = {{0, 9, 1, 0, 0, 0, 0, 0, 0},
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
            int[,]fiveStar = {{0, 5, 0, 0, 1, 3, 0, 0, 0},
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
            int[,]fiveStar2 = {{0, 0, 6, 0, 0, 0, 0, 4, 0},
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
            int[,]fiveStar3 = {{8, 0, 0, 0, 5, 6, 0, 0, 0},
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
        */
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
            for (int column = 8; column >= 0; column--) {
                twoD[row][column] = oneDRow % 10;
                oneDRow = oneDRow / 10;
            }
        }
        return twoD;

    }

}