package MP3player.players;

import MP3player.ChooseDir;
import MP3player.MP3Player;
import MP3player.PlayAllSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Player4 extends MP3Player implements PlayAllSongs, ChooseDir {
    private ArrayList<String> playlist = new ArrayList<>();                     // плейлист
    public static final int HEIGHT = 265;                                       // размер интерфейса
    private final ObservableList myLog = FXCollections.observableArrayList();   // элементы лога

    public Player4(double price) {
        super(price);
    }

    @Override
    public void playSong() {
        String logAdd;

        // генерируем объект вывода
        if (getCurrentSong() != null)
            logAdd = "Playing: " + getCurrentSong();
        else
            logAdd = "Выберите папку";

        // добавляем запись в лог
        myLog.add(0, logAdd);
    }

    @Override
    public void show(Pane root, Stage primaryStage) {
        // кнопка Play
        Button playButton = new Button("Play");
        playButton.setPrefWidth(50);
        playButton.setTranslateX(10);
        playButton.setTranslateY(130);
        addNode(playButton);

        // кнопка Play All Songs
        Button playAllButton = new Button("Play all songs");
        playAllButton.setPrefWidth(110);
        playAllButton.setTranslateX(65);
        playAllButton.setTranslateY(130);
        addNode(playAllButton);

        // статус
        Label labelStatus = new Label();
        labelStatus.setTranslateX(70);
        labelStatus.setTranslateY(135);
        addNode(labelStatus);

        // лог
        Label labelLog = new Label("Лог");
        labelLog.setTranslateX(10);
        labelLog.setTranslateY(165);
        addNode(labelLog);

        ListView listView = new ListView();
        listView.setPrefSize(235, 200);
        listView.setTranslateX(10);
        listView.setTranslateY(190);
        listView.setItems(myLog);
        addNode(listView);

        root.getChildren().addAll(playButton, playAllButton, labelStatus, labelLog, listView);

        playButton.setOnAction((ActionEvent event) -> {
            chooseDir(primaryStage);
            playSong();
        });

        playAllButton.setOnAction((ActionEvent event) -> {
            chooseDir(primaryStage);
            playAllSongs();
        });
    }

    @Override
    public void playAllSongs() {
        for (String name : playlist) {
            setCurrentSong(name);
            playSong();
        }
    }

    @Override
    public void chooseDir(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // диалог выбора директории
        File file = directoryChooser.showDialog(primaryStage);
        if (file != null) {
            FilenameFilter filter = (dir, name) -> name.endsWith(".mp3");

            File[] listFiles = file.listFiles(filter);
            if (listFiles.length > 0) {
                setCurrentSong(listFiles[listFiles.length - 1].getName());

                playlist.clear();
                for (File element : listFiles) {
                    playlist.add(element.getName());
                }
            }
        } else {
            setCurrentSong(null);
            playlist.clear();
        }
    }
}