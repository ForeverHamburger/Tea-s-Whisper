package com.xuptggg.forum.thread.model;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ThreadInfo{

    @SerializedName("post_id")
    private Long PostId;

    @SerializedName("title")
    private String Title;

    @SerializedName("url")
    private List<String> Url;

    @SerializedName("content")
    private String Content;

    @SerializedName("author_id")
    private Long AuthorId;

    @SerializedName("author_name")
    private String AuthorName;

    @SerializedName("author_url")
    private String AuthorUrl;

    @SerializedName("votes")
    private String Votes;

    @SerializedName("collection")
    private String Collection;

    @SerializedName("comments")
    private String Comments;

    @SerializedName("is_vote")
    private String IsVote;

    @SerializedName("is_collect")
    private String IsCollect;

    public Long getPostId() {
        return PostId;
    }

    public void setPostId(Long postId) {
        PostId = postId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<String> getUrl() {
        return Url;
    }

    public void setUrl(List<String> url) {
        Url = url;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(Long authorId) {
        AuthorId = authorId;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getAuthorUrl() {
        return AuthorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        AuthorUrl = authorUrl;
    }

    public String getVotes() {
        return Votes;
    }

    public void setVotes(String votes) {
        Votes = votes;
    }

    public String getCollection() {
        return Collection;
    }

    public void setCollection(String collection) {
        Collection = collection;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getIsVote() {
        return IsVote;
    }

    public void setIsVote(String isVote) {
        IsVote = isVote;
    }

    public String getIsCollect() {
        return IsCollect;
    }

    public void setIsCollect(String isCollect) {
        IsCollect = isCollect;
    }

    @Override
    public String toString() {
        return "PostData{" +
                "PostId=" + PostId +
                ", Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                ", Content='" + Content + '\'' +
                ", AuthorId=" + AuthorId +
                ", AuthorName='" + AuthorName + '\'' +
                ", AuthorUrl='" + AuthorUrl + '\'' +
                ", Votes='" + Votes + '\'' +
                ", Collection='" + Collection + '\'' +
                ", Comments='" + Comments + '\'' +
                ", IsVote='" + IsVote + '\'' +
                ", IsCollect='" + IsCollect + '\'' +
                '}';
    }
}