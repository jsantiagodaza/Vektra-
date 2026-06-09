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

        String contenido
                = "<!DOCTYPE html>"
                + "<html lang='es'>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<style>"
                + "body { background:#1a1a2e; display:flex; justify-content:center; align-items:center; padding:20px; font-family: Arial; }"
                + ".ticket { background:white; width:420px; border-radius:16px; overflow:hidden; }"
                + ".ticket-header { background:#f39c12; color:white; padding:24px; text-align:center; }"
                + ".ticket-body { padding:24px; }"
                + ".route { display:flex; justify-content:space-between; margin-bottom:20px; }"
                + ".station { text-align:center; }"
                + ".label { font-size:10px; color:#888; }"
                + ".name { font-weight:bold; margin-top:4px; }"
                + ".divider { border-top:2px dashed #eee; margin:16px 0; }"
                + ".info { margin-bottom:10px; }"
                + ".price { font-size:28px; font-weight:bold; color:#f39c12; text-align:center; }"
                + ".footer { text-align:center; font-size:12px; color:#777; margin-top:10px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='ticket'>"
                + "<div class='ticket-header'>"
                + "<h2>Vetkra</h2>"
                + "<p>Sistema de Transporte </p>"
                + "</div>"
                + "<div class='ticket-body'>"
                + "<div class='route'>"
                + "<div class='station'>"
                + "<div class='label'>Origen</div>"
                + "<div class='name'>" + t.getEstacionOrigen().getNombre() + "</div>"
                + "</div>"
                + "<div class='station'>"
                + "<div class='label'>Destino</div>"
                + "<div class='name'>" + t.getEstacionDestino().getNombre() + "</div>"
                + "</div>"
                + "</div>"
                + "<hr class='divider'>"
                + "<div class='info'><b>Pasajero:</b> " + t.getPasajero().getNombre() + "</div>"
                + "<div class='info'><b>Fecha:</b> " + t.getFecha() + "</div>"
                + "<div class='info'><b>Código:</b> " + t.getCodigo() + "</div>"
                + "<div class='price'>$" + t.getPrecio() + "</div>"
                + "<hr class='divider'>"
                + "<div class='footer'>"
                + "QR: " + t.getCodigoQR() + "<br>"
                + "🐻 Viktor dice: ¡Buen viaje!"
                + "</div>"
                + "</div>"
                + "</div>"
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

            mensaje.setContent(contenido, "text/html; charset=utf-8");

            Transport.send(mensaje);

            System.out.println("Correo enviado correctamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}