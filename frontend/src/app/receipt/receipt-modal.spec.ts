import { Receipt } from '../model/receipt.model';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReceiptModal } from './receipt-modal';

const receiptWithSavings: Receipt = {
  lines: [
    {
      productName: 'Apple',
      quantity: 4,
      unitPrice: 0.3,
      lineTotal: 0.9,
      offerDescription: '2 for 0.45',
      lineSaved: 0.3,
    },
    {
      productName: 'Banana',
      quantity: 1,
      unitPrice: 0.5,
      lineTotal: 0.5,
      offerDescription: null,
      lineSaved: 0,
    },
  ],
  total: 1.4,
  saved: 0.3,
};

const receiptNoSavings: Receipt = {
  lines: [
    {
      productName: 'Orange',
      quantity: 2,
      unitPrice: 0.6,
      lineTotal: 1.2,
      offerDescription: null,
      lineSaved: 0,
    },
  ],
  total: 1.2,
  saved: 0,
};

describe('ReceiptModal', () => {
  let fixture: ComponentFixture<ReceiptModal>;
  let component: ReceiptModal;
  let el: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReceiptModal],
    }).compileComponents();

    fixture = TestBed.createComponent(ReceiptModal);
    component = fixture.componentInstance;
    el = fixture.nativeElement;
  });

  it('should render receipt lines with name, quantity, unit price and line total', () => {
    component.receipt.set(receiptWithSavings);
    fixture.detectChanges();

    const rows = el.querySelectorAll('.receipt-table tbody tr:not(.savings-row)');
    expect(rows).toHaveLength(2);

    const firstCells = rows[0].querySelectorAll('td');
    expect(firstCells[0].textContent).toContain('Apple');
    expect(firstCells[1].textContent).toContain('4');
    expect(firstCells[2].textContent).toContain('0.30');
    expect(firstCells[3].textContent).toContain('0.90');
  });

  it('should show offer description when a line has one', () => {
    component.receipt.set(receiptWithSavings);
    fixture.detectChanges();

    const offerSpans = el.querySelectorAll('.receipt-offer');
    expect(offerSpans).toHaveLength(1);
    expect(offerSpans[0].textContent).toContain('2 for 0.45');
  });

  it('should show savings row when a line has savings', () => {
    component.receipt.set(receiptWithSavings);
    fixture.detectChanges();

    const savingsRows = el.querySelectorAll('.savings-row');
    expect(savingsRows).toHaveLength(1);
    expect(savingsRows[0].textContent).toContain('Saved');
    expect(savingsRows[0].querySelector('.saved')?.textContent).toContain('0.30');
  });

  it('should not show savings row when lineSaved is 0', () => {
    component.receipt.set(receiptNoSavings);
    fixture.detectChanges();

    expect(el.querySelectorAll('.savings-row')).toHaveLength(0);
  });
});
