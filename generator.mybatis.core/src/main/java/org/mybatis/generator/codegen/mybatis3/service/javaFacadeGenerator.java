package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeBatchCreateMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeBatchDeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeBatchModifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeCreateMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeDeleteByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeDeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeModifyByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.facade.FacadeModifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToDoListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToDoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToVoListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.TransformToVoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.ValidateVoListMethodGenrator;
import org.mybatis.generator.codegen.mybatis3.service.element.vo.ValidateVoMethodGenerator;
import org.mybatis.generator.config.file.javaFacadeGeneratorConfig;

public class javaFacadeGenerator extends AbstractJavaServiceGenerator {

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

		CommentGenerator commentGenerator = context.getCommentGenerator();
		javaFacadeGeneratorConfig config = context.getFacadeGeneratorConfig();
		String objectName = config.getFullClassType(introspectedTable);

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
		Interface interfaze = new Interface(type);
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(interfaze);
		commentGenerator.addInterfaceComment(interfaze);

		addImportList(config.getImportConfigs(), interfaze);
		
		AbstractJavaMapperMethodGenerator m1 = new FacadeCreateMethodGenerator();
		initializeAndExecuteGenerator(m1, interfaze);
		
		AbstractJavaMapperMethodGenerator m2 = new FacadeModifyMethodGenerator();
		initializeAndExecuteGenerator(m2, interfaze);
		
		AbstractJavaMapperMethodGenerator m3 = new FacadeDeleteMethodGenerator();
		initializeAndExecuteGenerator(m3, interfaze);
		
		AbstractJavaMapperMethodGenerator m4 = new FacadeBatchCreateMethodGenerator();
		initializeAndExecuteGenerator(m4, interfaze);
		
		AbstractJavaMapperMethodGenerator m5 = new FacadeBatchModifyMethodGenerator();
		initializeAndExecuteGenerator(m5, interfaze);
		
		AbstractJavaMapperMethodGenerator m6 = new FacadeBatchDeleteMethodGenerator();
		initializeAndExecuteGenerator(m6, interfaze);
		
		AbstractJavaMapperMethodGenerator m7 = new FacadeModifyByConditionMethodGenerator();
		initializeAndExecuteGenerator(m7, interfaze);
		
		AbstractJavaMapperMethodGenerator m8 = new FacadeDeleteByConditionMethodGenerator();
		initializeAndExecuteGenerator(m8, interfaze);
		
		interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		

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
