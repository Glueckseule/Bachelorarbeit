package walkthrough.toolWindow.highlighting;

import java.awt.geom.Path2D;

public class Arrow extends Path2D.Double {

    /**
     * Class representing an upwards arrow
     * TODO:   Change, so that the arrow can be to every direction
     *         depending on the section explained
     */

    int arrowLength = 40;
    int arrowArmWidth = 10;
    int arrowArmLength = 16;
    int paddingToHighlight = 20;

    public Arrow(int startingPoint, int startingHeight) {

        startingHeight += paddingToHighlight;

        moveTo(startingPoint,startingHeight+arrowLength);
        lineTo(startingPoint, startingHeight);
        moveTo(startingPoint-arrowArmWidth, startingHeight+arrowArmLength);
        lineTo(startingPoint,startingHeight);
        moveTo(startingPoint+arrowArmWidth,startingHeight+arrowArmLength);
        lineTo(startingPoint,startingHeight);

    }

}
