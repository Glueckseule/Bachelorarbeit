package walkthrough.toolWindow.highlighting;

import walkthrough.toolWindow.HighlightConstants;

import java.awt.geom.Path2D;

public class Arrow extends Path2D.Double {

    /**
     * Class representing an upwards arrow
     * TODO:   Change, so that the arrow can be to every direction
     *         depending on the section explained
     */

    public Arrow() {

    }

    public void createArrow(int startingX, int startingY, String direction) {
        if (direction.equals(HighlightConstants.UPWARDS)) {
            startingY += HighlightConstants.PADDING;

            moveTo(startingX,startingY+HighlightConstants.ARROW_LENGTH);
            lineTo(startingX, startingY);
            moveTo(startingX-HighlightConstants.ARM_WIDTH, startingY+HighlightConstants.ARM_LENGTH);
            lineTo(startingX,startingY);
            moveTo(startingX+HighlightConstants.ARM_WIDTH,startingY+HighlightConstants.ARM_LENGTH);
            lineTo(startingX,startingY);
        } else {
            startingX += HighlightConstants.PADDING;

            moveTo(startingX + HighlightConstants.ARROW_LENGTH, startingY);
            lineTo(startingX, startingY);
            moveTo(startingX + HighlightConstants.ARM_LENGTH, startingY + HighlightConstants.ARM_WIDTH);
            lineTo(startingX, startingY);
            moveTo(startingX + HighlightConstants.ARM_LENGTH, startingY - HighlightConstants.ARM_WIDTH);
            lineTo(startingX, startingY);
        }
    }

}
