import Database.DatabaseConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author migue
 */
public class MainVacapp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Views/LoginUI.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void init() throws Exception {
        super.init();
        if(!DatabaseConnection.getInstance().openConnection()) {
            Platform.exit();
        }
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        DatabaseConnection.getInstance().closeConnection();
    }


    public static void main(String[] args) {
//        LocalDate date = LocalDate.parse("2017-02-03");
//        System.out.println(date);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
//        date = formatter.format(date);
//        System.out.println(formatter.format(date));

        launch(args);
    }

    
}
