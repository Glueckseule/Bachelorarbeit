package walkthrough.toolWindow.highlighting;

import walkthrough.toolWindow.HighlightConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HighlightWindow extends JFrame {

    /**
     * This class is a JFrame with the same size as the underlying IDE -> shadow
     * As soon as the IDE moves or is resized, the shadow does the same
     * The HighlightWindow is always in front and displays a marker to highlight
     * the content which is currently talked about in the CustomWebView
     *
     * This is done by the inherent paint-Method, so:
     * TODO:   reorganize the painting, so that it can be done from the controller
     *         every time the content of the CustomWebView changes
     *
     * To emphasize the highlighting, the paint-Method also uses an Arrow, which is
     * implemented as an own component but drawn here
     */

    private JFrame basisFrame;
    private HighlightConstants hc = new HighlightConstants();

    //TODO: here for testing, replace with real step later
    int step = 2;

    public HighlightWindow(JFrame basis) {
        this.basisFrame = basis;
        initConstants();

        prepareShadowing();

        setUndecorated(true);
        setBackground(HighlightConstants.TRANSPARENT);
        setAlwaysOnTop(true);
    }

    private void initConstants() {
        hc.initWidth();
        hc.initHeight();
        hc.initXPos();
        hc.initYPos();
        hc.initArrowPos();
    }

    private void prepareShadowing() {
        this.basisFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                onBasisResized();
            }

            public void componentMoved(ComponentEvent e) {
                onBasisMoved();
            }
        });
    }

    private void onBasisMoved() {
        this.setLocation(basisFrame.getLocation());
    }

    private void onBasisResized() {
        this.setSize(basisFrame.getSize());
    }

    //Creates 5 HighlightWindows atm
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Point start = getArrowStart();

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(HighlightConstants.STROKE_WIDTH));
        g2D.setColor(HighlightConstants.HIGHLIGHT_COLOR);
        g2D.drawRect(hc.getXPos(step), hc.getYPos(step), hc.getWidth(step), hc.getHeight(step));

        Arrow arrow = new Arrow();
        arrow.createArrow(start.x, start.y, hc.getArrowPos(step));
        g2D.draw(arrow);
    }

    private Point getArrowStart() {
        Point startingPoint = new Point();

        if (hc.getArrowPos(step).equals(HighlightConstants.UPWARDS)) {
            startingPoint.x = hc.getXPos(step) + hc.getWidth(step) / 2;
            startingPoint.y = hc.getYPos(step) + hc.getHeight(step);
        } else {
            startingPoint.x = hc.getXPos(step) + hc.getWidth(step);
            startingPoint.y = hc.getYPos(step) + hc.getHeight(step) / 2;
        }

        return startingPoint;
    }

}
