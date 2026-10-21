<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="theme-color" content="#102f50">
    <title>Vehicontrol | Gestión vehicular</title>
    <link rel="preconnect" href="https://cdnjs.cloudflare.com" crossorigin>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <style>
        :root {
            color-scheme: light;
            --navy: #102f50;
            --navy-deep: #0b223b;
            --blue: #2367a5;
            --teal: #39c6b0;
            --ink: #172b40;
            --muted: #66788b;
            --line: #e5edf4;
            --surface: #ffffff;
        }
        * { box-sizing: border-box; }
        body {
            margin: 0;
            min-height: 100vh;
            padding: 32px;
            display: grid;
            place-items: center;
            overflow-x: hidden;
            font-family: Inter, "Segoe UI", Arial, sans-serif;
            color: var(--ink);
            background:
                radial-gradient(ellipse at 12% 12%, rgba(57,198,176,.13), transparent 30%),
                radial-gradient(ellipse at 88% 90%, rgba(35,103,165,.13), transparent 32%),
                #f1f5f9;
        }
        .page { width: min(100%, 1040px); }
        .card {
            display: grid;
            grid-template-columns: 1.04fr .96fr;
            min-height: 570px;
            overflow: hidden;
            background: var(--surface);
            border: 1px solid rgba(16,47,80,.08);
            border-radius: 26px;
            box-shadow: 0 28px 75px rgba(18,46,72,.16), 0 3px 12px rgba(18,46,72,.04);
        }
        .hero {
            position: relative;
            isolation: isolate;
            display: flex;
            align-items: center;
            padding: 64px 58px;
            overflow: hidden;
            color: #fff;
            background: linear-gradient(145deg, #163e64 0%, var(--navy) 55%, var(--navy-deep) 100%);
        }
        .hero::before, .hero::after {
            position: absolute;
            z-index: -1;
            content: "";
            border: 1px solid rgba(255,255,255,.09);
            border-radius: 50%;
        }
        .hero::before { width: 440px; height: 440px; right: -220px; top: -175px; box-shadow: 0 0 0 38px rgba(255,255,255,.025), 0 0 0 78px rgba(255,255,255,.02); }
        .hero::after { width: 260px; height: 260px; left: -165px; bottom: -160px; box-shadow: 0 0 0 30px rgba(255,255,255,.025); }
        .hero-content { max-width: 410px; }
        .brand-row { display: flex; align-items: center; gap: 15px; margin-bottom: 48px; }
        .brand-mark {
            width: 52px; height: 52px; display: grid; place-items: center;
            color: var(--teal); font-size: 22px;
            background: rgba(255,255,255,.1); border: 1px solid rgba(255,255,255,.13); border-radius: 16px;
            box-shadow: inset 0 1px 0 rgba(255,255,255,.1);
        }
        .brand-name { font-size: 15px; font-weight: 750; letter-spacing: .13em; }
        .eyebrow { margin: 0 0 14px; color: #8fe5d8; font-size: 11px; font-weight: 750; letter-spacing: .19em; text-transform: uppercase; }
        .hero h1 { max-width: 390px; margin: 0; font-size: clamp(34px, 4.3vw, 49px); line-height: 1.08; letter-spacing: -.045em; }
        .hero-copy { max-width: 365px; margin: 21px 0 0; color: #c4d2df; font-size: 16px; line-height: 1.75; }
        .feature-list { display: grid; gap: 15px; margin: 39px 0 0; padding: 0; list-style: none; color: #e1ebf3; font-size: 13px; }
        .feature-list li { display: flex; align-items: center; gap: 12px; }
        .feature-list i { width: 18px; color: var(--teal); font-size: 14px; text-align: center; }
        .panel { display: flex; flex-direction: column; justify-content: center; padding: 68px 62px; }
        .panel-label { display: inline-flex; align-items: center; gap: 8px; margin-bottom: 26px; color: var(--blue); font-size: 12px; font-weight: 700; letter-spacing: .07em; text-transform: uppercase; }
        .panel-label::before { width: 7px; height: 7px; content: ""; background: var(--teal); border-radius: 50%; box-shadow: 0 0 0 4px rgba(57,198,176,.14); }
        .panel h2 { margin: 0; color: var(--ink); font-size: 32px; letter-spacing: -.035em; }
        .panel-copy { max-width: 350px; margin: 13px 0 31px; color: var(--muted); font-size: 15px; line-height: 1.7; }
        .login-link {
            min-height: 54px; display: flex; align-items: center; justify-content: center; gap: 11px;
            padding: 0 22px; color: #fff; background: linear-gradient(100deg, #1b5688, #16446f);
            border: 1px solid rgba(16,47,80,.1); border-radius: 12px; box-shadow: 0 9px 19px rgba(22,68,111,.18);
            font-size: 14px; font-weight: 700; text-decoration: none;
            transition: transform .18s ease, box-shadow .18s ease, background .18s ease;
        }
        .login-link:hover { color: #fff; background: linear-gradient(100deg, #236ba7, #1a5485); box-shadow: 0 12px 24px rgba(22,68,111,.24); transform: translateY(-2px); }
        .login-link:focus-visible { outline: 3px solid rgba(57,198,176,.55); outline-offset: 4px; }
        .login-link i:last-child { margin-left: auto; color: #a8e9df; }
        .security-note { display: flex; align-items: flex-start; gap: 12px; margin-top: 30px; padding-top: 22px; color: var(--muted); border-top: 1px solid var(--line); font-size: 12px; line-height: 1.6; }
        .security-note i { margin-top: 2px; color: #238d83; font-size: 15px; }
        .security-note strong { display: block; margin-bottom: 2px; color: #344b61; font-size: 12px; }
        .footer { padding: 17px 8px 0; color: #8493a2; font-size: 11px; text-align: center; }
        @media (max-width: 760px) {
            body { padding: 20px; }
            .card { grid-template-columns: 1fr; min-height: 0; border-radius: 21px; }
            .hero { min-height: 330px; padding: 38px 34px; }
            .brand-row { margin-bottom: 34px; }
            .hero h1 { font-size: 37px; }
            .hero-copy { margin-top: 14px; font-size: 14px; }
            .feature-list { grid-template-columns: 1fr 1fr; gap: 13px; margin-top: 25px; font-size: 11px; }
            .panel { padding: 42px 34px; }
            .panel-label { margin-bottom: 19px; }
            .panel h2 { font-size: 28px; }
            .panel-copy { margin-bottom: 25px; }
        }
        @media (max-width: 420px) {
            body { padding: 12px; }
            .hero, .panel { padding-right: 25px; padding-left: 25px; }
            .hero { min-height: 315px; }
            .feature-list { grid-template-columns: 1fr; gap: 10px; }
            .security-note { margin-top: 24px; }
        }
        @media (prefers-reduced-motion: reduce) {
            *, *::before, *::after { scroll-behavior: auto !important; transition-duration: .01ms !important; }
        }
    </style>
</head>
<body>
<main class="page">
    <section class="card" aria-label="Página de bienvenida de Vehicontrol">
        <div class="hero">
            <div class="hero-content">
                <div class="brand-row">
                    <div class="brand-mark" aria-hidden="true"><i class="fa-solid fa-square-parking"></i></div>
                    <span class="brand-name">VEHICONTROL</span>
                </div>
                <p class="eyebrow">Gestión inteligente</p>
                <h1>Tu parqueadero, bajo control.</h1>
                <p class="hero-copy">Una plataforma sencilla para administrar vehículos, usuarios y movimientos desde un solo lugar.</p>
                <ul class="feature-list">
                    <li><i class="fa-solid fa-car-side" aria-hidden="true"></i> Control de vehículos</li>
                    <li><i class="fa-solid fa-clock-rotate-left" aria-hidden="true"></i> Historial de movimientos</li>
                    <li><i class="fa-solid fa-users" aria-hidden="true"></i> Gestión de usuarios</li>
                </ul>
            </div>
        </div>
        <div class="panel">
            <span class="panel-label">Sistema de parqueadero</span>
            <h2>Bienvenido</h2>
            <p class="panel-copy">Inicia sesión para consultar y administrar la operación de Vehicontrol.</p>
            <a class="login-link" href="${pageContext.request.contextPath}/login.jsp">
                <i class="fa-solid fa-right-to-bracket" aria-hidden="true"></i>
                <span>Iniciar sesión</span>
                <i class="fa-solid fa-arrow-right" aria-hidden="true"></i>
            </a>
            <div class="security-note">
                <i class="fa-solid fa-shield-halved" aria-hidden="true"></i>
                <div><strong>Acceso protegido</strong>Ingresa con tus credenciales para continuar.</div>
            </div>
        </div>
    </section>
    <footer class="footer">Vehicontrol · Control y administración vehicular</footer>
</main>
</body>
</html>
