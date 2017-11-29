import MP3player.MP3Player;
import MP3player.Show;
import MP3player.players.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class MP3PlayerInterface implements Show {
    public static final int WIDTH = 255;
    public static final int HEIGHT = 160;

    private MP3Player mp3Player;

    @Override
    public void show(Pane root, Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Button buttonPlayer1 = new Button("Плеер 1");
        buttonPlayer1.setPrefWidth(75);
        gridPane.add(buttonPlayer1, 0, 0);

        Button buttonPlayer2 = new Button("Плеер 2");
        buttonPlayer2.setPrefWidth(75);
        gridPane.add(buttonPlayer2, 1, 0);

        Button buttonPlayer3 = new Button("Плеер 3");
        buttonPlayer3.setPrefWidth(75);
        gridPane.add(buttonPlayer3, 2, 0);

        Button buttonPlayer4 = new Button("Плеер 4");
        buttonPlayer4.setPrefWidth(75);
        gridPane.add(buttonPlayer4, 0, 1);

        Button buttonPlayer5 = new Button("Плеер 5");
        buttonPlayer5.setPrefWidth(75);
        gridPane.add(buttonPlayer5, 1, 1);

        Button buttonPlayer6 = new Button("Плеер 6");
        buttonPlayer6.setPrefWidth(75);
        gridPane.add(buttonPlayer6, 2, 1);

        Button buttonRealPlayer = new Button("RealPlayer");
        buttonRealPlayer.setPrefWidth(155);
        gridPane.add(buttonRealPlayer, 0, 2, 2, 1);

        Button buttonClose = new Button("Закрыть");
        buttonClose.setPrefWidth(75);
        gridPane.add(buttonClose, 2, 2);

        Label labelStatus = new Label("Выберите плеер");
        gridPane.add(labelStatus, 0, 3, 3, 1);

        root.getChildren().addAll(gridPane);

        buttonClose.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            Platform.exit();
        });

        buttonPlayer1.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new Player1(100);
            labelStatus.setText("Плеер 1");
            primaryStage.setHeight(primaryStage.getHeight() + Player1.HEIGHT);
            mp3Player.show(root, primaryStage);
        });

        buttonPlayer2.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new Player2(200);
            labelStatus.setText("Плеер 2");
            primaryStage.setHeight(primaryStage.getHeight() + Player2.HEIGHT);
            mp3Player.show(root, primaryStage);
        });

        buttonPlayer3.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new Player3(300);
            labelStatus.setText("Плеер 3");
            primaryStage.setHeight(primaryStage.getHeight() + Player3.HEIGHT);
            mp3Player.show(root, primaryStage);
        });

        buttonPlayer4.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new Player4(400);
            labelStatus.setText("Плеер 4");
            primaryStage.setHeight(primaryStage.getHeight() + Player4.HEIGHT);
            mp3Player.show(root, primaryStage);
        });

        buttonPlayer5.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new Player5(500);
            labelStatus.setText("Плеер 5");
            primaryStage.setHeight(primaryStage.getHeight() + Player5.HEIGHT);
            mp3Player.show(root, primaryStage);
        });

        buttonPlayer6.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new Player6(600);
            labelStatus.setText("Плеер 6");
            primaryStage.setHeight(primaryStage.getHeight() + Player6.HEIGHT);
            mp3Player.show(root, primaryStage);
        });

        buttonRealPlayer.setOnAction((ActionEvent event) -> {
            close(root, primaryStage);
            mp3Player = new RealPlayer(700);
            labelStatus.setText("Real Player");
            primaryStage.setHeight(primaryStage.getHeight() + RealPlayer.HEIGHT);
            mp3Player.show(root, primaryStage);
        });
    }

    private void close(Pane root, Stage primaryStage) {
        if (mp3Player != null) {
            mp3Player.clear(root);
            mp3Player = null;
        }

        primaryStage.setHeight(HEIGHT);
    }
}