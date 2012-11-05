package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.io.*;

/**
 * Utilities for keys generation.
 */
public class KeyGeneratorUtils {
	public static String createUuidKey() {
		return (UUID.randomUUID()).toString();
	}

	public static String createPaddedKey(int idx) {
		return String.format("%036d",idx);
	}

	public static String createPaddedKey(int idx, int size) {
		return String.format("%0"+size+"d",idx);
	}
}
