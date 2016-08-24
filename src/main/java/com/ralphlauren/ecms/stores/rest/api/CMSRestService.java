package com.ralphlauren.ecms.stores.rest.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;

import com.heroku.sdk.jdbc.DatabaseUrl;
import com.ralphlauren.ecms.stores.rest.api.model.Message;
import com.ralphlauren.ecms.stores.rest.api.model.Store;
import com.ralphlauren.ecms.stores.rest.api.model.Version;
/**
 * CMS default service entry point exposing the CMS features.
 * @author Fabrizio Torelli
 *
 */
@Path("cms")
public class CMSRestService {
	private static final Version version = new Version();

	private Connection connection = null;

	/**
	 * This method is executed on the startup of the Access Point instance and check that the database is created properly.
	 */
	@PostConstruct
	public void init() {
		try {
			connection = DatabaseUrl.extract().getConnection();
			Statement stmt = connection.createStatement();
			//stmt.executeUpdate("DROP TABLE IF EXISTS stores");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS stores (store_id bigserial, store_name varchar(255), store_type varchar(1), address varchar(1000), image varchar(1000), imageType varchar(20), zip_code varchar(20) )");
		} catch (Throwable e) {
			//NOT NEEDED
			//e.printStackTrace();
		}
	}

	/**
	 * This method is executed before the shutdown of the Access Point instance and check that the database references are destroyed.
	 */
	@PreDestroy
	public void destroy() {
		try {
			if (connection != null)
				connection.close();
		} catch (Throwable e) {
		}
	}

	/**
	 	This web method is useful to understand the server version, the internal service name and the available features.
	 	Get Method. See {@link Version} for any further information.
	 	@return Version The API Version Object in JSON format
	 */
	@GET 
	@Path("/version")
	@Produces(MediaType.APPLICATION_JSON)
	public Version getVersion() {
		return version;
	}
	
	/**
	 	This web method save a single store from the UI with a form encoded data format.
	 	POST Method. See {@link Response} for any further information.
	 	@return Response The save Response Object in json format
	 */
	@POST 
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertStore(String param) {
		Message message = new Message();
		try {
			/*Here we recover the JSon string and we decode from the UI encoding*/
			String json = java.net.URLDecoder.decode(param, "UTF-8");
			/*Here we translate the json string in a JSON Object Document */
			JSONObject jsonObject = new JSONObject(json);
			/*Here we recover the data from the JSON Object Document */
			String storeName = jsonObject.getString("storeName");
			String storeType = jsonObject.getString("storeType");
			String storeAddress = jsonObject.getString("storeAddress");
			String storeZipCode = jsonObject.getString("storeZipCode");
			String storeImageType = jsonObject.getString("storeImageType");
			String tmp = jsonObject.getString("storeImage");
			/*Here we assign an unique name to the file */
			String storeImage = UUID.randomUUID().toString() + "-" + tmp.substring(tmp.lastIndexOf("/")+1).substring(tmp.lastIndexOf("\\")+1);
			/*Here we create the insert query */
			String sql = "INSERT INTO stores(store_name, store_type, address, image, imageType, zip_code) VALUES (?, ?, ?, ?, ?, ?)";
			/*Here we prepare the SQL Statement and we fill it with the parameters */
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, storeName);
			stmt.setString(2, storeType);
			stmt.setString(3, storeAddress);
			stmt.setString(4, storeImage);
			stmt.setString(5, storeImageType);
			stmt.setString(6, storeZipCode);
			/*Here we execute the SQL Statement */
			int results = stmt.executeUpdate();
			/*Here we check the correct execution of the statement */
			if (results==0) {
				/*Here we assign an error response variable value */
				message.setMessageCode("500");
				message.setMessageText("Unable to save the data");
				message.setSuccessStatus(false);
			}
			else {
				/*Here we assign a success response variable value */
				message.setSuccessStatus(true);
				message.setMessageCode("200");
				message.setMessageText(storeImage);
				message.setSuccessStatus(true);
			}
		} catch (Throwable e) {
			/*Here we assign an error response variable value */
			message.setMessageCode("500");
			message.setMessageText(e.getMessage());
			message.setSuccessStatus(false);
		}
		/*Here we send the response to the UI (logical exception) */
		return Response.status(200).entity(message).build();
	}
	/**
	 	This web method retrieve the full list of saved stores.
	 	Get Method. See {@link Store} for any further information.
	 	@return Version The Full Store list in JSON format
	 */
	@GET 
	@Path("/stores")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStores() {
		List<Store> response = new ArrayList<Store>(0);
		try {
			/*Here we create the SQL Statement */
			Statement statment = connection.createStatement();
			/*Here we extract all the stores and we package to a JAXB class */
			ResultSet responseSet = statment.executeQuery("SELECT store_id, store_name, store_type, address, image, imageType, zip_code FROM stores");
			while(responseSet.next()) {
				Store store = new Store();
				store.setStoreId(responseSet.getString(1));
				store.setStoreName(responseSet.getString(2));
				store.setStoreType(responseSet.getString(3));
				store.setStoreAddress(responseSet.getString(4));
				store.setStoreImage(responseSet.getString(5));
				store.setStoreImageType(responseSet.getString(6));
				store.setZipCode(responseSet.getString(7));
				response.add(store);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/*Here we make an instance of the client side cache control manager */
	    CacheControl cc = new CacheControl();
		/*Here we set the max-age cache control value */
	    cc.setMaxAge(300); 
	    //We cache client side the result for 5 minutes
	    //in order to arrange in the UI a kind of paged table
	    cc.setPrivate(true);
		/*Here we prepare the output json. The cache control has no json decoder inside */
	    String json = JSONObject.valueToString(response);
		/*Here we assign the json string to the response of the response builder */
	    ResponseBuilder builder = Response.ok(json);
		/*Here we assign the Cache Control to the response builder */
	    builder.cacheControl(cc);
		/*Here we send the cached enabled response to the UI */
	    return builder.build();
	}

	/**
	 	This web method retrieve the list of saved stores filtered by Store Type ('R' is for Retail,'F' is for Factory).
	 	Get Method. See {@link Store} for any further information.
	 	@return Version The Filtered Store list in JSON format
	 */
	@GET 
	@Path("/byType/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByType(@PathParam("type") String type) {
		List<Store> response = new ArrayList<Store>(0);
		try {
			/*Here we create the SQL Statement string */
			String sql = "SELECT store_id, store_name, store_type, address, image, imageType, zip_code FROM stores WHERE store_type LIKE ?";
			/*Here we prepare the SQL Statement */
			PreparedStatement statment = connection.prepareStatement(sql);
			/*Here we assign the parameter value */
			System.out.println("type = " + type);
			statment.setString(1, type);
			/*Here we extract all the stores and we package to a JAXB class */
			ResultSet responseSet = statment.executeQuery();
			while(responseSet.next()) {
				Store store = new Store();
				store.setStoreId(responseSet.getString(1));
				store.setStoreName(responseSet.getString(2));
				store.setStoreType(responseSet.getString(3));
				store.setStoreAddress(responseSet.getString(4));
				store.setStoreImage(responseSet.getString(5));
				store.setStoreImageType(responseSet.getString(6));
				store.setZipCode(responseSet.getString(7));
				response.add(store);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/*Here we make an instance of the client side cache control manager */
	    CacheControl cc = new CacheControl();
		/*Here we set the max-age cache control value */
	    cc.setMaxAge(300); 
	    //We cache client side the result for 5 minutes
	    //in order to arrange in the UI a kind of paged table
	    cc.setPrivate(true);
		/*Here we prepare the output json. The cache control has no json decoder inside */
	    String json = JSONObject.valueToString(response);
		/*Here we assign the json string to the response of the response builder */
	    ResponseBuilder builder = Response.ok(json);
		/*Here we assign the Cache Control to the response builder */
	    builder.cacheControl(cc);
		/*Here we send the cached enabled response to the UI */
	    return builder.build();
	}
	/**
	 	This web method retrieve the list of saved stores filtered by Store Name.
	 	Get Method. See {@link Store} for any further information.
	 	@return Version The Filtered Store list in JSON format
	 */
	@GET 
	@Path("/byName/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByName(@PathParam("name") String name) {
		List<Store> response = new ArrayList<Store>(0);
		try {
			/*Here we recover the Name string and we decode from the UI encoding*/
			String encodedName = java.net.URLDecoder.decode(name, "UTF-8");
			/*Here we create the SQL Statement string */
			String sql = "SELECT store_id, store_name, store_type, address, image, imageType, zip_code FROM stores WHERE store_name LIKE ?";
			/*Here we prepare the SQL Statement */
			PreparedStatement statment = connection.prepareStatement(sql);
			/*Here we assign the parameter value */
			statment.setString(1, "%" + encodedName + "%");
			/*Here we extract all the stores and we package to a JAXB class */
			ResultSet responseSet = statment.executeQuery();
			while(responseSet.next()) {
				Store store = new Store();
				store.setStoreId(responseSet.getString(1));
				store.setStoreName(responseSet.getString(2));
				store.setStoreType(responseSet.getString(3));
				store.setStoreAddress(responseSet.getString(4));
				store.setStoreImage(responseSet.getString(5));
				store.setStoreImageType(responseSet.getString(6));
				store.setZipCode(responseSet.getString(7));
				response.add(store);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/*Here we make an instance of the client side cache control manager */
	    CacheControl cc = new CacheControl();
		/*Here we set the max-age cache control value */
	    cc.setMaxAge(300); 
	    //We cache client side the result for 5 minutes
	    //in order to arrange in the UI a kind of paged table
	    cc.setPrivate(true);
		/*Here we prepare the output json. The cache control has no json decoder inside */
	    String json = JSONObject.valueToString(response);
		/*Here we assign the json string to the response of the response builder */
	    ResponseBuilder builder = Response.ok(json);
		/*Here we assign the Cache Control to the response builder */
	    builder.cacheControl(cc);
		/*Here we send the cached enabled response to the UI */
	    return builder.build();
	}



}
