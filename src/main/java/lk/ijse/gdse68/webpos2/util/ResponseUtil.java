package lk.ijse.gdse68.webpos2.util;

import lombok.*;

import java.io.Serializable;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
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
