package boom.ui;

import javafx.fxml.FXML;

import boom.model.Board;
import boom.model.Cell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class BoardPane extends UiPart<Region> {

    private static final String FXML = "BoardPane.fxml";
    private static boolean isFirstClick=false;

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
                gridPane.add(new Tile(board.getCell(i,j)).getRoot(),j,i);
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
                if (e.getButton() == MouseButton.SECONDARY) {
                    setFlag();
                }
            });
            button.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    reveal();
                }
            });
        }

        private void setFlag(){
            if(cell.isRevealed()){
                return;
            }
            cell.setFlag();
            if(cell.isFlagged()){
                ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/flag.png")));
                image.setPreserveRatio(true);
                image.fitHeightProperty().bind(button.widthProperty());
                image.fitHeightProperty().bind(button.heightProperty());
                button.setGraphic(image);
            }else{
                button.setGraphic(null);
            }
        }

        private void reveal(){
            if(!isFirstClick){
                isFirstClick=true;
                board.setMine(cell);
                board.print();
            }
            if(cell.isRevealed()){
                return;
            }
            button.setDisable(true);
            cell.reveal();
            board.setCellValue(cell);
            displayValue();
        }

        private void displayValue(){
            switch (cell.getValue()) {
            case 0:
                // todo recursive
//                revealNeighbourTile(this);
                break;
            case 1:
                label.setStyle("-fx-text-fill: blue");
                break;
            case 2:
                label.setStyle("-fx-text-fill: green");
                break;
            case 3:
                label.setStyle("-fx-text-fill: red");
                break;
            case 4:
                label.setStyle("-fx-text-fill: darkblue");
                break;
            case 5:
                label.setStyle("-fx-text-fill: brown");
                break;
            case 6:
                label.setStyle("-fx-text-fill: darkviolet");
                break;
            case 7:
                label.setStyle("-fx-text-fill: deeppink");
                break;
            case 8:
                label.setStyle("-fx-text-fill: tomato");
                break;
            default:
                label.setStyle("-fx-background-color: red");
            }
            label.setText(""+cell.getValue());
        }

    }
}
