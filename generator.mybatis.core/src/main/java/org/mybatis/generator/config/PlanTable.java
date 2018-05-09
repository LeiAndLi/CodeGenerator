package org.mybatis.generator.config;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class PlanTable {

    /** The table name. */
    private String tableName;
    
    /** The domain object name. */
    private String domainObjectName;
    
    public PlanTable(String table, String object){
        super();
        this.tableName = table;
        this.domainObjectName = object;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }
    
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("planTable"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("tableName", tableName)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("domainObjectName", domainObjectName));
        return xmlElement;
    }
    
    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(tableName)) {
            errors.add(getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
        if (!stringHasValue(domainObjectName)) {
            errors.add(getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
    }
    
    
}
