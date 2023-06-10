import org.graphstream.graph.Graph;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import java.awt.event.*;

public class ZoomAtMousePosition implements ViewerListener {
    protected boolean loop = true;
    protected Point3 mousePosition = new Point3();

    public ZoomAtMousePosition(Graph graph) {
        // We do as usual to display a graph.

        Viewer viewer = graph.display();
        viewer.disableAutoLayout();
        // We connect back the viewer to the graph,
        // the graph becomes a sink, it will receive all the viewer events.
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);

        View view = viewer.getDefaultView();
        ((ViewPanel) view).resizeFrame(800, 600);

        // Add mouse adapter for panning
        MouseAdapter ma = new MouseAdapter() {
            Point2 lastMousePressPoint;
            Point3 originalViewCenter;

            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePressPoint = view.getCamera().transformPxToGu(e.getX(), e.getY());
                originalViewCenter = view.getCamera().getViewCenter();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point2 lastMouseDragPoint = view.getCamera().transformPxToGu(e.getX(), e.getY());

                double newX = originalViewCenter.x - (lastMouseDragPoint.x - lastMousePressPoint.x);
                double newY = originalViewCenter.y - (lastMouseDragPoint.y - lastMousePressPoint.y);

                Camera cam = view.getCamera();
                cam.setViewCenter(newX, newY, 0);
            }
        };

        // Add mouse wheel listener
        ((ViewPanel) view).addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                e.consume();
                int i = e.getWheelRotation();
                double factor = Math.pow(1.25, i); // 1.25 is the zoom factor
                Camera cam = view.getCamera();
                double newViewPercent = cam.getViewPercent() * factor;

                Point2 mousePositionInView = cam.transformPxToGu(e.getX(), e.getY());
                cam.setViewPercent(newViewPercent);

                Point3 newMousePosition3D = new Point3(mousePositionInView.x, mousePositionInView.y, 0);
                Point3 currentPosition = cam.getViewCenter();

                double newX = currentPosition.x - newMousePosition3D.x + mousePosition.x;
                double newY = currentPosition.y - newMousePosition3D.y + mousePosition.y;

                cam.setViewCenter(newX, newY, currentPosition.z);

                mousePosition = newMousePosition3D;
            }
        });

        ((ViewPanel) view).addMouseListener(ma);
        ((ViewPanel) view).addMouseMotionListener(ma);

        // Add the mouse move event
        view.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = view.getCamera().transformPxToGu(e.getX(), e.getY());
            }
        });

        while (loop) {
            fromViewer.pump();
        }
    }

    public void viewClosed(String id) {
        loop = false;
    }

    public void buttonPushed(String id) {
    }

    public void buttonReleased(String id) {
    }
}

