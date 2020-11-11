package com.example.wewatchapp.Model;

public class SliderSide {
    public String   video_slide, video_type, video_thum,
            video_url , video_name, video_description , video_category;

    public SliderSide(String video_slide, String video_type, String video_thum,
                      String video_url, String video_name, String video_description,
                      String video_category) {
        this.video_slide = video_slide;
        this.video_type = video_type;
        this.video_thum = video_thum;
        this.video_url = video_url;
        this.video_name = video_name;
        this.video_description = video_description;
        this.video_category = video_category;
    }

    public SliderSide() {
    }

    public String getVideo_slide() {
        return video_slide;
    }

    public void setVideo_slide(String video_slide) {
        this.video_slide = video_slide;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_thum() {
        return video_thum;
    }

    public void setVideo_thum(String video_thum) {
        this.video_thum = video_thum;
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
