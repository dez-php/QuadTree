package dezbyte.quadtree;

import java.awt.*;
import java.util.*;

public class QuadTreeNode<T extends Object2D> {

    public static final int MAX_OBJECTS_PER_NODE = 2;
    public static final int MAX_DEPTH            = 4;
    private Map<NodeType, QuadTreeNode<T>> nodes;
    private int     depth       = 0;
    private boolean hasChildren = false;
    private QuadTreeBound bounds;
    private Set<T>        leafs;

    public QuadTreeNode(double minX, double minY, double maxX, double maxY, int depth)
    {
        this.bounds = new QuadTreeBound(minX, minY, maxX, maxY);
        this.leafs = new HashSet<>();
        this.nodes = new EnumMap<>(NodeType.class);
        this.depth = depth;
    }

    public void execute(QuadTree.Executor executor)
    {
        this.values().forEach(executor::execute);
    }

    public Set<T> values()
    {
        Set<T> items = new HashSet<>();
        items.addAll(this.leafs);

        if (this.hasChildren) {
            this.nodes().forEach((nodeType, node) -> items.addAll(node.values()));
        }

        return items;
    }

    public Set<T> leafs()
    {
        return this.leafs;
    }

    public void restructure()
    {
        if (this.hasChildren()) {
            if(this.values().size() > 0) {
                this.nodes.forEach((nodeType, node) -> node.restructure());
            } else {
//                this.nodes.forEach((nodeType, node) -> this.nodes.remove(nodeType));
            }
        } else {
            Iterator<T> iterator = this.leafs.iterator();
            while (iterator.hasNext()) {
                T leaf = iterator.next();
                if (!this.belong(leaf)) {
                    QuadTree.rootNode.insert(leaf);
                    iterator.remove();
                }
            }
        }
    }

    public boolean belong(T leaf)
    {
        return this.getBounds().contains(leaf.getX(), leaf.getY());
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

    public ArrayList<T> search(QuadTreeBound treeBound)
    {
        treeBound.intersects(this.bounds);
        return null;
    }

    public ArrayList<T> search(Rectangle rectangle)
    {
        return this.search(new QuadTreeBound(rectangle.x, rectangle.y, rectangle.getMaxX(), rectangle.getMaxY()));
    }

    public void insert(T object2D)
    {
        if (this.hasChildren) {
            this.nodes.get(this.detectNodeType(object2D)).insert(object2D);
        } else {
            if (MAX_OBJECTS_PER_NODE > this.leafs.size()) {
                this.leafs.add(object2D);
            } else if (MAX_DEPTH > this.getDepth() && this.leafs.size() >= MAX_OBJECTS_PER_NODE) {

                this.splitNode();
                this.nodes.get(this.detectNodeType(object2D)).insert(object2D);

                Iterator<T> iterator = this.leafs.iterator();
                while (iterator.hasNext()) {
                    T leaf = iterator.next();
                    this.nodes.get(this.detectNodeType(leaf)).insert(leaf);
                    iterator.remove();
                }
            } else {
                this.leafs.add(object2D);
            }
        }
    }

    public void splitNode()
    {
        QuadTreeNode<T> nodeNorthWest = new QuadTreeNode<>(this.bounds.minX, this.bounds.minY, this.bounds.centreX,
                this.bounds.centreY, this.depth + 1);
        QuadTreeNode<T> nodeNorthEast = new QuadTreeNode<>(this.bounds.centreX, this.bounds.minY, this.bounds.maxX,
                this.bounds.centreY, this.depth + 1);
        QuadTreeNode<T> nodeSouthEast = new QuadTreeNode<>(this.bounds.centreX, this.bounds.centreY, this.bounds.maxX,
                this.bounds.maxY, this.depth + 1);
        QuadTreeNode<T> nodeSouthWest = new QuadTreeNode<>(this.bounds.minX, this.bounds.centreY, this.bounds.centreX,
                this.bounds.maxY, this.depth + 1);

        this.nodes.put(NodeType.NW, nodeNorthWest);
        this.nodes.put(NodeType.NE, nodeNorthEast);
        this.nodes.put(NodeType.SE, nodeSouthEast);
        this.nodes.put(NodeType.SW, nodeSouthWest);

        this.hasChildren = true;
    }

    public NodeType detectNodeType(Rectangle rectangle)
    {
        int x = (int) rectangle.getCenterX();
        int y = (int) rectangle.getCenterY();

        return this.detectNodeType(x, y);
    }

    public NodeType detectNodeType(T object2D)
    {
        return this.detectNodeType(object2D.getX(), object2D.getY());
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

    public Map<NodeType, QuadTreeNode<T>> nodes()
    {
        return this.nodes;
    }

    public String toString()
    {
        return String.format("QuadTreeNode{ bounds: %s, \nleafs: %s,\n nodes: %s }", this.bounds, this.leafs.size(),
                this.nodes);
    }
}
