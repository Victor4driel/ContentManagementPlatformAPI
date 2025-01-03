package com.cms.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String status = "pending";

    @Column(name = "published_date", nullable = true)
    private Date publishedDate;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "content_category",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "content_tag",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Content(
            String title,
            String body,
            String status,
            Date publishedDate,
            User user,
            List<Category> categories,
            List<Tag> tags
    ) {
        this.title = title;
        this.body = body;
        this.status = status;
        this.publishedDate = publishedDate;
        this.user = user;
        this.categories = categories;
        this.tags = tags;
    }
}
