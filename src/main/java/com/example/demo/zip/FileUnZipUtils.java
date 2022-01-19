package com.example.demo.zip;


import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;


@Slf4j
public class FileUnZipUtils {

	private final static int buffer = 1024;

	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true) {
				fl.mkdir();
			} else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false) {
						subFile.mkdir();
					}
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			log.info("unZip success！");
		}
	}

	public static String unZip(String zipFilePath, String outputDirectory) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath,"GBK");
			Enumeration<?> e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			createDirectory(outputDirectory, "");
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName().trim();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					if (!f.exists()) {
						f.mkdir();
					}

				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					if (fileName.indexOf("/") != -1) {
						createDirectory(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));
						fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
					}
					File f = new File(outputDirectory + File.separator + zipEntry.getName());
					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);
					byte[] by = new byte[buffer];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					// 关闭流
			        if (in != null) {
			        	in.close();
			        }
			        if (out != null) {
			        	out.close();
			        }
				}
			}
			log.info("unZip success！");
			zipFile.close();
			zipEntry.clone();
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		return outputDirectory;
	}

	public static boolean zip(String srcDirName, String zipFilePath) {
		boolean flag = false;
		try {
			File srcdir = new File(srcDirName);
			if (!srcdir.exists())
				throw new RuntimeException(srcDirName + " is not exist!");
			Project prj = new Project();
			Zip zip_ = new Zip();
			zip_.setProject(prj);
			zip_.setDestFile(new File(zipFilePath));

			FileSet fileSet = new FileSet();
			fileSet.setProject(prj);
			fileSet.setDir(srcdir);
			zip_.addFileset(fileSet);

			zip_.execute();
			flag = true;
			log.info("zip finished");
			zip_.clone();
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		return flag;
	}

	public static void readFileContent(String fileDir,Boolean isDelete){

		String fileName = null;
		String filePath = null;
		String fileSize = null;
		String suffixName = null;
		String prefixName = null;
		File unzipDir = new File(fileDir);
		File[] files  = FileUtil.fileSortByCATPartASC(unzipDir.listFiles());
		FileUtil.fileSortByCATPartASC(files);
		for(File f:files){

			fileName = f.getName();
			filePath = fileDir+"\\"+fileName;
			suffixName = FileUtil.getFileSuffixName(f.getName());
			prefixName = FileUtil.getFilePrefixName(f.getName());
			fileSize = FileUtil.getFormatFileSize(filePath);
			log.info("=====================FileInfos==========================");
			log.info("fileName:{}",fileName);
			log.info("filePath:{}",filePath);
			log.info("suffixName:{}",suffixName);
			log.info("prefixName:{}",prefixName);
			log.info("fileSize:{}",fileSize);

			if(isDelete){
				new File(filePath).delete();
			}

		}
		log.info("read finish");
	}

	public static void readZipContent(String zipFilePath, String outputDirectory,Boolean isDelete){
		readFileContent(unZip(zipFilePath,outputDirectory),isDelete);
	}
}
