package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewUserController {

	@FXML private CheckBox chkPresupuesto, chkPedidos, chkAlbaranes, chkFacturas, chkClientes, chkProveedores, chkDiario, chkResumen, chkOtros;
	@FXML private VBox vboxPresupuesto, vboxPedidos, vboxAlbaranes, vboxFacturas, vboxClientes, vboxProveedores, vboxDiario, vboxResumen, vboxOtros;

	@FXML
	private void toggleCheckboxes() {
	    vboxPresupuesto.setVisible(chkPresupuesto.isSelected());
	    vboxPresupuesto.setManaged(chkPresupuesto.isSelected());

	    vboxPedidos.setVisible(chkPedidos.isSelected());
	    vboxPedidos.setManaged(chkPedidos.isSelected());

	    vboxAlbaranes.setVisible(chkAlbaranes.isSelected());
	    vboxAlbaranes.setManaged(chkAlbaranes.isSelected());

	    vboxFacturas.setVisible(chkFacturas.isSelected());
	    vboxFacturas.setManaged(chkFacturas.isSelected());

	    vboxClientes.setVisible(chkClientes.isSelected());
	    vboxClientes.setManaged(chkClientes.isSelected());

	    vboxProveedores.setVisible(chkProveedores.isSelected());
	    vboxProveedores.setManaged(chkProveedores.isSelected());

	    vboxDiario.setVisible(chkDiario.isSelected());
	    vboxDiario.setManaged(chkDiario.isSelected());

	    vboxResumen.setVisible(chkResumen.isSelected());
	    vboxResumen.setManaged(chkResumen.isSelected());
	    
	    vboxOtros.setVisible(chkOtros.isSelected());
	    vboxOtros.setManaged(chkOtros.isSelected());
	}

	
	@FXML
    private void abrirModifUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ModifUsers.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Modificar Usuarios");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ModifUserCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@FXML
    private void abrirConsultarUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ConsultarUsers.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = new Stage();
            stage.setTitle("Consultar Usuarios");
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(true);

            scene.getStylesheets().add(getClass().getResource("/Styles/ConsultarUsersCSS.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
