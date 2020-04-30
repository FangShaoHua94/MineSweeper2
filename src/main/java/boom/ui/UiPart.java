package boom.ui;

import javafx.fxml.FXMLLoader;
import boom.MainApp;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public abstract class UiPart<T> {

    public static final String FXML_FILE_FOLDER = "/view/";

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    public UiPart(URL fxmlFileUrl) {
        loadFxmlFile(fxmlFileUrl, null);
    }

    public UiPart(String fxmlFileName) {
        this(getFxmlFileUrl(fxmlFileName));
    }

    public UiPart(URL fxmlFileUrl, T root) {
        loadFxmlFile(fxmlFileUrl, root);
    }

    public UiPart(String fxmlFileName, T root) {
        this(getFxmlFileUrl(fxmlFileName), root);
    }

    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    private void loadFxmlFile(URL location, T root) {
        requireNonNull(location);
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(root);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static URL getFxmlFileUrl(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + fxmlFileName;
        URL fxmlFileUrl = MainApp.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }

}
