package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class ModifUserController {

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


}
