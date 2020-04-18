package walkthrough.toolWindow.utils;

import com.intellij.ui.JBColor;

import java.awt.*;

public final class Constants {

    //tutorial resources
    public static final String BASIC_TUTORIAL = "/tutorial/basic-tutorial.json";
    public static final String CODE_FOR_TUTORIAL = "public class Lander {\n    Ellipse ellipse = new Ellipse();\n}";
    public static final String RESTART_TUTORIAL_BUTTON = "Tutorial neu starten";
    public static final String FINISH_TUTORIAL_BUTTON = "Tutorial beenden";
    public static final String BACK_BUTTON = "Zur\u00fcck";
    public static final String NEXT_BUTTON = "Weiter";

    //event messages
    public static final String TUTORIAL_LOADED = "tutorial loaded";
    public static final String ASSETS_LOADED = "assets loaded";
    public static final String TUTORIAL_STARTED = "tutorial started";
    public static final String TUTORIAL_ENDING = "tutorial ended";
    public static final String NEXT_STEP = "next";
    public static final String PREVIOUS_STEP = "previous";
    public static final String ADD_CODE = "add Code";
    public static final String RESTART = "restart";
    public static final String FINISH = "finish and hide";

    //for all windows
    public static final int STROKE_WIDTH = 5;
    public static final Color HIGHLIGHT_COLOR = JBColor.RED;
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    //for arrows
    public static final String UPWARDS = "UP";
    public static final String TO_LEFT = "LEFT";

    public static final int ARROW_LENGTH = 40;
    public static final int ARM_WIDTH = 10;
    public static final int ARM_LENGTH = 16;
    public static final int PADDING = 20;
}
