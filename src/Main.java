import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();         // создаем пустую панель

        MP3PlayerInterface mp3PlayerInterface = new MP3PlayerInterface();
        mp3PlayerInterface.show(root, primaryStage);

        Scene scene = new Scene(root);  // создаем сцену и кладем внутрь root (пустую панельку)
        primaryStage.setScene(scene);   // привязываем окно программы к нашей сцене
        primaryStage.setWidth(MP3PlayerInterface.WIDTH);
        primaryStage.setHeight(MP3PlayerInterface.HEIGHT);
        primaryStage.setX(Screen.getPrimary().getBounds().getWidth() - primaryStage.getWidth());
        primaryStage.show();            // отображаем окно
    }
}