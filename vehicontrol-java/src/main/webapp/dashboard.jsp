<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Dashboard - VEHICONTROL</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

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
            color: #172033;
        }

        /* ==========================================
           SIDEBAR
           ========================================== */

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            height: 100vh;

            background:
                linear-gradient(
                    180deg,
                    #021b4f 0%,
                    #01153d 100%
                );

            color: white;
            padding: 25px 18px;
            z-index: 1000;

            display: flex;
            flex-direction: column;
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 12px;

            padding: 5px 12px 30px;

            font-size: 22px;
            font-weight: 800;
            letter-spacing: .3px;
        }

        .logo i {
            font-size: 28px;
        }

        .menu-title {
            font-size: 10px;
            color: #8ea5c9;

            text-transform: uppercase;

            margin: 5px 12px 12px;

            letter-spacing: 1px;
            font-weight: 700;
        }

        .menu a {
            display: flex;
            align-items: center;

            gap: 13px;

            padding: 13px 14px;

            margin-bottom: 7px;

            color: #dbe7ff;

            text-decoration: none;

            border-radius: 10px;

            transition: .2s;
        }

        .menu a:hover,
        .menu a.active {
            background: rgba(255,255,255,.13);
            color: white;
        }

        .menu a i {
            width: 20px;
            text-align: center;
        }

        .support {
            margin-top: auto;

            background: rgba(255,255,255,.07);

            border: 1px solid rgba(255,255,255,.06);

            border-radius: 12px;

            padding: 15px;

            font-size: 13px;

            color: #dbe7ff;
        }

        .support small {
            color: #8ea5c9;
        }

        /* ==========================================
           MAIN
           ========================================== */

        .main {
            margin-left: 250px;
            min-height: 100vh;
        }

        /* ==========================================
           TOPBAR
           ========================================== */

        .topbar {
            height: 75px;

            background: white;

            border-bottom: 1px solid #e6eaf0;

            display: flex;

            align-items: center;

            justify-content: space-between;

            padding: 0 30px;
        }

        .topbar-title {
            font-size: 22px;

            font-weight: 700;

            color: #172554;
        }

        .user-info {
            display: flex;

            align-items: center;

            gap: 12px;
        }

        .user-text {
            text-align: right;
            line-height: 1.25;
        }

        .user-text strong {
            font-size: 13px;
            color: #172033;
        }

        .user-text small {
            color: #7b8798;
            font-size: 11px;
        }

        .user-avatar {
            width: 42px;
            height: 42px;

            border-radius: 50%;

            background:
                linear-gradient(
                    135deg,
                    #021b4f,
                    #0d6efd
                );

            color: white;

            display: flex;
            align-items: center;
            justify-content: center;

            box-shadow:
                0 5px 14px rgba(2,27,79,.18);
        }

        /* ==========================================
           CONTENT
           ========================================== */

        .content {
            padding: 30px;
        }

        .welcome {
            margin-bottom: 25px;
        }

        .welcome h2 {
            margin: 0;

            font-size: 28px;

            font-weight: 800;

            color: #172554;
        }

        .welcome p {
            margin: 7px 0 0;

            color: #718096;

            font-size: 14px;
        }

        /* ==========================================
           STATISTICS
           ========================================== */

        .stats {
            display: grid;

            grid-template-columns:
                repeat(4, 1fr);

            gap: 18px;

            margin-bottom: 22px;
        }

        .stat-card {
            background: white;

            border: 1px solid #e6eaf0;

            border-radius: 15px;

            padding: 20px;

            box-shadow:
                0 5px 18px rgba(15,23,42,.04);

            transition: .2s;
        }

        .stat-card:hover {
            transform: translateY(-2px);

            box-shadow:
                0 10px 25px rgba(15,23,42,.07);
        }

        .stat-top {
            display: flex;

            align-items: center;

            justify-content: space-between;

            margin-bottom: 15px;
        }

        .stat-label {
            color: #64748b;

            font-size: 12px;

            font-weight: 700;

            text-transform: uppercase;

            letter-spacing: .3px;
        }

        .stat-icon {
            width: 45px;
            height: 45px;

            border-radius: 12px;

            display: flex;

            align-items: center;
            justify-content: center;

            font-size: 18px;
        }

        .icon-blue {
            background: #dbeafe;
            color: #1d4ed8;
        }

        .icon-green {
            background: #dcfce7;
            color: #15803d;
        }

        .icon-orange {
            background: #ffedd5;
            color: #c2410c;
        }

        .icon-purple {
            background: #ede9fe;
            color: #6d28d9;
        }

        .stat-number {
            font-size: 31px;

            font-weight: 800;

            color: #111827;

            line-height: 1;
        }

        .stat-description {
            margin-top: 9px;

            color: #94a3b8;

            font-size: 11px;
        }

        /* ==========================================
           GRID PRINCIPAL
           ========================================== */

        .dashboard-grid {
            display: grid;

            grid-template-columns:
                1.25fr .75fr;

            gap: 20px;

            margin-bottom: 20px;
        }

        .panel {
            background: white;

            border: 1px solid #e6eaf0;

            border-radius: 15px;

            box-shadow:
                0 5px 18px rgba(15,23,42,.04);

            overflow: hidden;
        }

        .panel-header {
            padding: 18px 21px;

            border-bottom: 1px solid #edf0f4;

            display: flex;

            align-items: center;

            justify-content: space-between;
        }

        .panel-header h5 {
            margin: 0;

            font-size: 16px;

            font-weight: 700;

            color: #172033;
        }

        .panel-header h5 i {
            color: #0d6efd;
        }

        .panel-body {
            padding: 20px;
        }

        /* ==========================================
           ACTIONS
           ========================================== */

        .actions {
            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 12px;
        }

        .action {
            display: flex;

            align-items: center;

            gap: 12px;

            padding: 14px;

            border: 1px solid #e6eaf0;

            border-radius: 11px;

            text-decoration: none;

            color: #1f2937;

            transition: .2s;

            background: #fff;
        }

        .action:hover {
            border-color: #9bbcf1;

            background: #f8fbff;

            color: #021b4f;

            transform: translateY(-1px);
        }

        .action-icon {
            width: 40px;
            height: 40px;

            flex-shrink: 0;

            border-radius: 10px;

            background: #eef4ff;

            color: #0d6efd;

            display: flex;

            align-items: center;

            justify-content: center;
        }

        .action strong {
            display: block;

            font-size: 13px;
        }

        .action small {
            color: #94a3b8;

            font-size: 11px;
        }

        /* ==========================================
           PARKING
           ========================================== */

        .parking-number {
            display: flex;

            align-items: baseline;

            gap: 7px;

            margin-bottom: 15px;
        }

        .parking-number strong {
            font-size: 35px;

            color: #172554;
        }

        .parking-number span {
            color: #94a3b8;

            font-size: 13px;
        }

        .progress-container {
            height: 10px;

            background: #e9eef5;

            border-radius: 20px;

            overflow: hidden;

            margin-bottom: 12px;
        }

        .progress-bar-custom {
            height: 100%;

            background:
                linear-gradient(
                    90deg,
                    #0d6efd,
                    #4c8ffb
                );

            border-radius: 20px;

            transition: width .5s ease;
        }

        .parking-info {
            display: flex;

            justify-content: space-between;

            font-size: 12px;

            color: #718096;
        }

        .available {
            color: #15803d;

            font-weight: 700;
        }

        /* ==========================================
           ACTIVIDAD RECIENTE
           ========================================== */

        .activity-panel {
            margin-bottom: 20px;
        }

        .activity-item {
            display: flex;

            align-items: center;

            justify-content: space-between;

            padding: 13px 0;

            border-bottom: 1px solid #eef1f5;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-left {
            display: flex;

            align-items: center;

            gap: 12px;
        }

        .activity-icon {
            width: 40px;
            height: 40px;

            border-radius: 10px;

            display: flex;

            align-items: center;

            justify-content: center;
        }

        .activity-entry {
            background: #dcfce7;
            color: #15803d;
        }

        .activity-exit {
            background: #fee2e2;
            color: #b91c1c;
        }

        .activity-plate {
            font-weight: 700;

            font-size: 13px;

            color: #172033;
        }

        .activity-description {
            color: #94a3b8;

            font-size: 11px;

            margin-top: 2px;
        }

        .activity-type {
            padding: 5px 9px;

            border-radius: 20px;

            font-size: 10px;

            font-weight: 800;
        }

        .badge-entry {
            background: #dcfce7;
            color: #15803d;
        }

        .badge-exit {
            background: #fee2e2;
            color: #b91c1c;
        }

        .empty-activity {
            text-align: center;

            padding: 30px 10px;

            color: #94a3b8;

            font-size: 13px;
        }

        /* ==========================================
           FOOTER
           ========================================== */

        .footer {
            margin-top: 25px;

            color: #94a3b8;

            font-size: 11px;

            text-align: right;
        }

        /* ==========================================
           RESPONSIVE
           ========================================== */

        @media (max-width: 1150px) {

            .stats {
                grid-template-columns:
                    repeat(2, 1fr);
            }

            .dashboard-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 900px) {

            .sidebar {
                width: 70px;

                padding: 20px 8px;
            }

            .logo span,
            .menu-title,
            .menu a span,
            .support {
                display: none;
            }

            .logo {
                justify-content: center;

                padding: 5px 0 30px;
            }

            .menu a {
                justify-content: center;
            }

            .main {
                margin-left: 70px;
            }

            .content {
                padding: 20px;
            }
        }

        @media (max-width: 600px) {

            .stats {
                grid-template-columns: 1fr;
            }

            .actions {
                grid-template-columns: 1fr;
            }

            .topbar {
                padding: 0 15px;
            }

            .topbar-title {
                font-size: 17px;
            }

            .user-text {
                display: none;
            }

            .welcome h2 {
                font-size: 23px;
            }
        }

    </style>

</head>

<body>

<!-- ==========================================
     SIDEBAR
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

        <a
            href="${pageContext.request.contextPath}/dashboard"
            class="active">

            <i class="fa-solid fa-gauge-high"></i>

            <span>Dashboard</span>

        </a>

        <a
            href="${pageContext.request.contextPath}/vehiculos">

            <i class="fa-solid fa-car"></i>

            <span>Registro de Vehículos</span>

        </a>

        <a
            href="${pageContext.request.contextPath}/movimientos">

            <i class="fa-solid fa-right-left"></i>

            <span>Entrada / Salida</span>

        </a>

        <a
            href="${pageContext.request.contextPath}/historial">

            <i class="fa-solid fa-clock-rotate-left"></i>

            <span>Historial</span>

        </a>

        <a
            href="${pageContext.request.contextPath}/usuarios">

            <i class="fa-solid fa-users"></i>

            <span>Usuarios</span>

        </a>

        <a
            href="${pageContext.request.contextPath}/configuracion">

            <i class="fa-solid fa-gear"></i>

            <span>Configuración</span>

        </a>

        <a
            href="${pageContext.request.contextPath}/logout">

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
     MAIN
     ========================================== -->

<main class="main">

    <!-- TOPBAR -->

    <header class="topbar">

        <div class="topbar-title">
            Panel de administración
        </div>

        <div class="user-info">

            <div class="user-text">

                <strong>
                    Administrador
                </strong>

                <br>

                <small>
                    admin@vehicontrol.com
                </small>

            </div>

            <div class="user-avatar">

                <i class="fa-solid fa-user"></i>

            </div>

        </div>

    </header>

    <!-- CONTENIDO -->

    <section class="content">

        <!-- BIENVENIDA -->

        <div class="welcome">

            <h2>
                Bienvenido a VEHICONTROL
            </h2>

            <p>
                Resumen general del control de entrada y salida de vehículos.
            </p>

        </div>

        <!-- ======================================
             ESTADÍSTICAS
             ====================================== -->

        <div class="stats">

            <!-- ENTRADAS -->

            <div class="stat-card">

                <div class="stat-top">

                    <span class="stat-label">
                        Total ingresos
                    </span>

                    <div class="stat-icon icon-green">

                        <i class="fa-solid fa-arrow-right-to-bracket"></i>

                    </div>

                </div>

                <div class="stat-number">
                    ${entradas}
                </div>

                <div class="stat-description">
                    Movimientos de entrada registrados
                </div>

            </div>

            <!-- SALIDAS -->

            <div class="stat-card">

                <div class="stat-top">

                    <span class="stat-label">
                        Total salidas
                    </span>

                    <div class="stat-icon icon-orange">

                        <i class="fa-solid fa-arrow-right-from-bracket"></i>

                    </div>

                </div>

                <div class="stat-number">
                    ${salidas}
                </div>

                <div class="stat-description">
                    Movimientos de salida registrados
                </div>

            </div>

            <!-- VEHÍCULOS -->

            <div class="stat-card">

                <div class="stat-top">

                    <span class="stat-label">
                        Vehículos registrados
                    </span>

                    <div class="stat-icon icon-blue">

                        <i class="fa-solid fa-car"></i>

                    </div>

                </div>

                <div class="stat-number">
                    ${totalVehiculos}
                </div>

                <div class="stat-description">
                    Vehículos registrados en el sistema
                </div>

            </div>

            <!-- DENTRO -->

            <div class="stat-card">

                <div class="stat-top">

                    <span class="stat-label">
                        Vehículos dentro
                    </span>

                    <div class="stat-icon icon-purple">

                        <i class="fa-solid fa-location-dot"></i>

                    </div>

                </div>

                <div class="stat-number">
                    ${vehiculosDentro}
                </div>

                <div class="stat-description">
                    Vehículos actualmente en el conjunto
                </div>

            </div>

        </div>

        <!-- ======================================
             GRID
             ====================================== -->

        <div class="dashboard-grid">

            <!-- ACCIONES -->

            <div class="panel">

                <div class="panel-header">

                    <h5>

                        <i class="fa-solid fa-bolt me-2"></i>

                        Acciones rápidas

                    </h5>

                </div>

                <div class="panel-body">

                    <div class="actions">

                        <a
                            href="${pageContext.request.contextPath}/movimientos"
                            class="action">

                            <div class="action-icon">

                                <i class="fa-solid fa-arrow-right-to-bracket"></i>

                            </div>

                            <div>

                                <strong>
                                    Registrar entrada
                                </strong>

                                <small>
                                    Registrar ingreso
                                </small>

                            </div>

                        </a>

                        <a
                            href="${pageContext.request.contextPath}/movimientos"
                            class="action">

                            <div class="action-icon">

                                <i class="fa-solid fa-arrow-right-from-bracket"></i>

                            </div>

                            <div>

                                <strong>
                                    Registrar salida
                                </strong>

                                <small>
                                    Registrar salida
                                </small>

                            </div>

                        </a>

                        <a
                            href="${pageContext.request.contextPath}/vehiculos"
                            class="action">

                            <div class="action-icon">

                                <i class="fa-solid fa-car"></i>

                            </div>

                            <div>

                                <strong>
                                    Ver vehículos
                                </strong>

                                <small>
                                    Administrar vehículos
                                </small>

                            </div>

                        </a>

                        <a
                            href="${pageContext.request.contextPath}/historial"
                            class="action">

                            <div class="action-icon">

                                <i class="fa-solid fa-clock-rotate-left"></i>

                            </div>

                            <div>

                                <strong>
                                    Ver historial
                                </strong>

                                <small>
                                    Consultar movimientos
                                </small>

                            </div>

                        </a>

                    </div>

                </div>

            </div>

            <!-- PARQUEADERO -->

            <div class="panel">

                <div class="panel-header">

                    <h5>

                        <i class="fa-solid fa-square-parking me-2"></i>

                        Ocupación

                    </h5>

                </div>

                <div class="panel-body">

                    <div class="parking-number">

                        <strong>
                            ${vehiculosDentro}
                        </strong>

                        <span>
                            de ${capacidadParqueaderos} espacios
                        </span>

                    </div>

                    <div class="progress-container">

                        <div class="progress-bar-custom"
                            data-ocupacion="${porcentajeOcupacion}">
                        </div>

                    </div>

                    <div class="parking-info">

                        <span>
                            Ocupación:
                            <strong>
                                ${porcentajeOcupacion}%
                            </strong>
                        </span>

                        <span class="available">

                            ${espaciosDisponibles}

                            disponibles

                        </span>

                    </div>

                </div>

            </div>

        </div>

        <!-- ======================================
             ACTIVIDAD RECIENTE
             ====================================== -->

        <div class="panel activity-panel">

            <div class="panel-header">

                <h5>

                    <i class="fa-solid fa-clock-rotate-left me-2"></i>

                    Actividad reciente

                </h5>

                <a
                    href="${pageContext.request.contextPath}/historial"
                    class="btn btn-sm btn-outline-primary">

                    Ver historial

                </a>

            </div>

            <div class="panel-body">

                <c:choose>

                    <c:when test="${not empty ultimosMovimientos}">

                        <c:forEach
                            var="movimiento"
                            items="${ultimosMovimientos}">

                            <div class="activity-item">

                                <div class="activity-left">

                                    <c:choose>

                                        <c:when test="${movimiento.tipo == 'ENTRADA'}">

                                            <div class="activity-icon activity-entry">

                                                <i class="fa-solid fa-arrow-right-to-bracket"></i>

                                            </div>

                                        </c:when>

                                        <c:otherwise>

                                            <div class="activity-icon activity-exit">

                                                <i class="fa-solid fa-arrow-right-from-bracket"></i>

                                            </div>

                                        </c:otherwise>

                                    </c:choose>

                                    <div>

                                        <div class="activity-plate">

                                            ${movimiento.placa}

                                        </div>

                                        <div class="activity-description">

                                            ${movimiento.marca}
                                            ${movimiento.modelo}

                                            ·

                                            <c:choose>

                                                <c:when
                                                    test="${not empty movimiento.observacion}">

                                                    ${movimiento.observacion}

                                                </c:when>

                                                <c:otherwise>

                                                    Sin observación

                                                </c:otherwise>

                                            </c:choose>

                                        </div>

                                    </div>

                                </div>

                                <div>

                                    <c:choose>

                                        <c:when test="${movimiento.tipo == 'ENTRADA'}">

                                            <span class="activity-type badge-entry">

                                                ${movimiento.tipo}

                                            </span>

                                        </c:when>

                                        <c:otherwise>

                                            <span class="activity-type badge-exit">

                                                ${movimiento.tipo}

                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                    <div
                                        class="text-end mt-1">

                                        <small
                                            class="text-muted"
                                            style="font-size:10px;">

                                            ${movimiento.fechaHora}

                                        </small>

                                    </div>

                                </div>

                            </div>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <div class="empty-activity">

                            <i class="fa-solid fa-clock fa-2x mb-3"></i>

                            <br>

                            No hay movimientos registrados todavía.

                        </div>

                    </c:otherwise>

                </c:choose>

            </div>

        </div>

        <!-- ======================================
             FOOTER
             ====================================== -->

        <div class="footer">

            VEHICONTROL © 2026 · Sistema de control vehicular

        </div>

    </section>

</main>

<script>
    document.addEventListener("DOMContentLoaded", function () {

        const barras = document.querySelectorAll(".progress-bar-custom");

        barras.forEach(function (barra) {

            const porcentaje = barra.getAttribute("data-ocupacion");

            if (porcentaje !== null && porcentaje !== "") {
                barra.style.width = porcentaje + "%";
            }

        });

    });
</script>

</body>

</html>