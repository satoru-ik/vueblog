package com.satoru.blog.service;

import com.satoru.blog.mapper.MailMapper;
import com.satoru.blog.model.User;
import com.satoru.blog.utility.Utility;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Service
public class MailService {
    private JavaMailSender sender;
    private MailProperties properties;
    private MailMapper mailMapper;
    private MimeMessage message;
    private MimeMessageHelper mimeMessageHelper;
    /**
     * 部署在服务器之前记得修改website
     */
    private String website = "http://localhost:8081";

    public MailService(JavaMailSender sender, MailProperties properties, MailMapper mailMapper) {
        this.sender = sender;
        this.properties = properties;
        this.mailMapper = mailMapper;
        this.message = sender.createMimeMessage();

        try {
            this.mimeMessageHelper = new MimeMessageHelper(this.message, true, "UTF-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送验证邮箱的邮件
     */
    @Async
    public Boolean sendMailActivate(User user, String title, String token) {
        try {
            this.mimeMessageHelper.setFrom(this.properties.getUsername());
            this.mimeMessageHelper.setSubject("新用户邮箱注册验证");
            this.website = String.format("%s/user/activate?token=%s", this.website, token);
            String content = this.mailMapper.selectMailById(1);
            content = content.replace("{username}", user.getNickname());
            content = content.replace("{website}", this.website);
            this.mimeMessageHelper.setText(content, true);
            this.mimeMessageHelper.setTo(user.getEmail());
            this.sender.send(this.message);
            Utility.log("异步发送邮件结束");
        } catch (MessagingException e) {
            Utility.log("异步发送账户激活邮件出错,%s", e);
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 发送修改密码的邮件
     */
    @Async
    public Boolean sendMailEditPassword(User user, String title, String token) {

        try {
            this.mimeMessageHelper.setFrom(this.properties.getUsername());
            this.mimeMessageHelper.setSubject("重置密码");
            this.website = String.format("%s/user/resetPassword?token=%s", this.website, token);
            String content = this.mailMapper.selectMailById(2);
            content = content.replace("{username}", user.getNickname());
            content = content.replace("{website}", this.website);
            this.mimeMessageHelper.setText(content, true);
            this.mimeMessageHelper.setTo(user.getEmail());
            this.sender.send(this.message);
            Utility.log("异步发送邮件结束");
        } catch (MessagingException e) {
            Utility.log("异步发送账户激活邮件出错,%s", e);
            e.printStackTrace();
        }
        return true;
    }
}
