package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.Repository;

public class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExpr(new IntValue(2))), new PrintStmt(new VarExpr("v"))));
        PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), ex1);
        Repository repo1 = new Repository(prg1, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExpr(1, new ValueExpr(new IntValue(2)), new ArithExpr(3, new ValueExpr(new IntValue(3)), new ValueExpr(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExpr(1, new VarExpr("a"), new ValueExpr(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));
        PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), ex2);
        Repository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))), new AssignStmt("v", new ValueExpr(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));
        PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), ex3);
        Repository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);

        IStmt test = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExpr("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CloseRFileStmt(new VarExpr("varf"))))))))));
        PrgState prgT = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), test);
        Repository repoT = new Repository(prgT, "test.out");
        Controller ctrlT = new Controller(repoT);

        IStmt exRelational = new CompStmt(new VarDeclStmt("a", new IntType()),new CompStmt(new VarDeclStmt("b", new IntType()),new CompStmt(new AssignStmt("a", new ValueExpr(new IntValue(5))), new CompStmt(new AssignStmt("b", new ValueExpr(new IntValue(7))), new PrintStmt(new RelationalExpr(new VarExpr("a"), new VarExpr("b"), "<"))))));
        PrgState prgR = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exRelational);
        Repository repoR = new Repository(prgR, "log4.txt");
        Controller ctrlR = new Controller(repoR);

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

        PrgState prgH =  new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exHeap);
        Repository repoH = new Repository(prgH, "log5.txt");
        Controller ctrlH = new Controller(repoH);

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
        PrgState prgRH = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exHeapRead);
        Repository repoRH = new Repository(prgRH, "log6.txt");
        Controller ctrlRH = new Controller(repoRH);

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
        PrgState prgWH = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exHeapWrite);
        Repository repoWH = new Repository(prgWH, "log7.txt");
        Controller ctrlWH = new Controller(repoWH);

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
        PrgState prgGC = new PrgState(new MyStack<IStmt>(), new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), exGarbageCollector);
        Repository repoGC = new Repository(prgGC, "log8.txt");
        Controller ctrlGC = new Controller(repoGC);

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
        PrgState prgWhile = new PrgState(new MyStack<IStmt>(),  new MyDictionary<>(), new MyList<>(), new FileTable(), new MyHeap(), whileExample);
        Repository repoWhile = new Repository(prgWhile, "log9.txt");
        Controller ctrlWhile = new Controller(repoWhile);


        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExample("1", "Run example 1.", ctrl1));
        menu.addCommand(new RunExample("2", "Run example 2.", ctrl2));
        menu.addCommand(new RunExample("3", "Run example 3", ctrl3));
        menu.addCommand(new RunExample("4", "Run test.", ctrlT));
        menu.addCommand(new RunExample("5", "Run relational example.", ctrlR));
        menu.addCommand(new RunExample("6", "Run Heap example.", ctrlH));
        menu.addCommand(new  RunExample("7", "Run Heap Read example.", ctrlRH));
        menu.addCommand(new  RunExample("8", "Run Heap Write example.", ctrlWH));
        menu.addCommand(new RunExample("9", "Run Heap Garbage Collector example.", ctrlGC));
        menu.addCommand(new RunExample("10", "Run While example.", ctrlWhile));
        menu.addCommand(new ExitCommand("0", "Exit."));
        menu.show();
    }
}



