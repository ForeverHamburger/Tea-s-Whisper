package com.xuptggg.navigation.model;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class NavigationInfo {
    public NavigationInfo(String fragmentName, Fragment fragment) {
        this.fragmentName = fragmentName;
        this.fragment = fragment;
    }

    private String fragmentName;
    private Fragment fragment;

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "NavigationInfo{" +
                "fragmentName='" + fragmentName + '\'' +
                ", fragment=" + fragment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavigationInfo that = (NavigationInfo) o;
        return Objects.equals(fragmentName, that.fragmentName) && Objects.equals(fragment, that.fragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fragmentName, fragment);
    }
}
