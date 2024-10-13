package edu.badpals.Ejercicio3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;

public class XmlPedidos {

    public static void main(String[] args) {

        ArrayList<Pedido> pedidos = new ArrayList<>();

        Producto monitor = new Producto(100, "Monitor", 100.0);
        Producto rato = new Producto(101, "Rato", 10.0);
        Producto portatil = new Producto(102, "Portatil", 600.0);
        Producto tablet = new Producto(103, "Tablet", 400.0);
        Producto teclado = new Producto(104, "Teclado", 50);

        pedidos.add(new Pedido(1, "Cliente 1", new Producto[]{monitor, rato}));
        pedidos.add(new Pedido(2, "Cliente 2", new Producto[]{portatil, tablet}));
        pedidos.add(new Pedido(3, "Cliente 2", new Producto[]{portatil, tablet}));
        pedidos.add(new Pedido(4, "Cliente 3", new Producto[]{monitor, rato}));
        pedidos.add(new Pedido(5, "Cliente 4", new Producto[]{teclado}));
        pedidos.add(new Pedido(6, "Cliente 5", new Producto[]{monitor, rato}));

        Document doc = generateDOM(pedidos);

        createXML(doc);
    }

    public static void createXML(Document doc) {
        try {
            File f=new File("ref/pedidos.bat");
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StreamResult result = new StreamResult(f);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("¡Error! No se ha podido llevar a cabo la transformación.");
        }
    }

    public static Document generateDOM(ArrayList<Pedido> pedidos) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("pedidos");
            doc.appendChild(rootElement);

            for (Pedido pedido : pedidos) {
                Element pedidoElement = doc.createElement("pedido");

                Element idPedido = doc.createElement("idPedido");
                idPedido.appendChild(doc.createTextNode(String.valueOf(pedido.getIdPedido())));
                pedidoElement.appendChild(idPedido);

                Element nomeCliente = doc.createElement("nomeCliente");
                nomeCliente.appendChild(doc.createTextNode(pedido.getNombreCliente()));
                pedidoElement.appendChild(nomeCliente);


                Element productos = doc.createElement("productos");
                pedidoElement.appendChild(productos);

                for (Producto producto : pedido.getProductos()) {
                    Element productoElement = doc.createElement("producto");

                    Element idProducto = doc.createElement("idProducto");
                    idProducto.appendChild(doc.createTextNode(String.valueOf(producto.getIdProducto())));
                    productoElement.appendChild(idProducto);

                    Element descripcion = doc.createElement("descripcion");
                    descripcion.appendChild(doc.createTextNode(producto.getNombreProducto()));
                    productoElement.appendChild(descripcion);

                    Element precio = doc.createElement("precio");
                    precio.appendChild(doc.createTextNode(String.valueOf(producto.getPrecio())));
                    productoElement.appendChild(precio);

                    productos.appendChild(productoElement);
                }

                rootElement.appendChild(pedidoElement);
            }
            return doc;
        } catch (Exception ex) {
            System.err.println("Error al crear el documento DOM: " + ex.getMessage());
            return null;
        }
    }
}
