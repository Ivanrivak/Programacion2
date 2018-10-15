<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="modelo.Intento"
    import="java.util.*"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Jugando entre <%= session.getAttribute("inferior")%> - <%= session.getAttribute("superior") %> </h3>
	<hr/>
<% String finalizar = (String)session.getAttribute("final");
if( finalizar != "final"){ %>
	<h5>Escribe el número a adivinar</h5>
	<form name="adivinar" action="logica" method="POST">
		<input type="hidden" name="juego" value="adivinar">
		<input type="text" name="adivinar">
		<input type="submit" value="confirmar">
	</form>
	<%} %>
	<form name="reset" action="logica" method="POST">
		<input type="hidden" name="juego" value="reset">
		<input type="submit" value="<% if(finalizar != "final")out.print("Reset");else out.print("Terminar");%>">
	</form>
	<%if(finalizar != "final"){ %>
	<form name="cancelar" action="logica" method="POST">
		<input type="hidden" name="juego" value="cancelar">
		<input type="submit" value="cancelar">
	</form>
	<%} %>
	<div>
		<% Object error = request.getAttribute("Error"); 
		if(error != null){
			out.print(error);
		}
		%>
	</div>
	<div>
		<table>
			<tr>
				<th>INTENTO</th>
				<th>FECHAHORA</th>
				<th>NUMERO</th>
				<th>MENSAJE</th>
			</tr>
			<% ArrayList<Intento> intentos =(ArrayList<Intento>) session.getAttribute("listaJuego");
			int i;
			for(i=0;i<intentos.size();i++){
				out.print("<tr>");
				out.print("<td>"+intentos.get(i).getOrden()+"</td>");
				out.print("<td>"+intentos.get(i).getFechaHora()+"</td>");
				out.print("<td>"+intentos.get(i).getNumeroJugado()+"</td>");
				out.print("<td>"+intentos.get(i).getMensaje()+"</td>");
				out.print("</tr>");
			}
			%>
		</table>
	</div>
	</form>

</body>
</html>