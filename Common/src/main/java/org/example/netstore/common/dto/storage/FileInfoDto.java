package org.example.netstore.common.dto.storage;

import java.io.File;

public class FileInfoDto extends File {
    private long length;
    private boolean isDirectory;
    private String parent;


    public FileInfoDto(String pathName, long length, boolean isDirectory, String parent){
        super(pathName);
        this.length = length;
        this.isDirectory = isDirectory;
    }

    public FileInfoDto(File file) {
        super(file.getPath());
        this.length = file.length();
        this.isDirectory = file.isDirectory();
        this.parent = file.getParent();
    }

    @Override
    public long length() {
        return this.length;
    }

    @Override
    public boolean isDirectory() {
        return this.isDirectory;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @Override
    public int compareTo(File o) {
        if(this.isDirectory() && !o.isDirectory()){
            return -1;
        } else if(!this.isDirectory && o.isDirectory()) {
            return 1;
        }
        return this.getName().compareTo(o.getName());
    }
}
