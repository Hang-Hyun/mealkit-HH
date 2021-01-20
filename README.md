# mealkit-HH


밀키트 판매

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW


# Table of contents

- [예제 - 밀키트판매](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
     1. SAGA
     2. CQRS
     3. Correlation
     4. Req/Res
  - [운영](#운영)
     5. Gateway
     6. Deploy
     7. CB
     8. HPA
     9. Readiness


# 서비스 시나리오


기능적 요구사항

1. 판매자가 상품을 등록한다.
2. 회원이 상품을 선택하여 주문한다.
3. 고객이 결제하고, 결제완료되면 배송 요청된다.
4. 결제완료되면 재고수량 변경을 한다.
5. 회원이 배송완료처리하면 배송완료된다.
6. 고객이 주문 취소할 수 있다.
7. 주문취소되면 배송이 취소된다.
8. 주문취소되면 결제가 취소된다.
9. 고객이 주문내역(상태)를 중간중간 조회가능하다.


***10. Marketing 서비스에 payment 완료되면 노티가 간다**** 

비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출
    
1. 장애격리
    1. 상품관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다  Circuit breaker, fallback
1. 성능
    1. 고객이 수시로 주문상태를 마이페이지에서 확인할 수 있어야 한다  CQRS




## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  


### 완성된 1차 모형
![image](https://user-images.githubusercontent.com/75401920/104998076-f9929380-5a6d-11eb-8ac9-1ba95cea971f.png)


#### 개인과제 추가모형 

![image](https://user-images.githubusercontent.com/75401873/105170529-66865600-5b60-11eb-9f93-817a00e3b94a.png)



# 구현: 


--개인 AZURE 에 배포완료 확인 __ (5.6 Gateway, Deploy) 

![image] (https://user-images.githubusercontent.com/75401873/105173898-0c3bc400-5b65-11eb-9613-0d0fce1cfe08.png)

1,3. 주문->결제->배송->주문 캡쳐


 - 주문 등록

![image](https://user-images.githubusercontent.com/75401873/105171349-75213d00-5b61-11eb-8b69-910dacddd8a3.png)

 - 주문 등록 후 결제 내역 조회

![image](https://user-images.githubusercontent.com/75401873/105171814-055f8200-5b62-11eb-97c3-2963658f1053.png)

 **- ""메세지"" 변경  (개인과제 구현한 부분)** 

![image](https://user-images.githubusercontent.com/75401920/105002205-3e212d80-5a74-11eb-9d3a-469df1f27d49.png)

 - 주문취소

![image](https://user-images.githubusercontent.com/75401920/105002335-6dd03580-5a74-11eb-860d-66d4062bd18f.png)

 - 주문취소 시 결제 취소 반영됨

![image](https://user-images.githubusercontent.com/75401920/105002401-95270280-5a74-11eb-89c9-069db87220e6.png)

 - 결제취소시 배송 취소
 
![image](https://user-images.githubusercontent.com/75401920/105002466-acfe8680-5a74-11eb-91ba-bc04509a8b10.png)


2. 마이페이지 조회

![image](https://user-images.githubusercontent.com/75401920/105002605-e8995080-5a74-11eb-99ad-15cdb20324ad.png)


3. 결제서비스 장애 시 주문 불가

![image](https://user-images.githubusercontent.com/75401920/105002912-52b1f580-5a75-11eb-8ce0-b661fbbcc1d3.png)



7. Istio 적용 캡쳐

  - Istio테스트를 위해서 Payment에 sleep 추가
  
![image](https://user-images.githubusercontent.com/75401920/105005616-e89b4f80-5a78-11eb-82cb-de53e5881e3f.png)

 - payments 서비스에 Istio 적용
   
![image](https://user-images.githubusercontent.com/75401920/105006822-7f1c4080-5a7a-11eb-9191-db35233773d3.png)

 - Istio 적용 후 seige 실행 시 대략 50%정도 확률로 CB가 열려서 처리됨

![image](https://user-images.githubusercontent.com/75401920/105006958-b2f76600-5a7a-11eb-99f3-c8b81a4ec270.png)

8. AutoScale

 - AutoScale 적용된 모습

![image](https://user-images.githubusercontent.com/75401920/105006642-4714fd80-5a7a-11eb-8424-aa2dede45666.png)

 - AutoScale로  order pod 개수가 증가함

![image](https://user-images.githubusercontent.com/75401920/105006308-cf46d300-5a79-11eb-96db-77d865c9bfe9.png)


9. Readness Proobe
 
  - Readiness 적용 전: 소스배포시 500 오류 발생
  
![image](https://user-images.githubusercontent.com/75401920/105004548-7d04b280-5a77-11eb-95cb-d5fe19a40557.png)


  - 적용 후: 소스배포시 100% 수행됨

![image](https://user-images.githubusercontent.com/75401920/105004912-f0a6bf80-5a77-11eb-88ee-f0bcd8f67f45.png)

