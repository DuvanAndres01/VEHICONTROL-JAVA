package com.vehicontrol.servlet;

import com.vehicontrol.dao.ConfiguracionDAO;
import com.vehicontrol.model.Configuracion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet encargado de gestionar la configuración general
 * del sistema VEHICONTROL.
 *
 * Permite consultar la configuración almacenada y procesar
 * las modificaciones realizadas desde el formulario de configuración.
 *
 * La información es gestionada mediante ConfiguracionDAO y
 * presentada en la vista configuracion.jsp.
 *
 * URL principal:
 * /configuracion
 *
 * @author Duvan Arias
 */
@WebServlet("/configuracion")
public class ConfiguracionServlet extends HttpServlet {

    /**
     * Objeto DAO utilizado para consultar y actualizar
     * la configuración del sistema.
     */
    private final ConfiguracionDAO configuracionDAO =
            new ConfiguracionDAO();

    /**
     * Verifica si existe una sesión activa con un usuario autenticado.
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
     * Atiende las solicitudes GET del módulo de configuración.
     *
     * Primero verifica que el usuario esté autenticado.
     * Posteriormente consulta la configuración almacenada y
     * la envía a la vista configuracion.jsp.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al procesar
     *         la solicitud
     * @throws IOException si ocurre un error de entrada o salida
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica que el usuario tenga una sesión activa.
        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Obtiene la configuración almacenada en la base de datos.
        Configuracion configuracion =
                configuracionDAO.obtener();

        // Envía la configuración a la vista JSP.
        request.setAttribute(
                "configuracion",
                configuracion
        );

        // Carga la página de configuración.
        request.getRequestDispatcher(
                "/configuracion.jsp"
        ).forward(request, response);
    }

    /**
     * Atiende las solicitudes POST utilizadas para guardar
     * los cambios realizados en la configuración.
     *
     * Obtiene los valores enviados desde el formulario,
     * construye un objeto Configuracion y solicita al DAO
     * su actualización en la base de datos.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error durante
     *         el procesamiento
     * @throws IOException si ocurre un error de entrada o salida
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica que el usuario esté autenticado.
        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Permite procesar correctamente caracteres especiales
        // enviados desde el formulario.
        request.setCharacterEncoding("UTF-8");

        try {

            // Obtiene el identificador de la configuración.
            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            // Obtiene los datos generales del conjunto.
            String nombreConjunto =
                    request.getParameter(
                            "nombreConjunto"
                    );

            String direccion =
                    request.getParameter(
                            "direccion"
                    );

            String telefono =
                    request.getParameter(
                            "telefono"
                    );

            // Obtiene la capacidad de parqueaderos.
            int capacidad =
                    Integer.parseInt(
                            request.getParameter(
                                    "capacidadParqueaderos"
                            )
                    );

            /*
             * Los campos checkbox envían un valor solamente
             * cuando se encuentran seleccionados.
             */
            boolean registroAutomatico =
                    request.getParameter(
                            "registroAutomatico"
                    ) != null;

            boolean notificaciones =
                    request.getParameter(
                            "notificaciones"
                    ) != null;

            boolean controlVisitantes =
                    request.getParameter(
                            "controlVisitantes"
                    ) != null;

            boolean controlParqueaderos =
                    request.getParameter(
                            "controlParqueaderos"
                    ) != null;

            // Obtiene las opciones relacionadas con la sesión.
            int tiempoSesion =
                    Integer.parseInt(
                            request.getParameter(
                                    "tiempoSesion"
                            )
                    );

            int intentosLogin =
                    Integer.parseInt(
                            request.getParameter(
                                    "intentosLogin"
                            )
                    );

            // Obtiene las preferencias regionales del sistema.
            String idioma =
                    request.getParameter(
                            "idioma"
                    );

            String zonaHoraria =
                    request.getParameter(
                            "zonaHoraria"
                    );

            String formatoFecha =
                    request.getParameter(
                            "formatoFecha"
                    );

            /*
             * Construye el objeto Configuracion utilizando
             * los valores recibidos desde el formulario.
             */
            Configuracion configuracion =
                    new Configuracion();

            configuracion.setId(id);

            configuracion.setNombreConjunto(
                    nombreConjunto
            );

            configuracion.setDireccion(
                    direccion
            );

            configuracion.setTelefono(
                    telefono
            );

            configuracion.setCapacidadParqueaderos(
                    capacidad
            );

            configuracion.setRegistroAutomatico(
                    registroAutomatico
            );

            configuracion.setNotificaciones(
                    notificaciones
            );

            configuracion.setControlVisitantes(
                    controlVisitantes
            );

            configuracion.setControlParqueaderos(
                    controlParqueaderos
            );

            configuracion.setTiempoSesion(
                    tiempoSesion
            );

            configuracion.setIntentosLogin(
                    intentosLogin
            );

            configuracion.setIdioma(
                    idioma
            );

            configuracion.setZonaHoraria(
                    zonaHoraria
            );

            configuracion.setFormatoFecha(
                    formatoFecha
            );

            // Actualiza la configuración en la base de datos.
            configuracionDAO.actualizar(
                    configuracion
            );

            // Redirige nuevamente al módulo mostrando
            // el indicador de que la información fue guardada.
            response.sendRedirect(
                    request.getContextPath()
                            + "/configuracion"
                            + "?guardado=true"
            );

        } catch (Exception e) {

            // Convierte cualquier error en una excepción
            // controlada por el servlet.
            throw new ServletException(
                    "Error al guardar la configuración",
                    e
            );
        }
    }
}