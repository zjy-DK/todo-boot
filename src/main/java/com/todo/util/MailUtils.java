package com.todo.util;

import cn.hutool.core.collection.CollectionUtil;
import com.todo.base.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author zjy
 * @date 2025/03/28  10:54
 */
@Slf4j
@Component
public class MailUtils {

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送人邮箱
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 具体发送邮件实现
     *
     * @param name    发送人名称
     * @param to      接收人","分割
     * @param subject 主题
     * @param content 内容
     * @param isHtml  内容类型
     * @param cc      抄送，多人用逗号隔开
     * @param bcc     密送，多人用逗号隔开
     * @param files   文件
     */
    public void send(String name, String to, String subject, String content, Boolean isHtml, String cc,
                     String bcc, List<File> files) {
        if (StringUtils.isEmpty(to)) {
            throw new UserException("接收人不能为空");
        }
        if (StringUtils.isEmpty(subject)) {
            throw new UserException("主题不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw new UserException("内容不能为空");
        }
        try {
            //true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            messageHelper.setFrom(new InternetAddress(name + "<" + from + ">"));
            //邮件收信人
            messageHelper.setTo(to.split(","));
            //邮件主题
            messageHelper.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, isHtml);
            //抄送
            if (StringUtils.isNotEmpty(cc)) {
                messageHelper.setCc(cc.split(","));
            }
            //密送
            if (StringUtils.isNotEmpty(bcc)) {
                messageHelper.setCc(bcc.split(","));
            }
            //添加邮件附件
            if (CollectionUtil.isNotEmpty(files)) {
                for (File file : files) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            // 邮件发送时间
            messageHelper.setSentDate(new Date());
            //正式发送邮件
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            log.error("邮件发送失败", e);
            throw new RuntimeException(e);
        }
    }
}
