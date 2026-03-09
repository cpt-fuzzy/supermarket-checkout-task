export interface ProductOffer {
  readonly requiredQuantity: number;
  readonly offerPrice: number;
  readonly description: string;
}

export interface Product {
  readonly sku: string;
  readonly name: string;
  readonly price: number;
  readonly offer: ProductOffer | null;
}
