<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Pahana Edu - Login</title>
  <style>
    body { font-family: sans-serif; margin: 2em; }
    .login-container { width: 300px; margin: 0 auto; padding: 2em; border: 1px solid #ccc; }
    .form-group { margin-bottom: 1em; }
    label { display: block; margin-bottom: 0.25em; }
    input { padding: 0.5em; width: 100%; box-sizing: border-box; }
    button { padding: 0.5em 1em; width: 100%; }
    .error-message { color: red; margin-bottom: 1em; }
  </style>
</head>
<body>

<div class="login-container">
  <h2>System Login</h2>

  <%-- Check if an error message was passed from the servlet --%>
  <c:if test="${not empty errorMessage}">
    <p class="error-message">${errorMessage}</p>
  </c:if>

  <%-- The form submits to our LoginController which is mapped to "/login" --%>
  <form action="${pageContext.request.contextPath}/login" method="post">
    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Login</button>
  </form>
</div>

</body>
</html>