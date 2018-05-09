package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class DeleteByConditionMethodGenerator extends AbstractJavaMapperMethodGenerator {

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getIntInstance();
        method.setReturnType(returnType);
        method.setName(introspectedTable.getDeleteByConditionStatementId());
        
        String paramName = introspectedTable.getSelectByCondtionParamName();
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(introspectedTable.getBaseConditionType());
        
        method.addParameter(new Parameter(paramType, paramName));
        importedTypes.add(paramType);
        
        method.addJavaDocLine("/**");
        method.addJavaDocLine("*");
        method.addJavaDocLine("*/");
        
        
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
	}

}
