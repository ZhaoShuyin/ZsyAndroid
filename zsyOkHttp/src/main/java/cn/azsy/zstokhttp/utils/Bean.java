package cn.azsy.zstokhttp.utils;

import java.util.List;

/**
 * Created by zsy on 2017/7/3.
 */

public class Bean {


    /**
     * retcode : 2000
     * msg : 版本
     * data : {"must":{"base_v":13,"nearest_v":14},"msg_arr":["1.xxx","2.ooo","3.yyy"]}
     */

    private int retcode;
    private String msg;
    /**
     * must : {"base_v":13,"nearest_v":14}
     * msg_arr : ["1.xxx","2.ooo","3.yyy"]
     */

    private DataBean data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * base_v : 13
         * nearest_v : 14
         */

        private MustBean must;
        private List<String> msg_arr;

        public MustBean getMust() {
            return must;
        }

        public void setMust(MustBean must) {
            this.must = must;
        }

        public List<String> getMsg_arr() {
            return msg_arr;
        }

        public void setMsg_arr(List<String> msg_arr) {
            this.msg_arr = msg_arr;
        }

        public static class MustBean {
            private int base_v;
            private int nearest_v;

            public int getBase_v() {
                return base_v;
            }

            public void setBase_v(int base_v) {
                this.base_v = base_v;
            }

            public int getNearest_v() {
                return nearest_v;
            }

            public void setNearest_v(int nearest_v) {
                this.nearest_v = nearest_v;
            }
        }
    }
}
