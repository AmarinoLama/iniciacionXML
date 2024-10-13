package edu.badpals.Ejercicio3;

public class Pedido {

    private String nombreCliente;
    private int idPedido;
    private Producto[] productos;

    public Pedido(int idPedido, String nombreCliente, Producto[] productos) {
        this.nombreCliente = nombreCliente;
        this.idPedido = idPedido;
        this.productos = productos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public Producto[] getProductos() {
        return productos;
    }
}
