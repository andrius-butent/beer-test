<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Beer Test</title>
</head>
<body>
    <h1>It's the weekend! Beer time.</h1>
    <form:form action="startBeerTravel" modelAttribute="myCoordinates">

        <b>Latitude</b>
        <br>
        <form:input path="latitude" cssClass="input" />
        <br><br>

        <b>Longitude</b>
        <br>
        <form:input path="longitude" cssClass="input" />
        <br><br>

        <input type="submit" value="Let's travel!" class="button">
    </form:form>
</body>
</html>
