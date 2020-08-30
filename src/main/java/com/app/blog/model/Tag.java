package com.app.blog.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date tagCreatedAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date tagUpdatedAt;

    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
    Set<PostTag> postTags = new HashSet<>();

    public Tag() {
    }

    public Tag(String tagName, Date tagCreatedAt, Date tagUpdatedAt, Set<PostTag> postTags) {
        this.tagName = tagName;
        this.tagCreatedAt = tagCreatedAt;
        this.tagUpdatedAt = tagUpdatedAt;
        this.postTags = postTags;
    }

    public Date getTagUpdatedAt() {
        return tagUpdatedAt;
    }

    public void setTagUpdatedAt(Date tagUpdatedAt) {
        this.tagUpdatedAt = tagUpdatedAt;
    }

    public Set<PostTag> getPostTags() {
        return postTags;
    }

    public void setPostTags(Set<PostTag> postTags) {
        this.postTags = postTags;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getTagCreatedAt() {
        return tagCreatedAt;
    }

    public void setTagCreatedAt(Date tagCreatedAt) {
        this.tagCreatedAt = tagCreatedAt;
    }
}
