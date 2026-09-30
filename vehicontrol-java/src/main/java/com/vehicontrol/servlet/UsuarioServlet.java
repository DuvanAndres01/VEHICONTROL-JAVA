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
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();


    // =========================================================
    // VERIFICAR SESIÓN
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


        String accion =
                request.getParameter("accion");


        // =====================================================
        // EDITAR
        // =====================================================

        if ("editar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");


            if (idParametro == null ||
                idParametro.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;
            }


            try {

                int id =
                        Integer.parseInt(idParametro);


                Usuario usuario =
                        usuarioDAO.buscarPorId(id);


                if (usuario == null) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/usuarios?error=USUARIO_NO_ENCONTRADO"
                    );

                    return;
                }


                request.setAttribute(
                        "usuario",
                        usuario
                );


                request.getRequestDispatcher(
                        "/editarUsuario.jsp"
                ).forward(
                        request,
                        response
                );

                return;

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;
            }
        }


        // =====================================================
        // ELIMINAR
        // =====================================================

        if ("eliminar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");


            if (idParametro == null ||
                idParametro.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;
            }


            try {

                int id =
                        Integer.parseInt(idParametro);


                Usuario usuario =
                        usuarioDAO.buscarPorId(id);


                if (usuario == null) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/usuarios?error=USUARIO_NO_ENCONTRADO"
                    );

                    return;
                }


                // =============================================
                // NO ELIMINAR EL USUARIO DE LA SESIÓN
                // =============================================

                HttpSession session =
                        request.getSession(false);


                Usuario usuarioSesion =
                        (Usuario) session.getAttribute("usuario");


                if (usuarioSesion != null
                        && usuarioSesion.getId() == id) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/usuarios?error=NO_ELIMINAR_SESION"
                    );

                    return;
                }


                // =============================================
                // PROTEGER EL ÚLTIMO ADMINISTRADOR
                // =============================================

                if ("admin".equalsIgnoreCase(
                        usuario.getRol())) {

                    int administradores =
                            usuarioDAO.contarAdministradores();


                    if (administradores <= 1) {

                        response.sendRedirect(
                                request.getContextPath()
                                        + "/usuarios?error=ULTIMO_ADMIN"
                        );

                        return;
                    }
                }


                usuarioDAO.eliminar(id);


                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?guardado=true"
                );

                return;


            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;

            } catch (RuntimeException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ERROR_ELIMINAR"
                );

                return;
            }
        }


        // =====================================================
        // LISTAR
        // =====================================================

        List<Usuario> usuarios =
                usuarioDAO.listar();


        request.setAttribute(
                "usuarios",
                usuarios
        );


        request.getRequestDispatcher(
                "/usuarios.jsp"
        ).forward(
                request,
                response
        );
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


        String accion =
                limpiar(
                        request.getParameter("accion")
                );


        // =====================================================
        // ACTUALIZAR
        // =====================================================

        if ("actualizar".equals(accion)) {

            actualizarUsuario(
                    request,
                    response
            );

            return;
        }


        // =====================================================
        // CREAR
        // =====================================================

        crearUsuario(
                request,
                response
        );
    }


    // =========================================================
    // CREAR USUARIO
    // =========================================================

    private void crearUsuario(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String nombre =
                limpiar(
                        request.getParameter("nombre")
                );


        String correo =
                limpiar(
                        request.getParameter("correo")
                );


        String password =
                limpiar(
                        request.getParameter("password")
                );


        String rol =
                limpiar(
                        request.getParameter("rol")
                );


        String estado =
                limpiar(
                        request.getParameter("estado")
                );


        // =====================================================
        // VALIDAR NOMBRE
        // =====================================================

        if (nombre.isEmpty()) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "El nombre es obligatorio."
            );

            return;
        }


        if (nombre.length() > 100) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "El nombre no puede superar los 100 caracteres."
            );

            return;
        }


        // =====================================================
        // VALIDAR CORREO
        // =====================================================

        if (correo.isEmpty()) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "El correo es obligatorio."
            );

            return;
        }


        if (!correoValido(correo)) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "Ingrese un correo electrónico válido."
            );

            return;
        }


        if (usuarioDAO.existeCorreo(correo)) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "Ya existe un usuario registrado con el correo "
                            + correo
                            + "."
            );

            return;
        }


        // =====================================================
        // VALIDAR CONTRASEÑA
        // =====================================================

        if (password.isEmpty()) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "La contraseña es obligatoria."
            );

            return;
        }


        if (password.length() < 6) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "La contraseña debe tener mínimo 6 caracteres."
            );

            return;
        }


        // =====================================================
        // VALIDAR ROL
        // =====================================================

        if (!rolValido(rol)) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "El rol seleccionado no es válido."
            );

            return;
        }


        // =====================================================
        // VALIDAR ESTADO
        // =====================================================

        if (!estadoValido(estado)) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "El estado seleccionado no es válido."
            );

            return;
        }


        // =====================================================
        // CREAR USUARIO
        // =====================================================

        Usuario usuario =
                new Usuario();


        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setPassword(password);
        usuario.setRol(rol.toLowerCase());
        usuario.setEstado(estado.toLowerCase());


        try {

            usuarioDAO.insertar(usuario);


            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?guardado=true"
            );


        } catch (RuntimeException e) {

            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "No fue posible registrar el usuario."
            );
        }
    }


    // =========================================================
    // ACTUALIZAR USUARIO
    // =========================================================

    private void actualizarUsuario(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String idParametro =
                limpiar(
                        request.getParameter("id")
                );


        if (idParametro.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?error=ID_INVALIDO"
            );

            return;
        }


        int id;


        try {

            id =
                    Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?error=ID_INVALIDO"
            );

            return;
        }


        Usuario existente =
                usuarioDAO.buscarPorId(id);


        if (existente == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?error=USUARIO_NO_ENCONTRADO"
            );

            return;
        }


        String nombre =
                limpiar(
                        request.getParameter("nombre")
                );


        String correo =
                limpiar(
                        request.getParameter("correo")
                );


        String rol =
                limpiar(
                        request.getParameter("rol")
                );


        String estado =
                limpiar(
                        request.getParameter("estado")
                );


        // =====================================================
        // VALIDAR NOMBRE
        // =====================================================

        if (nombre.isEmpty()) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "El nombre es obligatorio."
            );

            return;
        }


        if (nombre.length() > 100) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "El nombre no puede superar los 100 caracteres."
            );

            return;
        }


        // =====================================================
        // VALIDAR CORREO
        // =====================================================

        if (correo.isEmpty()) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "El correo es obligatorio."
            );

            return;
        }


        if (!correoValido(correo)) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "Ingrese un correo electrónico válido."
            );

            return;
        }


        if (usuarioDAO.existeCorreoExceptoId(
                correo,
                id)) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "Ya existe otro usuario con el correo "
                            + correo
                            + "."
            );

            return;
        }


        // =====================================================
        // VALIDAR ROL
        // =====================================================

        if (!rolValido(rol)) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "El rol seleccionado no es válido."
            );

            return;
        }


        // =====================================================
        // VALIDAR ESTADO
        // =====================================================

        if (!estadoValido(estado)) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "El estado seleccionado no es válido."
            );

            return;
        }


        // =====================================================
        // PROTEGER ÚLTIMO ADMINISTRADOR
        // =====================================================

        if ("admin".equalsIgnoreCase(existente.getRol())) {

            boolean dejaDeSerAdmin =
                    !"admin".equalsIgnoreCase(rol);


            boolean quedaInactivo =
                    !"activo".equalsIgnoreCase(estado);


            if (dejaDeSerAdmin || quedaInactivo) {

                int administradores =
                        usuarioDAO.contarAdministradores();


                if (administradores <= 1) {

                    mostrarErrorEditarUsuario(
                            request,
                            response,
                            existente,
                            "No puede desactivar o quitar el rol del único administrador del sistema."
                    );

                    return;
                }
            }
        }


        // =====================================================
        // ACTUALIZAR
        // =====================================================

        Usuario usuario =
                new Usuario();


        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setRol(rol.toLowerCase());
        usuario.setEstado(estado.toLowerCase());


        try {

            usuarioDAO.actualizar(usuario);


            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?guardado=true"
            );


        } catch (RuntimeException e) {

            mostrarErrorEditarUsuario(
                    request,
                    response,
                    existente,
                    "No fue posible actualizar el usuario."
            );
        }
    }


    // =========================================================
    // VALIDAR CORREO
    // =========================================================

    private boolean correoValido(
            String correo) {

        String regex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern
                .matches(regex, correo);
    }


    // =========================================================
    // VALIDAR ROL
    // =========================================================

    private boolean rolValido(
            String rol) {

        return "admin".equalsIgnoreCase(rol)
                || "vigilante".equalsIgnoreCase(rol);
    }


    // =========================================================
    // VALIDAR ESTADO
    // =========================================================

    private boolean estadoValido(
            String estado) {

        return "activo".equalsIgnoreCase(estado)
                || "inactivo".equalsIgnoreCase(estado);
    }


    // =========================================================
    // LIMPIAR
    // =========================================================

    private String limpiar(
            String valor) {

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }


    // =========================================================
    // ERROR NUEVO USUARIO
    // =========================================================

    private void mostrarErrorNuevoUsuario(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje)
            throws ServletException, IOException {


        request.setAttribute(
                "error",
                mensaje
        );


        request.setAttribute(
                "nombreAnterior",
                request.getParameter("nombre")
        );


        request.setAttribute(
                "correoAnterior",
                request.getParameter("correo")
        );


        request.setAttribute(
                "rolAnterior",
                request.getParameter("rol")
        );


        request.setAttribute(
                "estadoAnterior",
                request.getParameter("estado")
        );


        request.getRequestDispatcher(
                "/nuevoUsuario.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // ERROR EDITAR USUARIO
    // =========================================================

    private void mostrarErrorEditarUsuario(
            HttpServletRequest request,
            HttpServletResponse response,
            Usuario usuario,
            String mensaje)
            throws ServletException, IOException {


        request.setAttribute(
                "error",
                mensaje
        );


        request.setAttribute(
                "usuario",
                usuario
        );


        request.getRequestDispatcher(
                "/editarUsuario.jsp"
        ).forward(
                request,
                response
        );
    }
}