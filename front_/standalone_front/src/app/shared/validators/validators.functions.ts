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

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): null | ValidationErrors => {
    const value = control.value;

    // Check the length of the password
    if (!value || value.length < 8) {
      return { passwordError: 'The password must be at least 8 characters long.' };
    }

    // Use regex to check the password for different character types
    const hasNumber = /\d/.test(value);
    const hasLowercaseLetter = /[a-z]/.test(value);
    const hasUppercaseLetter = /[A-Z]/.test(value);
    const hasSpecialCharacter = /[\W_]/.test(value);

    // Return an error if any of the character types is missing
    if (!hasNumber || !hasLowercaseLetter || !hasUppercaseLetter || !hasSpecialCharacter) {
      return {
        passwordError: 'The password must contain at least one number, one lowercase letter, one uppercase letter, and one special character.'
      };
    }

    // Return null if the password passes all checks
    return null;
  };
}
