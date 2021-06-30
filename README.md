# black-postoffice
- 위치 기반의 남이 올려주는 인스타그램 텍스트 버전

### WIKI
해당 프로젝트의 모든 정보는 [WIKI](https://github.com/f-lab-edu/shoe-auction/wiki) 를 참고하실 수 있습니다.

- [프로젝트 소개 및 기능정의](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C)
- [프로토 타입 - 카카오 오븐을 활용해서 작성하였습니다.](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%ED%86%A0%ED%83%80%EC%9E%85)
- [ERD](https://github.com/f-lab-edu/black-postoffice/wiki/ERD)  
- [issue, pull Request, commit Template](https://github.com/f-lab-edu/black-postoffice/wiki/issue,-pull-Request,-commit-Template)
- [브렌치 관리 전략](https://github.com/f-lab-edu/black-postoffice/wiki/%EB%B8%8C%EB%A0%8C%EC%B9%98-%EA%B4%80%EB%A6%AC-%EC%A0%84%EB%9E%B5)  
- [Issue및 trouble shooting Posting](https://github.com/f-lab-edu/black-postoffice/wiki/Issue%EB%B0%8F-trouble-shooting-Posting)
- [이펙티브 코틀린, 이펙티브 자바를 기반으로](https://github.com/f-lab-edu/black-postoffice/wiki/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%BD%94%ED%8B%80%EB%A6%B0,-%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94%EB%A5%BC-%EA%B8%B0%EB%B0%98%EC%9C%BC%EB%A1%9C)

### 사용기술
`kotlin`, `springboot`, `gradle`, `mybatis`, `redis`, `mysql` + 추가 예정

### 기능 명세

### 전체 아키텍처

### 코틀린 사용 이유
- **코틀린**의 가장 큰 장점은 간결성과 효율성이다. 형식을 맞추기 위한 코드나 무의미하고 반복적인 코드들을 제거해 간결하고
  효율적인 문제 해결이 가능합니다. **코틀린**은 문법 자체가 깔끔하기 때문에 간결한 코드 작성을 통해 편리한 유지보수가 가능합니다.
- 자바는 기본적으로 초기화되지 않은 객체 참조 변수에 널 값을 할당합니다. 하지만, **코틀린**은 별도의 표기가 없는 경우 널 값을 허용하지 않습니다.
  그렇기 때문에 널 포인터 예외로부터 자유롭습니다. 자바에 비해 **코틀린**은 자바에서 흔히 발생하는 오류인 ‘널 포인터 예외(NullPointerException)’를
  피할 수 있습니다. 이 덕분에 널 포인터 때문에 프로그램이 중단되는 현상을 예방할 수 있습니다.
- Java와 100% 상호 호환하고 JVM에서 동작합니다.
- [이펙티브 자바의 내용들을 코틀린에서는 어떻게 적용할지에 관해 정리한 글](https://github.com/f-lab-edu/black-postoffice/wiki/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94%EC%9D%98-%EB%82%B4%EC%9A%A9%EB%93%A4%EC%9D%84-%EC%BD%94%ED%8B%80%EB%A6%B0%EC%97%90%EC%84%9C%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%A0%81%EC%9A%A9%ED%95%A0%EC%A7%80%EC%97%90-%EA%B4%80%ED%95%B4-%EC%A0%95%EB%A6%AC%ED%95%9C-%EA%B8%80)

### 프로젝트 주요 관심사
- 이유와 근거가 명확한 기술의 사용
- 실제 현업으로 가정했을때, 신입 개발자가 출근 첫 날에 코드를 보더라도 이해하기 쉬운 코드란 뭘까에 관한 끝없는 고민
- 이해, 유지보수하기 쉬운 코드 및 커뮤니케이션을 위한 코드에 관한 끝없는 고민
- 대용량 트래픽의 상황에서 지속적인 서버 개선을 위한 성능 튜닝
- 책과 도큐먼트를 통해 얻은 깊은 이론을 통해 깊은 설계 지향

