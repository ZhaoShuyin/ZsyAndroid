package zsy.cn.rxokretrofit.net;

//import java.util.Observable;

//import android.database.Observable;
//import io.reactivex.Observable;
//import rx.Observable
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zsy.cn.rxokretrofit.bean.BeanA;

/**
 * Created by Zsy on 2019/3/10 17:51
 */
public interface NetApi {
    /**
     * 1使用注解设置请求方式及参数
     * 2直接写返回的Bean类型
     * 3定义方法名
     */
    @GET("zsy")
    Call<BeanA> callA();

    @GET("zsy")
    Call<BeanA> callB(@Query("name") String name);


    //
    @GET("zsy")
    Observable<BeanA> callObserver(@Query("name") String name);

}
