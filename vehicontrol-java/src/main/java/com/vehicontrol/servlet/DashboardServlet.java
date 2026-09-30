package com.vehicontrol.servlet;

import com.vehicontrol.dao.ConfiguracionDAO;
import com.vehicontrol.dao.MovimientoDAO;
import com.vehicontrol.dao.VehiculoDAO;
import com.vehicontrol.model.Configuracion;
import com.vehicontrol.model.Movimiento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    private final VehiculoDAO vehiculoDAO =
            new VehiculoDAO();

    private final ConfiguracionDAO configuracionDAO =
            new ConfiguracionDAO();


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        // ==========================================
        // VERIFICAR SESIÓN
        // ==========================================

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("usuario") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }


        // ==========================================
        // ESTADÍSTICAS
        // ==========================================

        int entradas =
                movimientoDAO.contarEntradas();

        int salidas =
                movimientoDAO.contarSalidas();

        int totalVehiculos =
                vehiculoDAO.listar().size();

        int vehiculosDentro =
                movimientoDAO.contarVehiculosDentro();


        // ==========================================
        // CONFIGURACIÓN
        // ==========================================

        Configuracion configuracion =
                configuracionDAO.obtener();


        int capacidadParqueaderos = 120;

        if (configuracion != null
                && configuracion.getCapacidadParqueaderos() > 0) {

            capacidadParqueaderos =
                    configuracion.getCapacidadParqueaderos();
        }


        // ==========================================
        // ESPACIOS DISPONIBLES
        // ==========================================

        int espaciosDisponibles =
                capacidadParqueaderos
                        - vehiculosDentro;


        if (espaciosDisponibles < 0) {
            espaciosDisponibles = 0;
        }


        // ==========================================
        // PORCENTAJE DE OCUPACIÓN
        // ==========================================

        int porcentajeOcupacion = 0;

        if (capacidadParqueaderos > 0) {

            porcentajeOcupacion =
                    (int) Math.round(
                            (
                                vehiculosDentro * 100.0
                            )
                            / capacidadParqueaderos
                    );
        }


        // ==========================================
        // ÚLTIMOS MOVIMIENTOS
        // ==========================================

        List<Movimiento> movimientos =
                movimientoDAO.listar();


        int limite = Math.min(
                5,
                movimientos.size()
        );


        List<Movimiento> ultimosMovimientos =
                movimientos.subList(
                        0,
                        limite
                );


        // ==========================================
        // ENVIAR DATOS AL JSP
        // ==========================================

        request.setAttribute(
                "entradas",
                entradas
        );

        request.setAttribute(
                "salidas",
                salidas
        );

        request.setAttribute(
                "totalVehiculos",
                totalVehiculos
        );

        request.setAttribute(
                "vehiculosDentro",
                vehiculosDentro
        );

        request.setAttribute(
                "capacidadParqueaderos",
                capacidadParqueaderos
        );

        request.setAttribute(
                "espaciosDisponibles",
                espaciosDisponibles
        );

        request.setAttribute(
                "porcentajeOcupacion",
                porcentajeOcupacion
        );

        request.setAttribute(
                "ultimosMovimientos",
                ultimosMovimientos
        );


        // ==========================================
        // MOSTRAR DASHBOARD
        // ==========================================

        request.getRequestDispatcher(
                "/dashboard.jsp"
        ).forward(
                request,
                response
        );
    }
}