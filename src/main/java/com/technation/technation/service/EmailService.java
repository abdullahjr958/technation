package com.technation.technation.service;

import com.technation.technation.model.Order;
import com.technation.technation.model.UnverifiedUser;
import com.technation.technation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public void sendOtpEmail(UnverifiedUser unverifiedUser, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(unverifiedUser.getEmail());
            helper.setSubject("Your TechNation Verification Code");

            String htmlContent = buildVerificationHtmlContent(unverifiedUser.getName(), otp);
            helper.setText(htmlContent, true); // true enables HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void orderConfirmationEmail(User user, Order order, double totalAmount){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("TechNation Order Confirmation Email");

            String htmlContent = buildOrderConfirmationHtmlContent(user.getName(), order.getOrderNo(),
                    order.getOrderedOn().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")),
                    String.format("%.2f", totalAmount), order.getAddress());
            helper.setText(htmlContent, true); // true enables HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildVerificationHtmlContent(String name, String otp) {
        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                <h2 style="color: #0e7490;">Hi, %s!</h2>
                <p>We received a request to verify your email address on <strong>TechNation</strong>.</p>
                <p>Please use the following One-Time PIN (OTP) to complete your signup:</p>
                <div style="font-size: 28px; font-weight: bold; color: #0e7490; margin: 20px 0;">%s</div>
                <p style="color: #555;">This OTP is valid for 15 minutes. If you didn't request this, please contact our support immediately.</p>
                <p style="margin-top: 30px;">Thank you,<br><strong>TechNation Team</strong></p>
                <hr style="margin: 30px 0;">
                <small style="color: #aaa;">Need help? Contact us at (021) 111-222-333</small>
            </div>
        </body>
        </html>
    """.formatted(name, otp);
    }

    private String buildOrderConfirmationHtmlContent(String name, String orderNo, String orderDate,
                                                     String totalAmount, String shippingAddress) {
        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                <h2 style="color: #0e7490;">Hi, %s!</h2>
                <p>Thank you for your order with <strong>TechNation</strong>!</p>
                <p>We've received your order <strong>#%s</strong> placed on <strong>%s</strong>.</p>
               \s
                <h3 style="margin-top: 30px;">📦 Order Summary:</h3>
                <ul style="padding-left: 20px; line-height: 1.6;">
                    <li><strong>Total Amount:</strong> Rs. %s</li>
                    <li><strong>Shipping Address:</strong> %s</li>
                </ul>

                <p>We’ll notify you once your order is shipped.</p>

                <p style="margin-top: 30px;">Thank you for choosing <strong>TechNation</strong> — where technology meets trust!</p>
                <p style="margin-top: 30px;"><strong>TechNation Team</strong></p>

                <hr style="margin: 30px 0;">
                <small style="color: #aaa;">Need help? Contact us at (021) 111-222-333 or support@technation.pk</small>
            </div>
        </body>
        </html>
   \s""".formatted(name, orderNo, orderDate, totalAmount, shippingAddress);
    }

}
