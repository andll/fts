package ws.danasoft.fts;

public class Image
{
    private final String name;

    public Image(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Image && ((Image) o).name.equals(name);
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
