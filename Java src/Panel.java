/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sun.java2d.loops.DrawLine;

/**
 *
 * @author Marcin
 */
public class Panel extends JPanel implements KeyListener
{
    int screenWidth, screenHeigth;

    private Polygon3D leftWall = new Polygon3D( // OK
            new Vector3Ds(-100, -100, -100), 
            new Vector3Ds(-100, -100, 100), 
            new Vector3Ds(-100, 100, 100), 
            new Vector3Ds(-100, 100, -100));
    
    private Polygon3D rightWall = new Polygon3D( // OK
            new Vector3Ds(100, 100, 100), 
            new Vector3Ds(100, -100, 100), 
            new Vector3Ds(100, -100, -100), 
            new Vector3Ds(100, 100, -100));
    
    private Polygon3D frontWall = new Polygon3D( // OK
            new Vector3Ds(-100, 100, 100), 
            new Vector3Ds(-100, -100, 100),
            new Vector3Ds(100, -100, 100), 
            new Vector3Ds(100, 100, 100));
    
    private Polygon3D backWall = new Polygon3D(  //OK
            new Vector3Ds(-100, 100, -100), 
            new Vector3Ds(100, 100, -100), 
            new Vector3Ds(100, -100, -100), 
            new Vector3Ds(-100, -100, -100));
    
    private Polygon3D upWall = new Polygon3D(  //OK
            new Vector3Ds(-100, 100, 100), 
            new Vector3Ds(100, 100, 100), 
            new Vector3Ds(100, 100, -100), 
            new Vector3Ds(-100, 100, -100));
    
    private Polygon3D downWall = new Polygon3D( //OK
            new Vector3Ds(-100, -100, 100), 
            new Vector3Ds(-100, -100, -100), 
            new Vector3Ds(100, -100, -100), 
            new Vector3Ds(100, -100, 100));
    
    
        private Polygon3D leftWall2 = new Polygon3D( 
            new Vector3Ds(200, 300, 200), 
            new Vector3Ds(200, 300, -200), 
            new Vector3Ds(200, -100, -200), 
            new Vector3Ds(200, -100, 200));
    
    private Polygon3D rightWall2 = new Polygon3D( 
            new Vector3Ds(600, 300, -200), 
            new Vector3Ds(600, 300, 200), 
            new Vector3Ds(600, -100, 200), 
            new Vector3Ds(600, -100, -200));
    
    private Polygon3D frontWall2 = new Polygon3D( 
            new Vector3Ds(600, 300, 200), 
            new Vector3Ds(200, 300, 200),
            new Vector3Ds(200, -100, 200), 
            new Vector3Ds(600, -100, 200));
    
    private Polygon3D backWall2 = new Polygon3D( 
            new Vector3Ds(200, 300, -200), 
            new Vector3Ds(600, 300, -200), 
            new Vector3Ds(600, -100, -200), 
            new Vector3Ds(200, -100, -200));
    
    private Polygon3D upWall2 = new Polygon3D(  
            new Vector3Ds(600, 300, -200), 
            new Vector3Ds(200, 300, -200), 
            new Vector3Ds(200, 300, 200), 
            new Vector3Ds(600, 300, 200));
    
    private Polygon3D downWall2 = new Polygon3D( 
            new Vector3Ds(600, -100, 200), 
            new Vector3Ds(200, -100, 200), 
            new Vector3Ds(200, -100, -200), 
            new Vector3Ds(600, -100, -200));
    
    private Polygon3D[] Polygon1 = { backWall, downWall, frontWall, leftWall, upWall, rightWall };  
    private Polygon3D[] Polygon2 = { backWall2, downWall2, frontWall2, leftWall2, upWall2, rightWall2 };
    
    Color polygonColors[] = {Color.RED, Color.CYAN, Color.YELLOW, Color.PINK, Color.BLUE, Color.GREEN};
    
    private Transform3D treeTransform = new Transform3D(0, 0, -1500);
    private Polygon3D transformedPolygon = new Polygon3D();
    private ViewWindow viewWindow;
    private Polygon3D orderChecker = new Polygon3D();
    private float front = Float.NEGATIVE_INFINITY;

    float maxPoly1 = 0;
    float maxPoly2 = 0;
    
    public Panel(int width, int height) 
    {
        super();
        screenHeigth = height;
        screenWidth = width;
        setBounds(0, 0, width, height);
        setVisible(true);
        init();
        repaint();
        addKeyListener(this);
        setFocusable(true);
        
        JOptionPane.showMessageDialog(this, "Sterowanie w programie :\n \n"+"W,S,A,D - Poruszanie kamery \n"+"Strzałki - Rotacje \n"+"PgUp,PgDn - Zoom");
    }
    
    public void init()
    {
        viewWindow = new ViewWindow(0, 0, screenWidth, screenHeigth, (float)Math.toRadians(75));
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2; 
        g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeigth);
        g2.setColor(Color.white);

        
        for(int i = 0; i < Polygon2.length ; i++)
        {
            checkOrder(Polygon2[i]);
        }

        maxPoly2 = front - 60;
        
        front = Float.NEGATIVE_INFINITY;
        
        for(int i = 0; i < Polygon1.length ; i++)
        {
            checkOrder(Polygon1[i]);
        }
        
        maxPoly1 = front;
        
        
        if(maxPoly1 >= maxPoly2)
        {
            for(int i = 0 ; i < Polygon2.length ; i++)
            {
                transformAndDraw(g2, Polygon2[i], polygonColors[i]); 
            }
                for(int i=0; i < Polygon1.length ; i++)
                {
                    transformAndDraw(g2, Polygon1[i], polygonColors[Polygon1.length - 1 - i]); 
                }
        }
        else 
        {
        for(int i=0; i < Polygon1.length ; i++)
                {
                    transformAndDraw(g2, Polygon1[i], polygonColors[Polygon1.length - 1 - i]); 
                }
            for(int i = 0 ; i < Polygon2.length ; i++)
            {
                transformAndDraw(g2, Polygon2[i], polygonColors[i]);  // inne kolory
            }
        }
        
        
        maxPoly1 = 0; maxPoly2 = 0;

        repaint();
    }
    
    public void update(long elapsedTime)
    {
        elapsedTime = Math.min(elapsedTime, 100);
        
        treeTransform.rotateAngleY(0.002f*elapsedTime);
        
    }
    
    private void checkOrder(Polygon3D poly)
    {
        orderChecker.setTo(poly);
        orderChecker.add(treeTransform);
        
         
       Vector3Ds calcNormal = orderChecker.calcNormal();
       boolean facing = orderChecker.isFacing(new Vector3Ds(0, 0, 0));
       
       if(facing == true)
        {
            orderChecker.project(viewWindow);
            
            if(front < orderChecker.getVertex(0).z)
            {
               front = orderChecker.getVertex(0).z;
            }
        }
      
    }

    private void transformAndDraw(Graphics2D g, Polygon3D poly, Color color) 
    {
       transformedPolygon.setTo(poly);
       
       transformedPolygon.add(treeTransform);
       
       Vector3Ds calcNormal = transformedPolygon.calcNormal();
       boolean facing = transformedPolygon.isFacing(new Vector3Ds(0, 0, 0));
       
       if(facing == true)
        {
       
       transformedPolygon.project(viewWindow);
       
            //System.out.println(transformedPolygon.getVertex(0).z);
       
        GeneralPath path = new GeneralPath();
        Vector3Ds v = transformedPolygon.getVertex(0);
        path.moveTo(v.x, v.y);
        
        for ( int i = 1 ; i < transformedPolygon.getNumVertices() ; i++)
        {
            v = transformedPolygon.getVertex(i);
            path.lineTo(v.x, v.y);
        }
        g.setColor(color);
        g.fill(path);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)  // rozróżnia małe/duze litery
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)  // nierozróżnia małych/dużych liter - ważny klawisz
    {
        int keyCode = e.getKeyCode();
        
        switch(keyCode)
        {
            case KeyEvent.VK_UP :
            treeTransform.rotateAngleX(0.002f*30); 
            break;
            
            case KeyEvent.VK_DOWN:
            treeTransform.rotateAngleX(-0.002f*30); 
            break;
            
            case KeyEvent.VK_LEFT:
            treeTransform.rotateAngleY(0.002f*30); 
            break;
            
            case KeyEvent.VK_RIGHT:
            treeTransform.rotateAngleY(-0.002f*30); 
            break;
            
            case KeyEvent.VK_A:
            treeTransform.getLocation().x = treeTransform.getLocation().x + 3;
            break;
            
            case KeyEvent.VK_D:
            treeTransform.getLocation().x = treeTransform.getLocation().x - 3;
            break;
            
            case KeyEvent.VK_W:
            treeTransform.getLocation().y = treeTransform.getLocation().y - 3;
            break;
            
            case KeyEvent.VK_S:
            treeTransform.getLocation().y = treeTransform.getLocation().y + 3;
            break;
            
            case KeyEvent.VK_PAGE_UP:
            if(treeTransform.getLocation().z < -1000.0)  
            {       
                treeTransform.getLocation().z = treeTransform.getLocation().z + 3;
            }
                break;
            
            case KeyEvent.VK_PAGE_DOWN:
            treeTransform.getLocation().z = treeTransform.getLocation().z - 3;
            break;
            
            case KeyEvent.VK_ENTER:
             
            break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        
    }
}
