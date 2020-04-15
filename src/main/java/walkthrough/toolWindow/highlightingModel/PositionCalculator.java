package walkthrough.toolWindow.highlightingModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;
import java.awt.*;

public class PositionCalculator {

    private final Project project;
    int x;
    int y;
    int width;
    int height;

    //Basic values to be added
    Rectangle ideContentDimensions; //ToolWindowsPane: Breit und hoch wie IntelliJ, minus die Leiste ganz oben - 0,30,1258,625
    int defaultHeightOfBar = 28;
    int basicX;

    //Components needed for other calculations
    ToolWindowManager manager;
    JComponent projectToolWindow;
    JComponent walkthroughWindow;
    JFrame fullProjectFrame;
    JRootPane projectRoot;

    public PositionCalculator(Project project) {
        this.project = project;

        manager = ToolWindowManager.getInstance(project);
        fullProjectFrame = WindowManager.getInstance().getFrame(project);
        projectRoot = fullProjectFrame.getRootPane();
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
                System.out.println("Finde den Dialog über Mausposition, der nur im Kontext da ist...");
                setDimensions(0, 0, 0, 0);
                break;
            case "SELECT_IMPORT":
                System.out.println("Über Mausposition das Kontextmenü bekommen");
                setDimensions(0, 0, 0, 0);
                break;
            case "IMPORT_STATEMENT":
                System.out.println("Kann man die Zeile im Code finden, die mit *import* beginnt?");
                setDimensions(10, 20, 30, 40);
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
                ideContentDimensions.y + (2 * defaultHeightOfBar),
                projectToolWindow.getWidth(),
                projectToolWindow.getHeight()
        );
    }

    private void findEditor() {
        //mark height of projectToolWindow and full width - PTW-width - Walkthrough-width
        int editorWidth = (int) (ideContentDimensions.getWidth() - projectToolWindow.getWidth() - walkthroughWindow.getWidth());

        setDimensions(
                basicX + projectToolWindow.getWidth(),
                ideContentDimensions.y + defaultHeightOfBar,
                editorWidth,
                projectToolWindow.getHeight() + defaultHeightOfBar
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
                ideContentDimensions.y + defaultHeightOfBar,
                projectToolWindow.getWidth() / 3,
                defaultHeightOfBar
        );
    }

    private void findProjectDropdownPopup() {
        ContentManager cm = ToolWindowManager.getInstance(project).getToolWindow("Project").getContentManager();
        int x = cm.getContents().length;

        setDimensions(
                basicX + projectToolWindow.getX(),
                ideContentDimensions.y + 2 * defaultHeightOfBar,
                projectToolWindow.getWidth() / 3,
                x * defaultHeightOfBar
        );
    }

    private void setDimensions(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

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
    //</editor-fold>

}
