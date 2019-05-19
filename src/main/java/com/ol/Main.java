package com.ol;

import com.ol.decathlon.DecathlonProcessor;

import java.util.logging.Logger;

/**
 * Created by Semernitskaya on 18.05.2019.
 */
public class Main {

    private final static Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (args.length < 2) {
            LOG.severe(String.format("Invalid input args %s", args));
            return;
        }
        new DecathlonProcessor().process(args[0], args[1], args.length >= 3 ? args[2] : null);
    }

}
