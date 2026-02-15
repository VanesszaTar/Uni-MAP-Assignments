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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        ListView<IStmt> programListView = new ListView<>();
        programListView.setItems(FXCollections.observableArrayList(getExamplePrograms()));

        Button selectButton = new Button("Select program");
        selectButton.setOnAction(e -> {
            IStmt selected = programListView.getSelectionModel().getSelectedItem();
            if (selected == null)
                return;
            try {
                selected.typecheck(new MyDictionary<>());
                PrgState prg = new PrgState(
                        new MyStack<>(),
                        new MyDictionary<>(),
                        new MyList<>(),
                        new FileTable(),
                        new MyHeap(),
                        selected
                );

                Repository repo = new Repository(prg, "log_gui.txt");
                Controller controller = new Controller(repo);

                MainWindow mainWindow = new MainWindow(controller);
                mainWindow.show();
            } catch (MyException ex) {
                System.out.println("Typecheck error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, programListView, selectButton);
        Scene scene = new Scene(layout, 900, 400);

        stage.setTitle("Select program");
        stage.setScene(scene);
        stage.show();
    }

    private List<IStmt> getExamplePrograms() {
        IStmt ex1 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExpr(new IntValue(2))),
                                new PrintStmt(new VarExpr("v"))
                        )
                );
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExpr(1, new ValueExpr(new IntValue(2)), new ArithExpr(3, new ValueExpr(new IntValue(3)), new ValueExpr(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExpr(1, new VarExpr("a"), new ValueExpr(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));
            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))), new AssignStmt("v", new ValueExpr(new IntValue(3)))), new PrintStmt(new VarExpr("v"))))));
            IStmt test = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExpr("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"), new CompStmt(new PrintStmt(new VarExpr("varc")), new CloseRFileStmt(new VarExpr("varf"))))))))));
            IStmt exRelational = new CompStmt(new VarDeclStmt("a", new IntType()),new CompStmt(new VarDeclStmt("b", new IntType()),new CompStmt(new AssignStmt("a", new ValueExpr(new IntValue(5))), new CompStmt(new AssignStmt("b", new ValueExpr(new IntValue(7))), new PrintStmt(new RelationalExpr(new VarExpr("a"), new VarExpr("b"), "<"))))));
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

            IStmt exGarbageCollector = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(
                            new NewStmt("v", new ValueExpr(new IntValue(20))),
                            new CompStmt(
                                    new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(
                                            new NewStmt("a", new VarExpr("v")),
                                            new CompStmt(
                                                    new NewStmt("v", new ValueExpr(new IntValue(30))),
                                                    new PrintStmt(
                                                            new RHExpr(new RHExpr(new VarExpr("a")))
                                                    )
                                            )
                                    )
                            )
                    )
            );

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
        return List.of(ex1, ex2, ex3, test, exRelational, exHeap, exHeapRead, exHeapWrite, exGarbageCollector, whileExample, forkExample);
    }

    public static void main(String[] args) {
        launch();
    }
}
