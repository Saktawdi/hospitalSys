package top.cnwdi.Sakta_hospitalSys.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.cnwdi.Sakta_hospitalSys.model.entity.Users;

import java.util.Date;

/**
 * JWT工具类
 */
public class JWTUtil {
    /**
     * 过期时间：一天
     */
    private  static final long EXPIRE=6000*60*24;
    /**
     * 加密密钥
     */
    private static final String SECRET_KEY="cnwdi:190306";
    /**
     * token前缀
     */
    private  static final String TOKEN_PREFIX="sakta_wdi";
    /**
     * subject
     */
    private  static final String SUBJECT="cnwdi:666";

    public static String getJsonToken(Users user){
        String token=Jwts.builder().setSubject(SUBJECT)
                .claim("num",user.getNum())
                .claim("role",user.getRole())
                .claim("name",user.getName())
                .claim("affiliation",user.getAffiliation())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
        token=TOKEN_PREFIX+token;
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try {
            Claims claim=Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
            return claim;
        }catch(Exception e){
            return null;
        }
    }
}
