package com.ol.decathlon.xml;

import java.io.IOException;

/**
 * Created by Semernitskaya on 20.05.2019.
 */
@FunctionalInterface
public interface SupplierWithIO<T> {

    T get() throws IOException;

}
