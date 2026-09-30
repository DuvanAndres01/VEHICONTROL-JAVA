package com.vehicontrol.dao;

import com.vehicontrol.config.Conexion;
import com.vehicontrol.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) encargado de gestionar las operaciones
 * relacionadas con los usuarios del sistema VEHICONTROL.
 *
 * Esta clase permite realizar operaciones de autenticación,
 * consulta, creación, actualización, validación y eliminación
 * de usuarios almacenados en la base de datos.
 *
 * @author Duvan Arias
 */
public class UsuarioDAO {

    /**
     * Autentica un usuario utilizando su correo y contraseña.
     *
     * Solamente se permite el acceso a usuarios cuyo estado
     * se encuentre establecido como "activo".
     *
     * @param correo correo electrónico del usuario
     * @param password contraseña del usuario
     * @return objeto Usuario si las credenciales son correctas;
     *         null si no existe un usuario activo con esas credenciales
     */
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

            // Asigna las credenciales a los parámetros de la consulta.
            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    // Construye el objeto Usuario con los datos encontrados.
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

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * Los usuarios se ordenan de forma descendente utilizando
     * su identificador.
     *
     * @return lista de usuarios registrados
     */
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

            // Recorre los registros obtenidos de la base de datos.
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

    /**
     * Verifica si existe un usuario registrado con un correo
     * determinado.
     *
     * La comparación elimina espacios y no diferencia entre
     * mayúsculas y minúsculas.
     *
     * @param correo correo que se desea verificar
     * @return true si el correo ya existe; false en caso contrario
     */
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

    /**
     * Verifica si existe un correo registrado por otro usuario.
     *
     * Este método se utiliza principalmente durante la edición
     * de usuarios para permitir que el usuario conserve su propio
     * correo electrónico.
     *
     * @param correo correo que se desea verificar
     * @param id identificador del usuario que se debe excluir
     * @return true si el correo pertenece a otro usuario;
     *         false en caso contrario
     */
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

    /**
     * Cuenta la cantidad de usuarios que tienen asignado
     * el rol de administrador.
     *
     * @return cantidad de administradores registrados
     */
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

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario objeto Usuario con los datos que se desean registrar
     * @return true si el usuario fue insertado correctamente;
     *         false si no se realizó la inserción
     */
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

            // Asigna los datos del usuario a los parámetros SQL.
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

    /**
     * Busca un usuario mediante su identificador.
     *
     * @param id identificador del usuario
     * @return objeto Usuario si existe; null si no se encuentra
     */
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

    /**
     * Actualiza los datos principales de un usuario.
     *
     * La contraseña no se modifica mediante este método.
     *
     * @param usuario objeto Usuario con los datos actualizados
     * @return true si se actualizó el usuario;
     *         false si no se realizó ninguna actualización
     */
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

    /**
     * Elimina un usuario de la base de datos mediante su ID.
     *
     * Las validaciones relacionadas con permisos, usuario actual
     * y protección del último administrador se realizan en la capa
     * de servlet antes de ejecutar esta operación.
     *
     * @param id identificador del usuario que se desea eliminar
     * @return true si el usuario fue eliminado;
     *         false si no se encontró el registro
     */
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