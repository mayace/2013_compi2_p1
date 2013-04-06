/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java_cup.runtime.Symbol;

/**
 *
 * @author ce
 */
public class Error {
  public static String ERROR_CONVERT="No se puede convertir.";
  public static String ERROR_STMT="Se esperaba una sentencia.";

  private String msg;
  private Symbol sym;
  private Type type;
  public static final String FORMAT = "[Error] in line %d column %d on value '%s'. Message: %s";
  public static final String ERROR_ID = "Se esperaba un identificador...";
  public static final String ERROR_INT = "Se esperaba un entero...";
  public static final String ERROR_BOOLEAN = "Se esperaba un boolean verdadero 'verdadero' o 'falso'...";
  public static final String ERROR_NULO = "Valor nulo.";
  public static final String ERROR_VAR_NO_EXISTE= "Variable no existe.";

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public enum Type {

    LEXIC, SYNTACTIC, SEMANTIC
  }

  public Error(String msg, Symbol sym, Type type) {
    this.msg = msg;
    this.sym = sym;
    this.type = type;
  }

  public Symbol getSym() {
    return sym;
  }

  public void setSym(Symbol sym) {
    this.sym = sym;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    Symbol s = getSym();
    int line = -1;
    int column = -1;
    String value = "null";

    if (s != null) {
      line = s.left + 1;
      column = s.right + 1;
      value = s.value.toString();
    }

    return String.format(FORMAT, line, column, value, getMsg());

  }

  public void println() {

    String t = "Unknow";
    if (getType() != null) {
      t = getType().name();
    }


    System.err.println(t + " Error:");
    System.err.println(toString());
  }
}
