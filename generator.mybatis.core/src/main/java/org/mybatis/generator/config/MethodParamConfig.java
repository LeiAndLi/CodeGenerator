package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MethodParamConfig {

	private String name;
	private String type;
	private String annotation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	
	public XmlElement toXmlElement() {
		XmlElement xml = new XmlElement("methodParam");

		if (this.type != null) {
			xml.addAttribute(new Attribute("type", type)); //$NON-NLS-1$ );
		}
		
		if (this.name != null) {
			xml.addAttribute(new Attribute("name", name)); //$NON-NLS-1$ );
		}
		
		if (this.annotation != null) {
			xml.addAttribute(new Attribute("annotation", annotation)); //$NON-NLS-1$ );
		}

		return xml;
	}

	public void validate(List<String> errors, String contextId) {
		if (!stringHasValue(type)) {
			errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
		}
		
		if (!stringHasValue(name)) {
			errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
		}
		
		if (!stringHasValue(annotation)) {
			errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
		}
	}
	
}
