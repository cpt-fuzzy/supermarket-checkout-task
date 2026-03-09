import { TestBed } from '@angular/core/testing';
import { App } from './app';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should render the app header', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const heading = fixture.nativeElement.querySelector('.app-title');
    expect(heading?.textContent).toContain('My little supermarket');
  });

  it('should contain the catalog and cart sidebar', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelector('app-catalog')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('app-cart-sidebar')).toBeTruthy();
  });
});
