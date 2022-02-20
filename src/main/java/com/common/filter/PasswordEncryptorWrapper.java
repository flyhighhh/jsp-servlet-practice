package com.common.filter;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordEncryptorWrapper extends HttpServletRequestWrapper{
	
	public PasswordEncryptorWrapper(HttpServletRequest request) {
		super(request);
	}
	
	
	//getParameter메소드를 호출했을 때
	//매개변수의 값이 password와 관련되어있으면 암호화해서 반환하게 재정의
	
	@Override
	public String getParameter(String name) {
		//name값이 password일 경우 가공처리해서 반환하자.
		if(name.equals("password")||name.equals("password_")) {
			String oriPw=super.getParameter(name);
			System.out.println(oriPw);
			String encPw=getSHA512(oriPw);
			System.out.println(encPw);
			return encPw;
		}else {
			return super.getParameter(name);	//원본값을 반환
		}
	}
	
	//암호화처리 메소드 생성
	private String getSHA512(String oriPw) {
		String encPwd="";//암호화된 문자열 값 저장변수
		MessageDigest md=null;
		try {
			//암호화 알고리즘을 선택
			md=MessageDigest.getInstance("SHA-512");
			
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//암호화처리는 비트단위 연산을 해서 처리함 -> 바이트 자료형으로 암호화처리
		byte[] oribyte=oriPw.getBytes(Charset.forName("utf-8"));
		//messagedigest클래스의 update함수를 이용해서 암호화처리를 진행
		md.update(oribyte);
		//byte로 변환해서 암호화한 내용을 String으로 반환하기
		encPwd=Base64.getEncoder().encodeToString(md.digest());
		
		return encPwd;
	}
}
