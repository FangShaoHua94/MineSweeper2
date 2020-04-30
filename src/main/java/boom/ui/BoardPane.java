package boom.ui;

import boom.model.Board;
import javafx.scene.layout.Region;

public class BoardPane extends UiPart<Region> {

    private static final String FXML = "BoardPane.fxml";

    private Board board;

    public BoardPane(Board board){
        super(FXML);
        this.board=board;
    }



}
