package eripay.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.paypal.api.payments.CreditCard;

public class User {

  @Id
  private String id;
  @Indexed(unique = true)
  private String signum;
  private CreditCard creditCard;

  public User() {
  }

  public User(String id) {
    this.id = id;
  }

  public void setSignum(String signum) {
    this.signum = signum;
  }

  public String getSignum() {
    return signum;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

}
