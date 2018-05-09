package org.mybatis.generator.codegen.mybatis3.service.element.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;

public class deleteMethodGenerator extends AbstractJavaRPCMethodGenerator {

	@Override
	public void addMethodElements(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.getMethodInterface(importedTypes);

		method.addBodyLines(this.introspectedTable.getMethodFactory().getMethod(this.getClass().getSimpleName()));
		
		addOverrideAnnotation(method);

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.getMethodInterface(importedTypes);

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}
	
	
	protected Method getMethodInterface(Set<FullyQualifiedJavaType> importedTypes) {
		Method method = this.introspectedTable.getMethodFactory().
				getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
		return method;
	}

	@Override
	public void addRPCMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		String tag = this.getClass().getSimpleName() + "RPC";
		Method method = this.introspectedTable.getMethodFactory().getMybatisMethod(tag, importedTypes);
		List<String> lines = this.introspectedTable.getMethodFactory().getMethod(tag);
		method.addBodyLines(lines);
		method.addAnnotation("@ResourceMapping");
		
		topLevelClass.addMethod(method);
		topLevelClass.addImportedTypes(importedTypes);
	}

	@Override
	public void addRPCTestMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		String ftag = this.getClass().getSimpleName() + "RPCTestFields";
		List<Field> flist = this.introspectedTable.getMethodFactory().getMybatisField(ftag, importedTypes);
		for(Field f : flist){
			topLevelClass.addField(f);
		}
		
		String dataProviderName = flist.get(0).getName();
		
		String tag = this.getClass().getSimpleName() + "RPCTest";
		Method method = this.introspectedTable.getMethodFactory().getMybatisMethod(tag, importedTypes);
		List<String> lines = this.introspectedTable.getMethodFactory().getMethod(tag);
		method.addBodyLines(lines);
		method.addAnnotation("@Ignore");
		method.addAnnotation("@Test");
		method.addAnnotation("@DataProvider(fieldName = \""+dataProviderName+"\")");
		
		method.addException(new FullyQualifiedJavaType("java.net.MalformedURLException"));
		
		topLevelClass.addMethod(method);
		topLevelClass.addImportedTypes(importedTypes);
	}

}
