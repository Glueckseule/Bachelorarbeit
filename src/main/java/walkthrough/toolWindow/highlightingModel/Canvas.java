package walkthrough.toolWindow.highlightingModel;

import walkthrough.toolWindow.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JFrame {

    TargetArea area;
    Arrow arrow;

    public Canvas(Dimension size) {
        super("Canvas");

        this.setUndecorated(true);
        this.setBackground(Constants.TRANSPARENT);
        this.setAlwaysOnTop(true);
        this.setSize(size);
    }

    public void highlightElements(TargetArea area, Arrow arrow) {
        this.area = area;
        this.arrow = arrow;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponent(g);
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(Constants.STROKE_WIDTH));
        graphics2D.setColor(Constants.HIGHLIGHT_COLOR);

        if (area != null) {
            graphics2D.drawRect(area.x, area.y, area.width, area.height);
        }
        if (arrow != null) {
            Point arrowStart = getArrowStart();
            arrow.constructArrow(arrowStart.x, arrowStart.y, arrow.DIRECTION);
            graphics2D.draw(arrow);
        }

    }

    private Point getArrowStart() {
        Point startingPoint = new Point();

        if (arrow.DIRECTION.equals(Constants.UPWARDS)) {
            startingPoint.x = area.x + area.width / 2;
            startingPoint.y = area.y + area.height;
        } else {
            startingPoint.x = area.x + area.width;
            startingPoint.y = area.y + area.height / 2;
        }

        return startingPoint;
    }

}
