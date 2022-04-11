/**
 * Copyright @2019/2/19 songxiaolong All Rights Reserved
 */
package com.framework.mybatis.plus.supser.cls;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author wusha
 * @Package com.framework.mybatis.plus
 */
public interface BaseIService<T> extends IService<T> {

    public boolean remove(T t);

    public T getOne(T t);

    public Map<String, Object> getMap(T t);

    public int count(T t);

    public List<T> list(T t);

    public IPage<T> page(IPage<T> page, T t);

    default IPage<T> getPage(Page page, T t) {
        page.setTotal(Long.valueOf(count(t)));
        if (null != t)
            return page(page, t);
        else
            return page(page);
    }

    default IPage<T> getPage(int current) {
        Page<T> page = new Page<>();
        page.setCurrent(current);
        return getPage(page, null);
    }

    default IPage<T> getPage(int current, T t) {
        Page<T> page = new Page<>();
        page.setCurrent(current);
        return getPage(page, t);
    }

    public List<Map<String, Object>> listMaps(T t);

    public <V> List<V> listObjs(T t, Function<? super Object, V> mapper);

    public IPage<Map<String, Object>> pageMaps(IPage<T> page, T t);
}
