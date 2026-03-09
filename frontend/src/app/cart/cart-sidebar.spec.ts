import { Product } from '../model/product.model';
import { Receipt } from '../model/receipt.model';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CartSidebar } from './cart-sidebar';
import { CartService } from '../service/cart.service';
import { CheckoutService } from '../service/checkout.service';

const apple: Product = {
  sku: 'apple',
  name: 'Apple',
  price: 0.3,
  offer: { requiredQuantity: 2, offerPrice: 0.45, description: '2 for 0.45' },
};

const banana: Product = {
  sku: 'banana',
  name: 'Banana',
  price: 0.5,
  offer: null,
};

const mockReceipt: Receipt = {
  lines: [
    {
      productName: 'Apple',
      quantity: 2,
      unitPrice: 0.3,
      lineTotal: 0.6,
      offerDescription: null,
      lineSaved: 0,
    },
  ],
  total: 0.6,
  saved: 0,
};

describe('CartSidebar', () => {
  let fixture: ComponentFixture<CartSidebar>;
  let component: CartSidebar;
  let cartService: CartService;
  let mockCheckoutService: { checkout: ReturnType<typeof vi.fn> };

  beforeEach(async () => {
    localStorage.clear();
    mockCheckoutService = { checkout: vi.fn() };

    await TestBed.configureTestingModule({
      imports: [CartSidebar],
      providers: [{ provide: CheckoutService, useValue: mockCheckoutService }],
    }).compileComponents();

    cartService = TestBed.inject(CartService);
    fixture = TestBed.createComponent(CartSidebar);
    component = fixture.componentInstance;
  });

  it('should show empty message when cart is empty', () => {
    fixture.detectChanges();

    const empty = fixture.nativeElement.querySelector('.cart-empty');
    expect(empty).toBeTruthy();
    expect(empty.textContent).toContain('Your cart is empty');
  });

  it('should render cart items with name, quantity and price', () => {
    cartService.addOne(apple);
    cartService.addOne(apple);
    cartService.addOne(banana);
    fixture.detectChanges();

    const items = fixture.nativeElement.querySelectorAll('.cart-item');
    expect(items).toHaveLength(2);

    const firstItem = items[0];
    expect(firstItem.querySelector('.cart-item-name').textContent).toContain('Apple');
    expect(firstItem.querySelector('.cart-item-detail').textContent).toContain('2');

    const secondItem = items[1];
    expect(secondItem.querySelector('.cart-item-name').textContent).toContain('Banana');
  });

  it('should call checkout service and emit receipt on success', async () => {
    cartService.addOne(apple);
    cartService.addOne(apple);
    fixture.detectChanges();

    mockCheckoutService.checkout.mockResolvedValue(mockReceipt);

    let emittedReceipt: Receipt | undefined;
    component.checkedOut.subscribe((r) => (emittedReceipt = r));

    await component.checkout();
    fixture.detectChanges();

    expect(mockCheckoutService.checkout).toHaveBeenCalledOnce();
    expect(emittedReceipt).toEqual(mockReceipt);
  });

  it('should clear cart after successful checkout', async () => {
    cartService.addOne(apple);
    fixture.detectChanges();

    mockCheckoutService.checkout.mockResolvedValue(mockReceipt);
    await component.checkout();

    expect(cartService.isEmpty()).toBe(true);
  });

  it('should show error message on checkout failure', async () => {
    cartService.addOne(apple);
    fixture.detectChanges();

    mockCheckoutService.checkout.mockRejectedValue(new Error('Network error'));
    await component.checkout();
    fixture.detectChanges();

    const error = fixture.nativeElement.querySelector('.cart-error');
    expect(error).toBeTruthy();
    expect(error.textContent).toContain('Checkout failed');
  });
});
