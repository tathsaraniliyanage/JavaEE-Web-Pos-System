package lk.ijse.gdse68.webpos2.controller;


import com.google.gson.Gson;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.webpos2.bo.CustomerBO;
import lk.ijse.gdse68.webpos2.bo.ItemBo;
import lk.ijse.gdse68.webpos2.bo.impl.CustomerBOIMPL;
import lk.ijse.gdse68.webpos2.bo.impl.ItemBOImpl;
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
    private final ItemBo itemBo=new ItemBOImpl();

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

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        System.out.println(code);
        try (var writer = resp.getWriter()) {
            var customerBOIMPL = new CustomerBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");

            if (code != null) {

                ItemDTO item = itemBo.getItem(code, connection);

                ResponseUtil successfully = ResponseUtil
                        .builder()
                        .code(200)
                        .description("Successfully get Item")
                        .data(item)
                        .build();
                Gson gson = new Gson();
                String jsonString = gson.toJson(successfully);
                resp.getWriter().print(jsonString);
            } else {
                // Get all customers
                List<ItemDTO> itemDTOS = itemBo.getAllItems(connection);
                ResponseUtil successfully = ResponseUtil
                        .builder()
                        .code(200)
                        .description("Successfully get All Items")
                        .data(itemDTOS)
                        .build();
                Gson gson = new Gson();
                String jsonString = gson.toJson(successfully);
                resp.getWriter().print(jsonString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        System.out.println(code);
        try (var writer = resp.getWriter()) {
            var customerBOIMPL = new CustomerBOIMPL();
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");

            if (code != null) {
                ItemDTO item = itemBo.getItem(code, connection);
                String[] split = item.getImage().split("\\.");
                String extension=split[split.length-1];

                // Read the image from the file path and encode it in Base64
                if (item.getImage() != null && !item.getImage().isEmpty()) {
                    File imageFile = new File(item.getImage());
                    if (imageFile.exists()) {
                        byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        item.setImage("data:image/"+extension+";base64,"+base64Image); // Update the image field with the Base64-encoded string
                    } else {
                        logger.warn("Image file not found: " + item.getImage());
                    }
                }

                ResponseUtil successfully = ResponseUtil
                        .builder()
                        .code(200)
                        .description("Successfully get Item")
                        .data(item)
                        .build();
                Gson gson = new Gson();
                String jsonString = gson.toJson(successfully);
                resp.getWriter().print(jsonString);
            } else {
                // Get all items
                List<ItemDTO> itemDTOS = itemBo.getAllItems(connection);
                for (ItemDTO itemDTO : itemDTOS) {
                    String[] split = itemDTO.getImage().split("\\.");
                    String extension=split[split.length-1];
                    if (itemDTO.getImage() != null && !itemDTO.getImage().isEmpty()) {
                        File imageFile = new File(itemDTO.getImage());
                        if (imageFile.exists()) {
                            byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            itemDTO.setImage("data:image/"+extension+";base64,"+base64Image); // Update the image field with the Base64-encoded string
                        } else {
                            logger.warn("Image file not found: " + itemDTO.getImage());
                        }
                    }
                }

                ResponseUtil successfully = ResponseUtil
                        .builder()
                        .code(200)
                        .description("Successfully get All Items")
                        .data(itemDTOS)
                        .build();
                Gson gson = new Gson();
                String jsonString = gson.toJson(successfully);
                resp.getWriter().print(jsonString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error processing request", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseUtil responseUtil=null;
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria");
            return;
        }
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

            logger.info("Invoke idGenerate()");
            itemDTO.setCode(Util.idGenerate());


            /**Save Image to Sever*/
            try {
                String base64Image = itemDTO.getImage();
                String filePath = "imageBacut/"+itemDTO.getCode()+".png";
                String imagePath = saveImage(base64Image,filePath);
                itemDTO.setImage(imagePath);
            } catch (IOException e) {

                System.err.println("Failed to save image: " + e.getMessage());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                responseUtil = ResponseUtil.builder().code(500).description(e.getMessage()).data(null).build();
            }

            /**     Save to DB      */
            String result=itemBo.saveItem(itemDTO,connection);
            resp.setStatus(HttpServletResponse.SC_CREATED);

            responseUtil = ResponseUtil.builder().code(201).description(result).data(itemDTO).build();
            Gson gson = new Gson();
            String jsonString = gson.toJson(responseUtil);
            resp.getWriter().print(jsonString);

        } catch (Exception e) {
            logger.error("Connection failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    public static String saveImage(String base64Image,String filePath) throws IOException {
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageBytes);
           return file.getAbsolutePath();
        }
    }

}
