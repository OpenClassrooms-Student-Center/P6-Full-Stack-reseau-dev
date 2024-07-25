import {Component, OnInit} from "@angular/core";
import {Topic} from "../../interfaces/topic.interface";
import {TopicService} from "../../services/topic.service";

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  public onError = false;
  public errorMessage = "";

  public topics: Topic[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.topicService.getTopicsNotFollowedByUser().subscribe((topics: Topic[]) => {
      this.topics = topics;
    });
  }

  public subscribe(id:number): void{
    this.topicService.follow(id).subscribe({
      next: (topics: Topic[]) => {
        this.topics = topics;
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error.error.message;
      }
    });
  }

}
