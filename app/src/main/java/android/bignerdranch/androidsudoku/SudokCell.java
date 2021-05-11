package android.bignerdranch.androidsudoku;

public class SudokCell {
    //constructors
    private List<int> possibles = new List<int>();
    private bool solved = false;
    public SudokCell(int value)
    {
        if (value == -1 || value == 0)
        {
            for (int i = 1; i <= 9; i++)
            {
                possibles.Add(i);
            }
        }
        else
        {
            possibles.Add(value);
            solved = true;
        }



    }

    public SudokCell()
    {
        for (int i = 1; i <= 9; i++)
        {
            possibles.Add(i);
        }
    }
    public SudokCell(SudokCell obj)
    {
        new SudokCell(obj.getPossibles(), obj.getSolved());
    }
    public SudokCell(List<int> possibles, bool solved)
    {
        for (int i = 0; i < possibles.Count; i++)
        {
            this.possibles.Add(possibles[i]);
        }
        this.solved = solved;
    }

    public SudokCell(List<int> possibles)
    {
        for (int i = 0; i < possibles.Count; i++)
        {
            this.possibles.Add(possibles[i]);
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
        return possibles.IndexOf(val);
    }
    //removes a candidate
    public bool RemoveAt(int toRemove)
    {

        //to be commented out
        bool toPrint = false;

        possibles.RemoveAt(toRemove);
        if (possibles.Count == 1)
        {
            solved = true;
        }

        //to be commented out
        if (possibles.Count == 0)
        {
            //System.out.println("Removed all possibilities, my null pointer exception");
            toPrint = true;

            throw new InvalidOperationException("Cell has zero candidates");
        }
        return toPrint;
    }

    //removes all other candidates
    public void solve(int solution)
    {
        while (possibles.Count > 0)
        {
            possibles.RemoveAt(0);
        }
        possibles.Add(solution);
        solved = true;
    }

    //checks two cells for the same possibilities
    public bool samePossible(SudokCell other)
    {
        if (this.possibles.Count != other.possibles.Count)
        {
            return false;
        }
        for (int i = 0; i < this.possibles.Count; i++)
        {
            if (this.possibles[i] != other.possibles[i])
            {
                return false;
            }
        }
        return true;
    }

    //toString
    public string toStringWithoutCands()
    {
        string toReturn = "";
        if (solved)
        {
            return toReturn + possibles[0] + " ";
        }
        else
        {
            return "  ";
        }
    }

    public override string ToString()
    {
        string toReturn = "";
        if (solved)
        {
            return toReturn + possibles[0] + "\t";
        }
        else
        {
            for (int i = 0; i < possibles.Count; i++)
            {
                toReturn = toReturn + possibles[i];
            }
            return toReturn + "\t";
        }
    }

    //contains
    public bool contains(int x)
    {
        return possibles.Contains(x);
    }
    //accessors
    public bool getSolved()
    {
        return solved;
    }

    public int getVal()
    {
        if (solved)
        {
            return possibles[0];
        }
        else
        {
            return -1;
        }
    }

    public int getVal(int index)
    {
        return possibles[index];
    }

    public List<int> getPossibles()
    {
        return possibles;
    }
    public List<int> getPossibles(SudokCell obj)
    {
        return obj.getPossibles();
    }

    public int size()
    {
        return possibles.Count;
    }

    public bool Equals(SudokCell obj)
    {
        if (!this.getPossibles().SequenceEqual(obj.getPossibles()) || this.getSolved() != obj.getSolved())
        {
            return false;
        }
        return true;
    }

    public string ToStringVal()
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

    public bool isDigit(int x)
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
