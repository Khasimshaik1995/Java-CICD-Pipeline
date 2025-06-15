package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.IOException;
import javax.servlet.http.*;

public class VulnerableExample extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInput = request.getParameter("username");

        // 🔐 Hardcoded password (sensitive info)
        String dbPassword = "SuperSecret123";

        try {
            // 🔓 SQL Injection vulnerability
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", dbPassword);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
            stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 🔓 External input used in format string
        String message = String.format(userInput); // Tainted input
        response.getWriter().println("Message: " + message);
    }
}
