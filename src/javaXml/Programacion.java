package javaXml;

import org.w3c.dom.*;

public class Programacion 
  {//================================================
   //Atributos de la clase.
   //================================================
   private xml1 documentXml;
   private ContCanales canales;
   //================================================
   //Metodos constructores
   //================================================
   public Programacion(String fuenteXml) 
     {documentXml = new xml1 (fuenteXml);
      //=============================================
      //Declaracion de variables locales al metodo.
      //=============================================
      Canal [] arrCanales;
      //=============================================
      //1. Carga el documento XML
      Node nodo1 = documentXml.document.getFirstChild();
      //=============================================
      //2. Verifica que el nodo raiz tenga hijos 
      NodeList listCanales = nodo1.getChildNodes();
      if (listCanales.getLength()>0)
         {//=========================================
          //Declara la variable de control de nodos
          //validos como canales y no nodos de texto.
          //=========================================
          int ctrlCant =0;
          //==============================================
          //Verifica la cantidad de nodos validos que 
          //existen en el documento.
          //==============================================
          for (int i=0;i < listCanales.getLength();i++)
              {if (listCanales.item(i).getNodeName()!= "#text")
                  {ctrlCant++;
                  }
              }
   	      //==============================================
   	      //Inicializa el arreglo de canales
   	      arrCanales = new Canal[ctrlCant];
   	      //==============================================
   	      //Ingresa cada uno de los elementos del
   	      //arreglo de canales.
   	      ctrlCant=0;//Reiniciamos la variable de control de nodos canales
   	      for (int i=0;i < listCanales.getLength();i++)//Da tantas vueltas como canales existan
              {if (listCanales.item(i).getNodeName()!= "#text")
                  {Node nodoTmpCanal = listCanales.item(i);//Toma un nodo de canal
                   Programa [] arrTmpPrograma;
                   //========================================================
                   //Toma la informacion de los nodos hijos (Los programas)
                   //========================================================
                   NodeList listProgramas = nodoTmpCanal.getChildNodes();
                   if (listProgramas.getLength()>0)
     	              {int ctrlProgramas = 0;//Declara la variable de control de nodos de texto.
     	               //==============================================
     	               //Verifica la cantidad de nodos de programas
     	               //(no de texto) existen como hijos del
     	               //nodo canal actual.
     	               //==============================================
                       for (int i2=0;i2 < listProgramas.getLength();i2++)//Da tantas vueltas como programas tenga el canal
     	                   {if (listProgramas.item(i2).getNodeName()!= "#text")
     	                       {ctrlProgramas++;
     	                       }
     	                   }
                       //==============================================
                       //Genera el arreglo de programas temporal para
                       //crear el objeto canal.
                       //==============================================
                       arrTmpPrograma=new Programa[ctrlProgramas];
                       ctrlProgramas = 0;
                       //==============================================
                       for (int i2=0;i2 < listProgramas.getLength();i2++)
 	                       {if (listProgramas.item(i2).getNodeName()!= "#text")
 	                           {Node programaTmp = listProgramas.item(i2);
 	                            NamedNodeMap mapaNodo2 = programaTmp.getAttributes();
 	                            Node nodoNombrePrograma =mapaNodo2.getNamedItem("value");
 	                            Programa programa = new Programa(nodoNombrePrograma.getNodeValue(),"",ctrlProgramas);
 	                    	    arrTmpPrograma[ctrlProgramas]=programa;
 	                    	    ctrlProgramas++;
 	                           }
 	                       }
                       //==============================================
                       //Crea el objeto contenedor de programas
                       //para luego generar el objeto canal, que asu vez
                       //se ingresara a un objeto contenedor de canales.
                       //==============================================
                       ContProgramas contProg1 = new ContProgramas (arrTmpPrograma);
                       //==============================================
                       //Genera la informacion faltante sobre el canal 
                       //y crea el objeto canal
                       NamedNodeMap mapaNodo1 = nodoTmpCanal.getAttributes();
                       Node nodoNombreCanal = mapaNodo1.getNamedItem("name");
                       Node nodoSintonizaCanal = mapaNodo1.getNamedItem("cadena");
                       Node nodoGeneroCanal = mapaNodo1.getNamedItem("genero");
                       Canal canal1 = new Canal ((String)nodoNombreCanal.getNodeValue(),(String)nodoSintonizaCanal.getNodeValue(),(String)"",contProg1,ctrlCant,nodoGeneroCanal.getNodeValue());
                       //==============================================
                       //Ingresa el nuevo canal al arreglo de canales.
                       //==============================================
                       arrCanales[ctrlCant]= canal1;
                       ctrlCant++;//Aumentamos el controlador de elementos canales
     	              }
                   else
                      {//==============================================
                       //Si el canal no reporta programacion, se genera 
                       //un objeto nulo en ese indice del arreglo-canales
                       //==============================================
                       arrCanales[ctrlCant]=null;
                       ctrlCant++;//Aumentamos el controlador de elementos canales
                      }
                  }
              }
   	      //==============================================
   	      //Genera el nuevo Contenedor de canales
   	      //==============================================
   	      canales = new ContCanales (arrCanales);
         }
      else
         {canales=null;
         }
      documentXml=null;
     }
   //=====================================================
   //Metodos adicionales.
   public Canal getCanalInd (int ind)
     {return canales.getCanalInd (ind);
     }
   
   public String [] getProgramacionByCanalInd (int ind)
     {return canales.getProgrmacionByCanalInd(ind);
     }
   
   public int getCantCanalesProg ()
     {return canales.getCantCanales(); 
     }
   //=====================================================
  }
