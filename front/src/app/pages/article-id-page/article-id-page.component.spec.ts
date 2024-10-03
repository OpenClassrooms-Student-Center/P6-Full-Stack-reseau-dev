import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ArticleIdPageComponent } from './article-id-page.component';
describe('ArticleIdPageComponent', () => {
  let component: ArticleIdPageComponent;
  let fixture: ComponentFixture<ArticleIdPageComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArticleIdPageComponent ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(ArticleIdPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});