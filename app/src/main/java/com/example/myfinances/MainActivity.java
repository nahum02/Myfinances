package com.example.myfinances;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private finances currentFinances;

    private EditText accountNumber, initialBalance, currentBalance, interestRate, paymentAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clearButton();
        currentFinances = new finances();
        saveButton();
        getFinances();
        radioButtonClicked();
    }


    private void radioButtonClicked() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rbCd = findViewById(R.id.radioButtonCd);
                        RadioButton rbLoans = findViewById(R.id.radioButtonLoans);
                        RadioButton rbChecking = findViewById(R.id.radioButtonChecking);

                        if (checkedId == rbCd.getId()) {
                            paymentAmount.setEnabled(false);

                        }
                        else if (checkedId == rbLoans.getId()) {
                            paymentAmount.setEnabled(true);
                            initialBalance.setEnabled(true);
                            paymentAmount.setEnabled(true);
                            interestRate.setEnabled(true);


                        } else if(checkedId == rbChecking.getId()) {
                            initialBalance.setEnabled(false);
                            paymentAmount.setEnabled(false);
                            interestRate.setEnabled(false);
                        }

                    }

                }
        );
    }






    private void clearButton() {
        Button clear = findViewById(R.id.buttonClear);
        clear.setOnClickListener(v -> {
            EditText accountNum = findViewById(R.id.editTextAN);
            accountNum.setText("");
            EditText initialBal = findViewById(R.id.editTextIB);
            initialBal.setText("");
            EditText currentBal = findViewById(R.id.editTextCB);
            currentBal.setText("");
            EditText interestRate = findViewById(R.id.editTextIR);
            interestRate.setText("");
            EditText paymentAmount = findViewById(R.id.editTextPayment);
            paymentAmount.setText("");
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            radioGroup.clearCheck();
        });
    }


    private void saveButton() {
        Button save = findViewById(R.id.buttonSave);
        RadioButton rbCd = findViewById(R.id.radioButtonCd);
        RadioButton rbLoans = findViewById(R.id.radioButtonLoans);


        finanacesDataSource ds = new finanacesDataSource(MainActivity.this);
        save.setOnClickListener(v -> {


            if (rbCd.isChecked()) {


                try {
                    ds.open();

                    if (currentFinances.getFinancesID() == -1) {
                        currentFinances.setAccountNumber(Float.parseFloat(accountNumber.getText().toString()));
                        currentFinances.setCurrentBalance(Float.parseFloat(currentBalance.getText().toString()));
                        currentFinances.setInitialBalance(Float.parseFloat(initialBalance.getText().toString()));
                        currentFinances.setInterestRate(Float.parseFloat(interestRate.getText().toString()));
                        ds.insertFinances(currentFinances);

                        int newId = ds.getLastContactID();
                        currentFinances.setFinancesID(newId);
                        Log.d("MainActivity", "finances Inserted");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    ds.close();
                }

            }


            else if (rbLoans.isChecked()) {




                try {
                    ds.open();

                    if (currentFinances.getLoansID() == -1) {
                        currentFinances.setAccountNumber(Float.parseFloat(accountNumber.getText().toString()));
                        currentFinances.setCurrentBalance(Float.parseFloat(currentBalance.getText().toString()));
                        currentFinances.setInitialBalance(Float.parseFloat(initialBalance.getText().toString()));
                        currentFinances.setInterestRate(Float.parseFloat(interestRate.getText().toString()));
                        currentFinances.setPaymentAmount(Float.parseFloat(paymentAmount.getText().toString()));
                        ds.insertLoans(currentFinances);

                        int newId = ds.getLastContactID();
                        currentFinances.setLoansID(newId);
                        Log.d("MainActivity",  "Loans Inserted");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    ds.close();
                }

            }

            else {



                try {
                    ds.open();

                    if (currentFinances.getCheckingID() == -1) {
                        currentFinances.setAccountNumber(Float.parseFloat(accountNumber.getText().toString()));
                        currentFinances.setCurrentBalance(Float.parseFloat(currentBalance.getText().toString()));
                        ds.insertChecking(currentFinances);

                        int newId = ds.getLastContactID();
                        currentFinances.setCheckingID(newId);
                        Log.d("Mainctivity", "Checking Inserted");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    ds.close();
                }

            }


        });


    }

    private void getFinances(){
        accountNumber = findViewById(R.id.editTextAN);
        initialBalance = findViewById(R.id.editTextIB);
        interestRate = findViewById(R.id.editTextIR);
        currentBalance = findViewById(R.id.editTextCB);
        paymentAmount = findViewById(R.id.editTextPayment);

    }


}