
package Modelo;

import java.sql.*;
import java.util.*;


public class DataBase {

    private final String URL = "jdbs:sqlserver://localhost:1433;databaseName=PRESTAMO;"
            + "integratedSecurity=true;" + "encript=true;trustServerCertificate=true";

    private Connection conexion;

    public DataBase() {
        try {
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión Establecida");
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
        }
    }

    public int Actualizar(String consulta) {
        try {
            Statement st = conexion.createStatement();
            return st.executeUpdate(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List Organizardatos(ResultSet rs) {
        List filas = new ArrayList();
        try {
            int numColumnas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> renglon = new HashMap();
                for (int i = 1; i <= numColumnas; i++) {
                    String nombreCampo = rs.getMetaData().getColumnName(i);
                    Object valor = rs.getObject(nombreCampo);
                    renglon.put(nombreCampo, valor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;

    }

    public List Listar(String consulta) {
        ResultSet rs = null;
        List resultado = new ArrayList();
        try {
            Statement st = conexion.createStatement();
            rs = st.executeQuery(consulta);
            resultado = Organizardatos(rs);
        } catch (Exception e) {
            System.out.println("No se realizó la consulta ");
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean ejecutarProcedimiento(String nombre) {
        try {
            CallableStatement cs = conexion.prepareCall("{call" + nombre + "}");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
