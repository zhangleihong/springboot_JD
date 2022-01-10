package com.example.springboot_init.po;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class Search {

    private String title;

    private Integer page;

    private Boolean pageOrSearch;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getPageOrSearch() {
        return pageOrSearch;
    }

    public void setPageOrSearch(Boolean pageOrSearch) {
        this.pageOrSearch = pageOrSearch;
    }

    @Override
    public String toString() {
        return "Search{" +
                "title='" + title + '\'' +
                ", page=" + page +
                ", pageOrSearch=" + pageOrSearch +
                '}';
    }
}
