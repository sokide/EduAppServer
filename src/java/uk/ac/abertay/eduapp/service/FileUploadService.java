/**
 * 
 */
package uk.ac.abertay.eduapp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.abertay.eduapp.common.Constants;
import uk.ac.abertay.eduapp.common.Util;

import com.sun.jersey.core.header.FormDataContentDisposition;


/**
 * @author Sam Okide
 * 
 */

@Path("/file")
public class FileUploadService {
    
    private static Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    
    private static final String TOMCAT_WEB_APP = "C:/workspace/java/tools/apache-tomcat-7.0.47/webapps/";
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = Constants.APP_NAME + "/uploads/";

    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_JSON})
    public Response uploadFile(
	    @FormDataParam("file") InputStream fileInputStream,
	    @FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
	    @Context HttpServletRequest req) throws IOException {
	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Post called - upload file " );
	String fileName = contentDispositionHeader.getFileName();
	File serverDirectory = new File(TOMCAT_WEB_APP + SERVER_UPLOAD_LOCATION_FOLDER);
	if (!serverDirectory.exists()) {
	    serverDirectory.mkdirs();
	}
	String filePath = TOMCAT_WEB_APP + SERVER_UPLOAD_LOCATION_FOLDER + fileName;
	// save the file to the server
	saveFile(fileInputStream, filePath);
	String imageUrl = Util.getContextPath(req) + "/" + SERVER_UPLOAD_LOCATION_FOLDER + fileName;
	return Response.status(200).entity(imageUrl).build();
    }

    
    private void saveFile(InputStream uploadedInputStream, String serverLocation) { 
       try {
           OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024]; 
            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
	    uploadedInputStream.close();
        } catch (IOException e) { 
            e.printStackTrace();
        }
 
    }


}