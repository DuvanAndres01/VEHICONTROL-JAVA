package com.vehicontrol.servlet;

import com.vehicontrol.dao.MovimientoDAO;
import com.vehicontrol.model.Movimiento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet encargado de gestionar el historial de movimientos
 * registrados en el sistema VEHICONTROL.
 *
 * Permite consultar las entradas y salidas de vehículos
 * almacenadas en la base de datos y enviar dicha información
 * a la vista historial.jsp.
 *
 * URL:
 * /historial
 *
 * @author Duvan Arias
 */
@WebServlet("/historial")
public class HistorialServlet extends HttpServlet {

    /**
     * DAO encargado de realizar las consultas relacionadas
     * con los movimientos de los vehículos.
     */
    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    /**
     * Verifica si existe una sesión activa con un usuario
     * autenticado en el sistema.
     *
     * @param request solicitud HTTP actual
     * @return true si existe una sesión con usuario;
     *         false en caso contrario
     */
    private boolean usuarioAutenticado(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        return session != null
                && session.getAttribute("usuario") != null;
    }

    /**
     * Atiende las solicitudes GET del módulo de historial.
     *
     * Verifica la autenticación del usuario, consulta los
     * movimientos registrados mediante MovimientoDAO y
     * envía la información a la vista historial.jsp.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error durante
     *         el procesamiento de la solicitud
     * @throws IOException si ocurre un error de entrada o salida
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica que el usuario tenga una sesión activa.
        if (!usuarioAutenticado(request)) {

            // Si no está autenticado, se redirige al formulario de login.
            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        /*
         * Consulta todos los movimientos registrados.
         * El DAO se encarga de realizar la consulta a la
         * base de datos.
         */
        List<Movimiento> movimientos =
                movimientoDAO.listar();

        /*
         * Se almacena la lista de movimientos como atributo
         * de la solicitud para que pueda ser utilizada
         * desde historial.jsp mediante Expression Language.
         */
        request.setAttribute(
                "movimientos",
                movimientos
        );

        // Envía los datos a la vista del historial.
        request.getRequestDispatcher(
                "/historial.jsp"
        ).forward(request, response);
    }
}