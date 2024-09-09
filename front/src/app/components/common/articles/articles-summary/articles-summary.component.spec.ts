import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlesSummaryComponent } from './articles-summary.component';

describe('ArticlesSummaryComponent', () => {
  let component: ArticlesSummaryComponent;
  let fixture: ComponentFixture<ArticlesSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticlesSummaryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ArticlesSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
