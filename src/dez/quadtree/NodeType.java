package dez.quadtree;

public enum NodeType {

    NW(1), NE(2), SE(3), SW(4), ABROAD(-1);

    private int position;

    NodeType(int position)
    {
        this.position = position;
    }

    public int position()
    {
        return this.position;
    }

}
