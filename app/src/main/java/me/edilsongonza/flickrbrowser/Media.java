package me.edilsongonza.flickrbrowser;

/**
 * Created by Edilson Gonzalez on 19/05/2015.
 */
public class Media {
    private String m;

    public String getLinkToMedia() {
        return m.replaceAll("_m", "");
    }

    public String getLinkToMobileMedia() {
        return m;
    }

    public void setLinkToMedia(String mLinkToMedia) {
        this.m = mLinkToMedia;
    }
}
