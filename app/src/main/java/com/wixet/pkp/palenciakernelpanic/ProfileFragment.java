package com.wixet.pkp.palenciakernelpanic;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wixet.pkp.palenciakernelpanic.Api.ApiCallback;
import com.wixet.pkp.palenciakernelpanic.Api.ApiResponse;
import com.wixet.pkp.palenciakernelpanic.Api.PKPManager;
import com.wixet.pkp.palenciakernelpanic.Model.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText email;
    EditText username;
    EditText firstname;
    EditText lastname;
    EditText password;
    Button save;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        email = (EditText) view.findViewById(R.id.email);
        username = (EditText) view.findViewById(R.id.username);
        firstname = (EditText) view.findViewById(R.id.firstname);
        lastname = (EditText) view.findViewById(R.id.lastname);
        password = (EditText) view.findViewById(R.id.password);
        save = (Button) view.findViewById(R.id.button_save);



        Toast.makeText(getActivity(),
                "Cargando datos...", Toast.LENGTH_SHORT).show();

        PKPManager.getManager(getActivity().getApplicationContext()).loadUser(new ApiCallback() {
            @Override
            public void success(ApiResponse response) {
                User user = ((PKPManager.LoadUserResponse) response).getUser();
                if (user == null) {
                    Toast.makeText(getActivity(),
                            "Error al cargar usuario!!!", Toast.LENGTH_SHORT).show();
                } else {
                    email.setText(user.getEmail());
                    username.setText(user.getUsername());
                    firstname.setText(user.getFirstname());
                    lastname.setText(user.getLastname());
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setEnabled(false);
                username.setEnabled(false);
                firstname.setEnabled(false);
                lastname.setEnabled(false);
                password.setEnabled(false);

                String userEmail = email.getText()+"";
                final String userUsername = username.getText()+"";
                String userFirstname = firstname.getText()+"";
                String userLastname = lastname.getText()+"";
                final String userPassword = password.getText()+"";

                PKPManager.getManager(getActivity().getApplicationContext()).updateUser(userUsername, userEmail, userFirstname, userLastname, userPassword,
                        new ApiCallback() {
                            @Override
                            public void success(ApiResponse response) {
                                if(((PKPManager.RegisterUserResponse) response).isOk()){
                                    Toast.makeText(getActivity(),
                                            "Datos actuallizados con éxito", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getActivity(),
                                            "Error al actualizar cambios" +
                                                    "", Toast.LENGTH_LONG).show();
                                }

                                email.setEnabled(true);
                                username.setEnabled(true);
                                firstname.setEnabled(true);
                                lastname.setEnabled(true);
                                password.setEnabled(true);
                            }
                        });
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
