import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SmileyBean {

    private Face face;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face newFace) {
        Face oldFace = this.face;
        this.face = newFace;
        support.firePropertyChange("face", oldFace, newFace);
    }
}
