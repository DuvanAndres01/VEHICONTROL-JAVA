<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>VEHICONTROL - Iniciar sesión</title>

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
            min-height: 100vh;
            font-family: Arial, Helvetica, sans-serif;
            background:
                radial-gradient(
                    circle at 15% 20%,
                    rgba(13, 110, 253, 0.22),
                    transparent 30%
                ),
                linear-gradient(
                    135deg,
                    #01143d 0%,
                    #021b4f 45%,
                    #062d68 100%
                );
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 25px;
            overflow-x: hidden;
        }


        /* ==========================================
           CONTENEDOR PRINCIPAL
           ========================================== */

        .login-wrapper {
            width: 100%;
            max-width: 1080px;
            min-height: 650px;
            background: #ffffff;
            border-radius: 28px;
            overflow: hidden;
            display: grid;
            grid-template-columns: 1.05fr 0.95fr;
            box-shadow:
                0 30px 80px rgba(0, 0, 0, 0.35);
        }


        /* ==========================================
           PANEL IZQUIERDO
           ========================================== */

        .visual-panel {
            position: relative;
            min-height: 650px;
            padding: 45px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            overflow: hidden;

            background:
                linear-gradient(
                    135deg,
                    rgba(1, 20, 61, 0.97),
                    rgba(2, 27, 79, 0.92)
                );
        }


        .visual-panel::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            border-radius: 50%;
            background: rgba(13, 110, 253, 0.13);
            top: -170px;
            right: -130px;
        }


        .visual-panel::after {
            content: "";
            position: absolute;
            width: 300px;
            height: 300px;
            border-radius: 50%;
            background: rgba(255, 255, 255, 0.04);
            bottom: -120px;
            left: -100px;
        }


        .visual-content {
            position: relative;
            z-index: 2;
        }


        /* ==========================================
           LOGO
           ========================================== */

        .brand {
            display: flex;
            align-items: center;
            gap: 14px;
            color: #ffffff;
        }


        .brand-icon {
            width: 55px;
            height: 55px;
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;

            background: rgba(255, 255, 255, 0.12);
            border: 1px solid rgba(255, 255, 255, 0.15);

            box-shadow:
                0 10px 25px rgba(0, 0, 0, 0.18);
        }


        .brand-icon i {
            font-size: 27px;
        }


        .brand-name {
            font-size: 23px;
            font-weight: 800;
            letter-spacing: 1px;
        }


        .brand-subtitle {
            font-size: 12px;
            color: #a9c4ed;
            margin-top: 2px;
        }


        /* ==========================================
           TEXTO PRINCIPAL
           ========================================== */

        .hero-content {
            margin-top: 70px;
            max-width: 470px;
        }


        .hero-badge {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 8px 13px;
            border-radius: 30px;

            background: rgba(255, 255, 255, 0.09);
            border: 1px solid rgba(255, 255, 255, 0.12);

            color: #dbe9ff;
            font-size: 12px;
            font-weight: 600;
        }


        .hero-badge i {
            font-size: 11px;
        }


        .hero-content h1 {
            color: #ffffff;
            font-size: 42px;
            line-height: 1.12;
            font-weight: 800;
            margin: 22px 0 18px;
        }


        .hero-content h1 span {
            color: #65a9ff;
        }


        .hero-content p {
            color: #b9cae4;
            font-size: 15px;
            line-height: 1.7;
            margin: 0;
        }


        /* ==========================================
           CARACTERÍSTICAS
           ========================================== */

        .features {
            display: flex;
            gap: 12px;
            margin-top: 32px;
            flex-wrap: wrap;
        }


        .feature {
            display: flex;
            align-items: center;
            gap: 8px;

            padding: 9px 12px;
            border-radius: 10px;

            background: rgba(255, 255, 255, 0.07);
            border: 1px solid rgba(255, 255, 255, 0.08);

            color: #dbe7f8;
            font-size: 12px;
        }


        .feature i {
            color: #72b0ff;
        }


        /* ==========================================
           PIE PANEL IZQUIERDO
           ========================================== */

        .visual-footer {
            position: relative;
            z-index: 2;
            color: #7894bd;
            font-size: 12px;
        }


        .status {
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }


        .status-dot {
            width: 8px;
            height: 8px;
            background: #35d07f;
            border-radius: 50%;
            box-shadow: 0 0 0 5px rgba(53, 208, 127, 0.12);
        }


        /* ==========================================
           PANEL LOGIN
           ========================================== */

        .login-panel {
            background: #ffffff;
            padding: 55px 55px;
            display: flex;
            align-items: center;
        }


        .login-content {
            width: 100%;
            max-width: 390px;
            margin: auto;
        }


        .mobile-logo {
            display: none;
        }


        .login-header {
            margin-bottom: 32px;
        }


        .login-header h2 {
            margin: 0;
            color: #172554;
            font-size: 29px;
            font-weight: 800;
        }


        .login-header p {
            margin-top: 9px;
            margin-bottom: 0;
            color: #718096;
            font-size: 14px;
        }


        /* ==========================================
           ALERTA
           ========================================== */

        .alert-custom {
            display: flex;
            align-items: flex-start;
            gap: 12px;

            background: #fff4f4;
            border: 1px solid #ffd2d2;
            color: #a51d2d;

            border-radius: 12px;
            padding: 13px 15px;
            font-size: 13px;
            margin-bottom: 22px;
        }


        .alert-custom i {
            margin-top: 2px;
        }


        /* ==========================================
           CAMPOS
           ========================================== */

        .form-group {
            margin-bottom: 20px;
        }


        .form-label-custom {
            display: block;
            color: #263653;
            font-size: 13px;
            font-weight: 700;
            margin-bottom: 8px;
        }


        .input-wrapper {
            position: relative;
        }


        .input-icon {
            position: absolute;
            left: 16px;
            top: 50%;
            transform: translateY(-50%);
            color: #8b98aa;
            font-size: 15px;
            z-index: 2;
        }


        .form-control-custom {
            width: 100%;
            height: 52px;

            border: 1px solid #dce3ec;
            border-radius: 12px;

            padding: 0 46px 0 45px;

            font-size: 14px;
            color: #172554;

            background: #f9fbfd;

            outline: none;

            transition: all 0.2s ease;
        }


        .form-control-custom:focus {
            border-color: #0d6efd;
            background: #ffffff;

            box-shadow:
                0 0 0 4px rgba(13, 110, 253, 0.10);
        }


        .form-control-custom::placeholder {
            color: #a3adba;
        }


        .password-toggle {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);

            border: none;
            background: transparent;

            color: #8b98aa;
            cursor: pointer;

            padding: 5px;
        }


        .password-toggle:hover {
            color: #0d6efd;
        }


        /* ==========================================
           BOTÓN
           ========================================== */

        .btn-login {
            width: 100%;
            height: 53px;

            border: none;
            border-radius: 12px;

            background:
                linear-gradient(
                    135deg,
                    #0d6efd,
                    #0757c9
                );

            color: #ffffff;

            font-size: 14px;
            font-weight: 700;

            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;

            cursor: pointer;

            box-shadow:
                0 10px 24px rgba(13, 110, 253, 0.25);

            transition: all 0.2s ease;
        }


        .btn-login:hover {
            transform: translateY(-2px);

            box-shadow:
                0 14px 28px rgba(13, 110, 253, 0.32);
        }


        .btn-login:active {
            transform: translateY(0);
        }


        /* ==========================================
           SEGURIDAD
           ========================================== */

        .security-info {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 7px;

            margin-top: 24px;

            color: #8995a7;
            font-size: 11px;
        }


        .security-info i {
            color: #28a76c;
        }


        .copyright {
            text-align: center;
            margin-top: 30px;

            color: #a3adba;
            font-size: 11px;
        }


        /* ==========================================
           ANIMACIONES
           ========================================== */

        .login-wrapper {
            animation: showLogin 0.6s ease-out;
        }


        @keyframes showLogin {

            from {
                opacity: 0;
                transform: translateY(20px) scale(0.98);
            }

            to {
                opacity: 1;
                transform: translateY(0) scale(1);
            }
        }


        /* ==========================================
           RESPONSIVE
           ========================================== */

        @media (max-width: 900px) {

            .login-wrapper {
                grid-template-columns: 1fr;
                max-width: 520px;
                min-height: auto;
            }


            .visual-panel {
                display: none;
            }


            .login-panel {
                padding: 45px 35px;
                min-height: 600px;
            }


            .mobile-logo {
                display: flex;
                justify-content: center;
                margin-bottom: 35px;
            }


            .mobile-logo-box {
                width: 65px;
                height: 65px;
                border-radius: 18px;

                display: flex;
                align-items: center;
                justify-content: center;

                background:
                    linear-gradient(
                        135deg,
                        #021b4f,
                        #0d6efd
                    );

                color: #ffffff;

                box-shadow:
                    0 12px 28px rgba(13, 110, 253, 0.25);
            }


            .mobile-logo-box i {
                font-size: 31px;
            }
        }


        @media (max-width: 500px) {

            body {
                padding: 12px;
            }


            .login-wrapper {
                border-radius: 20px;
            }


            .login-panel {
                padding: 35px 24px;
            }


            .login-header h2 {
                font-size: 25px;
            }


            .hero-content h1 {
                font-size: 34px;
            }
        }

    </style>

</head>


<body>


<div class="login-wrapper">


    <!-- ==========================================
         PANEL VISUAL
         ========================================== -->

    <section class="visual-panel">

        <div class="visual-content">


            <!-- LOGO -->

            <div class="brand">

                <div class="brand-icon">

                    <i class="fa-solid fa-shield-halved"></i>

                </div>

                <div>

                    <div class="brand-name">
                        VEHICONTROL
                    </div>

                    <div class="brand-subtitle">
                        Sistema de control vehicular
                    </div>

                </div>

            </div>


            <!-- TEXTO -->

            <div class="hero-content">

                <div class="hero-badge">

                    <i class="fa-solid fa-shield-check"></i>

                    SISTEMA DE SEGURIDAD

                </div>


                <h1>

                    Controla el acceso.
                    <br>

                    <span>Protege tu conjunto.</span>

                </h1>


                <p>

                    Administra de manera sencilla y segura
                    el ingreso y salida de vehículos,
                    visitantes y espacios de parqueadero
                    desde un solo lugar.

                </p>


                <div class="features">

                    <div class="feature">

                        <i class="fa-solid fa-car"></i>

                        Vehículos

                    </div>


                    <div class="feature">

                        <i class="fa-solid fa-right-left"></i>

                        Entradas y salidas

                    </div>


                    <div class="feature">

                        <i class="fa-solid fa-chart-line"></i>

                        Reportes

                    </div>

                </div>

            </div>

        </div>


        <!-- FOOTER -->

        <div class="visual-footer">

            <div class="status">

                <span class="status-dot"></span>

                Sistema operativo

            </div>

        </div>

    </section>


    <!-- ==========================================
         PANEL LOGIN
         ========================================== -->

    <section class="login-panel">

        <div class="login-content">


            <!-- LOGO PARA CELULAR -->

            <div class="mobile-logo">

                <div class="mobile-logo-box">

                    <i class="fa-solid fa-shield-halved"></i>

                </div>

            </div>


            <!-- ENCABEZADO -->

            <div class="login-header">

                <h2>
                    Bienvenido
                </h2>

                <p>
                    Ingresa tus credenciales para continuar
                </p>

            </div>


            <!-- ERROR -->

            <% if (request.getAttribute("error") != null) { %>

                <div class="alert-custom">

                    <i class="fa-solid fa-circle-exclamation"></i>

                    <div>
                        <%= request.getAttribute("error") %>
                    </div>

                </div>

            <% } %>


            <!-- FORMULARIO -->

            <form action="login" method="POST">


                <!-- CORREO -->

                <div class="form-group">

                    <label class="form-label-custom">
                        Correo electrónico
                    </label>


                    <div class="input-wrapper">

                        <i class="fa-solid fa-envelope input-icon"></i>


                        <input
                            type="email"
                            name="correo"
                            class="form-control-custom"
                            placeholder="admin@vehicontrol.com"
                            autocomplete="username"
                            required>

                    </div>

                </div>


                <!-- CONTRASEÑA -->

                <div class="form-group">

                    <label class="form-label-custom">
                        Contraseña
                    </label>


                    <div class="input-wrapper">

                        <i class="fa-solid fa-lock input-icon"></i>


                        <input
                            type="password"
                            name="password"
                            id="password"
                            class="form-control-custom"
                            placeholder="Ingresa tu contraseña"
                            autocomplete="current-password"
                            required>


                        <button
                            type="button"
                            class="password-toggle"
                            id="togglePassword"
                            aria-label="Mostrar contraseña">

                            <i
                                class="fa-solid fa-eye"
                                id="passwordIcon">
                            </i>

                        </button>

                    </div>

                </div>


                <!-- BOTÓN -->

                <button
                    type="submit"
                    class="btn-login"
                    id="btnLogin">

                    <i class="fa-solid fa-right-to-bracket"></i>

                    Ingresar al sistema

                </button>


            </form>


            <!-- SEGURIDAD -->

            <div class="security-info">

                <i class="fa-solid fa-lock"></i>

                Conexión protegida y acceso autorizado

            </div>


            <div class="copyright">

                © 2026 VEHICONTROL · Sistema de control vehicular

            </div>


        </div>

    </section>

</div>


<script>

    // ==========================================
    // MOSTRAR / OCULTAR CONTRASEÑA
    // ==========================================

    const togglePassword =
        document.getElementById("togglePassword");

    const password =
        document.getElementById("password");

    const passwordIcon =
        document.getElementById("passwordIcon");


    togglePassword.addEventListener(
        "click",
        function () {

            if (password.type === "password") {

                password.type = "text";

                passwordIcon.classList.remove(
                    "fa-eye"
                );

                passwordIcon.classList.add(
                    "fa-eye-slash"
                );

                togglePassword.setAttribute(
                    "aria-label",
                    "Ocultar contraseña"
                );

            } else {

                password.type = "password";

                passwordIcon.classList.remove(
                    "fa-eye-slash"
                );

                passwordIcon.classList.add(
                    "fa-eye"
                );

                togglePassword.setAttribute(
                    "aria-label",
                    "Mostrar contraseña"
                );
            }

        }
    );


    // ==========================================
    // EFECTO AL ENVIAR
    // ==========================================

    const loginForm =
        document.querySelector("form");

    const btnLogin =
        document.getElementById("btnLogin");


    loginForm.addEventListener(
        "submit",
        function () {

            btnLogin.disabled = true;

            btnLogin.innerHTML =
                '<i class="fa-solid fa-spinner fa-spin"></i>' +
                ' Ingresando...';

        }
    );

</script>


</body>

</html>