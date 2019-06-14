  /** Java class "SIBAConst.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.siba.common;


/**
 * SIBAConst
 * Contiene diversas constantes globales de la aplicacion
 *
 */
public class SIBAConst {

    /**
     * Nombre del archivo de skin de fondo
     */
    public static final String BACKGROUND_SKIN = "skin/background.jpg";

    /**
     * Nombre del archivo de skin de fondo de celda
     */
    public static final String BACKGROUND_CELL_SKIN = "skin/background_cell.jpg";

    public static final String QTY_BACKGROUNDS = "3";
    public static final String BACKGROUND_CELL_SKIN_BASE = "skin/background_cell_";
    public static final String BACKGROUND_CELL_SKIN_TYPE = "jpg";
    /**
     * Nombre del archivo que contiene las preferencias de la aplicacion
     */
    public static final String APPLICATION_PREFERENCES = "preferences.ini";

    /**
     * Nombre que identifica cuando un canal continua su programacion
     */
    public static final String CONTINUATION_CHANNEL = "idem";

    /**
     * Tama–o por defecto de un canal grafico
     */
    public static final double CHANNEL_GRAPHIC_HEIGHT = 64.0;

	/**
     * Retraso de tiempo que demora entre el cambio de programacion de un dia
     */
    public static final int DELAY_BETWEEN_PROGRAMATION = 2000;

    /**
     * Retraso de tiempo de moviento de la grilla (Valocidad de la grilla)
     */
    public static final int DELAY_SCROLL = 50;

    /**
     * Agente de tiempo que determina cada 5 minutos el cambio de programacion de acuerdo a la hora actual.
     */
    public static final int TIME_AGENT_CHANGE_FOR_HOUR = 2000;//org 200000

    /**
     * Agente de cambio en el archivo de programacion, 
     * determina cada minuto si el archivo XML a sido cambiado
     */
    public static final int TIME_AGENT_ALERT_CHANGE = 15000;

    /**
     * Determina el numero de horas que se visualizaran en una programacion
     */
    public static final float SUM_HOUR = 2.0f;

    /**
	 * Representa la cordenada X y Y donde se empezara a graficar el texto.
	 * indice 0 = cordenada X
	 * indice 1 = cordenada Y
	 */
	public static final int[] G_JUSTIFICATION = {5,11};

} // end SIBAConst


