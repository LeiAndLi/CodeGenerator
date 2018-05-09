package org.mybatis.generator.codegen.mybatis3.service.element.vo;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaServiceMethodGenerator;
import org.mybatis.generator.config.file.JavaModelGeneratorConfiguration;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class ValidateVoMethodGenerator extends AbstractJavaServiceMethodGenerator{

	@Override
	public void addMethodElements(TopLevelClass topLevelClass) {
		
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.introspectedTable.getMethodFactory().
				getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
		method.addBodyLines(this.introspectedTable.getMethodFactory().getMethod(this.getClass().getSimpleName()));
		
		List<IntrospectedColumn> columList = this.introspectedTable.getAllColumns();
		for(IntrospectedColumn column : columList){
			if(context.getIgnoreColumnList().isColumnIgnored(column.getActualColumnName())){
				continue;
			}
			
			if(column.isNullable()){
				continue;
			}
			
			String property = column.getJavaProperty();
			FullyQualifiedJavaType fqjt = column.getFullyQualifiedJavaType();
			String getterName = JavaBeansUtil.getGetterMethodName(property, fqjt);
					
			StringBuffer sb = new StringBuffer();
			sb.append("//Assert.notNull(vo.");
			sb.append(getterName);
			sb.append("(),");
			sb.append("\"");
			sb.append(column.getRemarks());
			sb.append("can't empty");
			sb.append("\");");
			
			String line = sb.toString();
			method.addBodyLine(line);
			
		}
		
		
		addOverrideAnnotation(method);

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}


	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = this.introspectedTable.getMethodFactory().
				getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

}
