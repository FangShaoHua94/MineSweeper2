package boom;

import boom.logic.Logic;
import boom.logic.LogicManager;
import boom.model.Model;
import boom.model.ModelManager;
import boom.storage.Storage;
import boom.storage.StorageManager;
import boom.ui.Ui;
import boom.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Logic logic;
    private Model model;
    private Storage storage;
    private Ui ui;


    @Override
    public void init() throws Exception {
        model = new ModelManager();
        logic = new LogicManager(model);
        storage = new StorageManager();
        ui = new UiManager(logic);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui.start(primaryStage);
    }

}
