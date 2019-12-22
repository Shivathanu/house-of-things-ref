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

class CenterBaseLayout {
	
	private TextArea textArea = new TextArea();

	private Button buttonSave = new Button("Save");
		
	
	TextArea getTextArea() {
		
		return textArea;
	}
	
	Button getButtonSave() {
		
		return buttonSave;
	}
	
	
	VBox build() {
		
		
		VBox vBox = new VBox();
        //top, right, bottom, left
        vBox.setPadding(new Insets(10, 0, 10, 0)); 
        vBox.setSpacing(8);

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        
        
        Text title = new Text("Properties");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        //vBox.getChildren().add(title);
        grid.add(title, 0, 0);
        
	    textArea.setEditable(false);
	    textArea.setStyle("-fx-font: 12px \"Arial\";");
	    textArea.setPrefHeight(600);
	    textArea.setPrefWidth(430);///
	    //columnIndex, rowIndex, colspan, rowspan
	    grid.add(textArea, 0, 1, 1, 5);
        //vBox.getChildren().add( textArea );
        	   
        buttonSave.setPrefSize(75, 20);
        buttonSave.setAlignment(Pos.CENTER);
        buttonSave.setTooltip(new Tooltip("Save House"));
        buttonSave.setDisable(true);
        
        grid.add(buttonSave, 1, 4);
        
        //grid.setGridLinesVisible(true);
        
        vBox.getChildren().add( grid );
        
        return vBox;
	}
}
