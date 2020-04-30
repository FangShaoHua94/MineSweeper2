package boom.ui;

import javafx.fxml.FXML;

import boom.logic.Logic;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends UiPart<Stage>{

    private static final String FXML = "MainWindow.fxml";

    private Logic logic;
    private Stage primaryStage;

    private DisplayPanel displayPanel;
    private BoardPane boardPane;

    @FXML
    private StackPane displayPanelHolder;

    @FXML
    private StackPane boardPaneHolder;

    @FXML
    private MenuBar menuBar;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        this.logic=logic;
        this.primaryStage=primaryStage;
    }

    public void show(){
        primaryStage.show();
    }

    public void fillInnerParts(){
        displayPanel = new DisplayPanel();
        displayPanelHolder.getChildren().add(displayPanel.getRoot());

        boardPane = new BoardPane(logic.getBoard());
        boardPaneHolder.getChildren().add(boardPane.getRoot());
    }

}
