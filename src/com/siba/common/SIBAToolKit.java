/** Java class "SIBAToolKit.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.siba.common;

import java.awt.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URI;

/**
 * SIBAToolKit
 * Contiene diversas funcionalidades y utilidades para diversos usos en la
 * aplicacion.
 */
public class SIBAToolKit {

	private static SIBAToolKit sibaOwn = new SIBAToolKit();

	/**
	 * Amplia la ventana especificada como parametro a Full Screen
	 *
	 * @param frame venatana a ampliar
	 */
	public static void fullScreen(Frame frame) {
		frame.setUndecorated(true);
		frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);
	} // end fullScreen

	/**
	 * Retorna una preferencia de la aplicacion determinada
	 *
	 * @param namePreference Nombre de la preferencia de la aplicacion a consultar valor
	 * @return valor de la preferencia de la aplicacion
	 */
	public static String applicationPreference(String namePreference) throws IOException {
		// el preference.ini esta en el archivo siba.jar
		URL url = sibaOwn.getClass().getClassLoader().getResource(SIBAConst.APPLICATION_PREFERENCES);
		// abre un stream con el .jar que contiene las preferencias de la aplicacion
		InputStream filePrefences = url.openStream();
		// objeto que permite administrar propiedades
		Properties defaultProps = new Properties();
		// carga las preferencias de la aplicacion
		defaultProps.load(filePrefences);
		filePrefences.close();
		//System.out.println(defaultProps.toString());
		return defaultProps.getProperty(namePreference);
	} // end applicationPreference

} // end SIBAToolKit



