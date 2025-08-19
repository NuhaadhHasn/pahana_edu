<%-- /webapp/login.jsp --%>
<%-- Note: We now include the standard header --%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="row justify-content-center align-items-center" style="min-height: 70vh;">
    <div class="col-md-6 col-lg-4">
        <%-- We use the 'main-content' class from our style.css to get the glass effect --%>
        <div class="main-content shadow-lg">
            <div class="text-center mb-4">
                <i class="bi bi-book-half" style="font-size: 3rem;"></i>
                <h1 class="h3">System Login</h1>
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <c:out value="${errorMessage}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username"
                           required>
                    <label for="username">Username</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                           required>
                    <label for="password">Password</label>
                </div>
                <div class="d-grid mt-4">
                    <button type="submit" class="btn btn-primary btn-lg">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- Include the standard footer --%>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>