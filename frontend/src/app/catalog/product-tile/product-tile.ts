import { CurrencyPipe, NgOptimizedImage } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, inject, input } from '@angular/core';
import { Product } from '../../model/product.model';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-product-tile',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CurrencyPipe, NgOptimizedImage],
  host: {
    class: 'product-tile',
  },
  templateUrl: './product-tile.html',
  styleUrl: './product-tile.css',
})
export class ProductTile {
  private cartService = inject(CartService);

  product = input.required<Product>();

  quantity = computed(() => {
    const sku = this.product().sku;
    return this.cartService.items().find((i) => i.productSku === sku)?.quantity ?? 0;
  });

  addToCart(): void {
    this.cartService.addOne(this.product());
  }

  removeFromCart(): void {
    this.cartService.removeOne(this.product().sku);
  }
}
