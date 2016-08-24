package com.ralphlauren.ecms.stores.rest.api.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Root class exposing the API version, code and features list
 * @author Fabrizio Torelli
 *
 */
@XmlRootElement
public class Version {

	private String versionId = "1.0.0";
	private String serviceCode = "cms-stores";
	private ArrayList<String> services = new ArrayList<String>(0);
	
	/**
	 * Public Constructor that incubates the available services and prepares the related list
	 */
	public Version() {
		super();
		services.add("version");
	}
	/**
	 * Expose the API version Id
	 * @return The API version identifier string
	 */
	@XmlAttribute
	public String getVersionId() {
		return versionId;
	}
	/**
	 * Expose the API service code
	 * @return The API service code string
	 */
	@XmlAttribute
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * Expose the API available features list
	 * @return The list of the API available features
	 */
	@XmlElement
	public ArrayList<String> getServices() {
		return services;
	}
	
	
}
