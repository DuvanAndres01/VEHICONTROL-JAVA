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

/**
 * Servlet encargado de gestionar el panel principal
 * del sistema VEHICONTROL.
 *
 * El dashboard presenta información estadística relacionada
 * con los vehículos y movimientos registrados en el sistema.
 *
 * Entre los datos consultados se encuentran:
 * - Cantidad de entradas.
 * - Cantidad de salidas.
 * - Total de vehículos registrados.
 * - Vehículos actualmente dentro del conjunto.
 * - Capacidad total de parqueaderos.
 * - Espacios disponibles.
 * - Porcentaje de ocupación.
 * - Últimos movimientos registrados.
 *
 * URL:
 * /dashboard
 *
 * @author Duvan Arias
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    /**
     * DAO utilizado para consultar información
     * relacionada con los movimientos de vehículos.
     */
    private final MovimientoDAO movimientoDAO =
            new MovimientoDAO();

    /**
     * DAO utilizado para consultar los vehículos
     * registrados en el sistema.
     */
    private final VehiculoDAO vehiculoDAO =
            new VehiculoDAO();

    /**
     * DAO utilizado para consultar la configuración
     * general del conjunto residencial.
     */
    private final ConfiguracionDAO configuracionDAO =
            new ConfiguracionDAO();

    /**
     * Procesa las solicitudes GET realizadas al dashboard.
     *
     * Verifica la sesión del usuario, consulta las estadísticas
     * necesarias y envía la información a la vista dashboard.jsp.
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

        // ==========================================
        // VERIFICAR SESIÓN
        // ==========================================

        /*
         * Se obtiene la sesión existente sin crear una nueva.
         * Esto permite comprobar que el usuario haya iniciado sesión.
         */
        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("usuario") == null) {

            // Si no existe una sesión válida, se envía al login.
            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // ==========================================
        // ESTADÍSTICAS
        // ==========================================

        // Obtiene la cantidad total de entradas registradas.
        int entradas =
                movimientoDAO.contarEntradas();

        // Obtiene la cantidad total de salidas registradas.
        int salidas =
                movimientoDAO.contarSalidas();

        // Obtiene la cantidad de vehículos registrados.
        int totalVehiculos =
                vehiculoDAO.listar().size();

        // Obtiene la cantidad de vehículos actualmente
        // dentro del conjunto residencial.
        int vehiculosDentro =
                movimientoDAO.contarVehiculosDentro();

        // ==========================================
        // CONFIGURACIÓN
        // ==========================================

        // Consulta la configuración general del conjunto.
        Configuracion configuracion =
                configuracionDAO.obtener();

        /*
         * Se establece una capacidad predeterminada de 120
         * parqueaderos en caso de que no exista configuración
         * o que la capacidad almacenada no sea válida.
         */
        int capacidadParqueaderos = 120;

        if (configuracion != null
                && configuracion.getCapacidadParqueaderos() > 0) {

            capacidadParqueaderos =
                    configuracion.getCapacidadParqueaderos();
        }

        // ==========================================
        // ESPACIOS DISPONIBLES
        // ==========================================

        /*
         * Calcula los espacios disponibles restando los
         * vehículos actualmente dentro a la capacidad total.
         */
        int espaciosDisponibles =
                capacidadParqueaderos
                        - vehiculosDentro;

        /*
         * Evita que el número de espacios disponibles
         * sea mostrado como un valor negativo.
         */
        if (espaciosDisponibles < 0) {
            espaciosDisponibles = 0;
        }

        // ==========================================
        // PORCENTAJE DE OCUPACIÓN
        // ==========================================

        int porcentajeOcupacion = 0;

        /*
         * Calcula el porcentaje de ocupación de los
         * parqueaderos únicamente cuando existe
         * una capacidad válida.
         */
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

        /*
         * Consulta todos los movimientos registrados.
         * El DAO entrega los movimientos ordenados según
         * su fecha de registro.
         */
        List<Movimiento> movimientos =
                movimientoDAO.listar();

        /*
         * El dashboard solamente muestra los cinco
         * movimientos más recientes.
         *
         * Math.min evita intentar obtener más elementos
         * de los disponibles en la lista.
         */
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

        /*
         * Los siguientes atributos quedan disponibles
         * para ser utilizados mediante Expression Language
         * (EL) dentro de dashboard.jsp.
         */

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

        /*
         * Envía todos los datos preparados anteriormente
         * a la vista principal del sistema.
         */
        request.getRequestDispatcher(
                "/dashboard.jsp"
        ).forward(
                request,
                response
        );
    }
}