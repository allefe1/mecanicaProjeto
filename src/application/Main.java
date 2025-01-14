package application;

import controller.ClienteController;
import controller.VeiculoController;
import dao.DatabaseSQLite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
	    // Carregar a view de Clientes
	    FXMLLoader loaderCliente = new FXMLLoader(getClass().getResource("/views/ClientesView.fxml"));
	    AnchorPane rootCliente = loaderCliente.load();
	    ClienteController clienteController = loaderCliente.getController();

	    // Carregar a view de Veículos
	    FXMLLoader loaderVeiculo = new FXMLLoader(getClass().getResource("/views/VeiculosView.fxml"));
	    AnchorPane rootVeiculo = loaderVeiculo.load();
	    VeiculoController veiculoController = loaderVeiculo.getController();

	    // Passar o ClienteController para o VeiculoController
	    veiculoController.setClienteController(clienteController);

	    // Layout principal
	    HBox root = new HBox(20, rootCliente, rootVeiculo);

	    // Botão para abrir a tela de Serviços
	    Button openServicoButton = new Button("Abrir Serviços");
	    openServicoButton.setOnAction(this::openServicoWindow);
	    root.getChildren().add(openServicoButton);

	    // minha cena principal, no meu computador ficou bom com esse tamanho
	    Scene scene = new Scene(root, 1920, 1020);

	    
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	    primaryStage.setTitle("Sistema da Oficina");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}


    // Método para abrir a tela de serviços em uma nova janela
	private void openServicoWindow(ActionEvent event) {
	    try {
	        FXMLLoader loaderServico = new FXMLLoader(getClass().getResource("/views/ServicosView.fxml"));
	        AnchorPane rootServico = loaderServico.load(); 
	        Stage servicoStage = new Stage();
	        servicoStage.setTitle("Gerenciamento de Serviços");

	        Scene servicoScene = new Scene(rootServico, 600, 400);
	        
	        
	        servicoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	        servicoStage.setScene(servicoScene);
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
