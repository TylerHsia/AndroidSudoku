package android.bignerdranch.androidsudoku;

import android.util.Log;

import org.junit.Test;

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
        assertEquals(4, 2 + 2);
    }


    
        private int NumStoredSudokus = 23;


        /*

        @Test
        public void TestRateDifficulty()
        {
            SudokuSolver sudokuSolver = new SudokuSolver();
            for (int i = 1; i <= NumStoredSudokus; i++)
            {
                SudokuLogic.sudokCell sudokCell = new SudokuLogic.sudokCell();
                //inputted sudoku
                int[,] sudokuInputted = input(i);

                SudokuGrid mySudoku = new SudokuGrid();

                //my sudoku to be worked with
                for (int row = 0; row < 9; row++)
                {
                    for (int column = 0; column < 9; column++)
                    {
                        mySudoku[row, column] = new sudokCell(sudokuInputted[row, column]);
                    }
                }


                TestContext.WriteLine($"the level of {i} is {sudokuSolver.RateDifficulty(mySudoku)}");
            }
        }

        @Test
        public void TestMethod1()
        {


            List<int> possibles = new List<int>();
            possibles.Add(10);
            possibles.RemoveAt(0);
            //var x = new SudokuLogic.Class1();
            //var y = x.HelloTest();
            //TestContext.WriteLine($"the value is {y}");
            ////Assert.Fail(y);
            SudokuLogic.sudokCell sudokCell = new SudokuLogic.sudokCell();
            sudokCell newCell = new sudokCell(0);
            newCell.RemoveAt(0);


        }

        @Test
        public async Task URLTest()
    {
        var targetURL = @"https://ironmaster.queue-it.net/?c=ironmaster&e=june11restock&t=https%3A%2F%2Fwww.ironmaster.com%2F%3Fmc_cid%3Da1a20e0f04%26mc_eid%3Dc6fe4dabb3&cid=en-US&l=Custom%20Layout";
        var y = new HttpClient();
        var result = await y.GetAsync(targetURL);
        TestContext.WriteLine(result.Content.ToString());
    }

        @Test
        public void CheckContainsDuplicateMethod()
        {
            int[] x1 = { 1, 2, 3, 4 };
            List<int> list1 = new List<int>(x1);

            int[] x2 = { 1, 1, 2, 3 };
            List<int> list2 = new List<int>(x2);

            int[] x3 = { 1, 5, 2, 3, 9, 8, 4, 7, 6 };
            List<int> list3 = new List<int>(x3);

            int[] x4 = { 1, 5, 6, 3, 9, 8, 4, 7, 6 };
            List<int> list4 = new List<int>(x4);

            SudokuLogic.SudokuSolver sudokuSolver = new SudokuSolver();

            Assert.IsFalse(sudokuSolver.ContainsDuplicate(list1), "1 wrong");
            Assert.IsTrue(sudokuSolver.ContainsDuplicate(list2), "2 wrong");
            Assert.IsFalse(sudokuSolver.ContainsDuplicate(list3), "3 wrong");
            Assert.IsTrue(sudokuSolver.ContainsDuplicate(list4), "4 wrong ");
        }

        @Test
        public void TestSudokuGridCopier()
        {
            SudokuLogic.SudokuSolver sudokuSolver = new SudokuLogic.SudokuSolver();
            SudokuGrid myGrid = new SudokuGrid();
            myGrid = sudokuSolver.FromIntArray(input(1));
            SudokuGrid myGrid2 = sudokuSolver.Copy(myGrid);
            Assert.IsTrue(myGrid.ToString().Equals(myGrid2.ToString()));

            myGrid2[2, 3].solve(2);
            myGrid[2, 3].solve(3);
            Assert.IsFalse(myGrid.ToString().Equals(myGrid2.ToString()));
        }

        @Test
        public void CheckAllStoredLogic()
        {

            bool solvedAll = true;
            //1 to 23, inclusive
            for (int i = 1; i <= NumStoredSudokus; i++)
            {
                SudokuLogic.SudokuSolver sudokuSolver = new SudokuLogic.SudokuSolver();
                SudokuLogic.sudokCell sudokCell = new SudokuLogic.sudokCell();
                //inputted sudoku
                int[,] sudokuInputted = input(i);

                SudokuGrid mySudoku = new SudokuGrid();

                //my sudoku to be worked with
                for (int row = 0; row < 9; row++)
                {
                    for (int column = 0; column < 9; column++)
                    {
                        mySudoku[row, column] = new sudokCell(sudokuInputted[row, column]);
                    }
                }

                sudokuSolver.Solve(mySudoku, true);

                //bruteForceSolver(mySudoku);

                //if unsolved
                if (!sudokuSolver.IsSolved(mySudoku, false))
                {
                    solvedAll = false;
                    TestContext.WriteLine("More work on " + i);
                    TestContext.WriteLine("Num unsolved is " + sudokuSolver.numUnsolved(mySudoku));
                }
                //if there was a duplicate in row, column, or box
                if (sudokuSolver.InvalidMove(mySudoku))
                {
                    TestContext.WriteLine("Invalid move on " + i);
                }
            }
            if (solvedAll)
            {
                TestContext.WriteLine("All solved");
            }

            Assert.IsTrue(solvedAll, "Not all solved");
        }

        @Test
        public void CheckIsValidMethod()
        {
            //1 to 23, inclusive
            //23 broke
            for (int i = 1; i <= 23; i++)
            {
                SudokuLogic.SudokuSolver sudokuSolver = new SudokuLogic.SudokuSolver();
                SudokuLogic.sudokCell sudokCell = new SudokuLogic.sudokCell();
                //inputted sudoku
                int[,] sudokuInputted = input(i);

                SudokuGrid mySudoku = new SudokuGrid();

                //my sudoku to be worked with
                for (int row = 0; row < 9; row++)
                {
                    for (int column = 0; column < 9; column++)
                    {
                        mySudoku[row, column] = new sudokCell(sudokuInputted[row, column]);
                    }
                }

                Assert.IsTrue(mySudoku.IsValid(), $"{i} was said to be not valid");

            }

            SudokuGrid notValid1 = new SudokuGrid();
            //my sudoku to be worked with
            for (int row = 0; row < 9; row++)
            {
                for (int column = 0; column < 9; column++)
                {
                    notValid1[row, column] = new sudokCell();
                }
            }
            notValid1[1, 1].solve(2);
            Assert.IsFalse(notValid1.IsValid(), "IsValid said a nonValid Sudoku is validx");
        }

        @Test
        public void TestBruteForceChecker()
        {
            SudokuSolver sudokuSolver = new SudokuSolver();

            //need to check 5, 9, 12, 13
            SudokuGrid mySudoku = new SudokuGrid();
            int[,] sudokuInputted = input(5);
            //my sudoku to be worked with
            for (int row = 0; row < 9; row++)
            {
                for (int column = 0; column < 9; column++)
                {
                    mySudoku[row, column] = new sudokCell(sudokuInputted[row, column]);
                }
            }


            sudokuSolver.bruteForceSolver(ref mySudoku);
            Assert.IsTrue(sudokuSolver.IsSolved(mySudoku), "Brute force did not solve it");


        }


        @Test
        public void TestSudokuGridCopy()
        {
            SudokuGrid myGrid1 = new SudokuGrid();
            myGrid1[0, 0].solve(1);

            SudokuGrid myGrid2 = new SudokuGrid();
            myGrid2[0, 0].solve(1);

            Assert.IsTrue(myGrid1.Equals(myGrid2));
        }


        //gives sudoku from list of possibles
        public static int[,] input(int x)
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
    *///Todo: here

/*
        //converts int[] of integers length 9 into a 2d array
        public static int[,] twoDConverter(List<int> oneD)
    {
        int[,] twoD = new int[9, 9];
        for (int row = 0; row < 9; row++)
        {
            int oneDRow = oneD[row];
            for (int column = 8; column >= 0; column--)
            {
                twoD[row, column] = oneDRow % 10;
                oneDRow = oneDRow / 10;
            }
        }
        return twoD;

    }*/

}