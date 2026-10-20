<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Editar vehículo - VEHICONTROL</title>

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

        /* Barra lateral */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
            display: flex;
            flex-direction: column;
            width: 250px;
            height: 100vh;
            padding: 25px 18px;
            background: linear-gradient(180deg, #021b4f, #01153d);
            color: white;
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 35px;
            padding-left: 8px;
            font-size: 22px;
            font-weight: bold;
        }

        .logo i {
            font-size: 28px;
        }

        .menu-title {
            margin: 0 8px 10px;
            color: #9fb5df;
            font-size: 12px;
            text-transform: uppercase;
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
            transition: background 0.2s;
        }

        .menu a:hover,
        .menu a.active {
            background: rgba(255, 255, 255, 0.15);
            color: white;
        }

        .menu a i {
            width: 22px;
            text-align: center;
        }

        .support {
            margin-top: auto;
            padding: 15px;
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.08);
            color: #dbe7ff;
            font-size: 13px;
        }

        .support strong {
            display: block;
            margin-bottom: 5px;
            color: white;
        }

        /* Área principal */
        .main {
            min-height: 100vh;
            margin-left: 250px;
        }

        .topbar {
            height: 75px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
            background: white;
            border-bottom: 1px solid #e5e7eb;
        }

        .topbar h1 {
            margin: 0;
            color: #172554;
            font-size: 24px;
            font-weight: 700;
        }

        .profile {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .profile-icon {
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background: #e8eefc;
            color: #123b7a;
        }

        .profile-text strong,
        .profile-text span {
            display: block;
        }

        .profile-text strong {
            font-size: 14px;
        }

        .profile-text span {
            color: #6b7280;
            font-size: 12px;
        }

        .content {
            padding: 30px;
        }

        .page-title {
            margin-bottom: 25px;
        }

        .page-title h2 {
            margin: 0 0 5px;
            color: #172554;
            font-size: 27px;
        }

        .page-title p {
            margin: 0;
            color: #6b7280;
        }

        /* Tarjeta */
        .card-formulario {
            overflow: hidden;
            background: white;
            border: none;
            border-radius: 14px;
            box-shadow: 0 5px 20px rgba(15, 23, 42, 0.07);
        }

        .card-header-custom {
            padding: 20px 22px;
            background: white;
            border-bottom: 1px solid #edf0f4;
        }

        .card-header-custom h5 {
            margin: 0;
            color: #1f2937;
            font-size: 18px;
            font-weight: 700;
        }

        .card-header-custom h5 i {
            color: #0d6efd;
        }

        .card-body-custom {
            padding: 25px 22px;
        }

        .form-label {
            margin-bottom: 7px;
            color: #374151;
            font-size: 14px;
            font-weight: 600;
        }

        .form-control {
            padding: 11px 13px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            font-size: 14px;
        }

        .form-control:focus {
            border-color: #0d6efd;
            box-shadow: 0 0 0 3px rgba(13, 110, 253, 0.10);
        }

        .id-info {
            min-height: 45px;
            display: flex;
            align-items: center;
            padding: 11px 13px;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            background: #f8fafc;
            color: #64748b;
            font-size: 14px;
        }

        .id-info strong {
            color: #172554;
        }

        .botones {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 5px;
            padding-top: 22px;
            border-top: 1px solid #edf0f4;
        }

        .btn-principal,
        .btn-cancelar {
            padding: 10px 17px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
        }

        .btn-principal {
            border: none;
            background: #0d6efd;
            color: white;
            cursor: pointer;
        }

        .btn-principal:hover {
            background: #0b5ed7;
            color: white;
        }

        .btn-cancelar {
            border: 1px solid #e2e8f0;
            background: #f1f5f9;
            color: #475569;
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

            .content {
                padding: 25px 20px;
            }
        }

        @media (max-width: 700px) {
            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }

            .support {
                margin-top: 20px;
            }

            .main {
                margin-left: 0;
            }

            .topbar {
                height: auto;
                min-height: 70px;
                padding: 12px 20px;
            }

            .topbar h1 {
                font-size: 20px;
            }

            .content {
                padding: 20px 15px;
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

        <div class="menu-title">Menú principal</div>

        <nav class="menu">
            <a href="${pageContext.request.contextPath}/dashboard">
                <i class="fa-solid fa-gauge-high"></i>
                <span>Dashboard</span>
            </a>

            <a href="${pageContext.request.contextPath}/vehiculos"
               class="active">
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
            Contacta al administrador
        </div>
    </aside>

    <main class="main">
        <header class="topbar">
            <h1>Editar vehículo</h1>

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
                <h2>Editar vehículo</h2>
                <p>Modifica la información del vehículo registrado en VEHICONTROL.</p>
            </div>

            <div class="card-formulario">
                <div class="card-header-custom">
                    <h5>
                        <i class="fa-solid fa-car-side me-2"></i>
                        Información del vehículo
                    </h5>
                </div>

                <div class="card-body-custom">
                    <form
                        action="${pageContext.request.contextPath}/vehiculos"
                        method="post">

                        <input type="hidden" name="accion" value="actualizar">
                        <input type="hidden" name="id" value="${vehiculo.id}">

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">ID del vehículo</label>
                                <div class="id-info">
                                    <strong>#${vehiculo.id}</strong>
                                    <span>&nbsp; Identificador del registro</span>
                                </div>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label for="placa" class="form-label">Placa</label>
                                <input
                                    type="text"
                                    id="placa"
                                    name="placa"
                                    class="form-control"
                                    value="${vehiculo.placa}"
                                    maxlength="10"
                                    required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label for="marca" class="form-label">Marca</label>
                                <input
                                    type="text"
                                    id="marca"
                                    name="marca"
                                    class="form-control"
                                    value="${vehiculo.marca}"
                                    maxlength="50"
                                    required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label for="modelo" class="form-label">Modelo</label>
                                <input
                                    type="text"
                                    id="modelo"
                                    name="modelo"
                                    class="form-control"
                                    value="${vehiculo.modelo}"
                                    maxlength="50"
                                    required>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label for="color" class="form-label">Color</label>
                                <input
                                    type="text"
                                    id="color"
                                    name="color"
                                    class="form-control"
                                    value="${vehiculo.color}"
                                    maxlength="30"
                                    required>
                            </div>

                            <div class="col-md-6 mb-4">
                                <label for="propietario" class="form-label">
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

                        <div class="botones">
                            <a
                                href="${pageContext.request.contextPath}/vehiculos"
                                class="btn-cancelar">
                                <i class="fa-solid fa-arrow-left me-1"></i>
                                Cancelar
                            </a>

                            <button type="submit" class="btn-principal">
                                <i class="fa-solid fa-floppy-disk me-1"></i>
                                Guardar cambios
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </main>

</body>
</html>