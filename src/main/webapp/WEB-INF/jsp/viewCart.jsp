<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<c:if test="${member != null}">
	
	<h1>${member.memberName}'s cart</h1>

	<div>
		<table>
			<thead class="thead-dark">
				<tr>
					<th scope="col">Tool Id</th>
					<th scope="col">Tool Name</th>
					<th scope="col">Tool Description</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${tools}" var="tool">
					<tr>
						<td>${tool.toolId}</td>
						<td>${tool.toolName}</td>
						<td>${tool.toolDescription}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<c:url value="/checkOut" var="formAction" />
	<form action="${formAction}" method="POST">
		<div id="submitButtonDiv">
			<input id="formSubmitButton" type="submit" value="Check Out Cart" />
		</div>
	</form>
	
	<div>
		<c:url var="availableToolHref" value="/availableToolList" />
		<a href="${availableToolHref}">Add More Tools to Cart</a>
	</div>
	
</c:if>

<c:if test="${member == null}">
	<h1>No Patron Selected</h1>
</c:if>

<c:import url="/WEB-INF/jsp/footer.jsp" />