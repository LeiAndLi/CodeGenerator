package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SelectShortByConditionElementGenerator extends AbstractXmlElementGenerator{

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("id", introspectedTable.getSelectShortByConditionStatementId())); //$NON-NLS-1$
		
	
		String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
		
		FullyQualifiedJavaType list = new FullyQualifiedJavaType("java.util.List");
		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				list.getFullyQualifiedName()));
		
		answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
		    namespace + "." + introspectedTable.getBaseResultMapId()));
		
		answer.addElement(new TextElement("select"));
		answer.addElement(new TextElement("id"));
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));
		
		XmlElement where = new XmlElement("where");
		answer.addElement(where);
		
		sb.setLength(0);
		sb.append("1 = 1 ");
		sb.append("and ");
		sb.append("is_deleted = 'n' ");
		where.addElement(new TextElement(sb.toString()));
		
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", namespace + "." +  WhereConditionElementGenerator.ID));
		where.addElement(include);
		
		parentElement.addElement(answer);
	}

}
