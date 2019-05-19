package com.ol.xml;

import com.ol.decathlon.ResultRecord;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class SimpleXmlConverter {

    public String convertToXmlString(Object o) {
        String xmlString = null;
        try {
            JAXBContext context = JAXBContext.newInstance(ResultRecord.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            StringWriter sw = new StringWriter();
            m.marshal(o, sw);
            xmlString = sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlString;
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        XMLEncoder xmlEncoder = new XMLEncoder(stream);
//        xmlEncoder.writeObject(o);
//        xmlEncoder.close();
//        return stream.toString();
    }
}
