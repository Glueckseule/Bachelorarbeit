package walkthrough.toolWindow.highlighting;

import com.intellij.openapi.components.ServiceManager;

import java.util.ArrayList;

public class HighlightingService {

    private ArrayList<TargetArea> targetAreas;

    public static HighlightingService getInstance() {
        return ServiceManager.getService(HighlightingService.class);
    }


    // TODO: We should be able to update these targets if nesccary
    private void loadTargetAreas() {
        targetAreas = new ArrayList<TargetArea>();
        // Try to find every relevant target and create container
        targetAreas.add(new TargetArea(0,0,50,50,"FILE-MENU"));
    }

    public void setHighlightForArea(String areaCode) {
        // Check if areaCode is in targetAreas
        // Highlight area
    }

    public void removeCurrentHighlight() {

    }

}
