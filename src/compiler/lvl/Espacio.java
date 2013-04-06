package compiler.lvl;

import java.io.File;

public class Espacio {

  private String nombre;
  private File imagen;
  private Espacio extiende = null;
  private Especial especial = null;
  private Boolean pasable = true;
  private Boolean enemigo = false;
  private Boolean inicio = false;
  private Boolean fin = false;

  public Espacio(String nombre, File imagen) {
    this.nombre = nombre;
    this.imagen = imagen;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public File getImagen() {
    return imagen;
  }

  public void setImagen(File imagen) {
    this.imagen = imagen;
  }

  public Boolean getPasable() {
    return pasable;
  }

  public void setPasable(Boolean pasable) {
    if (pasable != null) {
      this.pasable = pasable;
    }
  }

  public Boolean getInicio() {
    return inicio;
  }

  public void setInicio(Boolean inicio) {
    if (inicio != null) {
      this.inicio = inicio;
    }
  }

  public Boolean getFin() {
    return fin;
  }

  public void setFin(Boolean fin) {
    if (fin != null) {
      this.fin = fin;
    }
  }

  public Boolean getEnemigo() {
    return enemigo;
  }

  public void setEnemigo(Boolean enemigo) {
    if (enemigo != null) {
      this.enemigo = enemigo;
    }
  }

  public Especial getEspecial() {
    return especial;
  }

  public void setEspecial(Especial especial) {
    this.especial = especial;
  }

  public Espacio getExtiende() {
    return extiende;
  }

  public void setExtiende(Espacio extiende) {
    this.extiende = extiende;
  }

  @Override
  public String toString() {
    return String.format("[nombre:%s;\nimagen:'%s';\nextiende:%s;\npasable:%b;\nenemigo:%b;\ninicio:%b;\nfin:%s]", getNombre(), imagen.getAbsoluteFile(), getExtiende(), getPasable(), getEnemigo(), getInicio(), getFin());
  }

  @Override
  protected Espacio clone() {
    Espacio clone = new Espacio(getNombre(), getImagen());

    clone.setEnemigo(getEnemigo());
    if (getEspecial() != null) {
      clone.setEspecial(getEspecial().clone());
    }
    clone.setExtiende(getExtiende());
    clone.setFin(getFin());
    clone.setInicio(getInicio());
    clone.setPasable(getPasable());

    return clone;
  }

  public static class Especial {

    private Integer danio = 0;
    private Integer cura = 0;
    private Integer magia = 0;
    private Integer invencibilidad = 0;

    public Integer getDanio() {
      return danio;
    }

    public void setDanio(Integer danio) {
      if (danio != null) {
        this.danio = danio;
      }
    }

    public Integer getCura() {
      return cura;
    }

    public void setCura(Integer cura) {
      if (cura != null) {
        this.cura = cura;
      }
    }

    public Integer getMagia() {
      return magia;
    }

    public void setMagia(Integer magia) {
      if (magia != null) {
        this.magia = magia;
      }
    }

    public Integer getInvencibilidad() {
      return invencibilidad;
    }

    public void setInvencibilidad(Integer invencibilidad) {
      if (invencibilidad != null) {
        this.invencibilidad = invencibilidad;
      }
    }

    @Override
    protected Especial clone() {
      Especial clone = new Especial();
      clone.setCura(getCura());
      clone.setDanio(getDanio());
      clone.setInvencibilidad(getInvencibilidad());
      clone.setMagia(getMagia());

      return clone;
    }
  }
}
