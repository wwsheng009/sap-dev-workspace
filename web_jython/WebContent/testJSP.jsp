<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<form method="GET" action="jy/add_to_page.py">
            <input type="text" name="p">
            <input type="submit">
        </form>
        <br/>
        <p>${page_text}</p>
        <br/>
        <form method="GET" action="jy/add_numbers.py">
            <input type="text" name="x">
            +
            <input type="text" name="y">
            =
            ${sum}
            <br/>
            <input type="submit" title="Add Numbers">
        </form>
</body>
</html>