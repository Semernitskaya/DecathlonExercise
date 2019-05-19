package com.ol.decathlon.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Semernitskaya on 19.05.2019.
 */
@XmlRootElement(name = "resultRecords")
public class ResultRecordsWrapper {

    private List<ResultRecord> resultRecords;

    public ResultRecordsWrapper(List<ResultRecord> resultRecords) {
        this.resultRecords = resultRecords;
    }

    public ResultRecordsWrapper() {
    }

    public List<ResultRecord> getResultRecords() {
        return resultRecords;
    }

    @XmlElement(name = "resultRecord")
    public void setResultRecords(List<ResultRecord> resultRecords) {
        this.resultRecords = resultRecords;
    }
}
