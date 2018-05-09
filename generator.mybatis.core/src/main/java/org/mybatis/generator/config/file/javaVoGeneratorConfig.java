package org.mybatis.generator.config.file;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.ColumnAdd;
import org.mybatis.generator.config.otherjavaGeneratorConfiguration;

public class javaVoGeneratorConfig extends otherjavaGeneratorConfiguration {
	
	public List<ColumnAdd> addColumns;

	public javaVoGeneratorConfig(){
		super();
		this.addColumns = new ArrayList<ColumnAdd>();
	}
	
	public XmlElement toXmlElement(){
		XmlElement answer = new XmlElement("javaVoGenerator"); //$NON-NLS-1$
		
		if(this.addColumns != null){
			for(ColumnAdd col : this.addColumns){
				answer.addElement(col.toXmlElement());
			}
		}
		
		return super.toXmlElement(answer);
	}
	
	public void validate(List<String> errors, String contextId) {
		super.validate(errors, contextId);
		
		if(this.addColumns != null){
			for(ColumnAdd col : this.addColumns){
				col.validate(errors, contextId);
			}
		}
	}

	public List<ColumnAdd> getAddColumns() {
		return addColumns;
	}

	public void setAddColumns(List<ColumnAdd> addColumns) {
		this.addColumns = addColumns;
	}
	
	public void addAddColumn(ColumnAdd col){
		this.addColumns.add(col);
	}
	
	
}
