package boom.ui;

import javafx.fxml.FXML;

import boom.model.Board;
import boom.model.Cell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class BoardPane extends UiPart<Region> {

    private static final String FXML = "BoardPane.fxml";

    private Board board;

    @FXML
    private GridPane gridPane;

    public BoardPane(Board board){
        super(FXML);
        this.board=board;
        setUpBoard();
    }

    private void setUpBoard(){
        for(int i=0;i<board.getHeight();i++){
            for(int j=0;j<board.getWidth();j++){
                gridPane.add(new Tile(board.getCell(i,j)).getRoot(),i,j);
            }
        }
    }

    class Tile extends UiPart<Region> {

        private static final String FXML = "Tile.fxml";

        private Cell cell;

        @FXML
        private Button button;

        @FXML
        private Label label;

        public Tile(Cell cell){
            super(FXML);
            this.cell=cell;
            setUpButton();
        }

        private void setUpButton(){
            button.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if(cell.isRevealed()){
                    return;
                }
                if (e.getButton() == MouseButton.SECONDARY) {
                    cell.setFlag();
                }
            });
            button.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
                if(cell.isRevealed()){
                    return;
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    cell.reveal();
                }
            });
        }


    }

}
