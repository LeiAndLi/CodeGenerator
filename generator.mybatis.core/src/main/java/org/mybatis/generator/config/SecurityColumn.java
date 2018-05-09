package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SecurityColumn {

	 /** The column name. */
    protected String columnName;
    
    protected String javaType;
    
    protected String jdbcType;
    
    public SecurityColumn(String columnName, String javaType, String jdbcType){
    	super();
    	this.columnName = columnName;
    	this.javaType = javaType;
    	this.jdbcType = jdbcType;
    }
    
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("securityColumn"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("column", columnName)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("javaType", javaType));
        xmlElement.addAttribute(new Attribute("jdbcType", jdbcType));
        return xmlElement;
    }
    
    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(columnName)) {
            errors.add(getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
        if (!stringHasValue(javaType)) {
            errors.add(getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
        if (!stringHasValue(jdbcType)) {
            errors.add(getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
    }
    
    
    public boolean matches(String columnName) {
    	return this.columnName.equalsIgnoreCase(columnName);
    }

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
    
	
    
}
