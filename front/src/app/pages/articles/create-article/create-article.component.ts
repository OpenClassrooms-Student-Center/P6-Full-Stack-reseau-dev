import {Component, inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {map, Observable, startWith} from "rxjs";
import {ThemeModel} from "../../../models/ThemeModel";
import {ArticleService} from "../../../services/article.service";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {BackComponent} from "../../../components/back/back.component";
import {AsyncPipe} from "@angular/common";
import {TokenModel} from "../../../models/TokenModel";
import {MatButton} from "@angular/material/button";
import {ThemeModelDto} from "../../../models/ThemeModelDto";

@Component({
  selector: 'app-create-article',
  standalone: true,
  templateUrl: './create-article.component.html',
  imports: [
    ReactiveFormsModule,
    MatAutocompleteTrigger,
    MatAutocomplete,
    BackComponent,
    MatOption,
    AsyncPipe,
    MatButton
  ],
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent {
  articleService: ArticleService = inject(ArticleService);
  fb: FormBuilder = inject(FormBuilder);
  myControl = new FormControl<string | ThemeModelDto>('');
  options: ThemeModelDto[] = [];
  filteredOptions!: Observable<ThemeModelDto[]>;

  public form: FormGroup = this.fb.group({
    title: ['', [Validators.required]],
    content: ['', [Validators.required]],
    authorId: '',
    themeId: '',
  });



  ngOnInit() {
    let tokenJson: string | null = localStorage.getItem('tokenModel')
    let tokenModel: TokenModel = {} as TokenModel;
    this.articleService.getThemes().subscribe((result: ThemeModelDto[]) =>{
      this.options = result;
      this.filteredOptions = this.myControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const title = typeof value === 'string' ? value : value?.name;
          return title ? this._filter(title as string) : this.options.slice();
        }),
      );

      this.myControl.valueChanges.subscribe(value => {
        if (typeof value === 'object' && value !== null) {
          this.form.patchValue({
            themeId: value.id,
          });
        } else {
          this.form.patchValue({
            themeId: '',
          });
        }
      });
    })
    if(tokenJson){
      tokenModel = JSON.parse(tokenJson);
    }
    this.form.patchValue({
      authorUsername: tokenModel.username,
      authorId: tokenModel.id,
    });
  }
  /**
   * Displays the name of the theme in the form input.
   * If the theme is valid and has a name, the theme's name is returned, otherwise an empty string is returned.
   *
   * @param theme The theme model containing the name to be displayed.
   * @return The name of the theme if available, otherwise an empty string.
   */
  displayFn(theme: ThemeModelDto): string {
    return theme && theme.name ? theme.name : '';
  }
  /**
   * Filters the list of available themes based on the provided title.
   * The filtering is case-insensitive and matches themes whose names include the filter value.
   *
   * @param title The title to filter the themes by.
   * @return A filtered list of themes whose names contain the filter value.
   */
  private _filter(title: string): ThemeModelDto[] {
    const filterValue = title.toLowerCase();
    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }
  /**
   * Creates a new article using the form data and sends it to the server.
   * The form values are logged to the console, and on successful creation, the result from the server is also logged.
   */
  create() {
    this.articleService.createArticle(this.form.value).subscribe((result: any) => {
    });
  }
}
