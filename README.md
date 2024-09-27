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