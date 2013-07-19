package ws.danasoft.fts;

import java.util.Iterator;
import java.util.List;

public interface Db
{
    int PICK_WEIGHT = 1;
    int INITIAL_WEIGHT = 2;

    void insert(Image image, List<Word> words);

    void pick(Image image, List<Word> words);

    Iterator<Image> find(List<Word> words);
}
