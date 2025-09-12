package com.example.wellnessportal.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;

@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;

    private String title;

    // "video" or "article"
    private String type;

    //To hold CLOB kind of data
    @Column(columnDefinition = "TEXT")
    private String content;

    //Mapping each resource to multiple user-defined tags 
    @ElementCollection
    private List<String> resourceTags;

    private String resourceCategory;

    // Constructors
    public Resource() {}

    public Resource(String title, 
                    String type, 
                    String content, 
                    List<String> resourceTags, 
                    String resourceCategory) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.resourceTags = resourceTags;
        this.resourceCategory = resourceCategory;
    }

    // Getters and Setters
    public Long getResourceId() { 
        return resourceId;
     }

    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        
        this.title = title; 
    }

    public String getType() { 
        return type; 
    }
    public void setType(String type) {
         this.type = type;
         }

    public String getContent() { 
        return content;
     }
    public void setContent(String content) { 
        this.content = content;
     }

    public List<String> getResourceTags() { 
        return resourceTags; 
    }
    public void setResourceTags(List<String> resourceTags) {
         this.resourceTags = resourceTags; 
        }

    public String getResourceCategory() { 
        return resourceCategory; 
    }
    public void setResourceCategory(String resourceCategory) { 
        this.resourceCategory = resourceCategory; 
    }
}