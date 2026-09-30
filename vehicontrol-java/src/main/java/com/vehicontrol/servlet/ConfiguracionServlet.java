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

@WebServlet("/configuracion")
public class ConfiguracionServlet extends HttpServlet {

    private final ConfiguracionDAO configuracionDAO =
            new ConfiguracionDAO();


    private boolean usuarioAutenticado(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        return session != null
                && session.getAttribute("usuario") != null;
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }


        Configuracion configuracion =
                configuracionDAO.obtener();


        request.setAttribute(
                "configuracion",
                configuracion
        );


        request.getRequestDispatcher(
                "/configuracion.jsp"
        ).forward(request, response);
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }


        request.setCharacterEncoding("UTF-8");


        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


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


            int capacidad =
                    Integer.parseInt(
                            request.getParameter(
                                    "capacidadParqueaderos"
                            )
                    );


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


            configuracionDAO.actualizar(
                    configuracion
            );


            response.sendRedirect(
                    request.getContextPath()
                            + "/configuracion"
                            + "?guardado=true"
            );


        } catch (Exception e) {

            throw new ServletException(
                    "Error al guardar la configuración",
                    e
            );
        }
    }
}