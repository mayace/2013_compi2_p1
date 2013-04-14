/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Compiler {

  public static HashMap<String, Object> compile(Path file) throws Exception {

    HashMap<String, Object> map = new HashMap<>();
    if (file != null) {
      final String path = file.toString().toLowerCase();
      if (path.endsWith(".lvl")) {
        InputStream input = Files.newInputStream(file);
        compiler.lvl.Scanner s = new compiler.lvl.Scanner(input);
        compiler.lvl.Parser p = new compiler.lvl.Parser(s);

        p.parse();
        p.errors.addAll(s.errors);
        map.put("errores", p.errors);
        map.put("espacios", p.espacios);
        map.put("estructura", p.estructura);
      } else if (path.endsWith(".psj")) {
        InputStream input = Files.newInputStream(file);
        compiler.psj.Scanner s = new compiler.psj.Scanner(input);
        compiler.psj.Parser p = new compiler.psj.Parser(s);

        p.parse();
        p.errors.addAll(s.errors);
        map.put("errores", p.errors);
        map.put("personaje", p.personaje);
      } else if (path.endsWith(".bad")) {
        InputStream input = Files.newInputStream(file);
        compiler.bad.Scanner s = new compiler.bad.Scanner(input);
        compiler.bad.Parser p = new compiler.bad.Parser(s);

        p.parse();
        p.errores.addAll(s.errors);
        map.put("errores", p.errores);
        map.put("enemigo", p.enemigos);
      } else {
        System.err.println("Archivo desconocido...");
      }
    } else {
      System.err.println("Archivo nulo...");
    }
    return map;
  }
}
