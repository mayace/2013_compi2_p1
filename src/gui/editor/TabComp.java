/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.editor;

import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author ce
 */
public class TabComp extends javax.swing.JPanel {

  /**
   * Creates new form TabComp
   */
  public TabComp() {
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

    title_label = new javax.swing.JLabel();
    close_button = new javax.swing.JButton();

    title_label.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
    title_label.setText("Title");

    close_button.setText("X");
    close_button.setBorderPainted(false);
    close_button.setFocusable(false);
    close_button.setMargin(new java.awt.Insets(0, 0, 0, 0));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(title_label, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(close_button))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(close_button)
      .addComponent(title_label, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton close_button;
  private javax.swing.JLabel title_label;
  // End of variables declaration//GEN-END:variables

  public JButton getClose_button() {
    return close_button;
  }

  public JLabel getTitle_label() {
    return title_label;
  }

  public void setClose_button(JButton close_button) {
    this.close_button = close_button;
  }

  public void setTitle_label(JLabel title_label) {
    this.title_label = title_label;
  }
}
