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

/**
 * Servlet encargado de gestionar el registro de entradas
 * y salidas de vehículos en el sistema VEHICONTROL.
 *
 * Permite:
 * - Consultar los movimientos registrados.
 * - Consultar los vehículos disponibles.
 * - Registrar movimientos de tipo ENTRADA o SALIDA.
 * - Validar la información recibida desde el formulario.
 * - Mostrar mensajes de error al usuario.
 *
 * URL:
 * /movimientos
 *
 * @author Duvan Arias
 */
@WebServlet("/movimientos")
public class MovimientoServlet extends HttpServlet {

    /**
     * DAO utilizado para consultar y registrar
     * los movimientos de los vehículos.
     */
    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    /**
     * DAO utilizado para consultar la información
     * de los vehículos registrados.
     */
    private final VehiculoDAO vehiculoDAO =
            new VehiculoDAO();

    /**
     * Verifica si existe una sesión activa con un usuario
     * autenticado en el sistema.
     *
     * @param request solicitud HTTP actual
     * @return true si el usuario está autenticado;
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
     * Atiende las solicitudes GET del módulo de movimientos.
     *
     * Verifica la autenticación del usuario y posteriormente
     * carga la información necesaria para mostrar la página
     * de registro de entradas y salidas.
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

        // Verifica que exista una sesión válida.
        if (!usuarioAutenticado(request)) {

            // Si no está autenticado, se redirige al login.
            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Carga la información necesaria para mostrar la página.
        cargarPagina(request, response);
    }

    /**
     * Atiende las solicitudes POST utilizadas para registrar
     * una entrada o salida de vehículo.
     *
     * Realiza las validaciones necesarias antes de enviar
     * la información al MovimientoDAO.
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

        // Verifica que el usuario esté autenticado.
        if (!usuarioAutenticado(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Permite procesar correctamente caracteres especiales.
        request.setCharacterEncoding("UTF-8");

        // =====================================================
        // OBTENER DATOS
        // =====================================================

        // Obtiene el identificador del vehículo seleccionado.
        String vehiculoParametro =
                request.getParameter("vehiculoId");

        // Obtiene el tipo de movimiento.
        String tipo =
                request.getParameter("tipo");

        // Obtiene la observación opcional.
        String observacion =
                request.getParameter("observacion");

        // =====================================================
        // VALIDAR VEHÍCULO
        // =====================================================

        /*
         * Verifica que el formulario haya enviado
         * un vehículo seleccionado.
         */
        if (vehiculoParametro == null
                || vehiculoParametro.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Debe seleccionar un vehículo."
            );

            cargarPagina(request, response);

            return;
        }

        // Variable donde se almacenará el ID convertido a entero.
        int vehiculoId;

        try {

            /*
             * Convierte el identificador recibido desde el formulario
             * de String a entero.
             */
            vehiculoId =
                    Integer.parseInt(
                            vehiculoParametro
                    );

        } catch (NumberFormatException e) {

            // El valor recibido no corresponde a un ID válido.
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

        /*
         * Verifica que el formulario haya enviado
         * el tipo de movimiento.
         */
        if (tipo == null
                || tipo.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Debe seleccionar el tipo de movimiento."
            );

            cargarPagina(request, response);

            return;
        }

        /*
         * Elimina espacios innecesarios y convierte el tipo
         * a mayúsculas para facilitar su validación.
         */
        tipo =
                tipo.trim().toUpperCase();

        /*
         * VEHICONTROL únicamente permite dos tipos
         * de movimiento: ENTRADA y SALIDA.
         */
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

        /*
         * Consulta el vehículo en la base de datos para
         * comprobar que el ID recibido realmente exista.
         */
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

        /*
         * Construye el objeto Movimiento con los datos
         * validados anteriormente.
         */
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

            /*
             * Envía el movimiento al DAO para realizar
             * el registro correspondiente en la base de datos.
             */
            movimientoDAO.registrar(
                    movimiento
            );

            /*
             * Después de registrar correctamente el movimiento,
             * redirige nuevamente al módulo utilizando el parámetro
             * guardado=true para informar que la operación fue exitosa.
             */
            response.sendRedirect(
                    request.getContextPath()
                            + "/movimientos"
                            + "?guardado=true"
            );

        } catch (RuntimeException e) {

            /*
             * Si el DAO genera un error de negocio, por ejemplo
             * intentar registrar una segunda ENTRADA consecutiva,
             * se muestra el mensaje correspondiente en la página.
             */
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

    /**
     * Carga la información necesaria para mostrar
     * la página de movimientos.
     *
     * Consulta los movimientos y vehículos registrados
     * y los envía a movimientos.jsp mediante atributos
     * de la solicitud.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al cargar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void cargarPagina(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Obtiene todos los movimientos registrados para
         * mostrarlos en el historial del módulo.
         */
        List<Movimiento> movimientos =
                movimientoDAO.listar();

        /*
         * Obtiene todos los vehículos registrados para
         * llenar el selector del formulario.
         */
        List<Vehiculo> vehiculos =
                vehiculoDAO.listar();

        // Envía los movimientos a la vista JSP.
        request.setAttribute(
                "movimientos",
                movimientos
        );

        // Envía los vehículos a la vista JSP.
        request.setAttribute(
                "vehiculos",
                vehiculos
        );

        // Muestra la página de entradas y salidas.
        request.getRequestDispatcher(
                "/movimientos.jsp"
        ).forward(
                request,
                response
        );
    }
}