package test.connect.mssql.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import test.connect.mssql.entity.Mail;
import test.connect.mssql.repository.mail.MailRepository;


import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;

    public String sendSimpleEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("인증키 생성");
        message.setTo(email);

        String ran = createRandomNum();
        message.setText(ran);

        javaMailSender.send(message);

        Mail mail = new Mail(ran,email);
        mailRepository.save(mail);

        return ran;
    }

    public String sendHTMLEmail(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String ran = createRandomNum();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("인증키 생성");
            helper.setTo(email);
            helper.setText(setContext(ran), true);

            helper.addInline("image1", new ClassPathResource("static/images/mnd_logo.png"));
            helper.addInline("image2", new ClassPathResource("static/images/image-2.png"));


           /* message.setSubject("인증키 생성");
            message.setText(setContext(ran), "UTF-8", "html");
            message.addRecipients(MimeMessage.RecipientType.TO,email);*/

            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

        return ran;
    }


    public boolean mail_authentication(String ran, String email){
        Mail mail = mailRepository.findByRanAndEmail(ran, email)
                .orElseThrow(() -> new NoSuchElementException("인증번호가 일치하지 않습니다."));

        log.info("인증키 ={}",mail.getRan());

        if(mail.getRan().equals(ran)){
            return true;
        }
        return false;
    }

    public String setContext(String ran){
        Context context = new Context();
        context.setVariable("ran", ran);
        return templateEngine.process("mail",context);
    }

    public String createRandomNum(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());



        for(int i=0; i<6; i++){
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
