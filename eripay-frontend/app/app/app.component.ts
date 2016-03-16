import {Component, OnInit} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES, Router, RouteParams} from 'angular2/router';
import {MenuListComponent} from './../menulist/menulist.component';
import {CheckoutComponent} from './../checkout/checkout.component'
import {ItemService} from '../service/item.service';

@Component({
	selector: 'my-app',
	providers: [ItemService],
	templateUrl: './app/app/app.component.html',
	directives: [ROUTER_DIRECTIVES]
})
@RouteConfig([
	{
		path: '/menu',
		name: 'MenuList',
		component: MenuListComponent,
		useAsDefault: true
	},
	{ path: '/checkout', name: 'Checkout', component: CheckoutComponent },
])
export class AppComponent { 
	public cartItemsCount = 0;
	constructor(private _itemService: ItemService, private _router: Router) { }

	getCartItemsCount() {
		return this._itemService.itemCount;
	}

	goToCheckout() {
		this._router.navigate(['Checkout']);
	}
}

