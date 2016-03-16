package eripay.domain;

public class Purchase {

  private String price;
  private String signum;

  public String getSignum() {
    return signum;
  }

  public void setSignum(String signum) {
    this.signum = signum;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

}
