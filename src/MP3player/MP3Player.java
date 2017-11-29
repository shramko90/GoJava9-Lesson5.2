package MP3player;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public abstract class MP3Player implements Show {
    private final double price;
    private String currentSong;
    private ArrayList<Node> nodeArrayList;

    protected MP3Player(double price) {
        this.price = price;
    }

    public String getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(String currentSong) {
        this.currentSong = currentSong;
    }

    //играть песню
    public abstract void playSong();

    // удаляем интерфейс плеера
    public void clear(Pane root) {
        if (nodeArrayList != null) {
            for (Node node : nodeArrayList)
                root.getChildren().remove(node);

            nodeArrayList.clear();
        }
    }

    // добавить элемент интерфейса
    public void addNode(Node node) {
        if (nodeArrayList == null) nodeArrayList = new ArrayList<>();

        nodeArrayList.add(node);
    }


}
