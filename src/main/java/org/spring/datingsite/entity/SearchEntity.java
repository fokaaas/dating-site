package org.spring.datingsite.entity;

public class SearchEntity {
    private String keyword;
    private Integer minAge;
    private Integer maxAge;
    private String location;
    private String sex;

    public SearchEntity(String keyword, Integer minAge, Integer maxAge, String location, String sex) {
        this.keyword = keyword;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.location = location;
        this.sex = sex;
    }

    public SearchEntity() {}

    public String getKeyword() {
        return keyword;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public String getLocation() {
        return location;
    }

    public String getSex() {
        return sex;
    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
