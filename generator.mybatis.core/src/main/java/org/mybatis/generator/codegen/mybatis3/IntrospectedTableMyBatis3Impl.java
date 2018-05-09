/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaExtMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ConditionRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.service.DTOAdapterGenerator;
import org.mybatis.generator.codegen.mybatis3.service.DTOAdapterImplGenerator;
import org.mybatis.generator.codegen.mybatis3.service.DTOParamGenerator;
import org.mybatis.generator.codegen.mybatis3.service.JavaServiceImplGenerator;
import org.mybatis.generator.codegen.mybatis3.service.JavaServiceInterfaceGenerator;
import org.mybatis.generator.codegen.mybatis3.service.JavaVoAdapterGenerator;
import org.mybatis.generator.codegen.mybatis3.service.JavaVoAdapterImplGenerator;
import org.mybatis.generator.codegen.mybatis3.service.JavaVoGenerator;
import org.mybatis.generator.codegen.mybatis3.service.javaFacadeGenerator;
import org.mybatis.generator.codegen.mybatis3.service.javaFacadeImplGenerator;
import org.mybatis.generator.codegen.mybatis3.service.javaRpcGenerator;
import org.mybatis.generator.codegen.mybatis3.service.javaRpcTestGenerator;
import org.mybatis.generator.codegen.mybatis3.template.AbstractTemplateGenerator;
import org.mybatis.generator.codegen.mybatis3.template.TemplateUserdefGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.otherjavaGeneratorConfiguration;
import org.mybatis.generator.config.file.JavaFacadeImplGeneratorConfig;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.UserdefGeneratorConfig;
import org.mybatis.generator.internal.ObjectFactory;

/**
 * The Class IntrospectedTableMyBatis3Impl.
 *
 * @author Jeff Butler
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {
    
    /** The java model generators. */
    protected List<AbstractJavaGenerator> javaModelGenerators;
    
    /** The client generators. */
    protected List<AbstractJavaGenerator> clientGenerators;
    
    /** The xml mapper generators. */
    protected List<AbstractXmlGenerator> xmlMapperGenerators;
    
    
    protected JavaVoGenerator voGenerator;
    
    protected JavaServiceInterfaceGenerator servInterfaceGenerator;
    
    protected JavaServiceImplGenerator servImplGenerator;
    
    protected javaRpcGenerator rpcGenerator;
    
    protected javaFacadeGenerator facadeGenerator;
    
    protected javaFacadeImplGenerator facadeImplGenerator;
    
    protected javaRpcTestGenerator rpcTestGenerator;
    
    
   
    protected List<TemplateUserdefGenerator> userdefGenrators;
    
    protected JavaVoAdapterGenerator voAdapterGenerator;
    
    protected JavaVoAdapterImplGenerator voAdapterImplGenerator;
    
    protected DTOParamGenerator dtoParamGenerator;
    
    protected DTOAdapterGenerator dtoAdapterGenerator;
    
    protected DTOAdapterImplGenerator dtoAdapterImplGenerator;

    /**
     * Instantiates a new introspected table my batis3 impl.
     */
    public IntrospectedTableMyBatis3Impl() {
        super(TargetRuntime.MYBATIS3);
        javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
        clientGenerators = new ArrayList<AbstractJavaGenerator>();
        xmlMapperGenerators = new ArrayList<AbstractXmlGenerator>();
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.IntrospectedTable#calculateGenerators(java.util.List, org.mybatis.generator.api.ProgressCallback)
     */
    @Override
    public void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        calculateJavaModelGenerators(warnings, progressCallback);
        
        calculateJavaServiceGenerator(warnings, progressCallback);
        
        List<AbstractJavaClientGenerator> javaClientGeneratorList =
            calculateClientGenerators(warnings, progressCallback);
        
        if(javaClientGeneratorList != null){
            for(AbstractJavaClientGenerator javaClientGenerator : javaClientGeneratorList){
                calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
            }
        }
       
        
        caculateUserDefGenerator(warnings, progressCallback);
    }

    /**
     * Calculate xml mapper generator.
     *
     * @param javaClientGenerator
     *            the java client generator
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, 
            List<String> warnings,
            ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                AbstractXmlGenerator xmlMapperGenerator = new XMLMapperGenerator();
                initializeAbstractGenerator(xmlMapperGenerator, warnings,
                    progressCallback);
                this.xmlMapperGenerators.add(xmlMapperGenerator);
            }
        } else {
            AbstractXmlGenerator xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
            initializeAbstractGenerator(xmlMapperGenerator, warnings,
                progressCallback);
            this.xmlMapperGenerators.add(xmlMapperGenerator);
        }
        
    }
    
    protected void caculateUserDefGenerator(List<String> warnings,
            ProgressCallback progressCallback){
    	
    	if(context.getUserdefGeneratorConfigs() != null){
    		this.userdefGenrators = new ArrayList<TemplateUserdefGenerator>();
    		for(UserdefGeneratorConfig config : this.context.getUserdefGeneratorConfigs()){
    			this.userdefGenrators.add(new TemplateUserdefGenerator(config));
    		}
    	}
    }
    
    

    /**
     * Calculate client generators.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     * @return true if an XML generator is required
     */
    protected List<AbstractJavaClientGenerator> calculateClientGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (!rules.generateJavaClient()) {
            return null;
        }
        
        List<AbstractJavaClientGenerator> javaGeneratorList = createJavaClientGenerator();
        if (javaGeneratorList == null || javaGeneratorList.isEmpty()) {
            return null;
        }
        
        for(AbstractJavaClientGenerator javaGenerator : javaGeneratorList){
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            clientGenerators.add(javaGenerator);
        }
        
        
        return javaGeneratorList;
    }
    
    /**
     * Creates the java client generator.
     *
     * @return the abstract java client generator
     */
    protected List<AbstractJavaClientGenerator> createJavaClientGenerator() {
       
        
        List<AbstractJavaClientGenerator> rt = new ArrayList<AbstractJavaClientGenerator>();
        
        String type = context.getJavaClientGeneratorConfiguration()
                .getConfigurationType();
        
        if (context.getJavaClientGeneratorConfiguration() != null && 
                context.getJavaClientGeneratorConfiguration().isGenerate()) {
            if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
                AbstractJavaClientGenerator javaGenerator = new JavaMapperGenerator();
                rt.add(javaGenerator);
                
            } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
                AbstractJavaClientGenerator javaGenerator  = new MixedClientGenerator();
                rt.add(javaGenerator);
                
            } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
                AbstractJavaClientGenerator javaGenerator = new AnnotatedClientGenerator();
                rt.add(javaGenerator);
                
            } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
                AbstractJavaClientGenerator javaGenerator = new JavaMapperGenerator();
                rt.add(javaGenerator);
                
            } else {
                AbstractJavaClientGenerator javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                        .createInternalObject(type);
                rt.add(javaGenerator);
                
            }
        }
        
        if(context.getJavaClientExtGeneratorConfiguration() != null
            && context.getJavaClientExtGeneratorConfiguration().isGenerate()){
            
            if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
                
                AbstractJavaClientGenerator javaGeneratorExt = new JavaExtMapperGenerator();
                rt.add(javaGeneratorExt);
                
            }  else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
                AbstractJavaClientGenerator javaGeneratorExt = new JavaExtMapperGenerator();
                rt.add(javaGeneratorExt);
                
            } 
        }

        
        
        
        
        return rt;
    }

    /**
     * Calculate java model generators.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
    	if(!context.getJavaModelGeneratorConfiguration().isGenerate()){
    		return;
    	}
    	
    	
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }
        
        if (getRules().generateCondition()) {
        	AbstractJavaGenerator javaGenerator = new ConditionRecordGenerator();
        	initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }
    }
    
    protected void calculateJavaServiceGenerator(List<String> warnings,
            ProgressCallback progressCallback){
    	
    	if(context.getVoGeneratorConfig() != null && context.getVoGeneratorConfig().isGenerate()){
    		JavaVoGenerator voGenerator = new JavaVoGenerator();
    		initializeAbstractGenerator(voGenerator, warnings,
                     progressCallback);
    		this.voGenerator = voGenerator;
    	}
    	
    	if(context.getServiceGeneratorConfig() != null && context.getServiceGeneratorConfig().isGenerate()){
    		JavaServiceInterfaceGenerator interfaceGenerator = new JavaServiceInterfaceGenerator();
    		initializeAbstractGenerator(interfaceGenerator, warnings,
                     progressCallback);
    		this.servInterfaceGenerator = interfaceGenerator;
    	}
    	
    	if(context.getServImplGeneratorConfig() != null){
    		JavaServiceImplGenerator implGenerator = new JavaServiceImplGenerator();
    		initializeAbstractGenerator(implGenerator, warnings,
                     progressCallback);
    		this.servImplGenerator = implGenerator;
    	}
    	
    	if(context.getRpcGenratorConfig() != null){
    		javaRpcGenerator  generator = new javaRpcGenerator();
    		initializeAbstractGenerator(generator, warnings,
                    progressCallback);
    		this.rpcGenerator = generator;
    	}
    	
    	
    	if(context.getVoAdapterGeneratorConfig() != null && context.getVoAdapterGeneratorConfig().isGenerate()){
    	    JavaVoAdapterGenerator generator = new JavaVoAdapterGenerator();
    	    initializeAbstractGenerator(generator, warnings,
                progressCallback);
    	    this.voAdapterGenerator = generator;
    	}
    	
    	if(context.getVoAdapterImplementGeneratorConfig() != null){
    	    JavaVoAdapterImplGenerator generator = new JavaVoAdapterImplGenerator();
    	    initializeAbstractGenerator(generator, warnings,
                progressCallback);
    	    this.voAdapterImplGenerator = generator;
    	}
    	
    	if(context.getDtoParamGeneratorConfig() != null && context.getDtoParamGeneratorConfig().isGenerate()){
    	    DTOParamGenerator generator = new DTOParamGenerator();
    	    initializeAbstractGenerator(generator, warnings,
                progressCallback);
    	    this.dtoParamGenerator = generator;
    	}
    	
    	if(context.getDtoAdapterGeneratorConfig() != null && context.getDtoAdapterGeneratorConfig().isGenerate()){
    	    DTOAdapterGenerator generator = new DTOAdapterGenerator();
    	    initializeAbstractGenerator(generator, warnings,
                progressCallback);
    	    this.dtoAdapterGenerator = generator;
    	}
    	
    	if(context.getDtoAdapterImplementGeneratorConfig() != null){
    	    DTOAdapterImplGenerator generator = new DTOAdapterImplGenerator();
    	    initializeAbstractGenerator(generator, warnings,
                progressCallback);
    	    this.dtoAdapterImplGenerator = generator;
    	}
    	
    	if(context.getFacadeGeneratorConfig() != null && context.getFacadeGeneratorConfig().isGenerate()){
    		javaFacadeGenerator  generator = new javaFacadeGenerator();
    		initializeAbstractGenerator(generator, warnings,
                    progressCallback);
    		this.facadeGenerator = generator;
    	}
    	
    	if(context.getFacadeImplGeneratorConfig() != null){
    		javaFacadeImplGenerator generator = new javaFacadeImplGenerator();
    		initializeAbstractGenerator(generator, warnings,
                    progressCallback);
    		this.facadeImplGenerator = generator;
    	}
    	
    	if(context.getRpcTestGeneratorConfig() != null){
    		javaRpcTestGenerator generator = new javaRpcTestGenerator();
    		initializeAbstractGenerator(generator, warnings,
                    progressCallback);
    		this.rpcTestGenerator = generator;
    	}
    	
    }

    /**
     * Initialize abstract generator.
     *
     * @param abstractGenerator
     *            the abstract generator
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void initializeAbstractGenerator(
            AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.IntrospectedTable#getGeneratedJavaFiles()
     */
    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
        
        

        for (AbstractJavaGenerator javaGenerator : javaModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaModelGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        for (AbstractJavaGenerator javaGenerator : clientGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaClientGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        
        if(this.voGenerator != null){
        	List<CompilationUnit> compilationUnits = this.voGenerator.getCompilationUnits();
        	for (CompilationUnit compilationUnit : compilationUnits) {
        		GeneratedJavaFile gjf = new GeneratedJavaFile(
        				compilationUnit,
        				context.getVoGeneratorConfig().getTargetProject(),
        				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
        				);
        		answer.add(gjf);
        	}
        }
        
        if(this.servInterfaceGenerator != null){
        	List<CompilationUnit> compilationUnits = this.servInterfaceGenerator.getCompilationUnits();
        	for (CompilationUnit compilationUnit : compilationUnits) {
        		GeneratedJavaFile gjf = new GeneratedJavaFile(
        				compilationUnit,
        				context.getServiceGeneratorConfig().getTargetProject(),
        				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
        				);
        		answer.add(gjf);
        	}
        }
        
        if(this.servImplGenerator != null){
        	List<CompilationUnit> compilationUnits = this.servImplGenerator.getCompilationUnits();
        	for (CompilationUnit compilationUnit : compilationUnits) {
        		GeneratedJavaFile gjf = new GeneratedJavaFile(
        				compilationUnit,
        				context.getServImplGeneratorConfig().getTargetProject(),
        				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
        				);
        		answer.add(gjf);
        	}
        }
        
        if(this.rpcGenerator != null){
        	List<CompilationUnit> compilationUnits = this.rpcGenerator.getCompilationUnits();
        	for (CompilationUnit compilationUnit : compilationUnits) {
        		GeneratedJavaFile gjf = new GeneratedJavaFile(
        				compilationUnit,
        				context.getRpcGenratorConfig().getTargetProject(),
        				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
        				);
        		answer.add(gjf);
        	}
        }
        
        if(this.voAdapterGenerator != null){
            List<CompilationUnit> compilationUnits = this.voAdapterGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(
                        compilationUnit,
                        context.getVoAdapterGeneratorConfig().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
                        );
                answer.add(gjf);
            }
        }
        
        if(this.voAdapterImplGenerator != null){
            List<CompilationUnit> compilationUnits = this.voAdapterImplGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(
                        compilationUnit,
                        context.getVoAdapterImplementGeneratorConfig().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
                        );
                answer.add(gjf);
            }
        }
        
        //是否历史表
        boolean isPlan = this.getTableConfiguration().isPlan();
        
        //主表上生成如下内容
        if(!isPlan){
            if(this.dtoParamGenerator != null){
                List<CompilationUnit> compilationUnits = this.dtoParamGenerator.getCompilationUnits();
                for (CompilationUnit compilationUnit : compilationUnits) {
                    GeneratedJavaFile gjf = new GeneratedJavaFile(
                            compilationUnit,
                            context.getDtoParamGeneratorConfig().getTargetProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                            context.getJavaFormatter()
                            );
                    answer.add(gjf);
                } 
            }
            
            if(this.dtoAdapterGenerator != null){
                List<CompilationUnit> compilationUnits = this.dtoAdapterGenerator.getCompilationUnits();
                for (CompilationUnit compilationUnit : compilationUnits) {
                    GeneratedJavaFile gjf = new GeneratedJavaFile(
                            compilationUnit,
                            context.getDtoAdapterGeneratorConfig().getTargetProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                            context.getJavaFormatter()
                            );
                    answer.add(gjf);
                } 
            }
            
            if(this.dtoAdapterImplGenerator != null){
                List<CompilationUnit> compilationUnits = this.dtoAdapterImplGenerator.getCompilationUnits();
                for (CompilationUnit compilationUnit : compilationUnits) {
                    GeneratedJavaFile gjf = new GeneratedJavaFile(
                            compilationUnit,
                            context.getDtoAdapterImplementGeneratorConfig().getTargetProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                            context.getJavaFormatter()
                            );
                    answer.add(gjf);
                } 
            }
            
            if(this.facadeGenerator != null){
                List<CompilationUnit> compilationUnits = this.facadeGenerator.getCompilationUnits();
                for (CompilationUnit compilationUnit : compilationUnits) {
                    GeneratedJavaFile gjf = new GeneratedJavaFile(
                            compilationUnit,
                            context.getFacadeGeneratorConfig().getTargetProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                            context.getJavaFormatter()
                            );
                    answer.add(gjf);
                }
            }
            
            if(this.facadeImplGenerator != null){
                List<CompilationUnit> compilationUnits = this.facadeImplGenerator.getCompilationUnits();
                for (CompilationUnit compilationUnit : compilationUnits) {
                    GeneratedJavaFile gjf = new GeneratedJavaFile(
                            compilationUnit,
                            context.getFacadeImplGeneratorConfig().getTargetProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                            context.getJavaFormatter()
                            );
                    answer.add(gjf);
                }
            }
        }
        
        if(this.rpcTestGenerator != null){
        	List<CompilationUnit> compilationUnits =  this.rpcTestGenerator.getCompilationUnits();
        	for (CompilationUnit compilationUnit : compilationUnits) {
        		GeneratedJavaFile gjf = new GeneratedJavaFile(
        				compilationUnit,
        				context.getRpcTestGeneratorConfig().getTargetProject(),
        				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()
        				);
        		answer.add(gjf);
        	}
        }
        
        if(this.userdefGenrators != null){
        	for(TemplateUserdefGenerator generator : this.userdefGenrators){
        		generator.renderFiles();
        	}
        }

        return answer;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.IntrospectedTable#getGeneratedXmlFiles()
     */
    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();
        
        if(this.xmlMapperGenerators != null && !this.xmlMapperGenerators.isEmpty()){
            for(AbstractXmlGenerator xmlMapperGenerator : this.xmlMapperGenerators){
                if (xmlMapperGenerator != null) {
                    Document document = xmlMapperGenerator.getDocument();
                    GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                        xmlMapperGenerator.getFileName(), getMyBatis3XmlMapperPackage(),
                        context.getSqlMapGeneratorConfiguration().getTargetProject(),
                        true, context.getXmlFormatter());
                    if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                        answer.add(gxf);
                    }
                }
            }
        }

        

        return answer;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.IntrospectedTable#getGenerationSteps()
     */
    @Override
    public int getGenerationSteps() {
        
        int step =javaModelGenerators.size() 
        		+ clientGenerators.size()
        		+ xmlMapperGenerators.size()
        		+ (voGenerator == null ? 0 : 1)
        		+ (servInterfaceGenerator == null ? 0 : 1)
        		+ (servImplGenerator == null ? 0 : 1)
        		+ (rpcGenerator == null ? 0 : 1)
        		+ (this.voAdapterGenerator == null ? 0 : 1)
        		+ (this.voAdapterImplGenerator == null ? 0 : 1)
        		+ (this.dtoParamGenerator == null ? 0 : 1)
        		+ (this.dtoAdapterGenerator == null ? 0 : 1)
        		+ (this.dtoAdapterImplGenerator == null ? 0 : 1)
        		+ (this.facadeGenerator == null ? 0 : 1)
        		+ (this.facadeImplGenerator == null ? 0 : 1)
        		+ (this.rpcTestGenerator == null ? 0 : 1)
        		+ (this.userdefGenrators == null ? 0 : this.userdefGenrators.size());
        

        
        return step;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.IntrospectedTable#isJava5Targeted()
     */
    @Override
    public boolean isJava5Targeted() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.IntrospectedTable#requiresXMLGenerator()
     */
    @Override
    public boolean requiresXMLGenerator() {
        List<AbstractJavaClientGenerator> javaClientGeneratorList =
            createJavaClientGenerator();
        
        if (javaClientGeneratorList == null || javaClientGeneratorList.isEmpty()) {
            return false;
        } else {
            return javaClientGeneratorList.get(0).requiresXMLGenerator();
        }
    }
}
