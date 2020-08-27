/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafa;

/**
 *
 * @author Marcin
 */
public class Vector3Ds
{
    public float x;
    public float y;
    public float z;
    
    
    public Vector3Ds()   // tworzy pusty wektor 0,0,0
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public Vector3Ds(Vector3Ds v) // tworzy wektor o skladowych innego wektora
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public Vector3Ds(float x, float y, float z) // tworzy wektor o podanych przy towrzniu obiektu wartosciach
    {
        setTo(x, y, z);
    }
    
    public boolean equal(Object obj) // porownuje podany wektor z innym wektorem na rzecz ktorego zostala 
    {                                // ta metoda wywolana
        Vector3Ds v = (Vector3Ds)obj;
        return (v.x == x && v.y == y && v.z == z);
    }
    
    public boolean equal(float x, float y, float z) // sprawdza czy wektor ma podane do metody skladowe
    {
        return(this.x == x && this.y == y && this.z == z);
    }
    
    public void setTo(Vector3Ds v) // ustawia wektorowi, na rzecz ktorego zostala metoda wywolana 
    {                             // wspolrzedne innego wektora
        setTo(v.x, v.y, v.z);
    }
    
    public void setTo(float x, float y, float z) // metoda ustawiajaca konkretne skladowe typu float
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void add(float x, float y, float z) // dodaje do skladowych wektora podane skladowe
    {
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
    }
    
    public void substract(float x, float y, float z) // odejmuje od skladowych wektora podane skladowe
    {
        add(-x, -y, -z);
    }
    
    public void addVector(Vector3Ds v) // dodaje do wektora skladowe innego wektora
    {
        add(v.x, v.y, v.z);
    }
    
    public void substractVector(Vector3Ds v) // odejmuje od wektora skladowe innego wektora
    {
        add(-v.x, -v.y, -v.z);
    }
    
    public void multiplyByConstant(float m) // mnozy wektor przez stala ( kazda skladowa )
    {
        this.x = this.x * m; 
        this.y = this.y * m;
        this.z = this.z * m;
    }
    
    public void devideByConstant(float d) // dzieli wektor przez stala ( kazda skladowa )
    {
        this.x = this.x / d; 
        this.y = this.y / d;
        this.z = this.z / d;
    }
    
    public float getLengthVector() // metoda zwraca dlugosc wektora
    {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public void normalizeVector()
    {
        devideByConstant(getLengthVector());
    }
    
    public String vectorToString()
    {
        return (this.x + " " + this.y + " " + this.z);
    }
    
    public void rotateX(float angle)
    {
        rotateX((float)Math.cos(angle), (float)Math.sin(angle));
    }
    
    public void rotateY(float angle)
    {
        rotateY((float)Math.cos(angle), (float)Math.sin(angle));
    }
    
    public void rotateZ(float angle)
    {
        rotateZ((float)Math.cos(angle), (float)Math.sin(angle));
    }

    private void rotateX(float cosAngle, float sinAngle) 
    {
        float newY = y*cosAngle - z*sinAngle;
        float newZ = y*sinAngle + z*cosAngle;
        y = newY;
        z = newZ;
    }
    
    private void rotateY(float cosAngle, float sinAngle) 
    {
        float newX = z*sinAngle + x*cosAngle;
        float newZ = z*cosAngle - x*sinAngle;
        x = newX;
        z = newZ;
    }
    
    private void rotateZ(float cosAngle, float sinAngle) 
    {
        float newX = x*cosAngle - y*sinAngle;
        float newY = x*sinAngle + y*cosAngle;
        y = newY;
        x = newX;
    }
    
    public void add(Transform3D xform)
    {
        addRotation(xform);
        addVector(xform.getLocation());
    }

    private void addRotation(Transform3D xform) 
    {
        rotateX(xform.getCosAngleX(), xform.getSinAngleX());
        rotateY(xform.getCosAngleY(), xform.getSinAngleY());
        rotateZ(xform.getCosAngleZ(), xform.getSinAngleZ());
    }
    
    public void substract(Transform3D xform)
    {
        substractVector(xform.getLocation());
        SubRotation(xform);
    }

    private void SubRotation(Transform3D xform) 
    {
        rotateX(xform.getCosAngleX(), -xform.getSinAngleX());
        rotateY(xform.getCosAngleY(), -xform.getSinAngleY());
        rotateZ(xform.getCosAngleZ(), -xform.getSinAngleZ());
    }
    
    float getDotProduct(Vector3Ds v)
    {
        return x*v.x + v.y*y + v.z*z;
    }
    
    void setToCrossProduct(Vector3Ds u, Vector3Ds v)
    {
        float x = u.y*v.z - u.z*v.y;
        float y = u.z*v.x - u.x*v.z;
        float z = u.x*v.y - u.y*v.x;
    
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
}