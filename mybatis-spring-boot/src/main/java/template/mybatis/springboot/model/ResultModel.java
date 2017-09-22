package template.mybatis.springboot.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 自定义返回结果
 * @author lwq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel {
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回结果描述
     */
    private String message;
    /**
     * 返回内容
     */
    private Object data;

    /**
     * 总数量
     */
    private long total;

    public ResultModel(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
    public ResultModel(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public ResultModel(ResultStatus status, Object data) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }
    public ResultModel(ResultStatus status, Object data,long total) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
        this.total=total;
    }


    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }


    public static ResponseEntity<ResultModel> fail(ResultStatus error) {
        return  new ResponseEntity<>(ResultModel.error(error), HttpStatus.OK);
    }

    public static ResponseEntity<ResultModel> fail(int code,String message) {
        return  new ResponseEntity<>(new ResultModel(code,message), HttpStatus.OK);
    }
    public static ResponseEntity<ResultModel> success() {
        return  new ResponseEntity<>(new ResultModel(ResultStatus.SUCCESS), HttpStatus.OK);
    }

    public static ResponseEntity<ResultModel> success(Object data,long total) {
        return  new ResponseEntity<>(new ResultModel(ResultStatus.SUCCESS,data,total), HttpStatus.OK);
    }

    public static ResponseEntity<ResultModel> success(Object data) {
        return  new ResponseEntity<>(new ResultModel(ResultStatus.SUCCESS,data), HttpStatus.OK);
    }

}