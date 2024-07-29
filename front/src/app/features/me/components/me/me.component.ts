import {Component, OnDestroy, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {FormBuilder, Validators} from "@angular/forms";
import {Topic} from "../../../topic/interfaces/topic.interface";
import {User} from "../../interfaces/user.interface";
import {AuthService} from "../../../auth/services/auth.service";
import {UserService} from "../../services/user.service";
import {TopicService} from "../../../topic/services/topic.service";
import {SessionInformation} from "../../../auth/interfaces/sessionInformation.interface";
import {Subscription} from "rxjs";
import {LoaderService} from "../../../../shared/services/loading.service";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {

  public onError = false;
  public errorMessage = "";

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.email
      ]
    ],
    username: ['', []],
    password: [
      '',
      [
        Validators.required,
        Validators.min(8),
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:"\\\\|,.<>\\/?]).+$')
      ]
    ]
  });

  public user: User | undefined;

  public topics: Topic[] = [];

  private userSubscription!: Subscription;
  private userUpdateSubscription!: Subscription;
  private topicsSubscription!: Subscription;

  constructor(private router: Router,
              private authService: AuthService,
              private loaderService: LoaderService,
              private fb: FormBuilder,
              private userService: UserService,
              private topicService: TopicService) {
  }

  public submit(): void
  {
    const updatedUser = this.form.value as User;
    this.userUpdateSubscription = this.userService.updateInfo(updatedUser).subscribe({
      next: (sessionInformation: SessionInformation) => {
        this.authService.save(sessionInformation);
        this.router.navigate(['/me']);
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error.error.message;
      }
    });
  }

  public ngOnInit(): void
  {
    this.userSubscription = this.userService
      .me()
      .subscribe({
        next: (user: User) => {
          this.user = user;
          this.form.patchValue({
            username: user.username,
            email: user.email,
            // Ne pas inclure password ici pour conserver les validateurs
          });
        },
        error: (error) => {
          this.onError = true;
          this.errorMessage = error.error.message;
        },
        complete: () => {
          this.loaderService.hide();
        }
      });
    this.topicsSubscription = this.topicService.getUserSubscriptions().subscribe((topics: Topic[]) => {
      this.topics = topics;
    });
  }

  public unsubscribe(id:number): void{
    this.topicService.unfollow(id).subscribe({
      next: (topics: Topic[]) => {
        this.topics = topics;
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error.error.message;
      }
    });
  }

  public logout(): void {
    this.authService.logOut();
    this.router.navigate([''])
  }

  public ngOnDestroy(): void
  {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
    if (this.userUpdateSubscription) {
      this.userUpdateSubscription.unsubscribe();
    }
    if (this.topicsSubscription) {
      this.topicsSubscription.unsubscribe();
    }
  }

}
