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

/**
 * Servlet encargado de gestionar los usuarios del sistema
 * VEHICONTROL.
 *
 * Permite realizar las siguientes operaciones:
 * - Listar usuarios.
 * - Consultar un usuario para editarlo.
 * - Registrar nuevos usuarios.
 * - Actualizar usuarios existentes.
 * - Eliminar usuarios.
 * - Validar correos electrónicos.
 * - Validar roles y estados.
 * - Proteger al último administrador del sistema.
 * - Evitar que el usuario elimine su propia sesión.
 *
 * URL:
 * /usuarios
 *
 * @author Duvan Arias
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    /**
     * DAO utilizado para consultar y modificar
     * la información de los usuarios.
     */
    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    /**
     * Verifica si existe una sesión activa con un usuario
     * autenticado en el sistema.
     *
     * @param request solicitud HTTP actual
     * @return true si existe un usuario autenticado;
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
     * Atiende las solicitudes GET del módulo de usuarios.
     *
     * Dependiendo del parámetro "accion", permite editar,
     * eliminar o listar usuarios.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error durante
     *         el procesamiento
     * @throws IOException si ocurre un error de entrada o salida
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica que exista una sesión autenticada.
        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Obtiene la acción solicitada desde la URL.
        String accion =
                request.getParameter("accion");

        // =====================================================
        // EDITAR
        // =====================================================

        if ("editar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");

            // Verifica que se haya enviado un identificador.
            if (idParametro == null ||
                idParametro.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;
            }

            try {

                // Convierte el ID recibido a entero.
                int id =
                        Integer.parseInt(idParametro);

                // Busca el usuario en la base de datos.
                Usuario usuario =
                        usuarioDAO.buscarPorId(id);

                // Comprueba que el usuario exista.
                if (usuario == null) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/usuarios?error=USUARIO_NO_ENCONTRADO"
                    );

                    return;
                }

                // Envía el usuario a la vista de edición.
                request.setAttribute(
                        "usuario",
                        usuario
                );

                // Muestra el formulario de edición.
                request.getRequestDispatcher(
                        "/editarUsuario.jsp"
                ).forward(
                        request,
                        response
                );

                return;

            } catch (NumberFormatException e) {

                // El identificador recibido no tiene un formato válido.
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

            // Verifica que se haya enviado un identificador.
            if (idParametro == null ||
                idParametro.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;
            }

            try {

                // Convierte el ID recibido a entero.
                int id =
                        Integer.parseInt(idParametro);

                // Busca el usuario antes de eliminarlo.
                Usuario usuario =
                        usuarioDAO.buscarPorId(id);

                // Verifica que el usuario exista.
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

                /*
                 * Obtiene la sesión actual para comprobar
                 * si el usuario que se desea eliminar es
                 * el mismo usuario que inició sesión.
                 */
                HttpSession session =
                        request.getSession(false);

                Usuario usuarioSesion =
                        (Usuario) session.getAttribute("usuario");

                /*
                 * Evita que un usuario pueda eliminar
                 * su propia cuenta mientras está autenticado.
                 */
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

                /*
                 * Si el usuario es administrador, se comprueba
                 * que exista al menos otro administrador antes
                 * de permitir su eliminación.
                 */
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

                // Elimina el usuario mediante el DAO.
                usuarioDAO.eliminar(id);

                // Redirige mostrando que la operación fue exitosa.
                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?guardado=true"
                );

                return;

            } catch (NumberFormatException e) {

                // Maneja un ID que no tenga formato numérico.
                response.sendRedirect(
                        request.getContextPath()
                                + "/usuarios?error=ID_INVALIDO"
                );

                return;

            } catch (RuntimeException e) {

                // Maneja errores ocurridos durante la eliminación.
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

        // Obtiene todos los usuarios registrados.
        List<Usuario> usuarios =
                usuarioDAO.listar();

        // Envía la lista a la vista.
        request.setAttribute(
                "usuarios",
                usuarios
        );

        // Muestra la página principal de usuarios.
        request.getRequestDispatcher(
                "/usuarios.jsp"
        ).forward(
                request,
                response
        );
    }

    /**
     * Atiende las solicitudes POST utilizadas para crear
     * o actualizar usuarios.
     *
     * La operación se determina mediante el parámetro
     * "accion" recibido desde el formulario.
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

        // Verifica la sesión del usuario.
        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Permite procesar correctamente caracteres especiales.
        request.setCharacterEncoding("UTF-8");

        // Obtiene y limpia la acción enviada por el formulario.
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

    /**
     * Registra un nuevo usuario después de validar
     * todos los datos recibidos desde el formulario.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al mostrar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void crearUsuario(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene los datos enviados por el formulario.
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

        /*
         * Comprueba que no exista otro usuario registrado
         * con el mismo correo electrónico.
         */
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

        /*
         * Construye el objeto Usuario con los datos
         * previamente validados.
         */
        Usuario usuario =
                new Usuario();

        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setPassword(password);
        usuario.setRol(rol.toLowerCase());
        usuario.setEstado(estado.toLowerCase());

        try {

            // Inserta el nuevo usuario en la base de datos.
            usuarioDAO.insertar(usuario);

            // Redirige al listado después de guardar.
            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?guardado=true"
            );

        } catch (RuntimeException e) {

            // Muestra un mensaje si ocurre un error durante el registro.
            mostrarErrorNuevoUsuario(
                    request,
                    response,
                    "No fue posible registrar el usuario."
            );
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al mostrar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void actualizarUsuario(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene y limpia el ID del usuario.
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

            // Convierte el ID a entero.
            id =
                    Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?error=ID_INVALIDO"
            );

            return;
        }

        // Busca el usuario que será actualizado.
        Usuario existente =
                usuarioDAO.buscarPorId(id);

        if (existente == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/usuarios?error=USUARIO_NO_ENCONTRADO"
            );

            return;
        }

        // Obtiene los nuevos datos del formulario.
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

        /*
         * Comprueba que el correo no pertenezca a otro
         * usuario diferente al que se está editando.
         */
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

        /*
         * Si el usuario actual es administrador, se evita
         * que el sistema quede sin administradores.
         */
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

        /*
         * Construye el objeto con la información actualizada.
         */
        Usuario usuario =
                new Usuario();

        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setRol(rol.toLowerCase());
        usuario.setEstado(estado.toLowerCase());

        try {

            // Actualiza el usuario mediante el DAO.
            usuarioDAO.actualizar(usuario);

            // Redirige al listado después de actualizar.
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

    /**
     * Valida el formato básico de un correo electrónico.
     *
     * @param correo correo electrónico a validar
     * @return true si cumple el patrón establecido;
     *         false en caso contrario
     */
    private boolean correoValido(
            String correo) {

        String regex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern
                .matches(regex, correo);
    }

    /**
     * Valida los roles permitidos dentro del sistema.
     *
     * @param rol rol que se desea validar
     * @return true para los roles admin o vigilante
     */
    private boolean rolValido(
            String rol) {

        return "admin".equalsIgnoreCase(rol)
                || "vigilante".equalsIgnoreCase(rol);
    }

    /**
     * Valida los estados permitidos para un usuario.
     *
     * @param estado estado que se desea validar
     * @return true para los estados activo o inactivo
     */
    private boolean estadoValido(
            String estado) {

        return "activo".equalsIgnoreCase(estado)
                || "inactivo".equalsIgnoreCase(estado);
    }

    /**
     * Limpia un valor recibido desde una solicitud HTTP.
     *
     * Si el valor es null, devuelve una cadena vacía.
     * En caso contrario, elimina espacios al inicio y al final.
     *
     * @param valor texto recibido desde el formulario
     * @return valor limpio
     */
    private String limpiar(
            String valor) {

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    /**
     * Envía un mensaje de error al formulario de creación
     * de usuarios y conserva algunos valores introducidos
     * anteriormente por el usuario.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @param mensaje mensaje que será mostrado
     * @throws ServletException si ocurre un error al cargar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void mostrarErrorNuevoUsuario(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje)
            throws ServletException, IOException {

        // Envía el mensaje de error a la vista.
        request.setAttribute(
                "error",
                mensaje
        );

        // Conserva los datos introducidos anteriormente.
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

        // Regresa al formulario de creación.
        request.getRequestDispatcher(
                "/nuevoUsuario.jsp"
        ).forward(
                request,
                response
        );
    }

    /**
     * Envía un mensaje de error al formulario de edición
     * y conserva la información del usuario consultado.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @param usuario usuario que se está editando
     * @param mensaje mensaje que será mostrado
     * @throws ServletException si ocurre un error al cargar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void mostrarErrorEditarUsuario(
            HttpServletRequest request,
            HttpServletResponse response,
            Usuario usuario,
            String mensaje)
            throws ServletException, IOException {

        // Envía el mensaje de error a la vista.
        request.setAttribute(
                "error",
                mensaje
        );

        // Conserva el usuario para volver a mostrar sus datos.
        request.setAttribute(
                "usuario",
                usuario
        );

        // Regresa al formulario de edición.
        request.getRequestDispatcher(
                "/editarUsuario.jsp"
        ).forward(
                request,
                response
        );
    }
}