package com.flaremars.servlet;

import com.flaremars.bean.SimpleBitmapBean;
import com.flaremars.common.Pageable;
import com.flaremars.data.BitmapDataHelper;
import com.flaremars.data.ProjectConstants;
import com.flaremars.entity.BitmapObject;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/9
 */
public class UploadServlet extends HttpServlet {

    private static final String RESULT_CODE = "statusCode";

    private static final String RESULT_MESSAGE = "message";

    private static final String RESULT_DATA = "data";

    private static final int SUCCESS_CODE = 200;
    private static final int FAIL_CODE = 210;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = ProjectConstants.BASE_DIR;
        factory.setRepository(new File(path));
        factory.setSizeThreshold(4 * 1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(factory);

        JSONObject result = new JSONObject();
        try {
            File receivedFile = null;
            result.put(RESULT_CODE, SUCCESS_CODE);
            result.put(RESULT_MESSAGE, "upload success");

            try {
                List<FileItem> list = upload.parseRequest(new ServletRequestContext(req));
                for (FileItem item : list) {
                    String name = item.getFieldName();
                    if (item.isFormField()) {
                        String value = item.getString();
                        System.out.println(name + " " + value);
                    } else {
                        String value = item.getName();
                        int start = value.lastIndexOf("\\");
                        String filename = value.substring(start + 1);
                        receivedFile = new File(path, filename);
                        OutputStream out = new FileOutputStream(receivedFile);
                        InputStream in = item.getInputStream();

                        int length = 0;
                        byte[] buf = new byte[1024];

                        while ((length = in.read(buf)) != -1) {
                            out.write(buf, 0, length);
                        }
                        in.close();
                        out.close();
                    }
                }

                BitmapDataHelper.INSTANCE.insertBitmapObject(receivedFile);

                List<SimpleBitmapBean> allObjects = BitmapDataHelper.INSTANCE.getAllBitmaps();
                result.put(RESULT_DATA,allObjects);
            } catch (Exception e) {
                e.printStackTrace();
                result.put(RESULT_CODE, FAIL_CODE);
                result.put(RESULT_MESSAGE, e.getMessage());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PrintWriter ou = resp.getWriter();
        ou.println(result.toString());
        ou.flush();
        ou.close();
    }
}
