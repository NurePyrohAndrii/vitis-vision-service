package com.vitisvision.vitisvisionservice.common.dbbackup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Service to handle database backup operations
 */
@Service
public class DbBackupService {

    /**
     * Path to store the backup result
     */
    @Value("${application.backup.result.path}")
    private String backupResultPath;

    /**
     * Name of the backup result file
     */
    @Value("${application.backup.result.name}")
    private String backupResultFileName;

    /**
     * Extension of the backup result file
     */
    @Value("${application.backup.result.extension}")
    private String backupResultFileExtension;

    /**
     * Path to the backup script file
     */
    @Value("${application.backup.script.path}")
    private String backupScriptPath;

    /**
     * Command to perform the backup
     */
    @Value("${application.backup.command}")
    private String backupCommand;

    /**
     * Perform database backup
     *
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the backup process is interrupted
     */
    @PreAuthorize("hasAuthority('admin:db-backup')")
    public void performBackup() throws IOException, InterruptedException {
        String command = buildCommand();
        int exitCode = Runtime.getRuntime().exec(command).waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Backup failed with exit code: " + exitCode);
        }
    }

    /**
     * Get a list of available backups
     *
     * @return list of available backups
     */
    @PreAuthorize("hasAuthority('admin:db-backup')")
    public List<DbBackupResponse> getBackups() {
        File backupFolder = new File(backupResultPath);

        if (!backupFolder.exists() || !backupFolder.isDirectory()) {
            throw new RuntimeException("Backup folder does not exist");
        }

        File[] files = backupFolder.listFiles();

        if (Objects.isNull(files)) {
            throw new RuntimeException("Backup folder is empty");
        }

        return Arrays.stream(files)
                .filter(file -> file.getName().endsWith(backupResultFileExtension))
                .map(file -> DbBackupResponse.builder()
                        .fileName(file.getName())
                        .build())
                .toList();
    }

    /**
     * Build the command to perform the backup
     *
     * @return the command to perform the backup
     */
    private String buildCommand() {
        return backupCommand + " " + backupScriptPath + " " + buildFilePath();
    }

    /**
     * Build the file path for the backup result
     *
     * @return the file path for the backup result
     */
    private String buildFilePath() {
        LocalDateTime now = LocalDateTime.now();
        char separator = '-';
        return backupResultPath + backupResultFileName
                + separator + now.getYear() + separator + now.getMonthValue()
                + separator + now.getDayOfMonth() + separator
                + "T" + now.getHour() + separator + now.getMinute()
                + separator + now.getSecond() + backupResultFileExtension;
    }

}
