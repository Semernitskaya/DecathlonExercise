package com.ol.csv;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class CsvReader {

    private File file;

    private final String separator;

    private final boolean skipHeader;

    private Supplier<InputStream> inputStreamSupplier;

    public CsvReader(String file, String separator, boolean skipHeader) {
        this.file = new File(file);
        this.separator = separator;
        this.skipHeader = skipHeader;
    }

    public CsvReader(Supplier<InputStream> inputStreamSupplier, String separator, boolean skipHeader) {
        this.inputStreamSupplier = inputStreamSupplier;
        this.separator = separator;
        this.skipHeader = skipHeader;
    }

    public void consumeAll(Consumer<String[]> consumer) throws IOException {
        try (Stream<String> lines = file != null ?
                Files.lines(file.toPath()) : getLinesFromInputStream()) {
            lines.skip(skipHeader ? 1 : 0)
                    .map(line -> line.trim().split(separator))
                    .forEach(consumer);
        }
    }

    private Stream<String> getLinesFromInputStream() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStreamSupplier.get()))) {
            String line;
            while ((line = r.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines.stream();
    }

    public <T> List<T> readAll(Function<String[], Optional<T>> function) throws IOException {
        List<T> result = new ArrayList<>();
        consumeAll(strings ->
                function.apply(strings).ifPresent(result::add));
        return result;
    }

}
