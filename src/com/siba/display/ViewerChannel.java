/** Java class "SIBAToolKit.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.siba.display;

import com.siba.common.SIBAConst;
import com.siba.common.SIBAToolKit;
import javaXml.Programacion;
import javaXml.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ViewerChannel
 * Esta clase visualiza la programacion de un operador de Cable determinado, es cargada
 * de un arhivo XML.
 *
 */
public class ViewerChannel extends JLabel implements Runnable {

    /**
     * una coleccion de canales graficos
     */
    private List<ChannelGraphic> channelGraphics = new LinkedList<ChannelGraphic>(); // of type ChannelGraphic
    /**
     * contiene diversas funcionalidades de consulta de la programacion de un canal
     */
    public Programacion programation;
    /**
     * Determina si la imagen cambio
     */
    private boolean changedImage = true;
    /**
     * Codigo de operador
     */
    private String customerCode;
    /**
     * Determina cuando hay cambio de programacion de acuerdo al final del dia
     */
    private boolean alreadyChange = false;
    /**
     * Determina si se remueve la anterior programacion
     */
    private boolean removeLastProgramation;
    /**
     * Determina cuando se cambiara de horario de acuerdo a la hora
     */
    private boolean activatedScheduleChange = false;
    /**
     * Contiene el nombre del archivo referente al flag general
     */
    private String fileFlag;
    /**
     * Contiene el nombre del archivo referente al flag personal del operador
     */
    private String fileFlagPersonal;
    /**
     * Establece cuando un flag global de todos los operadores es cambiado
     */
    private Control controlFlagGeneral;
    /**
     * Establece cuando un flag de un operador es cambiado
     */
    
    private Control controlFlagPersonal;
    
    /**
     * Controla la alerta para el tiempo
     */
    
    private int ctrlHours = 0;
    
    /**
     * Bandera de control de cambio de la programacion
     * de un dia para otro.
     */
    
    private int ctrlDia1ToDia2 = 1; //Cuando vale cero no debe cambiar cuando vale 1 si!
    
    /**
     * Ruta donde se encuentran los archivos de la aplicacion
     */
    private String pathFiles;
    
    /**
     * Define la cantidad de backgrounds disponibles para pintar
     */
    private int qtyBackgounds;
    
    /**
     * Ctrl indice background activo
     */
    private int bkgActivo = 1;
    public ViewerChannel(String customerCode, String fileFlagGeneral, String fileFlagPersonal) throws IOException {
        this.customerCode = customerCode;
        Color testc = new Color (0,0,0);
        System.out.println(" El color ajustados es este: " + new Color (88,88,88).getRGB());
        System.out.println(" El color ajustados es este 2: " + testc.getRGB());
        System.out.println("1. File Flag General: " +fileFlagGeneral);
        System.out.println("2. File Flag Personal: " +fileFlagPersonal);
        // setea los nombres de los arhivos pertenecientes a las banderas
        this.setFileFlag(fileFlagGeneral);
        this.setFileFlagPersonal(fileFlagPersonal);
        // instancia e inicializa los controles de flags
        System.out.println("3. File Flag General: " +this.getFileFlag());
        System.out.println("4. File Flag Personal: " +this.getFileFlagPersonal());
        
        
        controlFlagGeneral = new Control(this.getFileFlag());
        //System.out.println(controlFlagGeneral.getTiempoBase());
        setControlFlagPersonal(new Control(this.getFileFlagPersonal()));
        pathFiles = SIBAToolKit.applicationPreference("PATH_FILES");
        //this.qtyBackgounds = Integer.parseInt(SIBAConst.QTY_BACKGROUNDS);
        this.qtyBackgounds = Integer.parseInt(SIBAToolKit.applicationPreference("QTY_BACKGROUNDS"));
        System.out.println("Cantidad de Backgrounds: "+this.qtyBackgounds);
    }

    /**
     * Retorna el nombre del archivo del dia actual con la siguiente notacion
     * h_dia_codigooperador.xml
     *
     * @return nombre del archivo xml que contiene la programacion del dia
     */
    private String getFileOftheDay() throws IOException {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SUNDAY == day) {
            day = Calendar.SATURDAY;
        } else if (Calendar.MONDAY <= day && Calendar.SATURDAY >= day) {
            day -= 1;
        }
        return (pathFiles + "h_" + day + "_" + getCustomerCode() + ".xml");
    }

    /**
     * Metodo que se llama inmediatamente despues de que el componente se pinta
     *
     * @param g objeto grafico del componente
     */
    public void update(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        try {
            // si existe el archivo de imagen perteneciente al fondo se grafica de lo contrario se pinta del
            // color segun las preferencias.
            if (new File(pathFiles + SIBAConst.BACKGROUND_SKIN).exists()) {
                g2.drawImage(new ImageIcon(pathFiles + SIBAConst.BACKGROUND_SKIN).getImage(), 0, 0, this);
            } else {
                //TODO: color de fondo
            }
            try {
                graphicProgramation(g2);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                //TODO: preferencias del sistema no encontradas
            }
        }
        catch (InterruptedException e) {
            //TODO: mirar que hacer con este catch
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Grafica la programacion de la hora actual.
     *
     * @param g2 grafico 2D
     */
    protected void graphicProgramation(Graphics2D g2) throws InterruptedException, IOException {
        double yPosition = this.getSize().getHeight();
        // se cambian las propiedades de  los graficos
        this.setOpaque(true);
        // si no ha cambiado de programacion y no hay canales, se crea la nueva programacion
        if (!alreadyChange && channelGraphics.size() == 0) {
            // carga la programacion del dia
            programation = new Programacion(getFileOftheDay());
            // recargando imagenes
            for (int channel = 0; channel < programation.getCantCanalesProg(); channel++) {
                // se crea una imagen del canal grafico
                BufferedImage bufferedImage = new BufferedImage(this.getSize().width,
                        (int) SIBAConst.CHANNEL_GRAPHIC_HEIGHT, BufferedImage.TYPE_INT_RGB);
                Graphics2D big = bufferedImage.createGraphics();
                // se crea una instancia de un canal grafico
                ChannelGraphic channelGraphic = new ChannelGraphic(big, programation.getCanalInd(channel));
                // referencia la posicion donde quedara el grafico cuando sea graficado
                channelGraphic.setyPosition(yPosition);
                // se setea la altura del canal
                channelGraphic.setHeight(SIBAConst.CHANNEL_GRAPHIC_HEIGHT);
                // el ancho del canal grafico es el ancho del componente
                channelGraphic.setWidthComponent(this.getSize().getWidth());
                // se referencia la imagen
                channelGraphic.setBufferedImage(bufferedImage);
                // grafica el canal
                
                if ( this.qtyBackgounds < 0){
                	
                	System.out.println("V‡ a operar con categorias fondos");
                	channelGraphic.paintChannel(-1,ViewerHoursControl.getStartHour(), ViewerHoursControl.getStartHour() + SIBAConst.SUM_HOUR);
                	
                }
                else if (this.qtyBackgounds == 0)	
                {
                	System.out.println("No v‡ a operar con mœltiples fondos");
                	channelGraphic.paintChannel(ViewerHoursControl.getStartHour(), ViewerHoursControl.getStartHour() + SIBAConst.SUM_HOUR);
                }	
                else
                {
                	System.out.println("Si v‡ a operar con mœltiples fondos");
                	channelGraphic.paintChannel(this.bkgActivo,ViewerHoursControl.getStartHour(), ViewerHoursControl.getStartHour() + SIBAConst.SUM_HOUR);
                	this.moveToNextInd();
                }
                
                
                
                // se adjunta el canal grafico a la coleccion
                channelGraphics.add(channelGraphic);
                bufferedImage.flush();
                yPosition += SIBAConst.CHANNEL_GRAPHIC_HEIGHT;
                System.out.println(channelGraphic.getyPosition());
            }
            alreadyChange = true;
        }
        if (activatedScheduleChange) {
            // recargando imagenes
            for (ChannelGraphic channelGraphic : channelGraphics) {
                // grafica el canal
                channelGraphic.paintChannel(ViewerHoursControl.getStartHour(), ViewerHoursControl.getStartHour() + SIBAConst.SUM_HOUR);
            }
            activatedScheduleChange = false;
        }
        
        //System.out.println("Already Change: "+alreadyChange+" / Remove Last Programation: "+removeLastProgramation);
        for (Iterator<ChannelGraphic> iteratorChannel = channelGraphics.iterator(); iteratorChannel.hasNext();) {
            ChannelGraphic channelGraphicScroll = iteratorChannel.next();
            
            // si el componente ya fue visualizado, pasa a la cordenada del canal grafico que contiene la
            // cordenada "Y" mas alta
            if (channelGraphicScroll.getyPosition() + SIBAConst.CHANNEL_GRAPHIC_HEIGHT < 0) {
                channelGraphicScroll.setyPosition(getChannelYPositionLast() + SIBAConst.CHANNEL_GRAPHIC_HEIGHT);
            }
            channelGraphicScroll.setyPosition(channelGraphicScroll.getyPosition() - 1.0);
            // borrar anterior imagen
            g2.clearRect(0, (int) channelGraphicScroll.getyPosition(), (int) channelGraphicScroll.getWidthComponent(),
                    (int) channelGraphicScroll.getHeight() + 1);
            // animar la imagen
            g2.drawImage(channelGraphicScroll.getBufferedImage(), 0, (int) channelGraphicScroll.getyPosition(), this);
            // Cada canal que sobrepase los limites de la pantalla en el sentido Norte, sera eliminara
            // de la programacion del dia, (debido a que se hara cambio a una nueva programacion de otro dia)
            // si el componente ya fue visualizado, pasa a la cordenada del canal grafico que contiene la
            // cordenada "Y" mas alta
            if (channelGraphicScroll.getyPosition() + SIBAConst.CHANNEL_GRAPHIC_HEIGHT < 0) {
                // verifica si se van a eliminar los programas a medida que se hayan visto
                if (!alreadyChange && removeLastProgramation) {
                	System.out.println("Se ha definido iteratorChannel.remove()");
                    iteratorChannel.remove();
                }
            }
        }
        
    } // end graphicProgramation

    /**
     * Pinta el componente
     *
     * @param g grafico perteneciente al compoente
     */
    public void paint(Graphics g) {
        super.paint(g);
        update(g);
    }

    /**
     * Inicializa el agente de tiempo, es el que determina los cambios de hora para repintar el nuevo horario
     * en los canales
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
                if (hora > ctrlHours)
                   {ctrlHours = hora;
                    //activada bandera de modificacion de programacion
                    activatedScheduleChange = true;
                   }
                else
                   { if (hora < ctrlHours)
                	    ctrlHours = 0;
                   }
            	
            	
                
            }
        });
        timer.start();
    }

    /**
     * Inicializa el agente que determina un cambio en el archivo, 
     * es el que determina los cambios apartir de una
     * fecha con precision de segundos para repintar el nuevo 
     * horario en los canales
     */
    public void startAgentAlertChange() {
        // referencia la fecha que determina la alerta actual
        Timer timer = new Timer(SIBAConst.TIME_AGENT_ALERT_CHANGE, new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	//System.out.println("Entro aqui general 900: "+controlFlagGeneral.alerta());
            	//System.out.println("Entro aqui personal 900: "+controlFlagPersonal.alerta());
                //if (controlFlagGeneral.alerta() || getControlFlagPersonal().alerta()) {
                if (controlFlagGeneral.alerta().equals(true)) {                	
                    // activada banderas de carga de nuevos archivos XML
                	System.out.println("Se ha modificado la bandera General");
                    removeLastProgramation = true;
                    alreadyChange = false;
                }
                else if(controlFlagPersonal.alerta().equals(true))
                {
                    // activada banderas de carga de nuevos archivos XML
                	System.out.println("Se ha modificado la bandera Personal");
                    removeLastProgramation = true;
                    alreadyChange = false;
                }
                else
                {
                	System.out.println("No se han modificado las banderas");
                }
            }
        });
        timer.start();
    }

    /**
     * Inicializa el efecto de movimiento de la grilla
     */
    public void run() {
        while (true) {
            try {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                int seconds = calendar.get(Calendar.SECOND);
                // si son las doce del otro dia entre 0 y los segundos 
                // que se detiene para hacer el cambio
                // se hace cambio de programacion
                
                if (hour==0 && minutes==0 && ctrlDia1ToDia2==1)
                   {removeLastProgramation = true;
                    alreadyChange = false;
                    ctrlDia1ToDia2 = 0;
                   }
                else
                   {	
                    if (hour==0 && minutes==0)
                       ctrlDia1ToDia2 = 0;
                    else
                       ctrlDia1ToDia2 = 1;
                   }
                
                //Codigo del Rolo para modificar el contenido de la programacion
                //de un id a otro
                
                //if (hour == 0 && minutes == 0) {
                //    if (seconds >= 0 && seconds < SIBAConst.DELAY_BETWEEN_PROGRAMATION / 1000) {
                //        removeLastProgramation = true;
                //        alreadyChange = false;
                //    }
                repaint();
                Thread.sleep(SIBAConst.DELAY_SCROLL);
            }
            catch (InterruptedException e) {
                //TODO: mirar que hacer con este catch
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    /**
     * Retorna la posicion del ultimo canal en el eje Y
     *
     * @return posicion del canal en el eje Y
     */
    public double getChannelYPositionLast() {
        double max = 0.0;
        for (ChannelGraphic channelGraphic : channelGraphics) {
            if (channelGraphic.getyPosition() > max) {
                max = channelGraphic.getyPosition();
            }
        }
        return max;
    }

    public boolean isChangedImage() {
        return changedImage;
    }

    public void setChangedImage(boolean changedImage) {
        this.changedImage = changedImage;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public String getFileFlagPersonal() {
        return fileFlagPersonal;
    }

    public void setFileFlagPersonal(String fileFlagPersonal) {
        this.fileFlagPersonal = fileFlagPersonal;
    }

    public Control getControlFlagPersonal() {
        return controlFlagPersonal;
    }

    public void setControlFlagPersonal(Control controlFlagPersonal) {
        this.controlFlagPersonal = controlFlagPersonal;
    }

    
    /*
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        final ViewerChannel v;
        try {
            v = new ViewerChannel("PER01", "grilla_grl.flg", "grilla_PER01.flg");
            jf.getContentPane().add(v, BorderLayout.CENTER);
            //jf.setSize(800, 600);
            jf.setSize(633, 178);
//        SIBAToolKit.fullScreen(jf);
            jf.setVisible(true);
            Thread th = new Thread(v);
            th.start();
            v.startAgentTime();
            v.startAgentAlertChange();
//        Timer t = new Timer(30000, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Se modifico el archivo");
//                v.setFileFlag("2005-04-30-25:30:45");
//            }
//        });
//        t.setRepeats(false);
//        t.start();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    */
    
    
    private void moveToNextInd()
    {
    	if (this.bkgActivo >= this.qtyBackgounds)
    	{
    		this.bkgActivo = 1;
    	}
    	else
    	{
    		this.bkgActivo++;
    	}
    }

} // end ViewerChannel