/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package pxf.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author pxf 2018/3/16 0016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "User")
@XmlType(propOrder = {"id","name","age"})
public class User {

    private Integer id;

    private String name;

    private String age;

    public User() {
    }

    public User(Integer id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
