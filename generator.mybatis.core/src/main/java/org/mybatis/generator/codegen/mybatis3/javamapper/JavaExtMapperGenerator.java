package org.mybatis.generator.codegen.mybatis3.javamapper;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectShortByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.ExtXmlMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class JavaExtMapperGenerator extends AbstractJavaClientGenerator {

    public JavaExtMapperGenerator() {
        super(true);
    }

    public JavaExtMapperGenerator(boolean requiresMatchedXMLGenerator) {
        super(requiresMatchedXMLGenerator);
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        if(context.getSqlMapExtGeneratorConfiguration() != null){
            System.out.println("linan: hasConfig");
            return new ExtXmlMapperGenerator();
        }
        
        return null;
        
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
            introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
            introspectedTable.getMyBatis3JavaMapperExtType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        commentGenerator.addInterfaceComment(interfaze);

        String rootInterface = introspectedTable
            .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }

        addSelectShortByConditionMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null,
            introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;

    }

    protected void addSelectShortByConditionMethod(Interface interfaze) {

        if (this.introspectedTable.getRules().generateCondition()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectShortByConditionMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    public List<CompilationUnit> getExtraCompilationUnits() {
        return null;
    }

    protected void initializeAndExecuteGenerator(
        AbstractJavaMapperMethodGenerator methodGenerator,
        Interface interfaze) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

}
