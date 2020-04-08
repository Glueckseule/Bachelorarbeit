package walkthrough.toolWindow.highlightingModel;

import walkthrough.toolWindow.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JFrame {

    public Canvas(Dimension size) {
        this.setUndecorated(true);
        this.setBackground(Constants.TRANSPARENT);
        this.setAlwaysOnTop(true);
        this.setSize(size);
    }

    public void highlightElements(TargetArea area, Arrow arrow) {
        //Point start = getArrowStart();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(Constants.STROKE_WIDTH));
        graphics2D.setColor(Constants.HIGHLIGHT_COLOR);

        graphics2D.drawRect(250,250, 100,100);
        /*

        Arrow arrow = new Arrow();
        arrow.createArrow(start.x, start.y, hc.getArrowPos(step));
        g2D.draw(arrow);

         */
    }

    /*
    private Point getArrowStart() {
        Point startingPoint = new Point();

        if (hc.getArrowPos(step).equals(Constants.UPWARDS)) {
            startingPoint.x = hc.getXPos(step) + hc.getWidth(step) / 2;
            startingPoint.y = hc.getYPos(step) + hc.getHeight(step);
        } else {
            startingPoint.x = hc.getXPos(step) + hc.getWidth(step);
            startingPoint.y = hc.getYPos(step) + hc.getHeight(step) / 2;
        }

        return startingPoint;
    }

     */

}
