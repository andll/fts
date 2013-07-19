import org.junit.Assert;
import org.junit.Test;
import ws.danasoft.fts.Db;
import ws.danasoft.fts.DbImpl;
import ws.danasoft.fts.Image;
import ws.danasoft.fts.Word;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntegrationTest
{
    private final Db db = new DbImpl();

    @Test
    public void checkFindOrdering()
    {
        insert("z", "c", "d", "e");
        insert("x", "a", "b", "c");
        insert("y", "b", "c", "d");
        Assert.assertArrayEquals(new String[]{"x", "y", "z"}, find("a", "b", "c"));
    }

    @Test
    public void checkPickIncreaseRank()
    {
        insert("x", "a", "b");
        insert("y", "b");
        Assert.assertArrayEquals(new String[]{"x", "y"}, find("a", "b"));
        pick("y", "b");
        pick("y", "b");
        pick("y", "b");
        Assert.assertArrayEquals(new String[]{"y", "x"}, find("a", "b"));
    }

    private String[] find(String... words)
    {
        final Iterator<Image> iterator = db.find(words(words));
        ArrayList<String> strings = new ArrayList<String>();
        while (iterator.hasNext())
        {
            strings.add(iterator.next().toString());
        }
        return strings.toArray(new String[strings.size()]);
    }

    private void insert(String image, String... stringWords)
    {
        db.insert(new Image(image), words(stringWords));
    }

    private void pick(String image, String... stringWords)
    {
        db.pick(new Image(image), words(stringWords));
    }

    private List<Word> words(String... stringWords)
    {
        List<Word> words = new ArrayList<Word>(stringWords.length);
        for (String stringWord : stringWords)
        {
            words.add(new Word(stringWord));
        }
        return words;
    }
}
