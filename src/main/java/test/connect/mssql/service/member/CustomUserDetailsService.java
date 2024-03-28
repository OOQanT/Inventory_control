package test.connect.mssql.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import test.connect.mssql.dto.member.CustomUserDetails;
import test.connect.mssql.entity.Member;
import test.connect.mssql.repository.member.MemberRepository;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("==================================인증시도==============================================");

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 일치하지 않습니다."));

        log.info("사용자={}", member.getUsername());
        if(member != null){
            return new CustomUserDetails(member);
        }
        return null;
    }
}
