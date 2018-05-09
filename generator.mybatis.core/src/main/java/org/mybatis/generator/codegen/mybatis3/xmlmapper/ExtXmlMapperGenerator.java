package org.mybatis.generator.codegen.mybatis3.xmlmapper;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectShortByConditionElementGenerator;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class ExtXmlMapperGenerator extends AbstractXmlGenerator {

    public ExtXmlMapperGenerator() {
        super();
    }

    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
            "Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapExtNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
            namespace));

        context.getCommentGenerator().addRootComment(answer);

        addSelectShortByConditionElement(answer);

        return answer;
    }

    @Override
    public Document getDocument() {
        
        
        Document document = new Document(
            XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
            XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getSqlMapElement());

        if (!context.getPlugins().sqlMapDocumentGenerated(document,
            introspectedTable)) {
            document = null;
        }

        return document;
    }

    protected void addSelectShortByConditionElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateCondition()) {
            AbstractXmlElementGenerator elementGenerator = new SelectShortByConditionElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void initializeAndExecuteGenerator(
        AbstractXmlElementGenerator elementGenerator,
        XmlElement parentElement) {
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.setProgressCallback(progressCallback);
        elementGenerator.setWarnings(warnings);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public String getFileName() {
        
        return this.introspectedTable.getMyBatis3XmlMapperExtFileName();
    }
}
