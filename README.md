# twitter-clone-backend
- 노마드코더 트위터 클론 코딩의 파이어베이스 백엔드를 스프링 MVC로 구현해보았습니다.
    - [https://nomadcoders.co/nwitter](https://nomadcoders.co/nwitter)
- JWT + 스프링 시큐리티 인증
    - 인프런 jwt 튜토리얼을 기반으로
        - [https://www.inflearn.com/course/스프링부트-jwt/dashboard](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/dashboard)
    - refresh token 적용
        - 요청의 Authorization 헤더로 보내진 토큰을 먼저 access token의 키를 이용해 유효성을 확인하고, 유효하지 않은 경우 refresh token의 키로 유효성을 확인해 인증한다. refresh token의 키로 유효성이 확인된 경우에는 응답의  두 번의 유효성 확인이 모두 실패하는 경우 401 Unauthorized 응답을 반환한다.
        - 프론트엔드는 기본적으로 Authorization 헤더에 access token을 담아 요청을 한다. 요청의 결과 401 응답을 받는 경우  Authorization 헤더에 refresh token을 담아서 같은 요청을 보낸다.
        - refresh token을 DB에 저장해두고 요청으로 받은 refresh token을 비교하여 토큰을 강제로 만료시킬 수 있게 하는 기능은 구현하지 않음
