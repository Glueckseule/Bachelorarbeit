package walkthrough.toolWindow.highlightingModel;

import walkthrough.toolWindow.utils.Constants;

import java.awt.geom.Path2D;

/**
 * Class representing an arrow
 * depending on the section explained it points upwards or left
 */
public class Arrow extends Path2D.Double {

    public int STARTING_X;
    public int STARTING_Y;
    public final String DIRECTION;

    public Arrow (int startingX, int startingY, String direction) {
        this.STARTING_X = startingX;
        this.STARTING_Y = startingY;
        this.DIRECTION = direction;
    }

    public void constructArrow() {
        if (DIRECTION.equals(Constants.UPWARDS)) {
            STARTING_Y += Constants.PADDING;

            moveTo(STARTING_X, STARTING_Y + Constants.ARROW_LENGTH);
            lineTo(STARTING_X, STARTING_Y);
            moveTo(STARTING_X - Constants.ARM_WIDTH, STARTING_Y + Constants.ARM_LENGTH);
            lineTo(STARTING_X, STARTING_Y);
            moveTo(STARTING_X + Constants.ARM_WIDTH, STARTING_Y + Constants.ARM_LENGTH);
        } else if (DIRECTION.equals(Constants.TO_LEFT)){
            STARTING_X += Constants.PADDING;

            moveTo(STARTING_X + Constants.ARROW_LENGTH, STARTING_Y);
            lineTo(STARTING_X, STARTING_Y);
            moveTo(STARTING_X + Constants.ARM_LENGTH, STARTING_Y + Constants.ARM_WIDTH);
            lineTo(STARTING_X, STARTING_Y);
            moveTo(STARTING_X + Constants.ARM_LENGTH, STARTING_Y - Constants.ARM_WIDTH);
        } else {
            return;
        }
        lineTo(STARTING_X, STARTING_Y);
    }


}
