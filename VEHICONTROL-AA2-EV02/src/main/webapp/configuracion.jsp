<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    com.vehicontrol.model.Configuracion configuracion =
            (com.vehicontrol.model.Configuracion)
                    request.getAttribute("configuracion");

    if (configuracion == null) {
        response.sendRedirect(
                request.getContextPath() + "/configuracion"
        );
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Configuración - VEHICONTROL</title>

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            color: #333;
        }

        /* SIDEBAR */

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

            display: flex;
            flex-direction: column;

            z-index: 1000;
        }

        .logo {
            color: white;
            font-size: 22px;
            font-weight: bold;

            display: flex;
            align-items: center;

            gap: 10px;

            margin-bottom: 35px;
        }

        .logo i {
            font-size: 25px;
        }

        .menu {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .menu a {
            text-decoration: none;
            color: #dbe7ff;

            padding: 13px 14px;

            border-radius: 10px;

            display: flex;
            align-items: center;

            gap: 12px;

            font-size: 15px;

            transition: 0.2s;
        }

        .menu a:hover {
            background: rgba(255,255,255,0.10);
        }

        .menu a.active {
            background: rgba(255,255,255,0.15);
            color: white;
        }

        .menu a i {
            width: 20px;
            text-align: center;
        }

        .support {
            margin-top: auto;

            background: rgba(255,255,255,0.08);

            padding: 15px;

            border-radius: 12px;

            color: #dbe7ff;

            font-size: 13px;
        }

        .support strong {
            display: block;
            color: white;
            margin-bottom: 5px;
        }

        /* MAIN */

        .main {
            margin-left: 250px;
            min-height: 100vh;
        }

        /* TOPBAR */

        .topbar {
            height: 75px;

            background: white;

            border-bottom: 1px solid #e5e7eb;

            display: flex;
            align-items: center;
            justify-content: space-between;

            padding: 0 30px;
        }

        .topbar h1 {
            font-size: 24px;
            color: #172554;
        }

        .profile {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .profile-icon {
            width: 42px;
            height: 42px;

            border-radius: 50%;

            background: #e8eefc;

            display: flex;
            align-items: center;
            justify-content: center;

            color: #0d6efd;

            font-size: 18px;
        }

        .profile-info strong {
            display: block;
            color: #172554;
            font-size: 14px;
        }

        .profile-info span {
            color: #6b7280;
            font-size: 12px;
        }

        /* CONTENT */

        .content {
            padding: 30px;
        }

        .page-title {
            margin-bottom: 25px;
        }

        .page-title h2 {
            color: #172554;
            font-size: 27px;
            margin-bottom: 6px;
        }

        .page-title p {
            color: #6b7280;
            font-size: 14px;
        }

        /* MENSAJE */

        .success-message {
            background: #eaf8ef;
            border: 1px solid #b7e4c7;
            color: #237a3b;

            padding: 13px 16px;

            border-radius: 9px;

            margin-bottom: 20px;

            font-size: 14px;
        }

        .success-message i {
            margin-right: 7px;
        }

        .info-box {
            background: #f0f6ff;

            border: 1px solid #d6e6ff;

            color: #31527d;

            padding: 13px 15px;

            border-radius: 9px;

            font-size: 13px;

            margin-bottom: 20px;
        }

        .info-box i {
            margin-right: 7px;
        }

        /* FORM */

        form {
            width: 100%;
        }

        .config-grid {
            display: grid;

            grid-template-columns:
                repeat(2, minmax(0, 1fr));

            gap: 22px;
        }

        .card {
            background: white;

            border-radius: 14px;

            box-shadow:
                0 4px 15px rgba(0,0,0,0.05);

            overflow: hidden;
        }

        .card-header {
            padding: 20px 22px;

            border-bottom: 1px solid #edf0f5;

            display: flex;
            align-items: center;

            gap: 12px;
        }

        .card-header-icon {
            width: 40px;
            height: 40px;

            border-radius: 10px;

            background: #e8eefc;

            color: #0d6efd;

            display: flex;
            align-items: center;
            justify-content: center;
        }

        .card-header h3 {
            color: #172554;
            font-size: 17px;
        }

        .card-body {
            padding: 22px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group:last-child {
            margin-bottom: 0;
        }

        .form-group label {
            display: block;

            margin-bottom: 7px;

            color: #374151;

            font-size: 14px;

            font-weight: 600;
        }

        .form-control,
        .form-select {
            width: 100%;

            padding: 11px 13px;

            border: 1px solid #d8dee9;

            border-radius: 8px;

            font-size: 14px;

            background: white;

            outline: none;
        }

        .form-control:focus,
        .form-select:focus {
            border-color: #0d6efd;

            box-shadow:
                0 0 0 3px rgba(13,110,253,0.10);
        }

        .form-help {
            margin-top: 5px;

            color: #6b7280;

            font-size: 12px;
        }

        /* SWITCH */

        .option-row {
            display: flex;

            align-items: center;
            justify-content: space-between;

            padding: 14px 0;

            border-bottom: 1px solid #edf0f5;
        }

        .option-row:first-child {
            padding-top: 0;
        }

        .option-row:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }

        .option-info {
            padding-right: 15px;
        }

        .option-info strong {
            display: block;

            color: #172554;

            font-size: 14px;

            margin-bottom: 4px;
        }

        .option-info span {
            color: #6b7280;

            font-size: 12px;
        }

        .switch {
            position: relative;

            width: 46px;
            height: 24px;

            flex-shrink: 0;
        }

        .switch input {
            opacity: 0;

            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;

            cursor: pointer;

            inset: 0;

            background: #cbd5e1;

            border-radius: 30px;

            transition: 0.2s;
        }

        .slider:before {
            content: "";

            position: absolute;

            width: 18px;
            height: 18px;

            left: 3px;
            top: 3px;

            background: white;

            border-radius: 50%;

            transition: 0.2s;

            box-shadow: 0 1px 3px rgba(0,0,0,0.2);
        }

        .switch input:checked + .slider {
            background: #0d6efd;
        }

        .switch input:checked + .slider:before {
            transform: translateX(22px);
        }

        /* BOTONES */

        .actions {
            margin-top: 25px;

            display: flex;

            justify-content: flex-end;

            gap: 10px;
        }

        .btn {
            border: none;

            padding: 11px 18px;

            border-radius: 8px;

            font-size: 14px;

            cursor: pointer;

            text-decoration: none;

            display: inline-flex;

            align-items: center;

            gap: 8px;
        }

        .btn-primary {
            background: #0d6efd;
            color: white;
        }

        .btn-primary:hover {
            background: #0b5ed7;
        }

        .btn-secondary {
            background: #e9edf3;
            color: #374151;
        }

        /* RESPONSIVE */

        @media (max-width: 1000px) {

            .config-grid {
                grid-template-columns: 1fr;
            }

        }

        @media (max-width: 700px) {

            .sidebar {
                width: 70px;
                padding: 20px 10px;
            }

            .logo span,
            .menu a span,
            .support {
                display: none;
            }

            .logo {
                justify-content: center;
            }

            .menu a {
                justify-content: center;
            }

            .main {
                margin-left: 70px;
            }

            .topbar {
                padding: 0 18px;
            }

            .profile-info {
                display: none;
            }

            .content {
                padding: 20px;
            }

        }

    </style>

</head>

<body>


<!-- SIDEBAR -->

<aside class="sidebar">

    <div class="logo">

        <i class="fa-solid fa-shield-halved"></i>

        <span>VEHICONTROL</span>

    </div>


    <nav class="menu">

        <a href="${pageContext.request.contextPath}/dashboard">

            <i class="fa-solid fa-chart-line"></i>

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


        <a href="${pageContext.request.contextPath}/configuracion"
           class="active">

            <i class="fa-solid fa-gear"></i>

            <span>Configuración</span>

        </a>


        <a href="${pageContext.request.contextPath}/logout">

            <i class="fa-solid fa-right-from-bracket"></i>

            <span>Cerrar sesión</span>

        </a>

    </nav>


    <div class="support">

        <strong>

            <i class="fa-solid fa-circle-question"></i>

            Soporte

        </strong>

        ¿Necesitas ayuda con VEHICONTROL?

    </div>

</aside>


<!-- MAIN -->

<main class="main">


    <!-- TOPBAR -->

    <header class="topbar">

        <h1>Configuración</h1>


        <div class="profile">

            <div class="profile-icon">

                <i class="fa-solid fa-user"></i>

            </div>


            <div class="profile-info">

                <strong>Administrador</strong>

                <span>admin@vehicontrol.com</span>

            </div>

        </div>

    </header>


    <!-- CONTENT -->

    <section class="content">


        <div class="page-title">

            <h2>Configuración del sistema</h2>

            <p>
                Administra las opciones generales de VEHICONTROL.
            </p>

        </div>


        <% if ("true".equals(request.getParameter("guardado"))) { %>

            <div class="success-message">

                <i class="fa-solid fa-circle-check"></i>

                La configuración se guardó correctamente.

            </div>

        <% } %>


        <div class="info-box">

            <i class="fa-solid fa-circle-info"></i>

            Desde este módulo puedes configurar los parámetros
            generales utilizados por el sistema.

        </div>


        <!-- FORMULARIO -->

        <form
            action="${pageContext.request.contextPath}/configuracion"
            method="post"
        >

            <input
                type="hidden"
                name="id"
                value="<%= configuracion.getId() %>"
            >


            <div class="config-grid">


                <!-- INFORMACIÓN DEL CONJUNTO -->

                <div class="card">

                    <div class="card-header">

                        <div class="card-header-icon">

                            <i class="fa-solid fa-building"></i>

                        </div>

                        <h3>
                            Información del conjunto
                        </h3>

                    </div>


                    <div class="card-body">


                        <div class="form-group">

                            <label for="nombreConjunto">
                                Nombre del conjunto
                            </label>

                            <input
                                type="text"
                                id="nombreConjunto"
                                name="nombreConjunto"
                                class="form-control"
                                value="<%= configuracion.getNombreConjunto() %>"
                                required
                            >

                        </div>


                        <div class="form-group">

                            <label for="direccion">
                                Dirección
                            </label>

                            <input
                                type="text"
                                id="direccion"
                                name="direccion"
                                class="form-control"
                                value="<%= configuracion.getDireccion() == null ? "" : configuracion.getDireccion() %>"
                            >

                        </div>


                        <div class="form-group">

                            <label for="telefono">
                                Teléfono
                            </label>

                            <input
                                type="text"
                                id="telefono"
                                name="telefono"
                                class="form-control"
                                value="<%= configuracion.getTelefono() == null ? "" : configuracion.getTelefono() %>"
                            >

                        </div>


                        <div class="form-group">

                            <label for="capacidadParqueaderos">
                                Capacidad de parqueaderos
                            </label>

                            <input
                                type="number"
                                id="capacidadParqueaderos"
                                name="capacidadParqueaderos"
                                class="form-control"
                                value="<%= configuracion.getCapacidadParqueaderos() %>"
                                min="1"
                                required
                            >

                        </div>

                    </div>

                </div>


                <!-- PREFERENCIAS -->

                <div class="card">

                    <div class="card-header">

                        <div class="card-header-icon">

                            <i class="fa-solid fa-sliders"></i>

                        </div>

                        <h3>
                            Preferencias del sistema
                        </h3>

                    </div>


                    <div class="card-body">


                        <div class="option-row">

                            <div class="option-info">

                                <strong>
                                    Registro automático
                                </strong>

                                <span>
                                    Registrar automáticamente
                                    los movimientos.
                                </span>

                            </div>


                            <label class="switch">

                                <input
                                    type="checkbox"
                                    name="registroAutomatico"
                                    <%= configuracion.isRegistroAutomatico()
                                            ? "checked" : "" %>
                                >

                                <span class="slider"></span>

                            </label>

                        </div>


                        <div class="option-row">

                            <div class="option-info">

                                <strong>
                                    Notificaciones
                                </strong>

                                <span>
                                    Mostrar alertas del sistema.
                                </span>

                            </div>


                            <label class="switch">

                                <input
                                    type="checkbox"
                                    name="notificaciones"
                                    <%= configuracion.isNotificaciones()
                                            ? "checked" : "" %>
                                >

                                <span class="slider"></span>

                            </label>

                        </div>


                        <div class="option-row">

                            <div class="option-info">

                                <strong>
                                    Control de visitantes
                                </strong>

                                <span>
                                    Activar el registro de visitantes.
                                </span>

                            </div>


                            <label class="switch">

                                <input
                                    type="checkbox"
                                    name="controlVisitantes"
                                    <%= configuracion.isControlVisitantes()
                                            ? "checked" : "" %>
                                >

                                <span class="slider"></span>

                            </label>

                        </div>


                        <div class="option-row">

                            <div class="option-info">

                                <strong>
                                    Control de parqueaderos
                                </strong>

                                <span>
                                    Controlar disponibilidad de espacios.
                                </span>

                            </div>


                            <label class="switch">

                                <input
                                    type="checkbox"
                                    name="controlParqueaderos"
                                    <%= configuracion.isControlParqueaderos()
                                            ? "checked" : "" %>
                                >

                                <span class="slider"></span>

                            </label>

                        </div>

                    </div>

                </div>


                <!-- SEGURIDAD -->

                <div class="card">

                    <div class="card-header">

                        <div class="card-header-icon">

                            <i class="fa-solid fa-lock"></i>

                        </div>

                        <h3>
                            Seguridad
                        </h3>

                    </div>


                    <div class="card-body">


                        <div class="form-group">

                            <label for="tiempoSesion">
                                Tiempo de sesión
                            </label>

                            <select
                                id="tiempoSesion"
                                name="tiempoSesion"
                                class="form-select"
                            >

                                <option value="15"
                                    <%= configuracion.getTiempoSesion() == 15
                                            ? "selected" : "" %>>
                                    15 minutos
                                </option>

                                <option value="30"
                                    <%= configuracion.getTiempoSesion() == 30
                                            ? "selected" : "" %>>
                                    30 minutos
                                </option>

                                <option value="60"
                                    <%= configuracion.getTiempoSesion() == 60
                                            ? "selected" : "" %>>
                                    1 hora
                                </option>

                                <option value="120"
                                    <%= configuracion.getTiempoSesion() == 120
                                            ? "selected" : "" %>>
                                    2 horas
                                </option>

                            </select>

                            <div class="form-help">

                                Tiempo máximo de inactividad
                                antes de cerrar la sesión.

                            </div>

                        </div>


                        <div class="form-group">

                            <label for="intentosLogin">
                                Intentos de inicio de sesión
                            </label>

                            <select
                                id="intentosLogin"
                                name="intentosLogin"
                                class="form-select"
                            >

                                <option value="3"
                                    <%= configuracion.getIntentosLogin() == 3
                                            ? "selected" : "" %>>
                                    3 intentos
                                </option>

                                <option value="5"
                                    <%= configuracion.getIntentosLogin() == 5
                                            ? "selected" : "" %>>
                                    5 intentos
                                </option>

                                <option value="10"
                                    <%= configuracion.getIntentosLogin() == 10
                                            ? "selected" : "" %>>
                                    10 intentos
                                </option>

                            </select>

                        </div>

                    </div>

                </div>


                <!-- SISTEMA -->

                <div class="card">

                    <div class="card-header">

                        <div class="card-header-icon">

                            <i class="fa-solid fa-database"></i>

                        </div>

                        <h3>
                            Sistema
                        </h3>

                    </div>


                    <div class="card-body">


                        <div class="form-group">

                            <label for="idioma">
                                Idioma
                            </label>

                            <select
                                id="idioma"
                                name="idioma"
                                class="form-select"
                            >

                                <option
                                    value="Español"
                                    <%= "Español".equals(configuracion.getIdioma())
                                            ? "selected" : "" %>
                                >
                                    Español
                                </option>

                            </select>

                        </div>


                        <div class="form-group">

                            <label for="zonaHoraria">
                                Zona horaria
                            </label>

                            <select
                                id="zonaHoraria"
                                name="zonaHoraria"
                                class="form-select"
                            >

                                <option
                                    value="GMT-5 - Bogotá"
                                    <%= "GMT-5 - Bogotá".equals(configuracion.getZonaHoraria())
                                            ? "selected" : "" %>
                                >
                                    GMT-5 - Bogotá
                                </option>

                            </select>

                        </div>


                        <div class="form-group">

                            <label for="formatoFecha">
                                Formato de fecha
                            </label>

                            <select
                                id="formatoFecha"
                                name="formatoFecha"
                                class="form-select"
                            >

                                <option
                                    value="DD/MM/YYYY"
                                    <%= "DD/MM/YYYY".equals(configuracion.getFormatoFecha())
                                            ? "selected" : "" %>
                                >
                                    DD/MM/YYYY
                                </option>

                                <option
                                    value="YYYY-MM-DD"
                                    <%= "YYYY-MM-DD".equals(configuracion.getFormatoFecha())
                                            ? "selected" : "" %>
                                >
                                    YYYY-MM-DD
                                </option>

                            </select>

                        </div>

                    </div>

                </div>

            </div>


            <!-- BOTONES -->

            <div class="actions">

                <a
                    href="${pageContext.request.contextPath}/dashboard"
                    class="btn btn-secondary"
                >

                    <i class="fa-solid fa-xmark"></i>

                    Cancelar

                </a>


                <button
                    type="submit"
                    class="btn btn-primary"
                >

                    <i class="fa-solid fa-floppy-disk"></i>

                    Guardar cambios

                </button>

            </div>

        </form>

    </section>

</main>

</body>

</html>