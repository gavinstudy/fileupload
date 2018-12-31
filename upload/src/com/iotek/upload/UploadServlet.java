package com.iotek.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//磁盘文件工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(1024*1024); //允许占用内存的大小.
		File file=new File("c:\\temp");
		factory.setRepository(file);   //临时文件
		
		//处理上传的文件
		ServletFileUpload upload=new ServletFileUpload(factory);
		upload.setSizeMax(1024*1024*10); //限制上传文件的大小.
		
		try {
			//上传文件的存放在LIST集合中
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    	//获取表单数据
			      String note=item.getString(); //获取表单的内容;
			      System.out.println("note=="+note);
			    } else {
			       //上传文件域
			    	String fileName=item.getFieldName(); //name=myfile
			    	String name=item.getName();   //上传的文件名111.jpg
			    	String contentType=item.getContentType(); //上传文件的内容的类型image/jpeg;
			    	long size=item.getSize();   //获取上传文件的大小.
			    	System.out.println("fileName="+fileName);
			    	System.out.println("name="+name);
			    	System.out.println("contentType="+contentType);
			    	System.out.println("size="+size);
			    	//获取上传111.jpg数据;
			    	InputStream is=item.getInputStream(); 
			    	//指定上传的路径;
			    	
			    	String path=request.getRealPath("/")+"images\\";
			    	String fname=path+UUID.randomUUID()+".jpg";
			    	System.out.println("fname="+fname);
			    	
			    	FileOutputStream fos=new FileOutputStream(fname);
			    	byte content[]=new byte[1024];
			    	int len=0;
			    	while((len=is.read(content))!=-1){
			    		fos.write(content, 0, len);
			    		fos.flush();
			    	}
			    	fos.close();
			    	is.close();
			    	
			    }
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
