<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Nuevo usuario - VEHICONTROL</title>

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
            font-family: Arial, Helvetica, sans-serif;
            background: #f4f7fb;
            color: #1f2937;
        }

        .sidebar {
            position: fixed;
            left: 0;
            top: 0;
            width: 250px;
            height: 100vh;
            background: linear-gradient(180deg, #021b4f, #01153d);
            color: white;
            padding: 25px 18px;
            display: flex;
            flex-direction: column;
            z-index: 1000;
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 12px;
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 35px;
            padding-left: 8px;
        }

        .logo i {
            font-size: 28px;
        }

        .menu {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .menu a {
            color: #dbe7ff;
            text-decoration: none;
            padding: 13px 14px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            gap: 12px;
            transition: 0.2s;
        }

        .menu a:hover {
            background: rgba(255,255,255,0.10);
            color: white;
        }

        .menu a.active {
            background: rgba(255,255,255,0.15);
            color: white;
        }

        .menu i {
            width: 22px;
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

        .main {
            margin-left: 250px;
            min-height: 100vh;
        }

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
            margin: 0;
            font-size: 24px;
            font-weight: 700;
            color: #172554;
        }

        .profile {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .profile-icon {
            width: 42px;
            height: 42px;
            border-radius: 50%;
            background: #e8eefc;
            color: #123b7a;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .profile-text strong {
            display: block;
            font-size: 14px;
        }

        .profile-text span {
            font-size: 12px;
            color: #6b7280;
        }

        .content {
            padding: 30px;
        }

        .page-title {
            margin-bottom: 25px;
        }

        .page-title h2 {
            margin: 0 0 5px;
            font-size: 27px;
            color: #172554;
        }

        .page-title p {
            margin: 0;
            color: #6b7280;
        }

        .card-formulario {
            background: white;
            border: none;
            border-radius: 14px;
            box-shadow: 0 5px 20px rgba(15,23,42,0.07);
            overflow: hidden;
        }

        .card-header-custom {
            background: white;
            border-bottom: 1px solid #edf0f4;
            padding: 20px 22px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .card-header-custom h5 {
            margin: 0;
            font-size: 18px;
            font-weight: 700;
            color: #1f2937;
        }

        .card-header-custom h5 i {
            color: #0d6efd;
        }

        .card-body-custom {
            padding: 25px 22px;
        }

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
            box-shadow: 0 0 0 3px rgba(13,110,253,0.10);
        }

        .password-info {
            margin-top: 6px;
            color: #64748b;
            font-size: 12px;
        }

        .botones {
            margin-top: 5px;
            padding-top: 22px;
            border-top: 1px solid #edf0f4;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        .btn-principal {
            background: #0d6efd;
            color: white;
            border: none;
            padding: 10px 17px;
            border-radius: 8px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-principal:hover {
            background: #0b5ed7;
            color: white;
        }

        .btn-cancelar {
            background: #f1f5f9;
            color: #475569;
            border: 1px solid #e2e8f0;
            padding: 10px 17px;
            border-radius: 8px;
            font-weight: 600;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-cancelar:hover {
            background: #e2e8f0;
            color: #334155;
        }

        @media (max-width: 900px) {

            .sidebar {
                width: 210px;
            }

            .main {
                margin-left: 210px;
            }
        }

        @media (max-width: 700px) {

            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }

            .main {
                margin-left: 0;
            }

            .support {
                margin-top: 20px;
            }

            .topbar {
                padding: 0 20px;
            }

            .content {
                padding: 20px;
            }

            .botones {
                flex-direction: column-reverse;
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

<aside class="sidebar">

    <div class="logo">
        <i class="fa-solid fa-shield-halved"></i>
        <span>VEHICONTROL</span>
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

        <a href="${pageContext.request.contextPath}/usuarios"
           class="active">
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

        <strong>
            <i class="fa-solid fa-circle-question"></i>
            ¿Necesitas ayuda?
        </strong>

        Consulta el soporte de VEHICONTROL.

    </div>

</aside>


<main class="main">

    <header class="topbar">

        <h1>Nuevo usuario</h1>

        <div class="profile">

            <div class="profile-icon">
                <i class="fa-solid fa-user"></i>
            </div>

            <div class="profile-text">

                <strong>Administrador</strong>

                <span>admin@vehicontrol.com</span>

            </div>

        </div>

    </header>


    <section class="content">

        <div class="page-title">

            <h2>Registrar nuevo usuario</h2>

            <p>
                Ingresa la información del nuevo usuario que tendrá acceso a VEHICONTROL.
            </p>

        </div>


        <!-- ERROR -->

        <% if (request.getAttribute("error") != null) { %>

            <div class="alert alert-danger alert-dismissible fade show"
                 role="alert">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                <%= request.getAttribute("error") %>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="alert"></button>

            </div>

        <% } %>


        <div class="card-formulario">

            <div class="card-header-custom">

                <h5>

                    <i class="fa-solid fa-user-plus me-2"></i>

                    Información del usuario

                </h5>

            </div>


            <div class="card-body-custom">

                <form
                    action="${pageContext.request.contextPath}/usuarios"
                    method="post">

                    <input
                        type="hidden"
                        name="accion"
                        value="insertar">


                    <div class="row">


                        <!-- NOMBRE -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="nombre"
                                class="form-label">

                                Nombre completo

                            </label>

                            <input
                                type="text"
                                id="nombre"
                                name="nombre"
                                class="form-control"
                                placeholder="Ej: Juan Pérez"
                                maxlength="100"
                                value="<%= request.getAttribute("nombreAnterior") != null ? request.getAttribute("nombreAnterior") : "" %>"
                                required>

                        </div>


                        <!-- CORREO -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="correo"
                                class="form-label">

                                Correo electrónico

                            </label>

                            <input
                                type="email"
                                id="correo"
                                name="correo"
                                class="form-control"
                                placeholder="Ej: usuario@vehicontrol.com"
                                maxlength="150"
                                value="<%= request.getAttribute("correoAnterior") != null ? request.getAttribute("correoAnterior") : "" %>"
                                required>

                        </div>


                        <!-- PASSWORD -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="password"
                                class="form-label">

                                Contraseña

                            </label>

                            <input
                                type="password"
                                id="password"
                                name="password"
                                class="form-control"
                                placeholder="Ingrese una contraseña"
                                minlength="6"
                                required>

                            <div class="password-info">

                                <i class="fa-solid fa-circle-info"></i>

                                La contraseña debe tener mínimo 6 caracteres.

                            </div>

                        </div>


                        <!-- ROL -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="rol"
                                class="form-label">

                                Rol

                            </label>

                            <select
                                id="rol"
                                name="rol"
                                class="form-select"
                                required>

                                <option value=""
                                        disabled
                                        <%
                                            if (request.getAttribute("rolAnterior") == null) {
                                        %>
                                            selected
                                        <%
                                            }
                                        %>>

                                    Seleccione un rol

                                </option>

                                <option value="admin"
                                    <%
                                        if ("admin".equals(
                                            request.getAttribute("rolAnterior"))) {
                                    %>
                                        selected
                                    <%
                                        }
                                    %>>

                                    Administrador

                                </option>

                                <option value="vigilante"
                                    <%
                                        if ("vigilante".equals(
                                            request.getAttribute("rolAnterior"))) {
                                    %>
                                        selected
                                    <%
                                        }
                                    %>>

                                    Vigilante

                                </option>

                            </select>

                        </div>


                        <!-- ESTADO -->

                        <div class="col-md-6 mb-4">

                            <label
                                for="estado"
                                class="form-label">

                                Estado

                            </label>

                            <select
                                id="estado"
                                name="estado"
                                class="form-select"
                                required>

                                <option value="activo"
                                    <%
                                        if (request.getAttribute("estadoAnterior") == null ||
                                            "activo".equals(
                                                request.getAttribute("estadoAnterior"))) {
                                    %>
                                        selected
                                    <%
                                        }
                                    %>>

                                    Activo

                                </option>

                                <option value="inactivo"
                                    <%
                                        if ("inactivo".equals(
                                            request.getAttribute("estadoAnterior"))) {
                                    %>
                                        selected
                                    <%
                                        }
                                    %>>

                                    Inactivo

                                </option>

                            </select>

                        </div>

                    </div>


                    <div class="botones">

                        <a
                            href="${pageContext.request.contextPath}/usuarios"
                            class="btn-cancelar">

                            <i class="fa-solid fa-arrow-left me-1"></i>

                            Cancelar

                        </a>

                        <button
                            type="submit"
                            class="btn-principal">

                            <i class="fa-solid fa-floppy-disk me-1"></i>

                            Guardar usuario

                        </button>

                    </div>

                </form>

            </div>

        </div>

    </section>

</main>


<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>

</html>