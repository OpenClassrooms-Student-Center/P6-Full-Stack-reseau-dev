import { Component } from '@angular/core';
import {MatSelectModule} from "@angular/material/select";
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {TranslocoPipe, TranslocoService} from "@jsverse/transloco";

interface Language {
  lang: string;
  flag: string;
}

@Component({
  selector: 'app-language-switcher',
  standalone: true,
  imports: [MatSelectModule, ReactiveFormsModule, CommonModule, TranslocoPipe],
  templateUrl: './language-switcher.component.html',
  styleUrl: './language-switcher.component.scss'
})
export class LanguageSwitcherComponent {

  languages: Language[] = [
    { lang: 'FR', flag: "/assets/images/flags/fr.svg" },
    { lang: 'EN', flag: "/assets/images/flags/en.svg" },
  ];

  readonly DEFAULT_LANGUAGE = this.languages[0];

  formLanguage = new FormControl(this.getLanguage(localStorage.getItem('appLanguage')));

  constructor(private translocoService: TranslocoService) {
    this.translocoService.setActiveLang(this.formLanguage.value!.lang.toLowerCase());

    this.formLanguage.valueChanges.subscribe((selectedLanguage: Language | null) => {
      if (selectedLanguage) {
        this.translocoService.setActiveLang(selectedLanguage.lang.toLowerCase());
        localStorage.setItem('appLanguage', selectedLanguage.lang);
      }
    });
  }

  getLanguage(lang: string | null): Language {
    return this.languages.find(language => language.lang === lang) || this.DEFAULT_LANGUAGE;
  }
}
