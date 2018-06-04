/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

/**
 *
 * @author Alex
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();

        conexao = ModuloConexao.conector();
    }

    public void limpaForm() {
        // As linhas abaixo "limpa" os campos do form. 
        txtUsuId.setText(null);
        txtUsuNome.setText(null);
        txtUsuFone.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha.setText(null);
        cboUsuPerfil.setSelectedItem(null);
    }

    private void consultar() {

        String sql = "select * from tbusuarios where iduser=?";

        try {
            // As linhas abaixo preparaqm a consulta ao banco  em função do 
            // que foi digitado nas caixas de texto. O ? é substituído pelo 
            // conteúdo das variáveis.
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            // A linha abaixo executa a query.
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2));
                txtUsuFone.setText(rs.getString(3));
                txtUsuLogin.setText(rs.getString(4));
                txtUsuSenha.setText(rs.getString(5));
                // A linha abaixo se refere ao combobox.
                cboUsuPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                txtUsuId.setText(null);
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void adicionar() {

        String sql = "insert into tbusuarios (iduser,usuario,fone,login,senha,perfil) values(?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            pst.setString(2, txtUsuNome.getText());
            pst.setString(3, txtUsuFone.getText());
            pst.setString(4, txtUsuLogin.getText());
            pst.setString(5, txtUsuSenha.getText());
            pst.setString(6, cboUsuPerfil.getSelectedItem().toString());
            // Validação dos campos obrigatorios.

            if (txtUsuId.getText().isEmpty() || txtUsuNome.getText().isEmpty()
                    || txtUsuLogin.getText().isEmpty()
                    || txtUsuSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela tbusuarios com os dados do formulario.
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio a logica.
                //System.out.println(adicionado);
                if (adicionado >= 0) {
                    JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterar() {

        String sql = "update tbusuarios set usuario=?,fone=?,login=?,senha=?,perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuFone.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuId.getText());
            // Validação dos campos obrigatorios.

            if (txtUsuId.getText().isEmpty() || txtUsuNome.getText().isEmpty()
                    || txtUsuLogin.getText().isEmpty()
                    || txtUsuSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo altera a tabela tbusuarios com os dados do formulario.
                int atualizado = pst.executeUpdate();
                // A linha abaixo serve de apoio a logica.
                //System.out.println(adicionado);
                if (atualizado >= 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com Sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void deletar() {
        // A estrutura abaixo confirma a remoção do usuário.
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                int deletado = pst.executeUpdate();
                if (deletado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com Sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JTextField();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUptade = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuarios");
        setPreferredSize(new java.awt.Dimension(640, 480));
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("* id");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 20, 50, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("* Nome");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 80, 60, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("* Login");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(310, 140, 50, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("* Senha");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 210, 50, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("* Perfil");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(310, 210, 50, 30);
        getContentPane().add(txtUsuId);
        txtUsuId.setBounds(80, 20, 100, 30);

        txtUsuNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuNomeActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuNome);
        txtUsuNome.setBounds(80, 80, 450, 30);

        txtUsuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuLoginActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuLogin);
        txtUsuLogin.setBounds(370, 140, 160, 30);

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));
        cboUsuPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboUsuPerfilActionPerformed(evt);
            }
        });
        getContentPane().add(cboUsuPerfil);
        cboUsuPerfil.setBounds(370, 210, 160, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Fone");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(30, 140, 50, 30);

        txtUsuFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuFoneActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuFone);
        txtUsuFone.setBounds(80, 140, 150, 30);
        getContentPane().add(txtUsuSenha);
        txtUsuSenha.setBounds(80, 210, 150, 30);

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuCreate);
        btnUsuCreate.setBounds(130, 340, 80, 80);

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnUsuRead.setToolTipText("Consultar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuRead);
        btnUsuRead.setBounds(230, 340, 80, 80);

        btnUsuUptade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnUsuUptade.setToolTipText("Atualizar");
        btnUsuUptade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUptade.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuUptade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUptadeActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuUptade);
        btnUsuUptade.setBounds(330, 340, 80, 80);

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Apagar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuDelete);
        btnUsuDelete.setBounds(430, 340, 80, 80);

        jLabel7.setText("* Campos Obrigatórios");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(420, 10, 140, 40);

        setBounds(0, 0, 640, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuNomeActionPerformed

    private void txtUsuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuLoginActionPerformed

    private void txtUsuFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuFoneActionPerformed

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        consultar();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void cboUsuPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboUsuPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboUsuPerfilActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        adicionar();
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        deletar();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed

    private void btnUsuUptadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUptadeActionPerformed
        alterar();
    }//GEN-LAST:event_btnUsuUptadeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUptade;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
