package top.devonte.note.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public class NoteFile {

    private Integer id;
    private Integer folderId;
    private int type;
    private String title;
    private String body;
    @JsonDeserialize(as = Boolean.class)
    private Boolean topping;
    @JsonDeserialize(as = Boolean.class)
    private Boolean foldered;
    private Date created;
    private Date updated;
    private Date topped;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isTopping() {
        return topping;
    }

    public void setTopping(boolean topping) {
        this.topping = topping;
    }

    public boolean isFoldered() {
        return foldered;
    }

    public void setFoldered(boolean foldered) {
        this.foldered = foldered;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getTopped() {
        return topped;
    }

    public void setTopped(Date topped) {
        this.topped = topped;
    }
}
