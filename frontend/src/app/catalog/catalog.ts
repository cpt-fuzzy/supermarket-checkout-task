import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { ProductTile } from './product-tile/product-tile';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-catalog',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [ProductTile],
  templateUrl: './catalog.html',
  styleUrl: './catalog.css',
})
export class Catalog {
  private productService = inject(ProductService);

  readonly products = this.productService.products;
}
