<%@ page import="org.apache.solr.common.SolrDocument" %>
<%@ page import="org.apache.solr.common.SolrDocumentList" %>
<%@ page import="org.springframework.util.StringUtils" %>
<%@ page import="ru.factus.bo.search.FacetItem" %>
<%@ page import="ru.factus.bo.search.FacetItemGroup, ru.factus.utils.Utils, java.util.List, java.util.Map" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  final Map<String, String[]> facets = (Map) request.getAttribute("selected");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>Фактус</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css" href="/style.css"/>
  <script src="http://www.google.com/jsapi"></script>
  <script>google.load("prototype", "1.6.0.2");</script>
  <script type="text/javascript">
    var array = new Hash();

    function more(a, id) {
      new Ajax.Updater(a.parentNode.parentNode, "/more.do", {parameters: "id=" + id});
    }

    function find(className, value){
      var f = $('form');

      var el = document.createElement("input");
      el.type = "hidden";
      el.name = className;
      el.value = value;
      el.checked = true;

      f.appendChild(el);
      f.submit();
    }

    function addTag(el) {
      var parent = el.parentNode;

      var box = parent.getElementsByTagName("input")[0];
      box.checked = !box.checked;
      $('form').submit();
    }

  </script>
</head>
<body>
<table id="main_layout" cellspacing="0">
  <tr>
    <td colspan="2" id="head">
      <img src="/images/factus.jpg" hspace="4"/>
      <%--<form action="/faseted-search.do" method="post" id="form" onsubmit="prepare(this)">
        <table>
          <tr>
            <td></td>
            <td>
              <input type="text" name="query" id="query_field" value="${param.query}"/>
              <input type="hidden" name="persons"/>
              <input type="hidden" name="orgs"/>
              <input type="hidden" name="posts"/>
              <input type="submit" id="search_button" value="Искать!"/>
            </td>
          </tr>
          <tr>
            <td colspan="2" id="tags"></td>
          </tr>
        </table>
      </form>--%>
    </td>
  </tr>
  <tr>
    <td id="left_column"><form action="/faseted-search.do" method="post" id="form" >
      <div id="top"><%
        int nextId = 0;        

        final List<FacetItemGroup> groups = (List<FacetItemGroup>) request.getAttribute("facets");
        if (groups != null) {
          for (FacetItemGroup group : groups) {
            final List<FacetItem> list = group.getItems();
            if (list == null || list.isEmpty())
              continue;
      %>
        <h1><%
          String name = group.getName();
          Pattern pattern = null;

          final String[] selectedFacets = facets.get(name);
          if(selectedFacets != null){
            pattern = Pattern.compile(StringUtils.arrayToDelimitedString(selectedFacets, "|"));
          }

        %><%= group.getTitle()%> <%--<em style="font-size: 10px; font-weight: normal; color: #ccc;">(<%= group.getValueCount()%>)</em>--%></h1>
        <ul><%
          for (int i = 0; i < list.size() && i < 15; i++) {
            final FacetItem count = list.get(i);
            final boolean find = pattern != null && pattern.matcher(count.getName()).find();
        %>
          <li>
            <input type="checkbox" name="<%=name%>" value="<%= count.getName()%>" style="display: none;" <%= find? "checked=\"checked\"":""%>/>
            <a href="#" onclick="addTag(this);" <%= find? "class=\""+name+"\"":""%>><%= count.getTitle()%></a><span>(<%= count.getCount()%>)</span></li>
          <%
            }
          %></ul>
        <%
            }
          }
        %></div></form>
    </td>
    <td id="right_column"><%
      final SolrDocumentList result = (SolrDocumentList) request.getAttribute("result");
      if (result != null) {
        /*final String queryString = request.getParameter("query");
        if (queryString != null)
          out.append("<h1> Результаты поиска по запросы '").append(queryString).append("...'</h1>");*/

        out.print("<ul style=\"list-style: square\">");
        for (int i = 0; i < result.size(); i++) {
          SolrDocument doc = result.get(i);
          //todo подключить вадика хак для подстановки id
    %>

      <li class="phrase">
        <h2><%= doc.getFieldValue("post_title")%> | <a href="#"><%= Utils.formatTime(doc.getFieldValue("post_date"))%>
        </a></h2>

        <%
          final String phrase = (String) doc.getFieldValue("phrase_markup");
          final Pattern p = Pattern.compile("<entity type=\"([^\"]*)\">([^</]*)</entity>");

          final Matcher m = p.matcher(phrase);
          final StringBuffer buff = new StringBuffer();
          while (m.find()) {
            String type = m.group(1);
            if (type.equals("person")) {
              m.appendReplacement(buff, "<a href=\"javascript: find('persons', '$2')\" class='persons'>$2</a>");
            } else if (type.equals("org")) {
              m.appendReplacement(buff, "<a href=\"javascript: find('orgs', '$2')\" class='orgs'>$2</a>");
            } else if (type.equals("position")) {
              m.appendReplacement(buff, "<a href=\"javascript: find('positions', '$2')\" class='posts'>$2</a>");
            }
          }
          m.appendTail(buff);
          out.print(buff.toString());
        %>
        <%--<div><a href="#" onclick="more(this, <%= doc.getFieldValue("id")%>);" class="more">Больше..</a></div>--%>
      </li>
      <%
          }
          out.print("</ul>");
        }
      %></td>
  </tr>
</table>
</body>
</html>