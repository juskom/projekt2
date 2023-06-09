import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.view.Viewer;

import java.util.Map;

public class BinaryTreeVisualiserImpl implements BinaryTreeVisualiser {

    public BinaryTreeVisualiserImpl() {
    }

    public void displayTreeFromMap(Map<String, Integer> map) {

        int height = map.keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(0) + 1;
        Graph graph = new MultiGraph("Binary Tree");

        Node root = graph.addNode("0");
        root.addAttribute("ui.label", "root");
        root.addAttribute("ui.style", "text-size: 35;");
        root.setAttribute("y", 0);
        root.setAttribute("x", 0);

        buildBinaryTree(graph, root, 0, height);

        for (String key : map.keySet()) {
            String nodeName = "0";
            for (char i : key.toCharArray()) {
                if (i == '1') {
                    nodeName += 'L';
                } else {
                    nodeName += 'R';
                }
            }
            Integer value = map.get(key);
            Node node = graph.getNode(nodeName);
            node.setAttribute("label", value.toString());
            node.addAttribute("ui.style", "text-size: 30;");
            childrenRemover(node, graph);
        }

        HierarchicalLayout layout = new HierarchicalLayout();
        graph.addAttribute("ui.stylesheet", "node { shape: box; size: 10px, 10px; fill-color: #EEE; stroke-mode: plain; stroke-color: #222;}");

        Viewer viewer = graph.display();
        viewer.disableAutoLayout();
    }

    private void childrenRemover(Node node, Graph graph) {
        String name = node.getId();
        String leftChild = name + 'L';
        String rightChild = name + 'R';
        if (graph.getNode(leftChild) != null) {
            childrenRemover(graph.getNode(leftChild), graph);
            graph.removeNode(leftChild);
        }
        if (graph.getNode(rightChild) != null) {
            childrenRemover(graph.getNode(rightChild), graph);
            graph.removeNode(rightChild);
        }
    }

    private void buildBinaryTree(Graph graph, Node parent, int level, int height) {
        if (level >= height) {
            return;
        }

        // Tworzenie lewego dziecka
        Node leftChild = graph.addNode(parent.getId() + "L");
        Edge edge = graph.addEdge(parent.getId() + "-" + leftChild.getId(), parent, leftChild);
        edge.addAttribute("label", "1");
        edge.addAttribute("ui.style", "text-size: 30;");

        // Wywołanie rekurencyjne dla lewego dziecka
        buildBinaryTree(graph, leftChild, level + 1, height);

        // Tworzenie prawego dziecka
        Node rightChild = graph.addNode(parent.getId() + "R");

        edge = graph.addEdge(parent.getId() + "-" + rightChild.getId(), parent, rightChild);
        edge.addAttribute("label", "0");
        // Wywołanie rekurencyjne dla prawego dziecka
        buildBinaryTree(graph, rightChild, level + 1, height);
        edge.addAttribute("ui.style", "text-size: 30;");
        leftChild.setAttribute("y", -1 * (int) (level + 1) * 10);
        leftChild.setAttribute("x", getXfromName(leftChild.getId()));
        rightChild.setAttribute("y", -1 * (int) (level + 1) * 10);
        rightChild.setAttribute("x", getXfromName(rightChild.getId()));
    }

    private int getXfromName(String name) {
        double width = Math.pow(2, name.length());
        long countL = 0;
        long countR = 0;
        for (char i : name.toCharArray()) {
            if (i == 'L') {
                countL += width;
            }
            if (i == 'R') {
                countR += width;
            }
            width /= 2;
        }
        return (int) (countR - countL);
    }

}