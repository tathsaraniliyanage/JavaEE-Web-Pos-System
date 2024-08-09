package lk.ijse.gdse68.webpos2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {
    private String id;
    private String username;
    private String fullName;
    private String street;
    private String lane;
    private String city;
    private String email;
}
