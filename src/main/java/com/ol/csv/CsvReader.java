package com.ol.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class CsvReader {

    private final File file;

    private final String separator;

    private final boolean skipHeader;

    public CsvReader(String file, String separator, boolean skipHeader) {
        this.file = new File(file);
        this.separator = separator;
        this.skipHeader = skipHeader;
    }

    public void consumeAll(Consumer<String[]> consumer) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.skip(skipHeader ? 1 : 0)
                    .map(line -> line.split(separator))
                    .forEach(consumer);
        }
    }

    public <T> List<T> readAll(Function<String[], Optional<T>> function) throws IOException {
        List<T> result = new ArrayList<>();
        consumeAll(strings ->
                function.apply(strings).ifPresent(result::add));
        return result;
    }

}
