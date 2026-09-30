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

@WebServlet("/vehiculos")
public class VehiculoServlet extends HttpServlet {

    private final VehiculoDAO vehiculoDAO =
            new VehiculoDAO();


    // ==========================================
    // VERIFICAR SESIÓN
    // ==========================================
    private boolean usuarioAutenticado(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        return session != null
                && session.getAttribute("usuario") != null;
    }


    // ==========================================
    // GET
    // ==========================================
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


        // ======================================
        // EDITAR
        // ======================================
        if ("editar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");

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


            Vehiculo vehiculo =
                    vehiculoDAO.buscarPorId(id);


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


        // ======================================
        // ELIMINAR
        // ======================================
        if ("eliminar".equals(accion)) {

            String idParametro =
                    request.getParameter("id");


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


                response.sendRedirect(
                        request.getContextPath()
                                + "/vehiculos"
                                + "?eliminado=true"
                );

            } catch (RuntimeException e) {

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
        cargarLista(
                request,
                response
        );
    }


    // ==========================================
    // POST
    // ==========================================
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


    // ==========================================
    // REGISTRAR VEHÍCULO
    // ==========================================
    private void registrarVehiculo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


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

            vehiculoDAO.insertar(
                    vehiculo
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/vehiculos"
                            + "?guardado=true"
            );

        } catch (RuntimeException e) {

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


    // ==========================================
    // ACTUALIZAR VEHÍCULO
    // ==========================================
    private void actualizarVehiculo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String idParametro =
                request.getParameter("id");


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


    // ==========================================
    // VALIDAR CAMPOS
    // ==========================================
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


    // ==========================================
    // LIMPIAR TEXTO
    // ==========================================
    private String limpiar(String valor) {

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }


    // ==========================================
    // CREAR VEHÍCULO PARA FORMULARIO
    // ==========================================
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


    // ==========================================
    // CARGAR LISTA
    // ==========================================
    private void cargarLista(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        List<Vehiculo> vehiculos =
                vehiculoDAO.listar();


        request.setAttribute(
                "vehiculos",
                vehiculos
        );


        request.getRequestDispatcher(
                "/vehiculos.jsp"
        ).forward(
                request,
                response
        );
    }


    // ==========================================
    // CARGAR FORMULARIO NUEVO VEHÍCULO
    // ==========================================
    private void cargarPaginaNuevoVehiculo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        request.getRequestDispatcher(
                "/nuevoVehiculo.jsp"
        ).forward(
                request,
                response
        );
    }
}