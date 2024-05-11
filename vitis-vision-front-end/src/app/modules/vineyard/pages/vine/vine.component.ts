import {Component, OnInit} from '@angular/core';
import {VineResponse} from "../../../../core/api/models/vine-response";
import {VineRequest} from "../../../../core/api/models/vine-request";
import {VineService} from "../../../../core/api/services/vine.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ApiError} from "../../../../core/api/models/api-error";
import {DeviceService} from "../../../../core/api/services/device.service";
import {DeviceResponse} from "../../../../core/api/models/device-response";
import {DeviceRequest} from "../../../../core/api/models/device-request";

@Component({
  selector: 'app-vine',
  templateUrl: './vine.component.html',
  styleUrls: ['./vine.component.css']
})
export class VineComponent implements OnInit {

  vine: VineResponse = {}
  device: DeviceResponse = {}

  editVineRequest: VineRequest = {
    vineNumber: 0,
    rowNumber: 0,
    variety: '',
    bolesCount: 0,
    plantingDate: '',
    formationType: ''
  }
  deviceRequest: DeviceRequest = {
    description: '',
    deviceType: '',
    installationDate: '',
    manufacturer: '',
    name: ''
  }
  frequency: number = 0

  editVineErrorMessages: Array<ApiError> = [];
  createDeviceErrorMessages: Array<ApiError> = [];
  editDeviceErrorMessages: Array<ApiError> = [];
  activateDeviceErrorMessages: Array<ApiError> = [];
  deactivateDeviceErrorMessages: Array<ApiError> = [];

  editVineMode = false;
  deleteVineMode = false;
  createDeviceMode = false;
  editDeviceMode = false;
  deleteDeviceMode = false;
  activateDeviceMode = false;
  deactivateDeviceMode = false;

  constructor(
    private vineService: VineService,
    private deviceService: DeviceService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    const blockId = this.route.snapshot.params['blockId']
    const vineId = this.route.snapshot.params['vineId']

    this.vineService.getVine({
      blockId: blockId,
      vineId: vineId
    }).subscribe(vine => {
      const response = vine.data as VineResponse
      this.vine = response
      this.editVineRequest = {
        vineNumber: response.vineNumber as number,
        rowNumber: response.rowNumber as number,
        variety: response.variety as string,
        bolesCount: response.bolesCount as number,
        plantingDate: response.plantingDate as string,
        formationType: response.formationType as string
      }
      this.getDevice(response.id as number)
    })
  }

  editVine() {
    const blockId = this.route.snapshot.params['blockId']
    const vineId = this.route.snapshot.params['vineId']

    this.vineService.updateVine({
      blockId: blockId,
      vineId: vineId,
      body: this.editVineRequest
    }).subscribe({
      next: (res) => {
        this.toggleEditVineMode()
        this.vine = res.data as VineResponse
      },
      error: error => {
        this.editVineErrorMessages = error.error.errors
      }
    })
  }

  deleteVine() {
    const vineyardId = this.route.snapshot.params['vineyardId']
    const blockId = this.route.snapshot.params['blockId']
    const vineId = this.route.snapshot.params['vineId']

    this.vineService.deleteVine({
      blockId: blockId,
      vineId: vineId
    }).subscribe({
      next: () => {
        this.router.navigate(['/vineyard/', vineyardId, 'block', blockId]).then(r => r)
      }
    })
  }

  createDevice() {
    const vineId = this.route.snapshot.params['vineId']

    this.deviceService.createDevice({
      vineId: vineId,
      body: this.deviceRequest
    }).subscribe({
      next: () => {
        this.toggleCreateDeviceMode()
        this.getDevice(vineId)
      },
      error: error => {
        this.createDeviceErrorMessages = error.error.errors
      }
    })
  }

  editDevice() {
    const vineId = this.route.snapshot.params['vineId']

    this.deviceService.updateDevice({
      vineId: vineId,
      deviceId: this.device.id as number,
      body: this.deviceRequest
    }).subscribe({
      next: () => {
        this.toggleEditDeviceMode()
        this.getDevice(vineId)
      },
      error: error => {
        this.editDeviceErrorMessages = error.error.errors
      }
    })
  }

  deleteDevice() {
    const vineId = this.route.snapshot.params['vineId']

    this.deviceService.deleteDevice({
      vineId: vineId,
      deviceId: this.device.id as number
    }).subscribe({
      next: () => {
        this.device = {}
      }
    })
  }

  activateDevice() {
    this.deviceService.activateDevice({
      vineId: this.vine.id as number,
      deviceId: this.device.id as number,
      frequency: this.frequency
    }).subscribe({
      next: () => {
        this.toggleActivateDeviceMode()
        this.device.active = true
      },
      error: error => {
        this.activateDeviceErrorMessages = error.error.errors
      }
    })
  }

  deactivateDevice() {
    this.deviceService.deactivateDevice({
      vineId: this.vine.id as number,
      deviceId: this.device.id as number
    }).subscribe({
      next: () => {
        this.toggleDeactivateDeviceMode()
        this.device.active = false
      },
      error: error => {
        this.deactivateDeviceErrorMessages = error.error.errors
      }
    })
  }

  toggleEditVineMode() {
    this.editVineMode = !this.editVineMode
    this.editVineErrorMessages = []
  }

  toggleDeleteVineMode() {
    this.deleteVineMode = !this.deleteVineMode
  }

  toggleCreateDeviceMode() {
    this.createDeviceMode = !this.createDeviceMode
    this.createDeviceErrorMessages = []
  }

  toggleEditDeviceMode() {
    this.editDeviceMode = !this.editDeviceMode
    this.editDeviceErrorMessages = []
  }

  toggleDeleteDeviceMode() {
    this.deleteDeviceMode = !this.deleteDeviceMode
  }

  toggleActivateDeviceMode() {
    this.activateDeviceMode = !this.activateDeviceMode
  }

  toggleDeactivateDeviceMode() {
    this.deactivateDeviceMode = !this.deactivateDeviceMode
  }

  private getDevice(id: number) {
    this.deviceService.getDevice({
      vineId: id
    }).subscribe({
      next: device => {
        const deviceResponse = device.data as DeviceResponse
        this.device = deviceResponse as DeviceResponse
        this.deviceRequest = {
          description: deviceResponse.description as string,
          deviceType: deviceResponse.deviceType as string,
          installationDate: deviceResponse.installationDate as string,
          manufacturer: deviceResponse.manufacturer as string,
          name: deviceResponse.name as string
        }
      },
      error: () => {
        this.device = {}
      }
    })
  }
}
