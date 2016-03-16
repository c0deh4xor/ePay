package eripay.domain;

import org.springframework.data.annotation.Id;

public class Item {

  @Id
  private Integer id;
  private String name;
  private double price;
  private String imgPath;

  public Item(String name) {
    super();
    this.name = name;
  }

  public Item() {
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
