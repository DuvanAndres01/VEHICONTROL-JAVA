package com.vehicontrol.servlet;

import com.vehicontrol.dao.VehiculoDAO;
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
 * Servlet encargado de gestionar el módulo de vehículos
 * del sistema VEHICONTROL.
 *
 * Permite:
 * - Consultar vehículos registrados.
 * - Registrar nuevos vehículos.
 * - Editar vehículos existentes.
 * - Eliminar vehículos.
 * - Validar los datos enviados por los formularios.
 * - Evitar el registro de placas duplicadas.
 * - Mantener el estado actual del vehículo al editarlo.
 *
 * URL principal:
 * /vehiculos
 *
 * @author Duvan Arias
 */
@WebServlet("/vehiculos")
public class VehiculoServlet extends HttpServlet {

    /**
     * DAO encargado de realizar las operaciones de vehículos
     * en la base de datos.
     */
    private final VehiculoDAO vehiculoDAO =
            new VehiculoDAO();

    /**
     * Verifica si existe una sesión activa con un usuario
     * autenticado.
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
     * Procesa las solicitudes GET del módulo de vehículos.
     *
     * Permite consultar la lista, editar un vehículo
     * o eliminarlo dependiendo del parámetro "accion".
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al cargar
     *         una vista
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

        // Obtiene la acción solicitada.
        String accion =
                request.getParameter("accion");

        // ======================================
        // EDITAR
        // ======================================

        if ("editar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");

            // Verifica que se haya recibido el ID.
            if (idParametro == null
                    || idParametro.trim().isEmpty()) {

                request.setAttribute(
                        "error",
                        "El ID del vehículo es obligatorio."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            int id;

            try {

                // Convierte el ID recibido a entero.
                id = Integer.parseInt(
                        idParametro
                );

            } catch (NumberFormatException e) {

                request.setAttribute(
                        "error",
                        "El ID del vehículo no es válido."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            // Los identificadores deben ser positivos.
            if (id <= 0) {

                request.setAttribute(
                        "error",
                        "El ID del vehículo no es válido."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            // Busca el vehículo en la base de datos.
            Vehiculo vehiculo =
                    vehiculoDAO.buscarPorId(id);

            // Verifica que el vehículo exista.
            if (vehiculo == null) {

                request.setAttribute(
                        "error",
                        "El vehículo solicitado no existe."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            // Envía el vehículo a la vista de edición.
            request.setAttribute(
                    "vehiculo",
                    vehiculo
            );

            // Muestra el formulario de edición.
            request.getRequestDispatcher(
                    "/editarVehiculo.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // ELIMINAR
        // ======================================

        if ("eliminar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");

            // Verifica que se haya recibido el ID.
            if (idParametro == null
                    || idParametro.trim().isEmpty()) {

                request.setAttribute(
                        "error",
                        "El ID del vehículo es obligatorio."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            int id;

            try {

                // Convierte el identificador a entero.
                id = Integer.parseInt(
                        idParametro
                );

            } catch (NumberFormatException e) {

                request.setAttribute(
                        "error",
                        "El ID del vehículo no es válido."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            // Valida que el ID sea positivo.
            if (id <= 0) {

                request.setAttribute(
                        "error",
                        "El ID del vehículo no es válido."
                );

                cargarLista(
                        request,
                        response
                );

                return;
            }

            try {

                /*
                 * El DAO devuelve false cuando el vehículo
                 * no existe o no pudo ser eliminado.
                 */
                boolean eliminado =
                        vehiculoDAO.eliminar(id);

                if (!eliminado) {

                    request.setAttribute(
                            "error",
                            "El vehículo no existe o ya fue eliminado."
                    );

                    cargarLista(
                            request,
                            response
                    );

                    return;
                }

                // Redirige al listado después de eliminar.
                response.sendRedirect(
                        request.getContextPath()
                                + "/vehiculos"
                                + "?eliminado=true"
                );

            } catch (RuntimeException e) {

                /*
                 * Este error también permite informar que
                 * el vehículo tiene movimientos relacionados
                 * y no puede eliminarse.
                 */
                request.setAttribute(
                        "error",
                        "No se puede eliminar el vehículo porque tiene movimientos registrados."
                );

                cargarLista(
                        request,
                        response
                );
            }

            return;
        }

        // ======================================
        // LISTAR
        // ======================================

        // Si no existe una acción especial, muestra
        // el listado general de vehículos.
        cargarLista(
                request,
                response
        );
    }

    /**
     * Procesa las solicitudes POST utilizadas para registrar
     * o actualizar vehículos.
     *
     * La acción se determina mediante el parámetro "accion".
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al procesar
     *         la solicitud
     * @throws IOException si ocurre un error de entrada o salida
     */
    @Override
    protected void doPost(
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

        // Permite trabajar correctamente con caracteres especiales.
        request.setCharacterEncoding(
                "UTF-8"
        );

        String accion =
                request.getParameter("accion");

        // ======================================
        // ACTUALIZAR VEHÍCULO
        // ======================================

        if ("actualizar".equals(accion)) {

            actualizarVehiculo(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // REGISTRAR VEHÍCULO
        // ======================================

        registrarVehiculo(
                request,
                response
        );
    }

    /**
     * Registra un nuevo vehículo en el sistema.
     *
     * Antes de realizar la inserción se validan los campos
     * obligatorios, las longitudes y la existencia de
     * una placa duplicada.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al cargar
     *         el formulario
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void registrarVehiculo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene y limpia los datos enviados por el formulario.
        String placa =
                limpiar(
                        request.getParameter("placa")
                );

        String marca =
                limpiar(
                        request.getParameter("marca")
                );

        String modelo =
                limpiar(
                        request.getParameter("modelo")
                );

        String color =
                limpiar(
                        request.getParameter("color")
                );

        String propietario =
                limpiar(
                        request.getParameter("propietario")
                );

        // ======================================
        // VALIDAR CAMPOS
        // ======================================

        String error =
                validarCampos(
                        placa,
                        marca,
                        modelo,
                        color,
                        propietario
                );

        if (error != null) {

            request.setAttribute(
                    "error",
                    error
            );

            cargarPaginaNuevoVehiculo(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // NORMALIZAR PLACA
        // ======================================

        // Las placas se almacenan en mayúsculas.
        placa =
                placa.toUpperCase();

        // ======================================
        // VALIDAR PLACA DUPLICADA
        // ======================================

        if (vehiculoDAO.existePlaca(placa)) {

            request.setAttribute(
                    "error",
                    "Ya existe un vehículo registrado con la placa "
                            + placa
                            + "."
            );

            cargarPaginaNuevoVehiculo(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // CREAR VEHÍCULO
        // ======================================

        /*
         * Los nuevos vehículos comienzan con estado "activo".
         * El estado de entrada/salida se controla posteriormente
         * mediante el módulo de movimientos.
         */
        Vehiculo vehiculo =
                new Vehiculo(
                        placa,
                        marca,
                        modelo,
                        color,
                        propietario,
                        "activo"
                );

        try {

            // Guarda el vehículo en la base de datos.
            vehiculoDAO.insertar(
                    vehiculo
            );

            // Redirige al listado después de registrar.
            response.sendRedirect(
                    request.getContextPath()
                            + "/vehiculos"
                            + "?guardado=true"
            );

        } catch (RuntimeException e) {

            // Informa el error sin exponer detalles internos.
            request.setAttribute(
                    "error",
                    "No fue posible registrar el vehículo."
            );

            cargarPaginaNuevoVehiculo(
                    request,
                    response
            );
        }
    }

    /**
     * Actualiza la información de un vehículo existente.
     *
     * Mantiene el estado actual del vehículo para evitar
     * alterar la información controlada por el módulo
     * de movimientos.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al cargar
     *         una vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void actualizarVehiculo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro =
                request.getParameter("id");

        // Verifica que se haya enviado el ID.
        if (idParametro == null
                || idParametro.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "El ID del vehículo es obligatorio."
            );

            cargarLista(
                    request,
                    response
            );

            return;
        }

        int id;

        try {

            // Convierte el ID recibido a entero.
            id = Integer.parseInt(
                    idParametro
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "El ID del vehículo no es válido."
            );

            cargarLista(
                    request,
                    response
            );

            return;
        }

        // Valida que el identificador sea positivo.
        if (id <= 0) {

            request.setAttribute(
                    "error",
                    "El ID del vehículo no es válido."
            );

            cargarLista(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // OBTENER DATOS
        // ======================================

        String placa =
                limpiar(
                        request.getParameter("placa")
                );

        String marca =
                limpiar(
                        request.getParameter("marca")
                );

        String modelo =
                limpiar(
                        request.getParameter("modelo")
                );

        String color =
                limpiar(
                        request.getParameter("color")
                );

        String propietario =
                limpiar(
                        request.getParameter("propietario")
                );

        // ======================================
        // VALIDAR CAMPOS
        // ======================================

        String error =
                validarCampos(
                        placa,
                        marca,
                        modelo,
                        color,
                        propietario
                );

        if (error != null) {

            /*
             * Construye un objeto temporal para conservar
             * los datos introducidos en el formulario.
             */
            Vehiculo vehiculo =
                    crearVehiculoParaFormulario(
                            id,
                            placa,
                            marca,
                            modelo,
                            color,
                            propietario
                    );

            request.setAttribute(
                    "vehiculo",
                    vehiculo
            );

            request.setAttribute(
                    "error",
                    error
            );

            request.getRequestDispatcher(
                    "/editarVehiculo.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // NORMALIZAR PLACA
        // ======================================

        placa =
                placa.toUpperCase();

        // ======================================
        // VALIDAR PLACA DUPLICADA
        // ======================================

        /*
         * Al editar se excluye el ID actual para permitir
         * conservar la misma placa.
         */
        if (vehiculoDAO.existePlacaExceptoId(
                placa,
                id
        )) {

            Vehiculo vehiculo =
                    crearVehiculoParaFormulario(
                            id,
                            placa,
                            marca,
                            modelo,
                            color,
                            propietario
                    );

            request.setAttribute(
                    "vehiculo",
                    vehiculo
            );

            request.setAttribute(
                    "error",
                    "La placa "
                            + placa
                            + " ya pertenece a otro vehículo."
            );

            request.getRequestDispatcher(
                    "/editarVehiculo.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // VERIFICAR VEHÍCULO
        // ======================================

        Vehiculo existente =
                vehiculoDAO.buscarPorId(id);

        if (existente == null) {

            request.setAttribute(
                    "error",
                    "El vehículo que intenta actualizar no existe."
            );

            cargarLista(
                    request,
                    response
            );

            return;
        }

        // ======================================
        // CREAR OBJETO
        // ======================================

        /*
         * Se conserva el estado actual del vehículo.
         * El estado se modifica mediante los movimientos
         * de entrada y salida.
         */
        Vehiculo vehiculo =
                new Vehiculo(
                        placa,
                        marca,
                        modelo,
                        color,
                        propietario,
                        existente.getEstado()
                );

        vehiculo.setId(id);

        // ======================================
        // ACTUALIZAR
        // ======================================

        try {

            boolean actualizado =
                    vehiculoDAO.actualizar(
                            vehiculo
                    );

            if (!actualizado) {

                request.setAttribute(
                        "error",
                        "No fue posible actualizar el vehículo."
                );

                request.setAttribute(
                        "vehiculo",
                        vehiculo
                );

                request.getRequestDispatcher(
                        "/editarVehiculo.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

            // Redirige al listado después de actualizar.
            response.sendRedirect(
                    request.getContextPath()
                            + "/vehiculos"
                            + "?actualizado=true"
            );

        } catch (RuntimeException e) {

            request.setAttribute(
                    "error",
                    "No fue posible actualizar el vehículo."
            );

            request.setAttribute(
                    "vehiculo",
                    vehiculo
            );

            request.getRequestDispatcher(
                    "/editarVehiculo.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }

    /**
     * Valida los campos principales de un vehículo.
     *
     * Comprueba que los datos obligatorios estén presentes
     * y que no superen las longitudes definidas para la
     * aplicación.
     *
     * @param placa placa del vehículo
     * @param marca marca del vehículo
     * @param modelo modelo del vehículo
     * @param color color del vehículo
     * @param propietario propietario del vehículo
     * @return mensaje de error si existe una validación
     *         incorrecta; null si todos los campos son válidos
     */
    private String validarCampos(
            String placa,
            String marca,
            String modelo,
            String color,
            String propietario) {

        if (placa.isEmpty()) {
            return "La placa es obligatoria.";
        }

        if (marca.isEmpty()) {
            return "La marca es obligatoria.";
        }

        if (modelo.isEmpty()) {
            return "El modelo es obligatorio.";
        }

        if (color.isEmpty()) {
            return "El color es obligatorio.";
        }

        if (propietario.isEmpty()) {
            return "El propietario es obligatorio.";
        }

        if (placa.length() > 20) {
            return "La placa no puede superar los 20 caracteres.";
        }

        if (marca.length() > 100) {
            return "La marca no puede superar los 100 caracteres.";
        }

        if (modelo.length() > 100) {
            return "El modelo no puede superar los 100 caracteres.";
        }

        if (color.length() > 50) {
            return "El color no puede superar los 50 caracteres.";
        }

        if (propietario.length() > 150) {
            return "El propietario no puede superar los 150 caracteres.";
        }

        return null;
    }

    /**
     * Limpia un texto recibido desde un formulario.
     *
     * Si el valor es null, devuelve una cadena vacía.
     * De lo contrario, elimina los espacios al inicio y
     * al final.
     *
     * @param valor texto recibido
     * @return texto limpio
     */
    private String limpiar(String valor) {

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    /**
     * Construye un objeto Vehiculo temporal para conservar
     * la información ingresada en el formulario cuando ocurre
     * un error de validación.
     *
     * @param id identificador del vehículo
     * @param placa placa ingresada
     * @param marca marca ingresada
     * @param modelo modelo ingresado
     * @param color color ingresado
     * @param propietario propietario ingresado
     * @return objeto Vehiculo preparado para la vista
     */
    private Vehiculo crearVehiculoParaFormulario(
            int id,
            String placa,
            String marca,
            String modelo,
            String color,
            String propietario) {

        Vehiculo vehiculo =
                new Vehiculo(
                        placa,
                        marca,
                        modelo,
                        color,
                        propietario,
                        "activo"
                );

        vehiculo.setId(id);

        return vehiculo;
    }

    /**
     * Consulta todos los vehículos registrados y los envía
     * a la vista principal del módulo.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al cargar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void cargarLista(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Consulta los vehículos mediante el DAO.
        List<Vehiculo> vehiculos =
                vehiculoDAO.listar();

        // Envía la lista a la página JSP.
        request.setAttribute(
                "vehiculos",
                vehiculos
        );

        // Muestra la vista principal.
        request.getRequestDispatcher(
                "/vehiculos.jsp"
        ).forward(
                request,
                response
        );
    }

    /**
     * Abre el formulario utilizado para registrar
     * un nuevo vehículo.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error al cargar
     *         la vista
     * @throws IOException si ocurre un error de entrada o salida
     */
    private void cargarPaginaNuevoVehiculo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Muestra el formulario de registro de vehículos.
        request.getRequestDispatcher(
                "/nuevoVehiculo.jsp"
        ).forward(
                request,
                response
        );
    }
}