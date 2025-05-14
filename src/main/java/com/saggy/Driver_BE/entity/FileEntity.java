package com.saggy.Driver_BE.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path;
    private String type;

    private Long size;

    private Long parentFolderId;

    private LocalDateTime createdAt;

    public FileEntity() {
    }

    public FileEntity(LocalDateTime createdAt, Long id, String name, Long parentFolderId, String path, Long size, String type) {
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.parentFolderId = parentFolderId;
        this.path = path;
        this.size = size;
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
