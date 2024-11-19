package org.spring.datingsite.user.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
