<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>Techsis, List Users</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="owl-carousel/owl.carousel.css">
<link rel="stylesheet" href="owl-carousel/owl.theme.css">
<link href="http://fonts.googleapis.com/css?family=Arimo:400,700"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
</head>
<body class="size-960">
	<c:import url="/WEB-INF/menuconnexion.jsp" />
	<!-- HEADER -->
	<header>
		<div class="line">
			<div class="box2">
				<div class="s-6 l-2">
					<img src="img/logo.png" alt="logo">
				</div>
				<h6 align="right">
					<strong>Tel. 06.87.16.94.64</strong>
				</h6>
				<h6 align="right">
					<strong>Mail : <a href="mailto:contact@techsis.fr">contact@techsis.fr</a></strong>
				</h6>
			</div>
		</div>
		<!-- TOP NAV -->
		<div class="line">
			<nav class="margin-bottom">
				<p class="nav-text">Menu</p>
				<div class="top-nav s-12 l-10">
					<c:import url="/WEB-INF/menu.jsp" />
				</div>
			</nav>
		</div>
	</header>
	<section>
		<!-- HOME PAGE BLOCK -->
		<div class="line">
			<div class="margin">
				<div class="s-12 l-12 margin-bottom">
					<div class="box">
						<c:choose>
							<c:when test="${ empty sessionScope.user }">
								<p>Aucun utilisateur enregistré.</p>
							</c:when>
							<c:otherwise>
								<table>
									<tr>
										<th>Nom</th>
										<th>Prénom</th>
										<th>Email</th>
										<th>Newsletter</th>
										<th>Action</th>
									</tr>
									<c:forEach items="${ sessionScope.user }" var="mapUsers"
										varStatus="boucle">
										<tr>
											<td><c:out value="${ mapUsers.value.firstName }" /></td>
											<td><c:out value="${ mapUsers.value.lastName }" /></td>
											<td><c:out value="${ mapUsers.value.email }" /></td>
											<td><c:choose>
													<c:when test="${ mapUsers.value.news == 0}">
														<p>Non</p>
													</c:when>
													<c:otherwise>
														<p>Oui</p>
													</c:otherwise>
												</c:choose></td>
											<td><a
												href="<c:url value="/delUser"><c:param name="idUser" value="${ mapUsers.value.id }" /></c:url>">
													<input type="submit" value="Delete">
											</a></td>
										</tr>
									</c:forEach>
								</table>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- FOOTER -->
	<footer class="line">
		<div class="box">
			<div class="s-12 l-6">
				<p>2015 Techsis, Tous droits réservés</p>
			</div>
			<div class="s-12 l-6">
				<p class="right">Site Web par DECOMBE Sylvain et BINET Alexandre</p>
			</div>
		</div>
	</footer>
</body>
</html>