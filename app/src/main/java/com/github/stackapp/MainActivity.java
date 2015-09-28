package com.github.stackapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private MainActivity instance;

    private TextView stackSizeTextView;
    private EditText stackInputEditText;
    private Button pushButton;
    private Button popButton;
    private Button clearButton;
    private TextView stackTextView;
    private TextView actionTextView;
    private Boolean test = false;

    private ArrayList<Integer> stackArrayList = new ArrayList<Integer>();
    private Integer stackLimit = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);

        setUpUiElements();

    }

    private void setUpUiElements() {
        stackSizeTextView = (TextView) findViewById(R.id.activity_main_stack_size_textview);
        stackSizeTextView.setText(getString(R.string.stack_size) + " " + stackLimit.toString());
        stackInputEditText = (EditText) findViewById(R.id.activity_main_stack_input_edittext);
        pushButton = (Button) findViewById(R.id.activity_main_push_button);
        pushButton.setOnClickListener(this);
        popButton = (Button) findViewById(R.id.activity_main_pop_button);
        popButton.setOnClickListener(this);
        clearButton = (Button) findViewById(R.id.activity_main_clear_button);
        clearButton.setOnClickListener(this);
        stackTextView = (TextView) findViewById(R.id.activity_main_stack_textview);
        actionTextView = (TextView) findViewById(R.id.activity_main_action_textview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.exit) {
            finish();

            return true;
        } else if (id == R.id.change_stack_size) {
            changeStackSizeDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.activity_main_push_button: {
                pushStack(stackInputEditText.getText().toString());
                break;
            }
            case R.id.activity_main_pop_button: {
                popStack();
                break;
            }
            case R.id.activity_main_clear_button: {
                clearStack(false);
                break;
            }
        }
    }

    public void changeStackSizeDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_change_stack_size, null);
        final EditText changeStackSizeEditText = (EditText) view.findViewById(R.id.dialog_change_stack_size_edittext);
        changeStackSizeEditText.setText(String.valueOf(stackLimit));


        AlertDialog.Builder dialog = new AlertDialog.Builder(instance);
        dialog.setTitle(getString(R.string.actionbar_change_stack_size));
        dialog.setView(view);


        dialog.setPositiveButton(getString(R.string.enter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = changeStackSizeEditText.getText().toString();
                if(validateMaxInput(input, changeStackSizeEditText))
                {
                    Integer newStackLimit = Integer.valueOf(input);

                    if (newStackLimit == 0)
                    {
                        errorPopUp(getString(R.string.stack_size_error_empty));
                    }
                    else {


                        if (stackLimit > newStackLimit) {
                            clearStack(true);
                        } else {
                            actionTextView.setText(getString(R.string.changed_stack_size));
                        }

                        stackLimit = newStackLimit;
                        stackSizeTextView.setText(getString(R.string.stack_size) + " " + stackLimit.toString());

                    }


                }

            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();

    }

    public Boolean validateInput(String input, EditText editText) {

        boolean success = false;

        String regex = "^[0-9]{1}$";

//		stackInputEditText.setError(null);

        if (TextUtils.isEmpty(input) || !input.matches(regex)) {
            editText.setError(getString(R.string.invalid_stack_input));
            editText.requestFocus();
        } else {
            success = true;
        }

        return success;
    }

    public Boolean validateMaxInput(String input, EditText editText) {

        boolean success = false;

        String regex = "^[0-9]*$";

//		stackInputEditText.setError(null);

        if (TextUtils.isEmpty(input) || !input.matches(regex)) {
            editText.setError(getString(R.string.invalid_stack_input));
            editText.requestFocus();
        } else {

           success = true;
        }

        return success;
    }


    public Boolean pushStack(String input) {

        boolean success = true;
        if (stackInputEditText != null) {
            //String input = stackInputEditText.getText().toString();

            if (validateInput(input, stackInputEditText)) {
                if (stackArrayList.size() < stackLimit) {
                    stackArrayList.add(Integer.valueOf(input));
                    if (!test) {
                        displayStack();
                        actionTextView.setText(getString(R.string.pushed) + " " + input);
                    }
                } else {
                    errorPopUp(getString(R.string.push_error_full_stack));
                    if (!test) {
                        displayStack();
                        actionTextView.setText(null);
                    }
                }
            } else {
                success = false;
            }
        }
        return success;
    }

    public void popStack() {

        if (stackArrayList.size() > 0) {
            String removedElement = stackArrayList.get(stackArrayList.size() - 1).toString();
            stackArrayList.remove(stackArrayList.size() - 1);
            actionTextView.setText(getString(R.string.popped) + " " + removedElement);

        } else {
            errorPopUp(getString(R.string.stack_empty));
            actionTextView.setText(null);
        }

        displayStack();

    }

    public void clearStack(Boolean byStackChange ) {

        stackInputEditText.setText(null);

        stackArrayList.clear();
        if(byStackChange) {
            actionTextView.setText(getString(R.string.changed_stack_size) + ", " + getString(R.string.cleared));
        }
        else{
            actionTextView.setText(getString(R.string.cleared));
        }
        displayStack();
    }

    private void displayStack() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (stackArrayList.isEmpty()) {
            stackTextView.setText(getString(R.string.stack_empty));
        } else {
            sb.append(TextUtils.join(",", stackArrayList));


            sb.append("]");

            if (stackArrayList.size() == stackLimit) {
                sb.append("\n");
                sb.append(getString(R.string.stack_full));
            }

            stackTextView.setText(sb.toString());
        }
    }

    private void errorPopUp(String errorMessage) {
        AlertDialog builder = new AlertDialog.Builder(instance)
                .setTitle(getString(R.string.error_dialog_pop_up_title))
                .setMessage(errorMessage)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).show();
    }

    public int get(int numberInList) {
        return stackArrayList.get(numberInList);
    }

    public int getListSize() {
        return stackArrayList.size();
    }

    public Boolean isListEmpty() {
        if (stackArrayList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void testMethod() {
        test = true;
    }

    public void endTestMethod() {
        test = false;
    }

}
