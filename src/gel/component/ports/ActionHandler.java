package gel.component.ports;

import java.util.ArrayList;
import java.util.List;

public class  ActionHandler {

    private final static ActionHandler INSTANCE = new ActionHandler();

    private ActionHandler() {}

    public static ActionHandler getInstance() {
    	return INSTANCE;
    }

    private List<Action> actions = new ArrayList<Action>();


    public void addAction(Action action) {
        actions.add(action);
    }

    public void run(String _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(int _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(long _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(char _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(char[] _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(byte[] _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(short _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(float _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run(double _data) {
        for(Action action: actions) {
            //just to handle exception if there is any. Not to hamper other actions in case of any failures
            try {
                action.run(_data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
