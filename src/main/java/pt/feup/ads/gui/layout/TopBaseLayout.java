package pt.feup.ads.gui.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class TopBaseLayout {
	
	private Button buttonOpenProject = new Button("Open Project");
	
	private FileChooser fileChooser = new FileChooser();
	
	
	Button getButtonOpenProject() {
		
		return buttonOpenProject;
	}
	
	
	FileChooser getFileChooser() {
		
		return fileChooser;
	}
	
	HBox build(Stage stage) {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(30);
        hbox.setStyle("-fx-background-color: #336699;");

        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        
        buttonOpenProject.setPrefWidth(100);
        buttonOpenProject.setPrefHeight(30);
        buttonOpenProject.setAlignment(Pos.CENTER);
        buttonOpenProject.setTooltip(new Tooltip("Open a Project JSON File"));
        
       
        
        hbox.getChildren().add(buttonOpenProject);
        
        Text title = new Text("Software Architecture and Design\n" + 
        		"Master in Software Engineering (FEUP)");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        
        HBox hboxStudents = new HBox();
        hboxStudents.setPadding(new Insets(0, 5, 0, 150));
        hboxStudents.setSpacing(40);
        hboxStudents.setAlignment(Pos.CENTER_RIGHT);
        
        Text student1 = new Text("Rafael Lima Joia\n" + 
        		"up201902822@fe.up.pt");
        student1.setFill(Color.WHITE);
        student1.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        
        Text student2 = new Text("Daniel Lima Fernandes Vieira\n" + 
        		"up201900045@fe.up.pt");
        student2.setFill(Color.WHITE);
        student2.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        
        Text student3 = new Text("Bruno Viana do Nascimento\n" + 
        		"up201800049@fe.up.pt");
        student3.setFill(Color.WHITE);
        student3.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        
        Text student4 = new Text("Jose Alejandro Briones Romero\n" + 
        		"up201902669@fe.up.pt");
        student4.setFill(Color.WHITE);
        student4.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        
        hboxStudents.getChildren().addAll(student1, student2, student3, student4);
        hbox.getChildren().addAll(title, hboxStudents);
        
        return hbox;
	}

}
