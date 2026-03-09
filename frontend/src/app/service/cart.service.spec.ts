import { Product } from '../model/product.model';
import { CartService } from './cart.service';
import { TestBed } from '@angular/core/testing';

const apple: Product = {
  sku: 'apple',
  name: 'Apple',
  price: 0.3,
  offer: { requiredQuantity: 2, offerPrice: 0.45, description: '2 for 0.45' },
};

const banana: Product = {
  sku: 'banana',
  name: 'Apple',
  price: 0.5,
  offer: null,
};

describe('CartService', () => {
  let service: CartService;

  beforeEach(() => {
    localStorage.clear();
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartService);
  });

  it('should start with an empty cart', () => {
    expect(service.items()).toEqual([]);
    expect(service.isEmpty()).toBe(true);
    expect(service.itemCount()).toBe(0);
    expect(service.subtotal()).toBe(0);
  });

  it('should add a product to the cart', () => {
    service.addOne(apple);

    expect(service.items()).toHaveLength(1);
    expect(service.items()[0].productSku).toBe('apple');
    expect(service.items()[0].quantity).toBe(1);
    expect(service.isEmpty()).toBe(false);
  });

  it('should increment quantity when adding the same product', () => {
    service.addOne(apple);
    service.addOne(apple);

    expect(service.items()).toHaveLength(1);
    expect(service.items()[0].quantity).toBe(2);
    expect(service.itemCount()).toBe(2);
  });

  it('should store offer data when product has an offer', () => {
    service.addOne(apple);

    expect(service.items()[0].offer).toEqual({
      requiredQuantity: 2,
      offerDescription: '2 for 0.45',
    });
  });

  it('should store null offer when product has no offer', () => {
    service.addOne(banana);

    expect(service.items()[0].offer).toBeNull();
  });

  it('should track multiple products', () => {
    service.addOne(apple);
    service.addOne(banana);

    expect(service.items()).toHaveLength(2);
    expect(service.itemCount()).toBe(2);
  });

  it('should calculate subtotal correctly', () => {
    service.addOne(apple);
    service.addOne(apple);
    service.addOne(banana);

    expect(service.subtotal()).toBeCloseTo(1.1);
  });

  it('should decrement quantity', () => {
    service.addOne(apple);
    service.addOne(apple);
    service.removeOne('apple');

    expect(service.items()[0].quantity).toBe(1);
  });

  it('should remove product entirely when zero', () => {
    service.addOne(apple);
    service.removeOne('apple');

    expect(service.items()).toHaveLength(0);
    expect(service.isEmpty()).toBe(true);
  });

  it('should clear the cart', () => {
    service.addOne(apple);
    service.addOne(banana);
    service.clear();

    expect(service.items()).toHaveLength(0);
    expect(service.isEmpty()).toBe(true);
  });

  it('should persist items to localStorage', () => {
    service.addOne(apple);
    TestBed.tick();

    const stored = JSON.parse(localStorage.getItem('cart')!);
    expect(stored).toHaveLength(1);
    expect(stored[0].productSku).toBe('apple');
  });

  it('should restore items from localStorage', () => {
    service.addOne(apple);
    service.addOne(banana);
    TestBed.tick();

    TestBed.resetTestingModule();
    TestBed.configureTestingModule({});
    const freshService = TestBed.inject(CartService);

    expect(freshService.items()).toHaveLength(2);
  });
});
