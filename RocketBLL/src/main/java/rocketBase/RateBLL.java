package rocketBase;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException 
	{
		double dInterestRate = 0;
		
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		
		OptionalDouble r = rates.stream()
				.filter(rate -> rate.getiMinCreditScore() <= GivenCreditScore)
				.mapToDouble(RateDomainModel::getdInterestRate).min();
		
		if (r.isPresent()){
			dInterestRate = r.getAsDouble();
		}
		
		else{
			throw new RateException(rates.get(0));
		}
		
		
		return dInterestRate;
		
		
	}
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return FinanceLib.pmt(r/100/12, n*12, p, -f, t);
	}

}
