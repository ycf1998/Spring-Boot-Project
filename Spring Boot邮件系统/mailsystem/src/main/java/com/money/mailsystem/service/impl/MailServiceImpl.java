package com.money.mailsystem.service.impl;

import com.money.mailsystem.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.websocket.MessageHandler;
import java.io.File;

/**
 *@program: mailsystem
 *@description: 邮件服务实现类
 *@author: Money
 *@create: 2020/02/10 12:01
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;    // 发件人邮箱
    @Value("${spring.mail.fromAlias}")
    private String fromAlias;   // 发件人别名（使用其他名称）

    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        try {
            logger.info("发送普通文本邮件：{},{},{}", to, subject, content);
            SimpleMailMessage message = new SimpleMailMessage();
            // 要使用别名，必须进行拼接转换
            String fromByte = new String((fromAlias + " <" + from + ">")
                    .getBytes("UTF-8"));
            message.setFrom(fromByte);
//            message.setFrom(from); // 可不使用别名，这样发件人那里会写发件人的邮箱
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            logger.info("发送普通文本邮件成功！");
        } catch (Exception e){
            logger.error("发送普通文本邮件失败：", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean sendHtmlMail(String to, String subject, String content) {
        try {
            logger.info("发送Html邮件：{},{},{}", to, subject, content);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String fromByte = new String((fromAlias + " <" + from + ">")
                    .getBytes("UTF-8"));
            helper.setFrom(fromByte);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // 开启html转换
            mailSender.send(message);
            logger.info("发送Html邮件成功！");
        } catch (Exception e){
            logger.error("发送Html邮件失败：", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean sendAttachmentsMail(String to, String subject, String content, String filePath) {
        try {
            logger.info("发送带附件的邮件：{},{},{},{}", to, subject, content, filePath);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String fromByte = new String((fromAlias + " <" + from + ">")
                    .getBytes("UTF-8"));
            helper.setFrom(fromByte);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            // 多附件只要传入文件数组，一个一个addAttachment就可以了
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator)); // 获取文件名
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("发送带附件的邮件成功！");
        } catch (Exception e){
            logger.error("发送带附件的邮件失败：", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        try {
            logger.info("发送图片邮件：{},{},{},{},{}", to, subject, content, rscPath, rscId);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String fromByte = new String((fromAlias + " <" + from + ">")
                    .getBytes("UTF-8"));
            helper.setFrom(fromByte);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            mailSender.send(message);
            logger.info("发送图片邮件成功！");
        } catch (Exception e){
            logger.error("发送图片邮件失败：", e);
            return false;
        }
        return true;
    }
}
