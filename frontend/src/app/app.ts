import { ChangeDetectionStrategy, Component, viewChild } from '@angular/core';
import { Catalog } from './catalog/catalog';
import { CartSidebar } from './cart/cart-sidebar';
import { ReceiptModal } from './receipt/receipt-modal';
import { Receipt } from './model/receipt.model';

@Component({
  selector: 'app-root',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [Catalog, CartSidebar, ReceiptModal],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private receiptModal = viewChild.required(ReceiptModal);

  onCheckedOut(receipt: Receipt): void {
    this.receiptModal().open(receipt);
  }
}
