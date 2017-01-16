package fdi.ucm.cuentacuentas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Pikup on 07/05/2016.
 */
public class PhotoSelectorDialog extends DialogFragment {

    private EditText mEditText;
    private int opcion = 0;

    public PhotoSelectorDialog(int o) {
        this.opcion = o;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dlgview = inflater.inflate(R.layout.fragment_select_avatar, container);

        // Ponemos el título al diálogo
        getDialog().setTitle(R.string.selectav_method);

        // Cámara
        Button camera = (Button) dlgview.findViewById(R.id.butAvCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opcion == 1) {
                    ((ConfigGrupo) getActivity()).avatarPhoto();
                } if (opcion == 2) {
                    ((ConfigUsuario) getActivity()).avatarPhoto();
                }
                dismiss();
            }
        });

        // Galería
        Button gallery = (Button) dlgview.findViewById(R.id.butAvGallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opcion == 1) {
                    ((ConfigGrupo) getActivity()).avatarGallery();
                } if (opcion == 2) {
                    ((ConfigUsuario) getActivity()).avatarGallery();
                }
                dismiss();
            }
        });

        // Por defecto
      /*  Button defaultAvatar = (Button) dlgview.findViewById(R.id.butAvDefault);
        defaultAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opcion == 1) {
                    ((ConfigGrupo) getActivity()).avatarDefault();
                } if (opcion == 2) {
                    ((ConfigUsuario) getActivity()).avatarDefault();
                }
                dismiss();
            }
        });*/

        // Cancelar
        Button cancel = (Button) dlgview.findViewById(R.id.butAvCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dlgview;
    }
}
