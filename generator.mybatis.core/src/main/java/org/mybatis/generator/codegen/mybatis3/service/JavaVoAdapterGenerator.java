package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToDoListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToDoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToVoListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToVoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.ValidateVoListMethodGenrator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.ValidateVoMethodGenerator;
import org.mybatis.generator.config.file.JavaVOAdapterGeneratorConfig;

public class JavaVoAdapterGenerator extends AbstractJavaServiceGenerator{

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        CommentGenerator commentGenerator = context.getCommentGenerator();
        JavaVOAdapterGeneratorConfig config = context.getVoAdapterGeneratorConfig();
        String objectName = config.getFullClassType(introspectedTable);

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        commentGenerator.addInterfaceComment(interfaze);

        addImportList(config.getImportConfigs(), interfaze);

        addTransformToVo(interfaze);
        addTransformToDo(interfaze);
        addTransformToVoList(interfaze);
        addTransformToDoList(interfaze);
        addValidateVo(interfaze);
        addValidateVoList(interfaze);

        List<Method> methods = interfaze.getMethods();
        if (methods != null) {
            for (Method method : methods) {
                addMethodAnnotation(config.getAnnotations(), method);
                addMethodParam(config.getMethodParamConfigs(), method);
            }
        }

        answer.add(interfaze);
        return answer;
    }

    protected void addTransformToVo(Interface interfaze) {
        AbstractJavaMapperMethodGenerator method = new TransformToVoMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }

    protected void addTransformToDo(Interface interfaze) {
        AbstractJavaMapperMethodGenerator method = new TransformToDoMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }

    protected void addTransformToVoList(Interface interfaze) {
        AbstractJavaMapperMethodGenerator method = new TransformToVoListMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }

    protected void addTransformToDoList(Interface interfaze) {
        AbstractJavaMapperMethodGenerator method = new TransformToDoListMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }

    protected void addValidateVo(Interface interfaze) {
        AbstractJavaMapperMethodGenerator method = new ValidateVoMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }

    protected void addValidateVoList(Interface interfaze) {
        AbstractJavaMapperMethodGenerator method = new ValidateVoListMethodGenrator();
        initializeAndExecuteGenerator(method, interfaze);
    }
}
