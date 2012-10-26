package com.amm.mapper;

import com.amm.nosql.NoSqlException;
import java.io.*;
import java.util.*;

public class ObjectMapperUtils {

    public static byte[] objectToBytes(Object object) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(object);
            return stream.toByteArray();
        } catch(IOException e) {
            throw new NoSqlException(e);
        }
    }

    public static Object objectFromBytes(byte[] bytes) {
        try {
        	ObjectInputStream object = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
        } catch(IOException e) {
            throw new NoSqlException(e);
        } catch(ClassNotFoundException e) {
            throw new NoSqlException(e);
        }
    }
}
