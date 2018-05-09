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
import org.mybatis.generator.codegen.mybatis3.service.element.dto.FillContextMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.dto.FillListContextMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.dto.ToPlanListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.dto.ToPlanMethodGenerator;
import org.mybatis.generator.config.file.JavaDTOAdapterGeneratorConfig;

public class DTOAdapterGenerator extends AbstractJavaServiceGenerator{
    
    public DTOAdapterGenerator(){
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        CommentGenerator commentGenerator = context.getCommentGenerator();
        JavaDTOAdapterGeneratorConfig config = context.getDtoAdapterGeneratorConfig();
        String objectName = config.getFullClassType(introspectedTable);

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        commentGenerator.addInterfaceComment(interfaze);

        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        addImportList(config.getImportConfigs(), interfaze);
        
        AbstractJavaMapperMethodGenerator m1 = new FillContextMethodGenerator();
        initializeAndExecuteGenerator(m1, interfaze);
        
        AbstractJavaMapperMethodGenerator m2 = new FillListContextMethodGenerator();
        initializeAndExecuteGenerator(m2, interfaze);
        
        AbstractJavaMapperMethodGenerator m3 = new ToPlanMethodGenerator();
        initializeAndExecuteGenerator(m3, interfaze);
        
        AbstractJavaMapperMethodGenerator m4 = new ToPlanListMethodGenerator();
        initializeAndExecuteGenerator(m4, interfaze);
        
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

   
}
