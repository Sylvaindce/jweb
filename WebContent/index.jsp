<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>Techsis, Vente Smartphone ANDROID</title>
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
		<!-- CAROUSEL -->
		<div class="line">
			<div id="owl-demo" class="owl-carousel owl-theme  margin-bottom">
				<div class="item">
					<img src="img/940x380.jpg" alt="">
				</div>
				<div class="item">
					<img src="img/940x380-2.jpg" alt="">
				</div>
				<div class="item">
					<img src="img/940x380-3.jpg" alt="">
				</div>
			</div>
		</div>
		<!-- HOME PAGE BLOCK -->
		<div class="line">
			<div class="margin">
				<div class="s-12 l-6 margin-bottom">
					<div class="box">
						<h2>Dernieres News</h2>
						<c:choose>
							<c:when test="${ empty sessionScope.news }">
								<p>Aucunes newsletters enregistrées.</p>
							</c:when>
							<c:otherwise>
								<c:forEach items="${ sessionScope.news }" var="mapNews"
									varStatus="boucle" end="1">
									<h4 style="color: black">
										<c:out value="${ mapNews.value.title }" />
									</h4>
									<p>
										<c:out value="${ mapNews.value.content }" />
									</p>
									<br>
									<hr color="black">
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="s-12 l-6 margin-bottom">
					<div class="box">
						<h2>Derniers avis</h2>
						<c:choose>
							<c:when test="${ empty sessionScope.review }">
								<p>Aucuns retours enregistrées.</p>
							</c:when>
							<c:otherwise>
								<c:forEach items="${ sessionScope.review }" var="mapReviews"
									varStatus="boucle" end="1">
									<h4 style="color: black">
										<c:out value="${ mapReviews.value.author }" />
									</h4>
									<p>
										<c:out value="${ mapReviews.value.content }" />
									</p>
									<h4 style="color: red">
										<c:out value="${ mapReviews.value.note } / 5" />
									</h4>
									<br>
									<hr color="black">
								</c:forEach>
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
	<script type="text/javascript" src="owl-carousel/owl.carousel.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#owl-demo").owlCarousel({
				navigation : true,
				slideSpeed : 300,
				paginationSpeed : 400,
				autoPlay : true,
				singleItem : true
			});
		});
	</script>
</body>
</html>