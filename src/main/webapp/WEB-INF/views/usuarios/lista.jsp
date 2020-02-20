<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<c:url value="/" var="contextPath" />

<tags:pageTemplate titulo="Lista de Usuários">

	<div class="container">
		<a href="${s:mvcUrl('UC#form').build()}">Novo Usuário</a>

		<h1>Lista de Usuários</h1>

		<p>${message}</p>

		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Roles</th>
				<th></th>
			</tr>
			<c:forEach items="${usuarios }" var="user">
				<tr>
					<td>${user.nome }</td>
					<td>${user.email }</td>
					<td>${user.roles }</td>
					<td><form:form
							action="${s:mvcUrl('UC#listarRoles').arg(0, user.email).build() }"
							method="post" cssClass="container">
							<input type="image"
								src="${contextPath }resources/imagens/adicionar.png" />
						</form:form></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</tags:pageTemplate>