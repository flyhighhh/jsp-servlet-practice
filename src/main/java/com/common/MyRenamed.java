package com.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyRenamed implements FileRenamePolicy{
	//파일명 재정의하는 클래스 설정
	//1. cos.jar에서 제공하는 FileRenamePolicy인터페이스를 구현하면 됨
	//2. FileRenamePolicy인터페이스에는 rename() 추상메소드가 있음 이것을 구현하면 됨
		//File을 반환하고 반환된 파일을 생성해줌
	
	@Override
	public File rename(File oldFile) {
		//파일명을 변경해서 저장하는 로직을 구현 -> 중복값이 나오지 않게 파일명을 설정
		File newFile=null;
		do {
			//리네임규칙설정하기
			long currentTime=System.currentTimeMillis(); //현재시간을 밀리세컨초로 반환
			int ranNum=(int)(Math.random()*1000)+1;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyMMdd_HHmmssSSS");
			String oriName=oldFile.getName();//이전파일명가져오기
			String ext=oriName.substring(oriName.lastIndexOf("."));
			
			//새이름 설정하기
			String newName=sdf.format(new Date(currentTime))+"_"+ranNum+ext;
			newFile=new File(oldFile.getParent(),newName);
		}while(!createNewFile(newFile));
		
		return newFile;
	}
	
	private boolean createNewFile(File newFile) {
		try {
			return newFile.createNewFile(); //생성되면 true 반환
		}catch(IOException e) {
			return false;
		}
	}
	
	
	
	
}
