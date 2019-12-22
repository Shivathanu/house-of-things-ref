package pt.feup.ads.gui.layout;

import javafx.geometry.Insets;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pt.feup.ads.util.Identifiable;

class LeftBaseLayout {
	
	private TreeView<Identifiable> treeView = new  TreeView<Identifiable>();
	
	
	TreeView<Identifiable> getTreeView() {
		
		return treeView;
		
	}
	
	VBox build() {
		
		VBox vBox = new VBox();
        vBox.setPadding(new Insets(10)); // Set all sides to 10
        vBox.setSpacing(8);              // Gap between nodes

        Text title = new Text("Devices");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vBox.getChildren().add(title);
        
        treeView.setPrefHeight(600);
        treeView.setPrefWidth(250);
        
        treeView.setStyle("-fx-font: 12px \"Arial\";");
        
        
        vBox.getChildren().add( treeView );
        
        return vBox;

	}
}
