package View;

import Controller.Controller;
import Model.ADT.FileTable;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Expressions.ArithExpr;
import Model.Expressions.RelationalExpr;
import Model.Expressions.ValueExpr;
import Model.Expressions.VarExpr;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.Repository;

public class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExpr(new IntValue(2))), new PrintStmt(new VarExpr("v"))));
        PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), ex1);
        Repository repo1 = new Repository(prg1, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExpr(1, new ValueExpr(new IntValue(2)), new ArithExpr(3, new ValueExpr(new IntValue(3)), new ValueExpr(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExpr(1, new VarExpr("a"), new ValueExpr(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));
        PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), ex2);
        Repository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))), new AssignStmt("v", new ValueExpr(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));
        PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), ex3);
        Repository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);

        IStmt test = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExpr("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CloseRFileStmt(new VarExpr("varf"))))))))));
        PrgState prgT = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), test);
        Repository repoT = new Repository(prgT, "test.out");
        Controller ctrlT = new Controller(repoT);

        IStmt exRelational = new CompStmt(new VarDeclStmt("a", new IntType()),new CompStmt(new VarDeclStmt("b", new IntType()),new CompStmt(new AssignStmt("a", new ValueExpr(new IntValue(5))), new CompStmt(new AssignStmt("b", new ValueExpr(new IntValue(7))), new PrintStmt(new RelationalExpr(new VarExpr("a"), new VarExpr("b"), "<"))))));
        PrgState prgR = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), exRelational);
        Repository repoR = new Repository(prgR, "log4.txt");
        Controller ctrlR = new Controller(repoR);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("6", "Exit."));
        menu.addCommand(new RunExample("1", "Run example 1.", ctrl1));
        menu.addCommand(new RunExample("2", "Run example 2.", ctrl2));
        menu.addCommand(new RunExample("3", "Run example 3", ctrl3));
        menu.addCommand(new RunExample("4", "Run test.", ctrlT));
        menu.addCommand(new RunExample("5", "Run relational example.", ctrlR));
        menu.show();
    }
}



