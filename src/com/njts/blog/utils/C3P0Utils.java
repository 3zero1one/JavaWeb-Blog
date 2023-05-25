package com.njts.blog.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

/**
 * C3P0���ӳع�����
 * @author weidong
 */
public class C3P0Utils {
    private static ComboPooledDataSource ds;

    static {
        ds = new ComboPooledDataSource("myApp");
    }

    /**
     * ��ȡ����Դ
     * @return ���ӳ�
     */
    public static DataSource getDataSource() {
        return ds;
    }
}
