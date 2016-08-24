package com.ralphlauren.ecms.stores.rest.api.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Root class exposing the API version, code and features list
 * @author Fabrizio Torelli
 *
 */
@XmlRootElement
public class Store {

	private String storeId;
	private String storeName;
	private String storeType;
	private String storeAddress;
	private String storeImage;
	private String storeImageType;
	private String zipCode;

	/**
	 * Retrieve the Store Id
	 * @return The Store Id
	 */
	@XmlAttribute
	public String getStoreId() {
		return storeId;
	}
	/**
	 * Retrieve the Store Name
	 * @return The Store Name
	 */
	@XmlAttribute
	public String getStoreName() {
		return storeName;
	}
	/**
	 * Retrieve the Store Type
	 * @return The Store Type
	 */
	@XmlAttribute
	public String getStoreType() {
		return storeType;
	}
	/**
	 * Retrieve the Store Address
	 * @return The Store Address
	 */
	@XmlAttribute
	public String getStoreAddress() {
		return storeAddress;
	}
	/**
	 * Retrieve the Store Image
	 * @return The Store Image
	 */
	@XmlAttribute
	public String getStoreImage() {
		return storeImage;
	}
	/**
	 * Retrieve the Store Image Type
	 * @return The Store Image Type
	 */
	@XmlAttribute
	public String getStoreImageType() {
		return storeImageType;
	}
	/**
	 * Retrieve the Store Zip Code
	 * @return The Store Zip Code
	 */
	@XmlAttribute
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * Set the Store Id
	 * @param storeName The Store Id
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	/**
	 * Set the Store Name
	 * @param storeName The Store Name
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	/**
	 * Set the Store Type
	 * @param storeType The Store Type
	 */
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	/**
	 * Set the Store Address
	 * @param storeAddress The Store Address
	 */
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	/**
	 * Set the Store Image
	 * @param storeImage The Store Image
	 */
	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}
	/**
	 * Set the Store Image Type
	 * @param storeImage The Store Image Type
	 */
	public void setStoreImageType(String storeImageType) {
		this.storeImageType = storeImageType;
	}
	/**
	 * Set the Store Zip Code
	 * @param zipCode The Store Zip Code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}
