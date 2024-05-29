import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlesLayoutComponent } from './articles-layout.component';

describe('ArticlesLayoutComponent', () => {
  let component: ArticlesLayoutComponent;
  let fixture: ComponentFixture<ArticlesLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticlesLayoutComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ArticlesLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
