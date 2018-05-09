package org.mybatis.generator.codegen.mybatis3.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.internal.DefaultShellCallback;

public abstract class AbstractTemplateGenerator extends AbstractGenerator {

	public AbstractTemplateGenerator() {
		super();
		this.shellCallback = new DefaultShellCallback(false);
	}

	protected ShellCallback shellCallback;

	public void writeFile(File file, String content, String fileEncoding) throws IOException {
		FileOutputStream fos = new FileOutputStream(file, false);
		OutputStreamWriter osw;
		if (fileEncoding == null) {
			osw = new OutputStreamWriter(fos);
		} else {
			osw = new OutputStreamWriter(fos, fileEncoding);
		}

		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(content);
		bw.close();
	}

	public abstract void renderFiles();
}
