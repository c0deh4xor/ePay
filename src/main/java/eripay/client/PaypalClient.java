package eripay.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import eripay.domain.User;

@Component
public class PaypalClient {

  private static final String API_URL = "https://api.sandbox.paypal.com/";
  private static final String CLIENT_ID = "AWncMFIas1Q3UcXUPdqd-JOJue1930v85sU9vbl1a9T3zeaFfqW6ZdIdzWu-qTv9mod1P7HH8HPAH1y0";
  private static final String CLIENT_SECRET = "ENIjWJj8Yj8rl6WC0bzner64EJqBb1yS7kdJS3TT3rI8ySGbTDujdjwB7mMaNdIvD0VNCRb48ZK9xmaQ";

  private RestTemplate restTemplate = new RestTemplate();

  public String makePayment(User user, String price) throws PayPalRESTException {
    Map<String, String> map = new HashMap<String, String>();
    map.put("mode", "sandbox");
    String accessToken = new OAuthTokenCredential(CLIENT_ID, CLIENT_SECRET, map).getAccessToken();
    Map<String, String> sdkConfig = new HashMap<String, String>();
    sdkConfig.put("mode", "sandbox");

    APIContext apiContext = new APIContext(accessToken);
    apiContext.setConfigurationMap(sdkConfig);

    FundingInstrument fundingInstrument = new FundingInstrument();
    fundingInstrument.setCreditCard(user.getCreditCard());

    List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
    fundingInstrumentList.add(fundingInstrument);

    Payer payer = new Payer();
    payer.setFundingInstruments(fundingInstrumentList);
    payer.setPaymentMethod("credit_card");

    Amount amount = new Amount();
    amount.setCurrency("CAD");
    amount.setTotal(price);

    Transaction transaction = new Transaction();
    transaction.setDescription("creating a direct payment with credit card");
    transaction.setAmount(amount);

    List<Transaction> transactions = new ArrayList<Transaction>();
    transactions.add(transaction);

    Payment payment = new Payment();
    payment.setIntent("sale");
    payment.setPayer(payer);
    payment.setTransactions(transactions);

    Payment createdPayment = payment.create(apiContext);
    return createdPayment.getState();
  }

}
