package ws.danasoft.fts;

import java.util.*;

public class DbImpl implements Db
{
    private final Map<Word, Map<Image, Integer>> idx = new HashMap<Word, Map<Image, Integer>>();

    @Override
    public void insert(Image image, List<Word> words)
    {
        for (Word word : words)
        {
            Map<Image, Integer> images = idx.get(word);
            if (images == null)
            {
                idx.put(word, images = new HashMap<Image, Integer>());
            }
            images.put(image, INITIAL_WEIGHT);
        }
    }

    @Override
    public void pick(Image image, List<Word> words)
    {
        for (Word word : words)
        {
            Map<Image, Integer> images = idx.get(word);
            if (images == null)
            {
                idx.put(word, images = new HashMap<Image, Integer>());
            }
            final Integer prev = images.get(image);
            images.put(image, prev == null ? PICK_WEIGHT : prev + PICK_WEIGHT);
        }
    }

    @Override
    public Iterator<Image> find(List<Word> words)
    {
        final Map<Image, Integer> countMap = new HashMap<Image, Integer>();
        for (Word word : words)
        {
            final Map<Image, Integer> weightMap = idx.get(word);
            if (weightMap == null)
            {
                continue;
            }
            for (Map.Entry<Image, Integer> entry : weightMap.entrySet())
            {
                final Integer v = countMap.get(entry.getKey());
                countMap.put(entry.getKey(), v == null ? entry.getValue() : v + entry.getValue());
            }
        }
        List<Image> images = new ArrayList<Image>(countMap.keySet());
        Collections.sort(images, new Comparator<Image>()
        {
            @Override
            public int compare(Image o1, Image o2)
            {
                return countMap.get(o2).compareTo(countMap.get(o1));
            }
        });
        return images.iterator();
    }
}
