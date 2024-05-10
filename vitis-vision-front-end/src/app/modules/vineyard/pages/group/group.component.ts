import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GroupService} from "../../../../core/api/services/group.service";
import {GroupResponse} from "../../../../core/api/models/group-response";
import {UserResponse} from "../../../../core/api/models/user-response";
import {ApiError} from "../../../../core/api/models/api-error";
import {GroupRequest} from "../../../../core/api/models/group-request";
import {UserService} from "../../../../core/api/services/user.service";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {
  user: UserResponse = {};
  group: GroupResponse = {}

  editGroupRequest: GroupRequest = {
    name: '',
    description: '',
    formationReason: ''
  };

  editGroupErrorMessages: Array<ApiError> = [];

  editGroupMode = false;
  deleteGroupMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private groupService: GroupService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const groupId = this.route.snapshot.params['groupId'];
    this.userService.getMe().subscribe({
      next: user => {
        this.user = user.data as UserResponse;
      }
    });
    this.groupService.getGroup(
      {
        vineyardId: vineyardId,
        groupId: groupId
      }
    ).subscribe({
        next: group => {
          const response = group.data as GroupResponse;
          this.group = response as GroupResponse;
          this.editGroupRequest = {
            name: response.name as string,
            description: response.description as string,
            formationReason: response.formationReason as string
          }
          console.log(group.data);
        },
      }
    );
  }

  editGroup() {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const groupId = this.route.snapshot.params['groupId'];
    this.groupService.updateGroup(
      {
        vineyardId: vineyardId,
        groupId: groupId,
        body: this.editGroupRequest
      }
    ).subscribe({
      next: (res) => {
        this.group = res.data as GroupResponse;
        this.toggleEditGroup();
      },
      error: error => {
        this.editGroupErrorMessages = error.error.errors;
      }
    });
  }

  deleteGroup() {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const groupId = this.route.snapshot.params['groupId'];
    this.groupService.deleteGroup(
      {
        vineyardId: vineyardId,
        groupId: groupId
      }
    ).subscribe({
      next: () => {
        this.router.navigate(['/vineyard']).then(r => r);
      }
    });
  }

  toggleEditGroup() {
    this.editGroupMode = !this.editGroupMode;
    this.editGroupErrorMessages = [];
  }

  toggleDeleteGroup() {
    this.deleteGroupMode = !this.deleteGroupMode;
  }
}
