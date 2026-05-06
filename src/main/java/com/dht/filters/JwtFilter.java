package com.dht.filters;

import com.dht.utils.JwtUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();

        // Kiểm tra xem request có vào các khu vực cần bảo mật không
        if (requestUri.startsWith(contextPath + "/api/secure") || requestUri.startsWith(contextPath + "/api/admin")) {
        
            String header = httpRequest.getHeader("Authorization");
            
            if (header == null || !header.startsWith("Bearer ")) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
                return;
            } else {
                String token = header.substring(7);
                try {
                    // 1. Lấy Username từ token
                    String username = JwtUtils.validateTokenAndGetUsername(token);
                    // 2. Lấy Role từ token (đã thêm vào JwtUtils ở bước trước)
                    String role = JwtUtils.getRoleFromToken(token); 

                    if (username != null && role != null) {
                        // 3. Chuyển String role thành danh sách GrantedAuthority
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority(role));

                        // 4. Nạp quyền vào đối tượng Authentication
                        UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(username, null, authorities);
                        
                        // 5. Lưu vào SecurityContext để Spring Security biết User này là ai, quyền gì
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                        chain.doFilter(request, response);
                        return;
                    }
                } catch (Exception e) {
                    // Token lỗi hoặc hết hạn
                }
            }

            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ hoặc hết hạn");
            return;
        }
        
        chain.doFilter(request, response);
    }
}