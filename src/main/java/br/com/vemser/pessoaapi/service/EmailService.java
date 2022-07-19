package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.enums.TipoEmail;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo("prfultra@yahoo.com.br");
        message.setSubject("Assunto 1");
        message.setText("Meu e-mail!");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo("prfultra@yahoo.com.br");
        helper.setSubject("Assunto 1");
        helper.setText("Meu e-mail!");

        File file1 = new File("src/main/resources/diagram.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);

//        ClassLoader classLoader = getClass().getClassLoader();
//        File file2 = new File(classLoader.getResource("diagram.jpg").getFile());
//        FileSystemResource file = new FileSystemResource(file2);
//        helper.addAttachment(file2.getName(), file);

        emailSender.send(message);
    }

    public void sendEmail() {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo("prfultra@yahoo.com.br");
            mimeMessageHelper.setSubject("Assunto 3");
            mimeMessageHelper.setText(getContentFromTemplate(), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    public String getContentFromTemplate() throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", "Paulo");
        dados.put("email", "prfultra@yahoo.com.br");

        Template template = fmConfiguration.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void sendEmailPessoa(PessoaDTO pessoaDTO, String emailTipo) throws IOException, TemplateException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(pessoaDTO.getEmail());

            if (emailTipo.equals(TipoEmail.CREATE.getTipo())){
                mimeMessageHelper.setSubject("Assunto Criar PessoaEntity");
            } else if (emailTipo.equals(TipoEmail.PUT.getTipo())) {
                mimeMessageHelper.setSubject("Assunto Alterar PessoaEntity");
            } else {
                mimeMessageHelper.setSubject("Assunto Deletar PessoaEntity");
            }

            mimeMessageHelper.setText(getContentFromTemplatePessoa(pessoaDTO, emailTipo), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplatePessoa (PessoaDTO pessoaDTO, String emailTipo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", pessoaDTO.getNome());
        dados.put("email", from);

        Template template;
        if(emailTipo.equals(TipoEmail.CREATE.getTipo())){
            template = fmConfiguration.getTemplate("emailcriarpessoa-template.ftl");
        } else if (emailTipo.equals(TipoEmail.PUT.getTipo())){
            template = fmConfiguration.getTemplate("emailalterarpessoa-template.ftl");
        } else {
            template = fmConfiguration.getTemplate("emailexcluirpessoa-template.ftl");

        }
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void sendEmailEndereco(PessoaEntity pessoa, EnderecoDTO enderecoDTO, String emailTipo) throws IOException, TemplateException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(pessoa.getEmail());

            if (emailTipo.equals(TipoEmail.CREATE.getTipo())){
                mimeMessageHelper.setSubject("Assunto Criar Endereço");
            } else if (emailTipo.equals(TipoEmail.PUT.getTipo())) {
                mimeMessageHelper.setSubject("Assunto Alterar Endereço");
            } else {
                mimeMessageHelper.setSubject("Assunto Deletar Endereço");
            }

            mimeMessageHelper.setText(getContentFromTemplateEndereco(pessoa, enderecoDTO, emailTipo), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplateEndereco (PessoaEntity pessoa, EnderecoDTO enderecoDTO, String emailTipo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", pessoa.getNome());
        dados.put("email", from);

        Template template;
        if(emailTipo.equals(TipoEmail.CREATE.getTipo())){
            template = fmConfiguration.getTemplate("emailcriarendereco-template.ftl");
        } else if (emailTipo.equals(TipoEmail.PUT.getTipo())){
            template = fmConfiguration.getTemplate("emailalterarendereco-template.ftl");
        } else {
            template = fmConfiguration.getTemplate("emailexcluirendereco-template.ftl");

        }
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
