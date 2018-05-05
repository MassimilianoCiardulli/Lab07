package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.bean.PowerOutage;
import it.polito.tdp.poweroutages.db.PowerOutageDAO;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxNERC"
    private ComboBox<String> boxNERC; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="btnWorstCase"
    private Button btnWorstCase; // Value injected by FXMLLoader

    @FXML // fx:id="txtArea"
    private TextArea txtArea; // Value injected by FXMLLoader
    
    private Model model ;


    @FXML
    void handleNERC(ActionEvent event) {

    }

    @FXML
    void handleWorstCase(ActionEvent event) {
    	
    	int years = 0;
    	int h = 0;
    	
    	try {
    		years = Integer.parseInt(this.txtYears.getText());
    		h = Integer.parseInt(this.txtHours.getText());
    	} catch(Exception e) {
    		txtArea.setText("Formato dati non valido.");
    		return ;
    	}
    	
    	String nerc = boxNERC.getValue();
    	Nerc n = null;
    	
    	for(Nerc ntemp:model.getNercList())
    		if(ntemp.getValue().equals(nerc))
    			n = ntemp ;
    	
    	List<PowerOutage> result = model.getListaBlackout(n, years, h);
    	int customers = model.calcolaCustomers(result);
    	txtArea.setText("Tot people affected : " + customers + "\n");
    	int ore = model.calcolaOre(result);
    	txtArea.appendText("Tot hours of outage : " + ore + "\n");

    	for(PowerOutage po:result) {
    		txtArea.appendText(po + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxNERC != null : "fx:id=\"boxNERC\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnWorstCase != null : "fx:id=\"btnWorstCase\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model ;
		
		for(Nerc n:model.getNercList()) {
			boxNERC.getItems().add(n.getValue());
		}
	}
	
}