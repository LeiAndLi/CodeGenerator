package org.mybatis.generator.codegen.mybatis3.service.element.service;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;

public class selectAllMethodGenerator  extends AbstractJavaRPCMethodGenerator{

	@Override
	public void addRPCMethod(TopLevelClass topLevelClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRPCTestMethod(TopLevelClass topLevelClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMethodElements(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.getMethodInterface(importedTypes);
		method.addBodyLines(this.introspectedTable.getMethodFactory().getMethod(this.getClass().getSimpleName()));
		
		addOverrideAnnotation(method);

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}
	
	protected Method getMethodInterface(Set<FullyQualifiedJavaType> importedTypes) {
		
		Method method = this.introspectedTable.getMethodFactory().
				getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
		return method;
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.getMethodInterface(importedTypes);

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

}
