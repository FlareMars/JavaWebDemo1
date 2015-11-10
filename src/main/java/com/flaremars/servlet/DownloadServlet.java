package com.flaremars.servlet;

import com.flaremars.data.BitmapDataHelper;
import com.flaremars.entity.BitmapObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by FlareMars on 2015/11/9
 */
public class DownloadServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("bitmapId");
            if (id != null && !id.equals("")) {

                BitmapObject object = BitmapDataHelper.INSTANCE.getBitmapObject(id);
                assert object != null;
                System.out.println(object.getPath());
                response.setContentType("image/jpg");
                response.setHeader("Location", object.getName());
                response.setHeader("Content-Disposition", "attachment; filename=" + object.getName());
                OutputStream outputStream = response.getOutputStream();
                InputStream inputStream = new FileInputStream(object.getPath());
                byte[] buffer = new byte[1024];
                int i = -1;
                while ((i = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, i);
                }
                outputStream.flush();
                outputStream.close();
            } else {
                //返回一张默认图片
            }
        } catch(FileNotFoundException e1) {
            System.out.println("FileNotFoundException");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
