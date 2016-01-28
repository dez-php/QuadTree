import dez.quadtree.QuadTree;
import dez.quadtree.QuadTreeLeaf;

public class Main {

    public static void main(String[] args)
    {

        QuadTree<String> tree = new QuadTree<String>(0, 0, 800, 600);

        tree.add(new QuadTreeLeaf<>(10, 10, "Test"));

        System.out.println(tree);

    }

}
