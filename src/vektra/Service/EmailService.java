/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vektra.Service;

/**
 *
 * @author User
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import vektra.Model.Ticket;




public class EmailService {

    private final String remitente = "xaviacostamaldonado@gmail.com";
    private final String contraseña = "zadk tuhu ovee ldxt";

    public void enviarTicket(Ticket t) {

        String destinatario = t.getPasajero().getCorreo();

        String asunto = "Ticket de Compra";

        String contenido =
            "<html>"
            + "<body style='font-family: Arial; padding:20px;'>"

            + "<h2 style='color:#2E86C1;'>🎫 Ticket de Compra</h2>"

            + "<hr>"

            + "<p><strong>Código:</strong> " + t.getCodigo() + "</p>"

            + "<p><strong>Ruta:</strong> " + t.getRuta() + "</p>"

            + "<p><strong>Precio:</strong> $" + t.getPrecio() + "</p>"

            + "<p><strong>Fecha:</strong> " + t.getFecha() + "</p>"

            + "<br>"

            + "<p>Gracias por tu compra.</p>"

            + "</body>"
            + "</html>";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, contraseña);
                }
            });

        try {

            Message mensaje = new MimeMessage(session);

            mensaje.setFrom(new InternetAddress(remitente));

            mensaje.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario)
            );

            mensaje.setSubject(asunto);

            mensaje.setText(contenido);

            Transport.send(mensaje);

            System.out.println("Correo enviado correctamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}