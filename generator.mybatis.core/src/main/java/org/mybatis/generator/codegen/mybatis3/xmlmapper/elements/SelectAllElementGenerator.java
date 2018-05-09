package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

public class SelectAllElementGenerator  extends
AbstractXmlElementGenerator{

	@Override
	public void addElements(XmlElement parentElement) {
		 XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

	        answer.addAttribute(new Attribute(
	                "id", introspectedTable.getSelectAllStatementId())); //$NON-NLS-1$
	        answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
	                introspectedTable.getBaseResultMapId()));

	        context.getCommentGenerator().addComment(answer);

	        StringBuilder sb = new StringBuilder();
	        sb.append("select "); //$NON-NLS-1$   
	        answer.addElement(new TextElement(sb.toString()));
			answer.addElement(getBaseColumnListElement());
	        sb.setLength(0);
	        sb.append("from "); //$NON-NLS-1$
	        sb.append(introspectedTable
	                .getAliasedFullyQualifiedTableNameAtRuntime());
	        answer.addElement(new TextElement(sb.toString()));
	        
	        String orderByClause = introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
	        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
	        if (hasOrderBy) {
	            sb.setLength(0);
	            sb.append("order by "); //$NON-NLS-1$
	            sb.append(orderByClause);
	            answer.addElement(new TextElement(sb.toString()));
	        }

	        
	        parentElement.addElement(answer);
	}

}
