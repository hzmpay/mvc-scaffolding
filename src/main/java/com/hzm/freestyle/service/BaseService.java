package com.hzm.freestyle.service;

import com.github.pagehelper.Page;
import com.hzm.freestyle.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基础BaseService实现
 *
 * @author Hezeming
 * @version 1.0
 * @date 2019年08月05日
 */
@Slf4j
public abstract class BaseService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    //
    /**
     * 批量插入数量
     */
    private static final Integer BATCH_SAVE_NUM = 50;

    /**
     * 获取sql工厂
     *
     * @param
     * @return org.apache.ibatis.session.SqlSessionFactory
     * @author Hezeming
     */
    private SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionTemplate.getSqlSessionFactory();
    }

    /**
     * 批量新增方法，默认分批插入的条数为50
     *
     * @param str  sql
     * @param list 对象集合
     * @return void
     * @author Hezeming
     */
    protected <T> void batchSave(String str, List<T> list) {
        batchSave(str, list, BATCH_SAVE_NUM);
    }

    /**
     * 批量新增方法
     *
     * @param str          sql
     * @param list         对象集合
     * @param batchSaveNum 分批插入的条数，如：50，内部自动按照50切分插入，一次性提交
     * @return void
     * @author Hezeming
     */
    protected <T> void batchSave(String str, List<T> list, Integer batchSaveNum) {
        final int size = list.size();
        // 执行批量插入数量的次数
        int count = size / batchSaveNum;
        SqlSession sqlSession = null;
        try {
            final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int index = 0;
            for (int i = 0; i < count; i++) {
                sqlSession.insert(str, list.subList(index, index += batchSaveNum));
            }
            // 最后余数进行插入
            if (size % batchSaveNum != 0) {
                sqlSession.insert(str, list.subList(index, size));
            }
            sqlSession.commit();
        } catch (Exception e) {
            log.error("批量插入sql执行异常", e);
            sqlSession.rollback();
            throw e;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    /**
     * 新增方法
     *
     * @param str
     * @param obj
     * @return int
     * @author Hezeming
     */
    protected int save(String str, Object obj) {
        return sqlSessionTemplate.insert(str, obj);
    }

    /**
     * 修改方法
     *
     * @param str
     * @param obj
     * @return int
     * @author Hezeming
     */
    protected int update(String str, Object obj) {
        return sqlSessionTemplate.update(str, obj);
    }

    /**
     * 删除方法
     *
     * @param str
     * @param obj
     * @return int
     * @author Hezeming
     */
    protected int delete(String str, Object obj) {
        return sqlSessionTemplate.delete(str, obj);
    }

    /**
     * 查询单个对象方法
     *
     * @param str
     * @param obj
     * @return T
     * @author Hezeming
     */
    protected <T> T findForObject(String str, Object obj) {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 查询单个对象方法
     *
     * @param str
     * @return T
     * @author Hezeming
     */
    protected <T> T findForObject(String str) {
        return sqlSessionTemplate.selectOne(str);
    }

    /**
     * 查询List对象方法
     *
     * @param str
     * @param obj
     * @return java.util.List<T>
     * @author Hezeming
     */
    protected <T> List<T> findForList(String str, Object obj) {
        return sqlSessionTemplate.selectList(str, obj);
    }

    /**
     * 查询List对象方法
     *
     * @param str
     * @return java.util.List<T>
     * @author Hezeming
     */
    protected <T> List<T> findForList(String str) {
        return sqlSessionTemplate.selectList(str);
    }

    /**
     * 分页调用 <br>
     * 如果一个Controller中有两个分页则需先调用 {@link #pageClear()} 方法，<br>
     * 再进行 PageHelper.startPage(pageNum, pageSize) 设置 <br>
     *
     * @param count 是否需要进行count查询，默认为false,请看 {@link #page(int pageNum, int pageSize)}
     * @author Hezeming
     */
    protected Page page(int pageNum, int pageSize, Boolean count) {
        return PageResult.page(pageNum, pageSize, count);
    }

    /**
     * 分页调用 <br>
     * 如果一个Controller中有两个分页则需先调用 {@link #pageClear()} 方法，<br>
     * 再进行 PageHelper.startPage(pageNum, pageSize) 设置 <br>
     *
     * @author Hezeming
     */
    protected Page page(int pageNum, int pageSize) {
        return PageResult.page(pageNum, pageSize, true);
    }

    /**
     * 移除PageHelper中的分页变量副本
     *
     * @param continuePage 是否需要继续分页
     * @author Hezeming
     */
    protected void pageClear(boolean continuePage, int pageNum, int pageSize) {
        PageResult.pageClear(continuePage, pageNum, pageSize);
    }

    /**
     * 移除PageHelper中的分页变量副本
     *
     * @author Hezeming
     */
    protected void pageClear() {
        PageResult.pageClear();
    }
}
