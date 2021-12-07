package utilities.Iterator;

public class ScoreCollection implements Collection {
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    Score[] scoreList;

    public ScoreCollection()
    {
        scoreList = new Score[MAX_ITEMS];

    }

    public void addScore(short item)
    {
        Score score = new Score(item);
        if (numberOfItems >= MAX_ITEMS)
            System.err.println("Full");
        else
        {
            scoreList[numberOfItems] = score;
            numberOfItems = numberOfItems + 1;
        }
    }

    public Iterator createIterator()
    {
        return new ScoreIterator(scoreList);
    }
}
