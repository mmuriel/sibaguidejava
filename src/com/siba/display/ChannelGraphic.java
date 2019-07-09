/** Java class "SIBAToolKit.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.siba.display;

import com.siba.common.SIBAConst;
import com.siba.common.SIBAToolKit;

import javaXml.Canal;

import javax.swing.*;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;


/**
 * ChannelGraphic
 * Clase que grafica un canal y un numero determinado de horas de
 * programacion respectivas.
 */
public class ChannelGraphic {
    /**
     * Permite pintar el grafico
     */
    private Graphics2D g2;
    /**
     * Contiene la altura del grafico
     */
    private double height;
    /**
     * Contiene informacion y propiedades para un canal respectivo
     */
    private Canal channel;
    /**
     * Anchura del componente en pixeles que visualizara el grafico.
     */
    private double widthComponent;
    /**
     * posicion Y desde donde se empezara a graficar el canal
     */
    private double yPosition;

    private static final Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
    /**
     * Contiene el tipo de fuente
     */
    private Font fontType;
    private Font fontTypeChannel;
    private Font fontTypeNumberChannel;
    
    private int justXChannelName;
    private int justYChannelName;
    
    private int justXChannelNumber;
    private int justYChannelNumber;
    /**
     * Color de las letras de la grilla
     */
    private Color foreground;
    
    /**
     * Color de letra de los canales
     */
    private Color foregroundCanales;
    
    /**
     * Color de letra de los numeros canales
     */
    private Color foregroundNumberCanales;
    
    /**
     * Color de las lineas
     */
    private Color lineColor;
    /**
     * Color de fondo de la imagen
     */
    private Color backgroundIni;
    private Color backgroundLast;
    
    
    /**
     * Cuando se van a usar multiples backgrounds
     * este indice indica cual background utilizar
     */
    private int indiceMultiBackground = 0;
    /**
     * Buffer de imagen para optimizar los graficos
     */
    private BufferedImage bufferedImage;
    /**
     * Constante que se multiplica por el alto del rectangulo que se grafica cuando un canal ya es visto o continua
     * su programacion
     */
    private static final double MULT_RECTANGLE = 1;
    /**
     * Ruta donde se encuentran los archivos de la aplicacion
     */
    private String pathFiles;
    
    private String scrollBorders;
    
    
    

    /**
     * Contructor que referencia un Grafico 2D un canal a graficar
     *
     * @param g2      Objecto Graphics donde se graficara el canal y su programacion
     * @param channel canal y su programacion
     */
    public ChannelGraphic(Graphics2D g2, Canal channel) throws IOException {
        this.g2 = g2;
        this.channel = channel;
        initPreferences();
    } // end ChannelGraphic

    /**
     * Carga las preferencias de la aplicacion
     *
     * @throws IOException
     */
    private void initPreferences() throws IOException {
        // carga las preferencias de la aplicacion
        String fontNameProperty = SIBAToolKit.applicationPreference("FONT_NAME");
        String fontSizeProperty = SIBAToolKit.applicationPreference("FONT_SIZE");
        String fontStyleProperty = SIBAToolKit.applicationPreference("FONT_STYLE");
        String foregroundProperty = SIBAToolKit.applicationPreference("FOREGROUND");
        String foregroundPropertyCanales = SIBAToolKit.applicationPreference("FOREGROUND_CANALESHORAS");
        String lineColorProperty = SIBAToolKit.applicationPreference("LINE_COLOR");
        String foregroundCanalesNombre = SIBAToolKit.applicationPreference("FOREGROUND_NAME_CHANNEL");
        String justifyXNameChannel = SIBAToolKit.applicationPreference("JUSTIFICACION_X_NAME_CHANNEL");
        String justifyYNameChannel = SIBAToolKit.applicationPreference("JUSTIFICACION_Y_NAME_CHANNEL");
        
        String backGroundPropertyIni = SIBAToolKit.applicationPreference("BACKGROUND_INI");
        String backGroundPropertyLast = SIBAToolKit.applicationPreference("BACKGROUND_LAST");
        
        String fontNumberChannel = SIBAToolKit.applicationPreference("FONT_NUMBER_CHANNEL");
        String fontSizeNumberChannel = SIBAToolKit.applicationPreference("FONT_SIZE_NUMBER_CHANNEL");
        String fontColorNumberChannel = SIBAToolKit.applicationPreference("FOREGROUND_NUMBER_CHANNEL");
        String justifyXNumberChannel = SIBAToolKit.applicationPreference("JUSTIFICACION_X_NUMBER_CHANNEL");
        String justifyYNumberChannel = SIBAToolKit.applicationPreference("JUSTIFICACION_Y_NUMBER_CHANNEL");
        
        
        this.scrollBorders = SIBAToolKit.applicationPreference("SHOW_BORDER_SCROLL");
        
        
        //System.out.println(SIBAToolKit.applicationPreference("FOREGROUND_NAME_CHANNEL"));
        //System.out.println(SIBAToolKit.applicationPreference("OPERATOR_CODE"));
        
        
        pathFiles = SIBAToolKit.applicationPreference("PATH_FILES");
        foreground = new Color(Integer.parseInt(foregroundProperty));
        //foregroundCanales = new Color(Integer.parseInt(foregroundPropertyCanales));
        //foregroundCanales = new Color(256);
        foregroundCanales = new Color(Integer.parseInt(foregroundCanalesNombre));
        foregroundNumberCanales = new Color(Integer.parseInt(fontColorNumberChannel));
        //System.out.println(foregroundCanales.toString());
        lineColor = new Color(Integer.parseInt(lineColorProperty));
        fontType = new Font(fontNameProperty, Font.PLAIN, Integer.parseInt(fontSizeProperty));
        fontTypeChannel = new Font(fontNameProperty, Font.BOLD, Integer.parseInt(fontSizeProperty));
        fontTypeNumberChannel = new Font(fontNumberChannel, Font.BOLD, Integer.parseInt(fontSizeNumberChannel));
        backgroundIni = new Color(Integer.parseInt(backGroundPropertyIni));
        backgroundLast = new Color(Integer.parseInt(backGroundPropertyLast));
        
        
        justXChannelName = Integer.parseInt(justifyXNameChannel);
        justYChannelName = Integer.parseInt(justifyYNameChannel);
        
        justXChannelNumber = Integer.parseInt(justifyXNumberChannel);
        justYChannelNumber = Integer.parseInt(justifyYNumberChannel);
    }


    /**
     * Grafica la programacion de un canal determinado en el intervalo de horas
     * definidas, pero define el indice del background, se debe utilizar cuando
     * se ha mas de una imagen como fondo de los canales, en caso de que se
     * defina una sola imagen de fondo (igual para todos los canales) se debe usar
     * el método paintChannel(float initialHour, float endHour), ej:
     * </p>
     * De las 15:00 a las 16:30 es equivalente a: 15.0 a las 16.5.
     * <p/>
     * De las 10:45 a las 12:15 es equivalente a: 10.75 a las 16.25.
     *
     * @param g2          grafico 2D
     * @param initialHour hora inicial de la programacion a graficar
     * @param endHour     hora final de la programacion a graficar
     */
    public void paintChannel(int ind, float initialHour, float endHour) throws IOException {
        this.indiceMultiBackground = ind;
        paintChannel(initialHour, endHour);
    }
    
    
    /**
     * Grafica la programacion de un canal determinado en el intervalo de horas
     * definidas, ej:
     * </p>
     * De las 15:00 a las 16:30 es equivalente a: 15.0 a las 16.5.
     * <p/>
     * De las 10:45 a las 12:15 es equivalente a: 10.75 a las 16.25.
     *
     * @param g2          grafico 2D
     * @param initialHour hora inicial de la programacion a graficar
     * @param endHour     hora final de la programacion a graficar
     */
    public void paintChannel(Graphics2D g2, float initialHour, float endHour) throws IOException {
        this.g2 = g2;
        paintChannel(initialHour, endHour);
    }
    

    /**
     * Grafica la programacion de un canal determinado en el intervalo de horas
     * definidas, ej:
     * </p>
     * De las 15:00 a las 16:30 es equivalente a: 15.0 a las 16.5.
     * <p/>
     * De las 10:45 a las 12:15 es equivalente a: 10.75 a las 16.25.
     *
     * @param initialHour hora inicial de la programacion a graficar
     * @param endHour     hora final de la programacion a graficar
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public void paintChannel(float initialHour, float endHour) throws NumberFormatException, IOException {
        double widthCell = getWidthCellForText(initialHour, endHour);
        System.out.println("El ancho de la celda es: "+widthCell);
        double xSkeep;
        float dash[] = {4.0f};
        // alta calidad para los graficos 2D
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        // si el arhivo perteneciente al fondo de la imagen de celda no existe, se pinta de degradado
        if (this.indiceMultiBackground==0)
        {
        	//Pinta el canal para un solo background	
        	System.out.println("El canal "+this.channel.getNombre()+" será pintado tradicionalmente.");
        	if (new File(pathFiles + SIBAConst.BACKGROUND_CELL_SKIN).exists()) {
        		//imagen de fondo
        		//AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.DST_IN, 0.5f);
        		//g2.setComposite(alpha);
        		g2.drawImage(new ImageIcon(pathFiles + SIBAConst.BACKGROUND_CELL_SKIN).getImage(), 0, 0, (int) widthComponent, (int) height, null);
        	} else {
        		// degradado de fondo
        		g2.setPaint(new GradientPaint(0, (float) height / 2, backgroundIni, (float) widthComponent, (float) height / 2, backgroundLast));
        		g2.fill(new Rectangle2D.Double(0.0, 0.0, getWidthComponent(), getHeight()));
        	}
        }	
        else if (this.indiceMultiBackground==-1){
        	
        	//Pinta el fondo del canal, dependiendo de la categoria del mismo
        	System.out.println("El canal "+this.channel.getNombre()+" Pintado por categoria");
        	try {
				if (new File(pathFiles + SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_BASE")+this.channel.getGenero()+"."+SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_TYPE")).exists()){
					//imagen de fondo
					//AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.DST_IN, 0.5f);
					//g2.setComposite(alpha);
					g2.drawImage(new ImageIcon(pathFiles + SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_BASE")+this.channel.getGenero()+"."+SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_TYPE")).getImage(), 0, 0, (int) widthComponent, (int) height, null);
				} else {
					// degradado de fondo
					//g2.setPaint(new GradientPaint(0, (float) height / 2, backgroundIni, (float) widthComponent, (float) height / 2, backgroundLast));
					//g2.fill(new Rectangle2D.Double(0.0, 0.0, getWidthComponent(), getHeight()));
					g2.drawImage(new ImageIcon(pathFiles + SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_BASE")+"0."+SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_TYPE")).getImage(), 0, 0, (int) widthComponent, (int) height, null);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        else	
        {
        	//Pinta el canal para mœltiples backgrounds
        	System.out.println("El canal "+this.channel.getNombre()+" NO será pintado tradicionalmente.");
        	//System.out.println("Ruta archivo de fondo: "+pathFiles + SIBAConst.BACKGROUND_CELL_SKIN_BASE+this.indiceMultiBackground+"."+SIBAConst.BACKGROUND_CELL_SKIN_TYPE);
        	//if (new File(pathFiles + SIBAConst.BACKGROUND_CELL_SKIN_BASE+this.indiceMultiBackground+"."+SIBAConst.BACKGROUND_CELL_SKIN_TYPE).exists())
        	//SIBAToolKit.applicationPreference("QTY_BACKGROUNDS")
        	try {
				if (new File(pathFiles + SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_BASE")+this.indiceMultiBackground+"."+SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_TYPE")).exists()){
					//imagen de fondo
					//AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.DST_IN, 0.5f);
					//g2.setComposite(alpha);
					g2.drawImage(new ImageIcon(pathFiles + SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_BASE")+this.indiceMultiBackground+"."+SIBAToolKit.applicationPreference("BACKGROUND_CELL_SKIN_TYPE")).getImage(), 0, 0, (int) widthComponent, (int) height, null);
				} else {
					// degradado de fondo
					g2.setPaint(new GradientPaint(0, (float) height / 2, backgroundIni, (float) widthComponent, (float) height / 2, backgroundLast));
					g2.fill(new Rectangle2D.Double(0.0, 0.0, getWidthComponent(), getHeight()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        
        

        
        // el nombre del canal ocupa tres celdas
        xSkeep = widthCell * 3; 
        
        /*
        String fontNumberChannel = SIBAToolKit.applicationPreference("FONT_NUMBER_CHANNEL");
        String fontSizeNumberChannel = SIBAToolKit.applicationPreference("FONT_SIZE_NUMBER_CHANNEL");
        String fontColorNumberChannel = SIBAToolKit.applicationPreference("FOREGROUND_NUMBER_CHANNEL");
        */
        
        
        // se modifican las propiedades para el texto
        g2.setColor(foregroundNumberCanales);
        // grafica el nœmero del canal
        int channelNum = Integer.parseInt(channel.getNumeroSintoniza());
        double chnNumberFloat = (double)(channelNum);
        int chnNumberDecimales = (int)(chnNumberFloat % 100);
        chnNumberFloat = chnNumberFloat / 100;
        System.out.println(chnNumberFloat);
        System.out.println(chnNumberDecimales);
        //String chnNumberToPaint = Float.toString(chnNumberFloat);
        String chnNumberToPaint;
        
        if (chnNumberDecimales == 0){
        	chnNumberToPaint = ((int)(chnNumberFloat))+"";
        }
        else{
        	chnNumberToPaint = Double.toString(chnNumberFloat);
        }
        
        System.out.println(chnNumberToPaint);
        //System.out.println(chnNumberDouble);
        //if (channelNum <= 9){
        if (chnNumberToPaint.length() == 1){
        	//fitText(channel.getNumeroSintoniza(), g2,
            //    (float) (47 - (justXChannelNumber * 2)), (float)(justXChannelNumber * 2),(float) (justYChannelNumber), fontTypeNumberChannel);
        	System.out.println("Canal al tamaño grande");
        	fitText(chnNumberToPaint, g2,(float) (70 - (justXChannelNumber * 1.5)), 
        			(float)(justXChannelNumber * 1.5),(float) (justYChannelNumber), fontTypeNumberChannel);
        }	
        else if (chnNumberToPaint.length() == 2) {
        	
        	System.out.println("Canal al tamaño medio");
        	fitText(chnNumberToPaint, g2,
                    (float) (70 - (justXChannelNumber)), (float) (justXChannelNumber),(float) (justYChannelNumber), fontTypeNumberChannel);
        }
        else if (chnNumberToPaint.length() == 3) {
        	
        	System.out.println("Canal al tamaño medio");
        	fontTypeNumberChannel = new Font(SIBAToolKit.applicationPreference("FONT_NUMBER_CHANNEL"), Font.BOLD, (Integer.parseInt(SIBAToolKit.applicationPreference("FONT_SIZE_NUMBER_CHANNEL")) - 4 ));
        	fitText(chnNumberToPaint, g2,
                    (float) (70 - (justXChannelNumber)), (float) (justXChannelNumber),(float) (justYChannelNumber * 1.15), fontTypeNumberChannel);
        }
        else {
        	
        	System.out.println("Canal al menor tamaño");
        	fontTypeNumberChannel = new Font(SIBAToolKit.applicationPreference("FONT_NUMBER_CHANNEL"), Font.BOLD, (Integer.parseInt(SIBAToolKit.applicationPreference("FONT_SIZE_NUMBER_CHANNEL")) - 8 ));
        	fitText(chnNumberToPaint, g2,
                    (float) (70 - (justXChannelNumber * 0.70)), (float) (justXChannelNumber * 0.65),(float) (justYChannelNumber * 1.20), fontTypeNumberChannel);
        	
        }
        // se modifican las propiedades para el texto
        g2.setColor(foregroundCanales);
        // grafica el nombre del canal
        //float drawPosY = (float) ((SIBAConst.G_JUSTIFICATION[1] / 2));
        
        FontMetrics fm = g2.getFontMetrics();
        int strWidth = fm.stringWidth(channel.getNombre());
        System.out.println("El ancho de la cadena "+channel.getNombre()+" es: "+strWidth);
        if (strWidth <= 120){
        	
        	System.out.println("Pintando para el canal "+channel.getNombre()+" con ajuste x4 en la vertical ");
        	fitText(channel.getNombre(), g2,
                (float) (xSkeep - (justXChannelName / 2 ) - 70), 70, (float) ((justYChannelName * 4)), fontTypeChannel);
        }	
        else{
        	
        	System.out.println("Pintando para el canal "+channel.getNombre()+" sin ajsute en la vertical");
        	fitText(channel.getNombre(), g2,
                (float) (xSkeep - (justXChannelName / 2 ) - 70), 70, (float) ((justYChannelName)), fontTypeChannel);
        }
        // grafica las lineas de separacion
        //System.out.println(" El Color del scroll es: "+this.scrollBorders);
        if (this.scrollBorders.equals("true"))
		{g2.setColor(lineColor);
		 g2.draw(new Line2D.Double(xSkeep, 0.0, xSkeep, getHeight()));
		 g2.draw(new Line2D.Double(0, 0.0, getWidthComponent(), 0.0));
		 g2.draw(new Line2D.Double(0, getHeight(), getWidthComponent(), getHeight()));
		} 
        
        
        // se iteran los programas para ser graficados
        for (float countQuarters = initialHour; countQuarters <= endHour;) 
            {// Recupera el nombre del programa en el horario dado como paratemtro
             //String programName = getChannelByHour(countQuarters - 0.25f);
             String programName = getChannelByHour(countQuarters);
             // Recupera el ancho maximo en pixeles que ocupara el programa dado
             // en la hora actual.
             float maxWidth = (float) (widthCell) * countCellByProgram(countQuarters);
             
             if (isProgramContinue(countQuarters,endHour)) 
                {// determina cuantas celdas se paso
                 maxWidth -= (float) ((maxWidth + xSkeep) - getWidthComponent());
                 //maxWidth = (maxWidth == 0) ? maxWidth + 1 : maxWidth;
                }
             // grafica el nombre del programa
             g2.setColor(foreground);
             try {//Pinta el nombre del programa en la hora dada en el espacio adecuado.
            	 	
            	  float drawPosY = (float) ((SIBAConst.G_JUSTIFICATION[1] / 2));
            	  fitText(programName, g2, maxWidth - (SIBAConst.G_JUSTIFICATION[0] + 3), (float) xSkeep + SIBAConst.G_JUSTIFICATION[0], drawPosY, fontType);
            	  
                  g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                  if (isProgramContinue(countQuarters, endHour)) 
                     {drawContinuationProgram(g2, (maxWidth - ((SIBAConst.CHANNEL_GRAPHIC_HEIGHT / 4) * MULT_RECTANGLE)) + xSkeep);
                     }
                  if (isProgramAlreadyView(countQuarters, initialHour)) 
                     {drawAlreadyViewProgram(g2, xSkeep);
                     }
                  if (xSkeep >= getWidthComponent()) 
                     {xSkeep = getWidthComponent();
                     }
                  
                  // grafica la linea de separacion
                  xSkeep += widthCell * countCellByProgram(countQuarters);
                  g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 2.0f, dash, 0.0f));
                  g2.setColor(lineColor);
                  g2.draw(new Line2D.Double(xSkeep, 0.0, xSkeep, getHeight()));
                 }
             catch (Exception e) 
                 {//se captura el java.lang.ArrayIndexOutOfBoundsException arrojado por el metodo que ajusta el texto
                 }
             
             // aumenta cuartos de hora segun la cantidad de celdas que ocupa, 
             // cada celda es equivalente a un cuarto de hora
             countQuarters += 0.25f * countCellByProgram(countQuarters);
            }
    } // end paintChannel

    /**
     * Ajusta el texto en una area especificada
     *
     * @param text        texto a ajustar
     * @param g2          grafico 2D
     * @param formatWidth maximo tama–o horizontal que puede tener la frase, se especifica para 
     * 					  partir las cadenas apartir de este valor
     * @param drawPosX    posicion inicial X para pintar el texto
     * @param drawPosY    posicion inicial X para pintar el texto
     * @param Font type
     */
    public void fitText(String text, Graphics2D g2, float formatWidth, float drawPosX, float drawPosY, Font font) {
        map.put(TextAttribute.FONT, font);
        AttributedString vanGogh = new AttributedString(text, map);
        // numero de parrafos para el texto
        AttributedCharacterIterator paragraph = vanGogh.getIterator();
        // parrafo inicial
        int paragraphStart = paragraph.getBeginIndex();
        // parrafo final
        int paragraphEnd = paragraph.getEndIndex();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, new FontRenderContext(null, true, true));
        lineMeasurer.setPosition(paragraphStart);
        //float drawPosY = (float) ((SIBAConst.G_JUSTIFICATION[1] / 2));
        float drawPosXBreak;
        while (lineMeasurer.getPosition() < paragraphEnd) {
            // Retrieve next layout.
            TextLayout layout = lineMeasurer.nextLayout(formatWidth);
            // Move y-coordinate by the ascent of the layout.
            drawPosY += layout.getAscent();
            // Compute pen x position. If the paragraph is
            // right-to-left, we want to align the TextLayouts
            // to the right edge of the panel
            if (layout.isLeftToRight()) {
                drawPosXBreak = drawPosX;
            } else {
                drawPosXBreak = formatWidth - layout.getAdvance();
            }
            layout.draw(g2, drawPosXBreak, drawPosY);
            // Move y-coordinate in preparation for next layout.
            drawPosY += layout.getDescent() + layout.getLeading();
            // si al partir los caracteres sobrepasan el limite maximo de altura de visualizacion
        }
    }

    /**
     * Determina si el programa especificado en la hora enviada como parametro continua su programacion.
     *
     * @param initialHourProgram hora inicial del programa a determinar
     * @param endHour            hora final de visualizacion de la programacinon
     * @return true si el programa continua
     */
    public boolean isProgramContinue(float initialHourProgram, float endHour) {
        boolean programContinue = false;
        // cuntas celdas ocupa un programa
        int countCell = countCellByProgram(initialHourProgram);
        // cuenta cuantas celdas ocupa un programa en la hora especificada, cada celda equivale a 15 minutos
        // Entonces: se multiplica * 0.25 lo equivalente a 15 y se le suma la hora del programa. Si este valor
        // es mayor que la hora final el programa continua.
        // a la hora final se le quita el tiempo consumido por el ultimo programa
        if ((initialHourProgram + (countCell * 0.25)) > endHour) {
            programContinue = true;
        }
        return (programContinue);
    }

    /**
     * Determina si el programa especificado en la hora enviada como parametro f
     * ue visualizado en la anterior programacion.
     *
     * @param initialHourProgram hora inicial del programa a determinar
     * @param initHour           hora inicial de visualizacion de la programacinon
     * @return true si el fue visualizado en la anterior programacion
     */
    public boolean isProgramAlreadyView(float initialHourProgram, float initHour) {
        boolean programAlreadyView = false;
        // determina que no sea el primer canal del dia
        if (0 >= (initHour - 0.25)) {
            return (false);
        }
        // determina que no sea el primer canal de la programacion
        if (initialHourProgram > initHour) {
            return (false);
        }
        
        //Determina que el nombre del programa en la hora inicial 
        //sea distinto a 
        String[] programas = channel.getNombreProgramas();
        int ctrlInd = (int) (initHour / 0.25f);
        if (SIBAConst.CONTINUATION_CHANNEL.equals(programas[ctrlInd]))
           {return (true);
           }
        else
           {return (false);
           }
        
        //==============================================================
        //String channelBack = getChannelByHour(initialHourProgram - 0.75);
        //String channel = getChannelByHour(initialHourProgram - 0.25);
        //if (channelBack.equals(channel)) {
        //    programAlreadyView = true;
        //}
        //return (programAlreadyView);
    }

    /**
     * Retorna el nombre de un programa en la hora especificada, si en la hora 
     * especificada existe un "idem" (Se refiere a la continuacion de un programa) 
     * se retorna el nombre del programa anterior a ella.
     * <p/>
     * Si la hora es menor que cero. se retorna el primer prorama
     *
     * @param hour hora a buscar el programa
     * @return nombre del canal
     */
    private String getChannelByHour(double hour)
       {// Define una variable de control de cuartos de hora
    	float countQuarters = 0.0f;
    	
    	// Define el arreglo con los nombres de los programas
    	// para el dia en curso
    	
        String[] programas = channel.getNombreProgramas();
        String programSelected = null;
        
        for (String program : programas) 
            {if (!(SIBAConst.CONTINUATION_CHANNEL.equals(program))) 
                {programSelected = program;
                }
             // Si la sumatoria de los cuartos de hora es igual a 
             // la hora enviada como parametro
            
             
             countQuarters +=  0.25f;
             
             if (countQuarters > hour) 
                {return (programSelected);
                }
            }
        return null;  //To change body of created methods use File | Settings | File Templates.
       }

    public void drawContinuationProgram(Graphics2D g2, double posX) {
        Font currentFont = fontType;
        Font arrow = new Font(currentFont.getName(), currentFont.getStyle(), 9);
        double sideRectangle = SIBAConst.CHANNEL_GRAPHIC_HEIGHT / 4;
        g2.setFont(arrow);
        g2.setColor(lineColor);
        //g2.draw(new Rectangle.Double(posX, 0, sideRectangle * MULT_RECTANGLE, sideRectangle));
        g2.setColor(Color.BLACK);
        g2.drawString(">>", (int) (posX + 2), 9);
        g2.setFont(currentFont);
    }

    private void drawAlreadyViewProgram(Graphics2D g2, double posX) {
        Font currentFont = fontType;
        Font arrow = new Font(currentFont.getName(), currentFont.getStyle(), 9);
        double sideRectangle = SIBAConst.CHANNEL_GRAPHIC_HEIGHT / 4.5;
        g2.setFont(arrow);
        g2.setColor(lineColor);
        g2.getStroke();
        //g2.draw(new Rectangle.Double(posX, SIBAConst.CHANNEL_GRAPHIC_HEIGHT - sideRectangle, sideRectangle * MULT_RECTANGLE, sideRectangle));
        g2.setColor(Color.BLACK);
        //g2.drawString("<<", (int) (posX + 4), (int) (SIBAConst.CHANNEL_GRAPHIC_HEIGHT - 58));
        g2.drawString("<<", (int) (posX + 4), (int) (9));
        g2.setFont(currentFont);
    }

    /**
     * Retorna el ancho de celda en pixeles para el texto, se determina por el ancho del
     * componente a visualizar el grafico dividido por la cantidad de cuartos
     * de hora mas dos (Celda que visualizara el nombre del canal) existentes
     * entre la hora inicial y la hora final enviada como parametros a la
     * funcion.
     *
     * @param initialHour hora inicial
     * @param endHour     hora final
     */
    private double getWidthCellForText(float initialHour, float endHour) {
        // un cuarto de hora es representado como la 4 parte de 1, esto es equivalente a 0.25
    	float countQuarterHour = ((endHour - initialHour)+ 0.75f) / 0.25f;
        return getWidthComponent() / (countQuarterHour);
    	
    	
    	//Codigo Alex (Rolo)
    	//float countQuarterHour = (endHour - initialHour) / 0.25f;
        //return getWidthComponent() / (countQuarterHour + 1.25f);
    } // end getWidthCell

    /**
     * Cuenta la cantidad de celdas que ocupa un programa de television desde
     * la hora especificada como parametro.
     *
     * @param initialHour hora inicial a determinar la cantidad de celdas.
     * @return numero de celdas
     */
    private int countCellByProgram(double initialHour) {
        // contiene la sumatoria de cuartos de hora
        float countQuarters = 0.25f;
        String[] programas = channel.getNombreProgramas();
        // numero de celdas que ocupa un programa
        int countCell = 1;
        // El control del indice segun el valor de la hora dad
        int ctrlInd;
        
        ctrlInd = (int) (initialHour / countQuarters);
        try {
	        	for (int i=ctrlInd+1;SIBAConst.CONTINUATION_CHANNEL.equals(programas[i]);i++){
	        		countCell ++ ;
	            }
        }
        catch (Exception e){
        	
        }
        	
        return countCell;
    } // end countCellByProgram


    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Graphics2D getGraphics2D() {
        return g2;
    } // end getGraphics2D

    public void setGraphics2D(Graphics2D _g2) {
        g2 = _g2;
    } // end setGraphics2D

    public double getWidthComponent() {
        return widthComponent;
    } // end getWidthComponent

    public void setWidthComponent(double _widthComponent) {
        widthComponent = _widthComponent;
    } // end setWidthComponent

    public Canal getChannel() {
        return channel;
    } // end getChannel

    public void setChannel(Canal _channel) {
        channel = _channel;
    } // end setChannel

    public double getHeight() {
        return height;
    } // end getHeight

    public void setHeight(double _height) {
        height = _height;
    } // end setHeightViewerHours
    
    

    public int getIndiceMultiBackground() {
		return indiceMultiBackground;
	}

	public void setIndiceMultiBackground(int indiceMultiBackground) {
		this.indiceMultiBackground = indiceMultiBackground;
	}

	public static void main(String[] args) {
//		Programacion programation = new Programacion("h_7_8110094149.xml");
//		ChannelGraphic ch = new ChannelGraphic(null, programation.getCanalInd(0));
//		System.out.println(ch.getChannelByHour(0.24));
//        for (int i = 0; i < programation.getCantCanalesProg(); i++) {
//            System.out.println(programation.getCanalInd(i).getNombre()+", "+programation.getCanalInd(i).getId());
//        }
//		System.out.println(new Color (72, 249, 75).getRGB());
    }

} // end ChannelGraphic