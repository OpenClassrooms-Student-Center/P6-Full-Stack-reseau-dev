import { Component, input } from '@angular/core';

/**
 * Represents the component for comments.
 */
@Component({
  selector: 'app-comments',
  standalone: true,
  imports: [],
  templateUrl: './comments.component.html',
  styleUrl: './comments.component.scss',
})
export class CommentsComponent {
  /**
   * The username associated with the comment.
   */
  public readonly username = input.required<string>();

  /**
   * The content of the comment.
   */
  public readonly comment = input.required<string>();
}
