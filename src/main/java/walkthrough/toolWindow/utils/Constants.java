package walkthrough.toolWindow.utils;

import com.intellij.ui.JBColor;

import java.awt.*;

public final class Constants {

    //tutorial resources
    public static final String BASIC_TUTORIAL = "/tutorial/basic-tutorial.json";

    //event messages
    public static final String TUTORIAL_LOADED = "tutorial loaded";
    public static final String ASSETS_LOADED = "assets loaded";
    public static final String TUTORIAL_STARTED = "tutorial started";
    public static final String NEXT_STEP = "next";
    public static final String PREVIOUS_STEP = "previous";

    //for all windows
    public static final int STROKE_WIDTH = 5;
    public static final Color HIGHLIGHT_COLOR = JBColor.RED;
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    //for arrows
    public static final String UPWARDS = "UP";
    public static final String TO_LEFT = "LEFT";
    public static final String NO_ARROW = "NONE";

    public static final int ARROW_LENGTH = 40;
    public static final int ARM_WIDTH = 10;
    public static final int ARM_LENGTH = 16;
    public static final int PADDING = 20;


    /**
     * deprecated - gonna be changed
     *
     * dimensions for each window
     */
    public static final int[] WIDTH = new int[16];
    public static final int[] HEIGHT = new int[16];
    public static final int[] X_POS = new int[16];
    public static final int[] Y_POS = new int[16];

    public static final String[] ARROW_POS = new String[16];

    public void initWidth() {
        WIDTH[0] = 300;
        WIDTH[1] = 200;
        WIDTH[2] = 500;
    }

    public void initHeight() {
        HEIGHT[0] = 28;
        HEIGHT[1] = 100;
        HEIGHT[2] = 34;
    }

    public void initXPos() {
        X_POS[0] = 9;
        X_POS[1] = 219;
        X_POS[2] = 459;
    }

    public void initYPos() {
        Y_POS[0] = 3;
        Y_POS[1] = 55;
        Y_POS[2] = 311;
    }

    public void initArrowPos() {
        ARROW_POS[0] = UPWARDS;
        ARROW_POS[1] = TO_LEFT;
    }

    public int getWidth(int pos) {
        return WIDTH[pos - 1];
    }

    public int getHeight(int pos) {
        return HEIGHT[pos - 1];
    }

    public int getXPos(int pos) {
        return X_POS[pos - 1];
    }

    public int getYPos(int pos) {
        return Y_POS[pos - 1];
    }

    public String getArrowPos(int pos) {
        return ARROW_POS[pos - 1];
    }


}
