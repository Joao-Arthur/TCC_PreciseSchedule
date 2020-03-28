package PreciseSchedule.InterfacesGraficas;

import PreciseSchedule.Desenhistas.DesenhaCirculo;
import PreciseSchedule.Executavel;
import PreciseSchedule.ThreadTocarMusica;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

//toca a música nesse frame, caso ela exista, para parar quando o usuário der um ok

/*Essa classe vai ser a UI do evento cadastrado*/
public class Evento extends JFrame
{
    private final DesenhaCirculo circulo;
    private final String importancia;
    private final String somAlarme;
    private TocaMusica musica;
    private Principal principal;
    boolean criou;
    
    public Evento(Principal principal, String nome, String inicio, String fim, int idImportancia, String somAlarme, int idCategoria, int r, int g, int b)
    {
        principal.atualizaHabilitado(false);
        
        this.principal = principal;
        
        circulo = new DesenhaCirculo(r, g, b);
        
        importancia = Executavel.getProp("I" + idImportancia);
        setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource(Executavel.ICONE)).getImage());
        initComponents();
        setVisible(true);
        setTitle(Executavel.getProp("NaHoraEvento") + Executavel.getProp("C" + idCategoria) + "!");
        
        lNaHora.setText(Executavel.getProp("NaHora"));
        lAPartir.setText(Executavel.getProp("APartir"));
        lAteAs.setText(Executavel.getProp("Ate"));
        
        lNomeEvt.setText(nome);
        lInicioEvt.setText(inicio);
        lFinalEvt.setText(fim);
        
        lImportancia.setText(Executavel.getProp("Importancia"));
        lImportaciaEvt.setText(importancia);
        
        this.somAlarme = somAlarme;
        
        //aqui é o pulo do gato, toca a musica
        
        if(!(somAlarme.equals("0")))
        {
            System.out.println("é diferente");
            musica = new TocaMusica();
            musica.start();
        }
        
        criou = true;
        
        requestFocus();
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lNaHora = new javax.swing.JLabel();
        lNomeEvt = new javax.swing.JLabel();
        lInicioEvt = new javax.swing.JLabel();
        lFinalEvt = new javax.swing.JLabel();
        lAPartir = new javax.swing.JLabel();
        lAteAs = new javax.swing.JLabel();
        btConfirma = new javax.swing.JButton();
        pImportanciaEvt = circulo;
        lImportaciaEvt = new javax.swing.JLabel();
        lImportancia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lNaHora.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lNaHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lNaHora.setText("Está na hora de");

        lNomeEvt.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lNomeEvt.setText("Nome");

        lInicioEvt.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lInicioEvt.setText("00:00:00");

        lFinalEvt.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lFinalEvt.setText("00:00:00");

        lAPartir.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lAPartir.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lAPartir.setText("A partir de");

        lAteAs.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lAteAs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lAteAs.setText("Até às");

        btConfirma.setText("OK");
        btConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pImportanciaEvtLayout = new javax.swing.GroupLayout(pImportanciaEvt);
        pImportanciaEvt.setLayout(pImportanciaEvtLayout);
        pImportanciaEvtLayout.setHorizontalGroup(
            pImportanciaEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        pImportanciaEvtLayout.setVerticalGroup(
            pImportanciaEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lImportaciaEvt.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lImportaciaEvt.setText("Importancia");

        lImportancia.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lImportancia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lImportancia.setText("Importancia");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lAPartir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lAteAs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(pImportanciaEvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lImportancia))
                    .addComponent(lNaHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lFinalEvt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lImportaciaEvt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lNomeEvt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lInicioEvt, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btConfirma)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lFinalEvt, lImportaciaEvt, lInicioEvt, lNomeEvt});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lNomeEvt)
                    .addComponent(lNaHora))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lInicioEvt)
                    .addComponent(lAPartir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lAteAs)
                    .addComponent(lFinalEvt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pImportanciaEvt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lImportaciaEvt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lImportancia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btConfirma)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lAPartir, lAteAs, lFinalEvt, lInicioEvt});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /*Fecha a tela e para a música*/
    private void btConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmaActionPerformed
        if(criou)
        {
            if(!(somAlarme.equals("0")))
            {
                musica.tocaMusica.pararMusica = true;
            }
            this.dispose();
        }
    }//GEN-LAST:event_btConfirmaActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        principal.atualizaHabilitado(true);
        principal.atualizaTabela();
    }//GEN-LAST:event_formWindowClosed

    private class TocaMusica extends Thread
    {
        //objeto da classe que toca a música
        public ThreadTocarMusica tocaMusica;
        
        @Override
        public void run()
        {
            System.out.println("tocando:" + somAlarme);
            //código que toca a música
            //this.getClass().getClassLoader().getResourceAsStream("src/sons/" + somAlarme + ".wav");
            //File soundFile = new File("src/sons/" + somAlarme + ".wav");
            try
            {
                InputStream buff = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("sons/" + somAlarme + ".wav"));
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buff);
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info dataLineInfo = new DataLine.Info( SourceDataLine.class,audioFormat );
                SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                
                tocaMusica = new ThreadTocarMusica(audioFormat, audioInputStream, sourceDataLine);
                tocaMusica.start();
            }
            //mostra uma mensagem de erro para o usuário
            catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) { ThreadTocarMusica.erro(ex); }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btConfirma;
    private javax.swing.JLabel lAPartir;
    private javax.swing.JLabel lAteAs;
    private javax.swing.JLabel lFinalEvt;
    private javax.swing.JLabel lImportaciaEvt;
    private javax.swing.JLabel lImportancia;
    private javax.swing.JLabel lInicioEvt;
    private javax.swing.JLabel lNaHora;
    private javax.swing.JLabel lNomeEvt;
    private javax.swing.JPanel pImportanciaEvt;
    // End of variables declaration//GEN-END:variables
}