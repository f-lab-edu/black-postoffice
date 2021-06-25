# black-postoffice
- 위치 기반의 남이 올려주는 인스타그램 텍스트 버전
- 여러 사람이 볼 수 있는 피드에 올릴 글(편지, 텍스트)을 본인이 직접 올리는 것이 아니라, 
  나만 볼 수 있는 피드(우체통)에 올라온 글(편지, 텍스트)를 올려주는 형식

### WIKI
해당 프로젝트의 모든 정보는 [WIKI](https://github.com/f-lab-edu/shoe-auction/wiki) 를 참고하실 수 있습니다.

- [프로젝트 소개 및 기능정의](https://github.com/f-lab-edu/black-postoffice/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C)
- [issue, pull Request, commit Template](https://github.com/f-lab-edu/black-postoffice/wiki/issue,-pull-Request,-commit-Template)
- [Issue및 trouble shooting Posting](https://github.com/f-lab-edu/black-postoffice/wiki/Issue%EB%B0%8F-trouble-shooting-Posting)

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
- 유지보수 하기 쉬운 코드 및 커뮤니케이션을 위한 코드에 관한 끝없는 고민
- 대용량 트래픽의 상황에서 지속적인 서버 개선을 위한 성능 튜닝
- 책과 도큐먼트를 통해 얻은 깊은 이론을 통해 깊은 설계 지향

### 브렌치 관리 전략
&nbsp;&nbsp;&nbsp;&nbsp; Git-Flow 를 이용하여 브랜치를 관리하였습니다.

<p align="center">
  <img src="https://user-images.githubusercontent.com/54772162/101170794-45d27180-3682-11eb-8c42-6f4bf8ec73c9.PNG?raw=true" alt="Sublime's custom image"/>
</p>

✔️ master : 배포시 사용할 브랜치. 초기 시행착오에 의하여 몇몇 기능이 merge 되어 있으나, 원래 사용 용도는 완벽히 배포가 가능한 상태에만 merge가 되어야만 합니다.        
✔️ develop : 다음 버전을 개발하는 브랜치, 완전히 배포가 가능하다고 생각되면 master 브랜치에 merge 합니다.    
✔️ feature : 기능을 개발하는 브랜치    
✔️ release : 배포를 준비할 때 사용할 브랜치    
✔️ hotfix : 배포 후에 발생한 버그를 수정 하는 브랜치

