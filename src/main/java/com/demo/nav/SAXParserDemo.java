package com.demo.nav;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.demo.nav.handler.DataHandler;

public class SAXParserDemo {

	public static void main(String[] args) {

	      try {
	         File inputFile = new File("src/main/data/input.xml");
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         SAXParser saxParser = factory.newSAXParser();
	         DataHandler handler = new DataHandler();
	         saxParser.parse(inputFile, handler);     
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }   
}
