package org.mybatis.generator.codegen.mybatis3.service.element.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;

public class QueryByConditionMethodGenerator extends AbstractJavaRPCMethodGenerator {
	
	public QueryByConditionMethodGenerator(){
		super();
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
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.Page"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
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
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.Page"));
		topLevelClass.addImportedType(introspectedTable.getBaseConditionType());
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Arrays"));
	}

	@Override
	public void addMethodElements(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.introspectedTable.getMethodFactory().
				getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
		List<String> bodyLines = this.introspectedTable.getMethodFactory().getMethod(
				this.getClass().getSimpleName());
		method.addBodyLines(bodyLines);
		
		addOverrideAnnotation(method);

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.Page"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageHelper"));
		topLevelClass.addMethod(method);
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.introspectedTable.getMethodFactory().
				getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.Page"));
		interfaze.addMethod(method);
	}

}
