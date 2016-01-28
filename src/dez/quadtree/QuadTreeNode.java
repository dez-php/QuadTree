package dez.quadtree;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class QuadTreeNode<T> {

    public static final int MAX_OBJECTS_PER_NODE = 10;
    public static final int MAX_DEPTH            = 8;

    private int     depth       = 0;
    private boolean hasChildren = false;
    private QuadTreeBound                  bounds;
    private Map<NodeType, QuadTreeNode<T>> nodes;
    private ArrayList<QuadTreeLeaf<T>>     leafs;

    public QuadTreeNode(double minX, double minY, double maxX, double maxY, int depth)
    {
        this.bounds = new QuadTreeBound(minX, minY, maxX, maxY);
        this.leafs = new ArrayList<>();
        this.nodes = new EnumMap<NodeType, QuadTreeNode<T>>(NodeType.class);
    }

    public boolean put(QuadTreeLeaf<T> treeLeaf)
    {
        NodeType nodeType = this.detectNodeType(treeLeaf);

        if (MAX_OBJECTS_PER_NODE > this.leafs.size()) {
            this.leafs.add(treeLeaf);
        } else if (MAX_DEPTH > this.getDepth() && this.leafs.size() >= MAX_OBJECTS_PER_NODE) {
            this.splitNode();

            int i = 0;
            while (this.leafs.size() > 0) {
                i++;
                this.nodes.get(this.detectNodeType(this.leafs.get(i))).put(this.leafs.remove(i));
            }
        } else {
            throw new StackOverflowError("Max count elements of last node");
        }

        return false;
    }

    public void splitNode()
    {
        this.nodes.put(NodeType.NW,
                       new QuadTreeNode<T>(this.bounds.minX, this.bounds.minY, this.bounds.centreX, this.bounds.centreY,
                                           this.depth + 1));
        this.nodes.put(NodeType.NE,
                       new QuadTreeNode<T>(this.bounds.centreX, this.bounds.minY, this.bounds.maxX, this.bounds.centreY,
                                           this.depth + 1));
        this.nodes.put(NodeType.SE,
                       new QuadTreeNode<T>(this.bounds.centreX, this.bounds.centreY, this.bounds.maxX, this.bounds.maxY,
                                           this.depth + 1));
        this.nodes.put(NodeType.SW,
                       new QuadTreeNode<T>(this.bounds.minX, this.bounds.centreY, this.bounds.centreX, this.bounds.maxY,
                                           this.depth + 1));

        this.hasChildren = false;
    }

    public NodeType detectNodeType(QuadTreeLeaf treeLeaf)
    {
        return this.detectNodeType(treeLeaf.x, treeLeaf.y);
    }

    public NodeType detectNodeType(double x, double y)
    {

        return NodeType.ABROAD;
    }

    public int getDepth()
    {
        return this.depth;
    }

    public QuadTreeBound getBounds()
    {
        return this.bounds;
    }

    public boolean hasChildren()
    {
        return this.hasChildren;
    }

    public String toString()
    {
        return String.format("QuadTreeNode{ bounds: %s, nodes: %s }", this.bounds, this.nodes);
    }
}
