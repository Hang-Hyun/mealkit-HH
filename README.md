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


### 완성된 1차 모형
![image](https://user-images.githubusercontent.com/75401920/104998076-f9929380-5a6d-11eb-8ac9-1ba95cea971f.png)


#### 개인과제 추가모형 

![image](https://user-images.githubusercontent.com/75401873/105170529-66865600-5b60-11eb-9f93-817a00e3b94a.png)



# 구현: 


--개인 AZURE 에 배포완료 확인 __ (5.6 Gateway, Deploy) 

![image](https://user-images.githubusercontent.com/75401873/105173898-0c3bc400-5b65-11eb-9613-0d0fce1cfe08.png)

1,3. 주문->결제->배송->주문 캡쳐


 - 주문 등록

![image](https://user-images.githubusercontent.com/75401873/105171349-75213d00-5b61-11eb-8b69-910dacddd8a3.png)

 - 주문 등록 후 결제 내역 조회

![image](https://user-images.githubusercontent.com/75401873/105171814-055f8200-5b62-11eb-97c3-2963658f1053.png)

 **- ""메세지"" 변경  (개인과제 구현한 부분)** 

![image](https://user-images.githubusercontent.com/75401873/105271699-53fd3280-5bdb-11eb-8bbc-81f1d1e4ae0a.png)

![image](https://user-images.githubusercontent.com/75401873/105272523-dfc38e80-5bdc-11eb-85c3-a13a50daba5a.png)


 - 주문취소

![image](https://user-images.githubusercontent.com/75401873/105272086-033a0980-5bdc-11eb-97c5-583c2ca1c9f6.png)

 - 주문취소 시 결제 취소 반영됨

![image](https://user-images.githubusercontent.com/75401920/105002401-95270280-5a74-11eb-89c9-069db87220e6.png)






7. Istio 적용 (실패) 

 - payments 서비스에 Istio 적용
   
![image](https://user-images.githubusercontent.com/75401920/105006822-7f1c4080-5a7a-11eb-9191-db35233773d3.png)

 - Istio 적용 후 seige 실행 시 에러만 남 ㅠㅠㅠㅠ 

![image](https://user-images.githubusercontent.com/75401873/105277719-4cdc2180-5be7-11eb-9592-93d3f033e848.png)


