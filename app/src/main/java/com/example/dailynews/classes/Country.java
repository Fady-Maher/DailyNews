package com.example.dailynews.classes;

public class Country {
    private int flagImage;
    private String countryFlag;

    public Country(int flagImage, String countryFlag) {
        this.flagImage = flagImage;
        this.countryFlag = countryFlag;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(int flagImage) {
        this.flagImage = flagImage;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }
}
