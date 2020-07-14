package com.eghm.service.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 殿小二
 * @date 2020/7/13
 */
@Data
public class Course implements Serializable {

    private String courseName;

    private Integer score;
}
