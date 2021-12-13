package com.example.demo.excel;

import java.util.Date;

public class ExcelData {

    private Integer id;

    private String name;

    private Character sex;

    private Double score;

    private Date bornDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getBornDay() {
        return bornDay;
    }

    public void setBornDay(Date bornDay) {
        this.bornDay = bornDay;
    }

    public ExcelData(Integer id, String name, Character sex, Double score, Date bornDay) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.score = score;
        this.bornDay = bornDay;
    }
}
