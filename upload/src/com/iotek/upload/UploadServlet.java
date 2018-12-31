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
		//�����ļ�������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(1024*1024); //����ռ���ڴ�Ĵ�С.
		File file=new File("c:\\temp");
		factory.setRepository(file);   //��ʱ�ļ�
		
		//�����ϴ����ļ�
		ServletFileUpload upload=new ServletFileUpload(factory);
		upload.setSizeMax(1024*1024*10); //�����ϴ��ļ��Ĵ�С.
		
		try {
			//�ϴ��ļ��Ĵ����LIST������
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    	//��ȡ������
			      String note=item.getString(); //��ȡ��������;
			      System.out.println("note=="+note);
			    } else {
			       //�ϴ��ļ���
			    	String fileName=item.getFieldName(); //name=myfile
			    	String name=item.getName();   //�ϴ����ļ���111.jpg
			    	String contentType=item.getContentType(); //�ϴ��ļ������ݵ�����image/jpeg;
			    	long size=item.getSize();   //��ȡ�ϴ��ļ��Ĵ�С.
			    	System.out.println("fileName="+fileName);
			    	System.out.println("name="+name);
			    	System.out.println("contentType="+contentType);
			    	System.out.println("size="+size);
			    	//��ȡ�ϴ�111.jpg����;
			    	InputStream is=item.getInputStream(); 
			    	//ָ���ϴ���·��;
			    	
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
