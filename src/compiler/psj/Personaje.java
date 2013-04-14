package compiler.psj;

import java.awt.Image;
import java.awt.event.KeyEvent;

public class Personaje {

  private String nombre;
  private int vida;
  private int fuerza;
  private int magia;
  private int fuerza_magica;
  private int costo_magico;
  
  private Image imagen;
  
  private int[] keys=new int[]{KeyEvent.VK_UP,KeyEvent.VK_RIGHT,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,
                              KeyEvent.VK_Z,KeyEvent.VK_X};
  
  
  private Estado estado=Estado.VIVO;
  
  public enum Estado{MUERTO,VIVO,INVENCIBLE}
  
  public Personaje(String nombre, int vida, int fuerza, int magia, int fuerza_magica, int costo_magico) {
    this.nombre = nombre;
    this.vida = vida;
    this.fuerza = fuerza;
    this.magia = magia;
    this.fuerza_magica = fuerza_magica;
    this.costo_magico = costo_magico;
  }

  public Personaje(String nombre, int vida, int fuerza, int magia, int fuerza_magica, int costo_magico, Image imagen) {
    this.nombre = nombre;
    this.vida = vida;
    this.fuerza = fuerza;
    this.magia = magia;
    this.fuerza_magica = fuerza_magica;
    this.costo_magico = costo_magico;
    this.imagen = imagen;
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

  public Image getImagen() {
    return imagen;
  }

  public void setImagen(Image imagen) {
    this.imagen = imagen;
  }

  public void setKeys(int ... keys) {
    this.keys = keys;
  }

  public int[] getKeys() {
    return keys;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Estado getEstado() {
    return estado;
  }
  
  
  public void ganarVida(int cantidad){
    setVida(getVida()+cantidad);
  }
  
  public void ganarMagia(int cantidad){
    setMagia(getMagia()+cantidad);
  }
  
  public void reducirVida(int cantidad){
    setVida(getVida()-cantidad);
  }
  public void reducirMagia(int cantidad){
    setMagia(getMagia()-cantidad);
  }
  
  public boolean hacerAtaqueMagico(){
    int t=getMagia()-getCosto_magico();
    if(t>0){
      reducirMagia(getCosto_magico());
      return true;
    } else {
      return false;
    }
  }
  
  
  public void hacerDanio(int cantidad){
    if(getEstado()==Estado.INVENCIBLE){
      return;
    }
    reducirVida(cantidad);
    if(getVida()<=0){
      setEstado(Estado.MUERTO);
    }
  }
}
