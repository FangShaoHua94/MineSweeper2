import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Random;


public class Board extends Observable {

    private static final int NUM_TILE = 16;
    private static final int MINE_COUNT = 40;
    private int REVEALED_TILE_COUNT = 0;
    private boolean isFirstClick = true;

    private final GridPane boardPane;
    private final Tile[][] board = new Tile[NUM_TILE][NUM_TILE];
    private int flagCount = 0;
    private StringProperty flagCountDisplay = new ReadOnlyStringWrapper(String.format("%03d", MINE_COUNT - flagCount));
    private SmileyBean smileyBean;

    public Board(GridPane boardPane, SmileyBean smileyBean) {
        this.boardPane = boardPane;
        this.smileyBean = smileyBean;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < NUM_TILE; i++) {
            for (int j = 0; j < NUM_TILE; j++) {
                Tile tile = new Tile(i, j, boardPane.getPrefWidth() / NUM_TILE,
                        boardPane.getPrefHeight() / NUM_TILE);
                boardPane.add(tile, i, j);
                board[i][j] = tile;
            }
        }
    }

    private void setMines(Tile tile) {
        for (int i = 0; i < MINE_COUNT; i++) {
            Tile mineTile;
            Random random = new Random(System.currentTimeMillis() * i + System.currentTimeMillis());
            do {
                mineTile = board[random.nextInt(NUM_TILE)][random.nextInt(NUM_TILE)];
            } while ((mineTile.equals(tile) || mineTile.isMineTile()));
            mineTile.setMine();
        }
    }

    private int getNeighbourMineCount(Tile tile) {
        int count = 0;
        for (int i = tile.rowIndex - 1; i <= tile.rowIndex + 1; i++) {
            for (int j = tile.colIndex - 1; j <= tile.colIndex + 1; j++) {
                if (i < 0 || i >= NUM_TILE || j < 0 || j >= NUM_TILE) {
                    continue;
                }
                if (board[i][j].isMineTile()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void revealNeighbourTile(Tile tile) {
        for (int i = tile.rowIndex - 1; i <= tile.rowIndex + 1; i++) {
            for (int j = tile.colIndex - 1; j <= tile.colIndex + 1; j++) {
                if (i < 0 || i >= NUM_TILE || j < 0 || j >= NUM_TILE || board[i][j].equals(tile) || board[i][j].isReveal()) {
                    continue;
                }
                board[i][j].revealTile();
            }
        }
    }

    private void gameLost() {
        smileyBean.setFace(Face.DEAD);
        for (int i = 0; i < NUM_TILE; i++) {
            for (int j = 0; j < NUM_TILE; j++) {
                board[i][j].stop();
                if (board[i][j].isMineTile() && !board[i][j].isReveal()) {
                    board[i][j].revealMine();
                }
            }
        }
    }

    private void gameWon() {
        smileyBean.setFace(Face.COOL);
        for (int i = 0; i < NUM_TILE; i++) {
            for (int j = 0; j < NUM_TILE; j++) {
                board[i][j].stop();
                if (board[i][j].isMineTile()) {
                    board[i][j].flagTile();
                }
            }
        }
    }

    public ObservableStringValue getFlagCountDisplay() {
        return flagCountDisplay;
    }

    private void activateTimer() {
        setChanged();
        notifyObservers();
    }

    class Tile extends StackPane {

        private final double width;
        private final double height;
        private final int rowIndex;
        private final int colIndex;

        private Button button;
        private Label label;

        private boolean isRevealed = false;
        private boolean isFlagged = false;
        private boolean isMine = false;

        public Tile(int rowIndex, int colIndex, double width, double height) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            this.width = width;
            this.height = height;
            setUpTile();
        }

        private void setUpTile() {
            getStylesheets().add(getClass().getResource("/view/Tile.css").toExternalForm());
            setPrefSize(width, height);
            setUpLabel();
            setUpButton();
            getChildren().addAll(label, button);
        }

        private void setUpLabel() {
            label = new Label();
            label.setPrefSize(width, height);
            label.setMinSize(width, height);
        }

        private void setUpButton() {
            button = new Button();
            button.setPrefSize(width, height);
            button.setMinSize(width, height);
            button.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    smileyBean.setFace(Face.OI);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    rightClick();
                }
            });
            button.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    smileyBean.setFace(Face.SMILE);
                    leftClick();
                }
            });
        }

        private void leftClick() {
            if (isFirstClick) {
                setMines(this);
                isFirstClick = false;
                activateTimer();
            }
            if (isMine) {
                setRedMine();
                gameLost();
                return;
            }
            revealTile();
        }

        private void rightClick() {
            if (!isFlagged) {
                flagTile();
            } else {
                unFlagTile();
            }
        }

        private void flagTile() {
            if (isFlagged || flagCount == MINE_COUNT) {
                return;
            }
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/flag.png")));
            image.setPreserveRatio(true);
            image.fitHeightProperty().bind(button.widthProperty());
            image.fitHeightProperty().bind(button.heightProperty());
            button.setGraphic(image);
            isFlagged = true;
            flagCount++;
            flagCountDisplay.setValue(String.format("%03d", MINE_COUNT - flagCount));
        }

        private void unFlagTile() {
            if (!isFlagged) {
                return;
            }
            button.setGraphic(null);
            isFlagged = false;
            flagCount--;
            flagCountDisplay.setValue(String.format("%03d", MINE_COUNT - flagCount));
        }

        private void setMine() {
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mine.png")));
            image.setPreserveRatio(true);
            image.fitHeightProperty().bind(label.widthProperty());
            image.fitHeightProperty().bind(label.heightProperty());
            label.setGraphic(image);
            isMine = true;
//            to see the location of the mine
//            button.setVisible(false);
//            label.setStyle("-fx-background-color: blue");
        }

        private void setRedMine() {
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/redMine.jpg")));
            image.setPreserveRatio(true);
            image.fitHeightProperty().bind(label.widthProperty());
            image.fitHeightProperty().bind(label.heightProperty());
            label.setGraphic(image);
            isRevealed = true;
        }

        private void revealMine() {
            button.setVisible(false);
            isRevealed = true;
        }

        private boolean isReveal() {
            return isRevealed;
        }

        private boolean isMineTile() {
            return isMine;
        }

        private void stop() {
            button.setDisable(true);
        }

        private void revealTile() {
            isRevealed = true;
            REVEALED_TILE_COUNT++;
            button.setVisible(false);

            int neighbouringMineCount = Board.this.getNeighbourMineCount(this);
            switch (neighbouringMineCount) {
            case 0:
                revealNeighbourTile(this);
                break;
            case 1:
                label.setStyle("-fx-text-fill: blue");
                label.setText("  " + neighbouringMineCount);
                break;
            case 2:
                label.setStyle("-fx-text-fill: green");
                label.setText("  " + neighbouringMineCount);
                break;
            case 3:
                label.setStyle("-fx-text-fill: red");
                label.setText("  " + neighbouringMineCount);
                break;
            case 4:
                label.setStyle("-fx-text-fill: darkblue");
                label.setText("  " + neighbouringMineCount);
                break;
            case 5:
                label.setStyle("-fx-text-fill: brown");
                label.setText("  " + neighbouringMineCount);
                break;
            case 6:
                label.setStyle("-fx-text-fill: darkviolet");
                label.setText("  " + neighbouringMineCount);
                break;
            case 7:
                label.setStyle("-fx-text-fill: deeppink");
                label.setText("  " + neighbouringMineCount);
                break;
            case 8:
                label.setStyle("-fx-text-fill: tomato");
                label.setText("  " + neighbouringMineCount);
                break;
            default:
                label.setStyle("-fx-background-color: red");
            }

            if (NUM_TILE * NUM_TILE - REVEALED_TILE_COUNT == MINE_COUNT) {
                gameWon();
            }
        }
    }


}
