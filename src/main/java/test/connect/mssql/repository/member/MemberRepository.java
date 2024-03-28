package test.connect.mssql.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import test.connect.mssql.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}
