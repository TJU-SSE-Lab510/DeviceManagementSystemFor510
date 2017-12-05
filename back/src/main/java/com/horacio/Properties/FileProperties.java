package com.horacio.Properties;

import com.horacio.utils.UploadUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String imagePath;

    private String imageUrl;

    private String facility;

    private String user;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserPath() {
        return this.imagePath+ UploadUtil.separatar+this.user;
    }

    public String getFacilityPath() {
        return this.imagePath+ UploadUtil.separatar+this.facility;
    }

    public String getFacilityUrl(){
        return this.imageUrl+this.facility;
    }

    public String getUserUrl(){
        return this.imageUrl+this.user;
    }

}
