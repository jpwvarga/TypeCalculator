package typing;

import javafx.geometry.VPos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import typing.Types.Type;

public class TypeButton extends StackPane
{
	private final Type TYPE;
	private boolean isOn;
	
	private Rectangle background;
	private Rectangle selectionBorder;
	private Text text;
	private Font font = Font.font("Verdana", FontWeight.BOLD, 20);
	
	private final double WIDTH = 100;
	private final double HEIGHT = 25;
	private final double STROKE = 5;
	
	public TypeButton(Type forType)
	{
		TYPE = forType;
		isOn = false;
		
		background = new Rectangle(WIDTH, HEIGHT);
		background.setFill(TYPE.getColor());
		background.setStroke(TYPE.getColor());
		background.setStrokeWidth(STROKE);
		background.setStrokeType(StrokeType.OUTSIDE);
		background.setStrokeLineCap(StrokeLineCap.ROUND);
		background.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		selectionBorder = new Rectangle(WIDTH, HEIGHT);
		selectionBorder.setFill(Color.TRANSPARENT);
		selectionBorder.setStroke(Color.TRANSPARENT);
		selectionBorder.setStrokeWidth(STROKE);
		selectionBorder.setStrokeType(StrokeType.OUTSIDE);
		selectionBorder.setStrokeLineCap(StrokeLineCap.ROUND);
		selectionBorder.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		text = new Text();
		text.setText(TYPE.toString());
		text.setFont(font);
		text.setFill(Color.WHITE);
		text.setTextOrigin(VPos.CENTER);
	
		getChildren().addAll(background, selectionBorder, text);
		autosize();
		
		getProperties().put("isOn", isOn);
		
		setOnMouseClicked(event -> toggle());
	}

	public TypeButton()
	{
		this(Type.NIL);
	}
	
	public boolean isOn()
	{
		return isOn;
	}
	
	public boolean toggle() // returns the new value of isOn
	{
		return toggle(!isOn);
	}
	
	public boolean toggle(boolean bool) // returns the new value of isOn
	{
		isOn = bool;
		getProperties().put("isOn", isOn);
		
		if (isOn)
			selectionBorder.setStroke(TYPE.getColor().invert().desaturate());
		else
			selectionBorder.setStroke(Color.TRANSPARENT);
		
		return isOn;
	}
	
	public Type getType()
	{
		return TYPE;
	}
}
