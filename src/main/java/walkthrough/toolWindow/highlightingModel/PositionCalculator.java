package walkthrough.toolWindow.highlightingModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;

public class PositionCalculator {

    private final Project project;
    int x;
    int y;
    int width;
    int height;

    public PositionCalculator(Project project) {
        this.project = project;
    }

    public void updateDimensions(String name) {
        switch (name) {
            case "PROJECT_TOOLWINDOW":
                System.out.println("Such das Projektfenster");
                findRelevant();
                break;
            case "CODE":
                System.out.println("Such den Code?");
                setDimensions(60, 60, 250, 250);
                break;
            case "PROJECT_DROPDOWN":
                System.out.println("Such das Dropdown im Projektfenster");
                setDimensions(0, 0, 0, 0);
                break;
            case "OOP-FOLDER":
                System.out.println("Find raus, ob man einzelne Ordner erkennen kann");
                setDimensions(0, 0, 0, 0);
                break;
            case "OOP_SRC_FOLDER":
                System.out.println("Same problem");
                setDimensions(0, 0, 0, 0);
                break;
            case "RUN_BUTTON":
                System.out.println("Finde den Run-Button!");
                setDimensions(0, 0, 0, 0);
                break;
            case "GRAPHICS_APP":
                System.out.println("Muss ich die Highlighten?");
                setDimensions(0, 0, 0, 0);
                break;
            case "OUT_FOLDER":
                System.out.println("Wenn erkennbar, dann hier markieren");
                setDimensions(0, 0, 0, 0);
                break;
            case "CREATE_CLASS_OPTIONS":
                System.out.println("Geht das?");
                setDimensions(0, 0, 0, 0);
                break;
            case "CREATE_CLASS_DIALOG":
                System.out.println("Finde den Dialog über Mausposition, der nur im Kontext da ist...");
                setDimensions(0, 0, 0, 0);
                break;
            case "CLASS_FILE":
                System.out.println("Vermutlich den Editor markieren");
                setDimensions(0, 0, 0, 0);
                break;
            case "SELECT_IMPORT":
                System.out.println("Über Mausposition das Kontextmenü bekommen");
                setDimensions(0, 0, 0, 0);
                break;
            case "IMPORT_STATEMENT":
                System.out.println("Kann man die Zeile im Code finden, die mit import beginnt?");
                setDimensions(10, 20, 30, 40);
                break;
            default:
                System.out.println("Kein Highlighting");
                setDimensions(0, 0, 0, 0);
                break;
        }
    }

    public void findRelevant() {
        ToolWindowManager manager = ToolWindowManager.getInstance(project);
        ToolWindow projectWindow = manager.getToolWindow("Project");
        JComponent c = projectWindow.getComponent();
        boolean visible = projectWindow.isVisible();

        int x = c.getX();
        int y = c.getY();
        int w = c.getWidth();
        int h = c.getHeight();

        setDimensions(x, y, w, h);

        ContentManager cm = projectWindow.getContentManager();
        JComponent dropdown = cm.getContent(0).getComponent();
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
