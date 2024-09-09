import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';

/**
 * Represents the component for articles summary.
 */
@Component({
  selector: 'app-articles-summary',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './articles-summary.component.html',
  styleUrl: './articles-summary.component.scss',
})
export class ArticlesSummaryComponent {
  // * Signal  inputs
  /**
   * The ID of the article.
   */
  public readonly id = input.required<number>();

  /**
   * The title of the article.
   */
  public readonly title = input.required<string>();

  /**
   * The description of the article.
   */
  public readonly description = input.required<string>();

  /**
   * The author of the article.
   */
  public readonly author = input.required<string>();

  /**
   * The date of the article in string format.
   */
  public readonly date = input.required<string>();

  /**
   * The formatted publish date of the article
   */
  public publishDate = computed(() => {
    const date = new Date(this.date()).toLocaleDateString();

    return date;
  });
}
