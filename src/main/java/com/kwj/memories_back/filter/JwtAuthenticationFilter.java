package com.kwj.memories_back.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kwj.memories_back.provider.JwtProvider;
import com.kwj.memories_back.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// class: JWT Bearer Token 인증 처리를 위한 필터 //
// description : 필터 처리로 인증이 완료되면 접근 주체의 값에는 userId가 주입 //
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider; // 애가 컴포넌트로 되어 있기에 이 클래스도 @Component를 넣어서 Spring이 관리하도록
    private final UserRepository userRepository; 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)   
            throws ServletException, IOException { // OncePerRequestFilter(추상클래스)확장하면 JwtAuthenticationFilter를 필터 클래스로 사용가능 
        
                try{
                    String token = getToken(request);
                    if(token == null) {
                        filterChain.doFilter(request, response);
                        return; // null이면 필터를 하고 return;으로 마무리
                    }

                    String userId = jwtProvider.validate(token); // null값을 초기값으로 잡았기에 오류 발생시 null (jwt가 말도 안되거나 키 값이 안나오거나 파싱시 만료기간이 넘었을때) 값 나올 수 있는 거 인지
                    if(userId==null){
                        filterChain.doFilter(request, response);
                        return;
                    } // 여기까지 해서 누군지 알게 됨

                    // 데이터베이스에서 이 아이디가 있는지 한번 더 확인 -> UserRepository로 가서 만들고 다시 와서 의존성 주입
                    boolean existUser = userRepository.existsByUserId(userId);
                    if(!existUser) {
                        filterChain.doFilter(request, response);
                        return;
                    }

         // 인증된 사용자인걸 확인하고 인증 정보를 다른 곳(config or controller)에서 쓸 수 있도록, 필터가 끝나면 데이터가 사라져서 context에다가 넣어야 함(깔끔하게 하기 위해 밑에서 setContext 메서드로)
                    setContext(userId, request);


                }catch(Exception e) {
                    e.printStackTrace();
                }

                filterChain.doFilter(request, response);  
                // request는 header의 authorization에 담겨서 Bearer  (띄어쓰기 표현한거임)로 시작, 그래서 Bearer (띄어쓰기 표현한거임) 뒤에거(Token)를 가져오기 위한 과정

    }
    // function: Request 객체에서 Token 추출 메서드
    private String getToken(HttpServletRequest request){
        
        // description : Request 객체에서 Authorization header 값 추출 //
        String authorization = request.getHeader("Authorization");
        boolean hasAuthorization = StringUtils.hasText(authorization); // hasText() 빈문자열로만 이루어져있거나 공백이면 false 반환
        if(!hasAuthorization) return null;

        // description : Bearer 인증 방식인지 확인 //
        boolean isBearer = authorization.startsWith("Bearer "); // 'Bearer ' 로 시작하는 지 체크
        if(!isBearer) return null;

        // description : Authorization 필드 값에서 Token 추출 //
        String token = authorization.substring(7);
        return token;

    }

    // function: Security Context 생성 및 등록 //
    private void setContext(String userId, HttpServletRequest request) {

        // description : 접근 주체의 정보가 담길 인증 토큰 생성 //
        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES); //(아이디, 비번, 권한)

        // description : 생성한 인증 토큰이 어떤 요청의 정보인지 상세 내역 추가 // 
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // description : 빈 Security Context 생성 //
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        // description : 생성한 Security Context에 접근 주체 정보 주입 //
        securityContext.setAuthentication(authenticationToken);

        // description : 생성한 Security Context 등록 //
        SecurityContextHolder.setContext(securityContext);

    }
    
}
