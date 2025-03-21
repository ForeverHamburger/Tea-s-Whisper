package com.xuptggg.individual.tabitem.model;

public class ForumInfo {
    private String post_id;
    private String title;
    private String url;
    private String author_id;
    private String author_url;
    private String author_name;
    private String votes;

    public ForumInfo(String post_id, String title, String url, String author_id, String author_url, String author_name, String votes) {
        this.post_id = post_id;
        this.title = title;
        this.url = url;
        this.author_id = author_id;
        this.author_url = author_url;
        this.author_name = author_name;
        this.votes = votes;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
