<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Entrada / Salida - VEHICONTROL</title>

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

            background: #f4f7fb;

            font-family: Arial, Helvetica, sans-serif;

            color: #1f2937;
        }


        /* =====================================================
           SIDEBAR
        ===================================================== */

        .sidebar {

            position: fixed;

            left: 0;
            top: 0;

            width: 250px;
            height: 100vh;

            background: linear-gradient(
                180deg,
                #021b4f,
                #01153d
            );

            padding: 25px 18px;

            color: white;

            display: flex;
            flex-direction: column;

            z-index: 1000;
        }


        .logo {

            display: flex;

            align-items: center;

            gap: 10px;

            font-size: 22px;

            font-weight: 700;

            margin-bottom: 35px;
        }


        .logo i {

            font-size: 27px;
        }


        .menu-title {

            color: #9fb4d8;

            font-size: 12px;

            font-weight: 600;

            text-transform: uppercase;

            margin: 0 10px 12px;
        }


        .menu {

            display: flex;

            flex-direction: column;

            gap: 8px;
        }


        .menu a {

            display: flex;

            align-items: center;

            gap: 12px;

            padding: 13px 14px;

            border-radius: 10px;

            color: #dbe7ff;

            text-decoration: none;

            font-size: 14px;

            transition: 0.2s;
        }


        .menu a i {

            width: 20px;

            text-align: center;

            font-size: 16px;
        }


        .menu a:hover {

            background: rgba(255,255,255,0.10);

            color: white;
        }


        .menu a.active {

            background: rgba(255,255,255,0.15);

            color: white;

            font-weight: 600;
        }


        .support {

            margin-top: auto;

            background: rgba(255,255,255,0.08);

            padding: 15px;

            border-radius: 12px;

            color: #dbe7ff;

            font-size: 13px;

            line-height: 1.6;
        }


        .support i {

            margin-right: 7px;
        }


        .support small {

            color: #9fb4d8;
        }


        /* =====================================================
           CONTENIDO PRINCIPAL
        ===================================================== */

        .main {

            margin-left: 250px;

            min-height: 100vh;
        }


        /* =====================================================
           TOPBAR
        ===================================================== */

        .topbar {

            height: 75px;

            background: white;

            border-bottom: 1px solid #e5e7eb;

            display: flex;

            align-items: center;

            justify-content: space-between;

            padding: 0 30px;
        }


        .topbar-title {

            display: flex;

            align-items: center;

            gap: 12px;
        }


        .topbar-title i {

            color: #0d6efd;

            font-size: 21px;
        }


        .topbar-title h1 {

            margin: 0;

            color: #172554;

            font-size: 24px;

            font-weight: 700;
        }


        /* =====================================================
           PERFIL
        ===================================================== */

        .profile {

            display: flex;

            align-items: center;

            gap: 12px;
        }


        .profile-icon {

            width: 42px;
            height: 42px;

            border-radius: 50%;

            background: #e8f0ff;

            color: #0d6efd;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 18px;
        }


        .profile-info {

            display: flex;

            flex-direction: column;
        }


        .profile-info strong {

            color: #172554;

            font-size: 14px;
        }


        .profile-info span {

            color: #64748b;

            font-size: 12px;
        }


        /* =====================================================
           CONTENIDO
        ===================================================== */

        .content {

            padding: 30px;
        }


        .page-title {

            margin-bottom: 25px;
        }


        .page-title h2 {

            margin: 0;

            color: #172554;

            font-size: 27px;

            font-weight: 700;
        }


        .page-title p {

            margin: 6px 0 0;

            color: #64748b;

            font-size: 14px;
        }


        /* =====================================================
           ALERTA DE ERROR
        ===================================================== */

        .alert-error-custom {

            background: #fef2f2;

            border: 1px solid #fecaca;

            color: #991b1b;

            border-radius: 10px;

            padding: 14px 18px;

            margin-bottom: 22px;

            display: flex;

            align-items: center;

            gap: 10px;

            font-size: 14px;
        }


        .alert-error-custom i {

            font-size: 18px;
        }


        .alert-error-custom strong {

            font-weight: 700;
        }


        .alert-success-custom {

            background: #f0fdf4;

            border: 1px solid #bbf7d0;

            color: #166534;

            border-radius: 10px;

            padding: 14px 18px;

            margin-bottom: 22px;

            display: flex;

            align-items: center;

            gap: 10px;

            font-size: 14px;
        }


        /* =====================================================
           CARDS
        ===================================================== */

        .card-custom {

            background: white;

            border-radius: 14px;

            border: 1px solid #e5e7eb;

            box-shadow:
                0 4px 14px rgba(15,23,42,0.06);

            margin-bottom: 25px;

            overflow: hidden;
        }


        .card-header-custom {

            padding: 20px 25px;

            border-bottom: 1px solid #e5e7eb;

            display: flex;

            align-items: center;

            justify-content: space-between;
        }


        .card-header-left {

            display: flex;

            align-items: center;

            gap: 12px;
        }


        .card-header-icon {

            width: 42px;
            height: 42px;

            border-radius: 10px;

            background: #e8f0ff;

            color: #0d6efd;

            display: flex;

            align-items: center;

            justify-content: center;
        }


        .card-header-custom h5 {

            margin: 0;

            color: #172554;

            font-size: 18px;

            font-weight: 700;
        }


        .card-header-custom p {

            margin: 3px 0 0;

            color: #64748b;

            font-size: 13px;
        }


        .card-body-custom {

            padding: 25px;
        }


        /* =====================================================
           FORMULARIO
        ===================================================== */

        .form-label {

            font-weight: 600;

            color: #374151;

            margin-bottom: 7px;

            font-size: 14px;
        }


        .form-control,
        .form-select {

            padding: 11px 13px;

            border: 1px solid #d1d5db;

            border-radius: 8px;

            font-size: 14px;
        }


        .form-control:focus,
        .form-select:focus {

            border-color: #0d6efd;

            box-shadow:
                0 0 0 3px rgba(13,110,253,0.10);
        }


        /* =====================================================
           BOTÓN
        ===================================================== */

        .btn-registrar {

            background: #0d6efd;

            color: white;

            border: none;

            padding: 11px 22px;

            border-radius: 8px;

            font-weight: 600;

            font-size: 14px;

            transition: 0.2s;
        }


        .btn-registrar:hover {

            background: #0b5ed7;

            color: white;
        }


        /* =====================================================
           TABLA
        ===================================================== */

        .table {

            margin: 0;
        }


        .table thead th {

            background: #f8fafc;

            color: #64748b;

            font-size: 12px;

            text-transform: uppercase;

            letter-spacing: 0.4px;

            border-bottom: 1px solid #e5e7eb;

            padding: 14px 15px;

            white-space: nowrap;
        }


        .table tbody td {

            padding: 15px;

            vertical-align: middle;

            border-bottom: 1px solid #eef0f3;
        }


        .table tbody tr:hover {

            background: #f8fafc;
        }


        .placa {

            font-weight: 700;

            color: #111827;
        }


        .vehiculo-info {

            color: #6b7280;

            font-size: 13px;
        }


        .observacion {

            color: #6b7280;

            font-size: 13px;
        }


        /* =====================================================
           BADGES
        ===================================================== */

        .badge-entrada {

            display: inline-flex;

            align-items: center;

            gap: 6px;

            padding: 6px 10px;

            border-radius: 20px;

            background: #dcfce7;

            color: #166534;

            font-size: 12px;

            font-weight: 600;
        }


        .badge-salida {

            display: inline-flex;

            align-items: center;

            gap: 6px;

            padding: 6px 10px;

            border-radius: 20px;

            background: #fee2e2;

            color: #991b1b;

            font-size: 12px;

            font-weight: 600;
        }


        /* =====================================================
           SIN MOVIMIENTOS
        ===================================================== */

        .empty-history {

            text-align: center;

            padding: 50px 20px !important;

            color: #94a3b8;
        }


        .empty-history i {

            font-size: 35px;

            margin-bottom: 15px;
        }


        .empty-history strong {

            display: block;

            color: #64748b;

            margin-bottom: 5px;
        }


        /* =====================================================
           RESPONSIVE
        ===================================================== */

        @media (max-width: 1000px) {

            .sidebar {

                width: 220px;
            }


            .main {

                margin-left: 220px;
            }


            .topbar {

                padding: 0 20px;
            }


            .content {

                padding: 25px 20px;
            }

        }


        @media (max-width: 700px) {

            .sidebar {

                position: relative;

                width: 100%;

                height: auto;

                padding: 18px;
            }


            .logo {

                margin-bottom: 20px;
            }


            .support {

                display: none;
            }


            .main {

                margin-left: 0;
            }


            .topbar {

                height: auto;

                min-height: 70px;

                padding: 15px 20px;
            }


            .profile-info {

                display: none;
            }


            .content {

                padding: 20px 15px;
            }


            .card-body-custom {

                padding: 20px 18px;
            }

        }

    </style>

</head>


<body>


<!-- =====================================================
     SIDEBAR
===================================================== -->

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


        <a
            href="${pageContext.request.contextPath}/movimientos"
            class="active">

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


<!-- =====================================================
     CONTENIDO PRINCIPAL
===================================================== -->

<main class="main">


    <!-- =================================================
         TOPBAR
    ================================================= -->

    <header class="topbar">

        <div class="topbar-title">

            <i class="fa-solid fa-right-left"></i>

            <h1>
                Entrada / Salida
            </h1>

        </div>


        <div class="profile">

            <div class="profile-icon">

                <i class="fa-solid fa-user"></i>

            </div>


            <div class="profile-info">

                <strong>
                    Administrador
                </strong>

                <span>
                    admin@vehicontrol.com
                </span>

            </div>

        </div>

    </header>


    <!-- =================================================
         CONTENT
    ================================================= -->

    <section class="content">


        <!-- TITULO -->

        <div class="page-title">

            <h2>
                Registro de entrada y salida
            </h2>

            <p>
                Registra y controla los movimientos de los vehículos.
            </p>

        </div>


        <!-- =================================================
             MENSAJE DE ERROR
        ================================================= -->

        <c:if test="${not empty error}">

            <div class="alert-error-custom">

                <i class="fa-solid fa-circle-exclamation"></i>

                <div>

                    <strong>Error:</strong>

                    ${error}

                </div>

            </div>

        </c:if>


        <!-- =================================================
             MENSAJE DE ÉXITO
        ================================================= -->

        <c:if test="${param.guardado == 'true'}">

            <div class="alert-success-custom">

                <i class="fa-solid fa-circle-check"></i>

                <div>

                    <strong>Movimiento registrado correctamente.</strong>

                </div>

            </div>

        </c:if>


        <!-- =================================================
             FORMULARIO
        ================================================= -->

        <div class="card-custom">


            <div class="card-header-custom">

                <div class="card-header-left">

                    <div class="card-header-icon">

                        <i class="fa-solid fa-right-left"></i>

                    </div>


                    <div>

                        <h5>
                            Registrar movimiento
                        </h5>

                        <p>
                            Registra una entrada o salida del conjunto.
                        </p>

                    </div>

                </div>

            </div>


            <div class="card-body-custom">


                <form
                    action="${pageContext.request.contextPath}/movimientos"
                    method="post">


                    <div class="row">


                        <!-- VEHÍCULO -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="vehiculoId"
                                class="form-label">

                                Vehículo

                            </label>


                            <select
                                id="vehiculoId"
                                name="vehiculoId"
                                class="form-select"
                                required>

                                <option value="">
                                    Seleccione un vehículo
                                </option>


                                <c:forEach
                                    var="vehiculo"
                                    items="${vehiculos}">

                                    <option
                                        value="${vehiculo.id}">

                                        ${vehiculo.placa}
                                        -
                                        ${vehiculo.marca}
                                        ${vehiculo.modelo}

                                    </option>

                                </c:forEach>

                            </select>

                        </div>


                        <!-- TIPO -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="tipo"
                                class="form-label">

                                Tipo de movimiento

                            </label>


                            <select
                                id="tipo"
                                name="tipo"
                                class="form-select"
                                required>

                                <option value="">
                                    Seleccione el tipo
                                </option>

                                <option value="ENTRADA">
                                    ENTRADA
                                </option>

                                <option value="SALIDA">
                                    SALIDA
                                </option>

                            </select>

                        </div>


                        <!-- OBSERVACIÓN -->

                        <div class="col-md-12 mb-4">

                            <label
                                for="observacion"
                                class="form-label">

                                Observación

                            </label>


                            <textarea
                                id="observacion"
                                name="observacion"
                                class="form-control"
                                rows="3"
                                maxlength="255"
                                placeholder="Observación opcional..."></textarea>

                        </div>


                        <!-- BOTÓN -->

                        <div class="col-md-12">

                            <button
                                type="submit"
                                class="btn-registrar">

                                <i class="fa-solid fa-check me-1"></i>

                                Registrar movimiento

                            </button>

                        </div>

                    </div>

                </form>

            </div>

        </div>


        <!-- =================================================
             HISTORIAL
        ================================================= -->

        <div class="card-custom">


            <div class="card-header-custom">

                <div class="card-header-left">

                    <div class="card-header-icon">

                        <i class="fa-solid fa-clock-rotate-left"></i>

                    </div>


                    <div>

                        <h5>
                            Historial de movimientos
                        </h5>

                        <p>
                            Últimos movimientos registrados.
                        </p>

                    </div>

                </div>


                <span class="badge bg-light text-dark">

                    ${movimientos.size()} registros

                </span>

            </div>


            <div class="table-responsive">


                <table class="table">


                    <thead>

                        <tr>

                            <th>
                                ID
                            </th>

                            <th>
                                Placa
                            </th>

                            <th>
                                Vehículo
                            </th>

                            <th>
                                Tipo
                            </th>

                            <th>
                                Fecha / Hora
                            </th>

                            <th>
                                Observación
                            </th>

                        </tr>

                    </thead>


                    <tbody>


                        <c:forEach
                            var="movimiento"
                            items="${movimientos}">

                            <tr>


                                <td>
                                    ${movimiento.id}
                                </td>


                                <td>

                                    <span class="placa">
                                        ${movimiento.placa}
                                    </span>

                                </td>


                                <td>

                                    <strong>
                                        ${movimiento.marca}
                                    </strong>

                                    <br>

                                    <span class="vehiculo-info">
                                        ${movimiento.modelo}
                                    </span>

                                </td>


                                <td>

                                    <c:choose>

                                        <c:when
                                            test="${movimiento.tipo == 'ENTRADA'}">

                                            <span class="badge-entrada">

                                                <i class="fa-solid fa-arrow-right-to-bracket"></i>

                                                ENTRADA

                                            </span>

                                        </c:when>


                                        <c:otherwise>

                                            <span class="badge-salida">

                                                <i class="fa-solid fa-arrow-right-from-bracket"></i>

                                                SALIDA

                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                </td>


                                <td>

                                    ${movimiento.fechaHora}

                                </td>


                                <td>

                                    <c:choose>

                                        <c:when
                                            test="${not empty movimiento.observacion}">

                                            <span class="observacion">

                                                ${movimiento.observacion}

                                            </span>

                                        </c:when>


                                        <c:otherwise>

                                            <span class="observacion">

                                                Sin observación

                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                </td>

                            </tr>

                        </c:forEach>


                        <!-- SIN MOVIMIENTOS -->

                        <c:if
                            test="${empty movimientos}">

                            <tr>

                                <td
                                    colspan="6"
                                    class="empty-history">

                                    <i class="fa-solid fa-clock-rotate-left"></i>

                                    <strong>
                                        No hay movimientos registrados
                                    </strong>

                                    Registra una entrada o salida
                                    para verla en este historial.

                                </td>

                            </tr>

                        </c:if>


                    </tbody>

                </table>

            </div>

        </div>

    </section>

</main>


<!-- Bootstrap JS -->

<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>