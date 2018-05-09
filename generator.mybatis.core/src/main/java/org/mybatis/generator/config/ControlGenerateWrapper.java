package org.mybatis.generator.config;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class ControlGenerateWrapper extends PropertyHolder{
	
	private boolean generate;
	
	public ControlGenerateWrapper(){
		super();
		this.generate = true;
	}

	public boolean isGenerate() {
		return generate;
	}

	public void setGenerate(boolean generate) {
		this.generate = generate;
	}
	
	public void toXmlElememt( XmlElement answer){
		if(this.generate){
        	answer.addAttribute(new Attribute("generate", "true"));
        }else{
        	answer.addAttribute(new Attribute("generate", "false"));
        }
        

        addPropertyXmlElements(answer);
	}
	
	public void validate(List<String> errors, String contextId) {
		
	}

}
