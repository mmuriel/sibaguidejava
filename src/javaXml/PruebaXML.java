package javaXml;

public class PruebaXML 
  {public static void main(String[] args) 
	  {//=====================================================
	   //Declaracion de variables globales al programa
	   //=====================================================
       
	   //String xmlFuente = "c:\\h_1_8170017701.xml";
	   //Programacion prog = new Programacion(xmlFuente);
	   //Thread thisThread = Thread.currentThread();
	   //Canal can = prog.getCanalInd(4);
	   //System.out.println("El canal es "+can.getNombre());
	   //String [] nombreProgramas = prog.getProgramacionByCanalInd(4);
	   //for (int i=0;i < nombreProgramas.length;i++)
	   //    {System.out.println("El programa en la hora "+i+" Es "+nombreProgramas[i]);
	   //    }
	  
	   //=====================================================
	   //Prueba del objeto control
	   String cadena ="grilla_grl.flg";
	   Control ctrl1 = new Control(cadena);
       while (!(ctrl1.alerta()))
	   {System.out.println(ctrl1.getTiempoBase());
	   }
	   System.out.println(ctrl1.getTiempoBase());
      }
  }
