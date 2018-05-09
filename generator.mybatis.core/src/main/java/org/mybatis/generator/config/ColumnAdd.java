package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class ColumnAdd {

	private String dbSubfix;

	private String addSubfix;

	private String type;

	public ColumnAdd(String db, String add, String type) {
		this.dbSubfix = db;
		this.addSubfix = add;
		this.type = type;
	}

	public XmlElement toXmlElement() {
		XmlElement xmlElement = new XmlElement("columnAdd"); //$NON-NLS-1$
		xmlElement.addAttribute(new Attribute("dbSubFix", dbSubfix)); //$NON-NLS-1$
		xmlElement.addAttribute(new Attribute("addSubFix", addSubfix)); //$NON-NLS-1$
		xmlElement.addAttribute(new Attribute("type", type)); //$NON-NLS-1$

		return xmlElement;
	}

	public void validate(List<String> errors, String contextId) {
		if (!stringHasValue(this.dbSubfix)) {
			errors.add(getString("ValidationError.21", //$NON-NLS-1$
					contextId));
		}
		
		if (!stringHasValue(this.addSubfix)) {
			errors.add(getString("ValidationError.21", //$NON-NLS-1$
					contextId));
		}
		
		if (!stringHasValue(this.type)) {
			errors.add(getString("ValidationError.21", //$NON-NLS-1$
					contextId));
		}
	}

	public String getDbSubfix() {
		return dbSubfix;
	}

	public void setDbSubfix(String dbSubfix) {
		this.dbSubfix = dbSubfix;
	}

	public String getAddSubfix() {
		return addSubfix;
	}

	public void setAddSubfix(String addSubfix) {
		this.addSubfix = addSubfix;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFieldName(IntrospectedColumn introspectedColumn){
		String property = introspectedColumn.getJavaProperty();
		String name = property + this.getAddSubfix();
		return name;
	}
	
	public String getGetterName(IntrospectedColumn introspectedColumn){
		String property = introspectedColumn.getJavaProperty();
		String name = JavaBeansUtil.getGetterMethodName(property, new FullyQualifiedJavaType(this.getType()));
		return name + this.getAddSubfix();
	}
	
	public String getSetterName(IntrospectedColumn introspectedColumn){
		String property = introspectedColumn.getJavaProperty();
		String name = JavaBeansUtil.getSetterMethodName(property);
		return name + this.getAddSubfix();
	}
	
	

}
