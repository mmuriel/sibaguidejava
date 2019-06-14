package com.siba.display;

import com.siba.common.SIBAToolKit;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * AppletChannelProgramation
 * Esta clase visualiza la programacion en un aplet respectivo
 */
public class AppletChannelProgramation extends JApplet {

    public AppletChannelProgramation(){
    }
    /**
     * Metodo que inicializa el applet
     */
    public void init() {
        String paramFlagFilename;
        try {
            // carga el codigo del operador de las preferencias de la aplicacion
            String customerCode = SIBAToolKit.applicationPreference("OPERATOR_CODE");
            String pathFiles = SIBAToolKit.applicationPreference("PATH_FILES");
            System.out.println(pathFiles);
            
            
            try {
                // carga el nombre del archivo referente al flag
                paramFlagFilename = this.getParameter("FlagFilename");
            } catch (NullPointerException ex1) {
                // nombre del archivo perteneciente al flag, cuando no se envia como parametro en el applet
                // se referencia este valor por defecto
                paramFlagFilename = "grilla";
            }
            // instancia e inicializa el vizor de canales
            String tmpGrillaGral = pathFiles+"grilla_grl.flg";
            String tmpGrillaLocal = pathFiles+ "grilla_"+customerCode + ".flg";
            
            System.out.println("Grillas: "+tmpGrillaGral+ " / "+tmpGrillaLocal);
            ViewerChannel viewer = new ViewerChannel(customerCode,tmpGrillaGral, tmpGrillaLocal);
            
            //#####################################################
            // INSTANCIA EL VISOR DE LAS HORAS
            //#####################################################
            
            // instancia el visor de horas
            /* ViewerHoursControl viewerHours = new ViewerHoursControl();*/
            // modifica la altura del componente
            /* viewerHours.setPreferredSize(new Dimension(-1, 21)); */
            //#####################################################
            // FIN INSTANCIA EL VISOR DE LAS HORAS
            //#####################################################
            
            
            // crea un Hilo de ejecucion para visualizar el scroll del visor de canales
            Thread threadViewer = new Thread(viewer);
            // inicializa el agente de cambio en archivos XML
            viewer.startAgentAlertChange();
            // inicializa los agentes de cambio de horas
            viewer.startAgentTime();
            
            
            //viewerHours.startAgentTime();
            
            
            
            
            // adjunta el visor de canales al applet
            this.add(viewer, BorderLayout.CENTER);
            // adjunta el visor de horas al applet
            //this.add(viewerHours, BorderLayout.BEFORE_FIRST_LINE);
            // arranca el hilo de ejecucion
            threadViewer.start();
        } catch (IOException e) {
            //TODO: error al cargar las preferencias
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame fr = new JFrame();
        AppletChannelProgramation applet = new AppletChannelProgramation();
        applet.init();
        fr.add(applet, BorderLayout.CENTER);
        fr.setSize(1280,190);
        fr.setVisible(true);
    }

}
