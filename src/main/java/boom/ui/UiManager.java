package boom.ui;

import boom.logic.Logic;
import javafx.stage.Stage;

public class UiManager implements Ui{

    private Logic logic;
    private MainWindow mainWindow;

    public UiManager(Logic logic){
        this.logic= logic;
    }

    public void start(Stage primaryStage){
        mainWindow = new MainWindow(primaryStage, logic);
        mainWindow.show();
        mainWindow.fillInnerParts();
    }


}
