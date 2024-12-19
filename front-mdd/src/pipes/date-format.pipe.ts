import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: Date | string | undefined): string {
    if (!value) return ''; // Si aucune valeur, retourne une chaîne vide

    let date: Date;
    if (typeof value === 'string') {
      // Remplacement du format 'YYYY-MM-DD HH:mm:ss.ssssss' en ISO valide
      value = value.replace(' ', 'T'); // Remplace l'espace par 'T'
      date = new Date(value);
    } else {
      date = value;
    }

    if (isNaN(date.getTime())) {
      return ''; // Retourne une chaîne vide si la conversion échoue
    }

    const options: Intl.DateTimeFormatOptions = {
      day: '2-digit',
      month: 'long',
      year: 'numeric'
    };

    return date.toLocaleDateString('fr-FR', options);
  }
}
