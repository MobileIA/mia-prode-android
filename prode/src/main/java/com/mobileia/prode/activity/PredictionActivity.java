package com.mobileia.prode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileia.prode.R;
import com.mobileia.prode.entity.Match;
import com.mobileia.prode.rest.PredictionRest;

import java.util.Date;

public class PredictionActivity extends AppCompatActivity {
    /**
     * Constante que represetan el parametro de Match
     */
    public static final String EXTRA_MATCH = "com.mobileia.prode.EXTRA_MATCH";
    public static final String EXTRA_GROUP_ID = "com.mobileia.prode.EXTRA_GROUP_ID";
    /**
     * Constante para recibir los datos de la prediccion
     */
    public static final int REQUEST_CODE_FOR_MATCH = 52342;
    /**
     * instancia del campo de texto del equipo uno
     */
    protected TextView mResultOne;
    /**
     * Instancia del campo de texto del equipo dos
     */
    protected TextView mResultTwo;
    protected TextView mResultPenaltyOne;
    protected TextView mResultPenaltyTwo;
    protected RelativeLayout mContainerPenalties;
    /**
     * Almacena el partido abierto
     */
    protected Match mMatch;
    /**
     * Almacena el ID del grupo a editar
     */
    protected int mGroupId;
    /**
     * Variable que determinar si se esta enviando
     */
    protected boolean mIsSending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        // Procesar parametros
        processExtras();
        // Configurar vistas
        setUpViews();
    }

    /**
     * Funcion del boton confirmar
     * @param v
     */
    public void onClickConfirm(View v){
        // Verificar si ya no paso la fecha
        if(mMatch.day.getTime() <= new Date().getTime()){
            Toast.makeText(this, "Ups, tenes que pronosticar hasta 2 minutos antes del comienzo!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Verificar si ya se hizo click
        if(mIsSending){
            return;
        }
        // Seteamos que se esta enviando
        mIsSending = true;
        // Guardamos los datos en el partido
        mMatch.predicted_one = Integer.valueOf(mResultOne.getText().toString());
        mMatch.predicted_two = Integer.valueOf(mResultTwo.getText().toString());
        mMatch.predicted_penalty_one = Integer.valueOf(mResultPenaltyOne.getText().toString());
        mMatch.predicted_penalty_two = Integer.valueOf(mResultPenaltyTwo.getText().toString());
        // Llamar al servidor
        new PredictionRest(this).send(mGroupId, mMatch.id, mMatch.predicted_one, mMatch.predicted_two, mMatch.predicted_penalty_one, mMatch.predicted_penalty_two, new PredictionRest.OnSendComplete() {
            @Override
            public void onSuccess() {
                // Creamos intent para devolver los datos
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MATCH, mMatch);
                setResult(RESULT_OK, intent);
                finish();
                // cerramos modal
                finish();
            }

            @Override
            public void onError() {
                mIsSending = false;
                Toast.makeText(PredictionActivity.this, R.string.prediction_error_send, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Funcion para cerrar el modal
     * @param v
     */
    public void onClickBack(View v){
        finish();
    }

    /**
     * Funcion que suma un valor al equipo uno
     * @param v
     */
    public void onClickUpOne(View v){
        // Obtenemos valor actual
        String numberString = mResultOne.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Sumamos uno
        mResultOne.setText((number+1) + "");
        // Procesar penales
        processPenalties();
    }

    /**
     * Funcion que resta un valor al equipo uno
     * @param v
     */
    public void onClickDownOne(View v){
        // Obtenemos valor actual
        String numberString = mResultOne.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Verificamos si es 0
        if(number == 0){
            return;
        }
        // Restamos uno
        mResultOne.setText((number-1) + "");
        // Procesar penales
        processPenalties();
    }

    /**
     * Funcion que suma un valor al equipo uno
     * @param v
     */
    public void onClickUpTwo(View v){
        // Obtenemos valor actual
        String numberString = mResultTwo.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Sumamos uno
        mResultTwo.setText((number+1) + "");
        // Procesar penales
        processPenalties();
    }

    /**
     * Funcion que resta un valor al equipo uno
     * @param v
     */
    public void onClickDownTwo(View v){
        // Obtenemos valor actual
        String numberString = mResultTwo.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Verificamos si es 0
        if(number == 0){
            return;
        }
        // Restamos uno
        mResultTwo.setText((number-1) + "");
        // Procesar penales
        processPenalties();
    }
    /**
     * Funcion que suma un valor al equipo uno
     * @param v
     */
    public void onClickUpOnePenalty(View v){
        // Obtenemos valor actual
        String numberString = mResultPenaltyOne.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Sumamos uno
        mResultPenaltyOne.setText((number+1) + "");
    }

    /**
     * Funcion que resta un valor al equipo uno
     * @param v
     */
    public void onClickDownOnePenalty(View v){
        // Obtenemos valor actual
        String numberString = mResultPenaltyOne.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Verificamos si es 0
        if(number == 0){
            return;
        }
        // Restamos uno
        mResultPenaltyOne.setText((number-1) + "");
    }

    /**
     * Funcion que suma un valor al equipo uno
     * @param v
     */
    public void onClickUpTwoPenalty(View v){
        // Obtenemos valor actual
        String numberString = mResultPenaltyTwo.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Sumamos uno
        mResultPenaltyTwo.setText((number+1) + "");
    }

    /**
     * Funcion que resta un valor al equipo uno
     * @param v
     */
    public void onClickDownTwoPenalty(View v){
        // Obtenemos valor actual
        String numberString = mResultPenaltyTwo.getText().toString();
        // convertirmos a entero
        int number = Integer.valueOf(numberString);
        // Verificamos si es 0
        if(number == 0){
            return;
        }
        // Restamos uno
        mResultPenaltyTwo.setText((number-1) + "");
    }

    /**
     * Funcion que procesa si hay penales
     */
    protected void processPenalties(){
        // Verificar si el partido tiene penales
        if(mMatch.has_penalty != 1){
            return;
        }
        // convertirmos a entero
        int numberOne = Integer.valueOf(mResultOne.getText().toString());
        // convertirmos a entero
        int numberTwo = Integer.valueOf(mResultTwo.getText().toString());
        // Verificamos si hay empate
        if(numberOne == numberTwo){
            mContainerPenalties.setVisibility(View.VISIBLE);
        }else{
            mContainerPenalties.setVisibility(View.GONE);
            mResultPenaltyOne.setText("0");
            mResultPenaltyTwo.setText("0");
        }
    }

    /**
     * Configurar las vistas
     */
    protected void setUpViews(){
        // Obtenemos los campos de textos de resultado
        mResultOne = findViewById(R.id.result_team_one);
        mResultTwo = findViewById(R.id.result_team_two);
        mResultPenaltyOne = findViewById(R.id.result_team_one_penalty);
        mResultPenaltyTwo = findViewById(R.id.result_team_two_penalty);
        mContainerPenalties = findViewById(R.id.container_penalties);
        // Setear nombres de los equipos
        ((TextView)findViewById(R.id.title_team_one)).setText(mMatch.title_short_one);
        ((TextView)findViewById(R.id.title_team_two)).setText(mMatch.title_short_two);
        // Cargar prediccion
        if(mMatch.predicted_one > 0){
            mResultOne.setText(mMatch.predicted_one + "");
        }
        if(mMatch.predicted_two > 0){
            mResultTwo.setText(mMatch.predicted_two + "");
        }
        if(mMatch.predicted_penalty_one > 0){
            mResultPenaltyOne.setText(mMatch.predicted_penalty_one + "");
        }
        if(mMatch.predicted_penalty_two > 0){
            mResultPenaltyTwo.setText(mMatch.predicted_penalty_two + "");
        }
        // Verificar si mostramos los penales
        if(mMatch.has_penalty == 1 && mMatch.predicted_one == mMatch.predicted_two){
            mContainerPenalties.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Obtiene el partido a mostrar
     */
    protected void processExtras(){
        // Verificamos que haya parametros
        if (getIntent().getExtras() == null || getIntent().getExtras().isEmpty()) {
            // Cerrar pantalla
            finish();
            return;
        }
        // Obtenemos el usuario
        mMatch = getIntent().getExtras().getParcelable(EXTRA_MATCH);
        // obtenemos el ID del grupo
        mGroupId = getIntent().getExtras().getInt(EXTRA_GROUP_ID);
        // Verificar sino se cierra la pantalla
        if(mMatch == null){
            finish();
        }
    }

    /**
     * Inicia la pantalla con los parametros requeridos para devolver los datos
     * @param activity
     * @param match
     */
    public static void startActivityForResult(AppCompatActivity activity, int groupId, Match match){
        Intent intent = new Intent(activity, PredictionActivity.class);
        intent.putExtra(EXTRA_MATCH, match);
        intent.putExtra(EXTRA_GROUP_ID, groupId);
        activity.startActivityForResult(intent, REQUEST_CODE_FOR_MATCH);
    }

    public static Match onActivityResultForMatch(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE_FOR_MATCH && resultCode == RESULT_OK) {
            return data.getExtras().getParcelable(EXTRA_MATCH);
        }
        return null;
    }
}
