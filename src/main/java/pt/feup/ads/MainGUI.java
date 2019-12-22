package pt.feup.ads;

import java.io.OutputStream;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.feup.ads.gui.layout.BaseLayout;


public class MainGUI extends Application {

	
	private BaseLayout baseLayout = new BaseLayout();
	 
	public static void main(String[] args) {
		
		launch(args);
	}
	

	@Override
	public void start(Stage stage) {    
        
        stage.setTitle("House of Things");
        
        baseLayout.setStage(stage);
        
        final Scene scene = this.createBorderPane();
        
        scene.setFill(Color.LIGHTGRAY);
        stage.setScene(scene);
        
        stage.setWidth(1280);
        stage.setHeight(800);
        
	    stage.show();
	    
	    TextArea taLog = baseLayout.getLogTextArea();
	    
	    taLog.setWrapText(true);
	    
        Console console = new Console(taLog);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
         
    }
	
	
	Scene createBorderPane() {
		
		// Use a border pane as the root for scene
	    BorderPane border = new BorderPane();
	    
	    //TOP
	    HBox hBoxTOP = baseLayout.buildTOP();
	    border.setTop(hBoxTOP);
	    
	    //LEFT
	    VBox vBoxLEFT = baseLayout.buidLEFT();
	    border.setLeft( vBoxLEFT );
	    
	    //CENTER
	    VBox vBoxCENTER = baseLayout.buidCENTER();
	    border.setCenter( vBoxCENTER );
	    
	    //RIGHT
	    VBox vBoxRIGHT = baseLayout.buidRIGHT();
	    border.setRight( vBoxRIGHT );
	    
	    //BOTTOM
	    VBox vBoxBOTTOM = baseLayout.buidBOTTOM();
	    border.setBottom( vBoxBOTTOM );

	    return new Scene(border);
	}
	
	
	public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) {
        	
            output.appendText(String.valueOf((char) i));
        }
    }
}
