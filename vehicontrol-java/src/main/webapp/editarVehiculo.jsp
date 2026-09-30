```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Editar vehículo - VEHICONTROL</title>

    <!-- Bootstrap -->
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <!-- Font Awesome -->
    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            background: #f4f6f9;
            font-family: Arial, Helvetica, sans-serif;
            color: #1f2937;
        }

        /* ==========================================
           TOPBAR
        ========================================== */

        .topbar {
            height: 70px;

            background: #021b4f;

            color: white;

            display: flex;
            align-items: center;

            padding: 0 30px;

            font-size: 21px;
            font-weight: 600;
        }

        .topbar i {
            margin-right: 12px;
            font-size: 25px;
        }

        /* ==========================================
           CONTENEDOR
        ========================================== */

        .contenedor {

            max-width: 950px;

            margin: 40px auto;

            padding: 0 20px;
        }

        /* ==========================================
           TARJETA
        ========================================== */

        .card-formulario {

            background: white;

            border-radius: 12px;

            border: 1px solid #e5e7eb;

            padding: 30px;

            box-shadow:
                0 2px 8px rgba(0,0,0,0.04);
        }

        /* ==========================================
           TITULO
        ========================================== */

        .titulo {

            margin-bottom: 28px;

            border-bottom: 1px solid #e5e7eb;

            padding-bottom: 20px;
        }

        .titulo h2 {

            margin: 0;

            font-size: 25px;

            font-weight: 700;

            color: #111827;
        }

        .titulo p {

            margin: 6px 0 0;

            color: #6b7280;

            font-size: 14px;
        }

        /* ==========================================
           FORMULARIO
        ========================================== */

        .form-label {

            font-weight: 600;

            color: #374151;

            margin-bottom: 7px;
        }

        .form-control {

            padding: 11px 13px;

            border: 1px solid #d1d5db;

            border-radius: 7px;
        }

        .form-control:focus {

            border-color: #021b4f;

            box-shadow:
                0 0 0 3px rgba(2,27,79,0.10);
        }

        /* ==========================================
           INFORMACIÓN DEL ID
        ========================================== */

        .id-info {

            background: #f8fafc;

            border: 1px solid #e5e7eb;

            border-radius: 7px;

            padding: 11px 13px;

            color: #64748b;

            font-size: 14px;
        }

        .id-info strong {

            color: #111827;
        }

        /* ==========================================
           BOTONES
        ========================================== */

        .botones {

            margin-top: 10px;

            padding-top: 22px;

            border-top: 1px solid #e5e7eb;

            display: flex;

            gap: 10px;
        }

        .btn-principal {

            background: #021b4f;

            color: white;

            border: none;

            padding: 11px 22px;

            border-radius: 7px;

            font-weight: 600;

            text-decoration: none;

            cursor: pointer;
        }

        .btn-principal:hover {

            background: #032b76;

            color: white;
        }

        .btn-cancelar {

            background: #6b7280;

            color: white;

            border: none;

            padding: 11px 22px;

            border-radius: 7px;

            font-weight: 600;

            text-decoration: none;
        }

        .btn-cancelar:hover {

            background: #4b5563;

            color: white;
        }

        /* ==========================================
           RESPONSIVE
        ========================================== */

        @media (max-width: 700px) {

            .topbar {

                padding: 0 20px;

                font-size: 18px;
            }

            .contenedor {

                margin: 25px auto;

                padding: 0 15px;
            }

            .card-formulario {

                padding: 22px;
            }

            .botones {

                flex-direction: column;
            }

            .btn-principal,
            .btn-cancelar {

                width: 100%;

                text-align: center;
            }
        }

    </style>

</head>

<body>

<!-- ==========================================
     TOPBAR
========================================== -->

<aside class="sidebar">

    <div class="logo">

        <i class="fa-solid fa-shield-halved"></i>

        <span>VEHICONTROL</span>

    </div>

    <div class="menu-title">
        Menú principal
    </div>

    <nav class="menu">

        <a href="${pageContext.request.contextPath}/dashboard">

            <i class="fa-solid fa-gauge-high"></i>

            <span>Dashboard</span>

        </a>


        <a href="${pageContext.request.contextPath}/vehiculos">

            <i class="fa-solid fa-car"></i>

            <span>Registro de Vehículos</span>

        </a>


        <a href="${pageContext.request.contextPath}/movimientos">

            <i class="fa-solid fa-right-left"></i>

            <span>Entrada / Salida</span>

        </a>


        <a href="${pageContext.request.contextPath}/historial">

            <i class="fa-solid fa-clock-rotate-left"></i>

            <span>Historial</span>

        </a>


        <a href="${pageContext.request.contextPath}/usuarios">

            <i class="fa-solid fa-users"></i>

            <span>Usuarios</span>

        </a>


        <a href="${pageContext.request.contextPath}/configuracion">

            <i class="fa-solid fa-gear"></i>

            <span>Configuración</span>

        </a>


        <a href="${pageContext.request.contextPath}/logout">

            <i class="fa-solid fa-right-from-bracket"></i>

            <span>Cerrar sesión</span>

        </a>

    </nav>

    <div class="support">

        <i class="fa-solid fa-circle-question"></i>

        ¿Necesitas ayuda?

        <br>

        <small>
            Contacta al administrador
        </small>

    </div>

</aside>


<!-- ==========================================
     CONTENIDO
========================================== -->

<div class="contenedor">

    <div class="card-formulario">

        <!-- ======================================
             TITULO
        ======================================= -->

        <div class="titulo">

            <h2>
                <i class="fa-solid fa-pen-to-square me-2"></i>
                Editar vehículo
            </h2>

            <p>
                Modifica la información del vehículo registrado.
            </p>

        </div>


        <!-- ======================================
             FORMULARIO
        ======================================= -->

        <form
            action="${pageContext.request.contextPath}/vehiculos"
            method="post">

            <!-- Acción para VehiculoServlet -->
            <input
                type="hidden"
                name="accion"
                value="actualizar">


            <!-- ID del vehículo -->
            <input
                type="hidden"
                name="id"
                value="${vehiculo.id}">


            <div class="row">

                <!-- ==================================
                     ID
                =================================== -->

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        ID del vehículo
                    </label>

                    <div class="id-info">

                        <strong>
                            #${vehiculo.id}
                        </strong>

                        <span>
                            &nbsp; Identificador del registro
                        </span>

                    </div>

                </div>


                <!-- ==================================
                     PLACA
                =================================== -->

                <div class="col-md-6 mb-3">

                    <label
                        for="placa"
                        class="form-label">

                        Placa

                    </label>

                    <input
                        type="text"
                        id="placa"
                        name="placa"
                        class="form-control"
                        value="${vehiculo.placa}"
                        maxlength="10"
                        required>

                </div>


                <!-- ==================================
                     MARCA
                =================================== -->

                <div class="col-md-6 mb-3">

                    <label
                        for="marca"
                        class="form-label">

                        Marca

                    </label>

                    <input
                        type="text"
                        id="marca"
                        name="marca"
                        class="form-control"
                        value="${vehiculo.marca}"
                        maxlength="50"
                        required>

                </div>


                <!-- ==================================
                     MODELO
                =================================== -->

                <div class="col-md-6 mb-3">

                    <label
                        for="modelo"
                        class="form-label">

                        Modelo

                    </label>

                    <input
                        type="text"
                        id="modelo"
                        name="modelo"
                        class="form-control"
                        value="${vehiculo.modelo}"
                        maxlength="50"
                        required>

                </div>


                <!-- ==================================
                     COLOR
                =================================== -->

                <div class="col-md-6 mb-3">

                    <label
                        for="color"
                        class="form-label">

                        Color

                    </label>

                    <input
                        type="text"
                        id="color"
                        name="color"
                        class="form-control"
                        value="${vehiculo.color}"
                        maxlength="30"
                        required>

                </div>


                <!-- ==================================
                     PROPIETARIO
                =================================== -->

                <div class="col-md-6 mb-4">

                    <label
                        for="propietario"
                        class="form-label">

                        Propietario

                    </label>

                    <input
                        type="text"
                        id="propietario"
                        name="propietario"
                        class="form-control"
                        value="${vehiculo.propietario}"
                        maxlength="100"
                        required>

                </div>

            </div>


            <!-- ======================================
                 BOTONES
            ======================================= -->

            <div class="botones">

                <a
                    href="${pageContext.request.contextPath}/vehiculos"
                    class="btn-cancelar">

                    <i class="fa-solid fa-arrow-left me-1"></i>

                    Cancelar

                </a>


                <button
                    type="submit"
                    class="btn-principal">

                    <i class="fa-solid fa-floppy-disk me-1"></i>

                    Guardar cambios

                </button>

            </div>

        </form>

    </div>

</div>

</body>

</html>