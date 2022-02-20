package com.member.model.vo;

import java.sql.Date;
//lombok적용하기 -> vo객체를 만들었을때 setter/getter,생성자,builder패턴을 적용한 생성자를 자동생성해주는 라이브러리
//1. jar파일을 다운로드 받기
//2. jar를 실행해서 eclipse에 적용
//3. 프로젝트라이브러리에 추가
//4. lombok이 제공하는 어노테이션 이용해서 처리
//@Data : setter,getter,toString,hashcode,equals 기본생성자를 생성
//@Setter : setter메소드 생성
//@Getter : getter메소드 생성
//@AllArgsConstructor : 매개변수 있는 생성자 생성
//@NoArgsConstructor : 디폴트생성자
//@ToString : toString오버라이드
//@Builder : builder패턴으로 생성자생성
//@EqualsAndHashCode : equals, hashcode메소드 자동생성

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//롬복 단점: 커스터마이징 X, 변수명 주의 
public class Member {
	private String userId;
	private String password;
	private String userName;
	private String gender;
	private int age;
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate;
	
	
	
}