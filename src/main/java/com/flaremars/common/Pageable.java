package com.flaremars.common;

/**
 * Created by FlareMars on 2015/11/9
 */
public class Pageable {

    public static final int DEFAULT_SIZE_PER_PAGE = 10;

    private int totalPage;

    private int curPage;

    private int sizePerPage;

    private int totalSize;

    private Pageable() {
        totalPage = 0;
        sizePerPage = DEFAULT_SIZE_PER_PAGE;
        totalSize = 0;
        curPage = 1;
    }

    public static Pageable createPageableRequest(int curPage,int sizePerPage) {
        Pageable pageable = new Pageable();
        pageable.curPage = curPage;
        pageable.sizePerPage = sizePerPage;
        return pageable;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "totalPage=" + totalPage +
                ", curPage=" + curPage +
                ", sizePerPage=" + sizePerPage +
                ", totalSize=" + totalSize +
                '}';
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getSizePerPage() {
        return sizePerPage;
    }

    public void setSizePerPage(int sizePerPage) {
        this.sizePerPage = sizePerPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
