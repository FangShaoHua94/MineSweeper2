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
                gridPane.add(new Tile(board.getCell(i,j)),i,j);
            }
        }
    }

    class Tile extends StackPane {

        private static final int DIMENSION = 25;

        private Cell cell;
        private Button button;
        private Label label;

        public Tile(Cell cell){
            this.cell=cell;
            button = new Button();
            label = new Label();
            setUpButton();
            setUpLabel();
            getChildren().addAll(label,button);
        }

        private void setUpButton(){
            button.setMinSize(DIMENSION,DIMENSION);
            button.setPrefSize(DIMENSION,DIMENSION);
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

        private void setUpLabel(){
            label.setMinSize(DIMENSION,DIMENSION);
            label.setPrefSize(DIMENSION,DIMENSION);
        }

    }

}
