package artistDictionary;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.sql.*;
import java.util.ArrayList;

public class ArtistDictionary extends Application {

	public Connection connection;
	private Statement stmnt;
	private ImageView[] painting;
	private ComboBox<String> cboCountry = new ComboBox<>();
	private ComboBox<String> cboMovement = new ComboBox<>();
	public Button btSearch = new Button("Search");
	public TextField tfLastName = new TextField();
	private TextField tfFirstName = new TextField();
	ArrayList<String> arrayListCountry = new ArrayList<String>();
	private TextArea textAreaDescription = new TextArea();
	public String lastName;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initializeDB();
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		pane.add(textAreaDescription, 1, 5);

		pane.add(new Label("Last Name:"), 0, 0);
		pane.add(tfLastName, 1, 0);
		pane.add(new Label("First Name:"), 0, 1);
		pane.add(tfFirstName, 1, 1);
		pane.add(new Label("Country of Origin:"), 0, 2);
		pane.add(cboCountry, 1, 2);
		pane.add(new Label("Art Movement:"), 0, 3);
		pane.add(cboMovement, 1, 3);
		pane.add(btSearch, 1, 4);
		GridPane.setHalignment(btSearch, HPos.CENTER);

		arrayListCountry.add("Norway");
		arrayListCountry.add("England");
		arrayListCountry.add("Germany");
		arrayListCountry.add("Japan");
		arrayListCountry.add("Denmark");
		arrayListCountry.add("Poland");
		arrayListCountry.add("France");
		arrayListCountry.add("Canada");
		arrayListCountry.add("Austrian Empire");
		arrayListCountry.add("Italy");
		arrayListCountry.add("Australia");
		arrayListCountry.add("Austria");
		cboCountry.getItems().addAll(arrayListCountry);

		ArrayList<String> arrayListMovement = new ArrayList<String>();
		arrayListMovement.add("Romanticism");
		arrayListMovement.add("Golden Age of Illustration");
		arrayListMovement.add("German Romanticism");
		arrayListMovement.add("Neosymbolism");
		arrayListMovement.add("Classicism");
		arrayListMovement.add("Symbolism");
		arrayListMovement.add("Art Nouveau");
		arrayListMovement.add("Orientalism");
		arrayListMovement.add("Academicism");
		arrayListMovement.add("Liberty Style");
		arrayListMovement.add("Post-federation Australian");
		cboMovement.getItems().addAll(arrayListMovement);

		btSearch.setOnAction(e -> showResults());
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Artist Dictionary");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void initializeDB1() {
		try {
			// org.mariadb.jdbc.Driver
			// Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("Driver loaded");

			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/example", "root", "sesame");
			System.out.println("Database connected");

			stmnt = connection.createStatement();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void showResults() {
		
		String firstName;
		String query = "select ";
		int lengthQ;
		int endString;
		String correctQuery;
		ResultSet resultSet = null;
		try {
			if (tfLastName.getText() != null) {
				lastName = tfLastName.getText();
				query += ("last_name" + ", ");
				lengthQ = query.length();
				endString = (lengthQ - 2);
				correctQuery = query.substring(0, endString);

				resultSet = stmnt.executeQuery(
						correctQuery + " from artistDictionary where last_name = " + "'" + lastName + "'");

			}
/*
			if (tfFirstName.getText() != null) {
				firstName = tfFirstName.getText();
				query += ("first_name" + ", ");
				lengthQ = query.length();
				endString = (lengthQ - 2);
				correctQuery = query.substring(0, endString);

				if (tfLastName.getText() == null) {
					resultSet = stmnt.executeQuery(
							correctQuery + " from artistDictionary where first_name = " + "'" + firstName + "'");
				}
				
				if (tfLastName.getText() != null) {
					resultSet = stmnt.executeQuery(correctQuery + " from artistDictionary where last_name = " + "'"
							+ lastName + "'" + " and " + "first_name = " + "'" + firstName + "'");
				}

			}
			*/
			

			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
				System.out.printf("%-12s\t", rsMetaData.getColumnName(i));
				System.out.println();
			}
			while (resultSet.next()) {
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
					System.out.printf("%-12s\t", resultSet.getObject(i));
				System.out.println();
			}

		}
		

		catch (SQLException ex) {

		}
	}

	public ArtistDictionary() {
		// TODO Auto-generated constructor stub

	}

	private void initializeDB() {
		try {
			// org.mariadb.jdbc.Driver
			// Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("Driver loaded");

			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/artist", "root", "sesame");
			System.out.println("Database connected");

			stmnt = connection.createStatement();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}

