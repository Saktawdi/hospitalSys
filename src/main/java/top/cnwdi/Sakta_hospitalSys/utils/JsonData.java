package top.cnwdi.Sakta_hospitalSys.utils;

public class JsonData {
    private int code;
    private Object data;
    private String msg;
    public JsonData(){
    }
    public JsonData(int code, Object data){
        this.code = code;
        this.data = data;
    }
    public JsonData(int code, Object data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonData buildSuccess(Object data) {
        return new JsonData(0,data);
    }
    public JsonData buildError(String msg){
        return new JsonData(-1,"",msg);
    }
    public JsonData buildError(int code,String msg){
        return new JsonData(code,"",msg);
    }
}
