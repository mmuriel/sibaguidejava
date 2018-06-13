package javaXml;

public class ContProgramas 
  {//
   private Programa [] programas;
   //=======================================================
   //Metodos constructores
   //=======================================================
   public ContProgramas() 
     {programas = new Programa[104];
	 }
   
   public ContProgramas(int cant) 
     {programas = new Programa[cant];
	 }
   
   public ContProgramas(Programa [] arrProgramas) 
     {programas = arrProgramas;
	 }
   //=======================================================
   //Fin metodos constructores.
   //=======================================================
   public void setProgramas (Programa [] arrProgramas)
     {this.programas=arrProgramas;
     }
   
   public Programa getProgramaByIndex (int ind)
     {try
        {return programas[ind];
        }
      catch (java.lang.Exception e)
        {return null;
        }
     }
   
   public void setProgramaByIndex (int ind,Programa p)
     {try
        {programas[ind]= p;
        }
      catch (java.lang.Exception e)
        {
        }
     }
   
   
   public String [] getNombreProgramas ()
   {String [] arrNombres = new String[programas.length];
	for (int i=0;i < this.programas.length;i++)
        {arrNombres[i]=programas[i].getNombre();	   
        };
    return arrNombres;    
   }
  }
