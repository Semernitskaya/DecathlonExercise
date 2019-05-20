package com.ol.decathlon;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
public class DecathlonProcessorFullTest {

    private final static Logger LOG = Logger.getLogger(DecathlonProcessorFullTest.class.getName());

    @Test
    public void testProcessBaseData() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("base_data").toURI());
        runTestCase(path);
    }

    @Test
    public void testProcessEmptyInput() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("empty_input").toURI());
        runTestCase(path);
    }

    @Test
    public void testProcessCustomParameters() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("custom_parameters").toURI());
        runTestCase(path);
    }

    void runTestCase(Path path) throws IOException {
        LOG.info(path.toString());
        Path outputPath = path.resolve("output.xml");
        new DecathlonProcessor().process(
                path.resolve("input.csv").toString(),
                outputPath.toString(),
                path.resolve("parameters.csv").toString());
        Path expectedOutputPath = path.resolve("expected_output.xml");
        assertContentEquals(outputPath, expectedOutputPath);
    }

    private void assertContentEquals(Path actualPath, Path expectedPath) throws IOException {
        List<String> actualLines = Files.readAllLines(actualPath);
        List<String> expectedLines = Files.readAllLines(expectedPath);
        actualLines = trimLines(actualLines);
        expectedLines = trimLines(expectedLines);
//        results in certain event types could follow in different order for the same person, so check without order
        Assertions.assertThat(actualLines).containsExactlyInAnyOrderElementsOf(expectedLines);
    }

    private List<String> trimLines(List<String> actualLines) {
        return actualLines.stream().map(String::trim).collect(Collectors.toList());
    }
}