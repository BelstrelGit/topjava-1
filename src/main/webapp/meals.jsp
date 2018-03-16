<%--
  Created by IntelliJ IDEA.
  User: belstrel
  Date: 14.03.18
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<head>
    <title>Meal list</title>
    <style>
        .normal{
            color: green;
        }
        .exceeded{
            color: red;
        }
    </style>
</head>
    <body>
      <section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <hr/>
     <table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
         <c:forEach items="${meals}" var="meal">
             <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
             <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>${fn:formatDateTime(meal.dateTime)}</td>
                 <td>${meal.description}</td>
                 <td>${meal.calories}</td>
             </tr>
         </c:forEach>
</table>
</section>
</body>
</html>
