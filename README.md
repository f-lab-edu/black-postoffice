# black-postoffice
- 익명으로 편하게 고민, 일상을 공유하는 소셜 네트워크 서비스입니다.

### WIKI
해당 프로젝트의 모든 정보는 [WIKI](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C) 를 참고하실 수 있습니다.

- [프로젝트 소개 및 기능정의](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C)
- [프로토 타입 - 카카오 오븐을 활용해서 작성하였습니다.](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%ED%86%A0%ED%83%80%EC%9E%85)
- [ERD](https://github.com/f-lab-edu/black-postoffice/wiki/ERD)  
- [issue, pull Request, commit Template](https://github.com/f-lab-edu/black-postoffice/wiki/issue,-pull-Request,-commit-Template)
- [브렌치 관리 전략](https://github.com/f-lab-edu/black-postoffice/wiki/%EB%B8%8C%EB%A0%8C%EC%B9%98-%EA%B4%80%EB%A6%AC-%EC%A0%84%EB%9E%B5)  
- [Issue및 trouble shooting Posting](https://github.com/f-lab-edu/black-postoffice/wiki/Issue%EB%B0%8F-trouble-shooting-Posting)
- [이펙티브 코틀린, 이펙티브 자바를 기반으로](https://github.com/f-lab-edu/black-postoffice/wiki/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%BD%94%ED%8B%80%EB%A6%B0,-%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94%EB%A5%BC-%EA%B8%B0%EB%B0%98%EC%9C%BC%EB%A1%9C)

### 사용기술
![기술 스택](https://user-images.githubusercontent.com/43127088/125194910-4c673e00-e28e-11eb-80c8-1a3bda1be8df.PNG)

추가예정

### API 문서
- [RestDocs를 적용하여 API 문서화를 진행하였습니다.](https://hyung1jung.github.io/)

### 기능
- 회원가입
- 로그인
- 사용자 역할(비사용자, 사용자, 관리자)에 따른 접근 제한
- 로그아웃
- 회원탈퇴 
- 회원 정보 수정 - 프로필 사진 등록, 삭제 / 비밀번호 수정 / 닉네임 수정  / 주소 수정
- 자신의 피드에 글 작성
- 전체 사용자 글 최신순으로 조회
- 댓글, 대댓글 작성  
- 팔로잉, 팔로잉 취소, 팔로우
- 1대1 대화 신청시 결제 기능

기능에 관한 자세한 설명은 [여기](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C) 를 참고해주세요.

### 전체 아키텍처

추가 예정

### 이슈 및 트러블 슈팅 포스팅

- [gradlew (gradle wrapper)란](https://www.notion.so/gradlew-gradle-wrapper-98743215d59743f6ac8057e706ae506c)
- [implementation와 api(compile)](https://junghyungil.tistory.com/148?category=892281)
- [kotlin에서 lombok이 필요할까? data Class와 비교하여 고찰](https://junghyungil.tistory.com/157?category=953012)
- [유지보수하기 좋은 코드 및 확장성을 고려한 설계에 관한 고찰](https://junghyungil.tistory.com/158?category=953012)
- [Profile](https://junghyungil.tistory.com/159?category=892281)
- [log4j vs Lockback vs log4j2, 프로젝트에 무엇을 적용하는게 좋을까?](https://junghyungil.tistory.com/160?category=892281)
- [사용자가 늘어나면 서버를 어떻게 확장해야할까? 스케일 업(Scale-up) vs 스케일 아웃(Scale-out)](https://junghyungil.tistory.com/151?category=952148)
- [Redis vs memcached vs RDBMS](https://junghyungil.tistory.com/165)
- [Redis(Remote dictionary server)란?](https://junghyungil.tistory.com/162)
- [Redis 연동하기 및 Spring Boot Redis Docs 살펴보기](https://junghyungil.tistory.com/166)
- [프로필 이미지 AWS S3에 업로드](https://junghyungil.tistory.com/171)
- [지속적 통합의 개념 - 애자일 방법론과 폭포수 모델](https://junghyungil.tistory.com/170)
- [CI란?, 어떤 CI 도구를 사용할까?](https://junghyungil.tistory.com/167)
- [젠킨스란? 젠킨스를 이용해 CI 자동화하기, 슬랙 연동](https://junghyungil.tistory.com/168)
- [젠킨스와 슬랙 연동하여 알람받기](https://junghyungil.tistory.com/169)

추가 작성 예정
- 추가 작성 예정

그 외 프로젝트를 하며 공부헀던 것들
- [그 외](https://github.com/Hyung1Jung/LearnKit)

### 코틀린 사용 이유
- **코틀린**의 가장 큰 장점은 간결성과 효율성이다. 형식을 맞추기 위한 코드나 무의미하고 반복적인 코드들을 제거해 간결하고
  효율적인 문제 해결이 가능합니다. **코틀린**은 문법 자체가 깔끔하기 때문에 간결한 코드 작성을 통해 편리한 유지보수가 가능합니다.
- 자바는 기본적으로 초기화되지 않은 객체 참조 변수에 널 값을 할당합니다. 하지만, **코틀린**은 별도의 표기가 없는 경우 널 값을 허용하지 않습니다.
  그렇기 때문에 널 포인터 예외로부터 자유롭습니다. 자바에 비해 **코틀린**은 자바에서 흔히 발생하는 오류인 ‘널 포인터 예외(NullPointerException)’를
  피할 수 있습니다. 이 덕분에 널 포인터 때문에 프로그램이 중단되는 현상을 예방할 수 있습니다.
- Java와 100% 상호 호환하고 JVM에서 동작합니다.
- [이펙티브 자바의 내용들을 코틀린에서는 어떻게 적용할지에 관해 정리한 글](https://github.com/f-lab-edu/black-postoffice/wiki/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94%EC%9D%98-%EB%82%B4%EC%9A%A9%EB%93%A4%EC%9D%84-%EC%BD%94%ED%8B%80%EB%A6%B0%EC%97%90%EC%84%9C%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%A0%81%EC%9A%A9%ED%95%A0%EC%A7%80%EC%97%90-%EA%B4%80%ED%95%B4-%EC%A0%95%EB%A6%AC%ED%95%9C-%EA%B8%80)

### 프로젝트 주요 관심사
- 명확한 이유와 근거를 가지고 여러 기술들의 트레이드 오프를 고려하여 성능이 가장 뛰어난 기술 도입
- 실제 현업으로 가정했을때, 신입 개발자가 출근 첫 날에 코드를 보더라도 이해하기 쉬운 코드란 뭘까에 관한 끝없는 고민
- 유지보수 하기 쉬운 코드 및 커뮤니케이션을 위한 코드에 관한 끝없는 고민
- 대용량 트래픽의 상황에서 지속적인 서버 개선을 위한 성능 튜닝
- 책과 도큐먼트를 통해 얻은 깊은 이론을 통해 깊은 설계 지향
- 위의 관심사들을 기반으로 고민한 이슈들을 [기술블로그](https://junghyungil.tistory.com/category/Black-postOffice) 에 작성하고 팀 내 공유하여 함께 성장해가는 개발 문화 추구  

