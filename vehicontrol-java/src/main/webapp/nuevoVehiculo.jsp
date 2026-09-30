<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Registrar vehículo - VEHICONTROL</title>

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
            color: #1f2937;
        }


        /* ==========================================
           SIDEBAR
        ========================================== */

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


        /* ==========================================
           CONTENIDO PRINCIPAL
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

            border-bottom: 1px solid #e5e7eb;

            display: flex;

            align-items: center;

            justify-content: space-between;

            padding: 0 30px;
        }

        .topbar-title {
            display: flex;
            align-items: center;

            gap: 10px;

            font-size: 24px;

            font-weight: 700;

            color: #172554;
        }

        .topbar-title i {
            color: #123b7a;
        }


        /* PERFIL */

        .profile {
            display: flex;

            align-items: center;

            gap: 12px;
        }

        .profile-text {
            text-align: right;
        }

        .profile-text strong {
            display: block;

            font-size: 14px;

            color: #1f2937;
        }

        .profile-text span {
            font-size: 12px;

            color: #6b7280;
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


        /* ==========================================
           CONTENT
        ========================================== */

        .content {
            padding: 30px;
        }


        /* TITULO DE PAGINA */

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


        /* ==========================================
           CARD
        ========================================== */

        .card-formulario {
            background: white;

            border-radius: 14px;

            border: 1px solid #e5e7eb;

            box-shadow:
                0 5px 20px rgba(15,23,42,0.07);

            overflow: hidden;
        }


        /* HEADER CARD */

        .card-header-custom {
            background: white;

            border-bottom: 1px solid #edf0f4;

            padding: 20px 22px;

            display: flex;

            align-items: center;

            gap: 10px;
        }

        .card-header-custom h5 {
            margin: 0;

            font-size: 18px;

            font-weight: 700;

            color: #1f2937;
        }

        .card-header-custom i {
            color: #0d6efd;
        }


        /* BODY CARD */

        .card-body-custom {
            padding: 25px 22px;
        }


        /* ==========================================
           FORMULARIO
        ========================================== */

        .form-label {
            display: block;

            font-weight: 600;

            color: #374151;

            margin-bottom: 7px;

            font-size: 14px;
        }

        .form-control {
            width: 100%;

            padding: 11px 13px;

            border: 1px solid #d1d5db;

            border-radius: 8px;

            font-size: 14px;

            transition: 0.2s;
        }

        .form-control:focus {
            border-color: #0d6efd;

            box-shadow:
                0 0 0 3px rgba(13,110,253,0.10);
        }

        .form-control::placeholder {
            color: #9ca3af;
        }


        /* ==========================================
           BOTONES
        ========================================== */

        .botones {
            display: flex;

            justify-content: flex-end;

            gap: 10px;

            padding-top: 22px;

            margin-top: 5px;

            border-top: 1px solid #edf0f4;
        }

        .btn-principal {
            display: inline-flex;

            align-items: center;

            justify-content: center;

            gap: 8px;

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
            display: inline-flex;

            align-items: center;

            justify-content: center;

            gap: 8px;

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


        /* ==========================================
           MENSAJE
        ========================================== */

        .alert-custom {
            border-radius: 10px;

            margin-bottom: 20px;

            border: none;
        }


        /* ==========================================
           RESPONSIVE
        ========================================== */

        @media (max-width: 900px) {

            .sidebar {
                width: 210px;
            }

            .main {
                margin-left: 210px;
            }

            .content {
                padding: 25px;
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

            .profile-text {
                display: none;
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
            href="${pageContext.request.contextPath}/dashboard">

            <i class="fa-solid fa-gauge-high"></i>

            <span>Dashboard</span>

        </a>


        <a
            href="${pageContext.request.contextPath}/vehiculos"
            class="active">

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

        <div>

            <i class="fa-solid fa-circle-question"></i>

            ¿Necesitas ayuda?

        </div>

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

            <i class="fa-solid fa-car"></i>

            Registrar vehículo

        </div>


        <div class="profile">

            <div class="profile-text">

                <strong>
                    Administrador
                </strong>

                <span>
                    admin@vehicontrol.com
                </span>

            </div>


            <div class="profile-icon">

                <i class="fa-solid fa-user"></i>

            </div>

        </div>

    </header>



    <!-- ==========================================
         CONTENT
    ========================================== -->

    <section class="content">


        <div class="page-title">

            <h2>
                Registrar vehículo
            </h2>

            <p>
                Ingresa la información del vehículo.
            </p>

        </div>


        <!-- MENSAJE DE ERROR -->

        <% if (request.getAttribute("error") != null) { %>

            <div class="alert alert-danger alert-custom">

                <i class="fa-solid fa-circle-exclamation me-2"></i>

                <strong>Error:</strong>

                <%= request.getAttribute("error") %>

            </div>

        <% } %>



        <!-- CARD -->

        <div class="card-formulario">


            <div class="card-header-custom">

                <i class="fa-solid fa-car"></i>

                <h5>
                    Información del vehículo
                </h5>

            </div>


            <div class="card-body-custom">


                <form
                    action="${pageContext.request.contextPath}/vehiculos"
                    method="post">


                    <div class="row">


                        <!-- PLACA -->

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Placa
                            </label>

                            <input
                                type="text"
                                name="placa"
                                class="form-control"
                                placeholder="Ej: ABC-123"
                                maxlength="10"
                                required>

                        </div>


                        <!-- MARCA -->

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Marca
                            </label>

                            <input
                                type="text"
                                name="marca"
                                class="form-control"
                                placeholder="Ej: Toyota"
                                maxlength="100"
                                required>

                        </div>


                        <!-- MODELO -->

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Modelo
                            </label>

                            <input
                                type="text"
                                name="modelo"
                                class="form-control"
                                placeholder="Ej: Corolla 2023"
                                maxlength="100"
                                required>

                        </div>


                        <!-- COLOR -->

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Color
                            </label>

                            <input
                                type="text"
                                name="color"
                                class="form-control"
                                placeholder="Ej: Rojo"
                                maxlength="50"
                                required>

                        </div>


                        <!-- PROPIETARIO -->

                        <div class="col-md-12 mb-4">

                            <label class="form-label">
                                Propietario
                            </label>

                            <input
                                type="text"
                                name="propietario"
                                class="form-control"
                                placeholder="Nombre completo"
                                maxlength="150"
                                required>

                        </div>


                    </div>


                    <!-- BOTONES -->

                    <div class="botones">


                        <a
                            href="${pageContext.request.contextPath}/vehiculos"
                            class="btn-cancelar">

                            <i class="fa-solid fa-arrow-left"></i>

                            Cancelar

                        </a>


                        <button
                            type="submit"
                            class="btn-principal">

                            <i class="fa-solid fa-floppy-disk"></i>

                            Registrar vehículo

                        </button>


                    </div>


                </form>


            </div>

        </div>


    </section>


</main>


</body>

</html>