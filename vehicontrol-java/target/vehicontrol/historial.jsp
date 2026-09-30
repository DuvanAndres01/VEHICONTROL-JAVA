<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Historial - VEHICONTROL</title>

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
            background: rgba(255, 255, 255, 0.10);
            color: white;
        }

        .menu a.active {
            background: rgba(255, 255, 255, 0.15);
            color: white;
        }

        .menu i {
            width: 22px;
            text-align: center;
        }

        .support {
            margin-top: auto;

            background: rgba(255, 255, 255, 0.08);

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
                0 5px 20px rgba(15, 23, 42, 0.07);
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

        .placa {
            font-weight: 700;
            color: #172554;
        }

        .badge-entrada {
            background: #dcfce7;
            color: #166534;

            padding: 7px 12px;

            border-radius: 20px;

            font-size: 12px;
            font-weight: 700;
        }

        .badge-salida {
            background: #fee2e2;
            color: #991b1b;

            padding: 7px 12px;

            border-radius: 20px;

            font-size: 12px;
            font-weight: 700;
        }

        .fecha {
            white-space: nowrap;
            color: #475569;
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

            .topbar {
                padding: 0 20px;
            }

            .content {
                padding: 20px;
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

            <strong>

                <i class="fa-solid fa-circle-question"></i>

                ¿Necesitas ayuda?

            </strong>

            Consulta el soporte de VEHICONTROL.

        </div>

    </aside>


    <!-- CONTENIDO -->

    <main class="main">


        <!-- TOPBAR -->

        <header class="topbar">

            <h1>Historial</h1>


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


            <div class="page-title">

                <h2>

                    Historial de movimientos

                </h2>

                <p>

                    Consulta todas las entradas y salidas registradas en VEHICONTROL.

                </p>

            </div>


            <div class="card">


                <div class="card-header">

                    <h5>

                        <i class="fa-solid fa-clock-rotate-left me-2"></i>

                        Movimientos registrados

                    </h5>


                    <span class="total">

                        ${movimientos.size()} registros

                    </span>

                </div>


                <div class="table-container">

                    <table class="table table-hover align-middle">

                        <thead>

                            <tr>

                                <th>ID</th>

                                <th>Placa</th>

                                <th>Marca</th>

                                <th>Modelo</th>

                                <th>Tipo</th>

                                <th>Fecha / Hora</th>

                                <th>Observación</th>

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

                                        ${movimiento.marca}

                                    </td>


                                    <td>

                                        ${movimiento.modelo}

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

                                        <span class="fecha">

                                            ${movimiento.fechaHora}

                                        </span>

                                    </td>


                                    <td>

                                        <c:choose>

                                            <c:when
                                                test="${not empty movimiento.observacion}">

                                                ${movimiento.observacion}

                                            </c:when>

                                            <c:otherwise>

                                                <span class="text-muted">

                                                    Sin observación

                                                </span>

                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                </tr>

                            </c:forEach>


                            <c:if test="${empty movimientos}">

                                <tr>

                                    <td
                                        colspan="7"
                                        class="empty">

                                        <i class="fa-solid fa-clock-rotate-left d-block"></i>

                                        No hay movimientos registrados todavía.

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