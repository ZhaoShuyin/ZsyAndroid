package cn.azsy.zstokhttp.jsonparse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import cn.azsy.zstokhttp.R;
import cn.azsy.zstokhttp.zsyokhttp.zok.checkjson.JsonValidator;

/**
 * Created by zsy on 2017/6/13.
 */

public class JsonParseActivity extends AppCompatActivity {

    String jsonSvae = "{\"retcode\":2000," +
            "\"msg\":\"获取成功！\"," +
            "\"data\":" +
            "[" +
            "        {\"link\":\"\\u82f9\\u679c\",\"img\":\"网址链接一\",\"type\":\"类型一\"}," +
            "         {\"link\":\"\\u8bdd\\u8d39\",\"img\":\"网址链接二\",\"type\":\"类型e二\"}," +
            "        {\"link\":\"xxx\",\"img\":\"网址链接三\",\"type\":\"类型三\"}," +
            "        {\"link\":\"apicore\\/info\\/pk_shop_list\",\"img\":\"网址链接四\",\"type\":\"类型s四\"}" +
            "]" +
            "}";


    String json = "{\"retcode\":2000," +
            "\"msg\":\"获取成功！\"," +
            "\"data\":" +
            "[" +
            "        {\"link\":\"\\u82f9\\u679c\",\"img\":\"网址链接一\",\"type\":\"类型一\"}," +
            "         {\"link\":\"\\u8bdd\\u8d39\",\"img\":\"网址链接二\",\"type\":\"类型e二\"}," +
            "        {\"link\":\"xxx\",\"img\":\"网址链接三\",\"type\":\"类型三\"}," +
            "        {\"link\":\"apicore\\/info\\/pk_shop_list\",\"img\":\"网址链接四\",\"type\":\"类型s四\"}" +
            "]" +
            "}";


    String json2 = "{\"retcode\":2000," +
            "\"msg\":\"获取成功！\"," +
            "\"data\":" +
            "[" +
            "        {\"link\":\"\\u82f9\\u679c\",\"img\":\"网址链接一\",\"type\":\"类型一\"}," +
            "         {\"link\":\"\\u8bdd\\u8d39\",\"img\":\"网址链接二\",\"type\":\"类型e二\"}," +
            "        {\"img\":\"网址链接三\",\"type\":\"类型三\"}," +
            "        {\"link\":\"apicore\\/info\\/pk_shop_list\",\"img\":\"网址链接四\",\"type\":\"类型s四\"}" +
            "]" +
            "}";

    TextView jsonTv, tv1, tv2, tv3;
    private BeanTest beanTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparse);
        jsonTv = (TextView) findViewById(R.id.tv_json);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv3 = (TextView) findViewById(R.id.tv_3);
    }

    static int position = 0;
    static int Size = 0;

    public void showBean(View view) {
//        beanTest.getData().size();

//        Log.d("jsonjson", "beanTest.getData().size() = = "+beanTest.getData().size());

        if (beanTest != null) {
            Log.d("jsonjson", "showBean: ===== " + beanTest.toString());
        } else {
            Toast.makeText(this, "beanTest==null", Toast.LENGTH_SHORT).show();
        }
        if (Size != 0) {
            tv1.setText("link== " + beanTest.getData().get(position).getLink());
            tv2.setText("img== " + beanTest.getData().get(position).getImg());
            tv3.setText("type== " + beanTest.getData().get(position).getType());
            position++;
            if (position == (Size)) {
                position = 0;
            }
        } else {
            Toast.makeText(this, "beanTest.Size == 0", Toast.LENGTH_SHORT).show();
        }
    }


    public void startParse(View view) {
        Gson gson = new Gson();
        beanTest = gson.fromJson(json, BeanTest.class);
        Size = beanTest.getData().size();
    }


    public void startTryParse(View view) {
        Gson gson = new Gson();
        try {
            beanTest = gson.fromJson(json, BeanTest.class);
            Size = beanTest.getData().size();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            tv1.setText("json数据异常");
        }

    }


    public void validateJson(View view) {
        JsonValidator jsonValidator = new JsonValidator();
        boolean validate = jsonValidator.validate(json);
        tv3.setText("校验json格式 ===" + validate);
    }


    public void emptyJson(View view) {
        json = "";
    }

    public void showJson(View view) {
        jsonTv.setText(json);
    }

    public void upDataJson(View view) {
        json = json + "kljnadk";
    }

    public void wrongJson(View view) {
        json = "agasdklfgja";
    }

    public void resetJson(View view) {
        json = jsonSvae;
    }


    class BeanTest {
        /**
         * retcode : 2000
         * msg : 获取成功！
         * data : [{"link":"苹果","img":"banner/20170228/10679607268103.jpg","type":"1"},{"link":"话费","img":"banner/20170328/53323424671971.jpg","type":"1"},{"link":"xxx","img":"banner/20170328/92209139672033.jpg","type":"3"},{"link":"apicore/info/pk_shop_list","img":"banner/20170516/70544691904291.jpg","type":"4"}]
         */
        private int retcode;
        private String msg;
        /**
         * link : 苹果
         * img : banner/20170228/10679607268103.jpg
         * type : 1
         */
        private List<DataBean> data;

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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public class DataBean {
            private String link;
            private String img;
            private String type;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

}
