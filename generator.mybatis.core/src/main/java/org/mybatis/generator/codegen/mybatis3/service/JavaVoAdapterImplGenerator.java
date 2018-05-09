package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaServiceMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToDoListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToDoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToVoListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToVoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.ValidateVoListMethodGenrator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.ValidateVoMethodGenerator;
import org.mybatis.generator.config.file.JavaVOAdapterGeneratorConfig;
import org.mybatis.generator.config.file.JavaVOAdapterImplementGeneratorConfig;

public class JavaVoAdapterImplGenerator  extends AbstractJavaServiceGenerator{

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        CommentGenerator commentGenerator = context.getCommentGenerator();
        JavaVOAdapterImplementGeneratorConfig config = context.getVoAdapterImplementGeneratorConfig();
        String objectName = config.getFullClassType(introspectedTable);

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
        
        addSuperInterface(topLevelClass);
        addComponent(topLevelClass);
        addImportList(config.getImportConfigs(), topLevelClass);
        
     
        addTransformToVo(topLevelClass);
        addTransformToDo(topLevelClass);
        addTransformToVoList(topLevelClass);
        addTransformToDoList(topLevelClass);
        addValidateVo(topLevelClass);
        addValidateVoList(topLevelClass);
       
        
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.ArrayList"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.util.Assert"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.BeanUtils"));
        
        List<Method> methods = topLevelClass.getMethods();
        if(methods != null){
            for(Method method : methods){
                addMethodAnnotation(config.getAnnotations(), method);
                addMethodParam(config.getMethodParamConfigs(), method);
            }
        }
        
        answer.add(topLevelClass);
        return answer;
    }
    
    
    public void addSuperInterface(TopLevelClass topLevelClass){
        JavaVOAdapterGeneratorConfig config = context.getVoAdapterGeneratorConfig();
        String ObjectName = config.getFullClassType(introspectedTable);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(ObjectName);
        topLevelClass.addSuperInterface(type);
        topLevelClass.addImportedType(type);
    }
    
    public void addComponent(TopLevelClass topLevelClass){
        JavaVOAdapterGeneratorConfig config = context.getVoAdapterGeneratorConfig();
        String objectName = config.getLowShortClassName(introspectedTable);
        topLevelClass.addAnnotation("@Component(\""+objectName+"\")");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
    }
    
    
    protected void addTransformToVo(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new TransformToVoMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);
    }
    
    protected void addTransformToDo(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new TransformToDoMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);
    }
    
    protected void addTransformToVoList(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new TransformToVoListMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);
    }
    
    protected void addTransformToDoList(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new TransformToDoListMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);
    }
    
    protected void addValidateVo(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new ValidateVoMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);
    }
    
    protected void addValidateVoList(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new ValidateVoListMethodGenrator();
        initializeAndExecuteGenerator(method, topLevelClass);
    }
    
}
