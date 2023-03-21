## Jar 다운로드 링크
> https://drive.google.com/file/d/1RcFtlX0i7qZSlEf2YFhqeIGtDc3NqFLY/view?usp=sharing

## Git 주소
> https://github.com/dang7323/project01

## 빌드
> gradlew build

## 실행
> java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
> 
## 새롭게 구현한 기능
네이버 블로그 검색 구현
네이버 블로그 검색시 장애 발생하면 카카오 블로그로 검색
카카오 블로그 검색시 장애 발생하면 네이버 클로그로 검색

## API 구조

#### kakao blog controller
> GET /kakao (카카오 블로그 검색)
##### request body
- keyword : 검색을 원하는 질의어
- sort : 결과 문서 정렬 방식(accuracy-정확도 | recency-최신순, 기본값 accuracy)
- size : 한 페이지에 보여질 문서 수 
- page : 결과 페이지 번호
#### response body
- title : 제목
- contents : 컨텐츠
- blogname : 블로그 이름
- thumbnail : 썸네일
- datetime : 게시물 생성일

#### naver blog controller
> GET /naver (네이버 블로그 검색)
#### request body
- keyword : 검색을 원하는 질의어
- sort : 결과 문서 정렬 방식(sim-정확도 | date-최신순, 기본값 sim)
- start : 결과 페이지 번호
- display : 한 페이지에 보여질 문서 수
#### response body
- title : 제목
- contents : 컨텐츠
- blogname : 블로그 이름
- thumbnail : 썸네일
- datetime : 게시물 생성일

#### trend controller
> GET /trend (검색 트렌드 조회)

#### response body
- keyword : 검색어
- count : 검색 횟수


