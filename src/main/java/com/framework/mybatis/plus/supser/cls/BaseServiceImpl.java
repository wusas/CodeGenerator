/**
 * Copyright @2019/2/19 songxiaolong All Rights Reserved
 */
package com.framework.mybatis.plus.supser.cls;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author xiaolong.song
 * @Package com.framework.mybatis.plus
 * @Description: 顶级 Service 用来替换 mybatis-plus IService接口
 * @email loye.song@foxmail.com
 * @date 2019/2/19 17:16
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseIService<T> {

    /**
     * 根据 entity 条件，删除记录
     *
     * @param t 实体
     */
    @Override
    public boolean remove(T t) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>(t);
        return remove(updateWrapper);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param t 实体对象
     */
    @Override
    public T getOne(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return getOne(queryWrapper);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param t 实体对象
     */
    @Override
    public Map<String, Object> getMap(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return getMap(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param t 实体对象
     */
    @Override
    public int count(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return count(queryWrapper);
    }

    /**
     * 查询列表
     *
     * @param t 实体对象
     */
    @Override
    public List<T> list(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return list(queryWrapper);
    }

    /**
     * 翻页查询
     *
     * @param page 翻页对象
     * @param t    实体对象
     */
    @Override
    public IPage<T> page(IPage<T> page, T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return page(page, queryWrapper);
    }

    /**
     * 查询列表
     *
     * @param t 实体对象
     */
    @Override
    public List<Map<String, Object>> listMaps(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return listMaps(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param t      实体对象
     * @param mapper 转换函数
     */
    @Override
    public <V> List<V> listObjs(T t, Function<? super Object, V> mapper) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return listObjs(t, mapper);
    }

    /**
     * 翻页查询
     *
     * @param page 翻页对象
     * @param t    实体对象
     */
    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<T> page, T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return pageMaps(page, queryWrapper);
    }
}
