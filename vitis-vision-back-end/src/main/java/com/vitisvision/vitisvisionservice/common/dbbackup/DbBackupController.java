package com.vitisvision.vitisvisionservice.common.dbbackup;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Controller to handle database backup operations
 */
@RestController
@RequestMapping("api/v1/db-backups")
@RequiredArgsConstructor
@Tag(name = "Database Backup", description = "APIs to perform database backup operations")
public class DbBackupController {

    /**
     * Service to perform database backup
     */
    private final DbBackupService dbBackupService;

    /**
     * Backup database and store the result in the configured folder
     *
     * @return response with the result of the backup
     */
    @Operation(
            summary = "Backup database",
            description = "Backup database and store the result in the configured folder"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> backupDatabase()
            throws IOException, InterruptedException {
        dbBackupService.performBackup();
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

    /**
     * Get a list of available backups
     *
     * @return response with the list of available backups
     */
    @Operation(
            summary = "Get backups",
            description = "Get a list of file names of available backups in the configured folder"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<DbBackupResponse>>> getBackups() {
        return ResponseEntity.ok(ApiResponse.success(dbBackupService.getBackups(), HttpStatus.OK.value()));
    }
}
