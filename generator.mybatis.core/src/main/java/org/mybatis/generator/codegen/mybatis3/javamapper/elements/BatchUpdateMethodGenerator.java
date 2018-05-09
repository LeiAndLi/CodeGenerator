package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class BatchUpdateMethodGenerator extends AbstractJavaMapperMethodGenerator {

	public BatchUpdateMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName(introspectedTable.getBatchUpdateStatementId());
		addBatchMethodParameter(method, importedTypes);


		addMapperAnnotations(interfaze, method);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
		String tableName = this.introspectedTable.getTableConfiguration().getTableName();
    	String sql = "update " + tableName + " set ";
    	sql += getColumnUpdate();
    	sql += " where id=\"\" ";
    	method.addJavaDocLine("/*");
    	method.addJavaDocLine("*" + sql);
    	method.addJavaDocLine("*/");
	}

}
