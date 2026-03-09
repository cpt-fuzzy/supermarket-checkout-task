import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CartItem } from '../model/cart.model';
import { Receipt } from '../model/receipt.model';
import { firstValueFrom } from 'rxjs';

interface CheckoutRequest {
  items: { productSku: string; quantity: number }[];
}

@Injectable({ providedIn: 'root' })
export class CheckoutService {
  private http = inject(HttpClient);

  checkout(cartItems: CartItem[]): Promise<Receipt> {
    const body: CheckoutRequest = {
      items: cartItems.map(({ productSku, quantity }) => ({
        productSku,
        quantity,
      })),
    };
    return firstValueFrom(this.http.post<Receipt>('/api/checkout', body));
  }
}
