<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Beer Test</title>
</head>
<body>
    <h1>This is my beer travel!</h1>
    <br><br>

    ${resultMessage}
    <br><br><br>
    ${beerNames}
    <br><br>
    Program took: ${time}
    <form:form action="goBack">
        <input type="submit" value="Go Back" class="button">
    </form:form>
</body>
</html>
