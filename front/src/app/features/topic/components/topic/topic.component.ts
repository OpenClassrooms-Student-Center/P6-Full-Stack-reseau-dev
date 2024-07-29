import {Component, OnInit} from "@angular/core";
import {Topic} from "../../interfaces/topic.interface";
import {TopicService} from "../../services/topic.service";
import {Subscription} from "rxjs";
import {LoaderService} from "../../../../shared/services/loading.service";

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  public onError = false;
  public errorMessage = "";

  public topics: Topic[] = [];

  private topicSubscription!: Subscription;
  private followTopicSubscription!: Subscription;

  constructor(
    private topicService: TopicService,
    private loaderService: LoaderService
  ) {}

  ngOnInit(): void {
    this.topicSubscription = this.topicService.getTopicsNotFollowedByUser().subscribe((topics: Topic[]) => {
      this.topics = topics;
      this.loaderService.hide();
    });
  }

  public subscribe(id:number): void{
    this.followTopicSubscription = this.topicService.follow(id).subscribe({
      next: (topics: Topic[]) => {
        this.topics = topics;
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error.error.message;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.topicSubscription) {
      this.topicSubscription.unsubscribe();
    }
    if (this.followTopicSubscription) {
      this.followTopicSubscription.unsubscribe();
    }
  }

}
