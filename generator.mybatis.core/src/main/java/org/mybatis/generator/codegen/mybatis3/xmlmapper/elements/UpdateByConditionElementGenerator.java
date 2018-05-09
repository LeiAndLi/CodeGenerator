package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class UpdateByConditionElementGenerator extends AbstractXmlElementGenerator{

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("id", introspectedTable.getUpdateByConditionStatementId())); //$NON-NLS-1$
		
		answer.addElement(new TextElement("update"));
		answer.addElement(new TextElement(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
		
		XmlElement set = new XmlElement("set");
		XmlElement include1 = new XmlElement("include");
		include1.addAttribute(new Attribute("refid", ItemUpdateSelectElementGenerator.ID));
		set.addElement(include1);
		answer.addElement(set);
		
		XmlElement where = new XmlElement("where");
		
		
		XmlElement include2 = new XmlElement("include");
		include2.addAttribute(new Attribute("refid", ConditionUpdateElementGenerator.ID));
		where.addElement(include2);
		answer.addElement(where);
		
		parentElement.addElement(answer);
	}

}
