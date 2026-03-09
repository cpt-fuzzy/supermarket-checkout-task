import { ChangeDetectionStrategy, Component, ElementRef, signal, viewChild } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { Receipt } from '../model/receipt.model';

@Component({
  selector: 'app-receipt-modal',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CurrencyPipe],
  templateUrl: './receipt-modal.html',
  styleUrl: './receipt-modal.css',
})
export class ReceiptModal {
  private popoverRef = viewChild.required<ElementRef<HTMLElement>>('receiptPopover');

  readonly receipt = signal<Receipt | null>(null);

  open(receipt: Receipt): void {
    this.receipt.set(receipt);
    this.popoverRef().nativeElement.showPopover();
  }
}
