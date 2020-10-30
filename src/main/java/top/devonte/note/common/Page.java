package top.devonte.note.common;

import java.util.List;

public class Page<T> {

    private List<T> data;
    private int page;
    private int totalPage;
    private int total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static <T> Page<T> of(List<T> data, int pageSize, int total, int page) {
        Page<T> objectPage = new Page<>();
        objectPage.data = data;
        objectPage.page = page;
        objectPage.total = total;
        objectPage.totalPage = total / pageSize;
        return objectPage;
    }
}
