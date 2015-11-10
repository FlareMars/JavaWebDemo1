package com.flaremars.data;

import com.flaremars.bean.SimpleBitmapBean;
import com.flaremars.common.Pageable;
import com.flaremars.entity.BitmapObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/9
 */
public enum  BitmapDataHelper {
    INSTANCE;

    private static final String url = "jdbc:mysql://localhost:3306/webdemo?"
            + "user=root&password=Wei520Qian1314&useUnicode=true&characterEncoding=UTF8";

    private Connection connectJDBC() {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BitmapObject> getBitmaps(Pageable pageable) {
        List<BitmapObject> result = new ArrayList<BitmapObject>();

        Connection conn = connectJDBC();
        Statement statement = null;
        ResultSet resultSet = null;
        BitmapObject object;
        if (conn != null) {
            try {
                String sql = "SELECT * FROM t_bitmap_object ORDER BY create_time limit " + pageable.getSizePerPage() + " offset " + (pageable.getCurPage() - 1) * pageable.getSizePerPage() ;
                statement = conn.createStatement();
                resultSet = statement.executeQuery(sql);
                if (resultSet != null && resultSet.first()) {
                    resultSet.beforeFirst();
                    while (resultSet.next()) {
                        object = new BitmapObject();
                        object.setCreateTime(resultSet.getString("create_time"));
                        object.setUrl(resultSet.getString("url"));
                        object.setId(resultSet.getString("id"));
                        object.setName(resultSet.getString("name"));
                        result.add(object);
                    }
                }


                Statement countStatement = conn.createStatement();
                String sqlCount = "select count(*)  from t_bitmap_object";
                ResultSet rs = countStatement.executeQuery(sqlCount);
                int rowCount = 0;
                if(rs.next()) {
                    rowCount = rs.getInt(1);
                }
                countStatement.close();
                rs.close();

                pageable.setTotalSize(rowCount);
                pageable.setTotalPage(rowCount / pageable.getSizePerPage() + 1);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public List<SimpleBitmapBean> getAllBitmaps() {
        List<SimpleBitmapBean> result = new ArrayList<SimpleBitmapBean>();

        Connection conn = connectJDBC();
        Statement statement = null;
        ResultSet resultSet = null;
        SimpleBitmapBean object;
        if (conn != null) {
            try {
                String sql = "SELECT name,url FROM t_bitmap_object ORDER BY create_time";
                statement = conn.createStatement();
                resultSet = statement.executeQuery(sql);
                if (resultSet != null && resultSet.first()) {
                    resultSet.beforeFirst();
                    while (resultSet.next()) {
                        object = new SimpleBitmapBean();
                        object.setUrl(resultSet.getString("url"));
                        object.setName(resultSet.getString("name"));
                        result.add(object);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public int insertBitmapObject(File bitmapFile) {
        BitmapObject newBitmap = new BitmapObject();
        newBitmap.setName(bitmapFile.getName());
        newBitmap.setUrl(ProjectConstants.BASE_URL + "WebDemo/download?bitmapId=" + newBitmap.getId());
        newBitmap.setPath(bitmapFile.getPath());

        Connection conn = connectJDBC();
        if (conn != null) {
            int i = 0;
            String sql = "insert into t_bitmap_object (id,url,name,create_time,path) values(?,?,?,?,?)";
            PreparedStatement pStatement;
            try {
                pStatement = conn.prepareStatement(sql);
                pStatement.setString(1, newBitmap.getId());
                pStatement.setString(2, newBitmap.getUrl());
                pStatement.setString(3, newBitmap.getName());
                pStatement.setString(4, newBitmap.getCreateTime());
                pStatement.setString(5, newBitmap.getPath());
                i = pStatement.executeUpdate();
                pStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;
        } else {
            return -1;
        }
    }

    public BitmapObject getBitmapObject(String id) {
        Connection conn = connectJDBC();
        if (conn != null) {
            int i = 0;
            String sql = "select * from t_bitmap_object where id=?";
            PreparedStatement pStatement;
            try {
                pStatement = conn.prepareStatement(sql);
                pStatement.setString(1, id);
                ResultSet rs = pStatement.executeQuery();

                BitmapObject result = null;
                if (rs.first()) {
                    result = new BitmapObject();
                    result.setPath(rs.getString("path"));
                    result.setId(rs.getString("id"));
                    result.setUrl(rs.getString("url"));
                    result.setName(rs.getString("name"));
                    result.setCreateTime(rs.getString("create_time"));
                }
                rs.close();
                pStatement.close();
                conn.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
