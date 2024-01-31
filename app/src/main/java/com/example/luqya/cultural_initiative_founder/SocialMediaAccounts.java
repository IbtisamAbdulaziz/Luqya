package com.example.luqya.cultural_initiative_founder;

public class SocialMediaAccounts {

    private String siteName;
    private String profileLink;

    public SocialMediaAccounts(String siteName, String profileLink) {
        this.siteName = siteName;
        this.profileLink = profileLink;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getProfileLink() {
        return profileLink;

    }
}