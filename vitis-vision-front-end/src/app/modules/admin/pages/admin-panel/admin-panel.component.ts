import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../../../core/api/services/user.service";
import {UserResponse} from "../../../../core/api/models/user-response";
import {DbBackupControllerService} from "../../../../core/api/services/db-backup-controller.service";
import {DbBackupResponse} from "../../../../core/api/models/db-backup-response";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  users: Array<UserResponse> = [];
  dbBackups: Array<DbBackupResponse> = [];


  constructor(
    private userService: UserService,
    private backupService: DbBackupControllerService
  ) {
  }

  ngOnInit(): void {
    this.userService.getUsers({
      pageable: {
        page: 0,
        size: 100,
        sort: []
      }
    }).subscribe({
      next: (res) => {
        const allUsers = res.data?.content || [];
        this.users = allUsers.filter(user => user.role !== 'ADMIN');
      }
    });

    this.backupService.getBackups().subscribe(backups => {
      this.dbBackups = backups.data || [];
    });
  }

  blockUser(id: number | undefined) {
    if (id) {
      this.userService.blockUser(
        {
          body: {userId: id}
        }
      ).subscribe({
        next: () => {
          this.users = this.users.map(user => {
            if (user.id === id) {
              user.isBlocked = true;
            }
            return user;
          });
        }
      });
    }
  }

  unblockUser(id: number | undefined) {
    if (id) {
      this.userService.unblockUser(
        {
          body: {userId: id}
        }
      ).subscribe({
        next: () => {
          this.users = this.users.map(user => {
            if (user.id === id) {
              user.isBlocked = false;
            }
            return user;
          });
        }
      });
    }
  }

  deleteUser(id: number | undefined) {
    if (id) {
      this.userService.deleteUserById({userId: id}).subscribe({
        next: () => {
          this.users = this.users.filter(user => user.id !== id);
        }
      });
    }
  }

  createBackup() {
    this.backupService.backupDatabase().subscribe({
      next: () => {
        this.backupService.getBackups().subscribe(backups => {
          this.dbBackups = backups.data || [];
        });
      }
    });
  }
}
