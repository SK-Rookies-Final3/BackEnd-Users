## 사용자 관리 시스템
이 레포지토리는 회원가입, 로그인, 회원 정보 조회, 수정, 탈퇴 등의 사용자 관리 기능을 제공하는 API를 구현한 프로젝트입니다. Spring Boot를 기반으로 설계되었으며, RESTful API 방식으로 클라이언트와 통신합니다.

### 기능 설명

#### 1. 회원가입
- 신규 사용자가 회원 정보를 입력하고, 서버에 회원가입을 요청합니다.
- 서버는 유효성 검사를 수행하고, 중복된 계정이 없는 경우 회원을 생성합니다.
- 초기 nickname은 username으로 설정합니다.
- 회원가입 시 암호화된 비밀번호가 데이터베이스에 저장됩니다.
#### 2. 로그인
- 사용자가 ID와 비밀번호를 입력하여 서버에 로그인 요청을 보냅니다.
- 서버는 입력된 비밀번호와 데이터베이스에 저장된 비밀번호를 비교하여 일치하는 경우 로그인 성공 토큰을 발급합니다.
- 로그인 토큰은 이후 인증이 필요한 API 요청에 사용됩니다.
#### 3. 회원 정보 조회
- 로그인한 사용자가 자신의 회원 정보를 조회할 수 있습니다.
- 인증된 사용자만 자신의 정보를 조회할 수 있도록 보호되어 있습니다.
- role이 MASTER인 경우 모든 회원의 정보를 조회할 수 있도록 합니다.
#### 4. 회원 정보 수정
- 사용자가 자신의 개인정보(이름, 이메일 등)를 수정할 수 있습니다.
- 수정된 정보는 서버에서 검증 후 데이터베이스에 반영됩니다.
#### 5. 회원 탈퇴
- 사용자가 계정을 삭제할 수 있는 기능입니다.
- 탈퇴 시 해당 사용자 정보는 데이터베이스에서 삭제되며, 이후 로그인이나 조회가 불가능합니다.

### API 엔드포인트
- 회원가입: POST /open-api/user/register
- 로그인: POST /open-api/user/login
- 회원 정보 조회: GET /api/user
- 회원 정보 수정: PATCH /api/user/update
- 회원 탈퇴: DELETE /api/user/exit/{id}

### 사용 기술 스택
- Server Framework : Spring Boot 3.3.5
- Build Tool : Gradle
- Language : Java 21
- Database Library : JPA
- Database Server : MariaDB 10.11.8


### API 테스트
http://localhost:8080 에서 API 테스트 가능
