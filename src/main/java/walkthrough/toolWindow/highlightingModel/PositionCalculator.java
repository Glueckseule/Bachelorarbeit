package walkthrough.toolWindow.highlightingModel;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PositionCalculator {

    private final Project project;
    int x;
    int y;
    int width;
    int height;

    //Basic values to be added
    int basicX;
    int basicY = 28;

    //Components needed for other calculations
    JComponent projectToolWindow;
    JComponent walkthroughWindow;
    JRootPane projectRoot;
    Rectangle fullSizeMinusTopBar;


    public PositionCalculator(Project project) {
        this.project = project;

        projectRoot = WindowManager.getInstance().getFrame(project).getRootPane();
        basicX = projectRoot.getX();
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
                System.out.println("bei Schritt 4 - evtl unnötig, dann später löschen");
                setDimensions(0,0,0,0);
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
                System.out.println("Muss ich die Highlighten?");
                setDimensions(0, 0, 0, 0);
                break;
            case "OUT_FOLDER":
                findFolder("out");
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
                findEditor();
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
                defaultHighlighting();
                break;
        }
    }

    private void defaultHighlighting() {
        //ToolWindowsPane: Breit und hoch wie IntelliJ, minus die Leiste ganz oben (28px)
        fullSizeMinusTopBar = WindowManager.getInstance().getFrame(project).getContentPane().getComponent(2).getBounds();

        setDimensions(basicX + fullSizeMinusTopBar.x, basicY + fullSizeMinusTopBar.y, fullSizeMinusTopBar.width, fullSizeMinusTopBar.height);
    }

    private void findProjectToolWindow() {
        System.out.println("Such das Projektfenster");
        ToolWindow projectWindow = ToolWindowManager.getInstance(project).getToolWindow("Project");
        walkthroughWindow  = ToolWindowManager.getInstance(project).getToolWindow("Walkthrough durch IntelliJ").getComponent();
        if (!projectWindow.isVisible()) {
            projectWindow.show(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
        //NonOpaquePanel
        projectToolWindow = projectWindow.getComponent();
        //LayeredPane
        Container infiniteParent = projectToolWindow.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
        setDimensions(basicX + infiniteParent.getX(), 3 * basicY, projectToolWindow.getWidth(), projectToolWindow.getHeight());
    }

    private void findEditor() {
        //mark height of projectToolWindow and full width - PTW-width - Walkthrough-width
        int editorWidth = (int) (fullSizeMinusTopBar.getWidth() - projectToolWindow.getWidth()-walkthroughWindow.getWidth());
        System.out.println("Vermutlich den Editor markieren");
        System.out.println("Such den Code?");
        FileEditor currentEditor = FileEditorManager.getInstance(project).getSelectedEditor();
        JComponent editorSize = currentEditor.getComponent();
        setDimensions(basicX + projectToolWindow.getWidth(), 2 * basicY, editorWidth, (int) fullSizeMinusTopBar.getHeight());
    }

    private void findRunButton() {
        System.out.println("Finde den Run-Button!");
        //IdeRootPane -> myToolBar -> components ist die Leiste mit dem Runbutton (9 und 10?)
        //another hat die ToolBar an erster Stelle, aber an die komme ich noch nicht ran

        //menuBar hat selbst keine Abmessungen
        JMenuBar menuBar = projectRoot.getJMenuBar();

        Container contentPane = projectRoot.getContentPane();
        //nicht hilfreich: das hier sind die Menüpunkte in der obersten Leiste
        MenuElement[] els = menuBar.getSubElements();

        Component[] moreComps = projectRoot.getContentPane().getComponents();

        setDimensions(basicX, basicY, moreComps[0].getWidth(), moreComps[0].getHeight());
    }

    private void findFolder(String folderToFind) {
        System.out.println("Find raus, ob man einzelne Ordner erkennen kann");
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
        System.out.println("Such das Dropdown im Projektfenster");
        ContentManager cm = ToolWindowManager.getInstance(project).getToolWindow("Project").getContentManager();
        JComponent dropdown = Objects.requireNonNull(cm.getContent(0)).getComponent();

        setDimensions(basicX+projectToolWindow.getX(), projectToolWindow.getY(), projectToolWindow.getWidth()/3, 28);
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
