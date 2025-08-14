<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${not empty item ? 'Edit Item' : 'Add New Item'}</title>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.25em; }
        input, textarea { padding: 0.5em; width: 300px; box-sizing: border-box; }
        button { padding: 0.5em 1em; }
        .error-message { color: red; }
    </style>
</head>
<body>

<p><a href="${pageContext.request.contextPath}/items">Back to Item List</a></p>

<h1>${not empty item ? 'Edit Item' : 'Add New Item'}</h1>

<c:if test="${not empty errorMessage}">
    <p class="error-message">${errorMessage}</p>
</c:if>

<form action="${pageContext.request.contextPath}/items" method="post">

    <c:if test="${not empty item}">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="itemId" value="<c:out value='${item.itemId}' />" />
    </c:if>

    <div class="form-group">
        <label for="itemName">Item Name (Book Title):</label>
        <input type="text" id="itemName" name="itemName" value="<c:out value='${item.itemName}' />" required>
    </div>
    <div class="form-group">
        <label for="description">Description:</label>
        <textarea id="description" name="description" rows="3"><c:out value='${item.description}' /></textarea>
    </div>
    <div class="form-group">
        <label for="price">Price:</label>
        <input type="number" id="price" name="price" value="<c:out value='${item.price}' />" step="0.01" min="0" required>
    </div>
    <div class="form-group">
        <label for="stockQuantity">Stock Quantity:</label>
        <input type="number" id="stockQuantity" name="stockQuantity" value="<c:out value='${item.stockQuantity}' />" min="0" required>
    </div>

    <button type="submit">${not empty item ? 'Update Item' : 'Add Item'}</button>
</form>

</body>
</html>