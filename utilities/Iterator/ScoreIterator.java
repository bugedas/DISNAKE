package utilities.Iterator;

public class ScoreIterator implements Iterator {
    Score[] scoreList;

    // maintains current pos of iterator over the array
    int pos = 0;

    // Constructor takes the array of scoreList are
    // going to iterate over.
    public ScoreIterator (Score[] scoreList)
    {
        this.scoreList = scoreList;
    }
    public Object next()
    {
        // return next element in the array and increment pos
        Score score =  scoreList[pos];
        pos += 1;
        return score;
    }

    public boolean hasNext()
    {
        if (pos >= scoreList.length ||
                scoreList[pos] == null)
            return false;
        else
            return true;
    }
}
