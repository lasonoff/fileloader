package ru.yauroff.test.fileloader.model;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "fileLocation")
    private String fileLocation;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private FileStatus status;

    public File() {
    }

    public File(String name, String fileLocation, String description, FileStatus status) {
        this.name = name;
        this.fileLocation = fileLocation;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
