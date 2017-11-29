package MP3player.players;

import MP3player.ChooseFile;
import MP3player.MP3Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Player1 extends MP3Player implements ChooseFile {
    public static final int HEIGHT = 265;                                       // размер интерфейса
    private final ObservableList myLog = FXCollections.observableArrayList();   // элементы лога

    public Player1(double price) {
        super(price);
    }

    @Override
    public void playSong() {
        String logAdd;

        // генерируем объект вывода
        if (getCurrentSong() != null)
            logAdd = "Playing: " + getCurrentSong();
        else
            logAdd = "Выберите файл";

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

        root.getChildren().addAll(playButton, labelStatus, labelLog, listView);

        playButton.setOnAction((ActionEvent event) -> {
            chooseFile(primaryStage);
            playSong();
        });
    }

    @Override
    public void chooseFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();

        // фильтр разрешения
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);

        // диалог выбора файла
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null)
            setCurrentSong(file.getName());
        else setCurrentSong(null);
    }
}
