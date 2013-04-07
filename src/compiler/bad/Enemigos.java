package compiler.bad;

import compiler.Error;
import compiler.bad.Enemigos.Simbolo.Tipo;
import java.awt.Point;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java_cup.runtime.Symbol;

public class Enemigos {

  private final Pila pila;
  private final TablaSimbolos simbolos = new TablaSimbolos();

  public Enemigos(int size) {
    this.pila = new Pila(size);
    //load defaults here
  }

  public Pila getPila() {
    return pila;
  }

  public TablaSimbolos getSimbolos() {
    return simbolos;
  }

  private void setDefaults() {
    //funciones
    for (int i = 0; i < 10; i++) {
      Simbolo s = new Simbolo(null, null, Simbolo.Rol.FUNCION);
      switch (i) {
        case 0:
          s.setNombre("getMuniciones");
          s.setTipo(Simbolo.Tipo.INTEGER);
          s.setParametros(Simbolo.Tipo.INTEGER);
          break;
        case 1:
          s.setNombre("getLibre");
          s.setTipo(Simbolo.Tipo.BOOLEAN);
          s.setParametros(Simbolo.Tipo.INTEGER, Simbolo.Tipo.INTEGER, Simbolo.Tipo.INTEGER);
          break;
        case 2:
          s.setNombre("bordeTablero");
          s.setTipo(Simbolo.Tipo.BOOLEAN);
          s.setParametros(Simbolo.Tipo.INTEGER);
          break;
        case 3:
          s.setNombre("getVal");
          s.setTipo(Simbolo.Tipo.OBJECT);
          s.setParametros(Simbolo.Tipo.ID);
          break;
        case 4:
          s.setNombre("getf");
          s.setTipo(Simbolo.Tipo.STRING);
          s.setParametros(Simbolo.Tipo.ID);
          break;
        case 5:
          s.setNombre("getBool");
          s.setTipo(Simbolo.Tipo.BOOLEAN);
          s.setParametros(Simbolo.Tipo.OBJECT);
          break;
        case 6:
          s.setNombre("getStr");
          s.setTipo(Simbolo.Tipo.STRING);
          s.setParametros(Simbolo.Tipo.OBJECT);
          break;
        case 7:
          s.setNombre("getInt");
          s.setTipo(Simbolo.Tipo.INTEGER);
          s.setParametros(Simbolo.Tipo.OBJECT);
          break;
        case 8:
          s.setNombre("aumentar");
          s.setTipo(Simbolo.Tipo.INTEGER);
          s.setParametros(Simbolo.Tipo.ID);
          break;
        case 9:
          s.setNombre("disminuir");
          s.setTipo(Simbolo.Tipo.INTEGER);
          s.setParametros(Simbolo.Tipo.ID);
          break;
        case 10:
          s.setNombre("armaPropia");
          s.setTipo(Simbolo.Tipo.BOOLEAN);
          s.setParametros(Simbolo.Tipo.INTEGER, Simbolo.Tipo.INTEGER);
          break;
      }
//      getSimbolos().add(s);
    }

    for (int i = 0; i < 10; i++) {
      Simbolo s = new Simbolo(null, Simbolo.Tipo.VOID, Simbolo.Rol.METODO);


//      addSym(s);
    }
  }

  //<editor-fold defaultstate="collapsed" desc="classes">
  public class Hoja {

    //<editor-fold defaultstate="collapsed" desc="parse val">
    private Integer getInteger(Object val) throws NullPointerException, NumberFormatException {
      return getBigDecimal(val).intValue();
    }

    private Long getLong(Object val) throws NullPointerException, NumberFormatException {
      return getBigDecimal(val).longValue();
    }

    private String getString(Object val) throws NullPointerException {
      try {
        return val.toString();
      } catch (NullPointerException e) {
        throw new NullPointerException(Error.ERROR_NULO);
      }
    }

    private Boolean getBoolean(Object val) throws NullPointerException {
      return Boolean.parseBoolean(getString(val));
    }

    private Character getChar(Object val) throws NullPointerException {
      String s = val.toString();
      if (s.length() == 1) {
        return s.charAt(0);
      } else {
        throw new NullPointerException(Error.ERROR_NULO);
      }
    }

    private BigDecimal getBigDecimal(Object val) throws NullPointerException {
      try {
        return new BigDecimal(getString(val));
      } catch (NumberFormatException e) {
        throw new NumberFormatException(Error.ERROR_CONVERT);
      }
    }

    private Object parseObject(Simbolo.Tipo tipo, Object val) throws AssertionError, NullPointerException, NumberFormatException {
      Object v = null;
      if (tipo == null) {
        throw new NullPointerException("Tipo nulo.");
      }
      if(val==null){
        return null;
      }
      //////

      ///////
      switch (tipo) {
        case STRING:
          v = getString(val);
          break;
        case BOOLEAN:
          v = getBoolean(val);
          break;
        case INTEGER:
          v = getInteger(val);
          break;
        case LONG:
          v = getLong(val);
          break;
        case CHAR:
          v = getChar(val);
          break;
        case FLOAT:
          v = getBigDecimal(val);
          break;
        case OBJECT:
          v = val;
          break;
        case ID:
          v = getString(val);
          break;
        default:
          throw new AssertionError(tipo.name());
      }
      return v;
    }

    private Object getHojaVal(Hoja hoja) throws AssertionError, NullPointerException, NumberFormatException, UnsupportedOperationException, ArrayIndexOutOfBoundsException {
      Object v;
      if (hoja == null) {
        throw new NullPointerException("Hoja nula.");
      }
      Simbolo.Tipo t = hoja.getTipo();
      Object temp = hoja.getVal();
      if (hoja.isVar()) {
        Simbolo sym = getSimbolo(temp);
        //talvez compara tipos
        v = parseObject(sym.getTipo(), pila.get(sym));
      } else {
        v = parseObject(t, temp);
      }
      return v;
    }

    private Simbolo getSimbolo(Object valor) throws AssertionError, NumberFormatException, UnsupportedOperationException, NullPointerException, ArrayIndexOutOfBoundsException {

      if (valor == null) {
        throw new NullPointerException(Error.ERROR_NULO);
      }
      Simbolo result;

      if (valor instanceof Object[]) {
        Object[] obj = (Object[]) valor;

        if (obj.length < 2) {
          throw new ArrayIndexOutOfBoundsException(Error.ERROR_VAR_NO_EXISTE);
        }

        String nombre = obj[0].toString();
        Simbolo.Tipo t2 = (Simbolo.Tipo) obj[1];

        if (!simbolos.contains(nombre, t2)) {
          throw new NullPointerException(Error.ERROR_VAR_NO_EXISTE);
        }
        result = simbolos.get(nombre, t2);
      } else {
        throw new UnsupportedOperationException("Variable no soportada.");
      }
      return result;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="funciones">
    private Object function_getInt(Hoja l) {
      Object result = null;

      try {
        Object v = getHojaVal(l);

        if (v instanceof Boolean) {
          Boolean b = (Boolean) v;
          if (b) {
            result = 1;
          } else {
            result = 0;
          }
        } else if (v instanceof Character) {
          Character c = (Character) v;
          result = (int) c;
        } else if (v instanceof BigDecimal) {
          BigDecimal d = (BigDecimal) v;
          result = d.intValue();
        } else {
          result = getInteger(v);
        }
        setTipo(Simbolo.Tipo.INTEGER);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException exce) {
        error(exce, l);
      }
      return result;
    }

    private Object function_getBool(Hoja l) {
      boolean result = false;
      try {
        Object valor = getHojaVal(l);
        if (valor instanceof Integer || valor instanceof Long) {
          BigDecimal i = getBigDecimal(valor);
          if (i.intValue() == 1) {
            result = true;
          }
        } else {
          result = getBoolean(valor);
        }
        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException exce) {
        error(exce, l);
      }
      return result;
    }

    private Object function_getStr(Hoja l) {
      String result = null;
      try {
        Object valor = getHojaVal(l);
        result = getString(valor);
        setTipo(Simbolo.Tipo.STRING);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException exce) {
        error(exce, l);
      }

      return result;
    }

    private Object function_getObj(Hoja l) {
      Object result = null;
      try {
        result = getHojaVal(l);
        setTipo(l.getTipo());
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException exce) {
        error(exce, l);
      }
      return result;
    }
    //</editor-fold>

    private void error(final java.lang.Throwable exce, Hoja l) {
      Error e = new Error(exce.toString(), l.getSymbol(), Error.Type.SEMANTIC);
      e.println();
    }

    private void setf(Hoja l, Hoja r) {
      try {
        Object variable = l.getVal();
        Simbolo simbolo = getSimbolo(variable);
        //para tratar de castear
        Object valor = parseObject(simbolo.tipo, r.getVal());
        setSymVal(valor, simbolo);
      } catch (AssertionError | NumberFormatException | UnsupportedOperationException | NullPointerException | ArrayIndexOutOfBoundsException exce) {
        error(exce, r);
      }
    }

    private Object xor(Hoja l, Hoja r) {
      return operarLogico(l, r, Operacion.XOR);
    }

    private Object or(Hoja l, Hoja r) {
      return operarLogico(l, r, Operacion.OR);
    }

    private Object and(Hoja l, Hoja r) {
      return operarLogico(l, r, Operacion.AND);
    }

    private Object not(Hoja l) {
      Object result = null;
      try {
        Object v1 = getHojaVal(l);
        if (v1 instanceof Boolean) {
          Boolean b = (Boolean) v1;
          result = !b;
        }
        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, l);
      }
      return result;
    }

    private Object operarLogico(Hoja l, Hoja r, Operacion oper) {
      Object result = null;
      try {
        Object v1 = getHojaVal(l);
        Object v2 = getHojaVal(r);
        if (v1 instanceof Boolean && v2 instanceof Boolean) {
          Boolean b1 = (Boolean) v1;
          Boolean b2 = (Boolean) v2;
          switch (oper) {
            case XOR:
              result = b1 && !b2 || !b1 && b2;
              break;
            case OR:
              result = b1 || b2;
              break;
            case AND:
              result = b1 && b2;
              break;

          }

        } else {
          throw new UnsupportedOperationException("Datos incompatibles.");
        }

        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, l);
      }
      return result;
    }

    private Object enull(Hoja l) {
      Object result = false;
      try {
        if (l.isVar()) {
          Simbolo simbolo = getSimbolo(l.getVal());
          if (simbolo.posicion < 0) {
            result = true;
          }
        } else {
          throw new UnsupportedOperationException("No es una variable");
        }

        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (AssertionError | NumberFormatException | UnsupportedOperationException | NullPointerException | ArrayIndexOutOfBoundsException exce) {
        error(exce, l);
      }
      return result;
    }

    private Object equal(Hoja l, Hoja r) {
      Object result = null;
      try {
        Object v1 = getHojaVal(l);
        Object v2 = getHojaVal(r);

        result = v1.equals(v2);
        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (Exception exce) {
        error(exce, l);
      }
      return result;
    }

    private Object nequal(Hoja l, Hoja r) {
      Object result = null;
      try {
        Object v1 = getHojaVal(l);
        Object v2 = getHojaVal(r);

        
        result = !v1.equals(v2);
        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (Exception exce) {
        error(exce, l);
      }
      return result;
    }

    private Object lethan(Hoja l, Hoja r) {
      return operarComparacionNumeros(l, r, Operacion.LETHAN);

    }

    private Object lthan(Hoja l, Hoja r) {
      return operarComparacionNumeros(l, r, Operacion.LTHAN);
    }

    private Object bethan(Hoja l, Hoja r) {
      return operarComparacionNumeros(l, r, Operacion.BETHAN);
    }

    private Object bthan(Hoja l, Hoja r) {
      return operarComparacionNumeros(l, r, Operacion.BTHAN);
    }

    private Object operarComparacionNumeros(Hoja l, Hoja r, Operacion oper) {
      Object result = null;
      try {
        double v1 = getBigDecimal(getHojaVal(l)).doubleValue();
        double v2 = getBigDecimal(getHojaVal(r)).doubleValue();
        switch (oper) {
          case LETHAN:
            result = v1 <= v2;
            break;
          case LTHAN:
            result = v1 < v2;
            break;
          case BETHAN:
            result = v1 >= v2;
            break;
          case BTHAN:
            result = v1 > v2;
            break;
          default:
            throw new AssertionError(oper.name());
        }
        setTipo(Simbolo.Tipo.BOOLEAN);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, r);
      }
      return result;
    }

    private void mientras(Hoja l, Hoja r) {
      try {
        l.exec();
        while ((Boolean) getHojaVal(l)) {
          r.exec();
          l.exec();
        }
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, l);
      }
    }

    private void addf(Hoja l, Hoja r) {
      try {
        if (l.getVal() instanceof Object[]) {
          Object[] vals = (Object[]) l.getVal();
          String nombre = getString(vals[0]);
          Tipo t = (Tipo) vals[1];

          if (simbolos.contains(nombre, t)) {
            throw new Exception("Ya existe la variable");
          }
          Simbolo newSym = new Simbolo(nombre, t, Simbolo.Rol.VARIABLE);
          simbolos.add(newSym);

          //setf
          if (r != null) {
            Object v = r.getHojaVal(r);
            if (v != null) {
              Hoja hoja = new Hoja(Operacion.SETF, l, r);
              hoja.exec();
            }
          }
        }
      } catch (Exception exce) {
        error(exce, l);
      }
    }
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private void println(Hoja l) {
      try {
        System.out.println(ANSI_RED + l.getHojaVal(l));
      } catch (Exception e) {
        error(e, this);
      }
    }

    private Object function_getVal(Hoja l) {
      Object result = null;
      try {
        Simbolo simbolo = getSimbolo(l.getVal());
        result = pila.get(simbolo);
        setTipo(simbolo.getTipo());
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, l);
      }
      return result;
    }

    private void si() {
      try {
        Object v = getVal();
        if (v instanceof LinkedList) {
          LinkedList<Hoja> list = (LinkedList<Hoja>) v;
          for (int i = 0; i < list.size(); i++) {
            Hoja hoja = list.get(i);
            Operacion oper = hoja.getOperacion();

            if (oper == Operacion.ELSE_IF) {
              hoja.left.exec();
              if ((Boolean) getHojaVal(hoja.getLeft())) {
                hoja.right.exec();
                break;
              }
            } else {
              if (i == list.size() - 1) {
                hoja.left.exec();
                break;
              } else {
                throw new UnsupportedOperationException("Se esperaba expresion a compara.");
              }
            }
          }
        } else {
          throw new UnsupportedOperationException("If no soportado.");
        }
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, this);
      }

    }
    private Symbol symbol = null;
    private Object val = null;
    private boolean var = false;
    private Operacion operacion = null;
    private Simbolo.Tipo tipo = null;
    private Hoja left = null;
    private Hoja right = null;
    private final HashMap<String, Simbolo.Tipo> tipoMap = new HashMap<>();
    private final LinkedList<compiler.Error> errores = new LinkedList<>();
    //algunas operaciones no tienen hojas... pero deben operarse
    HashSet<Operacion> sin_hojas = new HashSet<>();
    HashSet<Operacion> hojas_indp = new HashSet<>();

    public HashSet<Operacion> getSin_hojas() {
      if (sin_hojas.isEmpty()) {
        sin_hojas.add(Operacion.IF);
        sin_hojas.add(Operacion.CREAR_ARMA);
        sin_hojas.add(Operacion.CREAR_ENEMIGO);
        sin_hojas.add(Operacion.CREAR_ESTRATEGIA);
        sin_hojas.add(Operacion.CREAR_IMAGEN);
        sin_hojas.add(Operacion.CREAR_POTENCIA);
        sin_hojas.add(Operacion.ASIGNAR_ARMA);
        sin_hojas.add(Operacion.ASIGNAR_PASO);
        sin_hojas.add(Operacion.ASIGNAR_HABILIDAD);
        sin_hojas.add(Operacion.ASIGNAR_SALTO);
      }
      return sin_hojas;
    }

    public HashSet<Operacion> getHojas_indp() {
      if (hojas_indp.isEmpty()) {
        hojas_indp.add(Operacion.WHILE);
        hojas_indp.add(Operacion.FOR);
        hojas_indp.add(Operacion.IF);
      }
      return hojas_indp;
    }

    public void exec() {

      Hoja l = left;
      Hoja r = right;

      //si no tienen hojas o controlan sus ejecuciones y deben operar
      if (getSin_hojas().contains(operacion) || getHojas_indp().contains(operacion)) {
        operar(l, r);
      } else {
        if (l != null) {
          l.exec();
          if (r != null) {
            r.exec();
          }
          operar(l, r);
        }
      }
    }

    private void operar(Hoja l, Hoja r) throws AssertionError, NullPointerException {
      Operacion oper = getOperacion();
      if (oper == null) {
        throw new NullPointerException("Operacion nula.");
      }
      switch (oper) {
        case SUMA:
          setVal(suma(l, r));
          break;
        case RESTA:
          setVal(resta(l, r));
          break;
        case MULTIPLICACION:
          setVal(multiplicacion(l, r));
          break;
        case DIVISION:
          setVal(division(l, r));
          break;
        case XOR:
          setVal(xor(l, r));
          break;
        case OR:
          setVal(or(l, r));
          break;
        case AND:
          setVal(and(l, r));
          break;
        case NOT:
          setVal(not(l));
          break;
        case ENULL:
          setVal(enull(l));
          break;
        case EQUAL:
          setVal(equal(l, r));
          break;
        case NEQUAL:
          setVal(nequal(l, r));
          break;
        case LETHAN:
          setVal(lethan(l, r));
          break;
        case LTHAN:
          setVal(lthan(l, r));
          break;
        case BETHAN:
          setVal(bethan(l, r));
          break;
        case BTHAN:
          setVal(bthan(l, r));
          break;
        case GET_INT:
          setVal(function_getInt(l));
          break;
        case GET_BOOL:
          setVal(function_getBool(l));
          break;
        case GET_STR:
          setVal(function_getStr(l));
          break;
        case GET_OBJ:
          setVal(function_getObj(l));
          break;
        case SETF:
          setf(l, r);
          break;
        case WHILE:
          mientras(l, r);
          break;
        case WHILE_PAUSE:
          mientras_pausa(l);
          break;
        case STMT:
          break;
        case ADDF:
          addf(l, r);
          break;
        case PRINTLN:
          println(l);
          break;
        case GET_VAL:
          setVal(function_getVal(l));
          break;
        case IF:
          //se ejecutan todo else if y else
          si();
          break;
        case FOR:
          para(l, r);
          break;
        case CREAR_ARMA:
          crear_arma();
          break;
        case CREAR_ENEMIGO:
          crear_enemigo();
          break;
        case CREAR_ESTRATEGIA:
          crear_estrategia();
          break;
        case CREAR_IMAGEN:
          crear_imagen();
          break;
        case CREAR_POTENCIA:
          crear_potencia();
          break;
        case AUMENTAR:
          setVal(aumentar(l));
          break;
        case DISMINUIR:
          setVal(disminuir(l));
          break;
        case ASIGNAR_ARMA:
          asignar_arma();
          break;
        case ASIGNAR_PASO:
          asignar_paso();
          break;
        case ASIGNAR_SALTO:
          asignar_salto();
          break;
        case ASIGNAR_HABILIDAD:
          asignar_habilidad();
          break;
        case AVANZAR:
          avanzar();
          break;
        case GIRAR:
          girar();
          break;
        default:
          throw new AssertionError(oper.name());
      }
    }

    //<editor-fold defaultstate="collapsed" desc="operaciones aritmeticas">
    private Object resta(Hoja l, Hoja r) {
      BigDecimal result = operarNumero(l, r, Operacion.RESTA);
      return result;
    }

    private Object suma(Hoja l, Hoja r) {
      BigDecimal result = null;
      try {
        result = operarNumero(l, r, Operacion.SUMA);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException exce) {
        error(exce, r);
      }
      return result;
    }

    private Object multiplicacion(Hoja l, Hoja r) {
      BigDecimal result = operarNumero(l, r, Operacion.MULTIPLICACION);
      return result;
    }

    private Object division(Hoja l, Hoja r) {
      BigDecimal result = operarNumero(l, r, Operacion.DIVISION);
      return result;
    }

    private String getTipoMapKey(Simbolo.Tipo f, Simbolo.Tipo i) {
      String key = f.name() + i.name();
      return key;
    }

    private BigDecimal operarNumero(Hoja l, Hoja r, Operacion oper) throws AssertionError, NullPointerException, NumberFormatException, UnsupportedOperationException {
      BigDecimal result = null;
      if (l != null && r != null) {
        final Object v1 = getHojaVal(l);
        final Object v2 = getHojaVal(r);

        if (v1 != null && v2 != null) {
          String key = getTipoMapKey(l.getTipo(), r.getTipo());
          final HashMap<String, Simbolo.Tipo> map = getTipoMap();
          if (map.containsKey(key)) {
            BigDecimal n1 = new BigDecimal(v1.toString());
            BigDecimal n2 = new BigDecimal(v2.toString());
            switch (oper) {
              case SUMA:
                result = n1.add(n2);
                break;
              case RESTA:
                result = n1.subtract(n2);
                break;
              case MULTIPLICACION:
                result = n1.multiply(n2);
                break;
              case DIVISION:
                result = n1.divide(n2);
                break;
              default:
                throw new AssertionError(oper.name());
            }


            Simbolo.Tipo t = map.get(key);
            //set tipo
            setTipo(t);
            //            System.out.println(result);
          } else {
            //error en operar los tipos
            compiler.Error e = new compiler.Error("Datos incompatibles.", r.getSymbol(), compiler.Error.Type.SEMANTIC);
            getErrores().add(e);
            e.println();
          }
        }
      }
      return result;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructores">
    public Hoja(Operacion operacion, Hoja left, Hoja Right) {
      this.operacion = operacion;
      this.left = left;
      this.right = Right;
    }

    public Hoja(Object val, Simbolo.Tipo tipo, boolean is_var) {
      this.val = val;
      this.var = is_var;
      this.tipo = tipo;
    }

    public Hoja(Object val, boolean is_var) {
      this.val = val;
      this.var = is_var;
    }

    public Hoja(Object val, Object sym, boolean is_var) {
      this.val = val;
      this.var = is_var;
      if (sym instanceof Symbol) {
        Symbol s = (Symbol) sym;
        this.symbol = s;
      }
    }

    public Hoja(Object val, Simbolo.Tipo tipo) {
      this.val = val;
      this.tipo = tipo;
    }

    public Hoja(Object val, Simbolo.Tipo tipo, Object sym) {
      this.val = val;
      this.tipo = tipo;
      if (sym instanceof Symbol) {
        Symbol s = (Symbol) sym;
        this.symbol = s;
      }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public void setVar(boolean var) {
      this.var = var;
    }

    public LinkedList<Error> getErrores() {
      return errores;
    }

    public HashMap<String, Simbolo.Tipo> getTipoMap() {
      if (tipoMap.isEmpty()) {
        Simbolo.Tipo f = Simbolo.Tipo.FLOAT;
        Simbolo.Tipo i = Simbolo.Tipo.INTEGER;
        Simbolo.Tipo l = Simbolo.Tipo.LONG;

        tipoMap.put(getTipoMapKey(i, i), i);
        tipoMap.put(getTipoMapKey(i, f), f);
        tipoMap.put(getTipoMapKey(i, l), l);
        tipoMap.put(getTipoMapKey(l, i), f);
        tipoMap.put(getTipoMapKey(l, f), f);
        tipoMap.put(getTipoMapKey(l, l), l);
        tipoMap.put(getTipoMapKey(f, i), f);
        tipoMap.put(getTipoMapKey(f, f), f);
        tipoMap.put(getTipoMapKey(f, l), f);

      }
      return tipoMap;
    }

    public boolean isVar() {
      return var;
    }

    public Operacion getOperacion() {
      return operacion;
    }

    public void setOperacion(Operacion operacion) {
      this.operacion = operacion;
    }

    public Simbolo.Tipo getTipo() {
      return tipo;
    }

    public void setTipo(Simbolo.Tipo tipo) {
      this.tipo = tipo;
    }

    public Hoja getLeft() {
      return left;
    }

    public void setLeft(Hoja left) {
      this.left = left;
    }

    public Hoja getRight() {
      return right;
    }

    public void setRight(Hoja right) {
      this.right = right;
    }

    public Symbol getSymbol() {
      return symbol;
    }

    public void setSymbol(Object symbol) {
      Symbol sym = null;
      if (symbol instanceof Symbol) {
        sym = (Symbol) symbol;
      }
      this.symbol = sym;
    }

    public Object getVal() {
      return val;
    }

    public void setVal(Object val) {
//      System.err.println("setVal=" + val);
      this.val = val;
    }
    //</editor-fold>

    private void crear_arma() {
      try {
        Object v = getVal();

        if (v instanceof Object[] && ((Object[]) v).length == 5) {
          Object[] valores = (Object[]) v;
          int id = (int) valores[0];
          String nombre = (String) valores[1];
          int max = (int) valores[2];
          int min = (int) valores[3];
          int municiones = (int) valores[4];

          if (getArmaMap().containsKey(id)) {
            throw new UnsupportedOperationException("Ya existe una arma con ID=" + id);
          }

          addArma(id, nombre, max, min, municiones);
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
      } catch (UnsupportedOperationException exce) {
        error(exce, this);
      }
    }

    private void crear_enemigo() {
      try {
        Object v = getVal();
        if (v instanceof Object[] && ((Object[]) v).length == 4) {
          Object[] valores = (Object[]) v;
          int id = (int) valores[0];
          String nombre = (String) valores[1];
          int potencia = (int) valores[2];
          int imagen = (int) valores[3];

          if (getEnemigoMap().containsKey(id)) {
            throw new UnsupportedOperationException("Ya existe un enemigo con ID=" + id);
          }
          if (!getPotenciaMap().containsKey(potencia)) {
            throw new UnsupportedOperationException("No existe la potencia con ID=" + potencia);
          }
          if (!getImagenMap().containsKey(imagen)) {
            throw new UnsupportedOperationException("No existe la imagen con ID=" + imagen);
          }
          addEnemigo(id, nombre, potencia, imagen);
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
      } catch (UnsupportedOperationException exce) {
        error(exce, this);
      }
    }

    private void crear_estrategia() {
      try {
        Object v = getVal();
        if (v instanceof Object[] && ((Object[]) v).length == 3) {
          Object[] valores = (Object[]) v;
          int id = (int) valores[0];
          String nombre = (String) valores[1];
          int punteo = (int) valores[2];

          if (getEstrategiaMap().containsKey(id)) {
            throw new UnsupportedOperationException("Ya existe una estrategia con ID=" + id);
          }
          addEstrategia(id, nombre, punteo);
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
      } catch (UnsupportedOperationException exce) {
        error(exce, this);
      }
    }

    private void crear_imagen() {
      try {
        Object v = getVal();
        if (v instanceof Object[] && ((Object[]) v).length == 3) {
          Object[] valores = (Object[]) v;
          int id = (int) valores[0];
          String nombre = (String) valores[1];
          String ruta = (String) valores[2];

          if (getImagenMap().containsKey(id)) {
            throw new UnsupportedOperationException("Ya existe una imagen con ID=" + id);
          }
          addImagen(id, nombre, ruta);
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
      } catch (UnsupportedOperationException exce) {
        error(exce, this);
      }
    }

    private void crear_potencia() {
      try {
        Object v = getVal();
        if (v instanceof Object[] && ((Object[]) v).length == 2) {
          Object[] valores = (Object[]) v;
          int id = (int) valores[0];
          Nivel nivel = (Nivel) valores[1];

          if (getPotenciaMap().containsKey(id)) {
            throw new UnsupportedOperationException("Ya existe una potencia el ID=" + id);
          }
          addPotencia(id, nivel);
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
      } catch (UnsupportedOperationException exce) {
        error(exce, this);
      }
    }

    private void para(Hoja l, Hoja r) {
      try {
        Object v = getVal();
        if (v instanceof Object[] && ((Object[]) v).length == 2) {
          Object[] variable = (Object[]) v;
          Hoja init = (Hoja) variable[0];
          Hoja setvar = (Hoja) variable[1];

          //inicializar
          init.exec();
          //calcular el resultado de la comparacion
          l.exec();
          //mientras se cumpla la condicion
          while ((Boolean) getHojaVal(l)) {
            //ejecutar los stmts dentro del for
            r.exec();
            //ejecutar la disminuir o aumentar
            setvar.exec();
            //realizar otra vez la comparacion
            l.exec();
          }
        } else {
          throw new UnsupportedOperationException("Operacion no soportada");
        }
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, this);
      }
    }

    private Object aumentar(Hoja l) {
      Object result = null;
      try {
        Object v = getHojaVal(l);
        if (v instanceof Integer) {
          Integer d = (Integer) v;
          result = d + 1;
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
        setSymVal(result, getSimbolo(l.getVal()));
        setTipo(Tipo.INTEGER);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, this);
      }
      return result;
    }

    private Object disminuir(Hoja l) {
      Object result = null;
      try {
        Object v = getHojaVal(l);
        if (v instanceof Integer) {
          Integer d = (Integer) v;
          result = d - 1;
        } else {
          throw new UnsupportedOperationException("Operacion no soportada.");
        }
        setSymVal(result, getSimbolo(l.getVal()));
        setTipo(Tipo.INTEGER);
      } catch (AssertionError | NullPointerException | NumberFormatException | UnsupportedOperationException | ArrayIndexOutOfBoundsException exce) {
        error(exce, this);
      }
      return result;
    }

    private void setSymVal(Object valor, Simbolo simbolo) {
      //ṕposiciones
      int p = pila.getP();
      int size = pila.put(valor);
      simbolo.setPosicion(p);
      simbolo.setTamanio(size);
    }

    private void mientras_pausa(Hoja l) {
      try {
        BigDecimal time = getBigDecimal(l.getVal());
        Thread.sleep(time.longValue());
      } catch (NullPointerException | InterruptedException exce) {
        error(exce, this);
      }
      
    }

    private void asignar_arma() {
      try {
        Object temp = getVal();
        if (temp instanceof Object[]) {
          Object[] val = (Object[]) temp;
          Hoja h1 = (Hoja) val[0];
          Hoja h2 = (Hoja) val[1];
          
          h1.exec();
          h2.exec();
          
          Integer i1 = getInteger(h1.getVal());
          Integer id2 = getInteger(h2.getVal());
          Enemigo enemigo = getEnemigo(i1);
          Arma arma = getArma(id2);
          
          enemigo.addArmas(id2);
          System.err.println("arma asignada");
        }
      } catch (NullPointerException | NumberFormatException exce) {
        error(exce, this);
      }
    }

    private void asignar_paso() {
      try {
        Object[] array = getValArray(getVal());
        
        Hoja i1 = (Hoja) array[0];
        Hoja i2 = (Hoja) array[1];
        Hoja i3 = (Hoja) array[2];
        Hoja i4 = (Hoja) array[3];
        Movimiento i5 = (Movimiento) array[4];
        
        i1.exec();
        i2.exec();
        i3.exec();
        i4.exec();
        
        final int id1 = getInteger(i1.getVal());
        final int id2 = getInteger(i2.getVal());
        
        Estrategia estrategia = getEstrategia(id1);
        Enemigo enemigo = getEnemigo(id2);
        int x = getInteger(i3.getVal());
        int y = getInteger(i4.getVal());
        Point p = new Point(x, y);
        Paso paso = new Paso(id1, id2, p, i5);
      } catch (Exception e) {
        error(e, this);
      }
      
    }

    private Object[] getValArray(Object val)throws UnsupportedOperationException{
      if (val instanceof Object[]) {
        Object[] array = (Object[]) val;
        return array;
      }
      throw new UnsupportedOperationException("No es array");
    }

    private void asignar_salto() {
      try {
        Object[] array = getValArray(getVal());
        
        Hoja i1=(Hoja) array[0];
        Hoja i2=(Hoja) array[1];
        Hoja i3=(Hoja) array[2];
        Hoja i4=(Hoja) array[3];
        
        i1.exec();
        i2.exec();
        i3.exec();
        i4.exec();
        
        int id1=getInteger(i1.getVal());
        int id2=getInteger(i2.getVal());
        int x=getInteger(i3.getVal());
        int y=getInteger(i4.getVal());
        
        getEstrategia(id1);
        getEnemigo(id2);
        Point destino = new Point(x, y);
        
        Salto salto = new Salto(id1, id2, destino);
        
      } catch (Exception e) {
        error(e, this);
      }
    }

    private void asignar_habilidad() {
      try {
        Object[] array = getValArray(getVal());
        
        Hoja i1=(Hoja) array[0];
        Hoja i2=(Hoja) array[1];
        Hoja i3=(Hoja) array[2];
        Hoja i4=(Hoja) array[3];
        
        i1.exec();
        i2.exec();
        i3.exec();
        i4.exec();
        
        int id1=getInteger(i1.getVal());
        int id2=getInteger(i1.getVal());
        int id3=getInteger(i1.getVal());
        int detonaciones=getInteger(i1.getVal());
        
        
        
        
      } catch (Exception e) {
        error(e, this);
      }
    }

    private void avanzar() {
      try {
        Object[] array = getValArray(getVal());
        
        Hoja i1 =(Hoja) array[0];
        Hoja i2 =(Hoja) array[1];
        Hoja i3 =(Hoja) array[2];
        
        i1.exec();
        i2.exec();
        i3.exec();
        
        int id1=getInteger(i1.getVal());
        int id2=getInteger(i2.getVal());
        int pasos=getInteger(i3.getVal());
        
      } catch (Exception e) {
        error(e, this);
      }
    }

    private void girar() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  }

  public static class Simbolo {

    public enum Tipo {

      STRING, BOOLEAN, INTEGER, LONG, CHAR, FLOAT, VOID, OBJECT, ID
    }

    public enum Rol {

      VARIABLE, METODO, FUNCION
    }
    private String nombre;
    private Tipo tipo;
    private Rol rol;
    private int posicion = -1;
    private int tamanio = 0;
    private Tipo[] parametros = null;

    public Simbolo(String nombre, Tipo tipo, Rol rol, int posicion) {
      this.nombre = nombre;
      this.tipo = tipo;
      this.rol = rol;
      this.posicion = posicion;
    }

    public Simbolo(String nombre, Tipo tipo, Rol rol, Tipo... parametros) {
      this.nombre = nombre;
      this.tipo = tipo;
      this.rol = rol;
      this.parametros = parametros;
    }

    public Simbolo(String nombre, Tipo tipo, Rol rol) {
      this.nombre = nombre;
      this.tipo = tipo;
      this.rol = rol;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public Tipo getTipo() {
      return tipo;
    }

    public void setTipo(Tipo tipo) {
      this.tipo = tipo;
    }

    public Rol getRol() {
      return rol;
    }

    public void setRol(Rol rol) {
      this.rol = rol;
    }

    public int getPosicion() {
      return posicion;
    }

    public void setPosicion(int posicion) {
      this.posicion = posicion;
    }

    public Tipo[] getParametros() {
      return parametros;
    }

    public void setParametros(Tipo... parametros) {
      this.parametros = parametros;
    }

    public int getSize() {
      return tamanio;
    }

    public void setSize(int size) {
      this.tamanio = size;
    }

    public int getTamanio() {
      return tamanio;
    }

    public void setTamanio(int tamanio) {
      this.tamanio = tamanio;
    }
  }

  public static class Pila {

    private final int[] pila;
    private int p = 0;

    public Pila(int size) {
      this.pila = new int[size];
    }

    public int put(Object val) {
      String s = val.toString();
      int size = 0;
      for (char c : s.toCharArray()) {
        add((int) c);
        size++;
      }
      return size;
    }

    public Object get(int p, int size) {
      if(p<0){
        return null;
      }
      char[] val = new char[size];
      for (int i = 0; i < size; i++) {
        val[i] = (char) getPila()[p + i];
      }

      return String.valueOf(val);
    }

    private void add(int i) {
      getPila()[getP()] = i;
      incrementP();
    }

    public int[] getPila() {
      return pila;
    }

    public int getP() {
      return p;
    }

    private void incrementP() {
      p++;
    }

    private Object get(Simbolo sym) {
      return get(sym.getPosicion(), sym.tamanio);
    }
  }

  public static class TablaSimbolos {

    HashMap<String, Simbolo> simbolos = new HashMap<>();

    public String getKey(Enemigos.Simbolo sym) {
      if (sym == null) {
        return null;
      }
      return getKey(sym.getNombre(), sym.getTipo());
    }

    public String getKey(String nombre, Simbolo.Tipo tipo) {
      if (nombre == null || tipo == null) {
        return null;
      }
      return nombre + "/" + tipo;
    }

    public void add(Simbolo sym) {
      String key = getKey(sym);
      simbolos.put(key, sym);
//      System.out.println(key + "(" + sym.getPosicion() + "," + sym.getSize() + ") added...");
    }

    public boolean contains(Simbolo sym) {
      String key = getKey(sym);
      return getSimbolos().containsKey(key);
    }

    public boolean contains(String nombre, Simbolo.Tipo tipo) {
      return getSimbolos().containsKey(getKey(nombre, tipo));
    }

    public Simbolo get(String nombre, Simbolo.Tipo tipo) {
      return getSimbolos().get(getKey(nombre, tipo));
    }

    public HashMap<String, Simbolo> getSimbolos() {
      return simbolos;
    }
  }

  public class Imagen {

    String nombre;
    String ruta;

    public Imagen(String nombre, String ruta) {
      this.nombre = nombre;
      this.ruta = ruta;
    }
  }

  public enum Nivel {

    TINNY, LOW, MEDIUM, HIGHT, HEAVY
  }

  public class Potencia {

    Nivel nivel;

    public Potencia(Nivel nivel) {
      this.nivel = nivel;
    }
  }

  public class Arma {

    String nombre;
    int min;
    int max;
    int municiones;

    public Arma(String nombre, int min, int max, int municiones) {
      this.nombre = nombre;
      this.min = min;
      this.max = max;
      this.municiones = municiones;
    }
  }

  public class Enemigo {

    String nombre;
    int potencia;
    int imagen;
    HashSet<Integer> armas = new HashSet<>();

    public Enemigo(String nombre, int potencia, int imagen) {
      this.nombre = nombre;
      this.potencia = potencia;
      this.imagen = imagen;
    }

    public void addArmas(int... armas) {
      for (int i : armas) {
        this.armas.add(i);
      }
    }
  
    
  }

  public class Estrategia {

    String nombre;
    int punteo;

    public Estrategia(String nombre, int punteo) {
      this.nombre = nombre;
      this.punteo = punteo;
    }
  }
  
  public class Paso{
    int estrategia;
    int enemigo;
    Point destino;
    Movimiento movimiento;

    public Paso(int estrategia, int enemigo, Point destino, Movimiento movimiento) {
      HashMap<Integer, Estrategia> m1 = getEstrategiaMap();
      HashMap<Integer, Enemigo> m2 = getEnemigoMap();
      if(!m1.containsKey(estrategia)){
        throw new NullPointerException("No existe estrategia con id="+estrategia);
      }
      if(!m2.containsKey(enemigo)){
          throw new NullPointerException("No existe enemigo con id="+enemigo);
      }
      this.estrategia = estrategia;
      this.enemigo = enemigo;
      this.destino = destino;
      this.movimiento = movimiento;
    }
    
    
  }
  
  public enum Movimiento{CIRCULAR,LINEAL}
  
  public class Salto{
    int estrategia;
    int enemigo;
    Point destino;

    public Salto(int estrategia, int enemigo, Point destino) throws NullPointerException{
      
      getEstrategia(estrategia);
      getEnemigo(enemigo);
      
      this.estrategia = estrategia;
      this.enemigo = enemigo;
      this.destino = destino;
    }
    
  }
  
  
  public class Habilidad{
    
    int estrategia;
    int enemigo;
    int arma;
    int detonaciones;

    public Habilidad(int estrategia, int enemigo, int arma, int detonaciones) {
      getEstrategia(estrategia);
      getEnemigo(enemigo);
      getArma(arma);
      this.estrategia = estrategia;
      this.enemigo = enemigo;
      this.arma = arma;
      this.detonaciones = detonaciones;
    }
    
    
    
  }
  
  //</editor-fold>
  
  
  ///sin ordenar...
  private final HashMap<String, HashMap<Integer, ? extends Object>> mapa = new HashMap<>();

  public HashMap<String, HashMap<Integer, ? extends Object>> getMapa() {
    if (mapa.isEmpty()) {
      HashMap<Integer, Imagen> imagenMap = new HashMap<>();
      HashMap<Integer, Potencia> potenciaMap = new HashMap<>();
      HashMap<Integer, Arma> armaMap = new HashMap<>();
      HashMap<Integer, Enemigo> enemigoMap = new HashMap<>();
      HashMap<Integer, Estrategia> estrategiaMap = new HashMap<>();

      mapa.put("imagen", imagenMap);
      mapa.put("potencia", potenciaMap);
      mapa.put("arma", armaMap);
      mapa.put("enemigo", enemigoMap);
      mapa.put("estrategia", estrategiaMap);
    }
    return mapa;
  }

  public HashMap<Integer, Imagen> getImagenMap() {
    return (HashMap<Integer, Imagen>) getMapa().get("imagen");
  }

  public HashMap<Integer, Potencia> getPotenciaMap() {
    return (HashMap<Integer, Potencia>) getMapa().get("potencia");
  }

  public HashMap<Integer, Arma> getArmaMap() {
    return (HashMap<Integer, Arma>) getMapa().get("arma");
  }

  public HashMap<Integer, Enemigo> getEnemigoMap() {
    return (HashMap<Integer, Enemigo>) getMapa().get("enemigo");
  }

  public HashMap<Integer, Estrategia> getEstrategiaMap() {
    return (HashMap<Integer, Estrategia>) getMapa().get("estrategia");
  }

  void addEstrategia(int id, String nombre, int punteo) {
    Estrategia estrategia = new Estrategia(nombre, punteo);
    getEstrategiaMap().put(id, estrategia);
  }

  void addEnemigo(int id, String nombre, int potencia, int imagen) {
    Enemigo enemigo = new Enemigo(nombre, potencia, imagen);
    getEnemigoMap().put(id, enemigo);
  }

  void addPotencia(int id, Nivel nivel) {
    Potencia potencia = new Potencia(nivel);
    getPotenciaMap().put(id, potencia);
  }

  void addImagen(int id, String nombre, String ruta) {
    Imagen imagen = new Imagen(nombre, ruta);
    getImagenMap().put(id, imagen);
  }

  void addArma(int id, String nombre, int max, int min, int municiones) {
    Arma arma = new Arma(nombre, min, max, municiones);
    getArmaMap().put(id, arma);
  }

  Enemigo getEnemigo(int id)throws NullPointerException{
    HashMap<Integer, Enemigo> map = getEnemigoMap();
    
    if(map.containsKey(id)){
      return map.get(id);
    }else{
      throw new NullPointerException("No existe enemigo con id="+id);
    }
  }
  Arma getArma(int id)throws NullPointerException{
    HashMap<Integer, Arma> map = getArmaMap();
    
    if(map.containsKey(id)){
      return map.get(id);
    }else{
      throw new NullPointerException("No existe arma con id="+id);
    }
  }
  Estrategia getEstrategia(int id)throws NullPointerException{
    HashMap<Integer, Estrategia> map = getEstrategiaMap();
    
    if(map.containsKey(id)){
      return map.get(id);
    }
      throw new NullPointerException("No existe estrategia con id="+id);
  }
  
  
  
  public enum Operacion {

    SUMA, RESTA, MULTIPLICACION, DIVISION, EQUAL, LTHAN, LETHAN,
    BTHAN, BETHAN, OR, AND, XOR, NOT, ENULL, NEQUAL,
    GET_INT, GET_BOOL, GET_STR, GET_OBJ, SETF, STMT,
    WHILE, WHILE_PAUSE, WHILE_STOP, ADDF, PRINTLN, GET_VAL,
    IF, ELSE_IF, ELSE, CREAR_ENEMIGO, CREAR_ARMA,
    CREAR_POTENCIA, CREAR_IMAGEN, CREAR_ESTRATEGIA,
    FOR, DISMINUIR, AUMENTAR, ASIGNAR_HABILIDAD,ASIGNAR_SALTO,
    ASIGNAR_ARMA,AVANZAR,GIRAR,GET_MUNICIONES,ARMA_PROPIA, BORDE_TABLERO, 
    GET_LIBRE,ASIGNAR_PASO
    
  };
}
