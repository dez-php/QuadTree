package dez.quadtree;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class QuadTreeNode<T> {

    public static final int MAX_OBJECTS_PER_NODE = 2;
    public static final int MAX_DEPTH            = 10;

    private int     depth       = 0;
    private boolean hasChildren = false;
    private QuadTreeBound                  bounds;
    private Map<NodeType, QuadTreeNode<T>> nodes;
    private ArrayList<QuadTreeLeaf<T>>     leafs;

    public QuadTreeNode(double minX, double minY, double maxX, double maxY, int depth)
    {
        this.bounds = new QuadTreeBound(minX, minY, maxX, maxY);
        this.leafs = new ArrayList<>();
        this.nodes = new EnumMap<>(NodeType.class);
        this.depth = depth;
    }

    public void draw(Graphics2D gfx)
    {

        for (QuadTreeLeaf leaf : this.leafs) {
            gfx.fillOval((int) leaf.x - 2, (int) leaf.y - 2, 4, 4);
        }

        if (this.hasChildren) {
            for (NodeType nodeType : NodeType.values()) {
                this.nodes.get(nodeType).draw(gfx);
            }
        }
    }

    public void clear()
    {
        this.leafs.clear();

        if (this.hasChildren) {
            for (NodeType nodeType : NodeType.values()) {
                this.nodes.get(nodeType).clear();
            }
        }

        this.hasChildren = false;
        this.nodes.clear();
    }

    public void put(QuadTreeLeaf<T> treeLeaf)
    {
        if (this.hasChildren) {
            this.nodes.get(this.detectNodeType(treeLeaf)).put(treeLeaf);
        } else {
            if (MAX_OBJECTS_PER_NODE > this.leafs.size()) {
                this.leafs.add(treeLeaf);
            } else if (MAX_DEPTH > this.getDepth() && this.leafs.size() >= MAX_OBJECTS_PER_NODE) {
                this.splitNode();

                this.nodes.get(this.detectNodeType(treeLeaf)).put(treeLeaf);
                for (QuadTreeLeaf<T> leaf : this.leafs) {
                    this.nodes.get(this.detectNodeType(leaf)).put(leaf);
                }
                this.leafs.clear();
            } else {
                this.leafs.add(treeLeaf);
            }
        }
    }

    public void splitNode()
    {
        this.nodes.put(NodeType.NW, new QuadTreeNode<T>(this.bounds.minX, this.bounds.minY, this.bounds.centreX, this.bounds.centreY, this.depth + 1));
        this.nodes.put(NodeType.NE, new QuadTreeNode<T>(this.bounds.centreX, this.bounds.minY, this.bounds.maxX, this.bounds.centreY, this.depth + 1));
        this.nodes.put(NodeType.SE, new QuadTreeNode<T>(this.bounds.centreX, this.bounds.centreY, this.bounds.maxX, this.bounds.maxY, this.depth + 1));
        this.nodes.put(NodeType.SW, new QuadTreeNode<T>(this.bounds.minX, this.bounds.centreY, this.bounds.centreX, this.bounds.maxY, this.depth + 1));

        this.hasChildren = true;
    }

    public NodeType detectNodeType(Rectangle rectangle)
    {
        int x = (int) rectangle.getCenterX();
        int y = (int) rectangle.getCenterY();

        return this.detectNodeType(x, y);
    }

    public NodeType detectNodeType(QuadTreeLeaf<T> treeLeaf)
    {
        return this.detectNodeType(treeLeaf.x, treeLeaf.y);
    }

    public NodeType detectNodeType(double x, double y)
    {
        NodeType nodeType;

        if (x > this.bounds.centreX) {
            nodeType = y > this.bounds.centreY ? NodeType.SE : NodeType.NE;
        } else {
            nodeType = y > this.bounds.centreY ? NodeType.SW : NodeType.NW;
        }

        return nodeType;
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
        return String.format("QuadTreeNode{ bounds: %s, \nleafs: %s,\n nodes: %s }", this.bounds, this.leafs.size(),
                             this.nodes);
    }
}
