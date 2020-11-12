package com.example.wewatchapp.Model;

public class SliderSide {
    public String video_slide, video_type,video_thumb, video_url,video_name
            ,video_extension,duration, video_description,video_category;

    public SliderSide(String video_slide,String video_type, String video_thumb, String video_url, String video_name, String video_extension,
                      String duration, String video_description, String video_category) {

        this.video_slide = video_slide;
        this.video_type = video_type;
        this.video_thumb = video_thumb;
        this.video_url = video_url;
        this.video_name = video_name;
        this.video_extension = video_extension;
        this.duration = duration;
        this.video_description = video_description;
        this.video_category = video_category;
    }

    public SliderSide(String video_thumb, String video_name) {
        this.video_thumb = video_thumb;
        this.video_name = video_name;
    }

    public String getVideo_slide() {
        return video_slide;
    }

    public void setVideo_slide(String video_slide) {
        this.video_slide = video_slide;
    }

    public SliderSide() {
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_extension() {
        return video_extension;
    }

    public void setVideo_extension(String video_extension) {
        this.video_extension = video_extension;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_category() {
        return video_category;
    }

    public void setVideo_category(String video_category) {
        this.video_category = video_category;
    }
}
