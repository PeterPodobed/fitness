package by.it_academy.fitness.web.controllers.filter;

import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.users.api.IUserService;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userService;
    private final JwtTokenUtil jwtTokenUtil;
//    private final IUserService iUserService;
    private final IUserEntityToDto iUserEntityToDto;
    private final IUserDao iUserDao;


    public String token;

    public JwtFilter(UserDetailsService userService, JwtTokenUtil jwtTokenUtil,
//                     IUserService iUserService,
                     IUserEntityToDto iUserEntityToDto, IUserDao iUserDao) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
//        this.iUserService = iUserService;
        this.iUserEntityToDto = iUserEntityToDto;
        this.iUserDao = iUserDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

//        final String token = header.split(" ")[1].trim();
        token = header.split(" ")[1].trim();

        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = null;
        UserDto userModel = null;


        try {
//            userModel = iUserEntityToDto.convertUserEntityToDto(iUserDao.findByMail(JwtTokenUtil.getUsername(token)).get());
//            userModel = iUserService.findUserByMail(JwtTokenUtil.getUsername(token));
            userModel = findUserByMail(JwtTokenUtil.getUsername(token));
        } catch (UsernameNotFoundException e) {
            chain.doFilter(request, response);
            return;
        } catch (MultipleErrorResponse e) {
            throw new RuntimeException(e);
        }


        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
        }
        if (userModel != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String commaSeparatedListOfAuthorities = JwtTokenUtil.extractAuthorities(jwt);
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + commaSeparatedListOfAuthorities);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userModel, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        chain.doFilter(request, response);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private UserDto findUserByMail(String mail) throws MultipleErrorResponse {
        UserEntity user = iUserDao.findByMail(mail).get();
        return iUserEntityToDto.convertUserEntityToDto(user);
    }
}