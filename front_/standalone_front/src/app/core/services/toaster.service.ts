import { Injectable } from '@angular/core';
import { ToastrService } from "ngx-toastr";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../models/api.error.model";

@Injectable({
  providedIn: 'root',
})
export class ToasterService {
  constructor(
    private toaster: ToastrService,
  ) {
  }

  public handleError(err: HttpErrorResponse | ApiError) {
    let errorMessage = "";
    if (err.message) {
      // A client-side or network error occurred.
      errorMessage = `An error occurred: ${err.message}`;
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

  public handleWarning(msg: string) {
    let message: string;
    if (msg) {
      message = `Warning : ${msg}`;
    } else {
      message = `Warning !`;
    }
    this.toaster.warning(message);
  }
}
