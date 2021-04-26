package com.hzm.freestyle.result;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.io.Serializable;
import java.util.List;


/**
 * 自定义返回分页对象
 *
 * @author Hezeming
 * @version 1.0
 * @date 2018年5月31日
 */
public class PageResult<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //
    /**
     * 当前页码
     */
    private Integer curPage;
    /**
     * 每页显示数量
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer pages;
    //
    /**
     * 结果集
     */
    private List<T> list;

    private PageResult() {
    }

    public PageResult(Integer curPage, Integer pageSize, Long total, Integer pages, List<T> list) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.list = list;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResult [curPage=" + curPage + ", pageSize=" + pageSize + ", total=" + total + ", pages=" + pages + ", list=" + list + "]";
    }
    //
    /**
     * 实例化Page对象
     *
     * @param page
     * @return
     * @author Hezeming
     * @see com.github.pagehelper.Page
     */
    public static <E> PageResult<E> init(Page<E> page) {
        return new PageResult(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getResult());
    }

    /**
     * 分页调用 <br>
     * 如果一个调用链中有两个分页则需先调用 {@link #pageClear()} 方法，<br>
     * 再进行 PageHelper.startPage(pageNum, pageSize) 设置 <br>
     *
     * @param count 是否需要进行count查询，默认为false,请看 {@link #page(int pageNum, int pageSize)}
     * @author Hezeming
     */
    public static Page page(int curPage, int pageSize, Boolean count) {
        return PageHelper.startPage(curPage, pageSize, count);
    }

    /**
     * 分页调用 <br>
     * 如果一个调用链中有两个分页则需先调用 {@link #pageClear()} 方法，<br>
     * 再进行 PageHelper.startPage(pageNum, pageSize) 设置 <br>
     *
     * @author Hezeming
     */
    public static Page page(int curPage, int pageSize) {
        return page(curPage, pageSize, true);
    }

    /**
     * 移除PageHelper中的分页变量副本
     *
     * @param continuePage 是否需要继续分页
     * @author Hezeming
     */
    public static void pageClear(boolean continuePage, int curPage, int pageSize) {
        pageClear();
        if (continuePage) {
            PageHelper.startPage(curPage, pageSize);
        }
    }

    /**
     * 移除PageHelper中的分页变量副本
     *
     * @author Hezeming
     */
    public static void pageClear() {
        PageHelper.clearPage();
    }
}
