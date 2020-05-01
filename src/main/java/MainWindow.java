
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class MainWindow extends AnchorPane {

    @FXML
    private GridPane boardPane;

    @FXML
    private AnchorPane topWindow;

    @FXML
    private Button smiley;

    @FXML
    private Label timeDisplay;

    @FXML
    private Label mineCountDisplay;

    private Board board;
    private Timer timer;
    private SmileyBean smileyBean;

    @FXML
    public void initialize() {
        timer = new Timer();
        smileyBean = new SmileyBean();
        board = new Board(boardPane, smileyBean);
        smileyBean.addPropertyChangeListener(e -> {
            changeFace((Face) e.getNewValue());
        });
        setImage(new ImageView(new Image(getClass().getResourceAsStream("/images/smileFace.png"))));
        board.addObserver(timer);
        timeDisplay.textProperty().bind(timer.getTimeSeconds());
        mineCountDisplay.textProperty().bind(board.getFlagCountDisplay());
    }

    @FXML
    public void resetGame() {
        initialize();
    }

    private void changeFace(Face face) {
        ImageView image = null;
        switch (face) {
        case SMILE:
            image = new ImageView(new Image(getClass().getResourceAsStream("/images/smileFace.png")));
            break;
        case OI:
            image = new ImageView(new Image(getClass().getResourceAsStream("/images/oiFace.png")));
            break;
        case COOL:
            image = new ImageView(new Image(getClass().getResourceAsStream("/images/coolFace.png")));
            timer.stop();
            break;
        case DEAD:
            image = new ImageView(new Image(getClass().getResourceAsStream("/images/deadFace.png")));
            timer.stop();
            break;
        default:
            break;
        }
        setImage(image);
    }

    private void setImage(ImageView image) {
        image.setPreserveRatio(true);
        image.fitHeightProperty().bind(smiley.widthProperty());
        image.fitHeightProperty().bind(smiley.heightProperty());
        smiley.setGraphic(image);
        smiley.setStyle("-fx-content-display: center");
    }
}
