<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inicio</title>
</head>
<body>
	<h1>Juego adivinar número</h1>
	<hr/>
	<h4>Rango</h4>
	<form name="rangoAleatorio" action="logica" method="POST">
	<input type="hidden" name="juego" value="rango">
		Inferior:<input type="text" name="inferior">
		Superior:<input type="text" name="superior">
		<input type="submit" value="confirmar">
	</form>
	<div>
		<% Object error = request.getAttribute("Error"); 
		if(error != null){
			out.print(error);
		}
		%>
		
	</div>
</body>
</html>