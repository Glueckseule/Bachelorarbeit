package walkthrough.toolWindow.highlightingModel;

import walkthrough.toolWindow.utils.Constants;

import java.awt.geom.Path2D;

public class Arrow extends Path2D.Double {

    /**
     * Class representing an arrow
     * depending on the section explained it points upwards or left
     */

    public void createArrow(int startingX, int startingY, String direction) {
        if (direction.equals(Constants.UPWARDS)) {
            startingY += Constants.PADDING;

            moveTo(startingX, startingY + Constants.ARROW_LENGTH);
            lineTo(startingX, startingY);
            moveTo(startingX - Constants.ARM_WIDTH, startingY + Constants.ARM_LENGTH);
            lineTo(startingX, startingY);
            moveTo(startingX + Constants.ARM_WIDTH, startingY + Constants.ARM_LENGTH);
        } else {
            startingX += Constants.PADDING;

            moveTo(startingX + Constants.ARROW_LENGTH, startingY);
            lineTo(startingX, startingY);
            moveTo(startingX + Constants.ARM_LENGTH, startingY + Constants.ARM_WIDTH);
            lineTo(startingX, startingY);
            moveTo(startingX + Constants.ARM_LENGTH, startingY - Constants.ARM_WIDTH);
        }
        lineTo(startingX, startingY);
    }

}
