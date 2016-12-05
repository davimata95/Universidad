package fdi.ucm.cuentacuentas;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Alvaro on 10/05/2016.
 */
public class CantPagoDialog extends DialogFragment {
    EditText cantidad;
    private boolean total;
    public CantPagoDialog(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dlgview = inflater.inflate(R.layout.fragment_intcantidad, container);

        // Ponemos el título al diálogo
        getDialog().setTitle("Añada cantidad pagada:");

         cantidad=(EditText)dlgview.findViewById(R.id.textcantidad);
        Bundle bun=getArguments();
        total=bun.getBoolean("total");

        //boton aceptar
        Button aceptar = (Button) dlgview.findViewById(R.id.butonaceptarcant);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String canti=cantidad.getText().toString();
                float cant=0;
                if(!canti.trim().equals("")) {
                    try{
                     cant = Float.parseFloat(cantidad.getText().toString());

                }catch(NumberFormatException e){
                    Toast.makeText(getContext(),"Debe introducir una cantidad numerica",Toast.LENGTH_LONG);
                        return;
                }
                    if (!total) {
                        ((ActivityAdminGrp) getActivity()).anadirpago(cant);
                        dismiss();
                    } else {
                        ((ActivityAdminGrp) getActivity()).anadirdebototal(cant);
                        dismiss();
                    }
                }else{

                    Toast.makeText(getActivity().getApplicationContext(),"Debe introducir una cantidad",Toast.LENGTH_LONG);
                }
            }
        });



        return dlgview;
    }





}
