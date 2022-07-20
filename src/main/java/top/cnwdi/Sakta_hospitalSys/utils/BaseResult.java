package top.cnwdi.Sakta_hospitalSys.utils;

import java.io.Serializable;

/**
 * 基础返回结构类
 * @param <T>
 */
public  abstract  class BaseResult<T> implements Serializable {
        /**
         * 成功与否
         */
        private boolean success = false;
        /**
         * 返回的操作信息
         */
        private String message;
        /**
         * 返回的数据
         */
        private T data;
        /**
         * 操作码
         */
        private String code;

        public boolean isSuccess() {
                return success;
        }

        public void setSuccess(boolean success) {
                this.success = success;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public T getData() {
                return data;
        }

        public void setData(T data) {
                this.data = data;
        }

        public String getCode() {
                return code;
        }

        public void setCode(String code) {
                this.code = code;
        }
}

