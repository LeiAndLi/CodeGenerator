package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class UserdefGeneratorConfig extends PropertyHolder{
	
	private String targetPackage;

    private String targetProject;
    
    private String templateFileName;
    
    private String targetFileName;
    
    private boolean enableAppend;
    
    public UserdefGeneratorConfig(){
    	super();
    	
    	this.enableAppend = false;
    }

	public String getTargetPackage() {
		return targetPackage;
	}

	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public String getTargetProject() {
		return targetProject;
	}

	public void setTargetProject(String targetProject) {
		this.targetProject = targetProject;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}
    
    
	public XmlElement toXmlElement( ) {
		XmlElement answer = new XmlElement("templateUserdefGenerator");
		
        if (targetPackage != null) {
            answer.addAttribute(new Attribute("targetPackage", targetPackage)); //$NON-NLS-1$
        }

        if (targetProject != null) {
            answer.addAttribute(new Attribute("targetProject", targetProject)); //$NON-NLS-1$
        }
        
        if (templateFileName != null) {
            answer.addAttribute(new Attribute("templateFileName", templateFileName)); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if (targetFileName != null) {
            answer.addAttribute(new Attribute("targetFileName", targetFileName)); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        if(this.enableAppend){
        	answer.addAttribute(new Attribute("enableAppend", "true"));
        }else{
        	answer.addAttribute(new Attribute("enableAppend", "false"));
        }
        

        addPropertyXmlElements(answer);

        return answer;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(targetProject)) {
            errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(targetPackage)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                    contextId)); //$NON-NLS-1$
        }
        
        if (!stringHasValue(templateFileName)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                     contextId)); //$NON-NLS-1$
        }
        
        if (!stringHasValue(targetFileName)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                     contextId)); //$NON-NLS-1$
        }
    
    }

	public boolean isEnableAppend() {
		return enableAppend;
	}

	public void setEnableAppend(boolean enableAppend) {
		this.enableAppend = enableAppend;
	}

    
    
}
