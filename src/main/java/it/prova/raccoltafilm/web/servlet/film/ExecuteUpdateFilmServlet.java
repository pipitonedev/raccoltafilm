package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteUpdateFilmServlet")
public class ExecuteUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUpdateFilmServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long idFilmParam = Long.parseLong(request.getParameter("idFilm"));
		String titoloInputParam = request.getParameter("titolo");
		String genereInputParam = request.getParameter("genere");
		String dataPubblicazioneInputParam = request.getParameter("dataPubblicazione");
		String minutiDurataInputParam = request.getParameter("minutiDurata");
		String registaInputParam = request.getParameter("regista.id");

		Film filmInstance = UtilityForm.createFilmFromParams(titoloInputParam, genereInputParam, minutiDurataInputParam,
				dataPubblicazioneInputParam, registaInputParam);
		filmInstance.setId(idFilmParam);

		if (!UtilityForm.validateFilmBean(filmInstance)) {
			request.setAttribute("update_film_attr", filmInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/film/update.jsp").forward(request, response);
			return;
		}

		try {
			MyServiceFactory.getFilmServiceInstance().aggiorna(filmInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/update.jsp").forward(request, response);
		}

		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");
	}

}