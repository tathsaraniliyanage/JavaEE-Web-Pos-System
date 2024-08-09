package lk.ijse.gdse68.webpos2.controller;

import com.google.gson.Gson;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.webpos2.bo.impl.CustomerBOIMPL;
import lk.ijse.gdse68.webpos2.dto.CustomerDTO;
import lk.ijse.gdse68.webpos2.util.ResponseUtil;
import lk.ijse.gdse68.webpos2.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
@WebServlet(urlPatterns = "/customer",loadOnStartup = 2)
public class Customer extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(Customer.class);
    Connection connection;

    @Override
    public void init() throws ServletException {
        System.out.println("init method invoked");
        logger.info("Init method invoked");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/cusReg");
            this.connection = pool.getConnection();
            logger.debug("Connection initialized", this.connection);

        } catch (SQLException | NamingException e) {
            logger.error("DB connection not init");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria");
            return;
        }
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            var customerBOIMPL = new CustomerBOIMPL();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            logger.info("Invoke idGenerate()");
            customer.setId(Util.idGenerate());
            // Save data in the DB
            String result = customerBOIMPL.saveCustomer(customer, connection);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            //Converting the Object to JSONString
            ResponseUtil successfully = ResponseUtil.builder().code(201).description("Customer saved successfully").data(customer).build();
            // Create a Gson instance
            Gson gson = new Gson();
            // Convert the object to a JSON string
            String jsonString = gson.toJson(successfully);
            resp.getWriter().print(jsonString);

        } catch (Exception e) {
            logger.error("Connection failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var customerId = req.getParameter("customerId");
            var customerBOIMPL = new CustomerBOIMPL();
            if(customerBOIMPL.deleteCustomer(customerId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId");
        System.out.println(customerId);
        try (var writer = resp.getWriter()) {
            var customerBOIMPL = new CustomerBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");

            if (customerId != null) {
                // Get customer by ID
                var customer = customerBOIMPL.getCustomer(customerId, connection);

                ResponseUtil successfully = ResponseUtil
                        .builder()
                        .code(200)
                        .description("Successfully get All Customers")
                        .data(customer)
                        .build();
                Gson gson = new Gson();
                // Convert the object to a JSON string
                String jsonString = gson.toJson(successfully);
                resp.getWriter().print(jsonString);
            } else {
                // Get all customers
                List<CustomerDTO> customers = customerBOIMPL.getAllCustomers(connection);
                ResponseUtil successfully = ResponseUtil
                        .builder()
                        .code(200)
                        .description("Successfully get All Customers")
                        .data(customers)
                        .build();
                Gson gson = new Gson();
                // Convert the object to a JSON string
                String jsonString = gson.toJson(successfully);
                resp.getWriter().print(jsonString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var customerBOIMPL = new CustomerBOIMPL();
            var customerId = req.getParameter("customerId");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            if(customerBOIMPL.updateCustomer(customerId,customer,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }


}
