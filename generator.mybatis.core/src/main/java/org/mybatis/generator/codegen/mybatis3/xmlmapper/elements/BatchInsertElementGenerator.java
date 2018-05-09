package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class BatchInsertElementGenerator extends AbstractXmlElementGenerator {

	public BatchInsertElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("insert");

		answer.addAttribute(new Attribute("id", this.introspectedTable.getBatchInsertStatementId()));

		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("java.util.List");
		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType.getFullyQualifiedName()));

		context.getCommentGenerator().addComment(answer);

		StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$
        
        XmlElement foreach = this.getForeachElement(",", "records", false);
        
        
        List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);

            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "item."));
            
            if (i + 1 < columns.size()) {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
            
        }
        
        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));
        answer.addElement(new TextElement("values "));
        answer.addElement(foreach);

        valuesClause.append("");
        valuesClauses.add(valuesClause.toString());

        foreach.addElement(new TextElement("("));
        for (String clause : valuesClauses) {
        	foreach.addElement(new TextElement(clause));
        }
        foreach.addElement(new TextElement(")"));
        
        parentElement.addElement(answer);
	}

}
