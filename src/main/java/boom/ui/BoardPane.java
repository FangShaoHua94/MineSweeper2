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
    private Tile[][] tiles;

    @FXML
    private GridPane gridPane;

    public BoardPane(Board board){
        super(FXML);
        this.board=board;
        setUpBoard();
    }

    private void setUpBoard(){
        tiles= new Tile[board.getHeight()][board.getWidth()];
        for(int i=0;i<board.getHeight();i++){
            for(int j=0;j<board.getWidth();j++){
                Tile tile = new Tile(board.getCell(i,j));
                gridPane.add(tile.getRoot(),j,i);
                tiles[i][j]=tile;
            }
        }
    }

    private void revealNeighbourTile(Tile tile){
        Cell cell=tile.getCell();
        for (int i = cell.getRowIndex() - 1; i <= cell.getRowIndex() + 1; i++) {
            for (int j = cell.getColIndex() - 1; j <= cell.getColIndex() + 1; j++) {
                if (i < 0 || i >= board.getHeight() || j < 0 || j >= board.getWidth()
                        || tiles[i][j].equals(tile) || board.getCell(i,j).isRevealed()) {
                    continue;
                }
                tiles[i][j].reveal();
            }
        }
    }

    private void gameOver(Tile tile){
        for(int i=0;i<board.getHeight();i++){
            for(int j=0;j<board.getWidth();j++){
                tiles[i][j].disableButton();
                if(tiles[i][j].getCell().isMineTile()){
                    if(tiles[i][j]==tile){
                        continue;
                    }
                    tiles[i][j].revealMine();
                }
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

        public Cell getCell(){
            return cell;
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
            disableButton();
            if(cell.isMineTile()){
                displayRedMine();
                gameOver(this);
            }
            cell.reveal();
            board.setCellValue(cell);
            revealTile();
        }

        private void revealTile(){
            switch (cell.getValue()) {
            case 0:
                revealNeighbourTile(this);
                return;
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

        public void disableButton(){
            button.setDisable(true);
            button.setVisible(false);
        }

        public void revealMine(){
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mine.png")));
            setLabelImage(image);
        }

        private void displayRedMine(){
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/redMine.jpg")));
            setLabelImage(image);
        }

        private void setLabelImage(ImageView image){
            image.setPreserveRatio(true);
            image.fitHeightProperty().bind(label.widthProperty());
            image.fitHeightProperty().bind(label.heightProperty());
            label.setGraphic(image);
        }

    }
}
