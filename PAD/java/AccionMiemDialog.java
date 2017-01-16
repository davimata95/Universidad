package fdi.ucm.cuentacuentas;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Alvaro on 10/05/2016.
 */
public class AccionMiemDialog extends DialogFragment {

    private EditText mEditText;

    public AccionMiemDialog(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dlgview = inflater.inflate(R.layout.fragmento_accionmiembro, container);

        // Ponemos el título al diálogo
        getDialog().setTitle("Seleccione Accion");



        Button pago = (Button) dlgview.findViewById(R.id.butonpago);
        pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityAdminGrp) getActivity()).accionpago();
                dismiss();
            }
        });


        Button deuda = (Button) dlgview.findViewById(R.id.butondeuda);
        deuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityAdminGrp) getActivity()).acciondeuda();
                dismiss();
            }
        });

        Button perfil=(Button)dlgview.findViewById((R.id.butonverperfil));
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityAdminGrp) getActivity()).verPerfil();
                dismiss();
            }
        });

        Button borrar=(Button)dlgview.findViewById((R.id.butonborrarmiembro));
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityAdminGrp) getActivity()).borrarmiembro();
                dismiss();
            }
        });




        return dlgview;
    }



}
