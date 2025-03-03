package org.iesvegademijas.stream.test;

import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class TiendaTest {
    /**
     * Orden ascendente: ordena los datos alfabéticamente (de la A a la Z) o mediante valores numéricos ascendentes.
     * Orden descendente: ordena los datos en orden alfabético inverso (de la Z a la A) o mediante valores numéricos descendentes.
     * Números orden ascendente 0 - 10
     * Números orden descendente 10 - 0
     * */
    @Test
    void testSkeletonFrabricante() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            //TODO STREAMS
            Optional<Fabricante> skeletonFabricante = listFab.stream()
                    .filter(s -> s.getNombre().equalsIgnoreCase("Skeleton"))
                    .findAny();
            assertFalse(skeletonFabricante.isPresent());
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }


    @Test
    void testSkeletonProducto() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            Optional<Producto> skeletonFabricante = listProd.stream()
                    .filter(s -> s.getNombre().equalsIgnoreCase("Skeleton"))
                    .findAny();

            assertFalse(skeletonFabricante.isPresent());

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    @Test
    void testAllFabricante() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            assertEquals(9, listFab.size());

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void testAllProducto() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            assertEquals(11, listProd.size());

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }


    }

    /**
     * 1. Lista los nombres y los precios de todos los productos de la tabla producto
     */
    @Test
    void test1() {

        ProductoHome prodHome = new ProductoHome();

        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> nombre = listProd.stream().map(p -> "Nombre: " + p.getNombre() + " Precio: " + p.getPrecio()).toList();
            // nombre.forEach(p -> System.out.println(p));
            nombre.forEach(System.out::println);

            System.out.println("Set nombre lista");
            // Set<String[]> setNombrePrecio = listProd.stream().map(producto -> new Object[] {producto.getNombre(), Double.toString(producto.getPrecio())});
            Set<String[]> setNombrePrecio = listProd.stream().map(producto -> new String[]{producto.getNombre(), Double.toString(producto.getPrecio())})
                    .collect(toSet());

            setNombrePrecio.forEach(s -> System.out.println("Nombre: " + s[0] + " - " + "Precio: " + s[1]));

            assertEquals(11, setNombrePrecio.size());
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }


    }


    /**
     * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
     */
    @Test
    void test2() {

        ProductoHome prodHome = new ProductoHome();

        try {
            prodHome.beginTransaction();
            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> precioPDolares = listProd.stream().
                    map(producto -> producto.getNombre() + " - " + new BigDecimal(Double.toString(producto.getPrecio())).multiply(new BigDecimal("1.0559952")).setScale(2, RoundingMode.HALF_UP)).toList();
            precioPDolares.forEach(System.out::println);
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
     */
    @Test
    void test3() {


        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> nombreMayuscula = listProd.stream().
                    map(producto -> producto.getNombre().toUpperCase() + " - " + producto.getPrecio()).toList();
            nombreMayuscula.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
     */
    @Test
    void test4() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<String> dosLetrasMayus = listFab.stream()
                    .map(fabricante -> fabricante.getNombre() + " - " + fabricante.getNombre().substring(0, 2).toUpperCase()).toList();

            dosLetrasMayus.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 5. Lista el código de los fabricantes que tienen productos.
     */
    @Test
    void test5() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();


            //TODO STREAMS
            List<Integer> fabricantesConProductos = listFab.stream().map(fabricante -> fabricante.getProductos().size()).filter(integer -> integer > 0).toList();
            fabricantesConProductos.forEach(codigo -> System.out.println("Código: " + codigo));
            // .filter(f->!f.getProductos().isEmpty())

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
     */
    @Test
    void test6() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            // TODO STREAMS
            List<String> listaReversa = listFab.stream().sorted(comparing(Fabricante::getNombre).reversed()).map(fabricante -> fabricante.getNombre()).toList();
            listaReversa.forEach(System.out::println);
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
     */
    @Test
    void test7() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            listProd.stream().sorted(comparing((Producto p) -> p.getNombre())
                            .thenComparing((comparing(Producto::getPrecio)).reversed()))
                    .map(producto -> producto.getNombre() + " - " + producto.getPrecio()).forEach(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 8. Devuelve una lista con los 5 primeros fabricantes.
     */
    @Test
    void test8() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<Fabricante> cinco = listFab.stream().limit(5).toList();
            assertEquals(5, cinco.size());
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
     */
    @Test
    void test9() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            //TODO STREAMS
            List<Fabricante> cinco = listFab.stream().skip(3).limit(2).toList();

            assertEquals(2, cinco.size());

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 10. Lista el nombre y el precio del producto más barato
     */
    @Test
    void test10() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            listProd.stream().sorted(comparing(Producto::getPrecio)).limit(1)
                    .map(producto -> producto.getNombre() + " - " + producto.getPrecio()).forEach(s -> System.out.println(s));

//            .min(comparingDouble(Producto::getPrecio))
//                    .map(p -> "Nombre: "+p.getNombre()+", Precio: "+p.getPrecio())
//                    .ifPresent(System.out::println);


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 11. Lista el nombre y el precio del producto más caro
     */
    @Test
    void test11() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS

            // en caso de que pueda haber null
            Optional<Producto> optionalProducto = listProd.stream()
                    .sorted(comparing(Producto::getPrecio).reversed())
                    .findFirst();
            optionalProducto.ifPresent(producto -> System.out.println(producto.getNombre() + " " + producto.getPrecio()));

//            .max(comparingDouble(Producto::getPrecio))
//                    .map(p -> "Nombre: "+p.getNombre()+", Precio: "+p.getPrecio())
//                    .ifPresent(System.out::println);

//            listProd.stream().sorted(comparing(Producto::getPrecio).reversed()).limit(1)
//                    .map(producto -> producto.getNombre() + " - " + producto.getPrecio()).forEach(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
     */
    @Test
    void test12() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> productosFabricanteDos = listProd.stream()
                    .filter(producto -> producto.getFabricante().getCodigo() == 2)
                    .map(producto -> producto.getNombre()).toList();
            productosFabricanteDos.forEach(s -> System.out.println("Producto: " + s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
     */
    @Test
    void test13() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> listaProductosPrecio = listProd.stream()
                    .filter(producto -> producto.getPrecio() <= 120)
                    .map(producto -> producto.getNombre() + " " + producto.getPrecio()).toList();
            listaProductosPrecio.forEach(s -> System.out.println("Producto: " + s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 14. Lista los productos que tienen un precio mayor o igual a 400€.
     */
    @Test
    void test14() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> listaProductosPrecio = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 400)
                    .map(producto -> producto.getNombre() + " " + producto.getPrecio()).toList();
            listaProductosPrecio.forEach(s -> System.out.println("Producto: " + s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
     */
    @Test
    void test15() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> listaProductosPrecio = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 80 && producto.getPrecio() <= 300)
                    .map(producto -> producto.getNombre() + " " + producto.getPrecio()).toList();
            listaProductosPrecio.forEach(s -> System.out.println("Producto: " + s));


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
     */
    @Test
    void test16() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> listaProductos = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 200 && producto.getFabricante().getCodigo() == 6)
                    .map(producto -> "Fabricante: " + producto.getFabricante().getCodigo() + " Producto: " + producto.getNombre() + " " + producto.getPrecio()).toList();
            listaProductos.forEach(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
     */
    @Test
    void test17() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            final Set<Integer> setValidos = new HashSet<>();
            setValidos.add(1);
            setValidos.add(3);
            setValidos.add(5);

            // estamos ahciedno una closyra, usando una variable externa al lambda obligatoriamente tiene que ser final,
            // si no la declaras fianl el compilador no se va a quejar si no la modificas claro

            List<String> pValidos = listProd.stream()
                    .filter(producto -> setValidos.contains(producto.getFabricante().getCodigo()))
                    .map(producto -> "Fabricante: " + producto.getFabricante().getCodigo() + " Producto: " + producto.getNombre() + " " + producto.getPrecio())
                    .toList();
            pValidos.forEach(integer -> System.out.println(integer));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 18. Lista el nombre y el precio de los productos en céntimos.
     */
    @Test
    void test18() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
//            List<String> centimos = listProd.stream()
//                    .map(producto -> producto.getNombre() + " " + producto.getPrecio() * 100)
//                    .toList();
//
//            centimos.forEach(s -> System.out.println(s));

            List<Object[]> listArrayObj = listProd.stream()
                    .map(p -> new Object[]{p.getNombre(), p.getPrecio()})
                    .toList();

            listArrayObj.forEach(objects -> System.out.println((String) objects[0] + " " + ((Double) objects[1] * 100)));

            assertEquals(8699.0, ((Double) listArrayObj.get(0)[1] * 100));
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
     */
    @Test
    void test19() {


        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<String> fabricantesConS = listFab.stream()
                    .filter(fabricante -> fabricante.getNombre().substring(0, 1).equals("S"))
                    .map(fabricante -> fabricante.getNombre()).toList();
            // .filter(f->f.getNombre().charAt(0)=='S')

            fabricantesConS.forEach(s -> System.out.println(s));
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
     */
    @Test
    void test20() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> protatiles = listProd.stream()
                    .filter(producto -> producto.getNombre().contains("Portátil"))
                    .map(producto -> producto.getNombre()).toList();
            // .map(Producto::getNombre)
            // .filter(nombre -> nombre.contains("Portátil")).toList();

            protatiles.forEach(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
     */
    @Test
    void test21() {


        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> monitoresBaratos = listProd.stream()
                    .filter(producto -> producto.getNombre().contains("Monitor") && producto.getPrecio() <= 215)
                    .map(producto -> producto.getNombre() + " " + producto.getPrecio()).toList();

            monitoresBaratos.forEach(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€.
     * Ordene el resultado en primer lugar por el precio (en orden descendente)  mas a menos
     * y en segundo lugar por el nombre (en orden ascendente). de Z a A
     */
    @Test
    void test22() {


        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> productos = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 180)
                    .sorted(comparing(Producto::getPrecio)
                            .thenComparing(Producto::getNombre).reversed())
                    .map(producto -> producto.getNombre() + " " + producto.getPrecio()).toList();

            productos.forEach(s -> System.out.println(s));

//            listProd.stream()
//                    // filter precio
//                    .filter(p->p.getPrecio()>=180)
//                    // ordenar por precio invertido
//                    .sorted(comparing(Producto::getPrecio)
//                            .reversed()
//                            // ordenar por nombre
//                            .thenComparing(Producto::getNombre))
//                    //formatear la salida
//                    .map(p ->"Nombre: "+ p.getNombre()+",Precio: "+p.getPrecio())
//                    .forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos.
     * Ordene el resultado por el nombre del fabricante, por orden alfabético.
     */
    @Test
    void test23() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<String> productos = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 180) // esto sobra xd
                    .sorted(comparing((Producto p) -> p.getFabricante().getNombre()))
                    .map(producto -> producto.getNombre() + " " + producto.getPrecio() + " " + producto.getFabricante().getNombre()).toList();

            productos.forEach(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
     */
    @Test
    void test24() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            Optional<String> optionalStringProducto = listProd.stream()
                    .sorted(comparing(Producto::getPrecio).reversed())
                    .findFirst()
                    // optional de producto
                    .map(p -> "Producto: " + p.getNombre() + " " + p.getPrecio() + " Fabricante: " + p.getFabricante().getNombre());
            // el map devuelve un optional de String

            optionalStringProducto.ifPresent(s -> System.out.println(s));

            // busqueda de un max, comparando por el precio por ejemplo
            Optional<String> optionalStringProducto2 = listProd.stream()
                    .max(comparing(Producto::getPrecio))
                    // optional de producto
                    .map(p -> "Producto: " + p.getNombre() + " " + p.getPrecio() + " Fabricante: " + p.getFabricante().getNombre());
            // el map devuelve un optional de String

            optionalStringProducto2.ifPresent(s -> System.out.println(s));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
     */
    @Test
    void test25() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<Producto> productosCrucial = listProd.stream()
                    .filter(p -> p.getPrecio() > 200 && p.getFabricante().getNombre().equals("Crucial"))
                    .toList();
            productosCrucial.forEach(p -> System.out.println(p));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
     */
    @Test
    void test26() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            Set<String> fabricantesValidos = new HashSet<>();
            fabricantesValidos.add("Asus");
            fabricantesValidos.add("Hewlett-Packard");
            fabricantesValidos.add("Seagate");

            List<Producto> productosValidos = listProd.stream()
                    .filter(p -> fabricantesValidos.contains(p.getFabricante().getNombre()))
                    .toList();
            productosValidos.forEach(p -> System.out.println(p));


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€.
     * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
     * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
     * La salida debe quedar tabulada como sigue:
     * <p>
     * Producto                Precio             Fabricante
     * -----------------------------------------------------
     * GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
     * Portátil Yoga 520      |452.79            |Lenovo
     * Portátil Ideapd 320    |359.64000000000004|Lenovo
     * Monitor 27 LED Full HD |199.25190000000003|Asus
     */
    @Test
    void test27() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<Producto> productosValidos = listProd.stream()
                    .filter(p -> p.getPrecio() >= 180)
                    .sorted(comparing(Producto::getPrecio).reversed()
                            .thenComparing(Producto::getNombre))
                    .toList();
            System.out.println(productosValidos.size());
            System.out.println("""
                    Producto                           Precio   Fabricante
                    ----------------------------------------------------------------""");
            Optional<Integer> maxNombre = listProd.stream()
                    .map(producto -> producto.getNombre().length())
                    .reduce(Integer::max);

            Optional<Integer> maxPrecio = listProd.stream()
                    .map(producto -> Double.toString(producto.getPrecio()).length())
                    .reduce(Integer::max);

            Optional<Integer> maxFabricante = listProd.stream()
                    .map(producto -> producto.getFabricante().getNombre().length())
                    .reduce(Integer::max);

            StringBuilder cadena = new StringBuilder();
            for (Producto p : productosValidos
            ) {
                cadena.append(p.getNombre()).append(" ");
                int espaciosNombre = maxNombre.get() - p.getNombre().length();
                for (int i = 0; i < espaciosNombre; i++) {
                    cadena.append(" ");
                }
                cadena.append(" | ").append(p.getPrecio());

                int espaciosPrecio = maxPrecio.get() - Double.toString(p.getPrecio()).length();
                for (int i = 0; i < espaciosPrecio; i++) {
                    cadena.append(" ");
                }

                cadena.append(" | ").append(p.getFabricante().getNombre());
                int espaciosFabricante = maxFabricante.get() - p.getFabricante().getNombre().length();
                for (int i = 0; i < espaciosFabricante; i++) {
                    cadena.append(" ");
                }
                cadena.append("\n");

            }

            String tabla = cadena.toString();
            System.out.println(tabla);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    @Test
    void test27B() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            //filtrar y ordenar que valen pa todos
            listProd = listProd.stream()
                    .filter(p -> p.getPrecio() >= 180)
                    .sorted(comparing(Producto::getPrecio).reversed()
                            .thenComparing(Producto::getNombre))
                    .toList();
            //obteniendo la longitud maxima para cada campo
//la longitud maxima no incluye la primera fila("Producto" "Precio" "Fabricante")
// porque en ese caso no es necesario
// si se quiere añadir hay que obtener una lista y añadirlos etc. y son lineas de codigos mas
            //lenght nombre
            int ln = listProd.stream()
                    .map(p -> p.getNombre().length())
                    .max(Integer::compareTo)
                    .orElse(0);
            //lenght producto
            int lp = listProd.stream()
                    .map(p -> (p.getPrecio() + "").length())
                    .max(Integer::compareTo)
                    .orElse(0);
            //lenght fabricante
            int lf = listProd.stream()
                    .map(p -> p.getFabricante().getNombre().length())
                    .max(Integer::compareTo)
                    .orElse(0);
            //imprimir la primera fila
            System.out.println(String.format("%1$-" + (ln + 1) + "s", "Producto")
                    + String.format("%1$-" + (lp + 1) + "s", "Precio")
                    + String.format("%1$-" + (lf) + "s", "Fabricante"));
            //imprimir los "-", +2 por 2"|"
            System.out.println("-".repeat(ln + lp + lf + 2));
            //imprimir los productos
            listProd.stream()
                    .map(p -> String.format("%1$-" + ln + "s", p.getNombre()) + "|"
                            + String.format("%1$-" + lp + "s", p.getPrecio()) + "|"
                            + String.format("%1$-" + lf + "s", p.getFabricante().getNombre()))
                    .forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos.
     * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
     * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
     * La salida debe queda como sigue:
     * Fabricante: Asus
     * <p>
     * Productos:
     * Monitor 27 LED Full HD
     * Monitor 24 LED Full HD
     * <p>
     * Fabricante: Lenovo
     * <p>
     * Productos:
     * Portátil Ideapd 320
     * Portátil Yoga 520
     * <p>
     * Fabricante: Hewlett-Packard
     * <p>
     * Productos:
     * Impresora HP Deskjet 3720
     * Impresora HP Laserjet Pro M26nw
     * <p>
     * Fabricante: Samsung
     * <p>
     * Productos:
     * Disco SSD 1 TB
     * <p>
     * Fabricante: Seagate
     * <p>
     * Productos:
     * Disco duro SATA3 1TB
     * <p>
     * Fabricante: Crucial
     * <p>
     * Productos:
     * GeForce GTX 1080 Xtreme
     * Memoria RAM DDR4 8GB
     * <p>
     * Fabricante: Gigabyte
     * <p>
     * Productos:
     * GeForce GTX 1050Ti
     * <p>
     * Fabricante: Huawei
     * <p>
     * Productos:
     * <p>
     * <p>
     * Fabricante: Xiaomi
     * <p>
     * Productos:
     */
    @Test
    void test28() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            // TODO STREAMS
            // replace all
            String resultado = listFab.stream().reduce("", (acumulador, fabricante) -> {
                String resultado2 = "Fabricante: " + fabricante.getNombre() + "\n\nProductos\n";
                Optional<String> productos = fabricante.getProductos().stream()
                        .map(producto -> producto.getNombre() + "\n")
                        .reduce((s, s2) -> s + s2);

                if (productos.isPresent()) {
                    resultado2 += productos.get();
                }

                return acumulador + resultado2 + "\n";
            }, String::concat);

            System.out.println(resultado);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void test28B() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            listFab.stream()
                    //formatear la salida(sobretodo los \n y \t)
                    .map(f -> "Fabricante: " + f.getNombre() + "\n\n\tProductos:\n" +
                            //un segundo stream para los productos
                            f.getProductos().stream()
                                    //formatear la salida de producto
                                    .map(p -> "\t" + p.getNombre() + "\n")
                                    //para que no salta como lista, osea sin "[],"
                                    .collect(joining()))
                    .forEach(System.out::println);
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
     */
    @Test
    void test29() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<Fabricante> fabricantesSinProd = listFab.stream()
                    .filter(fabricante -> fabricante.getProductos().isEmpty()).toList();
            fabricantesSinProd.forEach(fabricante -> System.out.println(fabricante.getNombre()));

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
     */
    @Test
    void test30() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS

            long totalProductos = listProd.stream().count();

            assertEquals(11, totalProductos);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
     */
    @Test
    void test31() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            List<Integer> listaFabricantes = listProd.stream()
                    .map(producto -> producto.getFabricante().getCodigo())
                    .distinct().sorted().toList();

            listaFabricantes.forEach(integer -> System.out.println("Fabricante cod: " + integer));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 32. Calcula la media del precio de todos los productos
     */
    @Test
    void test32() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS

            double mediaTotal = listProd.stream().distinct()
                    .map(producto -> producto.getPrecio())
                    .reduce(0d, Double::sum) / listProd.stream().distinct().toList().size();

            BigDecimal bd = BigDecimal.valueOf(mediaTotal);
            bd = bd.setScale(2, RoundingMode.HALF_UP);

//            listProd.stream()
//                    //obtener la media
//                    .collect(averagingDouble(Producto::getPrecio)))
//            //redondear a dos decimales
//					.setScale(2,RoundingMode.HALF_UP));

            assertEquals(BigDecimal.valueOf(271.72), bd);
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
     */
    @Test
    void test33() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            Optional<Double> max = listProd.stream().map(producto -> producto.getPrecio())
                    .reduce(Double::min);
//          obtener el mas barato
//			.min(Double::compareTo)
//           lo mismo que get pero si no hubiera nada devuelve 0.0, el get te va a decir de ponerlo en un ifPresent
//           .orElse(0.0));
            max.ifPresent(aDouble -> assertEquals(59.99, aDouble));

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 34. Calcula la suma de los precios de todos los productos.
     */
    @Test
    void test34() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            double sumaTotal = listProd.stream().distinct()
                    .map(producto -> producto.getPrecio())
                    .reduce(0d, Double::sum);

//            .mapToDouble(Producto::getPrecio)
//                    //suma
//                    .sum());
            assertEquals(2988.96, sumaTotal);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 35. Calcula el número de productos que tiene el fabricante Asus.
     */
    @Test
    void test35() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            long productosAsus = listProd.stream()
                    .filter(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Asus"))
                    .count();

            assertEquals(2, productosAsus);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 36. Calcula la media del precio de todos los productos del fabricante Asus.
     */
    @Test
    void test36() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            long productosAsus = listProd.stream()
                    .filter(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Asus"))
                    .count();

            Optional<Double> mediaAsus = listProd.stream()
                    .filter(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Asus"))
                    .map(producto -> producto.getPrecio())
                    .reduce((aDouble, aDouble2) -> aDouble + aDouble2);

            assertEquals(2, productosAsus);

            mediaAsus.ifPresent(aDouble -> System.out.println(aDouble / productosAsus));


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial.
     * Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
     */
    @Test
    void test37() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            //TODO STREAMS
            Double[] resultado1 = listProd.stream()
                    .filter(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Crucial"))
                    .map(producto -> new Double[]{producto.getPrecio(), producto.getPrecio(), 1.0})
                    .reduce(
                            new Double[]{Double.MIN_VALUE, Double.MAX_VALUE, 0.0}, // si no ahy ningun elemento tendra el valro inical por eso es necesario declararlo
                            (acumulador, valores) -> new Double[]{
                                    Math.max(acumulador[0], valores[0]),
                                    Math.min(acumulador[1], valores[1]),
                                    acumulador[2] + valores[2]
                            }
                    );


            Optional<Double[]> resultado = listProd.stream()
                    .filter(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("Crucial"))
                    .map(producto -> new Double[]{producto.getPrecio(), producto.getPrecio(), 1.0})
                    .reduce(// pilla el pirmer leemnto con el formato dado por el map, valo rinicial del acumulador
                            (acc, val) -> new Double[]{
                                    Math.min(acc[0], val[0]), // Mínimo
                                    Math.max(acc[1], val[1]), // Máximo
                                    acc[2] + val[2]// Conteo
                            }
                    );
            // Optional<Double> minOp = resultado.map(doubles -> doubles[2]);
            System.out.println(Arrays.stream(resultado1).toList());

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 38. Muestra el número total de productos que tiene cada uno de los fabricantes.
     * El listado también debe incluir los fabricantes que no tienen ningún producto.
     * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene.
     * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
     * La salida debe queda como sigue:
     * <p>
     * Fabricante     #Productos
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
     * Asus              2
     * Lenovo              2
     * Hewlett-Packard              2
     * Samsung              1
     * Seagate              1
     * Crucial              2
     * Gigabyte              1
     * Huawei              0
     * Xiaomi              0
     */
    @Test
    void test38() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            String encabezado = String.format("%15s%15s%n", "Fabricante", "#Productos");
            encabezado += "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n";
            String resultado = listFab.stream()
                    .sorted(comparing(fabricante -> fabricante.getProductos().size(), reverseOrder()))
                    .reduce("", (acumulador, fabricante) -> {

                        int numProductos = fabricante.getProductos().size();
                        String resultado2 = String.format("%15s%15d", fabricante.getNombre(), numProductos);
                        return acumulador + resultado2 + "\n";
                    }, String::concat); // que si el tipo del acumulador y el elemento son difrentes tiene que haber un combinador

            System.out.println(encabezado + resultado);
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes.
     * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
     * Deben aparecer los fabricantes que no tienen productos.
     */
    @Test
    void test39() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            String resultado1 = listFab.stream()
                    .map(fabricante -> {
                        var calculosOptional = fabricante.getProductos().stream()
                                .map(producto -> new Double[]{producto.getPrecio(), producto.getPrecio(), producto.getPrecio(), 1.0})
                                .reduce((acumulador, valores) -> new Double[]{
                                                Math.max(acumulador[0], valores[0]),
                                                Math.min(acumulador[1], valores[1]),
                                                acumulador[2] + valores[2],
                                                acumulador[3] + valores[3]
                                        }
                                );
                        if (calculosOptional.isPresent()) {
                            var calculos = calculosOptional.get();

                            return String.format("%-20s Mínimo %8.2f    Máximo %8.2f    Media %8.2f \n", fabricante.getNombre(), calculos[1], calculos[0], (calculos[2] / calculos[3]));
                        }
                        return String.format("%-20s", fabricante.getNombre()) + " No tiene productos" + "\n";

                    }).collect(joining());

            System.out.println(resultado1);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€.
     * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
     */
    @Test
    void test40() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            String resultado1 = listFab.stream()
                    .map(fabricante -> {
                        var calculosOptional = fabricante.getProductos().stream()
                                .map(producto -> new Double[]{producto.getPrecio(), producto.getPrecio(), producto.getPrecio(), 1.0})
                                .reduce((acumulador, valores) -> new Double[]{
                                                Math.max(acumulador[0], valores[0]),
                                                Math.min(acumulador[1], valores[1]),
                                                acumulador[2] + valores[2],
                                                acumulador[3] + valores[3]
                                        }
                                );
                        if (calculosOptional.isPresent()) {
                            var calculos = calculosOptional.get();
                            double media = (calculos[2] / calculos[3]);
                            if (media >= 200) {
                                return String.format("%-20s Mínimo %8.2f    Máximo %8.2f    Media %8.2f \n", fabricante.getNombre() + " " + fabricante.getCodigo(), calculos[1], calculos[0], media);
                            }
                        }
                        return "";

                    }).collect(joining());

            System.out.println(resultado1);


            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
     */
    @Test
    void test41() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<Fabricante> fabDosProductos = listFab.stream().filter(fabricante -> fabricante.getProductos().size() >= 2)
                    .toList();
            fabDosProductos.forEach(fabricante -> System.out.printf("%-20s %5d %n", fabricante.getNombre(), fabricante.getProductos().size()));
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €.
     * Ordenado de mayor a menor número de productos.
     */
    @Test
    void test42() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<Object[]> fabDosProductos = listFab.stream().map(fabricante -> {
                        Set<Producto> productos = fabricante.getProductos().stream().filter(producto -> producto.getPrecio() >= 220).collect(toSet());
                        return new Object[]{(String) fabricante.getNombre(), (Integer) productos.size()};

                    })
                    .sorted(comparing(objects -> (int) objects[1], reverseOrder())).toList(); // no sabe como comparar si no se le dice el tipo
            fabDosProductos.forEach(objects -> System.out.println(objects[0] + " " + objects[1]));
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
     */
    @Test
    void test43() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            // reduce funcion de agregación, se puede usar de muchas maneras, combianr una secuenncia de elemntos en un mismo resultado
            //TODO STREAMS
            List<String> sumaMayorMil = listFab.stream()
                    .map(fabricante -> {
                        Double sumaPrecios = fabricante.getProductos().stream()
                                .map(producto -> producto.getPrecio())
                                .reduce(0.0, (aDouble, aDouble2) -> aDouble + aDouble2);
                        return new Object[]{fabricante.getNombre(), sumaPrecios};

                    })
                    .filter(objects -> (Double) objects[1] > 1000)
                    .map(objects -> (String) objects[0]).toList();

            sumaMayorMil.forEach(s -> System.out.println(s));

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
     * Ordenado de menor a mayor por cuantía de precio de los productos.
     */
    @Test
    void test44() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<String> sumaMayorMil = listFab.stream()
                    .map(fabricante -> {
                        Double sumaPrecios = fabricante.getProductos().stream()
                                .map(producto -> producto.getPrecio())
                                .reduce(0.0, (aDouble, aDouble2) -> aDouble + aDouble2);
                        return new Object[]{fabricante.getNombre(), sumaPrecios};

                    })
                    .filter(objects -> (Double) objects[1] > 1000).sorted(comparing(objects -> (Double) objects[1]))
                    .map(objects -> (String) objects[0]).toList();

            sumaMayorMil.forEach(s -> System.out.println(s));

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante.
     * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante.
     * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
     */
    @Test
    void test45() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            //TODO STREAMS
            List<Object[]> mayorProducto = listFab.stream()
                    .map(fabricante -> {
                        Optional<Producto> productoOptional = fabricante.getProductos().stream().reduce((producto, producto2) -> producto.getPrecio() > producto2.getPrecio() ? producto : producto2);
                        String nombre = "";
                        double precio = 0.0;

                        if (productoOptional.isPresent()) {
                            nombre = productoOptional.get().getNombre();
                            precio = productoOptional.get().getPrecio();
                        }

                        return new Object[]{fabricante.getNombre(), nombre, precio};
                    })
                    .sorted(comparing(objects -> (String) objects[0])).toList();

            for (Object[] objects : mayorProducto) {
                System.out.printf("%-20s %-33s %8.2f%n", objects[0], objects[1], (Double) objects[2]);
            }

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de los precios de los productos de su mismo fabricante.
     * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
     */
    @Test
    void test46() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            List<Object[]> listaProdMedia = listFab.stream().map(fabricante -> {
                var calculosOptional = fabricante.getProductos().stream()
                        .map(producto -> new Double[]{producto.getPrecio(), 1.0})
                        .reduce((acumulador, valores) -> new Double[]{
                                        acumulador[0] + valores[0],
                                        acumulador[1] + valores[1]
                                }
                        );
                if (calculosOptional.isPresent()) {
                    var calculos = calculosOptional.get();
                    double media = (calculos[0] / calculos[1]);

                    var productosFiltrados = fabricante.getProductos().stream()
                            .filter(producto -> producto.getPrecio() >= media)
                            .sorted(comparing(producto -> producto.getPrecio())).toList();

                    return new Object[]{fabricante.getNombre(), productosFiltrados};
                }
                return new Object[]{fabricante.getNombre()};
            }).filter(objects -> objects.length == 2).sorted(comparing(objects -> (String) objects[0], reverseOrder())).toList();

            //TODO STREAMS
            for (Object[] objects : listaProdMedia) {
                System.out.printf("Fabricante: %-20s%n", objects[0]);
                ((List<Producto>) objects[1]).forEach(producto -> System.out.printf("  %-20s%n", producto.getNombre()));
            }

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

}

