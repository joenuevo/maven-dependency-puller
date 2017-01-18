package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private TextField txtFieldPathOnDisc;
	private Button btnDownload;
	private TextArea txtAreaDependency;

	@Override
	public void start(Stage primaryStage) {
		VBox root = createGUI();
		try {

			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private VBox createGUI(){
		VBox vbox = new VBox();

		this.txtFieldPathOnDisc = new TextField("path on disc");
		this.txtAreaDependency = new TextArea(
				"<dependency>" +
			    "<groupId>org.glassfish.tyrus.bundles</groupId>" +
			    "<artifactId>tyrus-standalone-client</artifactId>" +
			    "<version>1.13</version>" +
			"</dependency>"

				);
		this.btnDownload = new Button("Download");
		this.btnDownload.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("button clicked");
				String pathOnDisk = txtFieldPathOnDisc.getText();
				String dependency = txtAreaDependency.getText();
				ParsingThread pt = new ParsingThread(pathOnDisk, dependency);
				Thread t = new Thread(pt);
				t.start();

			}
		});

		vbox.getChildren().addAll(txtFieldPathOnDisc, txtAreaDependency, btnDownload);

		return vbox;
	}
}
