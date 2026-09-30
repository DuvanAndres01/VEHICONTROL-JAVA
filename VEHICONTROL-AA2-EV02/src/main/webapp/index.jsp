<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>VEHICONTROL</title>

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
                linear-gradient(
                    135deg,
                    #021b4f,
                    #01153d
                );

            display: flex;

            align-items: center;

            justify-content: center;

            color: white;
        }

        .container-principal {
            width: 100%;

            max-width: 900px;

            padding: 20px;
        }

        .card-principal {

            background: white;

            color: #1f2937;

            border-radius: 18px;

            overflow: hidden;

            box-shadow:
                0 20px 50px
                rgba(0,0,0,0.25);

            display: grid;

            grid-template-columns:
                1fr 1fr;

            min-height: 500px;
        }

        .lado-izquierdo {

            background:
                linear-gradient(
                    180deg,
                    #021b4f,
                    #01153d
                );

            display: flex;

            align-items: center;

            justify-content: center;

            text-align: center;

            padding: 40px;
        }

        .logo {

            width: 90px;

            height: 90px;

            border-radius: 50%;

            background:
                rgba(255,255,255,0.10);

            display: flex;

            align-items: center;

            justify-content: center;

            margin: 0 auto 25px;

            font-size: 45px;
        }

        .lado-izquierdo h1 {

            font-size: 34px;

            font-weight: 700;

            margin-bottom: 15px;

            letter-spacing: 1px;
        }

        .lado-izquierdo p {

            color: #cbd5e1;

            line-height: 1.6;

            margin: 0;
        }

        .lado-derecho {

            padding: 55px 45px;

            display: flex;

            flex-direction: column;

            justify-content: center;
        }

        .lado-derecho h2 {

            font-size: 26px;

            font-weight: 700;

            color: #111827;

            margin-bottom: 10px;
        }

        .lado-derecho p {

            color: #6b7280;

            margin-bottom: 30px;
        }

        .btn-login {

            width: 100%;

            padding: 13px;

            background: #021b4f;

            color: white;

            border: none;

            border-radius: 8px;

            font-weight: 600;

            text-decoration: none;

            display: flex;

            align-items: center;

            justify-content: center;

            gap: 10px;

            transition: 0.2s;
        }

        .btn-login:hover {

            background: #032b76;

            color: white;

        }

        .info {

            margin-top: 25px;

            padding-top: 20px;

            border-top: 1px solid #e5e7eb;

            font-size: 13px;

            color: #6b7280;

            text-align: center;
        }

        .info i {

            color: #021b4f;

            margin-right: 5px;

        }

        @media (max-width: 700px) {

            .card-principal {

                grid-template-columns: 1fr;

            }

            .lado-izquierdo {

                padding: 35px 25px;

            }

            .lado-derecho {

                padding: 40px 30px;

            }

            .lado-izquierdo h1 {

                font-size: 28px;

            }

        }

    </style>

</head>

<body>

<div class="container-principal">

    <div class="card-principal">


        <!-- LADO IZQUIERDO -->

        <div class="lado-izquierdo">

            <div>

                <div class="logo">

                    <i class="fa-solid fa-shield-halved"></i>

                </div>

                <h1>
                    VEHICONTROL
                </h1>

                <p>

                    Sistema de control de entrada
                    y salida de vehículos.

                </p>

            </div>

        </div>


        <!-- LADO DERECHO -->

        <div class="lado-derecho">

            <h2>
                Bienvenido
            </h2>

            <p>
                Accede al sistema para administrar
                el control vehicular.
            </p>


            <a
                href="${pageContext.request.contextPath}/login.jsp"
                class="btn-login">

                <i class="fa-solid fa-right-to-bracket"></i>

                Iniciar sesión

            </a>


            <div class="info">

                <i class="fa-solid fa-shield-halved"></i>

                Sistema de control vehicular

            </div>

        </div>

    </div>

</div>

</body>

</html>