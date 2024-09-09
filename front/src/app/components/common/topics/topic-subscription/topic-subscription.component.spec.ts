import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicSubscriptionComponent } from './topic-subscription.component';

describe('TopicSubscriptionComponent', () => {
  let component: TopicSubscriptionComponent;
  let fixture: ComponentFixture<TopicSubscriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopicSubscriptionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopicSubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
