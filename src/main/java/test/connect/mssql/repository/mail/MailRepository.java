package test.connect.mssql.repository.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import test.connect.mssql.entity.Mail;

import java.util.Optional;

public interface MailRepository extends JpaRepository<Mail,Long> {
    Optional<Mail> findByRanAndEmail(String ran, String email);
}
