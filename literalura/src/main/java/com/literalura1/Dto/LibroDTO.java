package com.literalura1.Dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LibroDTO {

    private String title;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("download_count")
    private Integer downloadCount;

    @SerializedName("authors")
    private List<AutorDTO> authors;

    public String getTitle() {
        return title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public List<AutorDTO> getAuthors() {
        return authors;
    }
}
