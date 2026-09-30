package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {


    // =========================================================
    // AUTENTICAR
    // =========================================================

    public Usuario autenticar(String correo, String password) {

        String sql = """
                SELECT id, nombre, correo, password, rol, estado
                FROM usuarios
                WHERE correo = ?
                AND password = ?
                AND estado = 'activo'
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));

                    return usuario;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL AUTENTICAR USUARIO: "
                    + e.getMessage(),
                    e
            );
        }

        return null;
    }


    // =========================================================
    // LISTAR
    // =========================================================

    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        String sql = """
                SELECT id, nombre, correo, password, rol, estado
                FROM usuarios
                ORDER BY id DESC
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));

                lista.add(usuario);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONSULTAR USUARIOS: "
                    + e.getMessage(),
                    e
            );
        }

        return lista;
    }


    // =========================================================
    // VERIFICAR CORREO
    // =========================================================

    public boolean existeCorreo(String correo) {

        String sql = """
                SELECT COUNT(*)
                FROM usuarios
                WHERE LOWER(TRIM(correo)) =
                      LOWER(TRIM(?))
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL VERIFICAR CORREO: "
                    + e.getMessage(),
                    e
            );
        }

        return false;
    }


    // =========================================================
    // VERIFICAR CORREO EXCEPTO EL USUARIO ACTUAL
    // =========================================================

    public boolean existeCorreoExceptoId(
            String correo,
            int id) {

        String sql = """
                SELECT COUNT(*)
                FROM usuarios
                WHERE LOWER(TRIM(correo)) =
                      LOWER(TRIM(?))
                AND id <> ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, correo);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL VERIFICAR CORREO: "
                    + e.getMessage(),
                    e
            );
        }

        return false;
    }


    // =========================================================
    // CONTAR ADMINISTRADORES
    // =========================================================

    public int contarAdministradores() {

        String sql = """
                SELECT COUNT(*)
                FROM usuarios
                WHERE LOWER(TRIM(rol)) = 'admin'
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL CONTAR ADMINISTRADORES: "
                    + e.getMessage(),
                    e
            );
        }

        return 0;
    }


    // =========================================================
    // INSERTAR
    // =========================================================

    public boolean insertar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (nombre, correo, password, rol, estado)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getEstado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL INSERTAR USUARIO: "
                    + e.getMessage(),
                    e
            );
        }
    }


    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Usuario buscarPorId(int id) {

        String sql = """
                SELECT id, nombre, correo, password, rol, estado
                FROM usuarios
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));

                    return usuario;
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL BUSCAR USUARIO: "
                    + e.getMessage(),
                    e
            );
        }

        return null;
    }


    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nombre = ?,
                    correo = ?,
                    rol = ?,
                    estado = ?
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getEstado());
            ps.setInt(5, usuario.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL ACTUALIZAR USUARIO: "
                    + e.getMessage(),
                    e
            );
        }
    }


    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM usuarios
                WHERE id = ?
                """;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ERROR AL ELIMINAR USUARIO: "
                    + e.getMessage(),
                    e
            );
        }
    }
}