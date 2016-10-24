package com.Suirui.net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BufferIOFile {
	public static void save(String filePath,String content) {
		try {
			
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)),"utf-8"));
		output.write(content);
		output.close();
		} catch ( IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}

}
