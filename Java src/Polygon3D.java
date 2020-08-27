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
public class Polygon3D 
{
    // wektory pomocnicze
    private static Vector3Ds temp1 = new Vector3Ds(); 
    private static Vector3Ds temp2 = new Vector3Ds(); 
    
    private int numVertices; // ilosc wierzcholkow
    private Vector3Ds[] v;  // tablica przechowujaca konkretne wierzcholki jako vector3D(x,y,z); 
    private Vector3Ds normal; // wektor prostopadły ( wyznacza kierunek ) 
    
    public Polygon3D()  // tworzy pusty wielokat roboczy
    {
        numVertices = 0;  
        v = new Vector3Ds[0];
        normal = new Vector3Ds(); 
    }
    
    public Polygon3D(Vector3Ds v0, Vector3Ds v1, Vector3Ds v2)
    {               // tworzenie nowego wektora, wywoluje konstruktor Polygon3D [ Vector3D[] )
        this(new Vector3Ds[] {v0,v1,v2});
    }
    
    public Polygon3D(Vector3Ds v0, Vector3Ds v1, Vector3Ds v2, Vector3Ds v3) 
    {               // tworzenie nowego wektora wierzcholkow, wywoluje konstruktor Polygon3D [ Vector3D[] )
        this(new Vector3Ds[] {v0,v1,v2,v3});
    }
    
    public Polygon3D(Vector3Ds[] vertices) // ustawia wierzcholki tak, jak podano w konstruktorze
    {
        numVertices = vertices.length;
        this.v = vertices;
        // calcNormal();
    }
    
    public void setTo(Polygon3D polygon) // ustawia wielokatowi parametry innego wielokata
    {
        this.numVertices = polygon.numVertices;
//        normal.setTo(polygon.normal);
        
        ensureCapacity(numVertices); // sprawdza, czy pomieszcza sie wierzcholki 
        for(int i = 0; i<numVertices; i++) // dopisuje wierzcholki do tablicy wierzcholkow
        {
            this.v[i].setTo(polygon.v[i]);
        }
    }
    
    public Vector3Ds calcNormal() // wylicza wektor normalny do dwóch wektorów rozpinających wielokąt
    {
        if(normal == null)
        {
            normal = new Vector3Ds();
        }
        temp1.setTo(v[2]);
        temp1.substractVector(v[1]);
        temp2.setTo(v[0]);
        temp2.substractVector(v[1]);
        normal.setToCrossProduct(temp1, temp2);
        normal.normalizeVector();
        
        return normal;
    }

    public Vector3Ds getNormal() 
    {
        return normal;
    }
    
    public void setNormal(Vector3Ds n)
    {
        if(normal == null)
        {
            this.normal = new Vector3Ds(n);
        }
        else
        {
            this.normal.setTo(n);
        }
    }

    private void ensureCapacity(int length) // upewnia sie, czy aktualna pojemność tablicy vectorów3D jest 
    {                       // co najmniej taka jak ilosc podana jako argument, jak nie to rozszerza do niej
        if(v.length < length)
        {
            Vector3Ds[] newV = new Vector3Ds[length];
            System.arraycopy(v, 0, newV, 0, v.length);
            for(int i = v.length; i < newV.length ; i++)
            {
                newV[i] = new Vector3Ds();
            }
            
            this.v = newV ;
        }
    } 

    public int getNumVertices() // pobiera liczbe wierzcholkow wielokata
    {
        return numVertices;
    }

    public Vector3Ds getVertex(int index) // pobiera konkretny wierzcholek ( wektor )
    {
        return v[index];
    }
    
    public void project(ViewWindow view) // rzutowanie wektorów składowych wielokąta na okno obrazu
    {
        for (int i=0; i<numVertices; i++ )
        {
            view.project(v[i]);
        }
    }
    
    public boolean isFacing(Vector3Ds u)
    {
        temp1.setTo(u);
        temp1.substractVector(v[0]);
        
        return (normal.getDotProduct(temp1) >= 0);
    }
    
    public void add(Transform3D xform)
    {
        addRotation(xform);
        addVectors(xform.getLocation());
    }
    
    public void substract(Transform3D xform)
    {
        substractVector(xform.getLocation());
        SubRotation(xform);
    }
    
    private void addRotation(Transform3D xform) 
    {
        rotateX(xform.getCosAngleX(), xform.getSinAngleX());
        rotateY(xform.getCosAngleY(), xform.getSinAngleY());
        rotateZ(xform.getCosAngleZ(), xform.getSinAngleZ());
    }
    
    private void SubRotation(Transform3D xform) // jakby nie dzialalo to po jednym punkcie transformowac
    { // dac tutaj for ( i =0 ; i< v. lenth ; i++ ) i dodac 3 pole(int i) do rotateX,Y,Z przekazywać tam "i"
        rotateX(xform.getCosAngleX(), -xform.getSinAngleX()); // i wewnatrz modyfikowac po koeli v[i]
        rotateY(xform.getCosAngleY(), -xform.getSinAngleY()); // rotateX,Y,Z (v[0], pozniej v[1] itd... )
        rotateZ(xform.getCosAngleZ(), -xform.getSinAngleZ());
    }
    
    private void rotateX(float cosAngle, float sinAngle) // + int licznik
    {
        for(int i = 0; i < v.length; i++) //bez for, tylko zapodany v[licznik]
        {
        float newY = v[i].y*cosAngle - v[i].z*sinAngle;
        float newZ = v[i].y*sinAngle + v[i].z*cosAngle;
        v[i].y = newY;
        v[i].z = newZ;
        }
    }
    
     private void rotateY(float cosAngle, float sinAngle) 
    {
        for(int i = 0 ; i < v.length ; i++)
        {
        float newX = v[i].z*sinAngle + v[i].x*cosAngle;
        float newZ = v[i].z*cosAngle - v[i].x*sinAngle;
        v[i].x = newX;
        v[i].z = newZ;
        }
    }
    
    private void rotateZ(float cosAngle, float sinAngle) 
    {
        for(int i = 0 ; i < v.length ; i++)
        {
        float newX = v[i].x*cosAngle - v[i].y*sinAngle;
        float newY = v[i].x*sinAngle + v[i].y*cosAngle;
        v[i].y = newY;
        v[i].x = newX;
        }
    }

    private void addVectors(Vector3Ds location) 
    {
        for(int i = 0; i < v.length ; i++)
        {
            v[i].x = v[i].x + location.x ;
            v[i].y = v[i].y + location.y ;
            v[i].z = v[i].z + location.z ;
        }
    }

    private void substractVector(Vector3Ds location) 
    {
        for(int i = 0; i < v.length ; i++)
        {
            v[i].x = v[i].x - location.x ;
            v[i].y = v[i].y - location.y ;
            v[i].z = v[i].z - location.z ;
        }
    }
    
}
