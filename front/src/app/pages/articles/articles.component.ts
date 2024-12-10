import {Component, inject} from '@angular/core';
import {ArticleService} from "../../services/article.service";
import {ArticleModel} from "../../models/ArticleModel";
import {CardArticleComponent} from "./card-article/card-article.component";
import {MatButton} from "@angular/material/button";
import {MatIconModule} from '@angular/material/icon';
import {RouterLink} from "@angular/router";
@Component({
  selector: 'app-articles',
  standalone: true,
  imports: [
    CardArticleComponent,
    MatButton,
    MatIconModule,
    RouterLink
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {
  private articleService: ArticleService = inject(ArticleService);
  reverseOrder: boolean = false;
  articles:ArticleModel[] = []
  ngOnInit(){
   this.loadArticles()
  }
  /**
   * Toggles the sort order for articles between normal and reverse chronological order.
   * After toggling, it reloads the articles using the updated sort order.
   */
  toggleSortOrder() {
    this.reverseOrder = !this.reverseOrder;
    this.loadArticles();
  }
  /**
   * Loads the list of articles from the server, sorted according to the current sort order (normal or reverse).
   * The loaded articles are assigned to the local `articles` variable, and the list is logged to the console.
   */
  loadArticles(){
    this.articleService.getAllArticles(this.reverseOrder).subscribe(((result: ArticleModel[]) =>{
      this.articles = result;
    }))
  }
}
