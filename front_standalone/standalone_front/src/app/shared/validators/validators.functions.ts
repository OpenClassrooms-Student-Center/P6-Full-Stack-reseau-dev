import {AbstractControl, FormControl, ValidationErrors, ValidatorFn} from '@angular/forms';

export function matchValidator(controlToMatch: FormControl): ValidatorFn {
  return (ctrl: AbstractControl): null | ValidationErrors => {
    if (ctrl.value === controlToMatch.value) {
      return null;
    } else {
      return {
        validValidator: ctrl.value
      };
    }
  };
}
