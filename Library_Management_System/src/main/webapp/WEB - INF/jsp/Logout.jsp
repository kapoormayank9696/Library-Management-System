<!-- Logout Library Management System By User -->
<%@ page language = "java" contentType="text/html; charset="UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
    <head>
        <title>
            Logout by User
        </title>
        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
                background-color: lightcoral;
                color: white;
            }
        </style>
    </head>
    <body>
        <%
        HttpSession mySession = request.getSession(true);
        
        %>
    </body>
</html>

