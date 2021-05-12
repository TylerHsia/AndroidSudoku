package android.bignerdranch.androidsudoku;

import java.util.ArrayList;

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
        new SudokCell(obj.getPossibles(), obj.getSolved());
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
    public boolean getSolved()
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

    //Todo: make sure this method is being called, not generic equals
    public boolean equals(SudokCell obj)
    {
        //Todo: this line might cause error if equals doesn't compares references
        if (!this.getPossibles().equals(obj.getPossibles()) || this.getSolved() != obj.getSolved())
        {
            return false;
        }
        return true;
    }

    public String toStringVal()
    {
        if(this.getSolved())
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
}
