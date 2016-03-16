import {Component, OnInit} from 'angular2/core';
import {Router} from 'angular2/router';
import {ItemService} from '../service/item.service';
@Component({
    selector: 'checkout',
    templateUrl: 'app/checkout/checkout.component.html'
})
export class CheckoutComponent {
	public items = [];
	public totalPrice = 0;
	public signum = "";

	constructor(private _itemService: ItemService,  private _router: Router) { }

	getCheckoutItems() {
		this.items = this._itemService.getCart();
		// this._itemService.getItems().subscribe(items => {
		// 	this.items = items;
		// });
		this.totalPrice = this._itemService.calculateTotalPrice();
	}
	ngOnInit() {
		this.getCheckoutItems();
	}

	deleteFromCart(index) {
		var self = this;
		this._itemService.deleteFromCart(this.items[index]);
		this.totalPrice = this._itemService.calculateTotalPrice();
		if(this.totalPrice == 0) {
			self._router.navigate(['MenuList']);
		}
	}

	checkout() {
	      var self = this;
	      var socket = io('http://localhost:3700');
	      console.log(socket);
	      	socket.on('connect', function(){
	      		});
	      	socket.on('message', function(data){
	        self.signum=data.name;
	        console.log(self.signum);
	      });
	      socket.on('disconnect', function(){});
		var price = {"signum": this.signum, "price": this.totalPrice};
		this._itemService.postPrice(price);
	}
}
