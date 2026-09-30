<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Vehículos - VEHICONTROL</title>

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
            background: #f4f6f9;
            font-family: Arial, Helvetica, sans-serif;
            color: #1f2937;
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
                #021b4f 0%,
                #01153d 100%
            );

            color: white;
            padding: 25px 15px;
            z-index: 1000;
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 12px;

            padding: 5px 15px 30px;

            font-size: 22px;
            font-weight: bold;
        }

        .logo i {
            font-size: 28px;
        }

        .menu-title {
            font-size: 11px;
            color: #9caecb;
            text-transform: uppercase;
            margin: 10px 15px;
        }

        .menu a {
            display: flex;
            align-items: center;
            gap: 13px;

            padding: 13px 15px;
            margin-bottom: 5px;

            color: #dbe7ff;
            text-decoration: none;

            border-radius: 8px;

            transition: 0.2s;
        }

        .menu a:hover,
        .menu a.active {
            background: rgba(255,255,255,0.12);
            color: white;
        }

        .menu a i {
            width: 20px;
            text-align: center;
        }

        .support {
            position: absolute;
            bottom: 25px;
            left: 15px;
            right: 15px;

            background: rgba(255,255,255,0.08);
            border-radius: 10px;

            padding: 15px;
            font-size: 13px;
        }

        .support i {
            margin-right: 6px;
        }

        /* CONTENIDO */

        .main {
            margin-left: 250px;
            min-height: 100vh;
        }

        .topbar {
            height: 70px;

            background: white;

            border-bottom: 1px solid #e5e7eb;

            display: flex;
            align-items: center;
            justify-content: space-between;

            padding: 0 30px;
        }

        .topbar-title {
            font-size: 21px;
            font-weight: 600;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .user-avatar {
            width: 38px;
            height: 38px;

            border-radius: 50%;

            background: #021b4f;
            color: white;

            display: flex;
            align-items: center;
            justify-content: center;
        }

        .content {
            padding: 30px;
        }

        /* HEADER */

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;

            margin-bottom: 25px;
        }

        .page-header h2 {
            margin: 0;
            font-size: 25px;
            font-weight: 700;
        }

        .page-header p {
            margin: 5px 0 0;
            color: #6b7280;
        }

        .btn-primary-custom {
            background: #021b4f;
            border: none;
            color: white;

            padding: 11px 18px;

            border-radius: 7px;

            font-weight: 600;

            text-decoration: none;
        }

        .btn-primary-custom:hover {
            background: #032b76;
            color: white;
        }

        /* CARD */

        .card-custom {
            background: white;
            border-radius: 12px;

            border: 1px solid #e5e7eb;

            box-shadow: 0 2px 8px rgba(0,0,0,0.04);
        }

        .card-header-custom {
            padding: 18px 22px;

            border-bottom: 1px solid #e5e7eb;

            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .card-header-custom h5 {
            margin: 0;
            font-size: 17px;
            font-weight: 600;
        }

        .search-box {
            width: 280px;
        }

        .search-box input {
            border-radius: 7px;
        }

        /* TABLA */

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
            font-weight: bold;
            color: #111827;
        }

        .vehiculo-info {
            font-size: 13px;
            color: #6b7280;
        }

        /* ESTADOS */

        .estado {
            display: inline-flex;
            align-items: center;
            gap: 6px;

            padding: 6px 10px;

            border-radius: 20px;

            font-size: 12px;
            font-weight: 600;
        }

        .estado-dentro {
            background: #dcfce7;
            color: #166534;
        }

        .estado-fuera {
            background: #fee2e2;
            color: #991b1b;
        }

        .estado-activo {
            background: #dbeafe;
            color: #1d4ed8;
        }

        .estado-inactivo {
            background: #e5e7eb;
            color: #4b5563;
        }

        /* BOTONES */

        .btn-action {
            display: inline-flex;
            align-items: center;
            justify-content: center;

            border: none;

            width: 34px;
            height: 34px;

            border-radius: 6px;

            background: #f1f5f9;
            color: #475569;

            text-decoration: none;
            margin-right: 4px;
        }

        .btn-action:hover {
            background: #e2e8f0;
            color: #021b4f;
        }

        .btn-action:hover {
            background: #e2e8f0;
        }

        /* VACÍO */

        .empty {
            text-align: center;
            padding: 50px;
            color: #6b7280;
        }

        .empty i {
            font-size: 40px;
            margin-bottom: 15px;
        }

        /* RESPONSIVE */

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

            .search-box {
                width: 200px;
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


<!-- CONTENIDO -->

<main class="main">

    <!-- TOPBAR -->

    <header class="topbar">

        <div class="topbar-title">

            Gestión de vehículos

        </div>

        <div class="user-info">

            <div>

                <strong>Administrador</strong>

                <br>

                <small class="text-muted">
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

        <!-- MENSAJE DE ERROR -->
        <c:if test="${not empty error}">

            <div class="alert alert-danger alert-dismissible fade show"
                role="alert">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                <strong>Error:</strong>
                ${error}

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="alert"
                        aria-label="Cerrar">
                </button>

            </div>

        </c:if>

        <div class="page-header">

            <div>

                <h2>Vehículos</h2>

                <p>
                    Consulta y administra los vehículos registrados.
                </p>

            </div>

            <a
                href="${pageContext.request.contextPath}/nuevo-vehiculo"
                class="btn-primary-custom">

                <i class="fa-solid fa-plus"></i>

                Registrar vehículo

            </a>

        </div>


        <div class="card-custom">

            <div class="card-header-custom">

                <h5>

                    <i class="fa-solid fa-car me-2"></i>

                    Vehículos registrados

                </h5>

                <div class="search-box">

                    <input
                        type="text"
                        id="buscar"
                        class="form-control"
                        placeholder="Buscar vehículo...">

                </div>

            </div>


            <div class="table-responsive">

                <table
                    class="table"
                    id="tablaVehiculos">

                    <thead>

                        <tr>

                            <th>ID</th>

                            <th>Placa</th>

                            <th>Vehículo</th>

                            <th>Color</th>

                            <th>Propietario</th>

                            <th>Estado</th>

                            <th>Acciones</th>

                        </tr>

                    </thead>

                    <tbody>

                        <c:forEach
                            var="vehiculo"
                            items="${vehiculos}">

                            <tr>

                                <td>
                                    ${vehiculo.id}
                                </td>

                                <td>

                                    <span class="placa">

                                        ${vehiculo.placa}

                                    </span>

                                </td>

                                <td>

                                    <strong>
                                        ${vehiculo.marca}
                                    </strong>

                                    <br>

                                    <span class="vehiculo-info">
                                        ${vehiculo.modelo}
                                    </span>

                                </td>

                                <td>

                                    ${vehiculo.color}

                                </td>

                                <td>

                                    ${vehiculo.propietario}

                                </td>

                                <td>

                                    <c:choose>

                                        <c:when test="${vehiculo.estado == 'dentro'}">

                                            <span class="estado estado-dentro">

                                                <i class="fa-solid fa-circle"></i>

                                                Dentro

                                            </span>

                                        </c:when>

                                        <c:when test="${vehiculo.estado == 'fuera'}">

                                            <span class="estado estado-fuera">

                                                <i class="fa-solid fa-circle"></i>

                                                Fuera

                                            </span>

                                        </c:when>

                                        <c:when test="${vehiculo.estado == 'activo'}">

                                            <span class="estado estado-activo">

                                                <i class="fa-solid fa-circle"></i>

                                                Activo

                                            </span>

                                        </c:when>

                                        <c:otherwise>

                                            <span class="estado estado-inactivo">

                                                ${vehiculo.estado}

                                            </span>

                                        </c:otherwise>

                                    </c:choose>

                                </td>

                                <td>

                                    <a
                                        href="${pageContext.request.contextPath}/vehiculos?accion=editar&id=${vehiculo.id}"
                                        class="btn-action"
                                        title="Editar vehículo">

                                        <i class="fa-solid fa-pen"></i>

                                    </a>

                                    <a
                                        href="${pageContext.request.contextPath}/vehiculos?accion=eliminar&id=${vehiculo.id}"
                                        class="btn-action"
                                        title="Eliminar vehículo"
                                        onclick="return confirm('¿Está seguro de eliminar este vehículo?');">

                                        <i class="fa-solid fa-trash"></i>

                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                        <c:if test="${empty vehiculos}">

                            <tr>

                                <td
                                    colspan="7"
                                    class="empty">

                                    <i class="fa-solid fa-car"></i>

                                    <br>

                                    No hay vehículos registrados.

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
<script>

    document
        .getElementById("buscar")
        .addEventListener("keyup", function () {

            let texto =
                this.value.toLowerCase();

            let filas =
                document.querySelectorAll(
                    "#tablaVehiculos tbody tr"
                );

            filas.forEach(function (fila) {

                let contenido =
                    fila.textContent.toLowerCase();

                fila.style.display =
                    contenido.includes(texto)
                        ? ""
                        : "none";

            });

        });

</script>

</body>

</html>