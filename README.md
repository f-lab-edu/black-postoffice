# black-postoffice
- 익명으로 편하게 고민, 일상을 공유하는 소셜 네트워크 서비스입니다.

### WIKI
해당 프로젝트의 모든 정보는 [WIKI](https://github.com/f-lab-edu/black-postoffice/wiki) 를 참고하실 수 있습니다.

- [1. 프로젝트 소개](https://github.com/f-lab-edu/black-postoffice/wiki/1.-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C)
- [2. 이슈 및 트러블 슈팅 포스팅](https://github.com/f-lab-edu/black-postoffice/wiki/2.-Issue%EB%B0%8F-trouble-shooting-Posting)
- [3. 프로토타입](https://github.com/f-lab-edu/black-postoffice/wiki/3.-%ED%94%84%EB%A1%9C%ED%86%A0%ED%83%80%EC%9E%85)
- [4. 이슈, 풀리퀘스트, 커밋 템플릿](https://github.com/f-lab-edu/black-postoffice/wiki/4.-issue,-pr,-commit-Template)
- [5. 브렌치 관리 전략](https://github.com/f-lab-edu/black-postoffice/wiki/5.-%EB%B8%8C%EB%A0%8C%EC%B9%98-%EA%B4%80%EB%A6%AC-%EC%A0%84%EB%9E%B5)
- [6. 이펙티브 코틀린](https://github.com/f-lab-edu/black-postoffice/wiki/6.-%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%BD%94%ED%8B%80%EB%A6%B0)

### API 문서
- [RestDocs를 적용하여 API 문서화를 진행하였습니다.](https://hyung1jung.github.io/)

### 현재까지의 사용기술 
![기술 스택](https://user-images.githubusercontent.com/43127088/125194910-4c673e00-e28e-11eb-80c8-1a3bda1be8df.PNG)

### 현재까지의 서버 구조도
![서버 구조도](https://user-images.githubusercontent.com/43127088/127910532-169fb9e6-9679-465e-aa33-0010c3f10d50.png)

### 프로젝트 주요 관심사
- 여러 기술들의 트레이드 오프를 고려한 후, 어떤 기술을 도입하는 것이 가장 서비스에 가장 적합할까?
- 다른 개발자가 내 코드를 보았다고 가정할 때, 어떻게 코드를 작성하는 것이 이해하기 쉬운 코드이며 유지보수에 용이한 코드일까?
- 꾸준히, 깊은 이론 학습을 통해 내가 작성한 코드를 어떻게 더 좋은 코드로 만들까?
- 대용량 트래픽의 상황에서 지속적인 서버 개선을 위해 어떻게 성능을 튜닝할까?
- 위의 관심사들을 기반으로 고민한 이슈들을 [기술블로그](https://junghyungil.tistory.com/category/Black-postOffice) 에 작성하고 팀 내 공유하여 함께 성장해가는 개발 문화 추구  

### 코틀린 사용 이유
- 자바에서는 형식을 맞추기 위해 무의미하고 반복적인 코드를 작성하게 되어 항상 코드가 비대해지기 마련이었습니다. 저는 이 문제를 해결하기 위해 코틀린을 사용하였습니다.
- 코틀린은 타입 추론과 더불어 자체적으로 함수형 프로그래밍을 지원해주기 때문에 자바에 비해 훨씬 코드를 간결하게 작성할 수
  있었으며 개발 생산성과 유지 보수성 면에서도 훨씬 좋은 성과를 거두었습니다.
- 또한 코틀린은 별도의 표기가 없는 경우 널 값을 허용하지 않기 때문에 자바에서 흔히 발생하는 널 포인터 예외를 피할 수 있었습니다.
  이는 널 포인터 때문에 프로그램이 중단되는 현상을 방지하는 성과를 거둘 수 있었습니다.