import { CartItem } from '../model/cart.model';
import { Receipt } from '../model/receipt.model';
import { CheckoutService } from './checkout.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';

const cartItems: CartItem[] = [
  {
    productSku: 'apple',
    productName: 'Apple',
    unitPrice: 0.3,
    quantity: 3,
    offer: { requiredQuantity: 2, offerDescription: '2 for 0.45' },
  },
  { productSku: 'banana', productName: 'Banana', unitPrice: 0.3, quantity: 1, offer: null },
];

const mockReceipt: Receipt = {
  lines: [
    {
      productName: 'Apple',
      quantity: 3,
      unitPrice: 0.3,
      lineTotal: 0.75,
      offerDescription: '2 for 0.45',
      lineSaved: 0.15,
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
  total: 1.25,
  saved: 0.15,
};

describe('CheckoutService', () => {
  let service: CheckoutService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(CheckoutService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should POST to checkout', async () => {
    const promise = service.checkout(cartItems);

    const req = httpMock.expectOne('/api/checkout');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({
      items: [
        { productSku: 'apple', quantity: 3 },
        { productSku: 'banana', quantity: 1 },
      ],
    });
    req.flush(mockReceipt);

    await promise;
  });

  it('should return the receipt from the response', async () => {
    const promise = service.checkout(cartItems);

    httpMock.expectOne('/api/checkout').flush(mockReceipt);

    const receipt = await promise;
    expect(receipt).toEqual(mockReceipt);
  });

  it('should reject when the API returns an error', async () => {
    const promise = service.checkout(cartItems);

    httpMock
      .expectOne('/api/checkout')
      .flush('Internal Server Error', { status: 500, statusText: 'Server Error' });

    await expect(promise).rejects.toBeTruthy();
  });
});
