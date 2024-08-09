package lk.ijse.gdse68.webpos2.util;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sasindu Malshan
 * @project webpos2
 * @date 8/5/2024
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@ToString
public class ResponseUtil implements Serializable {
    private int code ;
    private String description;
    private Object data;

}
