package eripay.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

import eripay.util.GenerateAccessToken;
import eripay.util.ResultPrinter;


/**
 * @author lvairamani
 *
 */
public class PaymentWithPayPalServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger
      .getLogger(PaymentWithPayPalServlet.class);
  Map<String, String> map = new HashMap<String, String>();

  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
    // ##Load Configuration
    // Load SDK configuration for
    // the resource. This intialization code can be
    // done as Init Servlet.
    InputStream is = PaymentWithPayPalServlet.class
        .getResourceAsStream("/sdk_config.properties");
    try {
      PayPalResource.initConfig(is);
    } catch (PayPalRESTException e) {
      LOGGER.fatal(e.getMessage());
    }

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  // ##Create
  // Sample showing to create a Payment using PayPal
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    createPayment(req, resp);
    req.getRequestDispatcher("response.jsp").forward(req, resp);
  }

  public Payment createPayment(HttpServletRequest req, HttpServletResponse resp) {
    Payment createdPayment = null;
    // ###AccessToken
    // Retrieve the access token from
    // OAuthTokenCredential by passing in
    // ClientID and ClientSecret
    APIContext apiContext = null;
    String accessToken = null;
    try {
      accessToken = GenerateAccessToken.getAccessToken();

      // ### Api Context
      // Pass in a `ApiContext` object to authenticate
      // the call and to send a unique request id
      // (that ensures idempotency). The SDK generates
      // a request id if you do not pass one explicitly.
      apiContext = new APIContext(accessToken);
      // Use this variant if you want to pass in a request id
      // that is meaningful in your application, ideally
      // a order id.
      /*
       * String requestId = Long.toString(System.nanoTime(); APIContext
       * apiContext = new APIContext(accessToken, requestId ));
       */
    } catch (PayPalRESTException e) {
      req.setAttribute("error", e.getMessage());
    }
    if (req.getParameter("PayerID") != null) {
      Payment payment = new Payment();
      if (req.getParameter("guid") != null) {
        payment.setId(map.get(req.getParameter("guid")));
      }

      PaymentExecution paymentExecution = new PaymentExecution();
      paymentExecution.setPayerId(req.getParameter("PayerID"));
      try {
        createdPayment = payment.execute(apiContext, paymentExecution);
        ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), Payment.getLastResponse(), null);
      } catch (PayPalRESTException e) {
        ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), null, e.getMessage());
      }
    } else {

      // ###Details
      // Let's you specify details of a payment amount.
      Details details = new Details();
      details.setShipping("1");
      details.setSubtotal("5");
      details.setTax("1");

      // ###Amount
      // Let's you specify a payment amount.
      Amount amount = new Amount();
      amount.setCurrency("USD");
      // Total must be equal to sum of shipping, tax and subtotal.
      amount.setTotal("7");
      amount.setDetails(details);

      // ###Transaction
      // A transaction defines the contract of a
      // payment - what is the payment for and who
      // is fulfilling it. Transaction is created with
      // a `Payee` and `Amount` types
      Transaction transaction = new Transaction();
      transaction.setAmount(amount);
      transaction
          .setDescription("This is the payment transaction description.");

      // ### Items
      Item item = new Item();
      item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
      ItemList itemList = new ItemList();
      List<Item> items = new ArrayList<Item>();
      items.add(item);
      itemList.setItems(items);

      transaction.setItemList(itemList);


      // The Payment creation API requires a list of
      // Transaction; add the created `Transaction`
      // to a List
      List<Transaction> transactions = new ArrayList<Transaction>();
      transactions.add(transaction);

      // ###Payer
      // A resource representing a Payer that funds a payment
      // Payment Method
      // as 'paypal'
      Payer payer = new Payer();
      payer.setPaymentMethod("paypal");

      // ###Payment
      // A Payment Resource; create one using
      // the above types and intent as 'sale'
      Payment payment = new Payment();
      payment.setIntent("sale");
      payment.setPayer(payer);
      payment.setTransactions(transactions);

      // ###Redirect URLs
      RedirectUrls redirectUrls = new RedirectUrls();
      String guid = UUID.randomUUID().toString().replaceAll("-", "");
      redirectUrls.setCancelUrl(req.getScheme() + "://"
          + req.getServerName() + ":" + req.getServerPort()
          + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
      redirectUrls.setReturnUrl(req.getScheme() + "://"
          + req.getServerName() + ":" + req.getServerPort()
          + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
      payment.setRedirectUrls(redirectUrls);


      // Create a payment by posting to the APIService
      // using a valid AccessToken
      // The return object contains the status;
      try {
        createdPayment = payment.create(apiContext);
        LOGGER.info("Created payment with id = "
            + createdPayment.getId() + " and status = "
            + createdPayment.getState());
        // ###Payment Approval Url
        Iterator<Links> links = createdPayment.getLinks().iterator();
        while (links.hasNext()) {
          Links link = links.next();
          if (link.getRel().equalsIgnoreCase("approval_url")) {
            req.setAttribute("redirectURL", link.getHref());
          }
        }
        ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), Payment.getLastResponse(), null);
        map.put(guid, createdPayment.getId());
      } catch (PayPalRESTException e) {
        ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), null, e.getMessage());
      }
    }
    return createdPayment;
  }
}