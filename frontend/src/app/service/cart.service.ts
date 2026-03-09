import { computed, effect, Injectable, signal } from '@angular/core';
import { CartItem } from '../model/cart.model';
import { Product } from '../model/product.model';

const STORAGE_KEY = 'cart';

function loadFromStorage(): CartItem[] {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? (JSON.parse(raw) as CartItem[]) : [];
  } catch (e) {
    console.log(e);
    return [];
  }
}

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly _items = signal<CartItem[]>(loadFromStorage());
  readonly items = this._items.asReadonly();

  readonly itemCount = computed(() => this.items().reduce((sum, item) => sum + item.quantity, 0));

  readonly subtotal = computed(() =>
    this.items().reduce((sum, item) => sum + item.unitPrice * item.quantity, 0),
  );

  readonly isEmpty = computed(() => this.items().length === 0);

  constructor() {
    effect(() => {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(this.items()));
    });
  }

  addOne(product: Product): void {
    this._items.update((items) => {
      const existing = items.find((i) => i.productSku === product.sku);
      if (existing) {
        return items.map((i) =>
          i.productSku === product.sku ? { ...i, quantity: i.quantity + 1 } : i,
        );
      }
      return [
        ...items,
        {
          productSku: product.sku,
          productName: product.name,
          unitPrice: product.price,
          quantity: 1,
        },
      ];
    });
  }

  removeOne(productSku: string): void {
    this._items.update((items) => {
      const existing = items.find((i) => i.productSku === productSku);
      if (!existing) return items;
      if (existing.quantity <= 1) {
        return items.filter((i) => i.productSku !== productSku);
      }
      return items.map((i) =>
        i.productSku === productSku ? { ...i, quantity: i.quantity - 1 } : i,
      );
    });
  }

  clear(): void {
    this._items.set([]);
  }
}
