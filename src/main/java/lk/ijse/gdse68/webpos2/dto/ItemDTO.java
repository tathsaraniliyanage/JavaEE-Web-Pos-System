package lk.ijse.gdse68.webpos2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project webpos2
 * @date 8/9/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemDTO {
    private String code;
    private String item_name;
    private String qty;
    private String price;
    private String description;
    private String image;
}
