package com.bolivia.burbuja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.SwingWorker;


public class BurbujaPanel extends JPanel{
    
    private int NUM_BOX                 =       5;
    private Dimension dimension         =       new Dimension(320,128);    
    private int max                     =       12;
    private int min                     =       1;
    private BoxNumber[] bNumber;
    
    
    public BurbujaPanel(){
        setSize(dimension);
        setVisible(true);                
    }
    
    @Override
    public void paintComponent(Graphics g){    
        Graphics2D g2 =(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);    
        g2.setColor( new Color(255,255,255) );
        g2.fill(new Rectangle2D.Double(0,0,getWidth(),getHeight()));                 
      
        if(bNumber!=null)
        for(BoxNumber b:bNumber){
            b.draw(g2);
        }
        
    }
    
   
    public void generar(){       
        bNumber = new BoxNumber[NUM_BOX];
        Random rn = new Random();
        for(int i=0;i<NUM_BOX;i++){
            bNumber[i] = new BoxNumber();    
            bNumber[i].x= 10 + bNumber[i].WIDTH * i;
            bNumber[i].y = getHeight()/2 - bNumber[i].HEIGHT/2 ;
            int num = rn.nextInt(max - min + 1) + min;
            bNumber[i].setNumber(String.valueOf(num));
        }
        repaint();
    }
    
    
    public void ordenar(){
        if(bNumber!=null)        
            new BurbujaWorker().execute();
    }
     
   
    public class BurbujaWorker extends SwingWorker<Void, Void> {

        private int SPEED = 11;       
        
        @Override
        protected Void doInBackground() throws Exception {
            
         int i, j;
         BoxNumber aux;
         for(i=0;i<bNumber.length-1;i++)
            for(j=0;j<bNumber.length-i-1;j++)
                if(bNumber[j+1].getValue()<bNumber[j].getValue()){
                      aux=bNumber[j+1];
                      
                      girar(j,j+1);                      
                      bNumber[j+1]=bNumber[j];
                      bNumber[j]=aux;
                   }         
            return null;
        }
        
        
        private void girar(int a , int b){
            //movmiento vertical
            for(int i=0; i< bNumber[0].HEIGHT;i++){
                bNumber[a].y -= 1;
                bNumber[b].y += 1;                   
                try {
                    Thread.sleep(SPEED);
                 } catch (InterruptedException e) {}
                repaint();
            }
            
            //vomiento horizontal
            for(int i=0; i< bNumber[0].WIDTH;i++){
                 bNumber[a].x += 1;
                 bNumber[b].x -= 1;
                 try {
                    Thread.sleep(SPEED);
                 } catch (InterruptedException e) {}
                repaint();
            }
            
            //movimiento vertical
            for(int i=0; i< bNumber[0].HEIGHT;i++){
                bNumber[a].y += 1;
                bNumber[b].y -= 1;                   
                try {
                    Thread.sleep(SPEED);
                 } catch (InterruptedException e) {}
                repaint();
            }
        }
        
    }
    
}
