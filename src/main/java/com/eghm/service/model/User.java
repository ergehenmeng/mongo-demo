package com.eghm.service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author 殿小二
 * @date 2020/7/13
 */
@Data
@Document(collection = "User")
public class User implements Serializable {

    @Id
    private Integer id;

    private String name;

    private Integer age;

    private Integer sex;

    private Integer sum;

    private List<Course> courseList;
}

