package com.ol.decathlon.xml;

import com.ol.decathlon.data.ResultRecordsWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class SimpleXmlWriter {

    private final static Logger LOG = Logger.getLogger(SimpleXmlWriter.class.getName());

    public void writeAsXmlString(ResultRecordsWrapper recordsWrapper,
                                     SupplierWithIO<Writer> writerSupplier) throws IOException {
        try (Writer writer = writerSupplier.get()) {
            JAXBContext context = JAXBContext.newInstance(ResultRecordsWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
            m.marshal(recordsWrapper, writer);
        } catch (JAXBException e) {
            LOG.log(Level.WARNING, "Error while converting to xml", e);
        }
    }
}
