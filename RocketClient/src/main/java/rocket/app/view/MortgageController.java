package rocket.app.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import eNums.eAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import rocket.app.MainApp;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {
	
	@FXML
	private TextField txtIncome;

	@FXML
	private TextField txtExpenses;

	@FXML
	private TextField txtCreditScore;

	@FXML
	private TextField txtHouseCost;

	@FXML
	private ComboBox<Integer> cmbTerm;

	@FXML
	private Label lblMortgagePayment;
	
	private TextField txtNew;
	
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message Here...");
		alert.setHeaderText("Look, an Information Dialog");
		alert.setContentText(txtNew.getText());
		alert.showAndWait().ifPresent(rs -> {
		    if (rs == ButtonType.OK) {
		        System.out.println("Pressed OK.");
		    }
		});
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		
		NumberFormat formatter = new DecimalFormat("#0.00");
		
		double PITI = Double.parseDouble(formatter.format((String.valueOf((lRequest.getIncome()- lRequest.getExpenses()) * 0.28 ))));
		double pmt = lRequest.getdPayment();
		int result = (int)(pmt - PITI) * 100;
		if(result > 0) {
			lblMortgagePayment.setText("House Cost too high");
		} else {
			lblMortgagePayment.setText("You can afford the house");
		}
		
	}
}
