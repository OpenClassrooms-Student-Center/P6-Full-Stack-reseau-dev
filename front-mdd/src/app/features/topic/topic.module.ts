import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicsComponent } from './components/topics/topics.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TopicRoutingModule } from './topic-routing.module';

@NgModule({
  declarations: [TopicsComponent],
  imports: [ReactiveFormsModule,CommonModule,TopicRoutingModule], // Ajout de CommonModule
  exports: [TopicsComponent], 
})
export class TopicModule {}