package compiler.psj;

public class Personaje {

  private String nombre;
  private int vida;
  private int fuerza;
  private int magia;
  private int fuerza_magica;
  private int costo_magico;

  public Personaje(String nombre, int vida, int fuerza, int magia, int fuerza_magica, int costo_magico) {
    this.nombre = nombre;
    this.vida = vida;
    this.fuerza = fuerza;
    this.magia = magia;
    this.fuerza_magica = fuerza_magica;
    this.costo_magico = costo_magico;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getVida() {
    return vida;
  }

  public void setVida(int vida) {
    this.vida = vida;
  }

  public int getFuerza() {
    return fuerza;
  }

  public void setFuerza(int fuerza) {
    this.fuerza = fuerza;
  }

  public int getMagia() {
    return magia;
  }

  public void setMagia(int magia) {
    this.magia = magia;
  }

  public int getFuerza_magica() {
    return fuerza_magica;
  }

  public void setFuerza_magica(int fuerza_magica) {
    this.fuerza_magica = fuerza_magica;
  }

  public int getCosto_magico() {
    return costo_magico;
  }

  public void setCosto_magico(int costo_magico) {
    this.costo_magico = costo_magico;
  }
}
