<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${not empty promotion ? 'Edit Promotion' : 'Add New Promotion'}</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .form-group {
            margin-bottom: 1em;
        }

        label {
            display: block;
            margin-bottom: 0.25em;
            font-weight: bold;
        }

        input {
            padding: 0.5em;
            width: 300px;
            box-sizing: border-box;
        }

        input[type="checkbox"] {
            width: auto;
        }

        button {
            padding: 0.5em 1em;
        }
    </style>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/promotions">Back to Promotion List</a></p>

<h1>${not empty promotion ? 'Edit Promotion' : 'Add New Promotion'}</h1>

<form action="${pageContext.request.contextPath}/promotions" method="post">

    <%-- If we are editing, include hidden fields for the action and the ID --%>
    <c:if test="${not empty promotion}">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="promoId" value="<c:out value='${promotion.promoId}' />"/>
    </c:if>

    <div class="form-group">
        <label for="promoCode">Promo Code:</label>
        <input type="text" id="promoCode" name="promoCode" value="<c:out value='${promotion.promoCode}' />" required>
    </div>
    <div class="form-group">
        <label for="discountPercentage">Discount (%):</label>
        <input type="number" id="discountPercentage" name="discountPercentage"
               value="<c:out value='${promotion.discountPercentage}' />" step="0.1" min="0" max="100" required>
    </div>
    <div class="form-group">
        <label for="startDate">Start Date (Optional):</label>
        <input type="date" id="startDate" name="startDate" value="<c:out value='${promotion.startDate}' />">
    </div>
    <div class="form-group">
        <label for="endDate">End Date (Optional):</label>
        <input type="date" id="endDate" name="endDate" value="<c:out value='${promotion.endDate}' />">
    </div>
    <div class="form-group">
        <%-- JSTL logic to correctly check the box if the promotion is active --%>
        <input type="checkbox" id="isActive"
               name="isActive" ${ (empty promotion or promotion.active) ? 'checked' : '' }>
        <label for="isActive" style="display: inline-block;">Is Active</label>
    </div>

    <button type="submit">${not empty promotion ? 'Update Promotion' : 'Add Promotion'}</button>
</form>

</body>
</html>