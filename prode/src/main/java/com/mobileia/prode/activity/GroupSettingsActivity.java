package com.mobileia.prode.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.mobileia.core.view.helper.ToolbarHelper;
import com.mobileia.prode.R;
import com.mobileia.prode.adapter.GroupSettingAdapter;
import com.mobileia.prode.entity.Friend;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.realm.GroupRealm;
import com.mobileia.recyclerview.MobileiaRecyclerView;

import java.util.ArrayList;

abstract public class GroupSettingsActivity extends AppCompatActivity implements GroupSettingAdapter.OnGroupSettingAdapterListener {

    /**
     * Constante que represetan el parametro del grupo
     */
    public static final String EXTRA_GROUP_ID = "com.mobileia.prode.EXTRA_GROUP_ID";
    /**
     * Almacena el ID del grupo
     */
    protected int mGroupId;
    /**
     * Almacena instancia del grupo
     */
    protected Group mGroup;
    /**
     * Almacena el adapter
     */
    protected GroupSettingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        // Procesar parametros
        processExtras();
        // Configurar Toolbar
        //new ToolbarHelper(this, R.id.toolbar).withTitle(mGroup.title).withBackButton();
        // Configurar vistas
        setUpViews();
    }
    /**
     * Manejador del menu de la toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLeaveGroup() {
        // Verificar cuantos grupos tiene creado
        /*if(new GroupRealm().fetchAll().size() == 0){
            // Enviar a la la pantalla principal
            ActivityHelper.createInstance(GroupSettingsActivity.this, StartProdeActivity.class);
        }else{
            // Enviar a la home
            ActivityHelper.createInstanceClearTop(GroupSettingsActivity.this, HomeActivity.class);
        }
        // Cerrar pantalla
        finish();*/
    }

    /**
     * Funcion que inicia el adaptador
     * @return
     */
    protected GroupSettingAdapter setUpAdapter(){
        // Creamos adaptador
        mAdapter = new GroupSettingAdapter(this, mGroup);
        // Asignamos los participantes
        mAdapter.setContacts(new ArrayList<Friend>(mGroup.contacts));
        // Asignamos listener
        mAdapter.setOnListener(this);
        // Devolvemos el adaptador
        return mAdapter;
    }

    /**
     * Funcion qie muestra los datos de las vistas
     */
    protected void setUpViews(){
        // Obtenemos lista
        MobileiaRecyclerView recyclerView = findViewById(R.id.list);
        // Asignamos layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Asignamos adapter
        recyclerView.setAdapter(setUpAdapter());
    }

    abstract public int getLayout();

    /**
     * Obtiene el grupo a mostrar
     */
    protected void processExtras(){
        // Verificamos que haya parametros
        if (getIntent().getExtras() == null || getIntent().getExtras().isEmpty()) {
            // Cerrar pantalla
            finish();
            return;
        }
        // obtenemos el ID del grupo
        mGroupId = getIntent().getExtras().getInt(EXTRA_GROUP_ID);
        // Buscamos el grupo guardado
        mGroup = new GroupRealm().fetchById(mGroupId);
        // Si no existe cerrar pantalla
        if(mGroup == null){
            finish();
        }
    }
    /**
     * Crear Intent con los parametros obligatorios
     * @param context
     * @param groupId
     * @return
     */
    public static Intent getStartIntent(Context context, int groupId) {
        Intent intent = new Intent(context, GroupSettingsActivity.class);
        intent.putExtra(EXTRA_GROUP_ID, groupId);
        return intent;
    }
}
