export interface ReceiptLine {
  readonly productName: string;
  readonly quantity: number;
  readonly unitPrice: number;
  readonly lineTotal: number;
  readonly offerDescription: string | null;
  readonly lineSaved: number;
}

export interface Receipt {
  readonly lines: readonly ReceiptLine[];
  readonly total: number;
  readonly saved: number;
}
