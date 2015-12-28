<%@ page import="ru.factus.bo.RelationStore" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.factus.bo.components.AbstractComponent" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8" %>
<div xmlns="http://www.w3.org/1999/xhtml"
     xmlns:b="http://www.backbase.com/b"
     xmlns:s="http://www.backbase.com/s" >
  <%!
    private String print(AbstractComponent c){
      if(c == null)
        return null;
      return c.getName();
    }
  %>
  <%

    List<RelationStore> result = (List<RelationStore>) request.getAttribute("result");
    for (RelationStore store : result) {
      %>
  <%= store.getPhraseMarkup().replaceAll("<entity type=\"(.*)\">([^</entity>]*)</entity>", "<a href='#'>$2</a>")%><br/>
  <hr/>
  <%
    }
  %>
</div>
<entity>dfd</entity> df <entity>dfd</entity>  dfd
<entity type="org">dfd</entity>    fd

Сергей <entity type="person">Ушаков</entity> родился в <entity type="org">Ленинграде</entity> в 1954 году, окончил Ленинградский государственный университет, с 1974 году служил в управлении КГБ СССР по Ленинграду и Ленинградской области, до 2001 года работал <entity type="position">заместителем</entity> начальника управления ФСБ в Санкт-Петербурге и Ленинградской области.
