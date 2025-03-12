package com.xuptggg.forum.publish.model;

import android.net.Uri;

import java.util.List;
import java.util.Objects;

public class PublishInfo {
    private String publishTitle;
    private String publishContent;
    private List<Uri> uriList;

    public PublishInfo(String publishTitle, String publishContent, List<Uri> uriList) {
        this.publishTitle = publishTitle;
        this.publishContent = publishContent;
        this.uriList = uriList;
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

    public List<Uri> getUriList() {
        return uriList;
    }

    public void setUriList(List<Uri> uriList) {
        this.uriList = uriList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishInfo that = (PublishInfo) o;
        return Objects.equals(publishTitle, that.publishTitle) && Objects.equals(publishContent, that.publishContent) && Objects.equals(uriList, that.uriList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publishTitle, publishContent, uriList);
    }
}