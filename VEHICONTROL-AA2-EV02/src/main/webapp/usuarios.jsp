<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Usuarios - VEHICONTROL</title>

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

            background: linear-gradient(
                180deg,
                #021b4f,
                #01153d
            );

            color: white;

            padding: 25px 18px;

            display: flex;
            flex-direction: column;
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

        .card {
            border: none;

            border-radius: 14px;

            box-shadow:
                0 5px 20px rgba(15,23,42,0.07);
        }

        .card-header {
            background: white;

            border-bottom: 1px solid #edf0f4;

            padding: 20px 22px;

            border-radius: 14px 14px 0 0 !important;

            display: flex;

            justify-content: space-between;

            align-items: center;
        }

        .card-header h5 {
            margin: 0;

            font-size: 18px;

            font-weight: 700;
        }

        .total {
            background: #eef3ff;

            color: #234b91;

            padding: 6px 12px;

            border-radius: 20px;

            font-size: 13px;

            font-weight: 600;
        }

        .btn-nuevo {
            background: #0d6efd;

            color: white;

            border: none;

            padding: 9px 15px;

            border-radius: 8px;

            text-decoration: none;

            font-size: 14px;

            font-weight: 600;
        }

        .btn-nuevo:hover {
            background: #0b5ed7;

            color: white;
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            margin: 0 !important;
        }

        thead th {
            background: #f8fafc !important;

            color: #475569;

            font-size: 13px;

            text-transform: uppercase;

            white-space: nowrap;
        }

        tbody td {
            vertical-align: middle;

            font-size: 14px;

            padding-top: 15px;

            padding-bottom: 15px;
        }

        .usuario-nombre {
            font-weight: 700;

            color: #172554;
        }

        .badge-admin {
            background: #e0e7ff;

            color: #3730a3;

            padding: 7px 11px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: 700;
        }

        .badge-activo {
            background: #dcfce7;

            color: #166534;

            padding: 7px 11px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: 700;
        }

        .badge-inactivo {
            background: #fee2e2;

            color: #991b1b;

            padding: 7px 11px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: 700;
        }

        .acciones {
            display: flex;

            gap: 7px;
        }

        .btn-accion {
            width: 34px;
            height: 34px;

            border: none;

            border-radius: 8px;

            display: flex;

            align-items: center;

            justify-content: center;

            text-decoration: none;
        }

        .btn-editar {
            background: #e0e7ff;

            color: #3730a3;
        }

        .btn-eliminar {
            background: #fee2e2;

            color: #b91c1c;
        }

        .btn-editar:hover {
            background: #c7d2fe;

            color: #312e81;
        }

        .btn-eliminar:hover {
            background: #fecaca;

            color: #991b1b;
        }

        .empty {
            padding: 45px !important;

            text-align: center;

            color: #64748b;
        }

        .empty i {
            font-size: 40px;

            margin-bottom: 12px;

            color: #94a3b8;
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


<!-- MAIN -->

<main class="main">


    <!-- TOPBAR -->

    <header class="topbar">

        <h1>Usuarios</h1>


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


    <!-- CONTENT -->

    <section class="content">

        <!-- MENSAJES -->

        <c:if test="${param.error == 'NO_ELIMINAR_SESION'}">

            <div class="alert alert-danger d-flex align-items-center">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                No puedes eliminar el usuario con el que tienes iniciada la sesión.

            </div>

        </c:if>


        <c:if test="${param.error == 'ULTIMO_ADMIN'}">

            <div class="alert alert-danger d-flex align-items-center">

                <i class="fa-solid fa-shield-halved me-2"></i>

                No puedes eliminar el único administrador del sistema.

            </div>

        </c:if>


        <c:if test="${param.error == 'USUARIO_NO_ENCONTRADO'}">

            <div class="alert alert-danger d-flex align-items-center">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                El usuario solicitado no existe.

            </div>

        </c:if>


        <c:if test="${param.error == 'ID_INVALIDO'}">

            <div class="alert alert-danger d-flex align-items-center">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                El identificador del usuario no es válido.

            </div>

        </c:if>


        <c:if test="${param.error == 'ERROR_ELIMINAR'}">

            <div class="alert alert-danger d-flex align-items-center">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                No fue posible eliminar el usuario.

            </div>

        </c:if>


        <c:if test="${param.guardado == 'true'}">

            <div class="alert alert-success d-flex align-items-center">

                <i class="fa-solid fa-circle-check me-2"></i>

                Operación realizada correctamente.

            </div>

        </c:if>


        <div class="page-title">

            <h2>
                Gestión de usuarios
            </h2>

            <p>
                Administra los usuarios que tienen acceso al sistema VEHICONTROL.
            </p>

        </div>


        <div class="page-title">

            <h2>

                Gestión de usuarios

            </h2>

            <p>

                Administra los usuarios que tienen acceso al sistema VEHICONTROL.

            </p>

        </div>


        <div class="card">


            <div class="card-header">

                <div>

                    <h5>

                        <i class="fa-solid fa-users me-2"></i>

                        Usuarios registrados

                    </h5>

                </div>


                <div class="d-flex align-items-center gap-2">

                    <span class="total">

                        ${usuarios.size()} usuarios

                    </span>


                    <a
                        href="${pageContext.request.contextPath}/nuevoUsuario.jsp"
                        class="btn-nuevo">

                        <i class="fa-solid fa-plus me-1"></i>

                        Nuevo usuario

                    </a>

                </div>

            </div>


            <div class="table-container">

                <table class="table table-hover align-middle">

                    <thead>

                        <tr>

                            <th>ID</th>

                            <th>Nombre</th>

                            <th>Correo</th>

                            <th>Rol</th>

                            <th>Estado</th>

                            <th>Acciones</th>

                        </tr>

                    </thead>


                    <tbody>


                        <c:forEach
                            var="usuario"
                            items="${usuarios}">

                            <tr>

                                <td>

                                    ${usuario.id}

                                </td>


                                <td>

                                    <span class="usuario-nombre">

                                        ${usuario.nombre}

                                    </span>

                                </td>


                                <td>

                                    ${usuario.correo}

                                </td>


                                <td>

                                    <span class="badge-admin">

                                        ${usuario.rol}

                                    </span>

                                </td>


                                <td>

                                    <c:choose>

                                        <c:when
                                            test="${usuario.estado == 'activo'}">

                                            <span class="badge-activo">

                                                Activo

                                            </span>

                                        </c:when>


                                        <c:otherwise>

                                            <span class="badge-inactivo">

                                                Inactivo

                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                </td>


                                <td>

                                    <div class="acciones">

                                        <a
                                            href="${pageContext.request.contextPath}/usuarios?accion=editar&id=${usuario.id}"
                                            class="btn-accion btn-editar"
                                            title="Editar">

                                            <i class="fa-solid fa-pen"></i>

                                        </a>


                                        <a
                                            href="${pageContext.request.contextPath}/usuarios?accion=eliminar&id=${usuario.id}"
                                            class="btn-accion btn-eliminar"
                                            title="Eliminar"
                                            onclick="return confirm('¿Está seguro de eliminar este usuario?');">

                                            <i class="fa-solid fa-trash"></i>

                                        </a>

                                    </div>

                                </td>

                            </tr>

                        </c:forEach>


                        <c:if test="${empty usuarios}">

                            <tr>

                                <td
                                    colspan="6"
                                    class="empty">

                                    <i class="fa-solid fa-users d-block"></i>

                                    No hay usuarios registrados.

                                </td>

                            </tr>

                        </c:if>


                    </tbody>

                </table>

            </div>

        </div>

    </section>

</main>


<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>


</body>

</html>