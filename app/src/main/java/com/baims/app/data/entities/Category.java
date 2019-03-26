package com.baims.app.data.entities;

import java.util.List;

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
public class Category {

    private String name;
    private String content;
    private int id;
    private String link;
    private String icon_image;
    private List<SubCategory> subcategories;
    private List<BundleCourse> bundle_courses;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon_image() {
        return icon_image;
    }

    public void setIcon_image(String icon_image) {
        this.icon_image = icon_image;
    }

    public List<SubCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public List<BundleCourse> getBundleCourses() {
        return bundle_courses;
    }

    public void setBundleCourses(List<BundleCourse> bundle_courses) {
        this.bundle_courses = bundle_courses;
    }
}
