package by.rss.reader.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WrapperMethods {

    public static <T> List<T> $(List<T> list) {
        return null == list ? new ArrayList<T>() : new ArrayList<T>(list) ;
    }

    public static <T> Set<T> $(Set<T> set) {
        return null == set? new HashSet<T>() : new HashSet<T>(set);
    }

}
