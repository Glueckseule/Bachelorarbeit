package walkthrough.toolWindow.highlightingModel;

import walkthrough.toolWindow.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JFrame {

    private ArrayList<TargetArea> areasToDraw;
    private ArrayList<Arrow> arrowsToDraw;

    public Canvas(Dimension size) {
        super(Constants.CANVAS_TITLE);
        areasToDraw = new ArrayList<>();
        arrowsToDraw = new ArrayList<>();

        this.setUndecorated(true);
        this.setBackground(Constants.TRANSPARENT);
        this.setAlwaysOnTop(true);
        this.setSize(size);
    }

    public void highlightElements(ArrayList<TargetArea> area, ArrayList<Arrow> arrow) {
        this.areasToDraw = area;
        this.arrowsToDraw = arrow;
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

        if ((areasToDraw != null) && (arrowsToDraw != null)) {
            for (int i = 0; i < areasToDraw.size(); i++) {
                TargetArea singleArea = areasToDraw.get(i);
                Arrow singleArrow = arrowsToDraw.get(i);
                Point arrowStart = getArrowStart(singleArrow.DIRECTION, singleArea);

                graphics2D.drawRect(singleArea.x, singleArea.y, singleArea.width, singleArea.height);
                singleArrow.constructArrow(arrowStart.x, arrowStart.y, singleArrow.DIRECTION);
                graphics2D.draw(singleArrow);
            }
        }
    }

    private Point getArrowStart(String DIRECTION, TargetArea singleArea) {
        Point startingPoint = new Point();

        if (DIRECTION.equals(Constants.UPWARDS)) {
            startingPoint.x = singleArea.x + singleArea.width / 2;
            startingPoint.y = singleArea.y + singleArea.height;
        } else {
            startingPoint.x = singleArea.x + singleArea.width;
            startingPoint.y = singleArea.y + singleArea.height / 2;
        }

        return startingPoint;
    }

}
