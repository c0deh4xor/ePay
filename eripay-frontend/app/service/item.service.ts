import {Injectable}     from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {Item} from './item.interface.ts';
import {Price} from './price.interface.ts';
import {Observable}     from 'rxjs/Observable';
import {Router} from 'angular2/router';

@Injectable()
export class ItemService {
  public cart = [];
  public itemCount = 0;
  public totalPrice = 0;
  constructor (private http: Http,  private _router: Router) {}

  private _itemsUrl = 'http://localhost:8080/items';
  private _priceUrl = 'http://localhost:8080/price';

  getItems () {
    return this.http.get(this._itemsUrl)
                    .map(res => <Item[]> res.json())
                    .catch(this.handleError);
  }

  postPrice (price) {
    console.log(price);
    let body = JSON.stringify(price);
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    var self = this;
    this.http.post(self._priceUrl, body, {
      headers: headers
      })
      .subscribe(
        data => {self._router.navigate(['MenuList']);
        },
        err => this.handleError,
        () => alert('Transaction Successful')
      );
  }

  private handleError (error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }

  private calculateTotalCartItems() {
    return this.cart.reduce(function(a, b) {
      return a + b['count'];
    }, 0);
  };

  public addToCart(item) {
    //If item is already in cart, it just updates the count
    //and recalculates the total item count and return
    var self = this;
    var updatedExistingItem;
    self.cart.forEach(function(cartItem) {
      if (cartItem.id === item.id) {
        cartItem.count = cartItem.count + 1;
        self.itemCount = self.calculateTotalCartItems();
        updatedExistingItem = true;
      }
    });
    //else if item wasnt found in the cart, it add it with a count of 1
    //and recalculates the total item count
    if (!updatedExistingItem) {
      self.cart.push(Object.assign(item, { count: 1 }));
      self.itemCount = self.calculateTotalCartItems();
    }
  }

  public deleteFromCart(item) {
    var self = this;
    self.cart.forEach(function(cartItem) {
      if (cartItem.id === item.id) {
        var index = self.cart.indexOf(cartItem, 0);
        if (index > -1) {
            self.cart.splice(index, 1);
        }
      }
    });
  };

  public calculateTotalPrice(){
    this.totalPrice = 0;
    for(let item in this.cart) {
        this.totalPrice = this.totalPrice + (this.cart[item].price * this.cart[item].count);
    }
    this.itemCount = this.calculateTotalCartItems();
    return this.totalPrice;
  }

  public getCart() {
    return this.cart;
  };
}