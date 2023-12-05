<%@ page language="java" pageEncoding="UTF-8" contentType="text/html" %>
<!DOCTYPE html>
<script>
    function searchTextChanged() {
        document.getElementById("search-button").disabled = document.getElementById("search-input").value === ""
    }

    function submitSearch() {
        window.location.replace('/keyword/' + document.getElementById("search-input").value);
    }
</script>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comment Dictionary</title>
    <link rel="stylesheet" href="/css/general.css">
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>
<jsp:include page="header.jsp" />
<form id="search-form" action="javascript:submitSearch()" >
    <input type="text" id="search-input" onkeyup="searchTextChanged()" maxlength="100" name="keyword"/>
    <button type="submit" id="search-button" disabled>검색</button>
</form>
</body>
</html>