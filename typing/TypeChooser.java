package typing;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import typing.Types.Type;;

public class TypeChooser extends BorderPane
{
	public static final boolean ON = true;
	public static final boolean OFF = false;
	
	private int MIN;
	private int MAX;
	
	private final ArrayList<Type> selectedTypes;
	private final ArrayList<TypeButton> buttons;
	
	public TypeChooser(String name, int minAllowed, int maxAllowed)
	{	
		getProperties().put("Changed", false);
		
		MIN = minAllowed;
		MAX = maxAllowed;
		
		selectedTypes = new ArrayList<Type>();
		buttons = new ArrayList<TypeButton>();
		
		GridPane gp = new GridPane();
		gp.setHgap(15);
		gp.setVgap(15);
		setPadding(new Insets(10,12,12,12));
		
		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 6; r++)
			{
				Type t = Type.valueOfIndex(3 * r + c);
				TypeButton btn = new TypeButton(t);
				
				btn.setOnMouseClicked(event->
				{
					int numSelected = selectedTypes.size();
					boolean isOn = btn.isOn();
					
					if (!isOn)
					{
						if (numSelected + 1 > MAX)
							getButtonOfType(selectedTypes.remove(0)).toggle(OFF); // Turn first button off and remove its type from the list
						
						// Turn this button on and add to the list
						selectedTypes.add(btn.getType());
						btn.toggle(ON);
						
						getProperties().put("Changed", true);
					}
					else
					{
						if (numSelected - 1 >= MIN)
						{
							selectedTypes.remove(btn.getType());
							btn.toggle(OFF);
							getProperties().put("Changed", true);
						}
					}
				});
				
				if (selectedTypes.size() < MIN)
				{
					btn.toggle(ON);
					selectedTypes.add(btn.getType());
				}
				
				buttons.add(btn);
				gp.add(btn, c, r);
			}
		
		Text title = new Text(name);
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		title.setFill(Color.BLACK);
		title.setTextOrigin(VPos.CENTER);
		
		StackPane titlePane = new StackPane(new Rectangle(gp.getWidth(), 25.0), title);
		titlePane.setPadding(new Insets(10,12,12,12));
		
		setTop(titlePane);
		setCenter(gp);
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
	}
	
	public ArrayList<Type> getSelectedTypes()
	{
		return selectedTypes;
	}
	
	public TypeButton getButtonOfType(Type t)
	{
		for (TypeButton btn: buttons)
			if (btn.getType() == t)
				return btn;
		
		return null;
	}
	
	public void printSelectedTypes()
	{
		for (Type t: selectedTypes)
			System.out.println(t.toString());
	}
	
	public void setMax(int i)
	{
		MAX = i;
		
		while (selectedTypes.size() > MAX)
			getButtonOfType(selectedTypes.remove(0)).toggle(OFF); 
	}
}
