package template.mybatis.springboot.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author lwq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {

    //用户id
    private String userId;

    //随机生成的uuid
    private String token;

}
