package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.file.JavaDTOParamGeneratorConfig;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class DTOParamGenerator  extends AbstractJavaServiceGenerator {

    public DTOParamGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        JavaDTOParamGeneratorConfig config = context.getDtoParamGeneratorConfig();
        
        String paaramObjectName = config.getFullClassType(introspectedTable);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(paaramObjectName);
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
        
        //add logTag
        String fieldName = "logTag";
        String fieldType = "java.lang.String";
        this.addField(fieldName, fieldType, topLevelClass);
        
        //add isDoModify
        String modifyName = "isDoModify";
        String modifyType = "boolean";
        this.addField(modifyName, modifyType, topLevelClass);
        
        //add isDoDelete
        String deleteName = "isDoDelete";
        String deleteType = "boolean";
        this.addField(deleteName, deleteType, topLevelClass);
        this.addModifyDO(topLevelClass);
        
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(topLevelClass);
        commentGenerator.addClassComment(topLevelClass, introspectedTable);
     
        return answer;

    }
    
  
    
    private void addField(String fieldName, String fieldType, TopLevelClass topLevelClass){
        Field field = JavaBeansUtil.getJavaBeanField(null, fieldName, fieldType);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(field.getType());

        Method method = JavaBeansUtil.getJavaBeansGetter(null, fieldName, fieldType);
        topLevelClass.addMethod(method);
        Method setmthod = this.simpleSetter(fieldName, fieldType);
        topLevelClass.addMethod(setmthod);
    }
    
    private Method simpleSetter( String fieldName, String fieldType){
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(fieldType);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(JavaBeansUtil.getSetterMethodName(fieldName));
        method.addParameter(new Parameter(type, fieldName));

        StringBuilder sb = new StringBuilder();
        
        sb.append("this."); //$NON-NLS-1$
        sb.append(fieldName);
        sb.append(" = "); //$NON-NLS-1$
        sb.append(fieldName);
        sb.append(';');
        method.addBodyLine(sb.toString());
        
        DefaultCommentGenerator.addMethodEmptyComment(method);

        return method;
    }
    
    private void addModifyDO(TopLevelClass topLevelClass){
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
       
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("modifyDO");
        method.addParameter(new Parameter(type, "obj"));
        method.addBodyLine("//TODO");
        
        DefaultCommentGenerator.addMethodEmptyComment(method);
        topLevelClass.addMethod(method);
    }

}
