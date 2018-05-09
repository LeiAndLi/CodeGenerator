package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

public class BatchInsertMethodGenerator extends AbstractJavaMapperMethodGenerator {

	public BatchInsertMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();

		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(introspectedTable.getBatchInsertStatementId());
		addBatchMethodParameter(method, importedTypes);

		addMapperAnnotations(interfaze, method);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
		String tableName = this.introspectedTable.getTableConfiguration().getTableName();
    	String sql = "insert into " + tableName + " ";
    	sql += getColumnNameSql();
    	sql += " values ";
    	sql += getColumnTabSql();
    	method.addJavaDocLine("/*");
    	method.addJavaDocLine("*" + sql);
    	method.addJavaDocLine("*/");
	}

}
