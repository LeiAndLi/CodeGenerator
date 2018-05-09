package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class ConditionUpdateElementGenerator extends AbstractXmlElementGenerator{
	
	
	public final static String ID="UPDATE_CONDITION";

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("id", ID)); //$NON-NLS-1$
		
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			

			if(context.getIgnoreColumnList().isColumnIgnored(introspectedColumn.getActualColumnName())){
				continue;
			}
			
			XmlElement test = buildIfElement(introspectedColumn.getJavaProperty(), introspectedTable.getSelectByCondtionParamName());
			addBasicField(introspectedColumn, test);
			answer.addElement(test);
			
			XmlElement testList = buildIfListElement(introspectedColumn.getJavaPropertySubfix("List"), introspectedTable.getSelectByCondtionParamName());
			answer.addElement(testList);
			StringBuffer sb = new StringBuffer();
			String colName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
			sb.append(" and ");
			sb.append(colName);
			sb.append(" in ");
			testList.addElement(new TextElement(sb.toString()));
			
			//String prop = MyBatis3FormattingUtilities.getParameterClause(column)
			XmlElement foreach = getForeachElement(",", introspectedTable.getSelectByCondtionParamName()+"."+introspectedColumn.getJavaPropertySubfix("List"), true);
			testList.addElement(foreach);
			addListField(introspectedColumn, foreach);	
			
			
		}
		
		parentElement.addElement(answer);
	}
	
	protected void addBasicField(IntrospectedColumn column, XmlElement last){
		StringBuffer sb = new StringBuffer();
		sb.append("and ");
		sb.append(MyBatis3FormattingUtilities
                .getAliasedEscapedColumnName(column));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(MyBatis3FormattingUtilities
                .getParameterClause(column, introspectedTable.getSelectByCondtionParamName()+"."));
        
        
        last.addElement(new TextElement(sb.toString()));
	}
	
	protected void addListField(IntrospectedColumn column, XmlElement last){
		StringBuffer sb = new StringBuffer();
        if(column.isSecurity()){
        	sb.append("aes_encrypt(#{item, jdbcType="+column.getSecurityJdbcType()+"},#{secretKey,jdbcType=VARCHAR})");
        }else{
        	sb.append("#{item}");
        }
         
        
        last.addElement(new TextElement(sb.toString()));
	}

}
