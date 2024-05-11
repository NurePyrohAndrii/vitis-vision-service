import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BlockService} from "../../../../core/api/services/block.service";
import {VineService} from "../../../../core/api/services/vine.service";
import {BlockResponse} from "../../../../core/api/models/block-response";
import {BlockRequest} from "../../../../core/api/models/block-request";
import {UserResponse} from "../../../../core/api/models/user-response";
import {ApiError} from "../../../../core/api/models/api-error";
import {UserService} from "../../../../core/api/services/user.service";
import {VineRequest} from "../../../../core/api/models/vine-request";
import {VineResponse} from "../../../../core/api/models/vine-response";
import {BlockReportRequest} from "../../../../core/api/models/block-report-request";

@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class BlockComponent implements OnInit {

  user: UserResponse = {};
  block: BlockResponse = {}
  vines: Array<VineResponse> = []

  editBlockRequest: BlockRequest = {
    name: '',
    partitioningType: '',
    rowOrientation: '',
    rowSpacing: 0,
    trellisSystemType: '',
    vineSpacing: 0
  };
  createVineRequest: VineRequest = {
    bolesCount: 0,
    formationType: '',
    plantingDate: '',
    rowNumber: 0,
    variety: '',
    vineNumber: 0
  }
  generateBlockReportRequest: BlockReportRequest = {
    startDate: '',
    endDate: '',
    aggregationInterval: 0
  }

  editBlockErrorMessages: Array<ApiError> = [];
  vineCreateErrorMessages: Array<ApiError> = [];
  blockReportErrorMessages: Array<ApiError> = [];

  editBlockMode = false;
  deleteBlockMode = false;
  createVineMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private blockService: BlockService,
    private vineService: VineService
  ) {
  }

  ngOnInit(): void {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const blockId = this.route.snapshot.params['blockId'];
    this.userService.getMe().subscribe({
      next: user => {
        this.user = user.data as UserResponse;
      }
    });
    this.blockService.getBlock(
      {
        vineyardId: vineyardId,
        blockId: blockId
      }
    ).subscribe({
        next: block => {
          const response = block.data as BlockResponse;
          this.block = response as BlockResponse;
          this.editBlockRequest = {
            name: response.name as string,
            partitioningType: response.partitioningType as string,
            rowOrientation: response.rowOrientation as string,
            rowSpacing: response.rowSpacing as number,
            trellisSystemType: response.trellisSystemType as string,
            vineSpacing: response.vineSpacing as number
          }
          this.getVines();
          console.log(block.data);
        },
      }
    );
  }

  editBlock(): void {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const blockId = this.route.snapshot.params['blockId'];
    this.blockService.updateBlock(
      {
        vineyardId: vineyardId,
        blockId: blockId,
        body: this.editBlockRequest
      }
    ).subscribe({
        next: block => {
          this.block = block.data as BlockResponse;
          console.log(block.data);
          this.toggleEditBlock();
        },
        error: error => {
          this.editBlockErrorMessages = error.error.errors;
        }
      }
    );
  }

  deleteBlock(): void {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const blockId = this.route.snapshot.params['blockId'];
    this.blockService.deleteBlock(
      {
        vineyardId: vineyardId,
        blockId: blockId
      }
    ).subscribe({
        next: () => {
          this.router.navigate(['/vineyard']).then(r => r);
        },
      }
    );

  }

  createVine(): void {
    const blockId = this.route.snapshot.params['blockId'];
    this.vineService.createVine(
      {
        blockId: blockId,
        body: this.createVineRequest
      }
    ).subscribe({
        next: vine => {
          this.toggleVineCreate();
          this.vines.push(vine.data as VineResponse);
        },
        error: error => {
          this.vineCreateErrorMessages = error.error.errors;
        }
      }
    );
  }

  generateBlockReport() {
    const vineyardId = this.route.snapshot.params['vineyardId'];
    const blockId = this.route.snapshot.params['blockId'];
    this.blockService.generateBlockReport(
      {
        vineyardId: vineyardId,
        blockId: blockId,
        body: this.generateBlockReportRequest
      }
    ).subscribe({
        next: (res: any) => {
          const a = document.createElement("a");
          a.href = URL.createObjectURL(new Blob([res], {type: "text/csv"}));
          let filename = "block-report_" + this.block.name + "_"
            + this.generateBlockReportRequest.startDate + "_"
            + this.generateBlockReportRequest.endDate;
          a.setAttribute("download", filename + ".csv");
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        },
        error: error => {
          this.blockReportErrorMessages = error.error.errors;
        }
      }
    );
  }

  toggleEditBlock(): void {
    this.editBlockMode = !this.editBlockMode;
    this.editBlockErrorMessages = [];
  }

  toggleDeleteBlock(): void {
    this.deleteBlockMode = !this.deleteBlockMode;
  }

  toggleVineCreate(): void {
    this.createVineMode = !this.createVineMode;
    this.vineCreateErrorMessages = [];
  }

  private getVines() {
    const blockId = this.route.snapshot.params['blockId'];
    this.vineService.getVines(
      {
        pageable: {
          page: 0,
          size: 10,
          sort: ['']
        },
        blockId: blockId
      }
    ).subscribe({
        next: vines => {
          this.vines = vines.data?.content as Array<VineResponse>;
        }
      }
    );
  }
}
