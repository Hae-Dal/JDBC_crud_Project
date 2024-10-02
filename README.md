# 일정 관리 앱 만들기
feat.내일배움 캠프

---

## 필수 기능
### LV. 1 API 명세 및 ERD 작성
- 요구사항 정의
    - [x]  **API 명세서 작성하기**
        - [x]  API명세서는 프로젝트 root(최상위) 경로의 `README.md` 에 작성
    - [x]  **ERD 작성하기**
        - [x]  ERD는 프로젝트 root(최상위) 경로의 `README.md` 에 첨부
    - [x] **SQL 작성하기**
        - [x] 설치한 데이터베이스(Mysql)에 ERD를 따라 테이블 생성

- 설계
    - API 명세서
      - **일정 등록**
        - HTTP Method : `POST`
        - Endpoint : `/api/schedule`
        - Request Syntax
          ```json
            POST /api/schedule HTTP/1.1
            Host: {localhost}
            Content-Type: application/json
        - Request Body
          ```json
            {
                "title": "string",
                "content": "string",
                "postTime": "YYYY-MM-DD HH:mm:ss",
                "userName": "string",
                "password": "string"
            }
        - Responses
          ```
            200 OK:
            {
              "message": "Schedule successfully registered",
              "scheduleId": "string"
            }
            
      - **일정 조회**
        - HTTP Method : `GET`
        - Endpoint : `/api/schedule/{id}`
        - Path Parameters
          - scheduleId (String) : 조회하려는 일정의 고유 ID
        - Request Syntax
          ```json
            GET /api/schedule/{id} HTTP/1.1
            Host: {localhost}
            Accept: application/json
        - Responses
          ```
            200 OK:
            {
                "scheduleId": "string",
                "title": "string",
                "content": "string",
                "postTime": "YYYY-MM-DD HH:mm:ss",
                "updatedTime": "YYYY-MM-DD HH:mm:ss",
                "userName": "string",
                "password": "string"
            }
            
      - **일정 목록 조회**
        - HTTP Method : `GET`
        - Endpoint : `/api/schedule`
        - Request Syntax
          ```json
            GET /api/schedule HTTP/1.1
            Host: {localhost}
            Accept: application/json
        - Responses
          ```
            200 OK:
            [
                {
                    "scheduleId": "string",
                    "title": "string",
                    "content": "string",
                    "postTime": "YYYY-MM-DD HH:mm:ss",
                    "updatedTime": "YYYY-MM-DD HH:mm:ss",
                    "userName": "string",
                    "password": "string"
                },
                {
                    ...
                }...
            ]
            
      - **일정 수정**
        - HTTP Method : `PUT`
        - Endpoint : `/api/schedule/{id}`
        - Path Parameters
            - scheduleId (String) : 수정하려는 일정의 고유 ID
        - Request Syntax
          ```json
              PUT /api/schedule/{id} HTTP/1.1
              Host: {localhost}
              Accept: application/json
        - Request Body
          ```json
            {
                "scheduleId": "string",
                "title": "string",
                "content": "string",
                "postTime": "YYYY-MM-DD HH:mm:ss",
                "userName": "string",
                "password": "string"
            }
        - Responses
          ```
            200 OK:
            {
                "message": "Schedule successfully updated"
            }
            404 Not Found:
            {
                "error": "Schedule not found"
            }

      - **일정 삭제**
        - HTTP Method : `DELETE`
        - Endpoint : `/api/schedule/{id}/{password}`
        - Path Parameters
            - scheduleId (String) : 삭제하려는 일정의 고유 ID
        - Request Syntax
          ```json
            DELETE /api/schedule/{id}/{password} HTTP/1.1
            Host: {localhost}
            Accept: application/json
        - Responses
          ```
            200 OK:
            {
              "message": "Schedule successfully deleted"
            }
            404 Not Found:
            {
              "error": "Schedule not found"
            }

    - ERD(Entity Relationship Diagram)

      ![](/read_me_img/erd.PNG)

### LV. 2 일정 생성 및 조회
- 요구사항 정의
  - [x] **일정 생성**
      - [x]  일정 생성 시, 포함되어야 할 데이터 - `할일`, `작성자명`, `비밀번호`, `작성 수정일`
      - [x]  각 일정의 고유 식별자를 자동으로 생성 후 관리
      - [x]  최초 입력 시, 수정일은 작성일과 동일하게 설정
  - [x] **전체일정 조회**
    - [x] 수정일 또는 작성자명을 바탕으로 등록된 일정을 모두 조회
    - [x] 조건 중 한 가지만을 충족하거나 둘 다 충족할 수도 있음
    - [x] 수정일 기준 내림차순 정렬
  - [x] **선택 일정 조회**
    - [x] 선택한 일정의 단건 정보 조회
    - [x] 고유 식별자를 사용하여 조회

### LV. 3 일정 수정 및 삭제
- 요구사항 정의
  - [x] **선택 일정 수정**
    - [x]  선택한 일정 내용 중 `할일`, `작성자명` 만 수정 가능
    - [x]  서버에 일정 수정을 요청할 때 비밀번호를 함께 전달
    - [x]  작성일 은 변경할 수 없으며, 수정일 은 수정 완료 시, 수정한 시점으로 변경
  - [x] **선택 일정 삭제**
    - [x] 선택한 일정을 삭제 가능
    - [x] 서버에 일정 수정을 요청할 때 비밀번호를 함께 전달

## 도전 기능
### LV. 4 연관 관계 설정
- 요구사항 정의
  - [x] **작성자와 일정의 연결**
    - [x] 동명이인의 작성자가 있어 어떤 작성자가 등록한 ‘할 일’인지 구별할 수 없음
    - [x]  작성자를 식별하기 위해 이름으로만 관리하던 작성자에게 고유 식별자를 부여합니다.
    - [x]  작성자 테이블을 생성하고 일정 테이블에 FK를 생성해 연관관계를 설정해 봅니다.
  - [x] **조건**
    - [x] 작성자는 `이름` 외에 `이메일`, `등록일`, `수정일` 정보를 가지고 있습니다.
      - [ ] 작성자의 정보는 추가로 받을 수 있습니다.(조건만 만족한다면 다른 데이터 추가 가능) &rarr; `(이거 무슨 소리인지 모르겠음)`
    - [x] 고유 식별자를 통해 작성자를 조회할 수 있도록 기존 코드를 변경합니다.
    - [x] 작성자의 고유 식별자가 일정 테이블의 외래키가 될 수 있도록 합니다.

### Lv 5. 페이지네이션
- 요구사항 정의
  - [ ] 설명
    - [ ] 많은 양의 데이터를 효율적으로 표시하기 위해 데이터를 여러 페이지로 나눕니다.
    - [ ] `페이지 번호`와 `페이지 크기`를 쿼리 파라미터로 전달하여 요청하는 항목을 나타냅니다.
    - [ ] 전달받은 페이지 번호와 크기를 기준으로 쿼리를 작성하여 필요한 데이터만을 조회하고 반환
  - [ ] 조건
    - [ ] 등록된 일정 목록을 `페이지 번호`와 `크기`를 기준으로 모두 조회
      - [ ] 조회한 일정 목록에는 `작성자 이름`이 포함
      - [ ] 범위를 넘어선 페이지를 요청하는 경우 빈 배열을 반환
      - [ ] Paging 객체를 활용할 수 있음