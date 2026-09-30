package com.vehicontrol.servlet;

import com.vehicontrol.dao.UsuarioDAO;
import com.vehicontrol.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet encargado de procesar el inicio de sesión
 * de los usuarios del sistema VEHICONTROL.
 *
 * Recibe las credenciales enviadas desde login.jsp,
 * consulta la información mediante UsuarioDAO y,
 * cuando las credenciales son correctas, crea una
 * sesión para el usuario autenticado.
 *
 * URL:
 * /login
 *
 * @author Duvan Arias
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
     * DAO utilizado para realizar la autenticación
     * de los usuarios contra la base de datos.
     */
    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    /**
     * Procesa las solicitudes POST provenientes del
     * formulario de inicio de sesión.
     *
     * Obtiene el correo y la contraseña enviados,
     * realiza la autenticación y establece la sesión
     * cuando las credenciales son válidas.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error durante
     *         el procesamiento de la solicitud
     * @throws IOException si ocurre un error de entrada o salida
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Permite procesar correctamente caracteres especiales.
        request.setCharacterEncoding("UTF-8");

        // Obtiene las credenciales enviadas desde el formulario.
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        /*
         * Consulta el usuario en la base de datos mediante
         * el método de autenticación del UsuarioDAO.
         */
        Usuario usuario =
                usuarioDAO.autenticar(correo, password);

        // Comprueba si las credenciales son válidas.
        if (usuario != null) {

            /*
             * Crea o recupera la sesión HTTP actual para
             * mantener autenticado al usuario durante
             * su navegación por el sistema.
             */
            HttpSession session =
                    request.getSession();

            // Almacena el usuario autenticado en la sesión.
            session.setAttribute(
                    "usuario",
                    usuario
            );

            /*
             * Después de iniciar sesión correctamente,
             * el usuario es enviado al dashboard principal.
             */
            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard"
            );

        } else {

            /*
             * Si las credenciales no son válidas, se
             * establece un mensaje de error para mostrarlo
             * nuevamente en la página de inicio de sesión.
             */
            request.setAttribute(
                    "error",
                    "Correo o contraseña incorrectos"
            );

            // Regresa al formulario de inicio de sesión.
            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);
        }
    }
}