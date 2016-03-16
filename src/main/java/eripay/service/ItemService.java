package eripay.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import eripay.domain.Item;

@Service
public class ItemService {


  /**
   * hard-coded value for front end- TEMP
   * @return
   */
  public List<Item> getItems() {

    List<Item> items = new ArrayList<Item>();

    Item item1 = new Item("BAGEL");
    item1.setId(1);
    item1.setPrice(2);
    item1.setImgPath("app/img/bagel.jpg");
    items.add(item1);

    Item item2 = new Item("BURGER");
    item2.setId(2);
    item2.setPrice(5);
    item2.setImgPath("app/img/burger.jpg");
    items.add(item2);

    Item item3 = new Item("FAJITAS");
    item3.setId(3);
    item3.setPrice(6);
    item3.setImgPath("app/img/fajitas.jpg");
    items.add(item3);

    Item item4 = new Item("PIZZA");
    item4.setId(4);
    item4.setPrice(3);
    item4.setImgPath("app/img/pizza.jpg");
    items.add(item4);

    Item item5 = new Item("TOPPING");
    item5.setId(5);
    item5.setPrice(1);
    item5.setImgPath("app/img/tomates.jpg");
    items.add(item5);

    Item item6 = new Item("VALUE MEAL");
    item6.setId(6);
    item6.setPrice(8);
    item6.setImgPath("app/img/value_meal.jpg");
    items.add(item6);

    Item item7 = new Item("BREAKFAST SPECIAL");
    item7.setId(7);
    item7.setPrice(6);
    item7.setImgPath("app/img/breakfast.jpg");
    items.add(item7);

    Item item8 = new Item("DRINK");
    item8.setId(8);
    item8.setPrice(2);
    item8.setImgPath("app/img/drink.jpg");
    items.add(item8);

    return items;

  }

}
