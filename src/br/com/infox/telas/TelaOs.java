package br.com.infox.telas;

import br.com.infox.dal.ModuloConexao;
import java.sql.*;
import javax.swing.JOptionPane;
// A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Alex
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // A linha abaixo cria uma variavel para armazenar um texto de acordo com o radion button selecionado.
    private String tipo;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisarCliente() {

        String sql = "select idcli as Id, nomecli as Nome, fonecli as Fone from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteúdo da caixa pesquisar para o ?.
            // Atenção ao % - continuação da string sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();

            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela.
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setarCampos() {

        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
    }

    private void emitirOS() {
        String sql = "insert into tbos (tipo,situacao,equipamentos,defeito,servico,tecnico,valor,idcli) values(?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamentos.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            // .replace substitui a virgula pelo ponto.
            pst.setString(7, txtOsValorTotal.getText().replace(",", "."));
            pst.setString(8, txtCliId.getText());

            // Validação dos campos obrigatorios.
            if (txtCliId.getText().isEmpty() || txtOsEquipamentos.getText().isEmpty() || txtOsDefeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
                    txtCliId.setText(null);
                    txtOsEquipamentos.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsValorTotal.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisarOs() {

        String numOs = JOptionPane.showInputDialog("Número da OS");
        String sql = "select * from tbos where os= " + numOs;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtOsData.setText(rs.getString(2));
                // Setando os radio buttons
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOs.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOsSituacao.setSelectedItem(rs.getString(4));
                txtOsEquipamentos.setText(rs.getString(5));
                txtOsDefeito.setText(rs.getString(6));
                txtOsServico.setText(rs.getString(7));
                txtOsTecnico.setText(rs.getString(8));
                txtOsValorTotal.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));
                // Evitando problemas.
                btnOsCreate.setEnabled(false);
                txtCliPesquisar.setEnabled(false);
                tblClientes.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada!");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS inválida!");
            //System.out.println(e);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void alterarOs() {

        String sql = "update tbos set tipo=?,situacao=?,equipamentos=?,defeito=?,servico=?,tecnico=?,valor=? where os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamentos.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            // .replace substitui a virgula pelo ponto.
            pst.setString(7, txtOsValorTotal.getText().replace(",", "."));
            pst.setString(8, txtOs.getText());

            // Validação dos campos obrigatorios.
            if (txtCliId.getText().isEmpty() || txtOsEquipamentos.getText().isEmpty() || txtOsDefeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso!");
                    txtOs.setText(null);
                    txtOsData.setText(null);
                    txtCliId.setText(null);
                    txtOsEquipamentos.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsValorTotal.setText(null);
                    // Habilitar os objetos.
                    btnOsCreate.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblClientes.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluirOs() {

        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOs.getText());
                int deletado = pst.executeUpdate();
                if (deletado > 0) {
                    JOptionPane.showMessageDialog(null, "OS removida com Sucesso!");
                    txtOs.setText(null);
                    txtOsData.setText(null);
                    txtCliId.setText(null);
                    txtOsEquipamentos.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsValorTotal.setText(null);
                    // Habilitar os objetos.
                    btnOsCreate.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblClientes.setEnabled(true);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        rbtOrcamento = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        txtOsData = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboOsSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtCliId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtOsEquipamentos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsDefeito = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOsServico = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOsTecnico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsValorTotal = new javax.swing.JTextField();
        btnOsCreate = new javax.swing.JButton();
        btnOsRead = new javax.swing.JButton();
        btnOsUptade = new javax.swing.JButton();
        btnOsDelete = new javax.swing.JButton();
        btnOsImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data ");

        txtOs.setEditable(false);

        buttonGroup1.add(rbtOrcamento);
        rbtOrcamento.setText("Orçamento");
        rbtOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        txtOsData.setEditable(false);
        txtOsData.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rbtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsData))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrcamento)
                    .addComponent(rbtOs))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 10, 260, 120);

        jLabel3.setText("Situação");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 150, 60, 30);

        cboOsSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na Bancada ", "Entrega Ok", "Orçamento REPROVADO", "Aguardando Aprovação ", "Aguardando Peças ", "Abandono pelo cliente", "Retornou" }));
        getContentPane().add(cboOsSituacao);
        cboOsSituacao.setBounds(70, 150, 210, 30);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/search.png"))); // NOI18N

        jLabel5.setText("* id");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        txtCliId.setEditable(false);
        txtCliId.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(290, 10, 320, 190);

        jLabel6.setText("* Equipamento");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 210, 90, 30);
        getContentPane().add(txtOsEquipamentos);
        txtOsEquipamentos.setBounds(100, 210, 440, 30);

        jLabel7.setText("* Defeito");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(40, 250, 60, 30);
        getContentPane().add(txtOsDefeito);
        txtOsDefeito.setBounds(100, 250, 440, 30);

        jLabel8.setText("Serviço");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(40, 290, 60, 30);
        getContentPane().add(txtOsServico);
        txtOsServico.setBounds(100, 290, 440, 30);

        jLabel9.setText("Técnico");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(40, 330, 60, 30);
        getContentPane().add(txtOsTecnico);
        txtOsTecnico.setBounds(100, 330, 200, 30);

        jLabel10.setText("Valor Total");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(320, 330, 70, 30);

        txtOsValorTotal.setText("0");
        getContentPane().add(txtOsValorTotal);
        txtOsValorTotal.setBounds(390, 330, 150, 30);

        btnOsCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOsCreate.setToolTipText("Adicionar");
        btnOsCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsCreateActionPerformed(evt);
            }
        });
        getContentPane().add(btnOsCreate);
        btnOsCreate.setBounds(70, 370, 80, 70);

        btnOsRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnOsRead.setToolTipText("Consultar");
        btnOsRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsReadActionPerformed(evt);
            }
        });
        getContentPane().add(btnOsRead);
        btnOsRead.setBounds(170, 370, 80, 70);

        btnOsUptade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnOsUptade.setToolTipText("Atualizar");
        btnOsUptade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsUptade.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsUptade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsUptadeActionPerformed(evt);
            }
        });
        getContentPane().add(btnOsUptade);
        btnOsUptade.setBounds(270, 370, 80, 70);

        btnOsDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnOsDelete.setToolTipText("Apagar");
        btnOsDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnOsDelete);
        btnOsDelete.setBounds(370, 370, 80, 70);

        btnOsImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/print.png"))); // NOI18N
        btnOsImprimir.setToolTipText("Imprimir OS");
        btnOsImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnOsImprimir);
        btnOsImprimir.setBounds(470, 370, 80, 70);

        setBounds(0, 0, 640, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOsCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsCreateActionPerformed
        emitirOS();
    }//GEN-LAST:event_btnOsCreateActionPerformed

    private void btnOsReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsReadActionPerformed
        pesquisarOs();
    }//GEN-LAST:event_btnOsReadActionPerformed

    private void btnOsUptadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsUptadeActionPerformed
        alterarOs();
    }//GEN-LAST:event_btnOsUptadeActionPerformed

    private void btnOsDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsDeleteActionPerformed
        excluirOs();
    }//GEN-LAST:event_btnOsDeleteActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        pesquisarCliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcamentoActionPerformed
        // A linha abaixo atribui um texto a variavel tipo se o radio button estiver selecionado.
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcamentoActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // A linha abaixo atribui um texto a variavel tipo se o radio button estiver selecionado.
        tipo = "OS";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao abrir o form marcar o radio button Orçamento
        rbtOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsCreate;
    private javax.swing.JButton btnOsDelete;
    private javax.swing.JButton btnOsImprimir;
    private javax.swing.JButton btnOsRead;
    private javax.swing.JButton btnOsUptade;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOrcamento;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtOsDefeito;
    private javax.swing.JTextField txtOsEquipamentos;
    private javax.swing.JTextField txtOsServico;
    private javax.swing.JTextField txtOsTecnico;
    private javax.swing.JTextField txtOsValorTotal;
    // End of variables declaration//GEN-END:variables
}
