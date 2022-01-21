package app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AppTableMusic extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {			
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("/vista/VistaTableMusic.fxml"));
			Scene scene = new Scene(root,1295,870);
			scene.getStylesheets().add(getClass().getResource("/vista/EstilosTableMusic.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Biblioteca de música");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
