package View;

import Controller.Controller;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Expressions.ArithExpr;
import Model.Expressions.ValueExpr;
import Model.Expressions.VarExpr;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Repository.Repository;

import java.util.Scanner;

public class View {
    IStmt example1() {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExpr(new IntValue(2))), new PrintStmt(new VarExpr("v"))));
        return ex1;
    }

    IStmt example2() {
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExpr(1, new ValueExpr(new IntValue(2)), new ArithExpr(3, new ValueExpr(new IntValue(3)), new ValueExpr(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExpr(1, new VarExpr("a"), new ValueExpr(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));
        return ex2;
    }

    IStmt example3() {
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))), new AssignStmt("v", new ValueExpr(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));
        return ex3;
    }

    public void printMenu() {
        System.out.println("~~~~~MENU~~~~~");
        System.out.println("1. Run example one.");
        System.out.println("2. Run example two.");
        System.out.println("3. Run example three.");
        System.out.println("4. Change display flag.");
        System.out.println("5. Exit.");
    }

    void runExample(IStmt ex, boolean displayFlag) {
        PrgState prgState = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), ex);
        Repository repo = new Repository();
        repo.addProgram(prgState);
        Controller controller = new Controller(repo);
        controller.setDisplayFlag(displayFlag);
        try {
            controller.allSteps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startApp() {
        IStmt example1 = example1();
        IStmt example2 = example2();
        IStmt example3 = example3();
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean displayFlag = true;
        while (true) {
            printMenu();
            System.out.println("Choose a number between 1 and 5: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> runExample(example1, displayFlag);
                case 2 -> runExample(example2, displayFlag);
                case 3 -> runExample(example3, displayFlag);
                case 4 -> {
                    displayFlag = !displayFlag;
                    System.out.println("Value of display flag: " + displayFlag);
                }
                case 5 -> {
                    System.out.println("Exiting. . .");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please pick a number between 1 and 5!");
            }
        }
    }
}



