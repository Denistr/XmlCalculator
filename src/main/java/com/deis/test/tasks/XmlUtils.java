package com.deis.test.tasks;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 27.08.18.
 */
public class XmlUtils {

    public static boolean isXmlValid(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (SAXException | IOException e) {
            return false;
        }
        return true;
    }

    public static List<Operation> parseXml(String xmlPath) {
        List<Operation> operations = new ArrayList<>();
        XMLStreamReader reader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(xmlPath);
            reader = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);
            reader.nextTag();
            while (!reader.getLocalName().equals("expressions")) {
                reader.nextTag();
            }
            reader.nextTag(); //expression
            while (!reader.isEndElement() || !reader.getLocalName().equals("expressions")) {
                operations.add(new XmlUtils().getMainExpression(reader));
                reader.nextTag();
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }
        }
        return operations;
    }

    private Operation getMainExpression(XMLStreamReader reader) throws XMLStreamException {
        reader.nextTag();
        Operation mainOperation = getOperation(reader);
        while (!reader.isEndElement() || !reader.getLocalName().equals("expression")) {
            reader.nextTag();
        }
        return mainOperation;
    }

    private Operation getOperation(XMLStreamReader reader) throws XMLStreamException {
        Operation operation = new Operation();
        if (reader.getLocalName().equals("arg")) {
            operation.setValue(Double.valueOf(reader.getElementText()));
            return operation;
        } else {
            String attributeValue = reader.getAttributeValue(0);
            operation.setOperationType(Operation.OperationType.valueOf(attributeValue));
            reader.nextTag();
            operation.setFirstOperation(getOperation(reader));
            reader.nextTag();
            while (reader.isEndElement() && reader.getLocalName().equals("operation")) {
                reader.nextTag();
            }
            operation.setSecondOperation(getOperation(reader));
            return operation;
        }
    }

    public static File createXml(List<Double> results) {
        String resultFileName = "CalculationResult.xml";
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        File outputXml = new File(resultFileName);
        XMLStreamWriter xmlWriter = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(outputXml);
            xmlWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(fw));

            xmlWriter.writeStartDocument("UTF-8", "1.0");
            xmlWriter.writeStartElement("simpleCalculator"); // <simpleCalculator >
            xmlWriter.writeStartElement("expressionResults"); // <expressionResults>

            for (Double result : results) {
                xmlWriter.writeStartElement("expressionResult"); // <expressionResult>
                xmlWriter.writeStartElement("result"); // <result>
                xmlWriter.writeCharacters(String.valueOf(result));
                xmlWriter.writeEndElement(); // </result>
                xmlWriter.writeEndElement(); // </expressionResult>
            }

            xmlWriter.writeEndElement(); // </expressionResults>
            xmlWriter.writeEndElement(); // </simpleCalculator>

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlWriter != null) {
                    xmlWriter.flush();
                    xmlWriter.close();
                }
                if (fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException | XMLStreamException e) {
                e.printStackTrace();
            }
        }
        return outputXml;
    }
}
