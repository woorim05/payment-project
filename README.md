# Payments-API-Example
결제 수단 API 구현 샘플

## 💻 프로젝트 소개
* 'openFeign' 을 활용한 결제 수단 별 샘플 RESTful API 구현
  * [카카오페이](https://github.com/woorim05/payment-project/tree/kakaoPay)
  * [네이버페이](https://github.com/woorim05/payment-project.git)
  * [페이코](https://github.com/woorim05/payment-project.git)
  * [이니시스](https://github.com/woorim05/payment-project.git)
  * [토스페이](https://github.com/woorim05/payment-project.git)
  * [애플페이](https://github.com/woorim05/payment-project.git)

## 🛠️ 개발 환경
* JAVA 17
* Framework: SpringBoot(3.x)
* DataBase: Redis(Amazon-ElastiCache), postgreSQL
* API: Swagger
* Dependencies: build.gradle 참고
  * lombok
  * openFeign
  * jsonObject

![springboot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white) 
![gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white) 
![elastiCache](https://img.shields.io/badge/Amazon_ElastiCache-C925D1?style=for-the-badge&logo=AmazonElastiCache&logoColor=white)
![postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

### 💡 환경 변수 설정
개발자 local 환경에서는 OS 환경 변수로 등록하고 개발.
|Name|설명|
|---|---|
|REDIS_HOST|redis(elastiCache) host|
|CLIENT_REDIRECT_URL|ready api 이후 redirect url|
|KAKAO_SECRET_KEY|카카오페이 인증 키|

### 📚 결제 수단 별 API 문서
* [카카오페이](https://developers.kakaopay.com/docs/payment/online/single-payment)
* [네이버페이](https://developer.pay.naver.com/docs/v2/api#common-common_certi)
* [페이코](https://devcenter.payco.com/guide/online/easypay/reserve?id=220401002)
* [이니시스](https://manual.inicis.com/pay/stdpay_pc.html)
* [토스페이](https://tossdev.github.io/gettingstarted.html#overview-1)
* [애플페이](http://developer.apple.com)