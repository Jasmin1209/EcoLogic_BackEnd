package br.com.ifba.ecologic_back_end.modulos.usuario.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.mail.from}")
    private String remetente;

    public void enviarEmailRedefinicaoSenha(String destinatario, String nome, String token) {
        String linkRedefinicao = frontendUrl + "/redefinir-senha?token=" + token;

        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(remetente.trim());
            helper.setTo(destinatario.trim());
            helper.setSubject("Redefinição de senha - EcoLogic");
            helper.setText(
                    "Olá, " + nome + "!\n\n" +
                            "Recebemos uma solicitação para redefinir a sua senha.\n" +
                            "Clique no link abaixo para criar uma nova senha:\n\n" +
                            linkRedefinicao + "\n\n" +
                            "Este link é válido por 30 minutos. Se você não solicitou essa alteração, ignore este e-mail.\n\n" +
                            "Atenciosamente,\nEquipe EcoLogic"
            );

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Falha ao montar e-mail de redefinicao de senha", e);
        }
    }
}
