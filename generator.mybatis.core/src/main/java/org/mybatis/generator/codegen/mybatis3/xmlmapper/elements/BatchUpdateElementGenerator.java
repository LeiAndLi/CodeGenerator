package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class BatchUpdateElementGenerator extends AbstractXmlElementGenerator{
	
	public BatchUpdateElementGenerator(){
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		// TODO Auto-generated method stub
		XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getBatchUpdateStatementId())); //$NON-NLS-1$
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("java.util.List");
		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType.getFullyQualifiedName()));

		context.getCommentGenerator().addComment(answer);
		
		
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
        
        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "item."));
            wh.addElement(new TextElement(sb.toString()));
        }
        

        parentElement.addElement(answer);
	}

}
