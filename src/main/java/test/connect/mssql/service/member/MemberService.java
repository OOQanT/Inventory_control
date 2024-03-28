package test.connect.mssql.service.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.connect.mssql.dto.member.JoinDto;
import test.connect.mssql.entity.Member;
import test.connect.mssql.repository.member.MemberRepository;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member joinMember(JoinDto joinDto){

        if(check_existUsername(joinDto.getUsername())){
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }

        if(check_existEmail(joinDto.getEmail())){
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        Member member = new Member(joinDto);
        member.changePassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        member.changeRole("ROLE_USER");
        member.changeMailAuthenticate(false);

        memberRepository.save(member);

        return member;
    }

    public void authed(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자 입니다."));

        member.changeMailAuthenticate(true);
    }

    public boolean check_existUsername(String username){
        return memberRepository.existsByUsername(username);
    }

    public boolean check_existEmail(String email){
        return memberRepository.existsByEmail(email);
    }

}
