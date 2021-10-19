package typing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Tester extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		TypeChooser tc = new TypeChooser("Test Chooser", 1, 4);
		
		Scene scene = new Scene(tc);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test");
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}