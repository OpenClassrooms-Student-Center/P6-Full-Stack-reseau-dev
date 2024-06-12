import {Component, Input, Output, EventEmitter} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardActions, MatCardContent, MatCardTitle} from "@angular/material/card";
import {NgForOf} from "@angular/common";
import {TranslocoPipe} from "@jsverse/transloco";
import {Topic} from "../../../core/models/topic";

@Component({
  selector: 'app-theme-layout',
  standalone: true,
    imports: [
        MatButton,
        MatCard,
        MatCardActions,
        MatCardContent,
        MatCardTitle,
        NgForOf,
        TranslocoPipe
    ],
  templateUrl: './theme-layout.component.html',
  styleUrl: './theme-layout.component.scss'
})
export class ThemeLayoutComponent {

  @Input() topics!: Topic[];

  @Input() buttonText!: string;

  @Output() clickEvent = new EventEmitter<number>();

  clickAction(topicId: number){
    this.clickEvent.emit(topicId);
  }

}
