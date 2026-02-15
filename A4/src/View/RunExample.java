package View;

import Controller.Controller;
import Model.Exceptions.MyException;

public class RunExample extends Command {
    private Controller controller;
    private boolean executed = false;

    public RunExample(String key, String desc, Controller controller) {
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        if (executed) {
            System.out.println("Example already executed!");
            return;
        }
        try{
            controller.allSteps();
        }  catch (MyException e){
            System.out.println("Error: " + e.getMessage());
        }
        executed = true;
    }
}
