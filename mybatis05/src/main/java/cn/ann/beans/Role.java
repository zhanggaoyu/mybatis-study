package cn.ann.beans;

import java.io.Serializable;
import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 11:56
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 5322113487542794660L;

    private Integer id;
    private String roleName;

    private List<User> users;

    public Role() {
    }

    public Role(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
