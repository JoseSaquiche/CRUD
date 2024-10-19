
package dbproyect;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DbProyect {
    private static final String URL = "jdbc:mysql://localhost:3306/dbtequilera";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }// fin conectar()
    
    public static void insertarProducto() {
        Scanner entrada= new Scanner(System.in);
        System.out.println("Ingrese el codigo del producto");
        String codigo = entrada.nextLine();
        System.out.println("ingrese el nombre del producto");
        String nombre = entrada.nextLine();
        System.out.println("ingrese el precio del producto");
        float precio = entrada.nextFloat();
        System.out.println("ingrese la cantidad del producto");
        int cantidad = entrada.nextInt();
        String fecha = entrada.nextLine();
    String query = "INSERT INTO producto (codigoProducto, nombreProducto, precioUnitario, cantidadProducto, fechaVencimiento) VALUES (?,?, ?, ?, ?)";
    try (Connection con = DbProyect.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, codigo);
        pst.setString(2, nombre);
        pst.setDouble(3, precio);
        pst.setInt(4, cantidad);
        pst.setDate(5, java.sql.Date.valueOf(fecha));
        pst.executeUpdate();
        System.out.println("Producto insertado correctamente");
    } catch (SQLException e) {
    }
}// fin insertarProducto()
    public static void listarProductos() {
    String query = "select * from producto;";  
    try (Connection con = DbProyect.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
        boolean hayResultados = false;
        while (rs.next()) {
            hayResultados = true; 
            System.out.println("Código: " + rs.getString("codigoProducto"));
            System.out.println("Nombre: " + rs.getString("nombreProducto"));
            System.out.println("Precio: " + rs.getDouble("precioUnitario"));
            System.out.println("Cantidad: " + rs.getInt("cantidadProducto"));
            System.out.println("Fecha de Vencimiento: " + rs.getDate("fechaVencimiento"));     
            System.out.println("");
        }
        if (!hayResultados) {
            System.out.println("No hay productos disponibles.");
        }//fin if
     
    } catch (SQLException e) {
        
    }//fin catch
}// fin listarProductos()
    
    public static void actualizarProducto() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("ingrese el nombre del producto");
        String nombre = entrada.nextLine();
        System.out.println("ingrese el precio del producto");
        float precio = entrada.nextFloat();
        System.out.println("ingrese el codigo del producto");
        String codigoProducto = entrada.nextLine();
        
    String query = "UPDATE producto SET nombreProducto = ?, precioUnitario = ? WHERE codigoProducto = ?";
    try (Connection con = DbProyect.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, nombre);
        pst.setDouble(2, precio);
        pst.setString(3, codigoProducto);
        pst.executeUpdate();
        System.out.println("Producto actualizado correctamente");
    } catch (SQLException e) {
    }
}// fin actualizarProducto
    
public static void eliminarProducto() {
    Scanner entrada = new Scanner(System.in);
    System.out.println("ingrese el codigo del producto que desee eliminar");
        String codigoProducto = entrada.nextLine();
    String query = "DELETE FROM producto WHERE codigoProducto = ?";
    try (Connection con = DbProyect.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, codigoProducto);
        pst.executeUpdate();
        System.out.println("Producto eliminado correctamente");
    } catch (SQLException e) {
        //e.printStackTrace();
    }
}// fin eliminarProducto
public static void buscarProducto(){
   Scanner entrada = new Scanner(System.in); 
    System.out.println("Ingrese el codigo del producto que desea buscar");
    String codigo = entrada.nextLine();
    String query = "SELECT * FROM producto WHERE codigo = ?";
    try (Connection con = DbProyect.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
     pst.setString(1, codigo);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("\nProducto encontrado:");
                System.out.println("Codigo: " + rs.getString("codigoProducto"));
                System.out.println("Nombre: " + rs.getString("nombreProducto"));
                System.out.println("Precio: " + rs.getDouble("precioUnitario"));
                System.out.println("Cantidad: " + rs.getInt("cantidadProducto"));
                System.out.println("Fecha de Vencimiento: " + rs.getDate("fechaVencimiento"));
                System.out.println("");
            } else {
                System.out.println("\nNo se encontró ningún producto con el código: " + codigo);
            }

       
    }catch (SQLException e){
        System.out.println("\n ERROR al buscar el producto:"+ e.getMessage());
    }
}
             
        
    public static void main(String[] args) throws InterruptedException {
       Scanner entrada = new Scanner(System.in);
        int opcion;
        //conectar();
        //insertarProducto("VRG100", "Virogrip", 50, 150, "2024-10-31");
        //listarProductos();
        //actualizarProducto("VRG100","Virogrip funcional",150.99);
        //eliminarProducto("VRG100");
        //listarProductos();    
        do{
        System.out.println("****BIENVENIDO AL MENU****");
        System.out.println("SELECCIONE UNA OPCION");
        System.out.println("1)VER LISTA DE PRODUCTO");
        System.out.println("2)INGRESAR PRODUCTO");
        System.out.println("3)ACTUALIZAR PRODUCTO");
        System.out.println("4) ELIMINAR PRODUCTO");
        System.out.println("5)BUSCAR PRODUCTO");
        opcion = entrada.nextInt();
        switch(opcion){
            
            case 1:
                    listarProductos();
                    Thread.sleep(3000);
                break;
                
                case 2:
                    insertarProducto();
                    Thread.sleep(3000);
                break;
                
                case 3:
                    actualizarProducto();
                    Thread.sleep(3000);
                break;
                
                case 4:
                    eliminarProducto();
                    Thread.sleep(3000);
                break;
                
                case 5:
                    buscarProducto();
                    Thread.sleep(3000);
                break;
        }
        }while(opcion !=6);
    }
}

    
// fin main


