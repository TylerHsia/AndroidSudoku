package android.bignerdranch.androidsudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SudokCell {
    //constructors
    private ArrayList<Integer> possibles = new ArrayList<Integer>();
    private boolean solved = false;
    public SudokCell(int value)
    {
        if (value == -1 || value == 0)
        {
            for (int i = 1; i <= 9; i++)
            {
                possibles.add(i);
            }
        }
        else
        {
            possibles.add(value);
            solved = true;
        }



    }

    public SudokCell()
    {
        for (int i = 1; i <= 9; i++)
        {
            possibles.add(i);
        }
    }
    public SudokCell(SudokCell obj)
    {
        new SudokCell(obj.getPossibles(), obj.isSolved());
    }
    public SudokCell(ArrayList<Integer> possibles, boolean solved)
    {
        for (int i = 0; i < possibles.size(); i++)
        {
            this.possibles.add(possibles.get(i));
        }
        this.solved = solved;
    }

    public SudokCell(ArrayList<Integer> possibles)
    {
        for (int i = 0; i < possibles.size(); i++)
        {
            this.possibles.add(possibles.get(i));
        }
        this.solved = false;
    }

    public SudokCell Clone(SudokCell myCell)
    {
        if(myCell.solved)
        {
            return new SudokCell(myCell.getVal());
        }

        return new SudokCell(myCell.getPossibles());
    }

    public int indexOf(int val)
    {
        return possibles.indexOf(val);
    }
    //removes a candidate
    public boolean remove(int toRemove)
    {

        //to be commented out
        boolean toPrint = false;

        possibles.remove(toRemove);
        if (possibles.size() == 1)
        {
            solved = true;
        }

        //to be commented out
        if (possibles.size() == 0)
        {
            //System.out.println("Removed all possibilities, my null pointer exception");
            toPrint = true;

            throw new IllegalStateException("Cell has zero candidates");
        }
        return toPrint;
    }

    //removes all other candidates
    public void solve(int solution)
    {
        while (possibles.size() > 0)
        {
            possibles.remove(0);
        }
        if(solution == -1){
            throw new IllegalArgumentException("solution -1");
        }
        possibles.add(solution);
        solved = true;
    }

    //checks two cells for the same possibilities
    public boolean samePossible(SudokCell other)
    {
        if (this.possibles.size() != other.possibles.size())
        {
            return false;
        }
        for (int i = 0; i < this.possibles.size(); i++)
        {
            if (this.possibles.get(i) != other.possibles.get(i))
            {
                return false;
            }
        }
        return true;
    }

    //toString
    public String toStringWithoutCands()
    {
        String toReturn = "";
        if (solved)
        {
            return toReturn + possibles.get(0) + " ";
        }
        else
        {
            return "  ";
        }
    }

    @Override
    public String toString()
    {
        String toReturn = "";
        if (solved)
        {
            return toReturn + possibles.get(0) + "\t";
        }
        else
        {
            for (int i = 0; i < possibles.size(); i++)
            {
                toReturn = toReturn + possibles.get(i);
            }
            return toReturn + "\t";
        }
    }

    //contains
    public boolean contains(int x)
    {
        return possibles.contains(x);
    }
    //accessors
    public boolean isSolved()
    {
        return solved;
    }

    public int getVal()
    {
        if (solved)
        {
            return possibles.get(0);
        }
        else
        {
            return -1;
        }
    }

    public int getVal(int index)
    {
        return possibles.get(index);
    }

    public ArrayList<Integer> getPossibles()
    {
        return possibles;
    }
    public ArrayList<Integer> getPossibles(SudokCell obj)
    {
        return obj.getPossibles();
    }

    public int size()
    {
        return possibles.size();
    }

    public boolean equals(SudokCell obj)
    {
        if (!this.getPossibles().equals(obj.getPossibles()) || this.isSolved() != obj.isSolved())
        {
            return false;
        }
        return true;
    }

    public String toStringVal()
    {
        if(this.isSolved())
        {
            if (isDigit(this.getVal()))
            {
                return "" + this.getVal();
            }
        }
        return "";
    }

    public boolean isDigit(int x)
    {
        for(int i = 1; i <= 9; i++)
        {
            if(x == i)
            {
                return true;
            }
        }
        return false;
    }

    //effectively deletes the given cell and reverts to unsolved state
    public void addAllPossibles() {
        solved = false;
        possibles = new ArrayList<Integer>();
        Collections.addAll(possibles, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    }
}
