package com.vitisvision.vitisvisionservice.common.dbbackup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Response object for database backups presentation
 */
@Data
@AllArgsConstructor
@Builder
public class DbBackupResponse {

    /**
     * File name of the backup
     */
    private String fileName;
}
