package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class BatchDeleteElementGenerator extends AbstractXmlElementGenerator{
	
	public BatchDeleteElementGenerator(){
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getBatchDeleteStatementId())); //$NON-NLS-1$
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("java.util.List");
		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType.getFullyQualifiedName()));

		context.getCommentGenerator().addComment(answer);
		
		StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        
        XmlElement wh = new XmlElement("where");
        answer.addElement(wh);
        
        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
        	
        	if(and){
        		wh.addElement(new TextElement("and"));	
        	}else{
        		and = true;
        	}
        	
        	XmlElement test = buildIfListElement("records", null);
        	wh.addElement(test);
        	
        	String colName = MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn);
        	sb.setLength(0);
        	sb.append(colName);
        	sb.append(" in ");
        	
        	test.addElement(new TextElement(sb.toString()));
        	
        	XmlElement foreach = this.getForeachElement(",", "records", true);
        	foreach.addElement(new TextElement("#{item}"));
            test.addElement(foreach);           
        	
        	
        }
        

        
        parentElement.addElement(answer);
        
	}

}
