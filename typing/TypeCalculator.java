package typing;

import java.util.ArrayList;
import java.util.Hashtable;

import typing.Types.Type;

import javafx.application.Application;
import javafx.collections.MapChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TypeCalculator extends Application
{	
	private BorderPane mainBox;
	private TypeChooser atkTypeChooser;
	private TypeChooser defTypeChooser;
	private BorderPane calcPane;
	private Text calcResultLabel;
	private ArrayList<Type> defTypes;
	private Hashtable<Type,Float> defMux;
	private float mod;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Attack Type
		atkTypeChooser = new TypeChooser("Attacking Type", 1, 1);
		atkTypeChooser.getProperties().addListener(new MapChangeListener<Object,Object>()
		{
			@Override
			public void onChanged(Change<? extends Object, ? extends Object> arg0)
			{
				if(arg0.getKey().toString().compareToIgnoreCase("changed") == 0)
				{
					calcMod();
					atkTypeChooser.getProperties().put("Changed", false);
				}
			}
		});
		
		// Defense Types
		defTypeChooser = new TypeChooser("Defensive Type", 1, 2);
		defTypeChooser.getProperties().addListener(new MapChangeListener<Object,Object>()
		{
			@Override
			public void onChanged(Change<? extends Object, ? extends Object> arg0)
			{
				if(arg0.getKey().toString().compareToIgnoreCase("changed") == 0)
				{
					calcMod();
					defTypeChooser.getProperties().put("Changed", false);
				}
			}
		});
		
		// Result
		calcResultLabel = new Text();
		calcResultLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		calcResultLabel.setFill(Color.BLACK);
		calcResultLabel.setTextOrigin(VPos.CENTER);
		
		
		// Calculator Pane
		calcPane = new BorderPane();
		calcPane.setCenter(calcResultLabel);
		calcPane.setPadding(new Insets(10,12,12,12));
		calcPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
		calcMod();
		
		// Main Box
		mainBox = new BorderPane();
		mainBox.setLeft(atkTypeChooser);
		mainBox.setCenter(defTypeChooser);
		mainBox.setBottom(calcPane);
		mainBox.autosize();
		
		// Scene
		Scene scene = new Scene(mainBox);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Type Calculator");
		primaryStage.show();
	}

	private void calcMod()
	{
		Types.getDefMux(defTypeChooser.getSelectedTypes()).get(atkTypeChooser.getSelectedTypes().get(0));
		updateDefTypes();
		updateDefMux();
		
		mod = defMux.get(atkTypeChooser.getSelectedTypes().get(0));
		calcResultLabel.setText(String.format("Modifier:\tx%1.2f", mod));
	}
	
	private void updateDefTypes()
	{
		defTypes = defTypeChooser.getSelectedTypes();
	}

	private void updateDefMux()
	{
		defMux = Types.getDefMux(defTypes);
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
