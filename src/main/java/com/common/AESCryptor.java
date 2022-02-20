package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

//양방향 암호화를 제공하는 클래스
//양방향 암호화처리 클래스는 기본 java API에서 제공
//java.crypto패키지, java.sercurity패키지에서 클래스와 메소드를 제공
//적용할 암호화알고리즘 AES알고리즘 -> 대칭키 방식의 암호화 알고리즘
//암호화, 복호화를 하나의 키로 하는 것 -> 양방향은 키 관리가 상당히 중요함

public class AESCryptor {
	
	private static SecretKey key; //암호화키를 저장
	private String path; //key를 파일로 저장해서 사용, 그 파일을 저장하는 파일 경로
	
	public AESCryptor() {
		// 기본생성자로 클래스가 생성되면
		// 1. key를 생성하기
		// - 미리 생성된 키가 있으면 그 키를 이용
		// - 생성된 키가 없으면 새로생성해서 이용
		this.path=AESCryptor.class.getResource("/").getPath();
		this.path=path.substring(0,this.path.indexOf("classes"));
		System.out.println(path);
		//key생성하기 Key를 저장한 파일이 있으면 그 파일을 불러오고 없으면 생성
		File keyFile=new File(path+"sunny.sun");
		if(keyFile.exists()) {
			//key파일이 존재할 때 -> 파일에 저장된 Secret클래스를 가져옴
			try {
				this.key=(SecretKey)new ObjectInputStream(new FileInputStream(keyFile)).readObject();
				
			}catch(IOException|ClassNotFoundException e){
				e.printStackTrace();
			}
		}else {
			//key파일이 존재하지 않을 때 -> Secretkey클래스 생성
			getGeneratorKey();
		}
	}
	
	private void getGeneratorKey() {
		//SecretKey클래스 생성
		SecureRandom random=new SecureRandom(); 
		//랜덤수를 가져오는 클래스 -> 중복이 안나오게 정밀함
		//Secretkey는 keyGenerator클래스의 메소드를 이용해서 암호 알고리즘을 설정하고
		//알고리즘에 필요한 설정을 한 뒤, key를 생성
		KeyGenerator keygen=null;
		try {
			keygen=KeyGenerator.getInstance("AES");	//적용할 알고리즘을 설정
			keygen.init(128,random); //key설정내용 초기화
			
			this.key=keygen.generateKey(); //생성된 KEY멤버변수에 넣기
			
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	//생성된 키는 파일로 보관
		File f=new File(path+"/sunny.sun");
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f))) {
			oos.writeObject(this.key);
			
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	//암호화메소드
	public static String encrypt(String str) throws InvalidKeyException, IllegalBlockSizeException, 
							BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
	
		//생성된 secretkey를 이용해서 암호화를 실행
		//암호화처리 클래스 : Cipher를 이용
		Cipher cipher=Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, AESCryptor.key);
		
		//암호처리하기
		byte[] enctemp=str.getBytes(Charset.forName("utf-8"));
		byte[] encResult=cipher.doFinal(enctemp); //암호화처리
		String strEncode=Base64.getEncoder().encodeToString(encResult);
		return strEncode;
		
	}
	
	//복호화메소드
	//원본값으로 보일 수 있게 하는 메소드
	public static String decrypt(String encStr) throws InvalidKeyException, NoSuchAlgorithmException, 
								NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher=Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE,AESCryptor.key);	//초기설정
		
		//base64로 인코딩한 문자열을 decode
		byte[] decodeBase=Base64.getDecoder().decode(encStr.getBytes(Charset.forName("UTF-8")));
		byte[] oriVal=cipher.doFinal(decodeBase);
		
		return new String(oriVal);
	}
	
	
	
	
	
}
