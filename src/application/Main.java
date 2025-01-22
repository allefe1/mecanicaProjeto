package application;

import controller.ClienteController;
import controller.VeiculoController;
import dao.DatabaseSQLite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loaderCliente = new FXMLLoader(getClass().getResource("/views/ClientesView.fxml"));
        AnchorPane rootCliente = loaderCliente.load();
        ClienteController clienteController = loaderCliente.getController();

        
        FXMLLoader loaderVeiculo = new FXMLLoader(getClass().getResource("/views/VeiculosView.fxml"));
        AnchorPane rootVeiculo = loaderVeiculo.load();
        VeiculoController veiculoController = loaderVeiculo.getController();

        // Passar o ClienteController para o VeiculoController
        veiculoController.setClienteController(clienteController);

       
        HBox topBar = createTopBar();

       
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(new HBox(20, rootCliente, rootVeiculo));

        // Cena principal
        Scene scene = new Scene(root, 1920, 1020);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setTitle("Sistema da Oficina");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Cria uma barra superior com o logo e outros elementos
     */
    private HBox createTopBar() {
      
        Image logoImage = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(100); 
        logoView.setFitHeight(100); 
        logoView.setPreserveRatio(true);

        
        Button openServicoButton = new Button("Abrir Serviços");
        openServicoButton.setOnAction(this::openServicoWindow);

        
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

       
        HBox topBar = new HBox(20, logoView, spacer, openServicoButton);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #3a414d; -fx-alignment: center-left;");

        return topBar;
    }


    /**
     * Método para abrir a tela de serviços em uma nova janela
     */
    private void openServicoWindow(ActionEvent event) {
        try {
            FXMLLoader loaderServico = new FXMLLoader(getClass().getResource("/views/ServicosView.fxml"));
            AnchorPane rootServico = loaderServico.load();
            Stage servicoStage = new Stage();
            servicoStage.setTitle("Gerenciamento de Serviços");

            Scene servicoScene = new Scene(rootServico, 600, 400);
            servicoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            servicoStage.setScene(servicoScene);

            
            servicoStage.setX(1170); 
            servicoStage.setY(100);   

            servicoStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseSQLite.createTables();
        launch(args);
    }
}
