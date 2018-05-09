package org.mybatis.generator.config.method;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByIdMethodGenerator;
import org.mybatis.generator.exception.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MethodFactory {

	public class ParamNode {
		private String name;
		private String type;
		private List<String> notes;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<String> getNotes() {
			return notes;
		}

		public void setNotes(List<String> notes) {
			this.notes = notes;
		}

		public void addNode(String note) {
			if (this.notes == null) {
				this.notes = new ArrayList<String>();
			}

			this.notes.add(note);
		}
	}

	public class FieldNode {
		private String name;
		private String type;
		private String init;
		private String remark;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getInit() {
			return init;
		}

		public void setInit(String init) {
			this.init = init;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

	}

	public class MethodNode {
		private String name;

		private String statement;

		private String returnType;

		private List<ParamNode> paramList;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatement() {
			return statement;
		}

		public void setStatement(String statement) {
			this.statement = statement;
		}

		public String getReturnType() {
			return returnType;
		}

		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}

		public List<ParamNode> getParamList() {
			return paramList;
		}

		public void setParamList(List<ParamNode> paramList) {
			this.paramList = paramList;
		}

		public void addParam(ParamNode node) {
			if (this.paramList == null) {
				this.paramList = new ArrayList<ParamNode>();
			}

			this.paramList.add(node);
		}

	}

	private List<String> parseErrors;
	private Map<String, List<String>> methodMap;
	private Map<String, MethodNode> methodStatementMap;
	private Map<String, List<FieldNode>> fieldMap;
	private VelocityEngine ve;
	private MethodParam methodParam;
	private VelocityContext context;

	public MethodFactory(MethodParam mp) throws XMLParserException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		this.methodMap = new HashMap<String, List<String>>();
		this.methodStatementMap = new HashMap<String, MethodNode>();
		this.fieldMap = new HashMap<String, List<FieldNode>>();
		parseErrors = new ArrayList<String>();
		this.methodParam = mp;
		this.context = mp.toVelocityContext();

		this.ve = new VelocityEngine();
		ve.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// 处理中文问题
		ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		ve.init();

		Template template = null;

		try {
			template = ve.getTemplate("org/mybatis/generator/method/MethodFactory.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringWriter sw = new StringWriter();

		if (template != null)
			template.merge(context, sw);

		//System.out.println(sw.toString());
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream is = new ByteArrayInputStream(sw.toString().getBytes("UTF-8")); 
			
			//InputStream is = getClass().getClassLoader()
		   //			.getResourceAsStream("org/mybatis/generator/method/MethodFactory.xml"); //$NON-NLS-1$
			InputSource ins = new InputSource(is);
			Document document = null;

			try {
				document = builder.parse(ins);
				this.parseDocument(document);
			} catch (SAXParseException e) {
				throw new XMLParserException(parseErrors);
			} catch (SAXException e) {
				if (e.getException() == null) {
					parseErrors.add(e.getMessage());
				} else {
					parseErrors.add(e.getException().getMessage());
				}
			}

			if (parseErrors.size() > 0) {
				throw new XMLParserException(parseErrors);
			}

		} catch (ParserConfigurationException e) {
			parseErrors.add(e.getMessage());
			throw new XMLParserException(parseErrors);
		}

	}

	private void parseDocument(Document document) {
		Element rootNode = document.getDocumentElement();
		NodeList nodeList = rootNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if ("methodBody".equals(childNode.getNodeName())) { //$NON-NLS-1$
				parseMethodBody(childNode);
			}

			if ("methodDeclaration".equals(childNode.getNodeName())) {
				parseMethodDeclaration(childNode);
			}

			if ("fields".equals(childNode.getNodeName())) {
				parseFields(childNode);
			}
		}

	}

	private void parseFields(Node node) {
		Properties attributes = parseAttributes(node);
		String name = attributes.getProperty("name"); //$NON-NLS-1$
		if (!this.fieldMap.containsKey(name)) {
			this.fieldMap.put(name, new ArrayList<FieldNode>());
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if ("publicField".equals(childNode.getNodeName())) {
				FieldNode f = parsePublicField(childNode);
				this.fieldMap.get(name).add(f);
			}
		}
	}

	private FieldNode parsePublicField(Node node) {
		FieldNode fnode = new FieldNode();

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if ("fieldName".equals(childNode.getNodeName())) {
				String fname = this.parseStr(childNode);
				fnode.setName(fname);
			}

			if ("fieldType".equals(childNode.getNodeName())) {
				String ftype = this.parseStr(childNode);
				fnode.setType(ftype);
			}

			if ("fieldInitStr".equals(childNode.getNodeName())) {
				String finit = this.parseStr(childNode);
				fnode.setInit(finit);
			}

			if ("remark".equals(childNode.getNodeName())) {
				String rm = this.parseStr(childNode);
				fnode.setRemark(rm);
			}

		}

		return fnode;
	}

	private void parseMethodBody(Node node) {
		Properties attributes = parseAttributes(node);
		String name = attributes.getProperty("name"); //$NON-NLS-1$
		
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if ("methodDiv".equals(childNode.getNodeName())) {
				List<String> list = parseMethodDiv(childNode);
				
				if(this.methodMap.containsKey(name)){
					this.methodMap.get(name).add(" ");
					this.methodMap.get(name).addAll(list);
				}else{
					this.methodMap.put(name, list);
				}
				
				//System.out.println(name + " : " + this.methodMap.get(name).toString());
			}
		}
	}
	
	private List<String> parseMethodDiv(Node node){
		String body = node.getTextContent();
		//System.out.println(body);
		String[] lines = body.split("\n");
		List<String> list = new ArrayList<String>();
		if (lines != null) {
			for (String r : lines) {
				String t = r.trim();
				if (t.length() > 0) {
					StringWriter writer = new StringWriter();
					ve.evaluate(context, writer, "", t);
					list.add(writer.toString());
				}
			}
		}
		
		
		
		
		return list;
	}

	private void parseMethodDeclaration(Node node) {
		Properties attributes = parseAttributes(node);
		String name = attributes.getProperty("name"); //$NON-NLS-1$

		MethodNode mnode = new MethodNode();
		mnode.setName(name);

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if ("statementId".equals(childNode.getNodeName())) {
				String id = this.parseStr(childNode);
				mnode.setStatement(id);
			}

			if ("returnType".equals(childNode.getNodeName())) {
				String type = this.parseStr(childNode);
				mnode.setReturnType(type);
			}

			if ("paramType".equals(childNode.getNodeName())) {
				ParamNode pnode = this.parseParamType(childNode);
				mnode.addParam(pnode);
			}

		}

		this.methodStatementMap.put(name, mnode);

	}

	private String parseStr(Node node) {
		String str = node.getTextContent();
		// StringWriter writer = new StringWriter();
		// ve.evaluate(context, writer, "", str);
		// return writer.toString();
		return str;
	}

	private ParamNode parseParamType(Node node) {
		Properties attributes = parseAttributes(node);
		String name = attributes.getProperty("name"); //$NON-NLS-1$
		String type = attributes.getProperty("type"); //$NON-NLS-1$
		StringWriter writer = new StringWriter();
		ve.evaluate(context, writer, "", type);
		ParamNode pn = new ParamNode();
		pn.setName(name);
		pn.setType(writer.toString());

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if ("annotation".equals(childNode.getNodeName())) {
				String note = childNode.getTextContent();
				pn.addNode(note);
			}
		}

		return pn;
	}

	protected Properties parseAttributes(Node node) {
		Properties attributes = new Properties();
		NamedNodeMap nnm = node.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node attribute = nnm.item(i);
			String value = attribute.getNodeValue();
			attributes.put(attribute.getNodeName(), value);
		}

		return attributes;
	}

	public List<String> getMethod(String name) {
		return this.methodMap.get(name);
	}

	public MethodNode getMethodNode(String name) {
		return this.methodStatementMap.get(name);
	}

	public Method getMybatisMethod(String name, Set<FullyQualifiedJavaType> importedTypes) {
		MethodNode mnode = this.methodStatementMap.get(name);
		Method method = new Method();
		if (mnode != null) {
			method.setVisibility(JavaVisibility.PUBLIC);
			method.setName(mnode.getStatement());
			FullyQualifiedJavaType rtType = new FullyQualifiedJavaType(mnode.getReturnType());
			method.setReturnType(rtType);
			importedTypes.add(rtType);
			List<ParamNode> pnodes = mnode.getParamList();
			if (pnodes != null) {
				for (ParamNode pnode : pnodes) {
					FullyQualifiedJavaType type = new FullyQualifiedJavaType(pnode.getType());
					importedTypes.add(type);
					Parameter pr = new Parameter(type, pnode.getName());

					List<String> notes = pnode.getNotes();
					if (notes != null) {
						for (String note : notes) {
							pr.addAnnotation(note);
						}
					}

					method.addParameter(pr);
				}
			}
		}

		return method;
	}

	public List<Field> getMybatisField(String name, Set<FullyQualifiedJavaType> importedTypes) {
		List<Field> rtList = new ArrayList<Field>();

		if (!this.fieldMap.containsKey(name)) {
			return null;
		}

		List<FieldNode> fnodes = this.fieldMap.get(name);
		for (FieldNode fnode : fnodes) {
			FullyQualifiedJavaType type = new FullyQualifiedJavaType(fnode.getType());
			importedTypes.add(type);

			Field field = new Field();
			field.setVisibility(JavaVisibility.PUBLIC);
			field.setType(type);
			field.setName(fnode.getName());
			field.setStatic(true);
			field.setInitializationString(fnode.getInit());
			field.addJavaDocLine(fnode.getRemark());

			rtList.add(field);

		}

		return rtList;
	}

	public static void main(String[] args) throws XMLParserException, IOException {
		MethodParam mp = new MethodParam();
		mp.setBaseRecordType("baseRecordType");
		mp.setDomainObjectName("PayShareDiffReport");
		mp.setJavaMapperName("javaMapperName");

		MethodFactory mf = new MethodFactory(mp);
		/*
		 * List<String> test = mf.getMethod("returnNull");
		 * System.out.println(test.size()); System.out.println(test.toString());
		 */

		String name = QueryByIdMethodGenerator.class.getSimpleName();
		// System.out.println(name);

		List<String> query = mf.getMethod(name);
		System.out.println(query.toString());

		MethodNode mnode = mf.getMethodNode(name);
		System.out.println(mnode.getStatement());
		System.out.println(mnode.getReturnType());
		System.out.println(mnode.getParamList().get(0).getName());
		System.out.println(mnode.getParamList().get(0).getType());
	}

}
