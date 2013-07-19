package ws.danasoft.fts;

public class Word
{
    private final String name;

    public Word(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Word && ((Word) o).name.equals(name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
