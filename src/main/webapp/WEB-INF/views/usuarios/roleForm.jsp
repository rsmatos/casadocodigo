<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tags:pageTemplate titulo="Cadastro de Permissões">

	<div class="container">
		<h1>Cadastro de Permissões para ${usuarioDto.nome }</h1>

		<%-- 		<c:forEach items="${roles }" var="role"> --%>
		<%-- 		<p>${role.nome }</p> --%>
		<form:form action="${s:mvcUrl('UC#editarPermissoes').build() }"
			modelAttribute="usuarioDto" method="post" enctype="multipart/form-data">
			<p>
				Permissões:
				<form:checkboxes element="li" path="roles" items="${roles }"  />
				<form:hidden path="email" value="${usuarioDto.email}"/>
			</p>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
		<%-- 		</c:forEach> --%>
	</div>

</tags:pageTemplate>