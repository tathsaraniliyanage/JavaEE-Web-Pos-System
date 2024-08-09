package lk.ijse.gdse68.webpos2.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
@WebServlet(urlPatterns = "/order", loadOnStartup = 2)
public class OrderController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(OrderController.class);
    Connection connection;
}
