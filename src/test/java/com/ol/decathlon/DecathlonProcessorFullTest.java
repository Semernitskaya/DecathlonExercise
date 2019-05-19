package com.ol.decathlon;

import com.ol.decathlon.data.Range;
import com.ol.decathlon.data.ResultRecord;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ol.decathlon.ResultRecordUtil.getResultRecord;
import static org.assertj.core.util.Lists.list;
import static org.testng.Assert.assertEquals;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class DecathlonProcessorFullTest {

    private final static Logger LOG = Logger.getLogger(DecathlonProcessorFullTest.class.getName());

    @Test
    public void testProcess() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("").toURI());

        try (Stream<Path> stream = Files.walk(path, 1)) {
            stream.skip(1).forEach(testCasePath -> {
                LOG.info(testCasePath.toString());
                new DecathlonProcessor().process(testCasePath.resolve("input.csv").toString(),
                        testCasePath.resolve("output.xml").toString(),
                        testCasePath.resolve("parameters.csv").toString());
            });
        }
    }
}