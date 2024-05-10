import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VineyardComponent } from './vineyard.component';

describe('VineyardComponent', () => {
  let component: VineyardComponent;
  let fixture: ComponentFixture<VineyardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VineyardComponent]
    });
    fixture = TestBed.createComponent(VineyardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
