<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row">
    <div class="col-xs-12">
        <div class="box box-warning">
            <div class="box-header with-border">
                <h3 class="box-title">Sửa danh mục</h3>
            </div>
            <!-- /.box-header -->

            <div class="box-body">
                <form:form method="POST" commandName="categories"  role="form"  >
                    <form:hidden path="categoryId"/>
                    <!-- text input -->
                    <div class="form-group">
                        <label>Tên danh mục</label>
                        <form:input  path="categoryName" id="name" type="text" class="form-control" placeholder="Enter ..." />
                    </div

                    <div class="form-group">
                        <label>Trạng thái </label>
                        <form:select  path="isActive"> 
                            <form:option value="0">khóa</form:option>
                            <form:option value="1">hoạt động</form:option>
                        </form:select>
                    </div>
                    <input type="hidden" value="${searchinput}" name="searchinput"/>
                    <c:if test="${not empty listCategories}">
                        <input type="hidden" value="${requestScope.listSearchCategories.current}" name="page"/>
                    </c:if>
                    <c:url value="/Supplier/updateCategory" var="updatecategories"/>
                    <div class="box-footer clearfix">
                        <button class="btn btn-primary" type="submit" onclick="form.action = '<c:out value="${updatecategories}"/>';" >Cập nhật</button>
                    </div>
                </form:form>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
</div>