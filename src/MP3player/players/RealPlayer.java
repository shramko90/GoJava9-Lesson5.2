package MP3player.players;

import MP3player.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

public class RealPlayer extends MP3Player implements PlayAllSongs, ChooseFile, ChooseDir, Shuffle {
    private MediaPlayer mediaPlayer;                                            // медиа плеер
    private ArrayList<String> playlist = new ArrayList<>();                     // плейлист
    private int songInPlaylist;                                                 // текущая песня в плейлисте
    public static final int HEIGHT = 300;                                       // размер интерфейса
    private final ObservableList myLog = FXCollections.observableArrayList();   // элементы лога

    public RealPlayer(double price) {
        super(price);
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

        // кнопка Shuffle
        Button shuffleButton = new Button("Shuffle");
        shuffleButton.setPrefWidth(65);
        shuffleButton.setTranslateX(180);
        shuffleButton.setTranslateY(130);
        addNode(shuffleButton);

        // статус
        Label labelStatus = new Label();
        labelStatus.setTranslateX(70);
        labelStatus.setTranslateY(135);
        addNode(labelStatus);

        // кнопка Previous
        Button prevButton = new Button("Prev");
        prevButton.setPrefWidth(50);
        prevButton.setTranslateX(10);
        prevButton.setTranslateY(165);
        addNode(prevButton);

        // кнопка Pause
        final ToggleGroup group = new ToggleGroup();

        ToggleButton pauseButton = new ToggleButton("Pause");
        pauseButton.setToggleGroup(group);
        pauseButton.setPrefWidth(65);
        pauseButton.setTranslateX(65);
        pauseButton.setTranslateY(165);
        addNode(pauseButton);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle == null) {
                    if (mediaPlayer != null) mediaPlayer.play();
                } else {
                    if (mediaPlayer != null) mediaPlayer.pause();
                    else pauseButton.setSelected(false);
                }
            }
        });

        // кнопка Next
        Button nextButton = new Button("Next");
        nextButton.setPrefWidth(50);
        nextButton.setTranslateX(135);
        nextButton.setTranslateY(165);
        addNode(nextButton);

        // лог
        Label labelLog = new Label("Лог");
        labelLog.setTranslateX(10);
        labelLog.setTranslateY(200);
        addNode(labelLog);

        ListView listView = new ListView();
        listView.setPrefSize(235, 200);
        listView.setTranslateX(10);
        listView.setTranslateY(225);
        listView.setItems(myLog);
        addNode(listView);

        root.getChildren().addAll(playButton, playAllButton, shuffleButton,
                prevButton, pauseButton, nextButton, labelStatus, labelLog, listView);

        playButton.setOnAction((ActionEvent event) -> {
            chooseFile(primaryStage);
            pauseButton.setSelected(false);
            playSong();
        });

        playAllButton.setOnAction((ActionEvent event) -> {
            chooseDir(primaryStage);
            pauseButton.setSelected(false);
            playAllSongs();
        });

        shuffleButton.setOnAction((ActionEvent event) -> {
            pauseButton.setSelected(false);
            shuffle();
        });

        prevButton.setOnAction((ActionEvent event) -> {
            pauseButton.setSelected(false);
            playPrev();
        });

        nextButton.setOnAction((ActionEvent event) -> {
            pauseButton.setSelected(false);
            playNext();
        });
    }

    @Override
    public void playSong() {
        String logAdd;
        if (mediaPlayer!= null) mediaPlayer.stop();

        // генерируем объект вывода
        if (getCurrentSong() != null) {
            File mp3File = new File(getCurrentSong());

            logAdd = "Playing: " + mp3File.getName();

            Media hit = new Media(mp3File.toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } else logAdd = "Выберите файл";

        // добавляем запись в лог
        myLog.add(0, logAdd);
    }

    @Override
    public void playAllSongs() {
        if (playlist.size() > 0 && songInPlaylist < playlist.size()) {
            setCurrentSong(playlist.get(songInPlaylist));

            playSong();

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();

                playNext();
            });
        }
    }

    public void playNext() {
        if (songInPlaylist < playlist.size() - 1) {
            songInPlaylist++;
            playAllSongs();
        }
    }

    public void playPrev() {
        if (songInPlaylist > 0) {
            songInPlaylist--;
            playAllSongs();
        }
    }

    @Override
    public void chooseFile(Stage primaryStage) {
        playlist.clear();
        FileChooser fileChooser = new FileChooser();

        // фильтр разрешения
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);

        // диалог выбора файла
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null)
            setCurrentSong(file.getAbsolutePath());
        else setCurrentSong(null);
    }

    @Override
    public void chooseDir(Stage primaryStage) {
        playlist.clear();

        DirectoryChooser directoryChooser = new DirectoryChooser();

        // диалог выбора директории
        File file = directoryChooser.showDialog(primaryStage);
        if (file != null) {
            FilenameFilter filter = (dir, name) -> name.endsWith(".mp3");

            File[] listFiles = file.listFiles(filter);
            if (listFiles.length > 0) {
                songInPlaylist = 0;

                for (File element : listFiles) {
                    playlist.add(element.getAbsolutePath());
                }
            }
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(playlist);
        songInPlaylist = 0;
        playAllSongs();
    }
}
