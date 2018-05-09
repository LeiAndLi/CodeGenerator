package org.mybatis.generator.codegen.mybatis3.model;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansSetter;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.file.JavaModelGeneratorConfiguration;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class ConditionRecordGenerator extends AbstractJavaGenerator{
	
	public ConditionRecordGenerator(){
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
	                introspectedTable.getBaseConditionType());
		TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        
        CommentGenerator commentGenerator = context.getCommentGenerator();
        
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            
        	if(context.getIgnoreColumnList().isColumnIgnored(introspectedColumn.getActualColumnName())){
				continue;
			}

            addFieldAndMethod(introspectedColumn, topLevelClass, commentGenerator);
            
            addListFieldAndMethod(introspectedColumn, topLevelClass, commentGenerator);
     
        }
        
        String fieldName = "secretKey";
        String fieldType = "java.lang.String";
        Field field = JavaBeansUtil.getJavaBeanField(null, fieldName, fieldType);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(field.getType());

        Method method = JavaBeansUtil.getJavaBeansGetter(null, fieldName, fieldType);
        topLevelClass.addMethod(method);
        Method setmthod = JavaBeansUtil.getJavaBeansSetter(null, fieldName, fieldType);
        topLevelClass.addMethod(setmthod);
        
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(topLevelClass);
        
        commentGenerator.addClassComment(topLevelClass, introspectedTable);
     
        return answer;
	}
	
	

}
