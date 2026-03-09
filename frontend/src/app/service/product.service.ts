import { Injectable } from '@angular/core';
import { httpResource } from '@angular/common/http';
import { Product } from '../model/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  readonly products = httpResource<Product[]>(() => '/api/products', {
    defaultValue: [],
  });
}
