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
import org.mybatis.generator.codegen.mybatis3.service.element.service.BatchUpdateByConditionSrvMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.CountByConditionSrvMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.DeleteAllMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.DeleteByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByConditionAllMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByIdMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryCodeListByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryKVMapByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryMapByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryShortByConditionAllMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryShortByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.UpdateByConditionSrvMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchDeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchInsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchModifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.deleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.insertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.modifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.selectAllMethodGenerator;
import org.mybatis.generator.config.file.javaServiceGeneratorConfig;
import org.mybatis.generator.config.file.javaServiceImplementGeneratorConfig;

public class JavaServiceImplGenerator extends AbstractJavaServiceGenerator {

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

		CommentGenerator commentGenerator = context.getCommentGenerator();

		javaServiceImplementGeneratorConfig config = context.getServImplGeneratorConfig();
		String objectName = config.getFullClassType(introspectedTable);
		javaServiceGeneratorConfig service = context.getServiceGeneratorConfig();
		String componentName = service.getLowShortClassName(introspectedTable);

		javaServiceGeneratorConfig interConfig = context.getServiceGeneratorConfig();
		String interfaceName = interConfig.getFullClassType(introspectedTable);

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
		addComponent(topLevelClass, componentName);
		addImportList(config.getImportConfigs(), topLevelClass);
		
		
		if(config.hasRootClass()){
			topLevelClass.addImportedType(new FullyQualifiedJavaType(config.getRootClass()));
			topLevelClass.setSuperClass(config.getRootClass());
		}

		FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(interfaceName);
		topLevelClass.addSuperInterface(superClass);
		topLevelClass.addImportedType(superClass);
		
		String name = introspectedTable.getMyBatis3MapperName();
		String ftype = introspectedTable.getMyBatis3JavaMapperType();
		addResourceField(topLevelClass, name, ftype);
		
		//insert
		this.addInsertMethod(topLevelClass);
		if(introspectedTable.getRules().generateBatchInsert()){
            this.addBatchInsertMethod(topLevelClass, config.getRootClass());
        }
		
		
		//modify
		this.addModifyMethod(topLevelClass);
		if(introspectedTable.getRules().generateBatchUpdate()){
            this.addBatchModifyMethod(topLevelClass, config.getRootClass());
        }
		if(introspectedTable.getRules().generateCondition()){
		    this.addUpdateByConditionMethod(topLevelClass);
            this.addBatchUpdateByConditionMethod(topLevelClass); 
		}
		
		
		//delete
		this.addDeleteMethod(topLevelClass);
		if(introspectedTable.getRules().generateBatchDelete()){
            this.addBatchDeleteMethod(topLevelClass, config.getRootClass());
        }
		if(introspectedTable.getRules().generateCondition()){
		    this.addDeleteByConditionMethod(topLevelClass);
		    this.addDeleteAllMethod(topLevelClass);
		}	
		
		
		//count
		if(introspectedTable.getRules().generateCondition()){
		    this.addCountByConditionMethod(topLevelClass);
		}
		
		//query
		this.addSelectAllMethod(topLevelClass);
		this.addQueryByIdMethod(topLevelClass);
	
		if(introspectedTable.getRules().generateCondition()){
			this.addQueryByCondition(topLevelClass);
        	this.addQueryByConditionAllMethod(topLevelClass);	
		}
		
		//生成EXT文件
		if(this.context.getJavaClientExtGeneratorConfiguration() != null ){
		    
		    String extName = introspectedTable.getMyBatis3ExtMapperName();
	        String extFtype = introspectedTable.getMyBatis3JavaMapperExtType();
	        addResourceField(topLevelClass, extName, extFtype);
		    
		    this.addQueryShortByConditionMethod(topLevelClass);
            this.addQueryShortByConditionAllMethod(topLevelClass);
		}
		
		if(introspectedTable.getRules().generateCondition()){
		    this.addQueryMapByConditionMethod(topLevelClass);
            this.addQueryKVMapByConditionMethod(topLevelClass);
            this.addQueryCodeListByConditionMethod(topLevelClass);
		}
		
		
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

	protected void addQueryByIdMethod(TopLevelClass topLevelClass) {
		AbstractJavaServiceMethodGenerator method = new QueryByIdMethodGenerator();

		initializeAndExecuteGenerator(method, topLevelClass);
	}

	protected void addInsertMethod(TopLevelClass topLevelClass) {
		AbstractJavaServiceMethodGenerator method = new insertMethodGenerator();

		initializeAndExecuteGenerator(method, topLevelClass);
	}

	protected void addModifyMethod(TopLevelClass topLevelClass) {
		AbstractJavaServiceMethodGenerator method = new modifyMethodGenerator();

		initializeAndExecuteGenerator(method, topLevelClass);
	}

	protected void addDeleteMethod(TopLevelClass topLevelClass) {
		AbstractJavaServiceMethodGenerator method = new deleteMethodGenerator();

		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addBatchInsertMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaServiceMethodGenerator method = new batchInsertMethodGenerator(rootClass);

		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addBatchModifyMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaServiceMethodGenerator method = new batchModifyMethodGenerator(rootClass);

		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addBatchDeleteMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaServiceMethodGenerator method = new batchDeleteMethodGenerator(rootClass);

		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addQueryByCondition(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method = new QueryByConditionMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addSelectAllMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new selectAllMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addQueryByConditionAllMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new QueryByConditionAllMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addDeleteByConditionMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new DeleteByConditionMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addQueryShortByConditionMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new QueryShortByConditionMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addQueryShortByConditionAllMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new QueryShortByConditionAllMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addCountByConditionMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new CountByConditionSrvMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addUpdateByConditionMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new UpdateByConditionSrvMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addBatchUpdateByConditionMethod(TopLevelClass topLevelClass){
		AbstractJavaServiceMethodGenerator method  = new BatchUpdateByConditionSrvMethodGenerator();
		initializeAndExecuteGenerator(method, topLevelClass);
	}
	
	protected void addDeleteAllMethod(TopLevelClass topLevelClass){
	    AbstractJavaServiceMethodGenerator method = new DeleteAllMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);  
    }
	
	protected void addQueryMapByConditionMethod(TopLevelClass topLevelClass){
	    AbstractJavaServiceMethodGenerator method = new QueryMapByConditionMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);  
	}
	
	protected void addQueryKVMapByConditionMethod(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new QueryKVMapByConditionMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);  
    }
	
	protected void addQueryCodeListByConditionMethod(TopLevelClass topLevelClass){
        AbstractJavaServiceMethodGenerator method = new QueryCodeListByConditionMethodGenerator();
        initializeAndExecuteGenerator(method, topLevelClass);  
    }

}
