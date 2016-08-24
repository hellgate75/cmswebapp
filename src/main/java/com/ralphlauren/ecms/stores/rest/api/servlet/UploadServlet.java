package com.ralphlauren.ecms.stores.rest.api.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

/**
 * This is the Multipart Image file load and persist Servlet. We have a request for a dual multipart information : the file and a new custom
 * and unique name defined during the Database record insert from the rest service.
 * @author Fabrizio Torelli
 *
 */
@MultipartConfig
@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {

    /**
	 * Serial Version UID of the Mask
	 */
	private static final long serialVersionUID = 3114279586424149847L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	/* Here We prepare the response variables */
    	int code = 500;
    	String message = "";
    	boolean success = false;
        try {
        	/* Here We extract two parts one contains the new name and the other the file data*/
			Part file = request.getPart("file");
			Part name = request.getPart("newName");
        	/* Here We convert to String the name income as InputStream*/
			String filename = IOUtils.toString(name.getInputStream(), "UTF-8");
        	/* Here We set a direcory in the startup path (usually writable and persistent) */
			String dir = System.getProperty("user.dir")+File.separator+"uploads";
        	/* Here We try to create the directory */
			File destDir = new File(dir);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
        	/* Here We create the reference to the new File */
			File tmpFile = new File(dir+File.separator+filename);
        	/* Here We write the file in the output directory */
			IOUtils.copy(file.getInputStream(), new FileOutputStream(tmpFile.getAbsolutePath()));
        	/* Here We assign the success state variables!! No error has been thrown */
			code = 200;
			success = true;
		} catch (Throwable e) {
        	/* Here We assign the failure state variables!! And error has been thrown */
			message = e.getMessage();
			e.printStackTrace();
		}
    	/* Here We prepare the servlet response to transfer to the UI the response */
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"messageCode\":\""+code+"\",\"messageText\":\""+message+"\",\"successStatus\":"+success+"}");
        response.setStatus(code);
    }
}