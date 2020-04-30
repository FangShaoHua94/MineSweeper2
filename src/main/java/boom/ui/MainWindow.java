package boom.ui;

import boom.logic.Logic;
import javafx.stage.Stage;

public class MainWindow extends UiPart<Stage>{

    private static final String FXML = "MainWindow.fxml";

    private Logic logic;
    private Stage primaryStage;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        this.logic=logic;
        this.primaryStage=primaryStage;
    }

    public void show(){
        primaryStage.show();
    }

    public void fillInnerParts(){

    }

}
