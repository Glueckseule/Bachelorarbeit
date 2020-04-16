package walkthrough.toolWindow.highlightingModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;
import java.awt.*;

public class PositionCalculator {

    //values for getter
    private final Project project;
    private int x;
    private int y;
    private int width;
    private int height;

    //Basic values to be used
    private Rectangle ideContentDimensions; //ToolWindowsPane: Breit und hoch wie IntelliJ, minus die Leiste ganz oben - 0,30,1258,625
    private int basicX;
    private static final int DEFAULT_HEIGHT_OF_BAR = 28;
    private static final int DEFAULT_HEIGHT_OF_SMALLER_BAR = 20;
    private static final int EDITOR_OFFSET = 55;

    //Components needed for other calculations
    private static final Dimension CLASS_DIALOG = new Dimension(330, 175);
    private ToolWindowManager manager;
    private JComponent projectToolWindow;
    private JComponent walkthroughWindow;
    private JRootPane projectRoot;

    public PositionCalculator(Project project) {
        this.project = project;

        manager = ToolWindowManager.getInstance(project);
        projectRoot = WindowManager.getInstance().getFrame(project).getRootPane();
        basicX = projectRoot.getX();

        walkthroughWindow = manager.getToolWindow("Walkthrough durch IntelliJ").getComponent();
    }

    public void updateDimensions(String name) {
        switch (name) {
            case "PROJECT_TOOLWINDOW":
                findProjectToolWindow();
                break;
            case "CODE":
                findEditor();
                break;
            case "PROJECT_DROPDOWN":
                findProjectDropdown();
                break;
            case "PROJECT_DROPDOWN_POPUP":
                findProjectDropdownPopup();
                break;
            case "OOP_FOLDER":
                findFolder("oop");
                break;
            case "OOP_SRC_FOLDER":
                findFolder("src");
                break;
            case "RUN_BUTTON":
                findRunButton();
                break;
            case "GRAPHICS_APP":
                setDimensions(0, 0, 0, 0);
                break;
            case "OUT_FOLDER":
                findFolder("out");
                break;
            case "CREATE_CLASS_DIALOG":
                findDialogInMiddle();
                break;
            case "SELECT_IMPORT":
                System.out.println("Soll hier was hervorgehoben werden?");
                setDimensions(0, 0, 0, 0);
                break;
            case "IMPORT_STATEMENT":
                selectImportStatement();
                break;
            default:
                defaultHighlighting();
                break;
        }
    }

    private void defaultHighlighting() {
        ideContentDimensions = WindowManager.getInstance().getFrame(project).getContentPane().getBounds();

        setDimensions(
                basicX + ideContentDimensions.x,
                ideContentDimensions.y,
                ideContentDimensions.width,
                ideContentDimensions.height
        );
    }

    private void findProjectToolWindow() {
        ToolWindow projectWindow = manager.getToolWindow("Project");
        projectToolWindow = projectWindow.getComponent();

        //check if ProjectToolWindow is visible - if not: make visible
        if (!projectWindow.isVisible()) {
            projectWindow.show(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        setDimensions(
                basicX,
                ideContentDimensions.y + (2 * DEFAULT_HEIGHT_OF_BAR),
                projectToolWindow.getWidth(),
                projectToolWindow.getHeight()
        );
    }

    private void findEditor() {
        //mark height of projectToolWindow and full width - PTW-width - Walkthrough-width
        int editorWidth = (int) (ideContentDimensions.getWidth() - projectToolWindow.getWidth() - walkthroughWindow.getWidth());

        setDimensions(
                basicX + projectToolWindow.getWidth(),
                ideContentDimensions.y + DEFAULT_HEIGHT_OF_BAR,
                editorWidth,
                projectToolWindow.getHeight() + DEFAULT_HEIGHT_OF_BAR
        );
    }

    private void findRunButton() {
        Component[] moreComps = projectRoot.getContentPane().getComponents();
        Component actionToolBar = moreComps[0];
        Component navBar = actionToolBar.getComponentAt(0, 0);
        Component firstNavBarComp = navBar.getComponentAt(0, 0);
        Component secondNavBarComp = navBar.getComponentAt(firstNavBarComp.getWidth(), 0);

        setDimensions(
                basicX + secondNavBarComp.getX(),
                ideContentDimensions.y,
                secondNavBarComp.getWidth(),
                secondNavBarComp.getHeight()
        );
    }

    private void findFolder(String folderToFind) {
        if (folderToFind.equals("oop")) {
            setDimensions(0, 0, 0, 0);
        }
        if (folderToFind.equals("out")) {
            setDimensions(0, 0, 0, 0);
        }
        if (folderToFind.equals("src")) {
            setDimensions(0, 0, 0, 0);
        }
    }

    private void findProjectDropdown() {
        setDimensions(
                basicX + projectToolWindow.getX(),
                ideContentDimensions.y + DEFAULT_HEIGHT_OF_BAR,
                projectToolWindow.getWidth() / 3,
                DEFAULT_HEIGHT_OF_BAR
        );
    }

    private void findProjectDropdownPopup() {
        ContentManager cm = ToolWindowManager.getInstance(project).getToolWindow("Project").getContentManager();
        int x = cm.getContents().length;

        setDimensions(
                basicX + projectToolWindow.getX(),
                ideContentDimensions.y + 2 * DEFAULT_HEIGHT_OF_BAR,
                projectToolWindow.getWidth() / 3,
                x * DEFAULT_HEIGHT_OF_SMALLER_BAR
        );
    }

    private void findDialogInMiddle() {
        setDimensions(
                ideContentDimensions.width/2 - CLASS_DIALOG.width/2,
                ideContentDimensions.height/2 - CLASS_DIALOG.height/3,
                CLASS_DIALOG.width,
                CLASS_DIALOG.height
        );
    }

    //<editor-fold desc="assumption: import statement is in line one and always the same length">
    private void selectImportStatement() {
        setDimensions(
                basicX + projectToolWindow.getWidth() + EDITOR_OFFSET,
                ideContentDimensions.y + (2 * DEFAULT_HEIGHT_OF_BAR),
                250,
                DEFAULT_HEIGHT_OF_BAR
        );
    }
    //</editor-fold>

    //<editor-fold desc="Getter">
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    //</editor-fold>K

    private void setDimensions(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

}
