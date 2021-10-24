import Advanced.GUI;
import Control.Control;
import Control.Dict;

public class Main {
    
    public static void main(String[] args) {
        Control control = new Control();
        Dict dict = control.trigger();
        GUI gui = new GUI(control, dict);
        gui.deploy();
    }
}
