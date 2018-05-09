package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.ColumnAdd;
import org.mybatis.generator.config.file.javaVoGeneratorConfig;

public class JavaVoGenerator extends AbstractJavaServiceGenerator {

	public JavaVoGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		CommentGenerator commentGenerator = context.getCommentGenerator();

		javaVoGeneratorConfig config = context.getVoGeneratorConfig();
		String voObjectName = config.getFullClassType(introspectedTable);

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(voObjectName);
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

		FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		topLevelClass.setSuperClass(superClass);
		topLevelClass.addImportedType(superClass);
		addImportList(config.getImportConfigs(), topLevelClass);

		List<ColumnAdd> addList = config.getAddColumns();
		List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getAllColumns();

		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			String columnName = introspectedColumn.getActualColumnName();

			for (ColumnAdd col : addList) {
				if (columnName.endsWith(col.getDbSubfix())) {
					addMember(topLevelClass, col, introspectedColumn);

					Method get = this.getJavaBeansGetter(introspectedColumn, col);
					topLevelClass.addMethod(get);

					Method set = this.getJavaBeansSetter(introspectedColumn, col);
					topLevelClass.addMethod(set);
				}
			}

		}

		List<Method> methods = topLevelClass.getMethods();
		if(methods != null){
			for(Method method : methods){
				addMethodAnnotation(config.getAnnotations(), method);
				addMethodParam(config.getMethodParamConfigs(), method);
			}
		}
		
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		answer.add(topLevelClass);
		return answer;
	}

	protected void addMember(TopLevelClass topLevelClass, ColumnAdd col, IntrospectedColumn introspectedColumn) {
		Field f = getField(col, introspectedColumn);
		topLevelClass.addField(f);
	}

	protected Field getField(ColumnAdd col, IntrospectedColumn introspectedColumn) {
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(col.getType());
		String name = col.getFieldName(introspectedColumn);

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(fqjt);
		field.setName(name);

		field.addJavaDocLine("/*");
		field.addJavaDocLine(introspectedColumn.getRemarks() + "Str");
		field.addJavaDocLine("*/");

		return field;
	}

	protected Method getJavaBeansGetter(IntrospectedColumn introspectedColumn, ColumnAdd addColumn) {
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(addColumn.getType());
		String name = addColumn.getGetterName(introspectedColumn);

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(fqjt);
		method.setName(name);

		StringBuilder sb = new StringBuilder();
		sb.append("return "); //$NON-NLS-1$
		sb.append(addColumn.getFieldName(introspectedColumn));
		sb.append(';');
		method.addBodyLine(sb.toString());

		return method;
	}

	protected Method getJavaBeansSetter(IntrospectedColumn introspectedColumn, ColumnAdd addColumn) {
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(addColumn.getType());
		String name = addColumn.getSetterName(introspectedColumn);
		String field = addColumn.getFieldName(introspectedColumn);
		
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(name);
		method.addParameter(new Parameter(fqjt, field));

		StringBuilder sb = new StringBuilder();

		sb.append("this."); //$NON-NLS-1$
		sb.append(field);
		sb.append(" = "); //$NON-NLS-1$
		sb.append(field);
		sb.append(" == null ? null : "); //$NON-NLS-1$
		sb.append(field);
		sb.append(".trim();"); //$NON-NLS-1$
		method.addBodyLine(sb.toString());
		return method;
	}
}
