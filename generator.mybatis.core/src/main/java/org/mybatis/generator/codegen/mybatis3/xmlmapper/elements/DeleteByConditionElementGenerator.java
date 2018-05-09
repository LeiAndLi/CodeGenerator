package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class DeleteByConditionElementGenerator extends AbstractXmlElementGenerator{

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("id", introspectedTable.getDeleteByConditionStatementId())); //$NON-NLS-1$
		
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("java.util.List");
		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType.getFullyQualifiedName()));
		
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));
		
		XmlElement where = new XmlElement("where");
		answer.addElement(where);
		
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", WhereConditionElementGenerator.ID));
		where.addElement(include);
		
		parentElement.addElement(answer);
	}

}
