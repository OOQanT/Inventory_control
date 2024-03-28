package test.connect.mssql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ran;
    private String email;

    public Mail(String ran, String email) {
        this.ran = ran;
        this.email = email;
    }

    public void changeRan(String ran){
        this.ran = ran;
    }
}
