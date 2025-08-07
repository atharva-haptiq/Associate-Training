package com.haptiq.secureBlogApp.dto;
import jakarta.validation.constraints.NotBlank;


public class BlogDTO {

    @NotBlank(message = "title is mandatory")
    private String title;
    private String description;
    @NotBlank(message = "content is mandatory")
    private String content;
    @NotBlank(message = "author name is mandatory")
    private String authorFirstName;
    private String authorLastName;

    public BlogDTO() {
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
