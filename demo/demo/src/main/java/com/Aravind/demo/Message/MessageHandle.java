package com.Aravind.demo.Message;

public class MessageHandle {


    public static final String buildApplicationReceivedEmail(String fullName, String companyName) {
        return """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    color: #333;
                }
                .container {
                    padding: 20px;
                    border: 1px solid #ccc;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #f9f9f9;
                }
                h1 {
                    color: #4CAF50;
                }
                p {
                    line-height: 1.6;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Application Received!</h1>
                <p>Dear <strong>%s,</strong></p>
                <p>Thank you for applying to <strong>%s</strong>. We have received your application and will review it shortly.</p>
                <p>Best regards,<br>
                <strong>The Recruitment Team</strong></p>
            </div>
        </body>
        </html>
        """.formatted(fullName, companyName);
    }

    // Private method to generate the HTML email content
    public static final String buildWelcomeEmail(String fullName) {
        return """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    color: #333;
                }
                .container {
                    padding: 20px;
                    border: 1px solid #ccc;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #f9f9f9;
                }
                h1 {
                    color: #4CAF50;
                }
                p {
                    line-height: 1.6;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Welcome to RevHire!</h1>
                <p>Dear <strong>%s,</strong></p>
                <p>Thank you for registering with RevHire. We are excited to have you on board!</p>
                <p>Explore opportunities, apply for jobs, and take the next step in your career.</p>
                <p>Best regards,<br>
                <strong>The RevHire Team</strong></p>
            </div>
        </body>
        </html>
        """.formatted(fullName);
    }


    // Method to generate the HTML email content
    public static final String buildWelcomeEmployeeEmail(String fullName) {
        return """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    color: #333;
                }
                .container {
                    padding: 20px;
                    border: 1px solid #ccc;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #f9f9f9;
                }
                h1 {
                    color: #4CAF50;
                }
                p {
                    line-height: 1.6;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Welcome to RevHire!</h1>
                <p>Dear <strong>%s,</strong></p>
                <p>Thank you for registering with RevHire. We are excited to have you on board!</p>
                <p>Explore opportunities, apply for jobs, and take the next step in your career.</p>
                <p>Best regards,<br>
                <strong>The RevHire Team</strong></p>
            </div>
        </body>
        </html>
        """.formatted(fullName);
    }


    public static final String buildApplicationStatusEmail(String fullName, String companyName, String status) {
        String message;
        switch (status) {
            case "Shortlisted":
                message = "We are pleased to inform you that you have been <strong>shortlisted</strong> for the position at <strong>%s</strong>. Our team will contact you shortly to discuss the next steps.";
                break;
            case "Rejected":
                message = "We regret to inform you that your application for the position at <strong>%s</strong> has been <strong>rejected</strong>. We encourage you to apply for other opportunities in the future.";
                break;
            default:
                message = "Your application for the position at <strong>%s</strong> is currently <strong>pending</strong>. We will update you once we have more information.";
        }

        return """
    <html>
    <head>
        <style>
            body {
                font-family: Arial, sans-serif;
                color: #333;
            }
            .container {
                padding: 20px;
                border: 1px solid #ccc;
                max-width: 600px;
                margin: 0 auto;
                background-color: #f9f9f9;
            }
            h1 {
                color: #4CAF50;
            }
            p {
                line-height: 1.6;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Application Update</h1>
            <p>Dear <strong>%s,</strong></p>
            <p>%s</p>
            <p>Best regards,<br>
            <strong>The Recruitment Team</strong></p>
        </div>
    </body>
    </html>
    """.formatted(fullName, message.formatted(companyName));
    }
}
