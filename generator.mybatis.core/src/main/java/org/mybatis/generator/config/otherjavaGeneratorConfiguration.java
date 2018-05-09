package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class otherjavaGeneratorConfiguration extends ControlGenerateWrapper{

	private String targetPackage;

    private String targetProject;
    
    private String nameSubfix;
    
    private List<ImportConfig> importConfigs;
    
    private List<MethodParamConfig> methodParamConfigs;
    
    private List<AnnotationConfig> annotations;
    
    
    public otherjavaGeneratorConfiguration(){
    	super();
    	this.importConfigs = new ArrayList<ImportConfig>();
    	this.methodParamConfigs = new ArrayList<MethodParamConfig>();
    	this.annotations = new ArrayList<AnnotationConfig>();
    }
    
    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }
    
    public XmlElement toXmlElement( XmlElement answer) {
        if (targetPackage != null) {
            answer.addAttribute(new Attribute("targetPackage", targetPackage)); //$NON-NLS-1$
        }

        if (targetProject != null) {
            answer.addAttribute(new Attribute("targetProject", targetProject)); //$NON-NLS-1$
        }
        
        if (nameSubfix != null) {
            answer.addAttribute(new Attribute("nameSubfix", nameSubfix)); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        for(ImportConfig cfg : this.importConfigs){
        	answer.addElement(cfg.toXmlElement());
        }
        
        for(MethodParamConfig cfg : this.methodParamConfigs){
        	answer.addElement(cfg.toXmlElement());
        }
        
        for(AnnotationConfig cfg : this.annotations){
        	answer.addElement(cfg.toXmlElement());
        }

        super.toXmlElememt(answer);

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
        
        if (!stringHasValue(nameSubfix)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                     contextId)); //$NON-NLS-1$
        }
        
        for(ImportConfig cfg : this.importConfigs){
        	cfg.validate(errors, contextId);
        }
        
        for(MethodParamConfig cfg : this.methodParamConfigs){
        	cfg.validate(errors, contextId);
        }
        
        for(AnnotationConfig cfg : this.annotations){
        	cfg.validate(errors, contextId);
        }
        
        super.validate(errors, contextId);
    
    }

	public String getNameSubfix() {
		return nameSubfix;
	}

	public void setNameSubfix(String nameSubfix) {
		this.nameSubfix = nameSubfix;
	}
	
	public String getFullClassType(IntrospectedTable introspectedTable){
		String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		
		return getFullClassType(domainObjectName);
	}
	
	public String getFullClassType(String domainObjectName){
	    StringBuilder sb = new StringBuilder();
        sb.append(this.targetPackage);
        sb.append(".");
        sb.append(domainObjectName);
        sb.append(this.nameSubfix);
        
        return sb.toString();
	}
	
	public String getLowShortClassName(IntrospectedTable introspectedTable){
		String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		return getLowShortClassName(domainObjectName);
	}
	
	public String getLowShortClassName(String domainObjectName){
	    String className = domainObjectName + this.nameSubfix;
        String prefix = className.substring(0, 1);
        String subfix = className.substring(1);
        String lowName = prefix.toLowerCase() + subfix;
        
        return lowName; 
	}
	
	public boolean hasRootClass(){
		return this.getProperty(PropertyRegistry.ANY_ROOT_CLASS) != null;
	}
	
	public String getRootClass(){
		String root = this.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
		return root;
	}

	public List<ImportConfig> getImportConfigs() {
		return importConfigs;
	}

	public void setImportConfigs(List<ImportConfig> importConfigs) {
		this.importConfigs = importConfigs;
	}

	public List<MethodParamConfig> getMethodParamConfigs() {
		return methodParamConfigs;
	}

	public void setMethodParamConfigs(List<MethodParamConfig> methodParamConfigs) {
		this.methodParamConfigs = methodParamConfigs;
	}

	public List<AnnotationConfig> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationConfig> annotations) {
		this.annotations = annotations;
	}
	
	public void addMethodConfig(MethodParamConfig cfg){
		this.methodParamConfigs.add(cfg);
	}
	
	public void addImportConfig(ImportConfig cfg){
		this.importConfigs.add(cfg);
	}
	
	public void addAnnotation(AnnotationConfig cfg){
		this.annotations.add(cfg);
	}

	
}
