<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

	
<h1>Welcome to the Tool Borrowing Library!</h1>


<div class="container">
  <h3>View some of the tools in our collection!</h3>  
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <div class="carousel-inner">
      <div class="item active">
        <img class="slidePic" src="img/buzzsaw.png" alt="pic1">
      </div>

      <div class="item">
        <img class="slidePic" src="img/hammer.jpg" alt="pic2">
      </div>
    
      <div class="item">
        <img class="slidePic" src="img/PowerDrills.jpg" alt="pic3">
      </div>
    </div>

    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>

<h1>About the company</h1>
<p> Stuff about the company </p>


<c:import url="/WEB-INF/jsp/footer.jsp" />