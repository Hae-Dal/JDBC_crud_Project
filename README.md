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
    - [ ] **SQL 작성하기**
        - [ ] 설치한 데이터베이스(Mysql)에 ERD를 따라 테이블 생성

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
        - Endpoint : `/api/schedule/{scheduleId}`
        - Path Parameters
          - scheduleId (String) : 조회하려는 일정의 고유 ID
        - Request Syntax
          ```json
            GET /api/schedule/{scheduleId} HTTP/1.1
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
            GET /api/schedule/{scheduleId} HTTP/1.1
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
                }
            ]
            
      - **일정 수정**
        - HTTP Method : `PUT`
        - Endpoint : `/api/schedule/{scheduleId}`
        - Path Parameters
            - scheduleId (String) : 수정하려는 일정의 고유 ID
        - Request Syntax
          ```json
              PUT /api/schedule/{scheduleId} HTTP/1.1
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
        - Endpoint : `/api/schedule/{scheduleId}`
        - Path Parameters
            - scheduleId (String) : 삭제하려는 일정의 고유 ID
        - Request Syntax
          ```json
            DELETE /api/schedule/{scheduleId} HTTP/1.1
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

      ![](/read_me_img/erd.png)

### LV. 2 일정 생성 및 조회
- 요구사항 정의
  - [x] **일정 생성**
      - [x]  일정 생성 시, 포함되어야 할 데이터 - `할일`, `작성자명`, `비밀번호`, `작성 수정일`
      - [x]  각 일정의 고유 식별자를 자동으로 생성 후 관리
      - [x]  최초 입력 시, 수정일은 작성일과 동일하게 설정
  - [ ] **전체일정 조회**
    - [ ] 수정일 또는 작성자명을 바탕으로 등록된 일정을 모두 조회
    - [ ] 조건 중 한 가지만을 충족하거나 둘 다 충족할 수도 있음
    - [ ] 수정일 기준 내림차순 정렬
  - [ ] **선택 일정 조회**
    - [ ]선택한 일정의 단건 정보 조회
    - [ ]고유 식별자를 사용하여 조회