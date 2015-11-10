package com.flaremars.servlet;

import com.flaremars.common.Pageable;
import com.flaremars.data.BitmapDataHelper;
import com.flaremars.entity.BitmapObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/9
 */
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String curPageStr = req.getParameter("curPage");
        int curPage;
        if (curPageStr == null) {
            curPage = 1;
        } else {
            curPage = Integer.parseInt(curPageStr);
        }

        Pageable pageable = Pageable.createPageableRequest(curPage,Pageable.DEFAULT_SIZE_PER_PAGE);
        System.out.println(pageable);
        List<BitmapObject> list = BitmapDataHelper.INSTANCE.getBitmaps(pageable);
        req.setAttribute("bitmapObjects",list);
        req.setAttribute("pageInfo",pageable);

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
}
