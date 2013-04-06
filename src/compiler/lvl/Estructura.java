/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.lvl;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author ce
 */
public class Estructura extends HashMap<Point, Estructura.Casilla> {

  private String nombre;
  private Dimension tamanio;

  public Estructura(String nombre, Dimension tamanio) {
    this.nombre = nombre;
    this.tamanio = tamanio;
    llenarCasillas(tamanio);
  }

  public Point getKey(Point punto) {
    //return String.format("%d,%d",punto.x,punto.y);
    return punto;
  }

  public Casilla addCasilla(Casilla casilla) throws ArrayIndexOutOfBoundsException {
    Point key = getKey(casilla.getPunto());
    if (containsKey(key)) {
      return super.put(key, casilla);
    } else {
      throw new ArrayIndexOutOfBoundsException("No existe el punto para la casilla...");
    }

  }

  private void llenarCasillas(Dimension tamanio) {
    for (int j = 0; j < tamanio.getHeight(); j++) {
      for (int i = 0; i < tamanio.getWidth(); i++) {
        super.put(getKey(new Point(i, j)), null);
      }
    }
  }

  public Dimension getTamanio() {
    return tamanio;
  }

  public void setTamanio(Dimension tamanio) {
    this.tamanio = tamanio;
    llenarCasillas(tamanio);
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Point[] getNulls() {
    HashSet<Point> set = new HashSet<>();

    for (Map.Entry<Point, Casilla> entry : this.entrySet()) {
      Point point = entry.getKey();
      Casilla casilla = entry.getValue();
      if (casilla == null) {
        set.add(point);
      }

    }
    return Arrays.copyOf(set.toArray(), set.size(), Point[].class);
  }

  public static class Casilla {

    private Point punto;
    private Espacio tipo;

    public Casilla(Point punto, Espacio tipo) {
      this.punto = punto;
      this.tipo = tipo;
    }

    public Espacio getTipo() {
      return tipo;
    }

    public void setTipo(Espacio tipo) {
      this.tipo = tipo;
    }

    public Point getPunto() {
      return punto;
    }

    public void setPunto(Point punto) {
      this.punto = punto;
    }
  }
}
