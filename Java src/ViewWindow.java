/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafa;

import java.awt.Rectangle;

/**
 *
 * @author Marcin
 */
public class ViewWindow 
{
    
    private Rectangle bounds;
    private float angle;
    private float distanceToCamera;
    
    public ViewWindow(int left, int top, int width, int height, float angle)
    {                               // konstruktor, tworzy okno widoku z podanych parametrow    
        bounds = new Rectangle();
        this.angle = angle;
        setBounds(left, top, width, height);
    }

    private void setBounds(int left, int top, int width, int height) 
    {                               // funckja ustala konkretne wartosci okna podane jako argumenty 
        bounds.x = left;
        bounds.y = top;
        bounds.height = height;
        bounds.width = width;
        
        distanceToCamera = (bounds.width / 2) / (float)(Math.tan(angle/2));
    }
    
    public void setAngle(float angle) //ustawia nowy kąt widzenia - konieczne wyliczenie nowej odl. kamery
    {
        this.angle = angle;
        
        distanceToCamera = (bounds.width / 2) / (float)(Math.tan(angle));
    }
    
    public float getAngle()
    {
        return angle;
    }

    public float getDistanceToCamera() 
    {
        return distanceToCamera;
    }
   
    public int getWidth()
    {
        return bounds.width;
    }
    
    public int getHeight()
    {
        return bounds.height;
    }
    
    public int getTopOffset() // pobiera odleglosc okna od gory ekranu
    {
        return bounds.y;
    }
    
    public int getLeft() // -||- od lewej strony ekranu
    {
        return bounds.x;
    }
    
    public float convertFromViewXtoScreenX(float x) // konwersja z okna widoku na współrzędne ekarnowe X
    {
        return bounds.x + bounds.width/2 + x;
    }
    
    public float convertFromViewYtoScreenY(float y)// konwersja z okna widoku na współrzędne ekarnowe Y
    {
        return bounds.y + bounds.height / 2 - y;
    }
    
    public float convertFromScreenXtoViewX(float x) // odwrtonie, ekranowe->okno widoku ( X )
    {
        return x - bounds.x - bounds.width / 2;
    }
    
    public float convertFromScreenYtoViewY(float y) // odwrtonie, ekranowe->okno widoku ( Y )
    {
        return -y + bounds.y + bounds.height / 2;
    }
    
    public void project(Vector3Ds v)  // rzutuje podany wektor na ekran
    {
        // rzutuje na okno obrazu -> mamy wspolrzedne w tym malym okienku widoku
        v.x = distanceToCamera * v.x / (-v.z) ; 
        v.y = distanceToCamera * v.y / (-v.z) ;
        
        // konwertuje na wspolrzedne ekranowe, pora z okienka widoku moc wyswietlic to w jakis sposob 
        // na naszym ekranie monitora
        v.x = convertFromViewXtoScreenX(v.x);
        v.y = convertFromViewYtoScreenY(v.y);
    }
    
}
