<%@ page import="ru.factus.bo.fetch.FetchedData" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  final FetchedData data = (FetchedData) request.getAttribute("data");
%>
<li class="phrase"><h4><%= data.getTitle()%></h4><%= data.getText()%></li>