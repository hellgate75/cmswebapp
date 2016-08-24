package com.ralphlauren.ecms.stores.rest.api.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * This is the Image file download Servlet. We have a request for couple of information : the unique file name and a the
 * file type.
 * @author Fabrizio Torelli
 *
 */
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {

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
    	/* Here We extract two parts one contains the new name and the other the file data*/
		String file = request.getParameter("file");
		String type = request.getParameter("type");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
        	/* Here We set a direcory in the startup path (usually writable and persistent) and we have saved the files */
			String dir = System.getProperty("user.dir")+File.separator+"uploads";
        	/* Here We try to create the directory */
			File destDir = new File(dir);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
        	/* Here We create the reference to the existing File */
			File tmpFile = new File(dir+File.separator+file);
        	/* Here We write the file in the byte array output stream */
			if (tmpFile.exists()) {
				IOUtils.copy(new FileInputStream(tmpFile), baos);
			}
        	/* Here We assign the success state variables!! No error has been thrown */
			code = 200;
		} catch (Throwable e) {
			e.printStackTrace();
		}
    	/* Here We prepare the servlet response to transfer to the file to the UI */
        response.setContentType(type);
        response.setCharacterEncoding("UTF-8");
    	/* Here We write the byte array output stream content to the response*/
        response.getWriter().write(baos.toString("UTF-8"));
        response.setStatus(code);
    }
}