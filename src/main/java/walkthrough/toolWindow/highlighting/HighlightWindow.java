package walkthrough.toolWindow.highlighting;

import com.intellij.ui.JBColor;

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
     * implemented as an own component
     *
     * TODO:   reorganize the Arrow-Class, so that there can be an arrow-up
     *         and an arrow to the left, depending on the section highlighted
     */

    JFrame basisFrame;
    int width = 300;
    int height = 28;
    int customZeroX = 9;
    int customZeroY = 3;
    int highlightStrokeWidth = 5;
    Color highlightColor = JBColor.RED;
    Color transparent = new Color(0,0,0,0);

    public HighlightWindow(JFrame basis) {
        this.basisFrame = basis;
        prepareShadowing();

        setUndecorated(true);
        setBackground(transparent);
        setAlwaysOnTop(true);
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
        System.out.println(basisFrame.getSize());
        System.out.println(this.getSize());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(highlightStrokeWidth));
        g2D.setColor(highlightColor);
        g2D.drawRect(customZeroX, customZeroY, width, height);

        Arrow arrow = new Arrow(width/2, height);
        g2D.draw(arrow);
    }

}
