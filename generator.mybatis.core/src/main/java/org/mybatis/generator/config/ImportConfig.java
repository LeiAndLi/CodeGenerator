package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class ImportConfig {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public XmlElement toXmlElement() {
		XmlElement xml = new XmlElement("import");

		if (this.type != null) {
			xml.addAttribute(new Attribute("type", type)); //$NON-NLS-1$ );
		}

		return xml;
	}

	public void validate(List<String> errors, String contextId) {
		if (!stringHasValue(type)) {
			errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
		}
	}

}
