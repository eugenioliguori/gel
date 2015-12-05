package gel.dock;

import java.util.ArrayList;
import java.util.List;

public class  DockActionHandler {

    private final static DockActionHandler INSTANCE = new DockActionHandler();

    public DockActionHandler() {}

    public static DockActionHandler getInstance() {
    	return INSTANCE;
    }

    private List<DockAction> actionsOnSelectObject = new ArrayList<DockAction>();
    private List<DockAction> actionsOnUnselectAllObjects = new ArrayList<DockAction>();


    public void addActionOnSelectObject(DockAction action) {
    	actionsOnSelectObject.add(action);
    }
    public void addActionOnUnselectAllObjects(DockAction action) {
    	actionsOnUnselectAllObjects.add(action);
    }

    public void runOnSelectObject() {
        for(DockAction action: actionsOnSelectObject) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void runOnUnselectAllObjects() {
        for(DockAction action: actionsOnUnselectAllObjects) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
