package javaXml;

public class Programa 
  {//Esta clase representa los programas que se emiten en un canal
   //=============================================================
   //Declaracion de atributos
   //=============================================================	
   private String nombre;
   private String sinopsis;
   private int id;
   //=============================================================
   //Metodos constructores
   //=============================================================
   public Programa(String nombre, String sinopsis, int id) 
     {super();
	  this.nombre = nombre;
	  this.sinopsis = sinopsis;
	  this.id = id;
	 }
   //=============================================================
   //Metodos Get and Set de los atributos
   //=============================================================
   public int getId() 
     {return id;
     }
   public void setId(int id) 
     {this.id = id;
     }
   public String getNombre() 
     {return nombre;
     }
   public void setNombre(String nombre) 
     {this.nombre = nombre;
     }
   public String getSinopsis() 
     {return sinopsis;
     }
   public void setSinopsis(String sinopsis) 
     {this.sinopsis = sinopsis;
     }
  }
