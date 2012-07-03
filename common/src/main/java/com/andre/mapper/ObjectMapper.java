package com.andre.mapper;

import java.util.*;
import java.io.*;

public interface ObjectMapper {
	public Object toObject(byte [] content, Class clazz) throws Exception ;
	public Object toObject(String content, Class clazz) throws Exception ;
	public String toString(Object obj) throws Exception ;
	public byte [] toBytes(Object obj) throws Exception ;
	public String getContentType() ;
}
