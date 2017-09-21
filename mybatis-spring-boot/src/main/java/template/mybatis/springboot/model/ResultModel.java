package template.mybatis.springboot.model;

/**
 * 自定义返回结果
 * @author lwq
 */

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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
    
    
    


//    @Override
//	public String toString() {
//		return "ResultModel [code=" + code + ", message=" + message + ", data=" + data + ", total=" + total + "]";
//	}
//	public int getCode() {
//		return code;
//	}
//	public ResultModel setCode(int code) {
//		return this;
//	}
//	public String getMessage() {
//		return message;
//	}
//	public ResultModel setMessage(String message) {
//		return this;
//	}
//	public Object getData() {
//		return data;
//	}
//	public ResultModel setData(Object data) {
//		return this;
//	}
//	public long getTotal() {
//		return total;
//	}
//	public ResultModel setTotal(long total) {
//		return this;
//	}
//	
//	public ResultModel() {
//		// TODO Auto-generated constructor stub
//	}
//	public  ResultModel Builder(){
//		return this;
//	}
	
    
    
	
	
	
  
}