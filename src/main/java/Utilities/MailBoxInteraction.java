package Utilities;

import org.apache.commons.codec.digest.DigestUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONObject;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MailBoxInteraction {
    private final OkHttpClient client = new OkHttpClient();

    public String getDomainNames() throws IOException {
        Request request = new Request.Builder()
                .url("https://privatix-temp-mail-v1.p.rapidapi.com/request/domains/")
                .get()
                .addHeader("x-rapidapi-key", "48de17534cmsh68d303b0d6d21b9p1fb17bjsn42d6dbd7a462")
                .addHeader("x-rapidapi-host", "privatix-temp-mail-v1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        String[] domains = responseBody.split("\",\"");

        System.out.println("Available domain names: " + responseBody
                + "\nDomain to be used for temp e-mails generation: " + domains[1]);
        return domains[1];
    }

    public HashMap checkMailBox(String email) throws IOException, NoSuchAlgorithmException {
        String md5Hash = generateMd5Hash(email);
        Request request = new Request.Builder()
                .url("https://privatix-temp-mail-v1.p.rapidapi.com/request/mail/id/" + md5Hash + "/")
                .get()
                .addHeader("x-rapidapi-key", "48de17534cmsh68d303b0d6d21b9p1fb17bjsn42d6dbd7a462")
                .addHeader("x-rapidapi-host", "privatix-temp-mail-v1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("response.body().string(): " + response.peekBody(10000).string());

        String responseBody = response.peekBody(10000).string();

        JSONObject Jobject = new JSONObject(responseBody.trim().substring(1, responseBody.lastIndexOf("]")));

        HashMap<String, String> mailData = new HashMap<String, String>();
        mailData.put("mail_from", Jobject.getString("mail_from"));
        mailData.put("mail_subject", Jobject.getString("mail_subject"));
        mailData.put("mail_html", Jobject.getString("mail_html"));
        System.out.println("mailHtml: " + mailData.get("mail_html"));

        return mailData;
    }

    public String getVerificationLink(String mailBody) {
        String verificationLink = mailBody.substring(mailBody.indexOf("http://www.cleverbot.com/mz/sv/"), mailBody.lastIndexOf("\">www.cleverbot.com/mz/sv/"));
        System.out.println("Verification link parsed: " + verificationLink);

        return verificationLink;
    }

    public String generateMd5Hash(String value) throws NoSuchAlgorithmException {
        String myHash = DigestUtils.md5Hex(value);
        System.out.println("MD5 hash for " + value + " is generated: " + myHash);

        return myHash;
    }

    public MailBoxInteraction() throws NoSuchAlgorithmException {
    }
}
