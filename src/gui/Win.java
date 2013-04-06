/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import compiler.lvl.Estructura;
import gui.CompilerSystem.DirViewer;
import gui.CompilerSystem.List;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author ce
 */
public class Win extends javax.swing.JFrame {

  /**
   * Creates new form Win
   */
  public Win() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    maps_csystem = new gui.CompilerSystem.CSystem();
    psj_csystem = new gui.CompilerSystem.CSystem();
    bad_csystem = new gui.CompilerSystem.CSystem();
    play_button = new javax.swing.JButton();
    menuBar = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    openMenuItem = new javax.swing.JMenuItem();
    saveMenuItem = new javax.swing.JMenuItem();
    saveAsMenuItem = new javax.swing.JMenuItem();
    exitMenuItem = new javax.swing.JMenuItem();
    editMenu = new javax.swing.JMenu();
    cutMenuItem = new javax.swing.JMenuItem();
    copyMenuItem = new javax.swing.JMenuItem();
    pasteMenuItem = new javax.swing.JMenuItem();
    deleteMenuItem = new javax.swing.JMenuItem();
    helpMenu = new javax.swing.JMenu();
    contentsMenuItem = new javax.swing.JMenuItem();
    aboutMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setPreferredSize(new java.awt.Dimension(800, 600));
    getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

    jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));
    jPanel1.add(maps_csystem);
    maps_csystem.dirViewer.setTitle("Mapas");
    maps_csystem.dirViewer.setDir(FileSystems.getDefault().getPath("mapas"));
    maps_csystem.dirViewer.setFilter(new DirViewer.FFilter("*.lvl","Maps files"));
    jPanel1.add(psj_csystem);
    psj_csystem.dirViewer.setTitle("Personajes");
    psj_csystem.dirViewer.setDir(FileSystems.getDefault().getPath("personajes"));
    psj_csystem.dirViewer.setFilter(new DirViewer.FFilter("*.psj","Personaje file"));
    jPanel1.add(bad_csystem);
    bad_csystem.dirViewer.setTitle("Enemigos");
    bad_csystem.dirViewer.setDir(FileSystems.getDefault().getPath("enemigos"));
    bad_csystem.dirViewer.setFilter(new DirViewer.FFilter("*.bad","Enemigo file"));

    getContentPane().add(jPanel1);

    play_button.setText("Play");
    play_button.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        play_buttonActionPerformed(evt);
      }
    });
    getContentPane().add(play_button);

    fileMenu.setMnemonic('f');
    fileMenu.setText("File");

    openMenuItem.setMnemonic('o');
    openMenuItem.setText("Open");
    fileMenu.add(openMenuItem);

    saveMenuItem.setMnemonic('s');
    saveMenuItem.setText("Save");
    fileMenu.add(saveMenuItem);

    saveAsMenuItem.setMnemonic('a');
    saveAsMenuItem.setText("Save As ...");
    saveAsMenuItem.setDisplayedMnemonicIndex(5);
    fileMenu.add(saveAsMenuItem);

    exitMenuItem.setMnemonic('x');
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    editMenu.setMnemonic('e');
    editMenu.setText("Edit");

    cutMenuItem.setMnemonic('t');
    cutMenuItem.setText("Cut");
    editMenu.add(cutMenuItem);

    copyMenuItem.setMnemonic('y');
    copyMenuItem.setText("Copy");
    editMenu.add(copyMenuItem);

    pasteMenuItem.setMnemonic('p');
    pasteMenuItem.setText("Paste");
    editMenu.add(pasteMenuItem);

    deleteMenuItem.setMnemonic('d');
    deleteMenuItem.setText("Delete");
    editMenu.add(deleteMenuItem);

    menuBar.add(editMenu);

    helpMenu.setMnemonic('h');
    helpMenu.setText("Help");

    contentsMenuItem.setMnemonic('c');
    contentsMenuItem.setText("Contents");
    helpMenu.add(contentsMenuItem);

    aboutMenuItem.setMnemonic('a');
    aboutMenuItem.setText("About");
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
      System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

  private void play_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play_buttonActionPerformed
    // TODO add your handling code here:
    HashSet<List.Cell> set = maps_csystem.getSet();
    if (!set.isEmpty()) {
      for (List.Cell cell : set) {
        HashMap<String, Object> map = (HashMap<String, Object>) cell.getValue();
        Estructura estructura = (Estructura) map.get("estructura");
        if (estructura != null) {
          getWe().getEscenario().setEstructura(estructura);
        }
      }
      getWe().setVisible(true);
    }
  }//GEN-LAST:event_play_buttonActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(Win.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Win.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Win.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Win.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        Win win = new Win();
        win.setLocationRelativeTo(null);
        win.setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem aboutMenuItem;
  private gui.CompilerSystem.CSystem bad_csystem;
  private javax.swing.JMenuItem contentsMenuItem;
  private javax.swing.JMenuItem copyMenuItem;
  private javax.swing.JMenuItem cutMenuItem;
  private javax.swing.JMenuItem deleteMenuItem;
  private javax.swing.JMenu editMenu;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenu helpMenu;
  private javax.swing.JPanel jPanel1;
  private gui.CompilerSystem.CSystem maps_csystem;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem openMenuItem;
  private javax.swing.JMenuItem pasteMenuItem;
  private javax.swing.JButton play_button;
  private gui.CompilerSystem.CSystem psj_csystem;
  private javax.swing.JMenuItem saveAsMenuItem;
  private javax.swing.JMenuItem saveMenuItem;
  // End of variables declaration//GEN-END:variables
  gui.escenario.Win we = null;

  public gui.escenario.Win getWe() {
    if (we == null) {
      we = new gui.escenario.Win();
      we.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      we.setLocationRelativeTo(this);
    }
    return we;
  }
}