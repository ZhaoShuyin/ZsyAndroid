package zsy.structure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import zsy.structure.view.QueueView;

/**
 * Title zsy.structure
 *
 * @author Zsy
 * @date 2019/9/10.
 */

public class QueueActivity extends Activity {
    QueueView queueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        queueView = findViewById(R.id.queue_view);
        queueView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueView.invalidate();
                Toast.makeText(QueueActivity.this, "刷新", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void start(View view){
        queueView.startLoop();
    }


    //1
    public void a(View view) {
        queueView.add(1);
    }

    //2
    public void b(View view) {
        queueView.add(2);
    }

    //3
    public void c(View view) {
        queueView.add(3);
    }

    //4
    public void d(View view) {
        queueView.add(4);
    }

    //5
    public void e(View view) {
        queueView.add(5);
    }

    //6
    public void f(View view) {
        queueView.add(6);
    }

    //7
    public void g(View view) {
        queueView.add(7);
    }

    //8
    public void h(View view) {
        queueView.add(8);
    }

    //9
    public void i(View view) {
        queueView.add(9);
    }

    //10
    public void j(View view) {
        queueView.add(10);
    }
    
}
