import {ChangeDetectorRef, Component, inject, Input} from '@angular/core';
import {ArticleModel} from "../../../models/ArticleModel";
import {DatePipe} from "@angular/common";
import {MatCard} from "@angular/material/card";

@Component({
  selector: 'app-card-article',
  standalone: true,
  imports: [
    DatePipe,
    MatCard
  ],
  templateUrl: './card-article.component.html',
  styleUrl: './card-article.component.scss'
})
export class CardArticleComponent {
  @Input() article!: ArticleModel;
}
