package com.vehicontrol.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet encargado de mostrar el formulario para registrar
 * un nuevo vehículo en el sistema VEHICONTROL.
 *
 * Este servlet verifica que el usuario se encuentre autenticado
 * antes de permitir el acceso al formulario de registro.
 *
 * El procesamiento y almacenamiento del vehículo se realiza
 * posteriormente mediante VehiculoServlet.
 *
 * URL:
 * /nuevo-vehiculo
 *
 * @author Duvan Arias
 */
@WebServlet("/nuevo-vehiculo")
public class NuevoVehiculoServlet extends HttpServlet {

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

        // Obtiene la sesión existente sin crear una nueva.
        HttpSession session =
                request.getSession(false);

        // Comprueba que exista la sesión y que contenga
        // información del usuario autenticado.
        return session != null
                && session.getAttribute("usuario") != null;
    }

    /**
     * Atiende las solicitudes GET utilizadas para mostrar
     * el formulario de registro de un nuevo vehículo.
     *
     * Si el usuario no está autenticado, se redirige al
     * formulario de inicio de sesión.
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

            // Si no está autenticado, se redirige al login.
            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        /*
         * Si el usuario está autenticado, se carga el formulario
         * que permite ingresar los datos del nuevo vehículo.
         */
        request.getRequestDispatcher(
                "/nuevoVehiculo.jsp"
        ).forward(request, response);
    }
}