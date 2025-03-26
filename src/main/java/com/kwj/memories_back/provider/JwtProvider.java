package com.kwj.memories_back.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// class: JWT 생성 및 검증 기능 제공자
// description : JWT 암호화 알고리즘 HS256 //
// description : 암호화에 사용되는 Secret Key는 환경변수의 jwt.secret으로 사용 //
// description : JWT 만료 기간은 9시간 //
// JWT 기능을 Spring Bean으로 떠넘기기 위해 @Component 사용 (인스턴스 생성 권한)
@Component
public class JwtProvider {

    
    @Value("${jwt.secret}") // application.properties에 있는 키를 사용하기 위해
    private String secretKey;

    

    // function: JWT 생성 메서드 //
    // description : 생성되는 JWT payload에는 sub(subject), iat(issuedAt), eat(expirationAt) 값이 들어가도록 //
    // description : sub - 사용자의 아이디,  iat - 생성 시 시간, exp - 생성시간 + 9시간 //
    public String create(String userId) {

        Date expiration = Date.from(Instant.now().plus(9, ChronoUnit.HOURS));

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); //  hmacShaKeyFor가 byte로 만들어내오기에 secretKey를 getBytes로해서 바이트로 나오게

        String jwt = null;

        try{

            jwt = Jwts.builder()
                        .signWith(key, SignatureAlgorithm.HS256) // 암호화는 HS256
                        .setSubject(userId) // subject로는 userId
                        .setIssuedAt(new Date()) // 현재 시간으로 발행
                        .setExpiration(expiration) // 만료 기간 // 이렇게 다해서 payload만들고
                        .compact(); // 이걸 통해 압축(인코딩)

        }catch(Exception e) {
            e.printStackTrace();
        }

        return jwt;

    }

    // 생성을 위에서 만들어서 이제 검증을 만들 것
    // function: JWT 검증 메서드 //
    // description : 검증이 성공적으로 완료되면 subject에 있는 userId 반환 //
    public String validate(String jwt) { // 검증할 jwt 받기

        String userId = null;

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // 복호화할 키를 가져오고

        try{

            userId = Jwts.parserBuilder() // 파싱을 해야되서
                            .setSigningKey(key)
                            .build() // 이걸로 빌드 작업
                            .parseClaimsJws(jwt) // 이걸로 jwt 전달하면 parsing 될거고 이걸로
                            .getBody() // 바디값 받악오고
                            .getSubject(); // 페이로드 된거에서 subject 받아오기
            
        }catch(Exception e) {
            e.printStackTrace();
        }

        return userId;
    }


}
