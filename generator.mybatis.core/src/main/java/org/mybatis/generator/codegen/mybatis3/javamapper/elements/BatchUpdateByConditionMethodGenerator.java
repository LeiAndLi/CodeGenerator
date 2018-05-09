package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class BatchUpdateByConditionMethodGenerator extends AbstractJavaMapperMethodGenerator{

	@Override
	public void addInterfaceElements(Interface interfaze) {
		// TODO Auto-generated method stub
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
	   

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(introspectedTable
                .getBatchUpdateByConditionStatementId());
       
	    this.addListBasePojoParam(importedTypes, method);  
        this.addPojoConditionParam(importedTypes, method);
        
        method.addJavaDocLine("/**");
        method.addJavaDocLine("*");
        method.addJavaDocLine("*/");
        
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
	}

}
