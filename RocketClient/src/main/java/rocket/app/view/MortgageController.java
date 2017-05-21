package rocket.app.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import eNums.eAction;
import exceptions.RateException;
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
import rocketBase.RateBLL;
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
	private TextField txtDownPayment;

	@FXML
	private TextField txtHouseCost;

	@FXML
	private ComboBox<Integer> cmbTerm;

	@FXML
	private Label lblMortgagePayment;
	
	private MainApp mainApp;
	
	private ObservableList<Integer> years = FXCollections.observableArrayList(15, 30);

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void initialize()
	{
		cmbTerm.setItems(years);
	}
	
	@FXML
	public void btnCalculatePayment(ActionEvent event) throws RateException
	{
		
		LoanRequest lq = new LoanRequest();
		lq.setIncome(Double.parseDouble((txtIncome.getText())));
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		lq.setExpenses(Double.parseDouble(txtExpenses.getText()));
		lq.setiTerm(Integer.parseInt(cmbTerm.getPromptText()));
		lq.setdRate(RateBLL.getRate(Integer.parseInt(txtCreditScore.getText())));
		
		mainApp.messageSend(lq);
		
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		
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
