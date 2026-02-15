package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.Repository;

public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExpr(new IntValue(2))), new PrintStmt(new VarExpr("v"))));
        try {
            ex1.typecheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), ex1);
            Repository repo1 = new Repository(prg1, "log1.txt");
            Controller ctrl1 = new Controller(repo1);
            Command c1 = new RunExample("1", "Run example 1.",ctrl1);
            menu.addCommand(c1);
        } catch (MyException e) {
            System.out.println("Typecheck error in example 1: " + e.getMessage());
        }

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExpr(1, new ValueExpr(new IntValue(2)), new ArithExpr(3, new ValueExpr(new IntValue(3)), new ValueExpr(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExpr(1, new VarExpr("a"), new ValueExpr(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));
        try {
            ex2.typecheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), ex2);
            Repository repo2 = new Repository(prg2, "log2.txt");
            Controller ctrl2 = new Controller(repo2);
            Command c2 = new RunExample("2", "Run example 2.",ctrl2);
            menu.addCommand(c2);
        } catch (MyException e) {
            System.out.println("Typecheck error in example 2: " + e.getMessage());
        }

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))), new AssignStmt("v", new ValueExpr(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));
        try {
            ex3.typecheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), ex3);
            Repository repo3 = new Repository(prg3, "log3.txt");
            Controller ctrl3 = new Controller(repo3);
            Command c3 = new RunExample("3", "Run example 3.",ctrl3);
            menu.addCommand(c3);
        }  catch (MyException e) {
            System.out.println("Typecheck error in example 3: " + e.getMessage());
        }

        IStmt test = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExpr("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CloseRFileStmt(new VarExpr("varf"))))))))));
        try {
            test.typecheck(new MyDictionary<>());
            PrgState prgT = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), test);
            Repository repoT = new Repository(prgT, "test.out");
            Controller ctrlT = new Controller(repoT);
            Command cT = new RunExample("4", "Run test example.",ctrlT);
            menu.addCommand(cT);
        } catch (MyException e) {
            System.out.println("Typecheck error in example test: " + e.getMessage());
        }

        IStmt exRelational = new CompStmt(new VarDeclStmt("a", new IntType()),new CompStmt(new VarDeclStmt("b", new IntType()),new CompStmt(new AssignStmt("a", new ValueExpr(new IntValue(5))), new CompStmt(new AssignStmt("b", new ValueExpr(new IntValue(7))), new PrintStmt(new RelationalExpr(new VarExpr("a"), new VarExpr("b"), "<"))))));
        try {
            exRelational.typecheck(new MyDictionary<>());
            PrgState prgR = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exRelational);
            Repository repoR = new Repository(prgR, "log4.txt");
            Controller ctrlR = new Controller(repoR);
            Command cR = new RunExample("5", "Run relational example.",ctrlR);
            menu.addCommand(cR);
        }  catch (MyException e) {
            System.out.println("Typecheck error in relational example: " + e.getMessage());
        }

        IStmt exHeap = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExpr("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExpr("v")),
                                                new PrintStmt(new VarExpr("a"))
                                        )
                                )
                        )
                )
        );
        try {
            exHeap.typecheck(new MyDictionary<>());
            PrgState prgH = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exHeap);
            Repository repoH = new Repository(prgH, "log5.txt");
            Controller ctrlH = new Controller(repoH);
            Command cH = new RunExample("6", "Run heap example.",ctrlH);
            menu.addCommand(cH);
        }  catch (MyException e) {
            System.out.println("Typecheck error in heap example: " + e.getMessage());
        }

        IStmt exHeapRead = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExpr("v")),
                                        new CompStmt(
                                                new PrintStmt(new RHExpr(new VarExpr("v"))),
                                                new PrintStmt(
                                                        new ArithExpr(
                                                                1,
                                                                new RHExpr(new RHExpr(new VarExpr("a"))),
                                                                new ValueExpr(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            exHeapRead.typecheck(new MyDictionary<>());
            PrgState prgRH = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exHeapRead);
            Repository repoRH = new Repository(prgRH, "log6.txt");
            Controller ctrlRH = new Controller(repoRH);
            Command cRH = new RunExample("7", "Run heap read example.",ctrlRH);
            menu.addCommand(cRH);
        }   catch (MyException e) {
            System.out.println("Typecheck error in heap read example: " + e.getMessage());
        }

        IStmt exHeapWrite = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new RHExpr(new VarExpr("v"))),
                                new CompStmt(
                                        new WHStmt("v", new ValueExpr(new IntValue(30))),
                                        new PrintStmt(
                                                new ArithExpr(1, new RHExpr(new VarExpr("v")), new ValueExpr(new IntValue(5)))
                                        )
                                )
                        )
                )
        );
        try {
            exHeapWrite.typecheck(new MyDictionary<>());
            PrgState prgWH = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exHeapWrite);
            Repository repoWH = new Repository(prgWH, "log7.txt");
            Controller ctrlWH = new Controller(repoWH);
            Command cWH = new RunExample("8", "Run heap write example.",ctrlWH);
            menu.addCommand(cWH);
        } catch (MyException e) {
            System.out.println("Typecheck error in heap write example: " + e.getMessage());
        }
        IStmt exGarbageCollector = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExpr(new IntValue(20))),
                        // new CompStmt(
                        // new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        //new CompStmt(
                        //new NewStmt("a", new VarExpr("v")),
                        //new CompStmt(
                        new NewStmt("v", new ValueExpr(new IntValue(30)))
                        //new PrintStmt(
                        //new RHExpr(new RHExpr(new VarExpr("a")))
                        //)
                        //)
                        //)
                        //)
                )
        );
        try {
            exGarbageCollector.typecheck(new MyDictionary<>());
            PrgState prgGC = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exGarbageCollector);
            Repository repoGC = new Repository(prgGC, "log8.txt");
            Controller ctrlGC = new Controller(repoGC);
            Command cGC = new RunExample("9", "Run garbage collector example.",ctrlGC);
            menu.addCommand(cGC);
        }  catch (MyException e) {
            System.out.println("Typecheck error in garbage collector example: " + e.getMessage());
        }

        IStmt whileExample = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExpr(new VarExpr("v"), new ValueExpr(new IntValue(0)), ">"),
                                        new CompStmt(
                                                new PrintStmt(new VarExpr("v")),
                                                new AssignStmt("v", new ArithExpr(2, new VarExpr("v"), new ValueExpr(new IntValue(1))))
                                        )
                                ),
                                new PrintStmt(new VarExpr("v"))
                        )
                )
        );
        try {
            whileExample.typecheck(new MyDictionary<>());
            PrgState prgWhile = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), whileExample);
            Repository repoWhile = new Repository(prgWhile, "log9.txt");
            Controller ctrlWhile = new Controller(repoWhile);
            Command cWhile = new RunExample("10", "Run while example.",ctrlWhile);
            menu.addCommand(cWhile);
        }  catch (MyException e) {
            System.out.println("Typecheck error in while example: " + e.getMessage());
        }

        IStmt forkExample = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExpr(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExpr(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WHStmt("a", new ValueExpr(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExpr(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExpr("v")),
                                                                                new PrintStmt(new RHExpr(new VarExpr("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExpr("v")),
                                                        new PrintStmt(new RHExpr(new VarExpr("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            forkExample.typecheck(new MyDictionary<>());
            PrgState prgFork = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), forkExample);
            Repository repoFork = new Repository(prgFork, "log10.txt");
            Controller ctrlFork = new Controller(repoFork);
            Command cFork = new RunExample("11", "Run fork example.",ctrlFork);
            menu.addCommand(cFork);

        } catch (MyException e) {
            System.out.println("Typecheck error in fork example: " + e.getMessage());
        }
        menu.addCommand(new ExitCommand("0", "Exit."));
        menu.show();
    }
}