package zsy.structure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import zsy.structure.view.StackView;

/**
 * Title zsy.structure
 * 数据结构栈
 * @author Zsy
 * @date 2019/9/10.
 */

public class StackActivity extends Activity {

    StackView stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        stackView = findViewById(R.id.stack_view);
        stackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackView.invalidate();
                Toast.makeText(StackActivity.this, "刷新", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void start(View view){
        stackView.startLoop();
    }


    //1
    public void a(View view) {
        stackView.add(1);
    }

    //2
    public void b(View view) {
        stackView.add(2);
    }

    //3
    public void c(View view) {
        stackView.add(3);
    }

    //4
    public void d(View view) {
        stackView.add(4);
    }

    //5
    public void e(View view) {
        stackView.add(5);
    }

    //6
    public void f(View view) {
        stackView.add(6);
    }

    //7
    public void g(View view) {
        stackView.add(7);
    }

    //8
    public void h(View view) {
        stackView.add(8);
    }

    //9
    public void i(View view) {
        stackView.add(9);
    }

    //10
    public void j(View view) {
        stackView.add(10);
    }
    
}
