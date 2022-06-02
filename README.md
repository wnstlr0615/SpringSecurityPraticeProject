# 스프링 시큐리티 설정

 ---
본 프로젝트는 스프링  시큐리티 설정과 각 필터들의 처리와 과련될 실습 프로젝트입니다.


### 요약 정리
외부로 부터 요청이 들어올 경우 인증과 인과를 확인 하게 되는데 인증을 담당하는 필터(UsernamePasswordAuthenticationFilter, BasicAuthenticationFilter, 둥)에서
사용자 로그인 요청 정보를 가지고 토큰을 만들어 AuthenticationManager 에게 인증을 위임 한다. 주로 AuthenticationManager 에 구현체인 ProviderManager 가 해당 토큰에 맞는
Provider를 찾아 인증 절차를 위임한다. UsernamePasswordToken 에 경우 DaoAuthenticationManager 처리 하여 인증을 담당한다.  
만약 인증에 실패할 경우 AuthenticationException 을 가지고 다음 필터로 이동 한다.
인증에 성공할 경우 SecurityContextHolder 에 있는 SecurityContext 에 Authentication 인증 정보를 넣어주며 config에서 정의한 인가 정책에 따라
FilterSecurityInterceptor 에서 인증과 인과 검사를 확인하며 문제가 있을 경우 ExceptionTranslationFilter 에서  AuthenticationException이나 AccessDeniedException을 발생 시켜준다. 

### 주요 키워드 정리
- Authentication : 사용자 인증 정보를 담은 객체  
- Principal :  사용자 정보
- authorization : 인가 정보
- AuthenticationManager - 인증 관련 매니저(ex. ProviderManager)
- AccessDecisionManger - 인가 관련 매니저(ex. AffirmativeBased)

### 주요 필터 정리

1. SecurityContextPersistenceFilter
    - 이전에 로그인 한 정보가 있다면 Session으로 부터 SecurityContext 정보를 가져와 입력하고 없다면 새로운 SecurityContext를 SecurityContextHolder에 설정해준다.
    - 매 요청마다 새로 SecurityContext를 SecurityContextHolder에 넣어주며 요청의 끝에 SecurityContextHolder를 비워준다.
2. CsrfFilter
   - 웹페이지 요청 시 부여한 csrf 토큰과 요청 시 들어온 csrf 토큰 일치 여부를 확인 한다.  
3. LogoutFilter
   - 로그아웃 요청 시에 처리해야할 것들을 설정 
4. UsernamePasswordAuthenticationFilter
   - 로그인 요청시 UsernamePasswordToken을 생성하여 AuthenticationManager에게 인증 권한을 위임하고 인증 성공 시 SecurityContext에 인증 정보를 넣어준다.
5. AnonymousAuthenticationFilter
   - 앞선 필터에서 모든 인증이 실패할 경우 AnonymousAuthentication을 SecurityContext에 넣어준다. 
6. SessionManagementFilter
   - 세션에 정상 여부와 세션 정책과 일치 한지 확인 한다. 
7. ExceptionTranslationFilter
   - AuthenticationException 이나 AccessDeniedException 을 발생시켜준다.
8. FilterSecurityInterceptor
   - AccessDecisionManger 로 부터 인가 정책을 위임하며 권한이 없을경우 AccessDeniedException울 발생하며 ExceptionTranslationFilter 에서 예외를 처리해준다.
