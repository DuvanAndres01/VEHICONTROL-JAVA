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

@WebServlet("/historial")
public class HistorialServlet extends HttpServlet {

    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

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

        List<Movimiento> movimientos =
                movimientoDAO.listar();

        request.setAttribute(
                "movimientos",
                movimientos
        );

        request.getRequestDispatcher(
                "/historial.jsp"
        ).forward(request, response);
    }
}