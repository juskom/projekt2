import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Tree {

    private final Graph graph = new SingleGraph("Huffman Tree");


    public void visualizeHuffmanTree() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        // Konfiguracja wyglądu grafu
        graph.setAttribute("ui.stylesheet", "node { size: 30px; shape: circle; fill-mode: plain; fill-color: #CCCCCC; stroke-mode: plain; stroke-color: #000000; text-size: 16px; text-color: #000000; }" +
                "edge { size: 2px; shape: line; fill-color: #000000; }");
        graph.display();
    }

    public void addNode(String nodeNumber) {
        graph.addNode(nodeNumber);
        org.graphstream.graph.Node e1 = graph.getNode(nodeNumber);
        e1.addAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px; text-alignment: center;");
        e1.addAttribute("ui.label", nodeNumber);
    }

    public void addEdge(String node1, String node2) {
        graph.addEdge(node1 + node2, node1, node2);

    }

}


