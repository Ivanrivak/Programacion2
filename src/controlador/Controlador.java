package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Contador;
import modelo.Intento;

/**
 * Servlet implementation class Controlador
 */
//@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Intento> listajuego;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controlador() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int aleatorio(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer inferior = null, superior = null;
		Random rn = new Random();
		try {
			inferior = Integer.parseInt(request.getParameter("inferior"));
			superior = Integer.parseInt(request.getParameter("superior"));
		} catch (NumberFormatException e) {
			return 0;
		} finally {
			session.setAttribute("inferior", inferior);
			session.setAttribute("superior", superior);
		}
		if (inferior > superior)
			return 0;
		else
			return rn.nextInt((superior - inferior) + 1) + inferior;
	}

	public Intento jugada(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Contador orden = (Contador) session.getAttribute("orden");
		if (orden == null) {
			// no existe esa variable en sesión , la creo
			orden = new Contador();
			session.setAttribute("orden", orden);
		}
		Contador numeroJugada = (Contador) session.getAttribute("orden");
		numeroJugada.inc();
		LocalDate fechaHora = LocalDate.now();
		int numeroAportado;
		try {
			numeroAportado = Integer.parseInt(request.getParameter("adivinar"));
		} catch (NumberFormatException e) {
			return null;
		}
		int numeroAleatorio = (int) session.getAttribute("numeroAleatorio");
		String mensaje = mensaje(request,numeroAleatorio, numeroAportado);
		Intento jugada = new Intento(fechaHora, numeroJugada.getContador(), numeroAportado, mensaje);
		return jugada;
	}

	public String mensaje(HttpServletRequest request,int numeroAleatorio, int numeroAportado) {
		HttpSession session = request.getSession();
		String mensaje = "";
		if (numeroAleatorio > numeroAportado)
			mensaje = "Introduce un valor superior";
		else if (numeroAleatorio < numeroAportado)
			mensaje = "Introduce un valor inferior";
		else if (numeroAleatorio == numeroAportado) {
			mensaje = "Valor Introducido correcto";
			session.setAttribute("final", "final");
		}
		return mensaje;
	}

	public String rango(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String rutaJSP = "/";
		String inf = request.getParameter("inferior");
		String sup = request.getParameter("superior");
		if (inf != "" && sup != "") {
			int numero = aleatorio(request);
			if (numero == 0)
				request.setAttribute("Error", "Error en algún parametro");
			else {
				System.out.println("Parametros aceptados");
				session.setAttribute("numeroAleatorio", numero);
				System.out.println("numero Aleatorio: " + numero);
				return rutaJSP = "/juego.jsp";
			}
		} else
			request.setAttribute("Error", "Parametro/s Incorrecto/s");
		return rutaJSP;
	}

	public String adivinar(HttpServletRequest request) {
		String rutaJSP = "/juego.jsp";
		Intento jugada = jugada(request);
		if (jugada != null) {
			listajuego.add(jugada);
			return rutaJSP;
		} else
			request.setAttribute("Error", "Valor Introducido no válido");
		return rutaJSP;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		listajuego = (ArrayList<Intento>) session.getAttribute("listaJuego");
		String juego = request.getParameter("juego");
		String rutaJSP = "/";
		if (listajuego == null) {
			listajuego = new ArrayList<Intento>();
			session.setAttribute("listaJuego", listajuego);
			System.out.println("lista creada");
		}
		if (juego != null) {
			switch (juego) {
			case "rango":
				rutaJSP = rango(request);
				break;
			case "adivinar":
				rutaJSP = adivinar(request);
				break;
			case "reset":
				rutaJSP = "/index.jsp";
				session.removeAttribute("orden");
				listajuego.clear();
				session.removeAttribute("final");
				break;
			case "cancelar":
				rutaJSP = "/index.jsp";
				session.invalidate();
				break;
			}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(rutaJSP);
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
