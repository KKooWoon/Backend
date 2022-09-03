package wit.shortterm1.kkoowoon.global.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import wit.shortterm1.kkoowoon.global.error.exception.EmptyTokenException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;
import wit.shortterm1.kkoowoon.global.error.exception.InvalidValueException;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${spring.jwt.blacklist.access-token}")
    private String blackListATPrefix;

    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.equals("")) {
            throw new EmptyTokenException(ErrorCode.EMPTY_TOKEN_ERROR);
        }
        return authorizationHeader.split(" ")[1];
    }

    public String getKakaoId(HttpServletRequest request) {
        return getValues(extractAccessToken(request.getHeader("Authorization")));
    }

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
