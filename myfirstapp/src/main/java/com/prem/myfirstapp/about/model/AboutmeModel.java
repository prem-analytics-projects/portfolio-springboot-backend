package com.prem.myfirstapp.about.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "about_content", schema = "raw_data")
@Getter @Setter
public class AboutmeModel {

    @Id
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "section_title")
    private String sectionTitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_active")
    private Boolean isActive;
}