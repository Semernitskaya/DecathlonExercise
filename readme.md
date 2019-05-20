## Decathlon exercise

Build using following command: 
```bash
mvn clean package
```

Run with default parameters A, B and C for points formula using following command: 
```bash
java -jar decathlon-1.0-SNAPSHOT.jar {input} {output}
```

Run with custom parameters A, B and C from file using following command: 
```bash
java -jar decathlon-1.0-SNAPSHOT.jar {input} {output} {parameters}
```

Example: 
```bash
java -jar decathlon-1.0-SNAPSHOT.jar input.csv output.xml parameters.csv
```

### Program description
1. {input} - path to the file with results data (file could be empty)
1. {output} - path to the file with calculation results 
(will be created if doesn't exist, will be overwritten if exists)
1. {parameters} - path to the file with custom parameters A, B and C for points formula 
(in case if skipped or not exist default parameters.csv will be used) 
1. Main processing logic view in DecathlonProcessor

### Program requirements
1. Run on Java 8
1. Assume, that we have enough memory to store all results-date


