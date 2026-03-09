export interface CartItemOffer {
  readonly requiredQuantity: number;
  readonly offerDescription: string;
}

export interface CartItem {
  readonly productSku: string;
  readonly productName: string;
  readonly unitPrice: number;
  readonly quantity: number;
  readonly offer: CartItemOffer | null;
}
