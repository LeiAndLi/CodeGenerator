package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.file.JavaModelGeneratorConfiguration;

public class SelectByConditionElementGenerator extends AbstractXmlElementGenerator {

	public SelectByConditionElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

		answer.addAttribute(new Attribute("id", introspectedTable.getSelectByCondtionStatementId())); //$NON-NLS-1$
		answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
				introspectedTable.getBaseResultMapId()));

		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("java.util.List");
		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType.getFullyQualifiedName()));

		StringBuilder sb = new StringBuilder();
		sb.append("select "); //$NON-NLS-1$
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement());
		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement where = new XmlElement("where");
		answer.addElement(where);
		where.addElement(new TextElement("1 = 1"));
		
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			addIsDelete(introspectedColumn, where);
		}

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", WhereConditionElementGenerator.ID));
		where.addElement(include);

		// list column

		parentElement.addElement(answer);
	}
	
	protected boolean addIsDelete(IntrospectedColumn column, XmlElement last){
		if(column.getActualColumnName().equals("is_deleted")){
			StringBuffer sb = new StringBuffer();
			sb.append("and ");
			sb.append("is_deleted = 'n'");
			last.addElement(new TextElement(sb.toString()));
			
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	

	
	
	

}
