package saint.cheshire.test_app_not_code_volume_1.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String MESSAGE = """
            Incoming HTTP request
            {} {}, status: {} for {} ms
            request: {}
            response: {}
            """;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long start = currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long duration = currentTimeMillis() - start;

        String method = request.getMethod();
        String path = request.getRequestURI();
        String requestBody = objectMapper.readTree(new String(requestWrapper.getContentAsByteArray(), UTF_8)).toPrettyString();
        String responseBody = objectMapper.readTree(new String(responseWrapper.getContentAsByteArray(), UTF_8)).toPrettyString();

        int status = response.getStatus();

        if (SC_BAD_REQUEST >= status) {
            log.error(MESSAGE, method, path, status, duration, requestBody, responseBody);
        } else {
            log.info(MESSAGE, method, path, status, duration, requestBody, responseBody);
        }

        responseWrapper.copyBodyToResponse();
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
