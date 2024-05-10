import {Component, OnInit} from '@angular/core';
import {UserResponse} from "../../../../core/api/models/user-response";
import {UserService} from "../../../../core/api/services/user.service";
import {VineyardResponse} from "../../../../core/api/models/vineyard-response";
import {VineyardService} from "../../../../core/api/services/vineyard.service";
import {BlockService} from "../../../../core/api/services/block.service";
import {BlockResponse} from "../../../../core/api/models/block-response";
import {GroupRequest} from "../../../../core/api/models/group-request";
import {GroupService} from "../../../../core/api/services/group.service";
import {VineyardRequest} from "../../../../core/api/models/vineyard-request";
import {ApiError} from "../../../../core/api/models/api-error";
import {BlockRequest} from "../../../../core/api/models/block-request";
import {GroupResponse} from "../../../../core/api/models/group-response";
import {StaffResponse} from "../../../../core/api/models/staff-response";
import {StaffService} from "../../../../core/api/services/staff.service";
import {StaffRequest} from "../../../../core/api/models/staff-request";

@Component({
  selector: 'app-vineyard',
  templateUrl: './vineyard.component.html',
  styleUrls: ['./vineyard.component.css']
})
export class VineyardComponent implements OnInit {

  user: UserResponse = {};
  vineyard: VineyardResponse = {};
  blocks: Array<BlockResponse> = [];
  groups: Array<GroupResponse> = [];
  staff: Array<StaffResponse> = [];

  createVineyardErrorMessages: Array<ApiError> = [];
  editVineyardErrorMessages: Array<ApiError> = [];
  createBlockErrorMessages: Array<ApiError> = [];
  createGroupErrorMessages: Array<ApiError> = [];
  hireEmployeeErrorMessages: Array<ApiError> = [];

  createVineyardRequest: VineyardRequest = {city: '', companyName: '', dbaName: '', email: '', phoneNumber: '', streetAddress: '', zipCode: ''};
  editVineyardRequest: VineyardRequest = {city: '', companyName: '', dbaName: '', email: '', phoneNumber: '', streetAddress: '', zipCode: ''};
  createBlockRequest: BlockRequest = {name: '', partitioningType: '', rowOrientation: '', rowSpacing: 0, trellisSystemType: '', vineSpacing: 0};
  createGroupRequest: GroupRequest = {description: '', formationReason: '', name: ''};
  hireEmployeeRequest: StaffRequest = {staffId: 0, vineyardRole: ''};
  editStaffRequest: StaffRequest = {staffId: 0, vineyardRole: ''};

  createVineyardMode = false;
  editVineyardMode = false;
  deleteVineyardMode = false;
  createBlockMode = false;
  createGroupMode = false;
  hireEmployeeMode = false;
  editStaffMode = false;
  editStaffId: number | undefined;

  constructor(
    private userService: UserService,
    private vineyardService: VineyardService,
    private blockService: BlockService,
    private groupService: GroupService,
    private staffService: StaffService
  ) {
  }

  ngOnInit(): void {
    this.userService.getMe().subscribe({
      next: (res) => {
        console.log(res.data)
        this.user = res.data as UserResponse;
        this.getVineyard(Number(this.user.vineyardId));
      },
    });
  }

  private getVineyard(id: number) {
    this.vineyardService.getVineyard({vineyardId: id}).subscribe({
      next: (res) => {
        console.log(res.data)
        const response = res.data as VineyardResponse;
        this.vineyard = response;
        this.editVineyardRequest = {
          city: response.city as string,
          companyName: response.companyName as string,
          dbaName: response.dbaName as string,
          email: response.email as string,
          phoneNumber: response.phoneNumber as string,
          streetAddress: response.streetAddress as string,
          zipCode: response.zipCode as string
        };

        this.getStaff(res.data?.id as number);
        this.getBlocks(res.data?.id as number);
        this.getGroups(res.data?.id as number);
      },
    });
  }

  private getStaff(id: number) {
    this.staffService.getAllStaff({
      pageable: {
        page: 0,
        size: 100,
        sort: []
      },
      vineyardId: id
    }).subscribe({
      next: (res) => {
        console.log(res.data)
        this.staff = res.data?.content as Array<StaffResponse>;
      },
    });
  }

  private getBlocks(id: number) {
    this.blockService.getBlocks({
      pageable: {
        page: 0,
        size: 100,
        sort: []
      },
      vineyardId: id
    }).subscribe({
      next: (res) => {
        console.log(res.data)
        this.blocks = res.data?.content as Array<BlockResponse>;
      },
    });
  }

  private getGroups(id: number) {
    this.groupService.getGroups({
      pageable: {
        page: 0,
        size: 100,
        sort: []
      },
      vineyardId: id
    }).subscribe({
      next: (res) => {
        console.log(res.data)
        this.groups = res.data?.content as Array<GroupResponse>;
      },
    });
  }

  createVineyard() {
    this.vineyardService.createVineyard({body: this.createVineyardRequest}).subscribe({
      next: (res) => {
        this.createVineyardMode = false;
        this.vineyard = res.data as VineyardResponse;
        this.user.role = 'VINEYARD_DIRECTOR';
        console.log(this.user);
      },
      error: (err) => {
        this.createVineyardErrorMessages = err.error.errors as Array<ApiError>;
      }
    });
  }

  editVineyard() {
    this.vineyardService.updateVineyard({
      vineyardId: this.vineyard.id as number,
      body: this.editVineyardRequest
    }).subscribe({
      next: (res) => {
        this.editVineyardMode = false;
        this.vineyard = res.data as VineyardResponse;
      },
      error: (err) => {
        this.editVineyardErrorMessages = err.error.errors as Array<ApiError>;
      }
    });
  }

  deleteVineyard() {
    this.vineyardService.deleteVineyard({vineyardId: this.vineyard.id as number}).subscribe({
      next: () => {
        this.deleteVineyardMode = false;
        this.user.role = 'USER';
        this.vineyard = {};
      },
    });
  }

  hireStaff() {
    this.staffService.hireStaff({
      body: this.hireEmployeeRequest,
      vineyardId: this.vineyard.id as number
    }).subscribe({
      next: () => {
        this.hireEmployeeMode = false;
        this.getStaff(this.vineyard.id as number)
      },
      error: (err) => {
        this.hireEmployeeErrorMessages = err.error.errors as Array<ApiError>;
      }
    });
  }

  fireStaff(id: number | undefined) {
    this.staffService.fireStaff({
      body: {staffId: id as number},
      vineyardId: this.vineyard.id as number
    }).subscribe({
      next: () => {
        this.getStaff(this.vineyard.id as number)
      },
    });
  }

  createBlock() {
    this.blockService.createBlock({
      body: this.createBlockRequest,
      vineyardId: this.vineyard.id as number
    }).subscribe({
      next: (res) => {
        this.createBlockMode = false;
        this.blocks.push(res.data as BlockResponse);
      },
      error: (err) => {
        this.createBlockErrorMessages = err.error.errors as Array<ApiError>;
      }
    });
  }

  createGroup() {
    this.groupService.createGroup({
      body: this.createGroupRequest,
      vineyardId: this.vineyard.id as number
    }).subscribe({
      next: (res) => {
        this.createGroupMode = false;
        this.groups.push(res.data as GroupResponse);
      },
      error: (err) => {
        this.createGroupErrorMessages = err.error.errors as Array<ApiError>;
      }
    });
  }

  editStaff() {
    this.staffService.updateStaff({
      body: this.editStaffRequest,
      vineyardId: this.vineyard.id as number
    }).subscribe({
      next: () => {
        this.editStaffMode = false;
        this.getStaff(this.vineyard.id as number)
      },
    });
  }

  toggleCreateVineyard() {
    this.createVineyardMode = !this.createVineyardMode;
  }

  toggleEditVineyard() {
    this.editVineyardMode = !this.editVineyardMode;
    this.editVineyardErrorMessages = [];
  }

  toggleDeleteVineyard() {
    this.deleteVineyardMode = !this.deleteVineyardMode;
  }

  toggleCreateBlock() {
    this.createBlockMode = !this.createBlockMode;
    this.createBlockErrorMessages = [];
  }

  toggleCreateGroup() {
    this.createGroupMode = !this.createGroupMode;
    this.createGroupErrorMessages = [];
  }

  toggleHireEmployee() {
    this.hireEmployeeMode = !this.hireEmployeeMode;
    this.hireEmployeeErrorMessages = [];
  }

  toggleEditStaff(id: number | undefined) {
    this.editStaffMode = !this.editStaffMode;
    this.editStaffId = id;
    const staff = this.staff.find(s => s.id === id);
    this.editStaffRequest.staffId = staff?.id as number;
    this.editStaffRequest.vineyardRole = staff?.role as string;
  }
}
