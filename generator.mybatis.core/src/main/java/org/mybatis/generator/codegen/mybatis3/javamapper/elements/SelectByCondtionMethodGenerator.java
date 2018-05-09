package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class SelectByCondtionMethodGenerator extends AbstractJavaMapperMethodGenerator {
	
	public SelectByCondtionMethodGenerator(){
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType;
        listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        method.setName(introspectedTable.getSelectByCondtionStatementId());
        
        String paramName = introspectedTable.getSelectByCondtionParamName();
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(introspectedTable.getBaseConditionType());
        
        method.addParameter(new Parameter(paramType, paramName));
        importedTypes.add(paramType);
        
        addJavaDoc(method);
        
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
	}
	
	protected void addJavaDoc(Method method){
		
		String tableName = this.introspectedTable.getTableConfiguration().getTableName();
    	String sql = "select * from " + tableName + " where 1=1 and is_deleted =\"n\" ";
    	String sql1 = sql + getColumnCondtion();
    	String sql2 = sql + getColumnCondtionList();
    	method.addJavaDocLine("/*");
    	method.addJavaDocLine("*" + sql1);
    	method.addJavaDocLine("*" + sql2);
    	method.addJavaDocLine("*/");
	}

}
