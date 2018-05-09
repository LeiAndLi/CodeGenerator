package org.mybatis.generator.codegen.mybatis3.template;
import java.io.File;
import java.util.ArrayList;

import org.mybatis.generator.config.UserdefGeneratorConfig;

public class TemplateUserdefGenerator extends AbstractTemplateGenerator{
	
	protected UserdefGeneratorConfig config;
	
	public TemplateUserdefGenerator(UserdefGeneratorConfig config){
		super();
		this.config = config;
		this.warnings = new ArrayList<String>();
	}

	@Override
	public void renderFiles() {
		// TODO Auto-generated method stub
		File targetFile;
		String source;
		try{
			File directory = shellCallback.getDirectory(
					 config.getTargetProject(), config.getTargetPackage());
			
			String fileName = config.getTargetFileName();
			if(!config.isEnableAppend()){
				String domainObjectName = this.introspectedTable.getFullyQualifiedTable().getDomainObjectName();
				fileName = domainObjectName + fileName;
			}
			
			System.out.println(fileName);
			targetFile = new File(directory,fileName);
			source = getSource();
            
            writeFile(targetFile, source, "UTF-8"); //$NON-NLS-1$ 
		}catch (Exception e) {
			System.out.println(e.getMessage());
            warnings.add(e.getMessage());
        }
	}
	
	protected String getSource(){
		return this.introspectedTable.getTableConfiguration().getTableName() + " hello";
	}
}
