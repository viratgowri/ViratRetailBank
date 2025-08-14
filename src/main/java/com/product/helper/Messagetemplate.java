package com.product.helper;

public class Messagetemplate {
	
	
	 public static String htmlEmailTemplate(String token, String code) {
	        String url = "http://127.0.0.1:8070/verify?token=" + token + "&code=" + code;

	        return """
	            <html>
	                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
	                    <div style="max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
	                        <h2 style="color: #2c3e50;">Welcome!</h2>
	                        <p style="font-size: 16px; color: #333;">
	                            Please click the button below to verify your account:
	                        </p>
	                        <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #3498db; color: white; text-decoration: none; border-radius: 5px;">
	                            Verify Your Account
	                        </a>
	                        <p style="margin-top: 20px; font-size: 12px; color: #777;">
	                            If you did not request this, please ignore this email.
	                        </p>
	                    </div>
	                </body>
	            </html>
	            """.formatted(url);
	    }
	

}
