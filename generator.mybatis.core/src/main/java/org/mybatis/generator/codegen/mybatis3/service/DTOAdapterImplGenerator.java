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
import org.mybatis.generator.codegen.mybatis3.service.element.dto.FillContextMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.dto.FillListContextMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.dto.ToPlanListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.dto.ToPlanMethodGenerator;
import org.mybatis.generator.config.file.JavaDTOAdapterGeneratorConfig;
import org.mybatis.generator.config.file.JavaDTOAdapterImplementGeneratorConfig;

public class DTOAdapterImplGenerator extends AbstractJavaServiceGenerator{
    
    public DTOAdapterImplGenerator(){
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        CommentGenerator commentGenerator = context.getCommentGenerator();
        JavaDTOAdapterImplementGeneratorConfig config = context.getDtoAdapterImplementGeneratorConfig();
        String objectName = config.getFullClassType(introspectedTable);

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
        
        addSuperInterface(topLevelClass);
        addComponent(topLevelClass);
        addImportList(config.getImportConfigs(), topLevelClass);
        topLevelClass.addImportedType("java.util.List");
        topLevelClass.addImportedType("com.google.common.collect.Lists");
        
        AbstractJavaServiceMethodGenerator m1 = new FillContextMethodGenerator();
        initializeAndExecuteGenerator(m1, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m2 = new FillListContextMethodGenerator();
        initializeAndExecuteGenerator(m2, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m3 = new ToPlanMethodGenerator();
        initializeAndExecuteGenerator(m3, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m4 = new ToPlanListMethodGenerator();
        initializeAndExecuteGenerator(m4, topLevelClass);

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
        JavaDTOAdapterGeneratorConfig config = context.getDtoAdapterGeneratorConfig();
        String ObjectName = config.getFullClassType(introspectedTable);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(ObjectName);
        topLevelClass.addSuperInterface(type);
        topLevelClass.addImportedType(type);
    }
    
    public void addComponent(TopLevelClass topLevelClass){
        JavaDTOAdapterGeneratorConfig config = context.getDtoAdapterGeneratorConfig();
        String objectName = config.getLowShortClassName(introspectedTable);
        topLevelClass.addAnnotation("@Component(\""+objectName+"\")");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
    }
    
   

}
