package me.itoxic.moduino.util;

import java.util.LinkedList;

public class ClassUtil {

    public static <E, K> LinkedList<E> getElementsInside(Class<E> clazz, LinkedList<K> linkedList) {

        LinkedList<E> result = new LinkedList<>();

        for(Object obj : linkedList)
            if (clazz.isInstance(obj))
                result.add((E) obj);

        return result;

    }

}
