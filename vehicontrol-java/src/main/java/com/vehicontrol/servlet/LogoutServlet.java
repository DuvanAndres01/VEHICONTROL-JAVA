package com.vehicontrol.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet encargado de cerrar la sesión del usuario
 * en el sistema VEHICONTROL.
 *
 * Invalida la sesión HTTP existente, establece cabeceras
 * para evitar el almacenamiento de páginas protegidas
 * en la caché del navegador y redirige al formulario
 * de inicio de sesión.
 *
 * URL:
 * /logout
 *
 * @author Duvan Arias
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Procesa la solicitud GET utilizada para cerrar
     * la sesión del usuario.
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

        /*
         * Obtiene la sesión existente sin crear una nueva.
         * Si no existe una sesión activa, el método devuelve null.
         */
        HttpSession session =
                request.getSession(false);

        /*
         * Invalida la sesión actual para eliminar
         * la información de autenticación del usuario.
         */
        if (session != null) {
            session.invalidate();
        }

        /*
         * Evita que el navegador almacene en caché las
         * páginas protegidas del sistema después del cierre
         * de sesión.
         */
        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        response.setHeader(
                "Pragma",
                "no-cache"
        );

        response.setHeader(
                "Expires",
                "0"
        );

        /*
         * Redirige al usuario nuevamente al formulario
         * de inicio de sesión.
         */
        response.sendRedirect(
                request.getContextPath()
                        + "/login.jsp"
        );
    }
}