import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Tree {

    private static final Graph graph = new SingleGraph("Tutorial 1");


    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        org.graphstream.graph.Node e1 = graph.getNode("A");
        e1.addAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px; text-alignment: center;");
        e1.addAttribute("ui.label", "node A");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.display();
    }

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

//
//
//
//

//
//        // Eksport grafu do obrazu
//
//        FileSinkImages.OutputPolicy outputPolicy = FileSinkImages.OutputPolicy.BY_STEP;
//        FileSinkImages.OutputType type = FileSinkImages.OutputType.PNG;
//        Resolution resolution = Resolutions.HD720;
//        FileSinkImages picExporter = new FileSinkImages(type, resolution) {
//
//            @Override
//            protected Camera getCamera() {
//                return null;
//            }
//
//            @Override
//            protected void render() {
//
//            }
//
//            @Override
//            protected BufferedImage getRenderedImage() {
//                return null;
//            }
//
//            @Override
//            protected void initImage() {
//
//            }
//
//            @Override
//            protected void clearImage(int i) {
//
//            }
//        };
//        picExporter.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
//        picExporter.setOutputPolicy(outputPolicy);
//
//        try {
//            picExporter.writeAll(graph, "C:\\Users\\Justyna\\Desktop\\studia\\sem2\\jimp\\huffman_tree.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    // Wyświetlenie grafu
//        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
//        Viewer viewer = graph.display();

}

//    private Node addNodesAndEdges(Graph graph, Node node) {
//        if (node != null) {
//            Node left = node.left;
//            Node right = node.right;
//
//            // Dodawanie węzła do grafu
//            String nodeId = Integer.toString(node.data);
//            graph.addNode(nodeId);
//
//
//            if (left != null) {
//                // Dodawanie krawędzi dla lewego dziecka
//                String leftNodeId = Integer.toString(left.data);
//                String leftEdgeId = nodeId + "-" + leftNodeId;
//                graph.addEdge(leftEdgeId, nodeId, leftNodeId);
//                left = addNodesAndEdges(graph, left);
//            }
//
//            if (right != null) {
//                // Dodawanie krawędzi dla prawego dziecka
//                String rightNodeId = Integer.toString(right.data);
//                String rightEdgeId = nodeId + "-" + rightNodeId;
//                graph.addEdge(rightEdgeId, nodeId, rightNodeId);
//                right = addNodesAndEdges(graph, right);
//            }
//        }
//        return node;
//    }


