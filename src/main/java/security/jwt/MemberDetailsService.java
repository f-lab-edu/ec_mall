package security.jwt;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) {
        MemberDao.UserDao memberDao = memberMapper.findByEmail(email);
        return User.builder()
                .username(String.valueOf(memberDao.getAccountId()))
                .password(memberDao.getPassword())
                .roles(memberDao.getRoles().toString())
                .build();
    }
}