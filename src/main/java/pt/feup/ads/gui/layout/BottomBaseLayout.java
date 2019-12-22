package pt.feup.ads.gui.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class BottomBaseLayout {
	
	private TextArea textArea = new TextArea();
	
	
	TextArea getTextArea() {
		
		return textArea;
	}
	
	
	VBox build() {
		
		VBox vBox = new VBox();
        vBox.setPadding(new Insets(10)); 
        vBox.setSpacing(8);              
    	
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        
    	Text logsText = new Text("Logs");
    	logsText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    	
    	grid.add(logsText, 0, 0);
    	
	    textArea.setEditable(false);    
	    textArea.setPrefHeight(200);
	    textArea.setPrefWidth(1180);
	    
	    //columnIndex, rowIndex, colspan, rowspan
	    grid.add(textArea, 0, 1, 1, 10);
	    
	    
	    Button buttonClear = new Button("Clear logs");
	    buttonClear.setPrefSize(90, 20);
	    buttonClear.setAlignment(Pos.CENTER);
	    buttonClear.setTooltip(new Tooltip("Clear logs"));
	    buttonClear.setOnAction(
    			value -> textArea.setText("") );
	    
	    
        grid.add(buttonClear, 1, 4);
	    
	    //vBox.getChildren().addAll(logsText, textArea);
	    
        //grid.setGridLinesVisible(true);
        
	    vBox.getChildren().add( grid );
	    
        return vBox;
	}

}
