package org.mybatis.generator.config.file;

import java.util.List;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.otherjavaGeneratorConfiguration;

public class JavaDTOAdapterImplementGeneratorConfig extends otherjavaGeneratorConfiguration{

    public JavaDTOAdapterImplementGeneratorConfig(){
        super();
    }
    
    public XmlElement toXmlElement(){
        XmlElement answer = new XmlElement("javaDTOAdapterImplementGenerator"); //$NON-NLS-1$
        return super.toXmlElement(answer);
    }
    
    public void validate(List<String> errors, String contextId) {
        super.validate(errors, contextId);
    }
}
