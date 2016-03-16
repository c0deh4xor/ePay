import {Component, OnInit} from 'angular2/core';
import {ItemService} from '../service/item.service';
import {Item} from '../service/item.interface.ts';

@Component({
    selector: 'my-app',
    templateUrl: 'app/menulist/menu.component.html'
})
export class MenuListComponent { 
	public cart = [];
	private itemCount = this._itemService.itemCount;
	public items: Item[];


	constructor(private _itemService: ItemService) { }

	getItems() {
		this._itemService.getItems().subscribe(items => {
			this.items = items;
		});
	}
	ngOnInit() {
		this.getItems();
	}

	getItemCount() {
		return this._itemService.itemCount;
	}

	onAddToCart(index) {
		this._itemService.addToCart(this.items[index]);
	}
}
