package javaXml;

public class Canal 
  {//Esta clase representa directamente los canales
   //existentes en un sistema de TV Guia
   //==================================================
   private String nombre;
   private String numeroSintoniza;
   private String logoTipo;
   private int id;
   private String genero;
   private ContProgramas programacion;
   //=================================================
   //Metodos constructores
   //=================================================
   public Canal (String nombre, String numeroSintoniza, String logoTipo, ContProgramas p, int id, String genero) 
     {super();
	  this.nombre = nombre;
	  this.numeroSintoniza = numeroSintoniza;
	  this.logoTipo = logoTipo;
	  this.programacion=p;
	  this.id = id;
	  this.genero =genero;
	 }
   //=================================================
   //Metodos Get and Set
   //=================================================
   public int getId() 
     {return id;
     }
   public void setId(int id) 
     {this.id = id;
     }
   public String getLogoTipo() 
     {return logoTipo;
     }
   public void setLogoTipo(String logoTipo) 
     {this.logoTipo = logoTipo;
     }
   public String getNombre() 
     {return nombre;
     }
   public void setNombre(String nombre) 
     {this.nombre = nombre;
     }
   public String getNumeroSintoniza() 
     {return numeroSintoniza;
     }
   public void setNumeroSintoniza(String numeroSintoniza) 
     {this.numeroSintoniza = numeroSintoniza;
     }
   public String getGenero() 
     {return genero;
     }
   public void setGenero(String genero) 
     {this.genero = genero;
     }
   public String [] getNombreProgramas ()
    {return programacion.getNombreProgramas();   
    }
  }
