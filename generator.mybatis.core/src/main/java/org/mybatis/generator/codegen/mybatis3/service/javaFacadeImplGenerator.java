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
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaServiceMethodGenerator;
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
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.file.JavaDTOAdapterGeneratorConfig;
import org.mybatis.generator.config.file.JavaFacadeImplGeneratorConfig;
import org.mybatis.generator.config.file.javaFacadeGeneratorConfig;
import org.mybatis.generator.config.file.javaServiceGeneratorConfig;
import org.springframework.util.Assert;

public class javaFacadeImplGenerator extends AbstractJavaServiceGenerator{

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

		CommentGenerator commentGenerator = context.getCommentGenerator();
		JavaFacadeImplGeneratorConfig config = context.getFacadeImplGeneratorConfig();
		String objectName = config.getFullClassType(introspectedTable);

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
		
		addSuperInterface(topLevelClass);
		addComponent(topLevelClass);
		addImportList(config.getImportConfigs(), topLevelClass);
		addResourceField(topLevelClass);
		
		AbstractJavaServiceMethodGenerator m1 = new FacadeCreateMethodGenerator();
		initializeAndExecuteGenerator(m1, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m2 = new FacadeModifyMethodGenerator();
        initializeAndExecuteGenerator(m2, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m3 = new FacadeDeleteMethodGenerator();
        initializeAndExecuteGenerator(m3, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m4 = new FacadeBatchCreateMethodGenerator();
        initializeAndExecuteGenerator(m4, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m5 = new FacadeBatchModifyMethodGenerator();
        initializeAndExecuteGenerator(m5, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m6 = new FacadeBatchDeleteMethodGenerator();
        initializeAndExecuteGenerator(m6, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m7 = new FacadeModifyByConditionMethodGenerator();
        initializeAndExecuteGenerator(m7, topLevelClass);
        
        AbstractJavaServiceMethodGenerator m8 = new FacadeDeleteByConditionMethodGenerator();
        initializeAndExecuteGenerator(m8, topLevelClass);
		
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.ArrayList"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.util.Assert"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.BeanUtils"));
		topLevelClass.addImportedType("java.util.List");
		
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
		javaFacadeGeneratorConfig config = context.getFacadeGeneratorConfig();
		String ObjectName = config.getFullClassType(introspectedTable);
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(ObjectName);
		topLevelClass.addSuperInterface(type);
		topLevelClass.addImportedType(type);
	}
	
	public void addComponent(TopLevelClass topLevelClass){
		javaFacadeGeneratorConfig config = context.getFacadeGeneratorConfig();
		String objectName = config.getLowShortClassName(introspectedTable);
		topLevelClass.addAnnotation("@Component(\""+objectName+"\")");
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
	}
	
	public void addResourceField(TopLevelClass topLevelClass){
	    String trsName = "transactionTemplate";
	    String trsType = "org.springframework.transaction.support.TransactionTemplate";
	    addResourceField(topLevelClass, trsName, trsType);
	    
	    javaServiceGeneratorConfig servCfg = context.getServiceGeneratorConfig();
	    if(servCfg != null){
	        String type = servCfg.getFullClassType(introspectedTable);
	        String name = servCfg.getLowShortClassName(introspectedTable); 
	        addResourceField(topLevelClass, name,type);
	        
	      //主表
	        TableConfiguration tc = introspectedTable.getTableConfiguration();
	        TableConfiguration ptc = tc.getPtc();
	        if(!tc.isPlan() && ptc != null){
	            String planDomainName = ptc.getDomainObjectName();
	            String ptype = servCfg.getFullClassType(planDomainName);
	            String pname = servCfg.getLowShortClassName(planDomainName);
	            addResourceField(topLevelClass, pname,ptype);
	        }
	    }
	    
	    JavaDTOAdapterGeneratorConfig dtoAdpCfg = context.getDtoAdapterGeneratorConfig();
	    if(dtoAdpCfg != null){
	        String type = dtoAdpCfg.getFullClassType(introspectedTable);
            String name = dtoAdpCfg.getLowShortClassName(introspectedTable);
            
            addResourceField(topLevelClass, name,type);
	    }
	    
	    

	    
	    
	    
	    
	}

}
