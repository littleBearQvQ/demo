package com.example.demo.zip;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFormatFileSize(String filePath) {
		File file = new File(filePath);
		return getFormatFileSize(file);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 * @return
	 */
	public static String getFormatFileSize(File file) {
		long length = file.length();
		DecimalFormat df = new DecimalFormat("#0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);
		double size = ((double) length) / (1 << 30);
		if (size >= 1) {
			return df.format(size) + "GB";
		}
		size = ((double) length) / (1 << 20);
		if (size >= 1) {
			return df.format(size) + "MB";
		}
		size = ((double) length) / (1 << 10);
		if (size >= 1) {
			return df.format(size) + "KB";
		}
		return length + "B";
	}

	/**
	 * 获取文件前缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePrefixName(String fileName) {
		String prefixName = fileName.substring(0, fileName.lastIndexOf("."));
		return prefixName;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffixName(String fileName) {
		String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
		return suffixName;
	}

	

	/**
	 * 获取目标文件夹下所有文件，将.xlsx文件排序在前面
	 * 
	 * @param files
	 * @return
	 */
	public static File[] fileSortByCATPartASC(File[] files) {
		List<File> catpartFile = new ArrayList<File>();
		List<File> otherFile = new ArrayList<File>();
		for (File file : files) {
			if (file.getName().toLowerCase().indexOf(".xlsx") != -1) {
				catpartFile.add(file);
			} else {
				otherFile.add(file);
			}
		}
		File[] catpartArray = catpartFile.toArray(new File[catpartFile.size()]);
		File[] otherArray = otherFile.toArray(new File[otherFile.size()]);
		return ArrayUtils.addAll(catpartArray, otherArray);
	}
}
