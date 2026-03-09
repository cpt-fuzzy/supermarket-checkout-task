import { Product } from '../../model/product.model';
import { TestBed } from '@angular/core/testing';
import { ProductTile } from './product-tile';
import { CartService } from '../../service/cart.service';

const appleWithOffer: Product = {
  sku: 'apple',
  name: 'Apple',
  price: 0.3,
  offer: { requiredQuantity: 2, offerPrice: 0.45, description: '2 for 0.45' },
};

const orangeNoOffer: Product = {
  sku: 'orange',
  name: 'Orange',
  price: 0.6,
  offer: null,
};

describe('ProductTile', () => {
  beforeEach(async () => {
    localStorage.clear();
    await TestBed.configureTestingModule({
      imports: [ProductTile],
    }).compileComponents();
  });

  it('should display product name and price', () => {
    const fixture = TestBed.createComponent(ProductTile);
    fixture.componentRef.setInput('product', orangeNoOffer);
    fixture.detectChanges();

    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.product-name')?.textContent).toContain('Orange');
    expect(el.querySelector('.product-price')?.textContent).toContain('0.60');
  });

  it('should show offer badge when product has an offer', () => {
    const fixture = TestBed.createComponent(ProductTile);
    fixture.componentRef.setInput('product', appleWithOffer);
    fixture.detectChanges();

    const badge = fixture.nativeElement.querySelector('.offer-badge');
    expect(badge).toBeTruthy();
    expect(badge?.textContent).toContain('2 for 0.45');
  });

  it('should not show offer badge when product has no offer', () => {
    const fixture = TestBed.createComponent(ProductTile);
    fixture.componentRef.setInput('product', orangeNoOffer);
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('.offer-badge')).toBeNull();
  });

  it('should add product to cart on + click', () => {
    const fixture = TestBed.createComponent(ProductTile);
    fixture.componentRef.setInput('product', appleWithOffer);
    fixture.detectChanges();

    const addBtn = fixture.nativeElement.querySelectorAll('.qty-btn')[1];
    addBtn.click();
    fixture.detectChanges();

    const cart = TestBed.inject(CartService);
    expect(cart.items()[0].productSku).toBe('apple');
    expect(cart.items()[0].quantity).toBe(1);
  });
});
