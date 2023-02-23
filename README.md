# board-example-admin
Intellij + spring boot(2.5.8) + gradle + Thymeleaf + mysql 연동  
Java 11  

## dependencies
spring-web  
spring-security  
thymeleaf  
jpa  
lombok  
mysql  
mybatis  
=> 구현 끝난 뒤 시간 남으면 JPA 사용해서 해보기

---

## 진행상황
#### 2023-02-20  
- 관리자 페이지 / 사용자 페이지 프로젝트 분리
- 관리자 페이지 포트 8081
- 세션 쿠키 이름 변경하여 세션 중복 방지

#### 2023-02-21  
- 사용자 비활성화 기능 추가

#### 2023-02-23  
- 관리자 권한 체크 인터셉터 추가  