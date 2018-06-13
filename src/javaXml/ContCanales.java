package javaXml;

public class ContCanales 
  {//Esta clase representa el contenedor de canales
   //totales que vengan en el documento xml.
   //==========================================================
   //Declaracion de atributos	
   private Canal [] canales;
   //==========================================================
   //Metodo constructor
   public ContCanales (int cantElements)
     {canales = new Canal [cantElements];
     }
   public ContCanales ()
     {canales = new Canal [120];
     }
   public ContCanales (Canal [] arrCan)
     {this.canales=arrCan;
     }
   //==========================================================
   //Metodos adicionales
   public Canal[] getArrContCanales() 
     {return canales;
     }
   public void setArrContCanales(Canal[] arrContCanales) 
     {this.canales = arrContCanales;
     }
   public void setContCanalesPosicion (int pos,Canal element)
     {this.canales[pos]=element;
     }
   public Canal getCanalInd (int ind)
     {return canales[ind]; 
     }
   public String [] getProgrmacionByCanalInd (int ind)
     {return canales[ind].getNombreProgramas ();
     }
   public int getCantCanales ()
     {return canales.length; 
     }
  }
