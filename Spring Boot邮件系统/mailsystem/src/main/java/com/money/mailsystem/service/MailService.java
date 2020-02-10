package com.money.mailsystem.service;

/**
 * @Author: Money
 * @Date: 2020/2/10 11:58
 */
public interface MailService {

    /**
     * 发送普通文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @return
     */
    boolean sendSimpleMail(String to, String subject, String content);

    /**
     * 发送Html邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @return
     */
    boolean sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    boolean sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送图片邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param rscPath
     * @param rscId
     */
    boolean sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
