package com.xuptggg.forum.publish.model;

import android.net.Uri;

import java.util.List;
import java.util.Objects;

public class PublishInfo {
    private String publishTitle;
    private String publishContent;
    private List<String> strings;
    private String status;



    public PublishInfo(String publishTitle, String publishContent, List<String> strings, String status) {
        this.publishTitle = publishTitle;
        this.publishContent = publishContent;
        this.strings = strings;
    }

    public String getPublishTitle() {
        return publishTitle;
    }

    public void setPublishTitle(String publishTitle) {
        this.publishTitle = publishTitle;
    }

    public String getPublishContent() {
        return publishContent;
    }

    public void setPublishContent(String publishContent) {
        this.publishContent = publishContent;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishInfo that = (PublishInfo) o;
        return Objects.equals(publishTitle, that.publishTitle) && Objects.equals(publishContent, that.publishContent) && Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publishTitle, publishContent, strings);
    }
}