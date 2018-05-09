package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class BatchUpdateByConditionElementGenerator extends AbstractXmlElementGenerator{

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("id", introspectedTable.getBatchUpdateByConditionStatementId())); //$NON-NLS-1$
		
		XmlElement foreach = this.getForeachElement(";", "records", false);
		answer.addElement(foreach);
		
		StringBuilder sb = new StringBuilder();

        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        foreach.addElement(new TextElement(sb.toString()));
        
        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        foreach.addElement(dynamicElement);
        
        XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", ItemUpdateSelectElementGenerator.ID));
		dynamicElement.addElement(include);
        
        XmlElement wh = new XmlElement("where");
        foreach.addElement(wh);
        
        XmlElement include2 = new XmlElement("include");
		include2.addAttribute(new Attribute("refid", ConditionUpdateElementGenerator.ID));
		wh.addElement(include2);
		
		parentElement.addElement(answer);
	}

}
