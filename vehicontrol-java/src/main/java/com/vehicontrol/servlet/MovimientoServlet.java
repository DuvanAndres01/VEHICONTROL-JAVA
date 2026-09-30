package com.vehicontrol.servlet;

import com.vehicontrol.dao.MovimientoDAO;
import com.vehicontrol.dao.VehiculoDAO;
import com.vehicontrol.model.Movimiento;
import com.vehicontrol.model.Vehiculo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/movimientos")
public class MovimientoServlet extends HttpServlet {

    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    private final VehiculoDAO vehiculoDAO =
            new VehiculoDAO();


    // =========================================================
    // VALIDAR SESIÓN
    // =========================================================

    private boolean usuarioAutenticado(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        return session != null
                && session.getAttribute("usuario") != null;
    }


    // =========================================================
    // GET
    // =========================================================

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

        cargarPagina(request, response);
    }


    // =========================================================
    // POST
    // =========================================================

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


        // =====================================================
        // OBTENER DATOS
        // =====================================================

        String vehiculoParametro =
                request.getParameter("vehiculoId");

        String tipo =
                request.getParameter("tipo");

        String observacion =
                request.getParameter("observacion");


        // =====================================================
        // VALIDAR VEHÍCULO
        // =====================================================

        if (vehiculoParametro == null
                || vehiculoParametro.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Debe seleccionar un vehículo."
            );

            cargarPagina(request, response);

            return;
        }


        int vehiculoId;

        try {

            vehiculoId =
                    Integer.parseInt(
                            vehiculoParametro
                    );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "El vehículo seleccionado no es válido."
            );

            cargarPagina(request, response);

            return;
        }


        // =====================================================
        // VALIDAR TIPO
        // =====================================================

        if (tipo == null
                || tipo.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Debe seleccionar el tipo de movimiento."
            );

            cargarPagina(request, response);

            return;
        }

        tipo =
                tipo.trim().toUpperCase();


        if (!"ENTRADA".equals(tipo)
                && !"SALIDA".equals(tipo)) {

            request.setAttribute(
                    "error",
                    "El tipo de movimiento no es válido."
            );

            cargarPagina(request, response);

            return;
        }


        // =====================================================
        // VALIDAR VEHÍCULO EXISTENTE
        // =====================================================

        Vehiculo vehiculo =
                vehiculoDAO.buscarPorId(
                        vehiculoId
                );

        if (vehiculo == null) {

            request.setAttribute(
                    "error",
                    "El vehículo seleccionado no existe."
            );

            cargarPagina(request, response);

            return;
        }


        // =====================================================
        // CREAR MOVIMIENTO
        // =====================================================

        Movimiento movimiento =
                new Movimiento(
                        vehiculoId,
                        tipo,
                        observacion
                );


        // =====================================================
        // REGISTRAR
        // =====================================================

        try {

            movimientoDAO.registrar(
                    movimiento
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/movimientos"
                            + "?guardado=true"
            );

        } catch (RuntimeException e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            cargarPagina(
                    request,
                    response
            );
        }
    }


    // =========================================================
    // CARGAR PÁGINA
    // =========================================================

    private void cargarPagina(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Movimiento> movimientos =
                movimientoDAO.listar();

        List<Vehiculo> vehiculos =
                vehiculoDAO.listar();

        request.setAttribute(
                "movimientos",
                movimientos
        );

        request.setAttribute(
                "vehiculos",
                vehiculos
        );

        request.getRequestDispatcher(
                "/movimientos.jsp"
        ).forward(
                request,
                response
        );
    }
}