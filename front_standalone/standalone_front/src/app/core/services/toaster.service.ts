import { Injectable } from '@angular/core';
import {ToastrService} from "ngx-toastr";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ToasterService {
  constructor(
    private toaster: ToastrService,
  ) { }

  public handleError(err: HttpErrorResponse) {
    let errorMessage: string;
    if (err.error instanceof ErrorEvent) {
      // A client-side or network error occurred.
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      // The backend returned an unsuccessful response.
      errorMessage = `Something went wrong`;
    }
    this.toaster.error(errorMessage);
    console.error(errorMessage);
  }


  public handleSuccess(msg: string) {
    let message: string;
    if (msg) {
      message = `Success : ${msg}`;
    } else {
      message = `Success`;
    }
    this.toaster.success(message);
  }
}
