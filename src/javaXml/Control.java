package javaXml;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Control {//=======================================
    // Attributos
    //=======================================
    private Date tiempoBase;
    private String rutaFuenteData;

    //=====================================
    // Metodos Constructores
    //=====================================
    public Control(String cadena) {//Crea un objeto de tipo xml1 temporalmente
        //para cargar los datos, luego lo desecha.
        //La fuente de los datos (el archivo xml) esta determinada
        //por la cadena que se pasa como parametro
        //==================================

        //Declaracion de atributos locales utiles
        //SimpleDateFormat
        this.rutaFuenteData = cadena;
        String vlrInicial;
        //==================================
        //Carga la informacion del archivo
        //XML fuente de los datos.
        try {
            xml1 xmlData = new xml1(this.rutaFuenteData);
            Node nodo1 = xmlData.document.getFirstChild();
            NamedNodeMap nodo2 = nodo1.getAttributes();
            vlrInicial = nodo2.item(0).getNodeValue();
            //System.out.print(rutaFuenteData);
        }
        catch (Exception e) {
            vlrInicial = "2005-01-01-01-01-01";
        }
        //===================================
        //Carga los datos al objeto date
        //===================================
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            tiempoBase = df.parse(vlrInicial);
        }
        catch (Exception e) {
            tiempoBase = new Date();
        }
    }

    //=====================================
    // Metodos regulares
    //=====================================

    //=====================================
    //Este metodo carga la informacion de la 
    //fuente de datos en el instante que es
    //llamado y la compara con la informacion
    //actual almacenada en tiempoBase, final-
    //mente actualiza el valor de tiempoBase
    //para que no 'alerte' en el proximo 
    //llamada por la misma alerta.
    public Boolean alerta() {
    	//==================================
        //Variables de ambito local, utiles
        //para ejecutar el metodo.
        //==================================
        String vlrAlerta;
        Date tmp;
        //==================================
        //Carga la informacion para comparar la alerta
        try {
            xml1 xmlData2 = new xml1(rutaFuenteData);
            Node nodo1 = xmlData2.document.getFirstChild();
            NamedNodeMap nodo2 = nodo1.getAttributes();
            vlrAlerta = nodo2.item(0).getNodeValue();
            //System.out.print(rutaFuenteData);
        }
        catch (Exception e) {
            vlrAlerta = "2004-01-01-01-01-01";
        }

        SimpleDateFormat dfNew;
        dfNew = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            tmp = dfNew.parse(vlrAlerta);
        }
        catch (Exception e) {
            tmp = new Date();
        }
        //==============================================
        //Compara el valor actual con el nuevo valor
        //==============================================
        if (!(tiempoBase.equals(tmp))) {
            tiempoBase = tmp;
            return true;
        } else {
            return false;
        }
    }

    public String getTiempoBase() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(tiempoBase);
    }

}