package com.siba.display;

import com.siba.common.SIBAToolKit;
import com.siba.common.SIBAConst;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.util.Calendar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * ViewerHoursControl
 * Esta clase permite graficar el nombre del canal y las horas especificadas que se visualizaran en una programacion
 * especifica.
 */
public class ViewerHoursControl extends JLabel {

    /**
     * Permite pintar el grafico
     */
    private Graphics2D g2;
    private Color foreground;
    private Color lineColor;
    private Font fontType;
    private Color background;
    
    /**
     * Controla la alerta para el tiempo
     */
    
    private int ctrlHours = 0;

    /**
     * Contructor por defecto
     */
    public ViewerHoursControl() throws IOException {
        initPreferences();
    }

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
        String foregroundProperty = SIBAToolKit.applicationPreference("FOREGROUND_CANALESHORAS");
        String lineColorProperty = SIBAToolKit.applicationPreference("LINE_COLOR");
        String backGroundProperty = SIBAToolKit.applicationPreference("BACKGROUND_HOURS");
        fontType = new Font(fontNameProperty,Font.PLAIN, Integer.parseInt(fontSizeProperty));

        /**
         * Instancia los objetos tipo color
         * para aplicarlos posteriormente a las lineas separadoras,
         * el fondo de bloque de horas y los caracteres
         */
        
        foreground = new Color(Integer.parseInt(foregroundProperty));
        lineColor = new Color(Integer.parseInt(lineColorProperty));
        background = new Color(Integer.parseInt(backGroundProperty));
    }

    /**
     * Obtiene la hora actual en formato 24 horas, los minutos son 
     * redondeados a 5 es decir: si son las 2:21 PM la hora es 
     * redondeada a 2:20 PM.
     * <p/>
     * Tiene presision de 30 minutos.
     *
     * @return retorna la hora con el siguiente formato: 
     * si son las 2:31 AM = 2.50, 1:30 PM = 13.50
     */
    public static float getStartHour() {
        Calendar calendar = Calendar.getInstance();
        int minutes = calendar.get(Calendar.MINUTE);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (minutes >= 0 && minutes < 30) {
            return (hourOfDay);
        } else {
            return (0.5f + hourOfDay);
        }
    }

    /**
     * Grafica el visor de horas en el intervalo de horas
     * definidas, ej:
     * </p>
     * De las 15:00 a las 16:30 es equivalente a: 15.0 a las 16.5.
     * <p/>
     * De las 10:45 a las 12:15 es equivalente a: 10.75 a las 16.25.
     *
     * @param g2          grafico 2D
     * @param initialHour hora inicial a graficar
     * @param endHour     hora final a graficar
     */
    public void paintViewerHours(Graphics2D g2, float initialHour, float endHour) {
        this.setG2(g2);
        paintViewerHours(initialHour, endHour);
    }

    /**
     * Grafica el visor de horas en el intervalo de horas
     * definidas, ej:
     * </p>
     * De las 15:00 a las 16:30 es equivalente a: 15.0 a las 16.5.
     * <p/>
     * De las 10:45 a las 12:15 es equivalente a: 10.75 a las 16.25.
     *
     * @param initialHour hora inicial a graficar
     * @param endHour     hora final a graficar
     */
    public void paintViewerHours(float initialHour, float endHour) {
        Calendar calendar = Calendar.getInstance();
        // redondea las horas a 30 minutos
        int minutes = calendar.get(Calendar.MINUTE);
        // si se esta entre este intervalo de minutos se modifica a 0 min de lo contrario a 30 min.
        if (minutes >= 0 && minutes < 30) {
            calendar.set(Calendar.MINUTE, 0);
        } else {
            calendar.set(Calendar.MINUTE, 30);
        }
        double widthCell = getWidthCellForText(initialHour, endHour);
        double xSkeep;
        // alta calidad para los graficos 2D
        getG2().setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        getG2().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        getG2().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        getG2().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        getG2().setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        getG2().setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        getG2().setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        getG2().setColor(background);

        getG2().fill(new Rectangle2D.Double(0.0, 0.0, getWidth(), getHeight()));
        // se modifican las propiedades para el texto
        getG2().setColor(foreground);
        getG2().setFont(fontType);
        // el nombre del canal ocupa tres celdas
        xSkeep = widthCell * 3.0;
        // grafica el nombre del canal
        getG2().drawString("Canales", SIBAConst.G_JUSTIFICATION[0]+43, 17);
        // grafica las lineas de separacion
        getG2().setColor(lineColor);
        getG2().draw(new Line2D.Double(xSkeep, 0.0, xSkeep, getHeight()));
        getG2().draw(new Line2D.Double(0, 0.0, getWidth(), 0.0));
        getG2().draw(new Line2D.Double(0, getHeight(), getWidth(), getHeight()));
        // se iteran los programas para ser graficados
        for (float countQuarters = initialHour; countQuarters <= endHour;) {
            getG2().setColor(lineColor);
            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            // grafica la linea de separacion
            getG2().draw(new Line2D.Double(xSkeep, 0.0, xSkeep, getHeight()));
            // grafica el texto representante a la hora
            try {
                // se formateara la fecha
                javax.swing.text.DateFormatter formatDate = new DateFormatter(new SimpleDateFormat("HH:mm"));
                // fecha formateada segun el patron especificado HH:mm
                String strFormat = formatDate.valueToString(calendar.getTime());
                getG2().setColor(foreground);//Define el color de la fuenta en las horas
                getG2().drawString(strFormat, (int) (xSkeep + SIBAConst.G_JUSTIFICATION[0]+28),17);
                // aumenta cuartos de hora segun la cantidad de celdas que ocupa, cada celda es equivalente a un
                // cuarto de hora
                xSkeep += widthCell * 2;
                countQuarters += 0.25f * 2;
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
            } catch (ParseException e) {
                //TODO: capturar
            }
        }
    }

    /**
     * Retorna el ancho de celda en pixeles para el texto, se determina por el ancho del
     * componente que contiene el grafico a visualizar (el propio elemnto viewerChannel, 
     * dividido por la cantidad de cuartos de hora mas tres (Celda que visualizara el nombre 
     * del canal) existentes entre la hora inicial y la hora final enviada como parametros
     * a la funcion + el espacio que se llevaria la zona donde van los nombres de los
     * canales.
     *
     * @param initialHour hora inicial
     * @param endHour     hora final
     */
    private double getWidthCellForText(float initialHour, float endHour) {
        // un cuarto de hora es representado como la 4 parte de 1, 
    	// esto es equivalente a 0.25
        float countQuarterHour = ((endHour - initialHour)+ 0.75f) / 0.25f;
        return getWidth() / (countQuarterHour);
    } // end getWidthCell

    /**
     * Pinta el componente
     *
     * @param g grafico perteneciente al componente
     */
    public void paint(Graphics g) {
        super.paint(g);    //To change body of overridden methods use File | Settings | File Templates.
        Graphics2D g2 = (Graphics2D) g;
        paintViewerHours(g2, 0.0f, SIBAConst.SUM_HOUR);
    }

    /**
     * Inicializa el agente de refresco de horas
     */
    public void startAgentTime() {
        Timer timer = new Timer(SIBAConst.TIME_AGENT_CHANGE_FOR_HOUR, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	Calendar c = Calendar.getInstance();
                int minutos = c.get(Calendar.MINUTE);
                int hora = c.get( (Calendar.HOUR_OF_DAY)); 
                hora = hora * 60;
                if (minutos >= 30 && minutos <= 59)
                   {hora +=30;	
                   }
                if (hora != ctrlHours)
                   {ctrlHours = hora;
                   repaint();
                   }
            }
        });
        timer.start();
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public static void main(String[] args) throws IOException {
        JFrame fr = new JFrame();
        ViewerHoursControl v = new ViewerHoursControl();
        v.startAgentTime();
        fr.setSize(645,60);
        fr.getContentPane().add(v, BorderLayout.CENTER);
        fr.setVisible(true);
        
    }

}
