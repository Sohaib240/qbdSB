package com.hourtimesheet.transformer;

/**
 * Created by hassan.mushtaq on 6/10/2015.
 */
public interface Transformer<K,V> {
    V transform(K k) throws Exception;
}
