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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Usuario usuario =
                usuarioDAO.autenticar(correo, password);

        if (usuario != null) {

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "usuario",
                    usuario
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Correo o contraseña incorrectos"
            );

            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);
        }
    }
}