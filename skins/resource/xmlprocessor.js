/*
 * XML Processor
 * Copyright (c) 2009 Ryan Morr (ryanmorr.com)
 * Licensed under the MIT license.
 */
 
 
/**
 * Constructor - initiate with the new operator
 * @param {String} xml URL of the XML document to load (optional)
 * @param {String} xslt URL of the XSLT document to load (optional)
 */
XMLProcessor = function(xml, xslt){
	if(xml){
		this.loadXML(xml);
		if(xslt){
			this.loadXSLT(xslt);
		}
	}
};
 
/**
 * ActiveX Objects for importing XML (IE only)
 * @type Array
 */
XMLProcessor.MSXML = [
	"Msxml2.DOMDocument.6.0",
	"Msxml2.DOMDocument.5.0", 
	"Msxml2.DOMDocument.4.0", 
	"Msxml2.DOMDocument.3.0", 
	"MSXML2.DOMDocument", 
	"Microsoft.XMLDOM"   
];
 
XMLProcessor.prototype = {
	
	/**
	 * Get the XML document
	 * @return {Object} the XML document
	 */
	getXML: function(){
		return this.xml;
	},
	
	/**
	 * Get the XSLT stylesheet
	 * @return {Object} the XSLT stylesheet
	 */
	getXSLT: function(){
		return this.xslt;
	},
	
	/**
	 * load a local XML document
	 * @param {String} url URL of the XML document to load
	 * @return {Object} the XML document
	 */
	loadXML: function(url){
		var doc = this.createDocument();
		this.xml = this.loadDocument(doc, url);
		return this.xml;
	},
	
	/**
	 * load a local XSLT stylesheet
	 * @param {String} url URL of the XML stylesheet to load
	 * @return {Object} the XSLT stylesheet
	 */
	loadXSLT: function(url){
		var doc = this.createDocument();
		this.xslt = this.loadDocument(doc, url);
		return this.xslt;
	},
	
	/**
	 * decode an XML string for use
	 * @param {String} str the encoded XML string
	 * @return {String} the decoded XML string
	 */
	decode: function(str){
		str = str.replace(/&amp;/g, '&');
		str = str.replace(/&lt;/g, '<');
		str = str.replace(/&gt;/g, '>');
		str = str.replace(/&quot;/g, '"');
		str = str.replace(/&#039;/g, '\'');
		return str
	},
	
	/**
	 * encode an XML string for transfer
	 * @param {String} str the XML string
	 * @return {String} the encoded XML string
	 */
	encode: function(str){
		str = str.replace(/&/g, '&amp;');
		str = str.replace(/</g, '&lt;');
		str = str.replace(/>/g, '&gt;');
		str = str.replace(/"/g, '&quot;');
		str = str.replace(/'/g, '&apos;');
		return str;
	},
	
	/**
	 * convert an XML document into a string
	 * @param {Boolean} encode should the string be encoded after serialization?
	 * @return {String} the XML string
	 */
	serialize: function(encode){
		if(window.XMLSerializer){
			serializer = new XMLSerializer();
			str = serializer.serializeToString(this.xml);
			return encode ? this.encode(str) : str;
		}else if(typeof (this.xml.xml || this.xml.outerHTML) == 'string'){
			var str = this.xml.xml || this.xml.outerHTML;
			return encode ? this.encode(str) : str;
		}
	},
	
	/**
	 * convert an XML string into a document
	 * @param {String} str the XML string to parse
	 * @return {Object} the XML document
	 */
	parse: function(str){
		str = this.decode(str);
		if(window.DOMParser){
			var parser = new DOMParser();
			this.xml = parser.parseFromString(str, "text/xml");
		}else if(window.ActiveXObject){
			this.xml = new ActiveXObject("Microsoft.XMLDOM");
			this.xml.async = false;
			this.xml.loadXML(str);
		}
		return this.xml;
	},
	
	/**
	 * create a document object
	 * @return {Object} the document
	 */
	createDocument: function(){
		var doc;
		if(document.implementation && document.implementation.createDocument){
			doc = document.implementation.createDocument("","",null);
		}else if(window.ActiveXObject){
			for(var i=0; i < XMLProcessor.MSXML.length; i++){
				try{
					doc = new ActiveXObject(XMLProcessor.MSXML[i]);
				}catch(e){}
			}
		}
		return doc;
	},
	
	/**
	 * load and populate the document
	 * @param {Object} doc the empty document to be populated
	 * @param {String} url URL of the document to load
	 * @return {Object} the populated document
	 */
	loadDocument: function(doc, url){
		if(doc && typeof doc.load != 'undefined'){
			doc.async=false;
			doc.load(url);
			return doc;
		}else{
			var request = new XMLHttpRequest();
			request.open("GET", url, false);
			request.send("");
			return request.responseXML;
		}
	},
	
	/**
	 * perform an XLS transformation on an XML document and store the results in an element
	 * @param {Element} target the element to populate with the results of the transformation
	 */
	transform: function(target){
		if(window.XSLTProcessor){
			var processor = new XSLTProcessor();
			processor.importStylesheet(this.xslt);
			var doc = processor.transformToFragment(this.xml, document);			
			target.appendChild(doc);			
		}else{
			target.innerHTML = this.xml.transformNode(this.xslt);
		}		
	},
	
	/**
	 * perform an xpath query on an XML document
	 * @param {String} query the xpath expression to be evaluated
	 * @return {Array} array of nodes
	 */
	find: function(query){
		if(typeof this.xml.selectNodes != 'undefined'){
			return this.xml.selectNodes(query);
		}else if(document.implementation.hasFeature('XPath', '3.0')){
			var nodes = [];
			var resolver = this.xml.createNSResolver(this.xml.documentElement);
			var items = this.xml.evaluate(query, this.xml, resolver, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
			for(var i=0; i < items.snapshotLength; i++){
			  nodes[i] = items.snapshotItem(i);
			}
			return nodes;
		}else{
			return [];
		}
	},
	
	/**
		* jmh 2009-05-17 Need to pass XSL parameters
	 * @param {Element} target the element to populate with the results of the transformation
	 * @param {Object} processor the XSLTProcessor for use in the transformation
		*/
		transform: function(target, processor){
		if(window.XSLTProcessor)
		{		
			var doc = processor.transformToFragment(this.xml, document);
			target.innerHTML = "";
			target.appendChild(doc);	
		}
		else
		{
			target.innerHTML = this.xml.transformNode(this.xslt);
		}		
	}

}