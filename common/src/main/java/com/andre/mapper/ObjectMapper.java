package com.andre.mapper;

import java.util.*;
import java.io.*;

public interface ObjectMapper {

	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception ;
	public <T> T toObject(String content, Class<T> clazz) throws Exception ;

	public String toString(Object obj) throws Exception ;
	public byte [] toBytes(Object obj) throws Exception ;

	public String getContentType() ;
}
