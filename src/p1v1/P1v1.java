/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p1v1;

import compiler.bad.Enemigos;
import gui.CompilerSystem.List;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ce
 */
public class P1v1 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {

    final ArrayList l = new ArrayList();




    synchronized (l) {

      for (;;) {
        if (!l.isEmpty()) {
          System.err.println("busy");
          l.wait();
          System.out.println("ok");
        } else {
          l.add("dddf");
          new Thread(new Runnable() {
            @Override
            public void run() {
              System.err.println("start");
              try {
                Thread.sleep(2000);
                System.err.println("ok 2 seg...");
                synchronized (l) {
                  l.clear();
                  //l.notify();
                }
              } catch (InterruptedException ex) {
                Logger.getLogger(P1v1.class.getName()).log(Level.SEVERE, null, ex);
              }

            }
          }).start();
        }
      }

    }
  }
}
