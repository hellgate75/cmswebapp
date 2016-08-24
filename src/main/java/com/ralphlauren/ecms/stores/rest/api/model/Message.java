package com.ralphlauren.ecms.stores.rest.api.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Root class exposing Operation Message to the UI
 * @author Fabrizio Torelli
 *
 */
@XmlRootElement
public class Message {

	private String messageCode;
	private String messageText;
	private boolean successStatus = false;

	/**
	 * Retrieve the Message Code
	 * @return The Message Code
	 */
	@XmlAttribute
	public String getMessageCode() {
		return messageCode;
	}
	/**
	 * Retrieve the Message Text
	 * @return The Message Text
	 */
	@XmlAttribute
	public String getMessageText() {
		return messageText;
	}
	/**
	 * Retrieve the Success Status
	 * @return The Success Status
	 */
	@XmlAttribute
	public boolean getSuccessStatus() {
		return successStatus;
	}
	/**
	 * Set the Message Code
	 * @param messageCode The Message Code
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	/**
	 * Set the Message Text
	 * @param messageText The Message Text
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	/**
	 * Set the Success Status
	 * @param successStatus The Success Status
	 */
	public void setSuccessStatus(boolean successStatus) {
		this.successStatus = successStatus;
	}
	
	
}
