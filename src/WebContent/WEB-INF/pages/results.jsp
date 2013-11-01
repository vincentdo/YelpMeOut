<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<title>YelpMeOut!</title>
<link rel="stylesheet" href="./css/bootstrap.css" type="text/css">
<script> 
function toggle(thechosenone) {
	var ele = document.getElementById(thechosenone);
		   if(ele.style.display == "block") {
	              $(ele).slideUp(200);
	       }
	       else {
	       	$(ele).slideDown(200);
	       	ele.style.display = "block";
	       }
} 
</script>
<script src="./js/bootstrap.js"></script>
<script src="./js/jquery.js"></script>
</head>
<body>
	<div class="container">
		<div align="right" style="margin-top: 5px;">
			<img src="./images/YMO-Logo.png" alt="logo" title="logo" height="373" width="251"/>
		</div>
	</div>
	<div style="margin-top: -80px;">
		<HR style="border-color: #E61616;">
	</div>
	<div class="container">
		<div class="row">
			<div class="span12">
				<h1 style="color: #E61616;">Your Trip:</h1><br>	
			</div>
		</div>
		<div class="row">
			<div class="span5">
				<table class="table">
					<tr>
				      	<td>
				            <a id="myHeader1-2" href="javascript:toggle('newboxes1-2');">${bus1.name}</a>
				         	<div id="newboxes1-2" style="display: none;">
				         		<br/>
				         		<a href="${link1}" target="_blank">Directions</a>
				         		<p>
				         		${bus1.address}
				         		Walking Distance: ${walk1}
				         		<br/>
				         		Driving Distance: ${drive1}
				         		<p/>
				         		<img src="${ct1}"/>
				         	</div>
				      	</td>
				  	</tr>
				  	<tr>
				      	<td>
				        	<a id="myHeader2-2" href="javascript:toggle('newboxes2-2');">${bus2.name}</a>
				         	<div id="newboxes2-2" style="display: none;">
				         		<br/>
				         		<a href="${link2}" target="_blank">Directions</a>
				         		<p>
				         		${bus2.address}
				         		Walking Distance: ${walk2}
				         		<br/>
				         		Driving Distance: ${drive2}
				         		<p/>
				         		<img src="${ct2}"/>
				         	</div>
				     	</td>
				  	</tr>
				  	<tr>
				      	<td>
				            <a id="myHeader3-2" href="javascript:toggle('newboxes3-2');">${bus3.name}</a>
				         	<div id="newboxes3-2" style="display: none;">
				         		<br/>
				         		<a href="${link3}" target="_blank">Directions</a>
				         		<p>
				         		${bus3.address}
				         		Walking Distance: ${walk3}
				         		<br/>
				         		Driving Distance: ${drive3}
				         		<p/>
				         		<img src="${ct3}"/>
				         	</div>
				      	</td>
				  </tr>
				  <tr>
				      	<td>
				            <a id="myHeader4-2" href="javascript:toggle('newboxes4-2');">${bus4.name}</a>
				         	<div id="newboxes4-2" style="display: none;">
				         		<br/>
				         		<a href="${link4}" target="_blank">Directions</a>
				         		<p>
				         		${bus4.address}
				         		Walking Distance: ${walk4}
				         		<br/>
				         		Driving Distance: ${drive4}
				         		<p/>
				         		<img src="${ct4}"/>
							</div>
				      	</td>
				  </tr>
				  <tr>
				      	<td>
				            <a id="myHeader5-2" href="javascript:toggle('newboxes5-2');">${bus5.name}</a>
				         	<div id="newboxes5-2" style="display: none;">
				         		<br/>
				         		<a href="${link5}" target="_blank">Directions</a>
				         		<p>
				         		${bus5.address}
				         		Walking Distance: ${walk5}
				         		<br/>
				         		Driving Distance: ${drive5}
				         		<p/>
				         		<img src="${ct5}"/>
				         	</div>
				      	</td>
				  </tr>
				</table>
			</div>
			<div class="span7">
			<img src="${mapurl}"/><br>
		</div>
		</div>

	</div>

</body>
</html>