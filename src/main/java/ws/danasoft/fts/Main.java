package ws.danasoft.fts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    private static final Pattern INSERT_PATTERN = Pattern.compile("^insert\\(([^,)]+),([^)]+)\\)$");
    private static final Pattern FIND_PATTERN = Pattern.compile("^find\\(([^)]+)\\)$");
    private static final Pattern PICK_PATTERN = Pattern.compile("^pick\\(([0-9]+)\\)$");

    public static void main(String[] args)
    {
        Db db = new DbImpl();
        Scanner scanner = new Scanner(System.in);
        List<Word> lastFindWords = null;
        List<Image> lastFindImages = null;
        System.out.println("Available commands: ");
        System.out.println("insert(image, words...)");
        System.out.println("find(words...)");
        System.out.println("pick(index/*one of previously found images*/)");
        while (true)
        {
            final String c = scanner.nextLine();
            Matcher matcher = INSERT_PATTERN.matcher(c);
            if (matcher.matches())
            {
                final Image image = new Image(matcher.group(1));
                final List<Word> words = new ArrayList<Word>();
                for (String s : matcher.group(2).split(" |,"))
                {
                    words.add(new Word(s));
                }
                db.insert(image, words);
                System.out.println("Inserted " + image + "=>" + words);
                continue;
            }
            matcher = FIND_PATTERN.matcher(c);
            if (matcher.matches())
            {
                final List<Word> words = new ArrayList<Word>();
                lastFindWords = words;
                for (String s : matcher.group(1).split(" |,"))
                {
                    words.add(new Word(s));
                }
                final Iterator<Image> it = db.find(words);
                lastFindImages = new ArrayList<Image>(10);
                int i = 0;
                System.out.print("Top 10 found: ");
                while (it.hasNext() && i++ < 10)
                {
                    final Image image = it.next();
                    System.out.print(image + ", ");
                    lastFindImages.add(image);
                }
                System.out.println();
                continue;
            }
            matcher = PICK_PATTERN.matcher(c);
            if (matcher.matches())
            {
                if (lastFindWords == null || lastFindImages == null)
                {
                    System.err.println("Can not pick - make find() call before");
                    continue;
                }
                int idx = Integer.parseInt(matcher.group(1));
                db.pick(lastFindImages.get(idx), lastFindWords);
                System.out.println("Picked " + lastFindImages.get(idx) + "=>" + lastFindWords);
                continue;
            }
            System.err.println("Sorry, do not understand you");
        }
    }
}
