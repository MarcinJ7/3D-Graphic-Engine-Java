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
public class Transform3D 
{
    protected Vector3Ds location;  // wektor translacji - przesuniecia
    private float sinAngleX; //  sin i
    private float cosAngleX; //  cos kąta przy osi x, obracanie wokół osi z ( przeciwnie do wsk. zeg )
    private float sinAngleY; //  sin i
    private float cosAngleY; //  cos kąta przy osi y, obracanie wokół osi x ( przeciwnie do wsk. zeg )
    private float sinAngleZ; //  sin i 
    private float cosAngleZ; //  cos kąta przy osi z, obracanie wokół osi y ( przeciwnie do wsk. zeg )

public Transform3D() // tworzy pusta transformacje
{
    this(0,0,0);
}

public Transform3D(float x, float y, float z) // tworzy transformacje i translacje 
{                                               //o zadanych parametrach x,y,z   
    location = new Vector3Ds(x, y, z);
    setAngle(0,0,0);
}

public Transform3D(Transform3D v)
{
    location = new Vector3Ds();
    setTo(v);
}

    private void setAngle(float angleX, float angleY,float angleZ) // ustawia katy rotacji wokol osi x,y,z
    {
        setAngleX(angleX); // wokol X
        setAngleY(angleY); // wokol Y
        setAngleZ(angleZ); // wokol Z
    }

    private void setAngleX(float angleX) 
    {
        sinAngleX = (float) Math.sin(angleX);
        cosAngleX = (float) Math.cos(angleX);
    }

    private void setAngleY(float angleY)
    {
        sinAngleY = (float) Math.sin(angleY);
        cosAngleY = (float) Math.cos(angleY);
    }

    private void setAngleZ(float angleZ) 
    {
        sinAngleZ = (float) Math.sin(angleZ);
        cosAngleZ = (float) Math.cos(angleZ);
    }

    public float getCosAngleX() 
    {
        return cosAngleX;
    }

    public float getCosAngleY() 
    {
        return cosAngleY;
    }

    public float getCosAngleZ() 
    {
        return cosAngleZ;
    }

    public float getSinAngleX() 
    {
        return sinAngleX;
    }

    public float getSinAngleY() 
    {
        return sinAngleY;
    }

    public float getSinAngleZ() 
    {
        return sinAngleZ;
    }

    public Vector3Ds getLocation() 
    {
        return location;
    }

    private void setTo(Transform3D v) // ustawia parametry jednej transformacji dla innej transformacji
    {
        this.cosAngleX = v.cosAngleX;
        this.cosAngleY = v.cosAngleY;
        this.cosAngleZ = v.cosAngleZ;
        this.location = v.location;
        this.sinAngleX = v.sinAngleX;
        this.sinAngleY = v.sinAngleY;
        this.sinAngleZ = v.sinAngleZ;
    }
    
    public Object cloneTransform() // tworzy nowa transformacje z parametrami obiektu ktory wywaolal
    {
        return new Transform3D(this);
    }
    
    public float getAngleX()  // zwraca kat miedzy osia x w radianach
    {
        return (float) Math.atan2(sinAngleX, cosAngleX);
    }
    
    public float getAngleY() // zwraca kat miedzy osia y w radianach
    {
        return (float) Math.atan2(sinAngleY, cosAngleY);
    }
    
    public float getAngleZ() // zwraca kat miedzy osia z w radianach
    {
        return (float) Math.atan2(sinAngleZ, cosAngleZ);
    }
    
    public void rotateAngleX(float angle) // wykonuje rotacje wokol osi x
    {
        if(angle != 0)
        {
            setAngleX(getAngleX() + angle);
        }
    }

    public void rotateAngleY(float angle) // wykonuje rotacja wokol osi y
    {
        if(angle != 0)
        {
            setAngleY(getAngleY() + angle);
        }
    }
    
    public void rotateAngleZ(float angle) // wykonuje rotacje wokol osi z
    {
        if(angle != 0)
        {
            setAngleZ(getAngleZ() + angle);
        }
    }
    
    public void rotateAngle(float angleX, float angleY, float angleZ) // wykonuje rotacje w kazdej osi
    {
        rotateAngleX(angleX);
        rotateAngleY(angleY);
        rotateAngleZ(angleZ);
    }

}


