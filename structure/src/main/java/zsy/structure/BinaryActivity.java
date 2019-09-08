package zsy.structure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import zsy.myapp.R;
import zsy.structure.view.BinaryTreeView;

public class BinaryActivity extends Activity {
    EditText editText;
    BinaryTreeView treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_tree);
        editText = findViewById(R.id.et_nubmber);
        treeView = findViewById(R.id.rbt_view);
    }

    int[] ints = new int[]{5, 2, 8, 1, 4, 6, 9, 30, 20, 40, 15, 25, 35, 45};
    int i = -1;

    public void init(final View view) {
        i++;
        if (i >= ints.length) {
            return;
        }
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                treeView.add(ints[i]);
                init(view);
            }
        }, 4000);
    }

    public void add(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        Integer integer = Integer.valueOf(s);
        treeView.add(integer);
    }

    public void delete(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        Integer integer = Integer.valueOf(s);
        treeView.delete(integer);
    }

    public void find(View view) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return;
        }
        Integer integer = Integer.valueOf(s);
        treeView.find(integer);
    }


    public void fristTraverse(View view) {
        Toast.makeText(this, "先序遍历", Toast.LENGTH_SHORT).show();
        treeView.prevTraverse();
    }

    public void middleTraverse(View view) {
        treeView.middleTraverse();
    }

    public void lastTraverse(View view) {
        treeView.subTraverse();
    }

}
