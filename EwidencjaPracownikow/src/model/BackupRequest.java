package model;

public class BackupRequest {
    public enum Mode { SAVE, RESTORE }
    public final Mode mode;
    public final BackupCompression compression; // przy RESTORE: AUTO
    public final String fileName;

    public BackupRequest(Mode mode, BackupCompression compression, String fileName) {
        this.mode = mode;
        this.compression = compression;
        this.fileName = fileName;
    }
}
