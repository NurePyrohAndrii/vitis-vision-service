import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GroupService} from "../../../../core/api/services/group.service";
import {GroupResponse} from "../../../../core/api/models/group-response";
import {UserResponse} from "../../../../core/api/models/user-response";
import {ApiError} from "../../../../core/api/models/api-error";
import {GroupRequest} from "../../../../core/api/models/group-request";
import {UserService} from "../../../../core/api/services/user.service";
import {VineResponse} from "../../../../core/api/models/vine-response";
import {GroupVineResponse} from "../../../../core/api/models/group-vine-response";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {
  user: UserResponse = {};
  group: GroupResponse = {}
  vinesInGroup: Array<GroupVineResponse> = [];
  vinesCanBeAssigned: Array<GroupVineResponse> = [];

  editGroupRequest: GroupRequest = {
    name: '',
    description: '',
    formationReason: ''
  };

  editGroupErrorMessages: Array<ApiError> = [];

  editGroupMode = false;
  deleteGroupMode = false;
  assignVinesMode = false;
  removeVinesMode = false;

  selectedVineIdsToBeAssigned: Array<number> = [];
  selectedVineIdsToBeRemoved: Array<number> = [];

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
          this.getVinesInGroup(vineyardId, groupId);
          this.getVinesCanBeAssigned(vineyardId, groupId);
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

  assignVinesToGroup() {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const groupId = this.route.snapshot.params['groupId'];
    this.groupService.addVinesToGroup({
      vineyardId: vineyardId,
      groupId: groupId,
      body: {
        vineIds: this.selectedVineIdsToBeAssigned
      }
    }).subscribe({
      next: () => {
        this.vinesCanBeAssigned = this.vinesCanBeAssigned
          .filter(vine => !this.selectedVineIdsToBeAssigned.includes(vine.vineId as number));
        this.selectedVineIdsToBeAssigned = [];
        this.getVinesInGroup(vineyardId, groupId);
        this.assignVinesMode = false;
      }
    });
  }

  removeVinesFromGroup() {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const groupId = this.route.snapshot.params['groupId'];
    this.groupService.removeVinesFromGroup({
      vineyardId: vineyardId,
      groupId: groupId,
      body: {
        vineIds: this.selectedVineIdsToBeRemoved
      }
    }).subscribe({
      next: () => {
        this.vinesInGroup = this.vinesInGroup
          .filter(vine => !this.selectedVineIdsToBeRemoved.includes(vine.vineId as number));
        this.selectedVineIdsToBeRemoved = [];
        this.getVinesCanBeAssigned(vineyardId, groupId);
        this.removeVinesMode = false;
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

  toggleAssignVines() {
    this.assignVinesMode = !this.assignVinesMode
  }

  toggleRemoveVines() {
    this.removeVinesMode = !this.removeVinesMode;
  }

  onVineSelectionChange(event: any, vineId: number | undefined): void {
    const id = vineId as number;
    if (event.target.checked) {
      if (!this.selectedVineIdsToBeAssigned.includes(id)) {
        this.selectedVineIdsToBeAssigned.push(id);
      }
    } else {
      this.selectedVineIdsToBeAssigned = this.selectedVineIdsToBeAssigned.filter(id => id !== vineId);
    }
  }

  onVineRemovalChange(event: any, vineId: number | undefined): void {
    const id = vineId as number;
    if (event.target.checked) {
      if (!this.selectedVineIdsToBeRemoved.includes(id)) {
        this.selectedVineIdsToBeRemoved.push(id);
      }
    } else {
      this.selectedVineIdsToBeRemoved = this.selectedVineIdsToBeRemoved.filter(id => id !== vineId);
    }
  }

  private getVinesInGroup(vineyardId: number, groupId: number) {
    this.groupService.getVinesInGroup({
      vineyardId: vineyardId,
      groupId: groupId,
      pageable: {
        page: 0,
        size: 10,
        sort: ['']
      }
    }).subscribe({
      next: vines => {
        this.vinesInGroup = vines.data?.content as Array<VineResponse>;
        console.log(vines.data?.content)
      },
    });
  }

  private getVinesCanBeAssigned(vineyardId: number, groupId: number) {
    this.groupService.getVinesToAssign({
      pageable: {
        page: 0,
        size: 100,
        sort: ['']
      },
      vineyardId: vineyardId,
      groupId: groupId,
    }).subscribe({
      next: vines => {
        const response = vines.data?.content as Array<GroupVineResponse>;
        this.vinesCanBeAssigned = response as Array<GroupVineResponse>;
        console.log(response)
      },
    });
  }
}
