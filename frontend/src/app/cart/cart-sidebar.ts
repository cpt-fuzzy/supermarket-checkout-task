import { ChangeDetectionStrategy, Component, inject, output, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { CartService } from '../service/cart.service';
import { CheckoutService } from '../service/checkout.service';
import { Receipt } from '../model/receipt.model';

@Component({
  selector: 'app-cart-sidebar',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CurrencyPipe],
  templateUrl: './cart-sidebar.html',
  styleUrl: './cart-sidebar.css',
})
export class CartSidebar {
  private cartService = inject(CartService);
  private checkoutService = inject(CheckoutService);

  readonly items = this.cartService.items;
  readonly itemCount = this.cartService.itemCount;
  readonly subtotal = this.cartService.subtotal;
  readonly isEmpty = this.cartService.isEmpty;

  readonly checkingOut = signal(false);
  readonly checkoutError = signal<string | null>(null);

  readonly checkedOut = output<Receipt>();

  async checkout(): Promise<void> {
    this.checkingOut.set(true);
    this.checkoutError.set(null);

    try {
      const receipt = await this.checkoutService.checkout(this.items());
      this.cartService.clear();
      this.checkedOut.emit(receipt);
    } catch {
      this.checkoutError.set('Checkout failed. Please try again.');
    } finally {
      this.checkingOut.set(false);
    }
  }
}
