package lk.ijse.gdse68.webpos2.controller;

/**
 * @author Sasindu Malshan
 * @project webpos2
 * @date 8/9/2024
 */

import com.google.gson.Gson;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.webpos2.bo.CustomerBOIMPL;
import lk.ijse.gdse68.webpos2.dto.CustomerDTO;
import lk.ijse.gdse68.webpos2.dto.ItemDTO;
import lk.ijse.gdse68.webpos2.util.ResponseUtil;
import lk.ijse.gdse68.webpos2.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
@WebServlet(urlPatterns = "/item", loadOnStartup = 2)
public class ItemController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria");
            return;
        }
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);


//            var customerBOIMPL = new CustomerBOIMPL();
            logger.info("Invoke idGenerate()");
            itemDTO.setCode(Util.idGenerate());
            // Save data in the DB
//            String result = customerBOIMPL.saveCustomer(itemDTO, connection);
//            resp.setStatus(HttpServletResponse.SC_CREATED);
            //Converting the Object to JSONString

            String base64Image = itemDTO.getImage();
            String filePath = "imageBacut/saved_image.png"; // Desired file path within project directory

            try {
                saveImage(base64Image, filePath);
                System.out.println("Image saved successfully at " + filePath);
            } catch (IOException e) {
                System.err.println("Failed to save image: " + e.getMessage());
            }
            ResponseUtil successfully = ResponseUtil.builder().code(201).description("Item saved successfully").data(itemDTO).build();
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

    public static void saveImage(String base64Image, String filePath) throws IOException {
        // Remove the "data:image/png;base64," prefix if it exists
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }

        // Decode the Base64 string into a byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Check if the byte array is empty or invalid
        System.out.println("Decoded image size: " + imageBytes.length + " bytes");

        // Create the directories if they don't exist
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        // Write the byte array to a file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageBytes);
            System.out.println("Image saved at: " + file.getAbsolutePath());
        }
    }
}
