package cn.ann.bean;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-26 13:18
 */
public class QueryVo {
    private User user;
    private int[] ids;
    private List<Integer> idList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}
