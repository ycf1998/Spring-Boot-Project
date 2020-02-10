package com.money.mailsystem.controller;

import com.money.mailsystem.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 *@program: mailsystem
 *@description:
 *@author: Money
 *@create: 2020/02/10 12:43
 */
@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping("/simpleMail")
    @ResponseBody
    public String sendSimpleMail(String email) {
        String subject = "麦尼的邮件系统";
        String content = "这是一封普通文本邮件";
        if (mailService.sendSimpleMail(email, subject, content)) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    @RequestMapping("/htmlMail")
    @ResponseBody
    public String sendHtmlMail(String email) {
        String subject = "麦尼的邮件系统";
        StringBuilder content = new StringBuilder();
        content.append("<html>\n")
                .append("<body>\n")
                .append("<h1>大标题</h1>\n")
                .append("</body>\n")
                .append("</html>\n");
        if (mailService.sendHtmlMail(email, subject, content.toString())) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    @RequestMapping("/attachmentsMail")
    @ResponseBody
    public String sendAttachmentsMail(String email) {
        String subject = "麦尼的邮件系统";
        String content = "带附件的邮件";
        String filePath = "F:\\1.txt";
        if (mailService.sendAttachmentsMail(email, subject, content, filePath)) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    @RequestMapping("/inlineResourcesMail")
    @ResponseBody
    public String sendInlineResourceMail(String email) {
        String subject = "麦尼的邮件系统";
        String imgPath = "F:\\person.jpg";
        String imgId = "001";
        StringBuilder content = new StringBuilder();
        content.append("<html>\n")
                .append("<body>\n")
                .append("这是图片邮件：<img src=\'cid:").append(imgId).append("\'>")
                .append("</body>\n")
                .append("</html>\n");
        if (mailService.sendInlineResourceMail(email, subject, content.toString(), imgPath, imgId)) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }


    /**
     *  使用模板邮件
     */
    @RequestMapping("/mailTemplate")
    @ResponseBody
    public String sendMailTemplate(String email) {
        Context context = new Context();
        context.setVariable("username", "user001"); // 这里就是填模板上的那个username
        String content = templateEngine.process("mailTemplate", context); // 读出html的文本

        String subject = "麦尼邮件系统";
        if (mailService.sendHtmlMail(email, subject, content)) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }
}
