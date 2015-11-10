<%@ page import="com.flaremars.common.Pageable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="demo" uri="/demo-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>超简易图片库</title>
    <%
        Pageable pageInfo = (Pageable) request.getAttribute("pageInfo");
        int totalSize = pageInfo.getTotalSize();
        int curPage = pageInfo.getCurPage();
        int totalPage = pageInfo.getTotalPage();

    %>
</head>
<body>


<table id="myTable" style="overflow-x: auto; overflow-y: auto" align="center">
    <thead>
    <tr>
        <th align="center">名称</th>
        <th align="center">上传时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${bitmapObjects}" var="obj">
        <tr>
            <td align="center">
                <a href="${obj.url}" >${obj.name}</a>
            </td>
            <td align="center">${obj.createTime}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>
<p align="center">
    共<%=totalSize%>个记录,分<%=totalPage%>页显示,当前页是:第<%=curPage%>页<br />
        <%if(curPage>1){%><a href="http://localhost:8080/WebDemo?curPage=1">首页</a><%}%>
        <%if(curPage>1){%><a href="http://localhost:8080/WebDemo?curPage=<%=curPage-1%>">上一页</a><%}%>
        <%
                for(int j=1;j<=totalPage;j++) {
                    out.print("  <a href='http://localhost:8080/WebDemo?curPage="+j+"'>"+j+"</a>");
                }
          %>

        <%if(curPage<totalPage){%><a href="http://localhost:8080/WebDemo?curPage=<%=curPage+1%>">下一页</a><%}%>
        <%if(totalPage>1){%><a href="http://localhost:8080/WebDemo?curPage=<%=totalPage%>">末页</a><%}%>
</body>
</html>
