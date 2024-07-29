import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {

  private loadingSubject = new BehaviorSubject<boolean>(true); // Initialize to true
  public loading$ = this.loadingSubject.asObservable();

  /**
   * Show the loader
   */
  show(): void {
    if(!this.loadingSubject.getValue()) {
      this.loadingSubject.next(true);
    }
  }

  /**
   * Hide the loader
   */
  hide(): void {
    if(this.loadingSubject.getValue()){
      this.loadingSubject.next(false);
    }
  }
}
